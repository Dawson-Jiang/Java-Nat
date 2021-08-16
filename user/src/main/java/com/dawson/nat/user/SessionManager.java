package com.dawson.nat.user;

import com.dawson.nat.baselib.ClientWrap;
import com.dawson.nat.baselib.GLog;
import com.dawson.nat.baselib.NatSession;
import com.dawson.nat.baselib.NatSessionManage;
import com.dawson.nat.baselib.bean.CmdConfig;
import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.baselib.net.ControlClient;
import com.dawson.nat.baselib.net.TransportClient;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * 管理会话
 *
 * @author dawson
 */
public class SessionManager extends NatSessionManage {
    /**
     * 创建会话
     *
     * @param client
     * @param config
     */
    public void createSession(ControlClient client, SocketChannel thirdSsocket, String tid, CmdConfig config) {
        UserSession session = new UserSession();
        session.setId(UUID.randomUUID().toString());
        session.setConfig(config);
        TerminalAndClientInfo clientInfo = new TerminalAndClientInfo();
        clientInfo.setIp(ControlCore.SERVER_IP);
        clientInfo.setPort(ControlCore.SERVER_PORT);
        ClientWrap wrap = new ClientWrap();
        wrap.setClient(client);
        wrap.setInfo(clientInfo);
        session.setClientWrap2(wrap);

        TransportClient transportClient = new TransportClient();
        transportClient.bindSocket(thirdSsocket);
        session.bindClient1(transportClient, tid);

        session.registerOnClosed(new Function<NatSession, Object>() {
            @Override
            public Object apply(NatSession s) {
                sessions.remove(s);
                return true;
            }
        });

        session.start();
        cleanSession();
        sessions.add(session);
    }
}
