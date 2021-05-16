package com.wb.rpc.transport;

import com.wb.rpc.serializer.CommonSerializer;

/**
 * @ClassName: SocketServer
 * @Author: wangb
 * @Date: 2021/5/12 17:52
 *
 * 服务器类通用接口
 */
public interface RpcServer {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    void start();

    <T> void publishServer(T service, String serviceName);

}