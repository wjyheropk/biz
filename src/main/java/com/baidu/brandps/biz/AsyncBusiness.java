/*
 * Copyright (c) 2017 Baidu.com, Inc. All Rights Reserved
 */

package com.baidu.brandps.biz;

/**
 * 异步业务组件
 *
 * @author wangjiayin
 * @see SyncBusiness
 * @since 2017/11/16
 */
public interface AsyncBusiness<C extends BusinessContext, R1 extends BusinessRequest, R2, R3>
        extends SyncBusiness<C, R1, R2> {

    /**
     * 获取业务执行结果
     *
     * @return 业务处理结果
     */
    R3 tryToGetResult(C businessContext);

    /**
     * 处理业务执行结果，按需实现
     *
     * @param businessResult 业务处理结果
     *
     * @see AsyncBusiness#tryToGetResult(BusinessContext)
     */
    void onFinish(R3 businessResult);

}
