package com.dawson.nat.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.terminal.ControlCore;

import java.util.UUID;

/**
 * 测试终端
 *
 * @author dawson
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(() -> startNat()).start();
    }

    ControlCore controlCore = new ControlCore();
    TerminalAndClientInfo clientInfo = new TerminalAndClientInfo();

    private void startNat() {
        clientInfo.setId(UUID.randomUUID().toString());
        clientInfo.setName("T001");
        controlCore.setClientInfo(clientInfo);
        controlCore.init("192.168.0.3", (short) 5025);
        controlCore.start();
    }
}