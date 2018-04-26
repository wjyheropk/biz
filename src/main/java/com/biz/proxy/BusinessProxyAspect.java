/*
 * Copyright (c) 2017 Baidu.com, Inc. All Rights Reserved
 */

package com.biz.proxy;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biz.sample.bean.Channel;
import com.biz.sample.bean.ProductLine;

/**
 * 产品线切面
 * <p/>
 * 目前主要用于切{@code controller}或者{@code task}，
 * 以便确定接下来的业务逻辑应该使用哪条产品线业务分支
 *
 * @author wangjiayin
 * @since 2017/11/18
 */
@Aspect
@Component
public class BusinessProxyAspect {

    @Autowired
    private BusinessProxy proxy;

    /**
     * 在方法入参中，按优先级依次寻找：
     * {@link ProductLine}类型的参数放入ThreadLocal中
     * 参数名叫{@code resourceId}的参数，并解析出产品线
     * 参数名叫{@code adId}的参数，并解析出产品线
     */
    @Before("@within(com.biz.proxy.ProductLineAware)")
    public void before1(JoinPoint jp) {

        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Class[] classes = methodSignature.getParameterTypes();

        for (int i = 0; i < classes.length; i++) {
            if (classes[i] == ProductLine.class) {
                proxy.setCurrentProductLine((ProductLine) jp.getArgs()[i]);
            } else if ("productLine".equals(methodSignature.getParameterNames()[i])) {
                Object productLine = jp.getArgs()[i];
                if (productLine != null && !"ALL".equals(productLine.toString())) {
                    proxy.setCurrentProductLine(ProductLine.valueOf(productLine.toString()));
                }
            } else if ("resourceId".equals(methodSignature.getParameterNames()[i])) {
                proxy.setCurrentProductLine(ProductLine.SL);
            }

            if (classes[i] == Channel.class) {
                proxy.setCurrentChannel((Channel) jp.getArgs()[i]);
            } else if ("channel".equals(methodSignature.getParameterNames()[i])) {
                Object channel = jp.getArgs()[i];
                if (channel != null && !"ALL".equals(channel.toString())) {
                    proxy.setCurrentChannel(Channel.valueOf(channel.toString()));
                }
            }
        }

    }

    @Before("@annotation(com.biz.proxy.ProductLineAware)")
    public void before2(JoinPoint jp) {
        before1(jp);
    }

    @Before("@within(com.biz.proxy.ChannelAware)")
    public void before3(JoinPoint jp) {
        before1(jp);
    }

    @Before("@annotation(com.biz.proxy.ChannelAware)")
    public void before4(JoinPoint jp) {
        before1(jp);
    }

}
