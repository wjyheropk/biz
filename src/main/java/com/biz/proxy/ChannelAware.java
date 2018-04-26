package com.biz.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参见{@link ProductLineAware}
 *
 * @author wangjiayin
 * @see BusinessProxyAspect
 * @see ProductLineAware
 * @since 2017/11/18
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ChannelAware {
}
