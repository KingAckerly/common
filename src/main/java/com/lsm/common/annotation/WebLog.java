package com.lsm.common.annotation;

import com.lsm.common.enums.LogTypeEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebLog {
    /**
     * 方法描述,可使用占位符获取参数:{{tel}}
     *
     * @return
     */
    String description() default "";

    /**
     * 日志等级:自己指定，此处分为1-9
     *
     * @return
     */
    int level() default 0;

    /**
     * 日志类型
     *
     * @return
     */
    LogTypeEnum type() default LogTypeEnum.DEFAULT;
}
