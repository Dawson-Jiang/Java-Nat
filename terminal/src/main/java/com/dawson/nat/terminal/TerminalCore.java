package com.dawson.nat.terminal;

import com.dawson.nat.baselib.GLog;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;

/**
 * tx2模拟服务
 *
 * @author dawson
 */
public class TerminalCore {
    public static void main(String[] args) {
        GLog.println("start...");
        ControlCore controlCore = new ControlCore();
        TerminalAndClientInfo clientInfo = new TerminalAndClientInfo();
        clientInfo.setId("3rfwefwe");
        clientInfo.setName("v001");
        controlCore.setClientInfo(clientInfo);
        controlCore.start();
        GLog.println("start success");
    }
}
