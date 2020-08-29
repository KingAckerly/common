package com.lsm.common.aop;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.lsm.common.annotation.WebLog;
import com.lsm.common.bean.RequestLog;
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
     * 组装请求日志对象,推送kafka
     */
    private static RequestLog requestLog;

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
        requestLog.setResponseArgs(new Gson().toJson(result));
        // 执行耗时
        logger.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
        requestLog.setTimeConsuming((System.currentTimeMillis() - startTime) + " ms");
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
        requestLog = new RequestLog();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = df.format(new Date());
        logger.info("开始时间:" + now);
        requestLog.setStartTime(now);
        // 打印请求 url
        logger.info("URL            : {}", request.getRequestURL().toString());
        requestLog.setUrl(request.getRequestURL().toString());
        // 打印描述信息
        logger.info("description    : {}", description);
        requestLog.setDescription(description);
        logger.info("level          : {}", level);
        requestLog.setLevel(level);
        logger.info("type           : {}", type);
        requestLog.setType(type);
        // 打印 Http method
        logger.info("HTTP Method    : {}", request.getMethod());
        requestLog.setHttpMethod(request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        logger.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        requestLog.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        // 打印请求的 IP
        logger.info("IP             : {}", request.getRemoteAddr());
        requestLog.setIp(request.getRemoteAddr());
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
        requestLog.setRequestArgs(paramMap);
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
    }

    /**
     * 处理完请求，返回内容
     *
     * @param result
     */
    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void doAfterReturning(Object result) {
        logger.info("方法的返回值             : {}", result);
        requestLog.setResponseResult(result);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = df.format(new Date());
        logger.info("结束时间:" + now);
        requestLog.setEntTime(now);
        logger.info("=========================================== End ===========================================");
        //推送kafka
        //因为KafkaTemplate.send只支持String类型的消息,所以将自定义bean转成json字符串传输,当消息被消费者消费时,消费者再将json字符串重新转回自定义bean
        String message = JSON.toJSONString(requestLog);
        //未使用Kafka时请注释下面的推送
        //kafkaTemplate.send("requestLog", message);
    }


    /**
     * 后置异常通知
     */
    @AfterThrowing("webLog()")
    public void throwException(JoinPoint joinPoint) {
        logger.info("方法异常时执行.....");
    }
}
