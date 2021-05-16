package com.wb.rpc.transport.netty.server;

import com.wb.rpc.entity.RpcRequest;
import com.wb.rpc.entity.RpcResponse;
import com.wb.rpc.handler.RequestHandler;
import com.wb.rpc.provider.ServiceProviderImpl;
import com.wb.rpc.provider.ServiceProvider;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: NettyServerHandler
 * @Author: wangb
 * @Date: 2021/5/12 19:54
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private static final RequestHandler requestHandler;


    static {
        requestHandler = new RequestHandler();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        try {
            logger.info("服务器 [{}] 接收到请求: {}", ctx.channel().localAddress(), msg);
            System.out.println("服务器 [" + ctx.channel().localAddress() + "] 处理请求");
            Object result = requestHandler.handle(msg);
            ChannelFuture future = ctx.writeAndFlush(RpcResponse.success(result, msg.getRequestId()));
            future.addListener(ChannelFutureListener.CLOSE);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("处理过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }
}