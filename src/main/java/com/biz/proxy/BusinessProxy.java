/*
 * Copyright (c) 2017 Baidu.com, Inc. All Rights Reserved
 */

package com.biz.proxy;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.Business;
import com.biz.sample.bean.Channel;
import com.biz.sample.bean.ProductLine;
import com.google.common.collect.Lists;

/**
 * 业务组件路由器
 * <p/>
 * 根据{@code threadLocal}中的产品线信息找到具体{@code business}实例
 *
 * @author wangjiayin
 * @see BusinessProxyAspect
 * @see ProductLineAware
 * @since 2017/11/17
 */
@Service
public class BusinessProxy {

    /**
     * 用于保存当前线程的产品线信息
     */
    private ThreadLocal<ProductLine> currentProductLine = new ThreadLocal<>();

    /**
     * 用于保存当前线程的频道信息
     */
    private ThreadLocal<Channel> currentChannel = new ThreadLocal<>();

    @Autowired
    private List<Business> businesses;

    /**
     * 按产品线分类的业务组件
     */
    private Map<ProductLine, List<Business>> productLineBusinessMap;

    /**
     * 按频道分类的业务组件
     */
    private Map<Channel, List<Business>> channelBusinessMap;

    /**
     * 初始化业务组件池
     */
    private synchronized void initMap() {
        if (productLineBusinessMap != null && channelBusinessMap != null) {
            return;
        }
        productLineBusinessMap = new HashMap<>();
        channelBusinessMap = new HashMap<>();
        businesses.forEach(b -> {
            if (b instanceof ProductLined) {
                ProductLine p = ((ProductLined) b).getProductLine();
                productLineBusinessMap.computeIfAbsent(p, k -> Lists.newArrayList());
                productLineBusinessMap.get(p).add(b);
            }
            if (b instanceof Channeled) {
                Channel c = ((Channeled) b).getChannel();
                channelBusinessMap.computeIfAbsent(c, k -> Lists.newArrayList());
                channelBusinessMap.get(c).add(b);
            }
        });
    }

    /**
     * 根据业务组件类型获取业务组件实例
     *
     * @param clazz 业务组件类型
     *
     * @return 对应类型、对应产品线的业务组件实现类
     */
    public <B> B of(Class<B> clazz) {

        initMap();

        ProductLine p = currentProductLine.get();

        if (p == null) {
            throw new RuntimeException();
        }

        for (Business b : productLineBusinessMap.get(p)) {
            if (clazz.isAssignableFrom(b.getClass())) {
                return (B) b;
            }
        }

        return null;
    }

    /**
     * 根据产品线、业务组件类型获取业务组件实例
     *
     * @param businessClass     业务组件类型
     * @param businessClassType 业务组件维度的类型
     *
     * @return 对应类型、对应产品线的业务组件实现类
     */
    public <B> B of(Class<B> businessClass, Type businessClassType) {

        initMap();

        if (businessClassType == ProductLined.class) {
            return of(businessClass);
        }

        Channel c = currentChannel.get();

        for (Business b : channelBusinessMap.get(c)) {
            if (businessClass.isAssignableFrom(b.getClass())) {
                return (B) b;
            }
        }

        return null;
    }

    public BusinessProxy setCurrentProductLine(ProductLine p) {
        currentProductLine.set(p);
        return this;
    }

    public BusinessProxy setCurrentChannel(Channel c) {
        currentChannel.set(c);
        return this;
    }
}
