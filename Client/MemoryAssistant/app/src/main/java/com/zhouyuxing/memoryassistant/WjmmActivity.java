package com.zhouyuxing.memoryassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zxing.activity.CaptureActivity;

import java.io.File;

public class WjmmActivity extends BaseActivity implements View.OnClickListener {

    private static Button btnpwq;
    private static Button btnpwa;
    private static AppCompatEditText wjmm;
    private static AppCompatEditText wjmmpw;
    private static AppCompatEditText wjmmpw1;
    private static String username;
    private static final String WEB_URL = "http://192.168.3.23/Memas/";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wjmm);
        initUI();
    }
    @Override
    public void initUI() {
        btnpwq = (Button) findViewById(R.id.btnpwq);
        btnpwq.setOnClickListener(this);
        btnpwq.setVisibility(View.VISIBLE);
        btnpwa = (Button) findViewById(R.id.btnpwa);
        btnpwa.setOnClickListener(this);
        btnpwa.setVisibility(View.GONE);
        wjmm = (AppCompatEditText) findViewById(R.id.wjmm);
        wjmm.setVisibility(View.VISIBLE);
        wjmmpw = (AppCompatEditText) findViewById(R.id.wjmmpw);
        wjmmpw.setVisibility(View.GONE);
        wjmmpw1 = (AppCompatEditText) findViewById(R.id.wjmmpw1);
        wjmmpw1.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnpwq) {
            username = wjmm.getText().toString();
            Http.myget(WEB_URL + "getpwq.php?na=" + username);
            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(500);
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        if (v.getId() == R.id.btnpwa) {
            String password = mymd5.getMd5Value(wjmmpw.getText().toString());
            String password2 = mymd5.getMd5Value(wjmmpw1.getText().toString());
            if (password.equals(password2)) {
                Http.myget(WEB_URL + "uppwa.php?na=" + username + "&pw=" + password);
                Toast.makeText(getApplicationContext(),"修改成功！", 3).show();
            }else{
                Toast.makeText(getApplicationContext(),"密码不匹配不存在！", 3).show();
            }
        }
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (!Http.response.equals("_wr_")) {
                        wjmmpw.setVisibility(View.VISIBLE);
                        wjmmpw1.setVisibility(View.VISIBLE);
                        btnpwq.setVisibility(View.GONE);
                        btnpwa.setVisibility(View.VISIBLE);
                        wjmm.setText("");
                        wjmm.setHint("请回答:" + Http.response + "?");
                    }else
                        Toast.makeText(getApplicationContext(),"用户名不存在！", 3).show();
                    Http.textviewresponse.setText("");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}