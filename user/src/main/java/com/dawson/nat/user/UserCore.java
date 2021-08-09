package com.dawson.nat.user;

import com.dawson.nat.baselib.GLog;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * tx2模拟服务
 *
 * @author dawson
 */
public class UserCore {
    private static ControlCore controlCore = new ControlCore();

    public static void main(String[] args) {
        GLog.println("start...");
        TerminalAndClientInfo clientInfo = new TerminalAndClientInfo();
        clientInfo.setId("hgff5443");
        clientInfo.setName("u001");
        controlCore.setClientInfo(clientInfo);
        controlCore.start();
        GLog.println("start success");
        GLog.println("input cmd to do sth.");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String cmd = br.readLine();
                if (cmd != null) {
                    handleCmd(cmd.split(" "));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void handleCmd(String[] cmd) {
        if (cmd[0].equals("-e")) {
            System.exit(0);
        } else if (cmd[0].equals("-l")) {
            controlCore.getTerminals();
        } else if (cmd[0].equals("-c")) {
            controlCore.getCmdConfigs();
        } else if (cmd[0].equals("-n")) {
            if (cmd.length < 3) {
                GLog.println("cmd param error");
                return;
            }
            controlCore.newClient(Integer.parseInt(cmd[1]), cmd[2]);
        } else if (cmd[0].equals("-h")) {
            printHelp();
        } else {
            GLog.println("unknown cmd");
        }
    }

    private static void printHelp() {
        GLog.println("-l 列表已经注册的终端");
        GLog.println("-c 查看支持的命令配置");
        GLog.println("-n num cmd ,发起连接 num终端列表中的序号，cmd命令 如ssh");
        GLog.println("-h 查看帮助");
        GLog.println("-e 退出");
    }
}
