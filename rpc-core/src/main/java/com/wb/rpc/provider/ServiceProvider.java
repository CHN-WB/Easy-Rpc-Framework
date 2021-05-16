package com.wb.rpc.provider;

/**
 * @ClassName: ServiceProvider
 * @Author: wangb
 * @Date: 2021/5/12 16:53
 *
 * 保存和提供服务实例对象
 */
public interface ServiceProvider {

    // 注册服务
    <T> void addServiceProvider(T service);
    // 根据服务名获取服务
    Object getServiceProvider(String serviceName);

}