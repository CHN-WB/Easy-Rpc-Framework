package com.wb.rpc.transport.netty.client;

import com.wb.rpc.codec.CommonDecoder;
import com.wb.rpc.codec.CommonEncoder;
import com.wb.rpc.entity.RpcRequest;
import com.wb.rpc.entity.RpcResponse;
import com.wb.rpc.enumeration.RpcError;
import com.wb.rpc.exception.RpcException;
import com.wb.rpc.factory.SingletonFactory;
import com.wb.rpc.loadbalance.LoadBalancer;
import com.wb.rpc.loadbalance.RandomLoadBalancer;
import com.wb.rpc.registry.NacosServiceDiscovery;
import com.wb.rpc.registry.ServiceDiscovery;
import com.wb.rpc.serializer.CommonSerializer;
import com.wb.rpc.serializer.JsonSerializer;
import com.wb.rpc.transport.RpcClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.font.TextHitInfo;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName: NettyClient
 * @Author: wangb
 * @Date: 2021/5/12 18:11
 */
public class NettyClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private static final EventLoopGroup group;
    private static final Bootstrap bootStrap;

    static {
        group = new NioEventLoopGroup();
        bootStrap = new Bootstrap();
        bootStrap.group(group)
                .channel(NioSocketChannel.class);
    }

    private final ServiceDiscovery serviceDiscovery;
    private static CommonSerializer serializer;


    private final UnprocessedRequests unprocessedRequests;

    public NettyClient() {
        this(DEFAULT_SERIALIZER, new RandomLoadBalancer());
    }
    public NettyClient(LoadBalancer loadBalancer) {
        this(DEFAULT_SERIALIZER, loadBalancer);
    }
    public NettyClient(Integer serializer) {
        this(serializer, new RandomLoadBalancer());
    }
    public NettyClient(Integer serializer, LoadBalancer loadBalancer) {
        this.serviceDiscovery = new NacosServiceDiscovery(loadBalancer);
        this.serializer = CommonSerializer.getByCode(serializer);
        this.unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
    }

    @Override
    public CompletableFuture<RpcResponse> sendRequest(RpcRequest rpcRequest) {
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        CompletableFuture<RpcResponse> resultFuture = new CompletableFuture<>();
        try {
            InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
            Channel channel = ChannelProvider.get(inetSocketAddress, serializer);
            if (!channel.isActive()) {
                group.shutdownGracefully();
                return null;
            }
            unprocessedRequests.put(rpcRequest.getRequestId(), resultFuture);
            channel.writeAndFlush(rpcRequest).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    logger.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                } else {
                    future.channel().close();
                    resultFuture.completeExceptionally(future.cause());
                    logger.error("发送消息时有错误发生: ", future.cause());
                }
            });
        } catch (InterruptedException e) {
            unprocessedRequests.remove(rpcRequest.getRequestId());
            logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        return resultFuture;
    }
}