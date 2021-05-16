package com.wb.rpc.transport;

import com.wb.rpc.entity.RpcRequest;
import com.wb.rpc.serializer.CommonSerializer;

/**
 * @ClassName: SocketClient
 * @Author: wangb
 * @Date: 2021/5/12 17:53
 *
 * 客户端通用接口
 */
public interface RpcClient {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    Object sendRequest(RpcRequest rpcRequest);

}