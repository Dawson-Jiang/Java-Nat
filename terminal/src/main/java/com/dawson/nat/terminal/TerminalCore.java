package com.dawson.nat.terminal;

import com.dawson.nat.baselib.GLog;
import com.dawson.nat.baselib.bean.TerminalAndClientInfo;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * tx2模拟服务
 *
 * @author dawson
 */
public class TerminalCore {
    public static void main(String[] args) {
//        GLog.println("start...");
//        ControlCore controlCore = new ControlCore();
//        TerminalAndClientInfo clientInfo = new TerminalAndClientInfo();
//        clientInfo.setId("3rfwefwefkkgjgfiejciufi");
//        clientInfo.setName("v001");
//        controlCore.setClientInfo(clientInfo);
//        controlCore.start();
//        GLog.println("start success");

        try {
            File myfile =new File("c:\\Windows\\System32","cmd.exe");
//            File myfile = new File("E:\\program\\AndroidSDK\\platform-tools", "adb.exe");
//            String path = myfile.getAbsolutePath();
//            String commons[] = {"c:\\Windows\\System32\\cmd.exe","adb connect 172.16.3.10"};
//            String commons[] = {"adb connect 172.16.3.10"};
            String commons[] = {"adb"};
           Process p= Runtime.getRuntime().exec(commons);
//            Desktop.getDesktop().open(myfile);
            BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = "";
            while((str = buf.readLine())!= null)
            {
                System.out.println(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
