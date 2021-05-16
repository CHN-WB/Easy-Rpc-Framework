package com.wb.rpc.transport.socket.util;

import com.wb.rpc.entity.RpcRequest;
import com.wb.rpc.enumeration.PackageType;
import com.wb.rpc.serializer.CommonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Socket 方式从输入流中读取字节并反序列化
 *
 * @ClassName: ObjectWriter
 * @Author: wangb
 * @Date: 2021/5/13 17:46
 */
public class ObjectWriter {

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    public static void writeObject(OutputStream outputStream, Object object, CommonSerializer serializer) throws IOException {
        // 先写入魔数
        outputStream.write(intToBytes(MAGIC_NUMBER));
        // 判断类型并写入
        if (object instanceof RpcRequest) {
            outputStream.write(intToBytes(PackageType.REQUEST_PACK.getCode()));
        } else {
            outputStream.write(intToBytes(PackageType.RESPONSE_PACK.getCode()));
        }
        // 写入序列化器编号
        outputStream.write(intToBytes(serializer.getCode()));
        // 将对象序列化
        byte[] bytes = serializer.serialize(object);
        // 写入数据长度
        outputStream.write(intToBytes(bytes.length));
        outputStream.write(bytes);
        outputStream.flush();
    }

    private static byte[] intToBytes(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value>>24) & 0xFF);
        src[1] = (byte) ((value>>16) & 0xFF);
        src[2] = (byte) ((value>>8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }
}