package com.wb.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName: RpcError
 * @Author: wangb
 * @Date: 2021/5/12 17:00
 */
@AllArgsConstructor
@Getter
public enum RpcError {

    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务未实现接口"),
    SERVICE_NOT_FOUND("找不到相应服务"),
    UNKNOWN_PROTOCOL("无法识别的协议包"),
    UNKNOWN_PACKAGE_TYPE("无法识别的数据包类型"),
    UNKNOWN_SERIALIZER("无法识别的(反)序列化器"),
    FAILED_TO_CONNECT_TO_SERVICE_REGISTRY("连接注册中心失败"),
    REGISTER_SERVICE_FAILED("注册服务失败"),
    SERIALIZER_NOT_FOUND("未设置序列化器"),
    SERVICE_SCAN_PACKAGE_NOT_FOUND("启动类缺少 @ServiceScan 注解"),
    UNKNOWN_ERROR("未知错误"),
    SERVICE_INVOCATION_FAILURE("服务调用失败"),
    RESPONSE_NOT_MATCH("响应与请求号不匹配");


    private final String message;

}
