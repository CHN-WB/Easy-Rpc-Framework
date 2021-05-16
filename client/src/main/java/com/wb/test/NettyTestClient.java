package com.wb.test;

import com.wb.rpc.api.ByeObject;
import com.wb.rpc.api.ByeService;
import com.wb.rpc.api.HelloObject;
import com.wb.rpc.api.HelloService;
import com.wb.rpc.loadbalance.RoundRobinLoadBalancer;
import com.wb.rpc.serializer.CommonSerializer;
import com.wb.rpc.transport.RpcClientProxy;
import com.wb.rpc.transport.netty.client.NettyClient;


/**
 * @ClassName: NettyTestClient
 * @Author: wangb
 * @Date: 2021/5/12 20:06
 */
public class NettyTestClient {

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient(CommonSerializer.Protobuf_SERIALIZER, new RoundRobinLoadBalancer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(nettyClient);
        // 测试 hello 服务
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "Netty client test");
        String res = helloService.hello(object);
        System.out.println(res);
        // 测试 bye 服务
        // ByeService byeService = rpcClientProxy.getProxy(ByeService.class);
        // ByeObject byeObject = new ByeObject(1, "Audi RS 7");
        // String byeRes = byeService.bye(byeObject);
        // System.out.println(byeRes);
    }

}