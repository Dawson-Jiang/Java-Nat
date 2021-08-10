package com.dawson.nat.baselib.net;

import com.dawson.nat.baselib.ExecutorUtil;
import com.dawson.nat.baselib.GLog;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

/**
 * 负责监听客户端连接
 *
 * @author dawson
 */
public class SocketServer {
    private int port;
    protected boolean isWork;
    protected boolean isStart;
    protected ExecutorService executor = ExecutorUtil.getCommonExecutor();

    private ServerSocketChannel server;


    public void init(Object... params) {
        if (params != null && params.length > 0) {
            this.port = (int) params[0];
        }
    }

    public void start() {
        if (isWork) {
            return;
        }
        executor.execute(() -> {
            try {
                if (server == null) {
                    server = ServerSocketChannel.open();
                }
                server.bind(new InetSocketAddress(port));
                isStart = true;
                do {
                    GLog.println("start socket server begin accept client");
                    SocketChannel socket = server.accept();
                    GLog.println("new socket client accepted");

                    if (clientConnCallback != null) {
                        clientConnCallback.apply(socket);
                    }
                } while (isWork);
            } catch (Exception ex) {
                ex.printStackTrace();
                //重启服务
                isStart = false;
                start();
            }
        });
    }

    private Function<SocketChannel, Boolean> clientConnCallback;

    /**
     * 注册客户端连接回调
     *
     * @return
     */
    public void registerOnClientConnect(Function<SocketChannel, Boolean> callback) {
        clientConnCallback = callback;
    }

    private Function<Boolean, Object> stateCallback;

    /**
     * 服务器状态回调
     */
    public void registerServerState(Function<Boolean, Object> callback) {
        stateCallback = callback;
    }

    public void stop() {
        if (stateCallback != null) {
            stateCallback.apply(false);
        }
        if (server != null) {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                server = null;
            }
        }
        isStart = false;
    }
}
