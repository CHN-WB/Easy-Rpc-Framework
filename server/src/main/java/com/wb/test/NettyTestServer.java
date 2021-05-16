package com.wb.test;

import com.wb.rpc.annotation.ServiceScan;
import com.wb.rpc.api.HelloService;
import com.wb.rpc.enumeration.SerializerCode;
import com.wb.rpc.provider.ServiceProviderImpl;
import com.wb.rpc.provider.ServiceProvider;
import com.wb.rpc.transport.netty.server.NettyServer;

/**
 * @ClassName: NettyTestServer
 * @Author: wangb
 * @Date: 2021/5/12 20:05
 */
@ServiceScan
public class NettyTestServer {

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer("127.0.0.1", 9999, SerializerCode.PROTOBUF.getCode());
        nettyServer.start();
    }

}