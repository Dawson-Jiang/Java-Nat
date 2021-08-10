package com.dawson.nat.server;

import com.dawson.nat.baselib.ClientWrap;
import com.dawson.nat.baselib.DataUtil;
import com.dawson.nat.baselib.bean.CmdConfig;
import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.baselib.net.BaseCmdWrap;
import com.dawson.nat.baselib.net.ControlClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

    public void newClient(ControlClient client, TerminalAndClientInfo info) {
        final ClientWrap clientWrap = new ClientWrap();
        clientWrap.setClient(client);
        clientWrap.setInfo(info);
        //回复配置信息
        BaseCmdWrap res = new BaseCmdWrap(CommonBean.ControlTypeConst.TYPE_REG_INFO, configs);
        clientWrap.getClient().sendData(res);
        client.addOnDataReceived(new Function<BaseCmdWrap, Object>() {
            @Override
            public Object apply(BaseCmdWrap baseCmdWrap) {
                handleCmd(clientWrap, baseCmdWrap);
                return null;
            }
        });
        clients.add(clientWrap);
    }

    private void handleCmd(ClientWrap clientWrap, BaseCmdWrap baseCmdWrap) {
        if (baseCmdWrap.getType() == CommonBean.ControlTypeConst.TYPE_GET_TERMINALS) {
            List<TerminalAndClientInfo> infos = clients.stream().filter(new Predicate<ClientWrap>() {
                @Override
                public boolean test(ClientWrap cw) {
                    return cw.getInfo() != null && cw.getInfo().getType().equals("terminal");
                }
            }).map(new Function<ClientWrap, TerminalAndClientInfo>() {
                @Override
                public TerminalAndClientInfo apply(ClientWrap cw) {
                    return cw.getInfo();
                }
            }).collect(Collectors.toList());
            BaseCmdWrap res = new BaseCmdWrap(CommonBean.ControlTypeConst.TYPE_GET_TERMINALS, infos);
            clientWrap.getClient().sendData(res);
        } else if (baseCmdWrap.getType() == CommonBean.ControlTypeConst.TYPE_NEW_CONN) {
            JsonObject param = new Gson().fromJson(baseCmdWrap.getStringValue(), JsonObject.class);
            String tid = param.get("terminalId").getAsString();
            ClientWrap t = findClient(tid);
            String cmd = param.get("cmd").getAsString();
            CmdConfig config = findConfig(cmd);
            SessionManager.getInstance().createSession(clientWrap, t, config);
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
