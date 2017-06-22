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

public class LoginActivity extends BaseActivity implements OnClickListener {
    private static Button btnscan;
    private static Button btnlog;
    private static Button btnreg;
    private static Button btnlogout;
    private static Button btnenter;
    private static Button btnchatlog;
    private static Button btnupdate;
    private static Button btnstart;
    private static Button btnjj;
    private static Button btnwjmm;
    public String mUsername;
    private static AppCompatEditText etusername;
    private static AppCompatEditText etpassword;
    private static final String WEB_URL = "http://192.168.3.23/Memas/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        Http.textviewresponse = (TextView)findViewById(R.id.textviewresponse);
    }

    @Override
    public void initUI() {
        btnjj = (Button) findViewById(R.id.btnjj);
        btnjj.setOnClickListener(this);
        btnjj.setVisibility(View.GONE);
        btnwjmm = (Button) findViewById(R.id.btnwjmm);
        btnwjmm.setOnClickListener(this);
        btnwjmm.setVisibility(View.VISIBLE);
        btnscan = (Button) findViewById(R.id.btnscan);
        btnscan.setOnClickListener(this);
        btnscan.setVisibility(View.GONE);
        btnstart = (Button) findViewById(R.id.btnstart);
        btnstart.setOnClickListener(this);
        btnstart.setVisibility(View.GONE);
        btnlog = (Button) findViewById(R.id.btnlog);
        btnlog.setOnClickListener(this);
        btnreg = (Button) findViewById(R.id.btnreg);
        btnreg.setOnClickListener(this);
        btnlogout = (Button) findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(this);
        btnlogout.setVisibility(View.GONE);
        btnenter = (Button) findViewById(R.id.btnenter);
        btnenter.setOnClickListener(this);
        btnenter.setVisibility(View.GONE);
        btnchatlog = (Button) findViewById(R.id.btnchatlog);
        btnchatlog.setOnClickListener(this);
        btnchatlog.setVisibility(View.GONE);
        btnlog.setVisibility(View.VISIBLE);
        btnreg.setVisibility(View.VISIBLE);
        btnupdate = (Button) findViewById(R.id.btnupdate);
        btnupdate.setOnClickListener(this);
        btnupdate.setVisibility(View.VISIBLE);
        etusername = (AppCompatEditText) findViewById(R.id.etusername);
        etpassword = (AppCompatEditText) findViewById(R.id.etpassword);
        etusername.setVisibility(View.VISIBLE);
        etpassword.setVisibility(View.VISIBLE);
    }

    private static Handler handlerlog = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    btnwjmm.setVisibility(View.GONE);
                    btnchatlog.setVisibility(View.VISIBLE);
                    btnenter.setVisibility(View.VISIBLE);
                    btnjj.setVisibility(View.VISIBLE);
                    btnlogout.setVisibility(View.VISIBLE);
                    btnlog.setVisibility(View.GONE);
                    btnreg.setVisibility(View.GONE);
                    btnupdate.setVisibility(View.GONE);
                    btnscan.setVisibility(View.VISIBLE);
                    btnstart.setVisibility(View.VISIBLE);
                    etusername.setVisibility(View.INVISIBLE);
                    etpassword.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                   // update.install(this.Context,"/storage/sdcard/"+update.DOWNLOAD_FOLDER_NAME + "/" + update.DOWNLOAD_FILE_NAME);
                default:
                    break;
            }
        }
    };
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnscan) {
            Intent intent = new Intent(this, CaptureActivity.class);
            startActivityForResult(intent, 0);
            //startActivity(intent);
        }
        if (v.getId() == R.id.btnlog) {
            mUsername = etusername.getText().toString();
            String password = mymd5.getMd5Value(etpassword.getText().toString());
            String url = WEB_URL + "l_input.php?na=" + mUsername + "&pw=" + password;
            Http.myget(url);

            new Thread(){
                @Override
                public void run() {
                    try {
                        sleep(1000);
                        if (Http.response.charAt(0) == 'S') {
                            Message message = new Message();
                            message.what = 2;
                            handlerlog.sendMessage(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        if (v.getId() == R.id.btnreg) {
            Intent mIntent = new Intent();
            mIntent.setClass(this, SignupActivity.class);
            startActivity(mIntent);
        }
        if (v.getId() == R.id.btnlogout) {
            String username = etusername.getText().toString();
            String url = WEB_URL + "logout.php?na=" + username;
            Http.myget(url);
            btnenter.setVisibility(View.GONE);
            btnchatlog.setVisibility(View.GONE);
            btnlogout.setVisibility(View.GONE);
            btnlog.setVisibility(View.VISIBLE);
            btnreg.setVisibility(View.VISIBLE);
            btnupdate.setVisibility(View.VISIBLE);
            etusername.setVisibility(View.VISIBLE);
            etpassword.setVisibility(View.VISIBLE);
            btnscan.setVisibility(View.GONE);
            btnstart.setVisibility(View.GONE);
            btnjj.setVisibility(View.GONE);
            btnwjmm.setVisibility(View.VISIBLE);
        }
        if (v.getId() == R.id.btnenter) {
            if (isNetworkAvailable()) {
                Intent mIntent = new Intent();
                mIntent.setClass(this, ChatActivity.class);
                mIntent.putExtra("username", mUsername);
                startActivity(mIntent);
          //      this.finish();
            } else {
                showSimpleDialog(null, getString(R.string.app_network_error));
            }
        }
        if (v.getId() == R.id.btnjj) {
            Intent mIntent = new Intent();
            mIntent.setClass(this, jingjilog.class);
            mIntent.putExtra("username", mUsername);
            startActivity(mIntent);
        }
        if (v.getId() == R.id.btnchatlog) {
            Intent mIntent = new Intent();
            mIntent.setClass(this, ChatlogActivity.class);
            mIntent.putExtra("username", mUsername);
            startActivity(mIntent);
        }
        if (v.getId() == R.id.btnstart) {
            Intent mIntent = new Intent();
            mIntent.setClass(this, MainActivity.class);
            mIntent.putExtra("username", mUsername);
            startActivity(mIntent);
        }
        if (v.getId() == R.id.btnwjmm) {
            Intent mIntent = new Intent();
            mIntent.setClass(this, WjmmActivity.class);
            startActivity(mIntent);
        }
        if (v.getId() == R.id.btnupdate) {
            update myupdate = new update(getApplicationContext());
            int vnow = 1;
            try {
                vnow = myupdate.getVersionName();
            }catch (Exception e) {
                e.printStackTrace();
            }
            Http.myget(WEB_URL + "ver.php?vnow=" + vnow);
            String url = WEB_URL + "vnew.apk";
            myupdate.downLoad(url);
            final String fp = "/storage/sdcard/"+update.DOWNLOAD_FOLDER_NAME + "/" + update.DOWNLOAD_FILE_NAME;
            new Thread(){
                @Override
                public void run() {
                    try {
                        while(true) {
                            sleep(1000);
                            File file = new File(fp);
                            if (file != null && file.length() > 0 && file.exists() && file.isFile() && Http.response.charAt(0) == 'A'){
                                update.install(getApplicationContext(),fp);
                                break;
                            }
                            if (Http.response.charAt(0) == 'D')
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String hashnumber = data.getExtras().getString("result");
            String username = etusername.getText().toString();
            String password = mymd5.getMd5Value(etpassword.getText().toString());
            String url = WEB_URL + "qrl_input.php?hashn=" + hashnumber + "&na=" + username + "&pw=" + password;
            Http.myget(url);
        }
    }
}