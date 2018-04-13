/**
 * NoFilter.java
 * Copyright (c) 2014, 北京直立人科技有限公司版权所有.
*/
package com.juyo.visa.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 如果不需要验证，请注释上这个。
 * 可以作用在类名或者方法上。
 * 如果作用在类名，则整个类都不需要sso校验。如果是方法的话，则方法不需要sso校验
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoFilter {

}
