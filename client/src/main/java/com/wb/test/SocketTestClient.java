package com.wb.test;

import com.wb.rpc.api.HelloObject;
import com.wb.rpc.api.HelloService;
import com.wb.rpc.serializer.CommonSerializer;
import com.wb.rpc.transport.RpcClientProxy;
import com.wb.rpc.transport.socket.client.SocketClient;

/**
 * 测试用消费者（Socket方式）
 *
 * @ClassName: SocketTestClient
 * @Author: wangb
 * @Date: 2021/5/12 16:43
 */
public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient(CommonSerializer.KRYO_SERIALIZER);
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "Socket client test");
        String res = helloService.hello(object);
        System.out.println(res);
    }

}