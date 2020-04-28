package com.lsm.common.aop;

import com.google.gson.Gson;
import com.lsm.common.annotation.WebLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 执行顺序
 * Around>Before>接口中的逻辑代码>After>AfterReturning
 * Around:环绕,可以在切入点前后织入代码,并且可以自由的控制何时执行切点
 * Before:在切点之前,织入相关代码
 * After:在切点之后,织入相关代码
 * AfterReturning:在切点返回内容后,织入相关代码,一般用于对返回值做些加工处理的场景
 * AfterThrowing:用来处理当织入的代码抛出异常后的逻辑处理
 */
@Aspect
@Component
public class WebLogAspect {

    private static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * 换行符
     */
    private static final String LINE_SEPARATOR = System.lineSeparator();

    /**
     * 组装请求日志文本,推送kafka
     */
    private static StringBuffer sb;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 定义一个切点
     * 这里的表达式是自定义注解的全路径
     */
    @Pointcut("@annotation(com.lsm.common.annotation.WebLog)")
    public void webLog() {
    }

    /**
     * 环绕
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 打印出参
        logger.info("Response Args  : {}", new Gson().toJson(result));
        sb.append("Response Args  : " + new Gson().toJson(result) + LINE_SEPARATOR);
        // 执行耗时
        logger.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
        sb.append("Time-Consuming : " + (System.currentTimeMillis() - startTime) + " ms" + LINE_SEPARATOR);
        return result;
    }

    /**
     * 在切点之前织入
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取 @WebLog 注解的描述信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        WebLog webLog = method.getAnnotation(WebLog.class);
        String description = webLog.description();
        int level = webLog.level();
        String type = webLog.type().getType();
        // 打印请求相关参数
        logger.info("========================================== Start ==========================================");
        sb = new StringBuffer();
        sb.append("========================================== Start ==========================================" + LINE_SEPARATOR);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = df.format(new Date());
        logger.info("开始时间:" + now);
        sb.append("开始时间:" + now + LINE_SEPARATOR);
        // 打印请求 url
        logger.info("URL            : {}", request.getRequestURL().toString());
        sb.append("URL            : " + request.getRequestURL().toString() + LINE_SEPARATOR);
        // 打印描述信息
        logger.info("description    : {}", description);
        sb.append("description    : " + description + LINE_SEPARATOR);
        logger.info("level          : {}", level);
        sb.append("level          : " + level + LINE_SEPARATOR);
        logger.info("type           : {}", type);
        sb.append("type           : " + type + LINE_SEPARATOR);
        // 打印 Http method
        logger.info("HTTP Method    : {}", request.getMethod());
        sb.append("HTTP Method    : " + request.getMethod() + LINE_SEPARATOR);
        // 打印调用 controller 的全路径以及执行方法
        logger.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        sb.append("Class Method   : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + LINE_SEPARATOR);
        // 打印请求的 IP
        logger.info("IP             : {}", request.getRemoteAddr());
        sb.append("IP             : " + request.getRemoteAddr() + LINE_SEPARATOR);
        // 打印请求入参
        Object[] args = joinPoint.getArgs();
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        Gson gson = new Gson();
        String[] parameterNames = pnd.getParameterNames(method);
        Map<String, Object> paramMap = new HashMap<>(32);
        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(parameterNames[i], gson.toJson(args[i]));
        }
        logger.info("Request Args   : {}", paramMap);
        sb.append("Request Args   : " + paramMap + LINE_SEPARATOR);
    }

    /**
     * 在切点之后织入
     *
     * @throws Throwable
     */
    @After("webLog()")
    public void doAfter() throws Throwable {
        // 接口结束后换行，方便分割查看
        //logger.info("=========================================== End ===========================================" + LINE_SEPARATOR);
        //sb.append("=========================================== End ===========================================" + LINE_SEPARATOR);
    }

    /**
     * 处理完请求，返回内容
     *
     * @param result
     */
    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void doAfterReturning(Object result) {
        logger.info("方法的返回值             : {}", result);
        sb.append("方法的返回值             : " + result + LINE_SEPARATOR);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = df.format(new Date());
        logger.info("结束时间:" + now);
        sb.append("结束时间:" + now + LINE_SEPARATOR);
        logger.info("=========================================== End ===========================================");
        sb.append("=========================================== End ===========================================");
        logger.info(sb.toString());
        //推送kafka
        kafkaTemplate.send("test", sb.toString());
        logger.info("已推送");
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing("webLog()")
    public void throwException(JoinPoint joinPoint) {
        logger.info("方法异常时执行.....");
    }
}
