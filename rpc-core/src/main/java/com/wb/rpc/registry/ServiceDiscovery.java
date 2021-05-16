package com.wb.rpc.registry;

import java.net.InetSocketAddress;

/**
 * @ClassName: ServiceDiscovery
 * @Author: wangb
 * @Date: 2021/5/12 20:12
 *
 * 服务发现接口
 */
public interface ServiceDiscovery {

    /**
     * 根据服务名称查找服务实体
     *
     * @param serviceName 服务名称
     * @return 服务实体
     */
    InetSocketAddress lookupService(String serviceName);

}