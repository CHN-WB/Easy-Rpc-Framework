package com.wb.rpc.provider;

import com.wb.rpc.enumeration.RpcError;
import com.wb.rpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: ServiceProviderImpl
 * @Author: wangb
 * @Date: 2021/5/12 16:55
 *
 * 默认的服务注册表，保存服务端本地服务
 */
public class ServiceProviderImpl implements ServiceProvider {

    private static final Logger logger = LoggerFactory.getLogger(ServiceProviderImpl.class);

    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    private static final Set<String> registredService = ConcurrentHashMap.newKeySet();

    @Override
    public synchronized <T> void addServiceProvider(T service) {
        String serviceName = service.getClass().getCanonicalName();
        if(registredService.contains(serviceName)) return;
        registredService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if(interfaces.length == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for(Class<?> i : interfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }
        logger.info("向接口: {} 注册服务: {}", interfaces, serviceName);
    }

    @Override
    public synchronized Object getServiceProvider(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if(service == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}