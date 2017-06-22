package com.zhouyuxing.memoryassistant;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.zhouyuxing.memoryassistant.adapters.MessageAdapter;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatActivity extends BaseActivity implements Validator.ValidationListener {

    private static final String TAG = "CHAT_ACTIVITY";
    private static final String SEND_MESSAGE = "sendMessage";
    private static final String IS_OFFLINE = "isOffline";
    private static final String IS_ONLINE = "isOnline";
    private static final String WEB_URL = "http://192.168.3.23/Memas/";
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @NotEmpty(message = "不能发送空信息")
    @BindView(R.id.editTextMessage)
    protected AppCompatEditText editTextMessage;

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;

    @BindString(R.string.server_url)
    protected String serverURL;

    protected String username;
    protected String userIdentifier;
    protected Validator mValidator;

    private Socket socketClient;
    private MessageAdapter mMessageAdapter;
    private Uri soundUri;
    private Ringtone notificationRing;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        try {
            username = getIntent().getExtras().getString("username");
            userIdentifier = getIdentifier();
        } catch (Exception e) {
            Log.e(TAG, "Fail getting username: " + e.getMessage());
            Intent mIntent = new Intent();
            mIntent.setClass(this, LoginActivity.class);
            startActivity(mIntent);
            this.finish();
        }

        ButterKnife.bind(this);
        initUI();
        setupSocketClient();
    }


    public void setupSocketClient() {
        IO.Options options = new IO.Options();
        options.query = "username=" +username + "&userid=" + userIdentifier;
        try {
            socketClient = IO.socket(serverURL, options);
            socketClient.connect();
            createSocketCommands();
        } catch (URISyntaxException e) {
            Log.e(TAG, "Fail to create socket: " + e.getMessage());
        }
    }

    @Override
    public void initUI() {
        setupToolBar(mToolbar, "你好！ " + username, null, false, false);

        editTextMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    mValidator.validate();
                }
                return false;
            }
        });
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);

        mMessageAdapter = new MessageAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mMessageAdapter);
    }

    private final Handler msgHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplicationContext(),(String)msg.obj, 3).show();
                    break;
                case 2:
                    Http.myget((String)msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    @OnClick(R.id.sendImage)
    protected void onClickSendText() {
        mValidator.validate();
    }

    private void createSocketCommands() {
        socketClient.on(SEND_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args != null && args.length>0) {
                    try {
                        final JSONObject jsonMessage = (JSONObject) args[0];
                        if (jsonMessage.getString("userid").compareToIgnoreCase(userIdentifier) == 0) {
                            String url = WEB_URL + "msg_input.php?na=" + jsonMessage.getString("username") + "&msg=" + jsonMessage.getString("message");
                            Message msg = msgHandler.obtainMessage();
                            msg.what = 2;
                            msg.obj = url;
                            msgHandler.sendMessage(msg);
                            jsonMessage.put("appType", MessageAdapter.VIEW_TYPE_SEND_MESSAGE);
                        } else {
                            jsonMessage.put("appType", MessageAdapter.VIEW_TYPE_RECEIVE_MESSAGE);
                            emitSound();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMessageAdapter.addItem(jsonMessage);
                            }
                        });

                    }catch (Exception e) {
                        Log.d(TAG, "Fail on execute SEND_MESSAGE: " + e.getMessage());
                    }
                }
            }
        });

        socketClient.on(IS_ONLINE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                int x = 1;
                if (args != null && args.length>0) {
                    try {
                        final JSONObject jsonMessage = (JSONObject) args[0];
                        String ti = "<" + jsonMessage.getString("time") + "> " + jsonMessage.getString("username") + ": Hi!";
                        Message msg = msgHandler.obtainMessage();
                        msg.what = 1;
                        msg.obj = ti;
                        msgHandler.sendMessage(msg);
                    }catch (Exception e) {
                        Log.d(TAG, "Fail on execute IS_ONLINE: " + e.getMessage());
                    }
                }
            }
        });

        socketClient.on(IS_OFFLINE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                int x = 1;
                if (args != null && args.length>0) {
                    try {
                        final JSONObject jsonMessage = (JSONObject) args[0];
                        String ti = "<" + jsonMessage.getString("time") + "> " + jsonMessage.getString("username") + " Bye!";
                        Message msg = msgHandler.obtainMessage();
                        msg.what = 1;
                        msg.obj = ti;
                        msgHandler.sendMessage(msg);
                    }catch (Exception e) {
                        Log.d(TAG, "Fail on execute IS_OFFLINE: " + e.getMessage());
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socketClient != null) {
            socketClient.off();
            socketClient.disconnect();
        }
    }

    @Override
    public void onValidationSucceeded() {
        String mUsername = editTextMessage.getText().toString().trim();
        if (socketClient.connected()) {
            try {
                JSONObject jsonMessage = new JSONObject().put("message", mUsername);
                editTextMessage.setText("");
                hideKeyboard();
                socketClient.emit(SEND_MESSAGE, jsonMessage);
            } catch (JSONException e) {
                Log.e(TAG, "Fail on send message: " + e.getMessage());
            }

        } else {
            showSimpleDialog(null, getString(R.string.app_network_error));
        }
    }

    public void emitSound() {
        try {

            if (soundUri == null) {
                soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notificationRing = RingtoneManager.getRingtone(getApplicationContext(), soundUri);
            }

            notificationRing.play();

        } catch (Exception e) {
            Log.e("MessageAdapter", "Fail during alert: " + e.getMessage());
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError mError : errors) {
            String mErrorMessage = mError.getCollatedErrorMessage(this);
            View mView = mError.getView();
            if (mView instanceof AppCompatEditText)
                ((AppCompatEditText) mView).setError(mErrorMessage);

        }
    }
}
