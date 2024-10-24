package net.somta.core.annotation;

import java.lang.annotation.*;

/**
 * @author Gavin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permission {

    /**
     * default module  name
     * @return String
     */
    String module() default "";

    /**
     * default Permission  name
     * @return String
     */
    String key() default "";

    /**
     * default Permission  description
     * @return String
     */
    String description() default "";
}