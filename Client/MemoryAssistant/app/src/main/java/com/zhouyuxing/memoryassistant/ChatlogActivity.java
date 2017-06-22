package com.zhouyuxing.memoryassistant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChatlogActivity extends BaseActivity  implements View.OnClickListener {
    public static TextView chatlog;
    public static TextView chatlog2;
    private static Button btnsend;
    private static Button btnrecv;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    protected String username;
    private static final String WEB_URL = "http://192.168.3.23/Memas/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_log);
        try {
            username = getIntent().getExtras().getString("username");
        } catch (Exception e) {
            Log.e(TAG, "Fail getting username: " + e.getMessage());
            Intent mIntent = new Intent();
            mIntent.setClass(this, LoginActivity.class);
            startActivity(mIntent);
            this.finish();
        }
        ButterKnife.bind(this);
        initUI();
        chatlog = (TextView)findViewById(R.id.chatlog);
        chatlog.setMovementMethod(ScrollingMovementMethod.getInstance());
        chatlog2 = (TextView)findViewById(R.id.chatlog2);
        chatlog2.setMovementMethod(ScrollingMovementMethod.getInstance());
        btnsend = (Button) findViewById(R.id.btnsend);
        btnsend.setOnClickListener(this);
        btnrecv = (Button) findViewById(R.id.btnrecv);
        btnrecv.setOnClickListener(this);
        getchatlog();
    }

    @Override
    public void initUI() {
        setupToolBar(mToolbar, "你好！ " + username, null, false, false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnsend) {
            chatlog.setVisibility(View.GONE);
            chatlog2.setVisibility(View.VISIBLE);
        }
        if (v.getId() == R.id.btnrecv) {
            chatlog.setVisibility(View.VISIBLE);
            chatlog2.setVisibility(View.GONE);
        }
    }
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    chatlog.setText(Http.response);
                    Http.textviewresponse.setText("");
                    break;
                case 2:
                    chatlog2.setText(Http.response);
                    Http.textviewresponse.setText("");
                    break;
                default:
                    break;
            }
        }

    };

    public void getchatlog() {
        String url = WEB_URL + "chat_log.php?na=" + username+ "&tp=1";
        Http.myget(url);
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(200);
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        final String url2 = WEB_URL + "chat_log.php?na=" + username+ "&tp=2";
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(500);
                    Http.myget(url2);
                    sleep(200);
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
