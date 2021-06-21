package com.wastedrivinggroup.service.annotation;

import com.wastedrivinggroup.service.naming.RegisterPolicy;

import java.lang.annotation.*;

/**
 * @author chen
 * @date 2021-04-16
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceProvider {

    Class<? extends RegisterPolicy>[] register();
}

