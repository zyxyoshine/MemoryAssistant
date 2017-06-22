package com.zhouyuxing.memoryassistant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.AppCompatEditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.KeyEvent;
import android.widget.Toast;
import android.widget.RadioGroup;
import com.zxing.activity.CaptureActivity;
import android.widget.CompoundButton.OnCheckedChangeListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.widget.CompoundButton;
import android.os.CountDownTimer;
import java.util.Timer;
import java.util.TimerTask;
public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static WebView probinfo;
    public static int nowprob;
    public static int maxprob;
    public static int yesprob;
    public static char tp;
    public static int no;
    public static int qnum;
    public static LinearLayout layout_kinda;
    public static AppCompatEditText kinda1;
    public static AppCompatEditText kinda2;
    public static AppCompatEditText kinda3;
    public static AppCompatEditText kinda4;
    public static LinearLayout layout_kindb;
    public static CheckBox kindb1;
    public static CheckBox kindb2;
    public static CheckBox kindb3;
    public static CheckBox kindb4;
    public static Button btnforget;
    public static Button btnwish;
    public static Button btnnextp;
    public static TextView minfo;

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    protected String username;
    private static final String WEB_URL = "http://192.168.3.23/Memas/";
    public static String probs ="B14A11B22A22";//for demo
    private boolean[] ck = new boolean[5];
    private CountDownTimer timer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        probinfo = (WebView)findViewById(R.id.probinfo);
        probinfo.getSettings().setJavaScriptEnabled(true);
        nowprob = 0;
        maxprob = probs.length() / 3;
        yesprob = 0;
        donextp();
       // probinfo.loadUrl(WEB_URL + "getinfo.php");
       // probinfo.setWebViewClient(new HelloWebViewClient ());
    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void initUI() {
        setupToolBar(mToolbar, "你好！ " + username, null, false, false);
        minfo = (TextView) findViewById(R.id.minfo);
        btnforget = (Button) findViewById(R.id.btnforget);
        btnforget.setOnClickListener(this);
        btnwish = (Button) findViewById(R.id.btnwish);
        btnwish.setOnClickListener(this);
        btnnextp = (Button) findViewById(R.id.btnnextp);
        btnnextp.setOnClickListener(this);
        layout_kinda = (LinearLayout) findViewById(R.id.layout_kinda);
        layout_kindb = (LinearLayout) findViewById(R.id.layout_kindb);
        kinda1 = (AppCompatEditText) findViewById(R.id.kinda1);
        kinda2 = (AppCompatEditText) findViewById(R.id.kinda2);
        kinda3 = (AppCompatEditText) findViewById(R.id.kinda3);
        kinda4 = (AppCompatEditText) findViewById(R.id.kinda4);
        kindb1 = (CheckBox) findViewById(R.id.kindb1);
        kindb2 = (CheckBox) findViewById(R.id.kindb2);
        kindb3 = (CheckBox) findViewById(R.id.kindb3);
        kindb4 = (CheckBox) findViewById(R.id.kindb4);
        kindb1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
                ck[1] = arg1;
            }
        });
        kindb2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
                ck[2] = arg1;
            }
        });
        kindb3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
                ck[3] = arg1;
            }
        });
        kindb4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
                ck[4] = arg1;
            }
        });
    }

    public void donextp() {
        if(nowprob > 0)
            timer.cancel();
        if (nowprob >= maxprob) {
            //minfo.setText("完成 " + (nowprob) + "/" + maxprob + " |正确 " + yesprob + "/" + nowprob);
            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(1000);
                        Message message = new Message();
                        message.what = 3;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }else {
            tp = probs.charAt(nowprob * 3);
            no = probs.charAt(nowprob * 3 + 1) - '0';
            qnum = probs.charAt(nowprob * 3 + 2) - '0';
            new Thread() {
                @Override
                public void run() {
                    try {
                        Message message = new Message();
                        message.what = 2;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            if (tp == 'A') {
                layout_kindb.setVisibility(View.GONE);
                layout_kinda.setVisibility(View.VISIBLE);
                probinfo.loadUrl(WEB_URL + "getinfoa.php?pid=" + no);
                probinfo.setWebViewClient(new HelloWebViewClient());
                if (qnum >= 1) {
                    kinda1.setVisibility(View.VISIBLE);
                } else
                    kinda1.setVisibility(View.GONE);
                if (qnum >= 2) {
                    kinda2.setVisibility(View.VISIBLE);
                } else
                    kinda2.setVisibility(View.GONE);
                if (qnum >= 3) {
                    kinda3.setVisibility(View.VISIBLE);
                } else
                    kinda3.setVisibility(View.GONE);
                if (qnum >= 4) {
                    kinda4.setVisibility(View.VISIBLE);
                } else
                    kinda4.setVisibility(View.GONE);
            }
            if (tp == 'B') {
                layout_kinda.setVisibility(View.GONE);
                layout_kindb.setVisibility(View.VISIBLE);
                probinfo.loadUrl(WEB_URL + "getinfob.php?pid=" + no);
                probinfo.setWebViewClient(new HelloWebViewClient());
                if (qnum >= 1) {
                    kindb1.setVisibility(View.VISIBLE);
                } else
                    kindb1.setVisibility(View.GONE);
                if (qnum >= 2) {
                    kindb2.setVisibility(View.VISIBLE);
                } else
                    kindb2.setVisibility(View.GONE);
                if (qnum >= 3) {
                    kindb3.setVisibility(View.VISIBLE);
                } else
                    kindb3.setVisibility(View.GONE);
                if (qnum >= 4) {
                    kindb4.setVisibility(View.VISIBLE);
                } else
                    kindb4.setVisibility(View.GONE);
                kindb1.setText("");
                kindb2.setText("");
                kindb3.setText("");
                kindb4.setText("");
                kindb4.setChecked(false);
                kindb3.setChecked(false);
                kindb2.setChecked(false);
                kindb1.setChecked(false);
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Http.myget(WEB_URL + "getnumb.php?pid=" + no + "&anst=" + 1);
                            sleep(200);
                            kindb1.setText(Http.response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(250);
                            Http.myget(WEB_URL + "getnumb.php?pid=" + no + "&anst=" + 2);
                            sleep(200);
                            kindb2.setText(Http.response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(500);
                            Http.myget(WEB_URL + "getnumb.php?pid=" + no + "&anst=" + 3);
                            sleep(200);
                            kindb3.setText(Http.response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(750);
                            Http.myget(WEB_URL + "getnumb.php?pid=" + no + "&anst=" + 4);
                            sleep(200);
                            kindb4.setText(Http.response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnnextp) {
            String mans = "";
            if (nowprob >= maxprob) {
                Toast.makeText(getApplicationContext(),"练习已经结束！", 3).show();
                probinfo.setVisibility(View.GONE);
                layout_kindb.setVisibility(View.GONE);
                layout_kinda.setVisibility(View.GONE);
            }else {
                if (tp == 'A') {
                    mans = mans + kinda1.getText().toString();
                    mans = mans + kinda2.getText().toString();
                    mans = mans + kinda3.getText().toString();
                    mans = mans + kinda4.getText().toString();
                    Http.myget(WEB_URL + "cknuma.php?pid=" + no + "&ans=" + mans);
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
                if (tp == 'B') {
                    for (int i = 1; i <= qnum; i++) {
                        if (ck[i])
                            mans = mans + '1';
                        else
                            mans = mans + '0';
                    }
                    Http.myget(WEB_URL + "cknumb.php?pid=" + no + "&ans=" + mans);
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
                nowprob += 1;
                donextp();
            }
        }
        if (v.getId() == R.id.btnwish) {
            Toast.makeText(getApplicationContext(),":)别忘了呦！", 3).show();
            yesprob++;
            nowprob += 1;
            donextp();
           // Http.myget(WEB_URL + "addwish.php?pid=" + no +"&una=" + username);
        }
        if (v.getId() == R.id.btnforget) {
            Toast.makeText(getApplicationContext(),":(继续努力！", 3).show();
            nowprob += 1;
            donextp();
          //  Http.myget(WEB_URL + "addfq.php?pid=" + no +"&una=" + username);
        }
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (Http.response.equals("1")) {
                        Toast.makeText(getApplicationContext(),"回答正确！", 3).show();
                        yesprob++;
                    }else
                        Toast.makeText(getApplicationContext(),"回答错误！", 3).show();
                    Http.textviewresponse.setText("");
                    break;
                case 2:
                    timer = new CountDownTimer(10000,1000) {
                        public void onTick(long millisUntilFinished) {
                            minfo.setText("当前 "+(nowprob+1) +"/" + maxprob +" |正确 " + yesprob + "/" + nowprob + " |剩余 " + (millisUntilFinished / 1000) + "秒");
                        }
                        public void onFinish() {
                            nowprob++;
                            donextp();
                        }
                    };
                    timer.start();
                    break;
                case 3:
                    minfo.setText("完成 " + (nowprob) + "/" + maxprob + " |正确 " + yesprob + "/" + nowprob);
                    probinfo.setVisibility(View.GONE);
                    layout_kindb.setVisibility(View.GONE);
                    layout_kinda.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"练习已经结束！", 3).show();
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