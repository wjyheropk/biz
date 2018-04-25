/*
 * Copyright (c) 2017 Baidu.com, Inc. All Rights Reserved
 */

package com.baidu.brandps.biz;

/**
 * 业务组件
 * <p/>
 * 用于支持多维度可扩展的代码架构
 *
 * @author wangjiayin
 * @since 2017/11/16
 */
public interface Business<C extends BusinessContext, R extends BusinessRequest> {

    /**
     * 构建业务组件执行上下文
     */
    C buildContext(R businessRequest);

}
