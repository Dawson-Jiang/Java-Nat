package com.dawson.nat.server;

import com.dawson.nat.baselib.NatSession;
import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.net.BaseCmdWrap;
import com.dawson.nat.baselib.net.TransportClient;
import com.google.gson.JsonObject;

/**
 * 该会话中，client1 指用户端的信息和连接  client2指终端的信息和连接
 *
 * @author dawson
 */
public class ServerSession extends NatSession {

    @Override
    public synchronized void start() {
        JsonObject req = new JsonObject();
        req.addProperty("sessionId", getId());
        req.addProperty("cmd", getConfig().getCmd());
        BaseCmdWrap baseCmdWrap = new BaseCmdWrap(CommonBean.ControlTypeConst.TYPE_NEW_SESSION, req);
        //给终端发送新连接的命令
        getClientWrap2().getClient().sendData(baseCmdWrap);
    }

    public void bindClient1(TransportClient client) {
        client1 = client;
    }
    public void bindClient2(TransportClient client) {
        client2 = client;
        super.start();
    }
}
