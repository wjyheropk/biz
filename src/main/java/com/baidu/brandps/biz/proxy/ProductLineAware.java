/*
 * Copyright (c) 2017 Baidu.com, Inc. All Rights Reserved
 */

package com.baidu.brandps.biz.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记某个类或某个方法，是需要感知产品线的
 * <p/>
 * 被标记后，进入方法之前会自动判断当前产品线，并放入{@code threadLocal}中
 * 运行时一个{@link BusinessProxy}会根据{@code threadLocal#productLine}来找到对应的实现类
 *
 * @author wangjiayin
 * @since 2017/11/18
 * @see BusinessProxyAspect
 * @see BusinessProxy#currentProductLine
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductLineAware {
}
