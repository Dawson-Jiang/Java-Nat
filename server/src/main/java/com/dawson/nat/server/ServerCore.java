package com.dawson.nat.server;

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
        GLog.println("hello dawson");
        new ServerCore().start();
    }

    /**
     * 监听服务
     */
    private SocketServer controlServer;

    private ControlCore controlCore = new ControlCore();

    public void start() {
        controlServer = new SocketServer();
        controlServer.init(5036);
        controlServer.registerOnClientConnect(new Function<SocketChannel, Boolean>() {
            @Override
            public Boolean apply(SocketChannel socketChannel) {
                handleNewClient(socketChannel);
                return true;
            }
        });
    }


    private void handleNewControlClient(final SocketChannel socketChannel) {
    }

    private void handleNewClient(final SocketChannel socketChannel) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
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
        }
        if (cmd.getType() == CommonBean.ControlTypeConst.TYPE_REG_INFO) {
            final ControlClient controlClient = new ControlClient();
            controlClient.bindSocket(socketChannel);
            TerminalAndClientInfo info = new Gson().fromJson(cmd.getStringValue(), TerminalAndClientInfo.class);
            controlCore.newClient(controlClient, info);
        } else if (cmd.getType() == CommonBean.ControlTypeConst.TYPE_NEW_CMD_CONN) {
            JsonObject jsonObject
                    = new Gson().fromJson(cmd.getStringValue(), JsonObject.class);
            String sessionId = jsonObject.getAsJsonObject("sessionId").getAsString();
            String type = jsonObject.getAsJsonObject("type").getAsString();


            SessionManager.getInstance().newClient(socketChannel,sessionId,type);
        }
    }
}
