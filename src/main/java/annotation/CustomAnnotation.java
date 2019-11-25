package annotation;

import java.lang.annotation.*;

/**
 * 自定义注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomAnnotation {
    String value() default "";
}
