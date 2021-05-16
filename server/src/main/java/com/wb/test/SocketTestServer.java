package com.wb.test;

import com.wb.rpc.annotation.ServiceScan;
import com.wb.rpc.serializer.CommonSerializer;
import com.wb.rpc.transport.RpcServer;
import com.wb.rpc.transport.socket.server.SocketServer;

/**
 * 测试用的服务提供方（Socket方式）
 * @ClassName: SocketTestServer
 * @Author: wangb
 * @Date: 2021/5/12 16:40
 */
@ServiceScan
public class SocketTestServer {

    public static void main(String[] args) {
        RpcServer socketServer = new SocketServer("127.0.0.1", 9998, CommonSerializer.KRYO_SERIALIZER);
        socketServer.start();
    }

}