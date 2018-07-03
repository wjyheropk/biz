package com.biz;

/**
 * 同步业务组件
 *
 * @author wangjiayin
 * @see AsyncBusiness
 * @since 2017/12/9
 */
public interface SyncBusiness<C extends BusinessContext, R extends BusinessRequest, R1> extends Business<C, R> {

    /**
     * 执行业务逻辑
     *
     * @return 同步执行结果，如果没有，用{@link Void}代替
     */
    R1 doBusiness(C businessContext);

}
