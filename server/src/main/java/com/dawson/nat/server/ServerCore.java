package com.dawson.nat.server;

import com.dawson.nat.baselib.ClientWrap;
import com.dawson.nat.baselib.DataUtil;
import com.dawson.nat.baselib.GLog;
import com.dawson.nat.baselib.bean.CmdConfig;
import com.dawson.nat.baselib.bean.CommonBean;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.baselib.net.BaseCmdWrap;
import com.dawson.nat.baselib.net.ControlClient;
import com.dawson.nat.baselib.net.SocketServer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.function.Function;

/**
 * tx2模拟服务
 *
 * @author dawson
 */
public class ServerCore {
    public static void main(String[] args) {
        new ServerCore().start();
    }

    /**
     * 监听服务
     */
    private SocketServer controlServer;

    private ControlCore controlCore = new ControlCore();

    public void start() {
        controlServer = new SocketServer();
        controlServer.init(5025);
        controlServer.registerOnClientConnect(new Function<SocketChannel, Boolean>() {
            @Override
            public Boolean apply(SocketChannel socketChannel) {
                new Thread(() -> handleNewClient(socketChannel)).start();
                return true;
            }
        });
        controlServer.start();
    }

    private void handleNewClient(final SocketChannel socketChannel) {
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        int len = 0;
        try {
            len = socketChannel.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BaseCmdWrap cmd = new BaseCmdWrap();
        if (len > 0) {
            buffer.flip();
            cmd.decode(buffer);
            buffer.compact();
        } else {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            GLog.println("new conn read exception");
            return;
        }
        if (cmd.getType() == CommonBean.ControlTypeConst.TYPE_REG_INFO) {
            TerminalAndClientInfo info = new Gson().fromJson(cmd.getStringValue(), TerminalAndClientInfo.class);
            GLog.println("reg new client id:" + info.getId() + " name:" + info.getName() + " type:" + info.getType());
            controlCore.newClient(socketChannel, info);
        } else if (cmd.getType() == CommonBean.ControlTypeConst.TYPE_NEW_SESSION) {
            JsonObject jsonObject
                    = new Gson().fromJson(cmd.getStringValue(), JsonObject.class);
            String sessionId = jsonObject.get("sessionId").getAsString();
            String type = jsonObject.get("type").getAsString();
            GLog.println("new cmd conn sid:" + sessionId + " type:" + type);
            controlCore.newClient(socketChannel, jsonObject);
        } else {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            GLog.println("new conn but conn info erro");
        }
    }
}
