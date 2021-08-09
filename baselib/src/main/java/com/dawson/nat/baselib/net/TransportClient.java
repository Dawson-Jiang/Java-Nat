package com.dawson.nat.baselib.net;

import java.util.function.Function;

/**
 * 负责透传数据
 */
public class TransportClient extends AbstractClient {

    private Function<byte[], Object> dataCallback;

    /**
     * 客户端收到数据回调
     */
    public void registerOnDataReceived(Function<byte[], Object> callback) {
        this.dataCallback = callback;
    }

    @Override
    protected void handleData() {
        byte[] t = new byte[buffer.remaining()];
        buffer.get(t);
        if (dataCallback != null) {
            dataCallback.apply(t);
        }
    }
}
