package com.gwm.avpdatacloud.basicpackages.log.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GDC {
    public enum Level{INFO,WARN,ERROR,DEBUG}

    Level level() default Level.DEBUG;
}
