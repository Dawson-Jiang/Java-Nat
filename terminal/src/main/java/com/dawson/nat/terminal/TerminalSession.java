package com.dawson.nat.terminal;

import com.dawson.nat.baselib.NatSession;
import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.net.TransportClient;

import java.util.function.Function;

/**
 * 会话
 *
 * @author dawson
 */
public class TerminalSession extends NatSession {
    public void start() {
        client2 = new TransportClient();
        client2.init(getClient1Info().getIp(), getClient1Info().getPort());
        client2.registerConnect(new Function<Boolean, Object>() {
            @Override
            public Object apply(Boolean aBoolean) {
                if (!aBoolean) {
                    closeSession();
                }
                return true;
            }
        });
        client1 = new TransportClient();
        client1.init(getClient1Info().getIp(), getClient1Info().getPort());
        client1.registerConnect(new Function<Boolean, Object>() {
            @Override
            public Object apply(Boolean aBoolean) {
                if (aBoolean) {
                    closeSession();
                }
                return true;
            }
        });
    }

    private void closeSession() {
        setState(CommonBean.SessionStateConst.STATE_CLOSE);
        client1.close();
        client2.close();
        if (closeCallback != null) {
            closeCallback.apply(this);
        }
    }
}
