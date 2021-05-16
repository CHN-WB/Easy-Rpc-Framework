package com.wb.rpc.exception;

import com.wb.rpc.enumeration.RpcError;

/**
 * @ClassName: RpcException
 * @Author: wangb
 * @Date: 2021/5/12 16:59
 *
 * RPC 调用异常
 */
public class RpcException extends RuntimeException{

    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }

}