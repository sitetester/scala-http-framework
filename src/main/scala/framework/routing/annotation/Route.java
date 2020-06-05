package framework.routing.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@interface Route {
    String name();

    String path();

    String method() default "GET";
}