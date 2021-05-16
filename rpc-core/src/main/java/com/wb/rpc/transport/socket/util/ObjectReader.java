package com.wb.rpc.transport.socket.util;

import com.sun.org.apache.xml.internal.security.keys.content.MgmtData;
import com.wb.rpc.entity.RpcRequest;
import com.wb.rpc.entity.RpcResponse;
import com.wb.rpc.enumeration.PackageType;
import com.wb.rpc.enumeration.RpcError;
import com.wb.rpc.exception.RpcException;
import com.wb.rpc.registry.ServiceRegistry;
import com.wb.rpc.serializer.CommonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Socket 方式从输入流中读取字节并反序列化
 *
 * @ClassName: ObjectReader
 * @Author: wangb
 * @Date: 2021/5/13 17:32
 */
public class ObjectReader {

    private static final Logger logger = LoggerFactory.getLogger(ObjectReader.class);
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    public static Object readObject(InputStream in) throws IOException {
        byte[] numberBytes = new byte[4];
        // 读入前4个字节，获得魔数
        in.read(numberBytes);
        int magic = bytesToInt(numberBytes);
        if (magic != MAGIC_NUMBER) {
            logger.error("不识别的协议包: {}", magic);
            throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
        }
        // 再次读入4个字节，获得 PackageType
        in.read(numberBytes);
        int packageCode = bytesToInt(numberBytes);
        Class<?> packageClass;
        if (packageCode == PackageType.REQUEST_PACK.getCode()) {
            packageClass = RpcRequest.class;
        } else if (packageCode == PackageType.RESPONSE_PACK.getCode()) {
            packageClass = RpcResponse.class;
        } else {
            logger.error("不识别的数据包: {}", packageCode);
            throw new RpcException(RpcError.UNKNOWN_PACKAGE_TYPE);
        }
        // 再次读入4个字节，获得 序列化器编号
        in.read(numberBytes);
        int serializerCode = bytesToInt(numberBytes);
        CommonSerializer serializer = CommonSerializer.getByCode(serializerCode);
        if (serializer == null) {
            logger.error("不识别的反序列化器: {}", serializerCode);
            throw new RpcException(RpcError.UNKNOWN_SERIALIZER);
        }
        // 再次读入4个字节，获得 数据长度
        in.read(numberBytes);
        int length = bytesToInt(numberBytes);
        // 接收数据的字节数组
        byte[] bytes = new byte[length];
        in.read(bytes);
        return serializer.deserialize(bytes, packageClass);
    }

    public static int bytesToInt(byte[] src) {
        int value;
        value = ((src[0] & 0xFF) << 24)
                | ((src[1] & 0xFF) << 16)
                | ((src[2] & 0xFF) << 8)
                | (src[3] & 0xFF);
        return value;
    }

}