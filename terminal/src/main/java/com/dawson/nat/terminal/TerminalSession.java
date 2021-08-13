package com.dawson.nat.terminal;

import com.dawson.nat.baselib.GLog;
import com.dawson.nat.baselib.NatSession;
import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.net.BaseCmdWrap;
import com.dawson.nat.baselib.net.TransportClient;
import com.google.gson.JsonObject;

import java.util.function.Function;

/**
 * 该会话中，client1 指服务器的信息和连接  client2指本机命令服务的信息和连接 如shell
 *
 * @author dawson
 */
public class TerminalSession extends NatSession {
    @Override
    public synchronized void start() {
        final TransportClient c1 = new TransportClient();
        c1.init(getClientWrap1().getInfo().getIp(), getClientWrap1().getInfo().getPort());
        c1.registerConnect(aBoolean -> {
            if (aBoolean) {//发送连接信息
                GLog.println("term. session client1 conn");
                JsonObject res = new JsonObject();
                res.addProperty("sessionId", getId());
                res.addProperty("type", CommonBean.ClientType.CLIENT_TERMINAL);

                BaseCmdWrap baseCmdWrap = new BaseCmdWrap(CommonBean.ControlTypeConst.TYPE_NEW_CMD_CONN, res);
                c1.sendData(baseCmdWrap);

                client1 = c1;
                TerminalSession.super.start();
            }
            return true;
        });
        c1.start();

        final TransportClient c2 = new TransportClient();
        c2.init(getClientWrap2().getInfo().getIp(), getClientWrap2().getInfo().getPort());
        c2.registerConnect(new Function<Boolean, Object>() {
            @Override
            public Object apply(Boolean aBoolean) {
                if (aBoolean) {
                    GLog.println("term. session client2 conn");
                    client2 = c2;
                    TerminalSession.super.start();
                }
                return true;
            }
        });
        c2.start();
    }
}
