package com.wb.rpc.handler;

import com.wb.rpc.entity.RpcRequest;
import com.wb.rpc.provider.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @ClassName: RpcRequestHandlerThread
 * @Author: wangb
 * @Date: 2021/5/12 17:22
 */
public class RequestHandlerThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceProvider serviceRegistry;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceProvider serviceRegistry) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            // String interfaceName = rpcRequest.getInterfaceName();
            // Object service = serviceRegistry.getService(interfaceName);
            Object result = requestHandler.handle(rpcRequest);
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();

        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用或发送时有错误发生: ", e);
        }
    }
}