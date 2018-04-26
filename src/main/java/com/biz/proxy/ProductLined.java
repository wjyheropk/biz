package com.biz.proxy;

import com.biz.sample.bean.ProductLine;

/**
 * 标记一段业务逻辑/业务组件是哪个产品线的业务实现
 * 用于支持多产品线{@link ProductLine}可扩展的代码架构
 *
 * @author wangjiayin
 * @since 2018/4/24
 */
public interface ProductLined {

    ProductLine getProductLine();

}