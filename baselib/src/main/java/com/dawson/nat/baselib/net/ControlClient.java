package com.dawson.nat.baselib.net;

import java.util.function.Function;

/**
 * 负责收发控制命令
 *
 * @author dawson
 */
public class ControlClient extends AbstractClient {
    public ControlClient() {
        setAutoReconnect(true);
    }

    private Function<BaseCmdWrap, Object> dataCallback;

    /**
     * 客户端收到数据回调
     */
    public void registerOnDataReceived(Function<BaseCmdWrap, Object> callback) {
        this.dataCallback = callback;
    }

    @Override
    protected void handleData() {
        BaseCmdWrap cmd = new BaseCmdWrap();
        cmd.decode(buffer);
        if (dataCallback != null) {
            dataCallback.apply(cmd);
        }
    }
}
