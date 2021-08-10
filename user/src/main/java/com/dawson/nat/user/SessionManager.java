package com.dawson.nat.user;

import com.dawson.nat.baselib.ClientWrap;
import com.dawson.nat.baselib.NatSession;
import com.dawson.nat.baselib.bean.CmdConfig;
import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.baselib.net.ControlClient;
import com.dawson.nat.baselib.net.TransportClient;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 管理会话
 *
 * @author dawson
 */
public class SessionManager {
    List<UserSession> sessions = new ArrayList<>();

    public void createSession(ControlClient client, String sid, CmdConfig config) {
        UserSession session = new UserSession();
        session.setId(sid);
        session.setConfig(config);
        TerminalAndClientInfo clientInfo = new TerminalAndClientInfo();
        clientInfo.setIp(ControlCore.SERVER_IP);
        clientInfo.setPort(ControlCore.SERVER_PORT);
        ClientWrap wrap=new ClientWrap();
        wrap.setClient(client);
        wrap.setInfo(clientInfo);
        session.setClientWrap2(wrap);

        session.registerOnClosed(new Function<NatSession, Object>() {
            @Override
            public Object apply(NatSession s) {
                sessions.remove(s);
                return true;
            }
        });
        sessions.add(session);
        session.start();
    }

    public void newCmdConn(SocketChannel socketChannel){
        for (UserSession session : sessions) {
            if(session.getState().equals(CommonBean.SessionStateConst.STATE_READY)){
                TransportClient transportClient=new TransportClient();
                transportClient.bindSocket(socketChannel);
                session.bindClient1(transportClient);
                break;
            }
        }
    }
}
