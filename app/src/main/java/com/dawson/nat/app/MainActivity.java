package com.dawson.nat.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dawson.nat.baselib.bean.TerminalAndClientInfo;
import com.dawson.nat.terminal.ControlCore;

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
        clientInfo.setId("3rfwefwefkkgjgfiejciufi");
        clientInfo.setName("v001");
        controlCore.setClientInfo(clientInfo);
        controlCore.start();
    }
}