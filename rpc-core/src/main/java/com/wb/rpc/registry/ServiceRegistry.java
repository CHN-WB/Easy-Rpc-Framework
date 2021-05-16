package com.wb.rpc.registry;

import java.net.InetSocketAddress;

/**
 * @ClassName: ServiceRegistry
 * @Author: wangb
 * @Date: 2021/5/12 21:58
 *
 * 服务注册接口
 */
public interface ServiceRegistry {
    /**
     * 将一个服务注册到注册表
     * @param serviceName 服务名称
     * @param inetSocketAddress 提供服务的地址
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);
}