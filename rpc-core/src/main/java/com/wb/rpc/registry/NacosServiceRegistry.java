package com.wb.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.wb.rpc.enumeration.RpcError;
import com.wb.rpc.exception.RpcException;
import com.wb.rpc.loadbalance.LoadBalancer;
import com.wb.rpc.loadbalance.RandomLoadBalancer;
import com.wb.rpc.util.NacosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @ClassName: NacosServiceRegistry
 * @Author: wangb
 * @Date: 2021/5/12 22:01
 */
public class NacosServiceRegistry implements ServiceRegistry{

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            NacosUtil.registerService(serviceName, inetSocketAddress);
        } catch (NacosException e) {
            logger.error("注册服务时有错误发生: ", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

}