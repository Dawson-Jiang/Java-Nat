package com.dawson.nat.terminal;

import com.dawson.nat.baselib.ClientWrap;
import com.dawson.nat.baselib.NatSession;
import com.dawson.nat.baselib.bean.CmdConfig;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.baselib.net.ControlClient;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 管理会话
 *
 * @author dawson
 */
public class SessionManager {
    List<TerminalSession> sessions = new ArrayList<>();

    public void createSession(ControlClient client, String sid, CmdConfig config) {
        TerminalSession session = new TerminalSession();
        session.setId(sid);
        session.setConfig(config);
        TerminalAndClientInfo clientInfo = new TerminalAndClientInfo();
        clientInfo.setIp(ControlCore.SERVER_IP);
        clientInfo.setPort(ControlCore.SERVER_PORT);
        ClientWrap wrap=new ClientWrap();
        wrap.setInfo(clientInfo);
        wrap.setClient(client);
        session.setClientWrap1(wrap);

        clientInfo = new TerminalAndClientInfo();
        clientInfo.setIp("localhost");
        clientInfo.setPort(config.getPort());
        wrap=new ClientWrap();
        wrap.setInfo(clientInfo);
        session.setClientWrap2(wrap);
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
