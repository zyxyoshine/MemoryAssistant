package com.zhouyuxing.memoryassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zxing.activity.CaptureActivity;

import java.io.File;

public class SignupActivity extends BaseActivity implements OnClickListener {

        private static Button sgbtnreg;
        public String name,pw,info,pwa,pwq;
        private static AppCompatEditText sgusername;
        private static AppCompatEditText sgpassword;
        private static AppCompatEditText sgpassword2;
        private static AppCompatEditText sguserinfo;
        private static AppCompatEditText sgpwq;
        private static AppCompatEditText sgpwa;
        public static TextView sgtext;
        private static final String WEB_URL = "http://192.168.3.23/Memas/";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);
            initUI();
            sgtext = (TextView)findViewById(R.id.sgtext);
        }

        @Override
        public void initUI() {
            sgbtnreg = (Button) findViewById(R.id.sgbtnreg);
            sgbtnreg.setOnClickListener(this);
            sgbtnreg.setVisibility(View.VISIBLE);
            sgusername = (AppCompatEditText) findViewById(R.id.sgusername);
            sgpassword = (AppCompatEditText) findViewById(R.id.sgpassword);
            sgpassword2 = (AppCompatEditText) findViewById(R.id.sgpassword2);
            sguserinfo = (AppCompatEditText) findViewById(R.id.sguserinfo);
            sgpwq = (AppCompatEditText) findViewById(R.id.sgpwq);
            sgpwa = (AppCompatEditText) findViewById(R.id.sgpwa);
            sgusername.setVisibility(View.VISIBLE);
            sgpassword.setVisibility(View.VISIBLE);
            sgpassword2.setVisibility(View.VISIBLE);
            sguserinfo.setVisibility(View.VISIBLE);
            sgpwq.setVisibility(View.VISIBLE);
            sgpwa.setVisibility(View.VISIBLE);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.sgbtnreg) {
                String username = sgusername.getText().toString();
                String password = mymd5.getMd5Value(sgpassword.getText().toString());
                String password2 = mymd5.getMd5Value(sgpassword2.getText().toString());
                String userinfo = sguserinfo.getText().toString();
                String pwq = sgpwq.getText().toString();
                String pwa = sgpwa.getText().toString();
                if (password.equals(password2)) {
                    String url = WEB_URL + "register.php?na=" + username + "&pw=" + password + "&info=" + userinfo + "&pwq=" + pwq + "&pwa=" + pwa;
                    Http.myget(url);
                    Intent mIntent = new Intent();
                    mIntent.setClass(this, LoginActivity.class);
                    startActivity(mIntent);
                    this.finish();
                }else{
                    sgtext.setText("密码验证失败！请重新输入");
                }
            }
        }
    }