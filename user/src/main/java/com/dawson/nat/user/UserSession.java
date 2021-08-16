package com.dawson.nat.user;

import com.dawson.nat.baselib.GLog;
import com.dawson.nat.baselib.NatSession;
import com.dawson.nat.baselib.bean.CmdConfig;
import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.net.BaseCmdWrap;
import com.dawson.nat.baselib.net.TransportClient;
import com.google.gson.JsonObject;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

/**
 * 该会话中，client2 指用服务器的信息和连接  client1指本机命令的信息和连接 如shell
 *
 * @author dawson
 */
public class UserSession extends NatSession {
    private String terminalId;

    @Override
    public synchronized void start() {
        client2 = new TransportClient();
        client2.init(getClientWrap2().getInfo().getIp(), getClientWrap2().getInfo().getPort());
        client2.registerConnect(aBoolean -> {
            if (aBoolean) {
                GLog.println("user session client2 conn");
                //发送连接信息
                JsonObject res = new JsonObject();
                res.addProperty("terminalId", terminalId);
                res.addProperty("sessionId", getId());
                res.addProperty("type", CommonBean.ClientType.CLIENT_USER);

                BaseCmdWrap baseCmdWrap = new BaseCmdWrap(CommonBean.ControlTypeConst.TYPE_NEW_SESSION, res);
                client2.sendData(baseCmdWrap);

                super.start();
            }
            return true;
        });
        client2.start();
    }

    public void bindClient1(TransportClient client, String tid) {
        terminalId = tid;
        client1 = client;
    }
}
