package com.dawson.nat.user;

import com.dawson.nat.baselib.Common;
import com.dawson.nat.baselib.GLog;
import com.dawson.nat.baselib.bean.CmdConfig;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.UUID;

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
        String[] im = Common.getIPAndMac();
        if (im != null) {
            clientInfo.setIp(im[0]);
            clientInfo.setId(im[1]);
        }
        clientInfo.setName("U001");
        controlCore.setClientInfo(clientInfo);
        controlCore.start();
        GLog.println("please input cmd...");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String cmd = br.readLine();
                if (cmd != null) {
                    handleCmd(cmd.split(" "));
                }else{
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void handleCmd(String[] cmd) {
        if (cmd[0].equals("exit")) {
            System.exit(0);
        } else if (cmd[0].equals("ts")) {
            controlCore.getTerminals();
        } else if (cmd[0].equals("cs")) {
            controlCore.printConfigs();
        } else {
            for (CmdConfig config : controlCore.configs) {
                if (cmd[0].equals(config.getCmd())) {
                    GLog.println("start conn...");
                    controlCore.newClient(Integer.parseInt(cmd[1]), cmd[0]);
                    return;
                }
            }
            printHelp();
        }
    }

    private static void printHelp() {
        GLog.println("ts 列表已经注册的终端");
        GLog.println("cs 查看支持的命令配置");
        GLog.println("cmd num ,发起连接cmd命令 如ssh,num终端列表中的序号");
        GLog.println("h|help 查看帮助");
        GLog.println("exit 退出");
    }
}
