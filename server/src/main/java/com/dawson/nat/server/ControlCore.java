package com.dawson.nat.server;

import com.dawson.nat.baselib.ClientWrap;
import com.dawson.nat.baselib.DataUtil;
import com.dawson.nat.baselib.GLog;
import com.dawson.nat.baselib.NatSession;
import com.dawson.nat.baselib.bean.CmdConfig;
import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.baselib.net.BaseCmdWrap;
import com.dawson.nat.baselib.net.ControlClient;
import com.dawson.nat.baselib.net.TransportClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author dawson
 */
public class ControlCore {
    protected List<ClientWrap> clients = new ArrayList<>();
    List<CmdConfig> configs = DataUtil.getConfigs();

    public void newClient(SocketChannel socket, TerminalAndClientInfo info) {
        final ControlClient controlClient = new ControlClient();
        controlClient.setAutoReconnect(false);
        controlClient.bindSocket(socket);

        final ClientWrap clientWrap = new ClientWrap();
        clientWrap.setClient(controlClient);
        clientWrap.setInfo(info);

        if (clients.contains(clientWrap)) {
            controlClient.close();
            return;
        }
        //回复配置信息
        BaseCmdWrap res = new BaseCmdWrap(CommonBean.ControlTypeConst.TYPE_REG_INFO, configs);
        clientWrap.getClient().sendData(res);
        controlClient.addOnDataReceived(new Function<BaseCmdWrap, Object>() {
            @Override
            public Object apply(BaseCmdWrap baseCmdWrap) {
                handleCmd(clientWrap, baseCmdWrap);
                return null;
            }
        });
        controlClient.registerConnect(aBoolean -> {
            if (!aBoolean) {
                GLog.println("control client disconn id:" + info.getId());
                clients.remove(clientWrap);
            }
            return true;
        });
        clients.add(clientWrap);
    }

    private void handleCmd(ClientWrap clientWrap, BaseCmdWrap baseCmdWrap) {
        if (baseCmdWrap.getType() == CommonBean.ControlTypeConst.TYPE_GET_TERMINALS) {
            List<TerminalAndClientInfo> infos = clients.stream().filter(cw -> cw.getInfo() != null
                    && cw.getInfo().getType().equals(CommonBean.ClientType.CLIENT_TERMINAL))
                    .map(cw -> cw.getInfo())
                    .collect(Collectors.toList());
            BaseCmdWrap res = new BaseCmdWrap(CommonBean.ControlTypeConst.TYPE_GET_TERMINALS, infos);
            clientWrap.getClient().sendData(res);
        }
    }

    public void newClient(SocketChannel socketChannel, JsonObject param) {
        String sid = param.get("sessionId").getAsString();

        ServerSession session = SessionManager.getInstance().findSession(sid);
        if (session == null) {
            //没有该session 需要创建一个session
            String uid = param.get("userId").getAsString();
            ClientWrap c1 = findClient(uid);
            String tid = param.get("terminalId").getAsString();
            ClientWrap c2 = findClient(tid);
            String cmd = param.get("cmd").getAsString();
            CmdConfig config = findConfig(cmd);
            SessionManager.getInstance().createSession(c1, c2, config);
        } else {
            //已有该session 表示该链接是terminal 直接绑定
            TransportClient client = new TransportClient();
            client.bindSocket(socketChannel);
            session.bindClient2(client);
        }
    }

    private ClientWrap findClient(String id) {
        for (ClientWrap client : clients) {
            if (client.getInfo().getId().equals(id)) {
                return client;
            }
        }
        return null;
    }

    private CmdConfig findConfig(String cmd) {
        for (CmdConfig config : configs) {
            if (config.getCmd().equals(cmd)) {
                return config;
            }
        }
        return null;
    }
}
