package com.dawson.nat.server;

import com.dawson.nat.baselib.ClientWrap;
import com.dawson.nat.baselib.NatSession;
import com.dawson.nat.baselib.NatSessionManage;
import com.dawson.nat.baselib.bean.CmdConfig;
import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.baselib.net.BaseCmdWrap;
import com.dawson.nat.baselib.net.TransportClient;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author dawson
 */
public class SessionManager extends NatSessionManage {
    private SessionManager() {
    }

    private static SessionManager instance;

    public synchronized static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public synchronized void createSession(ClientWrap c1, ClientWrap c2, final CmdConfig config) {
        ServerSession session = new ServerSession();
        session.setId(UUID.randomUUID().toString());
        session.setClientWrap1(c1);
        session.setClientWrap2(c2);
        session.setConfig(config);
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

    public ServerSession findSession(String sid) {
        for (NatSession session : sessions) {
            if (session.getId().equals(sid)) {
                return (ServerSession) session;
            }
        }
        return null;
    }
}
