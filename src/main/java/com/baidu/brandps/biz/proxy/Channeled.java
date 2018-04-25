package com.baidu.brandps.biz.proxy;

import com.baidu.brandps.biz.sample.bean.Channel;

/**
 * 标记一段业务逻辑/业务组件是哪个频道的业务逻辑实现
 * 用于支持多产品线{@link Channel}可扩展的代码架构
 *
 * @author wangjiayin
 * @since 2018/4/24
 */
public interface Channeled {

    Channel getChannel();

}