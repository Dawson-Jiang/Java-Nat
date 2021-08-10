package com.dawson.nat.baselib.net;

import java.util.List;
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

    private List<Function<BaseCmdWrap, Object>> dataCallbacks;

    /**
     * 客户端收到数据回调
     */
    public void addOnDataReceived(Function<BaseCmdWrap, Object> callback) {
        this.dataCallbacks.add(callback);
    }

    /**
     * 客户端收到数据回调
     */
    public void removeOnDataReceived(Function<BaseCmdWrap, Object> callback) {
        this.dataCallbacks.remove(callback);
    }

    @Override
    protected void handleData() {
        BaseCmdWrap cmd = new BaseCmdWrap();
        cmd.decode(buffer);
        if (dataCallbacks != null && !dataCallbacks.isEmpty()) {
            for (Function<BaseCmdWrap, Object> dataCallback : dataCallbacks) {
                dataCallback.apply(cmd);
            }
        }
    }
}
