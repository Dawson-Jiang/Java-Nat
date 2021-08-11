package com.dawson.nat.terminal;

import com.dawson.nat.baselib.NatSession;
import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.net.BaseCmdWrap;
import com.dawson.nat.baselib.net.TransportClient;
import com.google.gson.JsonObject;

import java.util.function.Function;

/**
 * 该会话中，client1 指用服务器的信息和连接  client2指本机命令服务的信息和连接 如shell
 *
 * @author dawson
 */
public class TerminalSession extends NatSession {
    @Override
    public void start() {
        client2 = new TransportClient();
        client2.init(getClientWrap2().getInfo().getIp(), getClientWrap2().getInfo().getPort());

        client1 = new TransportClient();
        client1.init(getClientWrap1().getInfo().getIp(), getClientWrap1().getInfo().getPort());
        client1.registerConnect(new Function<Boolean, Object>() {
            @Override
            public Object apply(Boolean aBoolean) {
//发送连接信息
                JsonObject res = new JsonObject();
                res.addProperty("sessionId", getId());

                BaseCmdWrap baseCmdWrap = new BaseCmdWrap(CommonBean.ControlTypeConst.TYPE_NEW_CMD_CONN, res);
                client1.sendData(baseCmdWrap);
                return true;
            }
        });
        super.start();
    }
}
