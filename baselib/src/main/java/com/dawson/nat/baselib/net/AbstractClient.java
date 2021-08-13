package com.dawson.nat.baselib.net;

import com.dawson.nat.baselib.ExecutorUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

/**
 * 包装客户端socket,负责二进制接收与发送
 *
 * @author dawson
 */
public abstract class AbstractClient {
    private SocketChannel socket;
    /**
     * 服务端ip
     */
    private String ip;
    /**
     * 服务端端口
     */
    private int port;
    protected boolean isWork;
    protected boolean isStart;
    /**
     * 是否自动重连
     */
    private boolean isAutoReconnect;
    protected ExecutorService executor = ExecutorUtil.getCommonExecutor();

    public void init(Object... params) {
        if (params != null && params.length > 1) {
            this.ip = (String) params[0];
            this.port = (short) params[1];
        }
    }

    public void start() {
        if (isWork) {
            return;
        }
        isWork = true;
        conn();
    }

    boolean isDoingConn = false;

    private synchronized void conn() {
        if (!isWork) {
            return;
        }
        if (isDoingConn) {
            return;
        }
        isDoingConn = true;
        executor.execute(() -> {
            isStart = true;
            do {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    socket = SocketChannel.open();
                    socket.connect(new InetSocketAddress(ip, port));
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (isWork && isAutoReconnect);
            if (isWork) {
                //连接成功
                startReceiveData();
                if (connectCallback != null) {
                    connectCallback.apply(true);
                }
            }
            isDoingConn = false;
        });
    }

    private Function<Boolean, Object> connectCallback;

    /**
     * 客户端连接状态回调
     */
    public void registerConnect(Function<Boolean, Object> callback) {
        connectCallback = callback;
    }


    public void bindSocket(SocketChannel socket) {
        this.socket = socket;
        isWork = true;
        startReceiveData();
    }

    private static final short buffer_size = 4096;
    protected ByteBuffer buffer = ByteBuffer.allocate(buffer_size);

    public void startReceiveData() {
        startReceiveDataMethod();
    }

    public void startReceiveDataMethod() {
        executor.execute(() -> {
            try {
                do {
                    long len = socket.read(buffer);
                    System.out.println("client read len:" + len);
                    if (len > 0) {
                        buffer.flip();
                        handleData();
                        buffer.compact();
                    } else if (len == 0) {
                        Thread.sleep(500);
                    } else {
                        break;
                    }
                } while (isWork);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //重新连接
                if (isAutoReconnect && isWork) {
                    if (connectCallback != null) {
                        connectCallback.apply(false);
                        isStart = false;
                    }
                    conn();
                } else {
                    close();
                }
            }
        });
    }


    protected void handleData() {
        buffer.clear();
    }

    /**
     * 关闭客户端
     */
    public void close() {
        isWork = false;
        try {
            if (socket != null) {
                socket.close();
                System.out.println("socket.close()");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            onClose();
        }
    }

    public boolean sendData(BaseCmdWrap basedata) {
        return sendData(basedata.encode());
    }

    public boolean sendData(byte[] data) {
        return sendData(ByteBuffer.wrap(data));
    }

    private boolean sendData(ByteBuffer data) {
        boolean canSend = isConnect();

        if (canSend) {
            System.out.println("sendData len:" + data.remaining());
            try {
                int res = socket.write(data);
                return res > 0;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    private void onClose() {
        if (connectCallback != null) {
            connectCallback.apply(false);
        }
        connectCallback = null;
    }

    public boolean isConnect() {
        return socket != null && socket.isConnected();
    }

    public boolean isAutoReconnect() {
        return isAutoReconnect;
    }

    public void setAutoReconnect(boolean autoReconnect) {
        isAutoReconnect = autoReconnect;
    }
}

