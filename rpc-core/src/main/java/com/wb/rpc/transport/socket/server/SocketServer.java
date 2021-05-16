package com.wb.rpc.transport.socket.server;

import com.wb.rpc.factory.ThreadPoolFactory;
import com.wb.rpc.handler.RequestHandler;
import com.wb.rpc.hook.ShutdownHook;
import com.wb.rpc.provider.ServiceProviderImpl;
import com.wb.rpc.registry.NacosServiceRegistry;
import com.wb.rpc.serializer.CommonSerializer;
import com.wb.rpc.transport.AbstractRpcServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @ClassName: SocketServer
 * @Author: wangb
 * @Date: 2021/5/12 16:15
 *
 * 监听端口，接收请求并处理，返回处理结果
 */
public class SocketServer extends AbstractRpcServer {


    private final ExecutorService threadPool;
    private final CommonSerializer serializer;
    private final RequestHandler requestHandler = new RequestHandler();

    public SocketServer(String host, int port) {
        this(host, port, DEFAULT_SERIALIZER);
    }

    public SocketServer(String host, int port, Integer serializer) {
        this.host = host;
        this.port = port;
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        this.serviceRegistry = new NacosServiceRegistry();
        this.serviceProvider = new ServiceProviderImpl();
        this.serializer = CommonSerializer.getByCode(serializer);
        scanServices();
    }

    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(host, port));
            logger.info("服务器启动...");
            // 服务器关闭时自动注销服务
            ShutdownHook.getShutdownHook().addClearAllHook();
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new SocketRequestHandlerThread(socket, requestHandler, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生: ", e);
        }
    }

}