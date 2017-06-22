package com.zhouyuxing.memoryassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zxing.activity.CaptureActivity;

import java.io.File;

public class jingjilog extends BaseActivity implements View.OnClickListener {

    private static Button btnqrjr;
    private static Button btncj;
    private static Button btnjr;
    private static AppCompatEditText jrbh;
    private static AppCompatEditText jrys;
    private static String username;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jingjilog);
        try {
            username = getIntent().getExtras().getString("username");
        } catch (Exception e) {
            Log.e(TAG, "Fail getting username: " + e.getMessage());
            Intent mIntent = new Intent();
            mIntent.setClass(this, LoginActivity.class);
            startActivity(mIntent);
            this.finish();
        }
        initUI();
    }

    @Override
    public void initUI() {
        btnqrjr = (Button) findViewById(R.id.btnqrjr);
        btnqrjr.setOnClickListener(this);
        btnqrjr.setVisibility(View.GONE);
        btncj = (Button) findViewById(R.id.btncj);
        btncj.setOnClickListener(this);
        btncj.setVisibility(View.VISIBLE);
        btnjr = (Button) findViewById(R.id.btnjr);
        btnjr.setOnClickListener(this);
        btnjr.setVisibility(View.VISIBLE);
        jrbh = (AppCompatEditText) findViewById(R.id.jrbh);
        jrbh.setVisibility(View.GONE);
        jrys = (AppCompatEditText) findViewById(R.id.jrys);
        jrys.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnqrjr) {
            Intent mIntent = new Intent();
            mIntent.setClass(this, JingjiActivity.class);
            mIntent.putExtra("username", username);
            mIntent.putExtra("jyrs",jrys.getText().toString());
            startActivity(mIntent);
        }
        if (v.getId() == R.id.btnjr) {
            Intent mIntent = new Intent();
            mIntent.setClass(this, JingjiActivity.class);
            mIntent.putExtra("username", username);
            startActivity(mIntent);
        }
        if (v.getId() == R.id.btncj) {
            jrbh.setVisibility(View.VISIBLE);
            btnqrjr.setVisibility(View.VISIBLE);
            jrys.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}