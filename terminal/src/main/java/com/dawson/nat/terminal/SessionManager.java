package com.dawson.nat.terminal;

import com.dawson.nat.baselib.NatSession;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 管理会话
 * @author dawson
 */
public class SessionManager {
    List<TerminalSession> sessions = new ArrayList<>();

    public void createSession(short port, short serverPort) {
        TerminalSession session = new TerminalSession();
        TerminalAndClientInfo clientInfo = new TerminalAndClientInfo();
        clientInfo.setIp("19.3");
        clientInfo.setPort(serverPort);
        session.setClient1Info(clientInfo);
        clientInfo = new TerminalAndClientInfo();
        clientInfo.setIp("localhost");
        clientInfo.setPort(port);
        session.setClient2Info(clientInfo);
        session.setId(port + "_" + System.currentTimeMillis());
        session.start();

        session.registerOnClosed(new Function<NatSession, Object>() {
            @Override
            public Object apply(NatSession s) {
                sessions.remove(s);
                return true;
            }
        });
        sessions.add(session);
    }
}
