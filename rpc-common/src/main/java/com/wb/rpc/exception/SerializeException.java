package com.wb.rpc.exception;

/**
 * @ClassName: SerializeException
 * @Author: wangb
 * @Date: 2021/5/12 21:35
 *
 * 序列化异常
 */
public class SerializeException extends RuntimeException{

    public SerializeException(String msg) {
        super(msg);
    }

}