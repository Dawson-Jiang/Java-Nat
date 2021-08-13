package com.dawson.nat.baselib.net;

import java.util.function.Function;

/**
 * 负责透传数据
 *
 * @author dawson
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

    @Override
    public void startReceiveData() {
         //屏蔽自动开始接收数据 在需要的时候，主动调用 #startReceiveDataMethod()
    }
}
