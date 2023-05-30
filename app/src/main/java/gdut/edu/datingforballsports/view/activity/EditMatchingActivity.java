package gdut.edu.datingforballsports.view.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.ChatMessage;
import gdut.edu.datingforballsports.domain.MatchingItem;
import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.presenter.EditMatchingPresenter;
import gdut.edu.datingforballsports.util.GPSUtils;
import gdut.edu.datingforballsports.util.TextUtils;
import gdut.edu.datingforballsports.view.EditMatchingView;
import gdut.edu.datingforballsports.view.Service.SocketService;
import gdut.edu.datingforballsports.view.adapter.CommonAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EditMatchingActivity extends BaseActivity implements EditMatchingView {
    private static final int LOAD_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;
    private static final int MATCHING_FINISH = 3;
    private MatchingMessageReceiver matchingMessageReceiver;
    private View view;

    private EditMatchingPresenter ePresenter;

    private List<Post> list = null;
    private CommonAdapter<Post> mCommonAdapter = null;
    private Intent intent;
    private int userId = -1;
    private String token;
    private String RCmsg;
    private String ballType;
    private String memberNum;
    private String city;
    private RecyclerView recyclerView;
    public Handler mHandler;
    private Gson gson;
    private SharedPreferences sharedPreferences;
    private ServiceConnection serviceConnection;
    private SocketService.JWebSocketClientBinder binder;
    private SocketService socketService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_match_room);
        System.out.println("EditMatchingActivityCreate:wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case LOAD_SUCCEED:
                        Toast.makeText(getApplication(), "上传成功！", Toast.LENGTH_LONG).show();
                        setView();
                        break;
                    case LOAD_FAILED:
                        Toast.makeText(getApplication(), String.valueOf(message.obj), Toast.LENGTH_LONG).show();
                        break;
                    case MATCHING_FINISH:
                        if (Integer.parseInt(memberNum) == 2) {
                            Intent intent = new Intent();
                            intent.putExtra("userId", userId);
                            intent.putExtra("token", token);
                            intent.putExtra("messageBean", String.valueOf(message.obj));
                            intent.setClass(getApplicationContext(), ChatActivity.class);
                            startActivity(intent);
                        }else {

                        }
                        finish();
                        break;
                }
            }
        };

        setData();
        setView();
        bindService();
    }

    private void setData() {
        ePresenter = new EditMatchingPresenter(this);
        intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        token = intent.getStringExtra("token");
        gson = new Gson();
        /*GPSUtils.getInstance().getProvince(view.getContext(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    JSONObject jsonObjectALL = new JSONObject(responseData);
                    JSONObject result = jsonObjectALL.getJSONObject("result");
                    city = result.getJSONObject("addressComponent").getJSONObject("city").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });*/
        city = "广州";
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = (SocketService.JWebSocketClientBinder) service;
                socketService = binder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                binder = null;
            }
        };
        doRegisterReceiver();
    }

    private void setView() {
        buttonClick(R.id.new_match_room_button_OK, view -> {
            ballType = ((TextView) findViewById(R.id.new_match_room_content)).getText().toString();
            memberNum = ((TextView) findViewById(R.id.new_match_room_people)).getText().toString();
            if (!TextUtils.isEmpty(ballType) && !TextUtils.isEmpty(memberNum)) {
                ((LinearLayout) findViewById(R.id.new_match_reminder)).setVisibility(View.VISIBLE);
                ePresenter.uploadMatchingRequest(userId, token, ballType, memberNum, city);
            }
        });

        buttonClick(R.id.new_match_room_button, view -> {
            ePresenter.cancelMatchingRequest(userId);
            finish();
        });
    }

    private void bindService() {
        Intent bindIntent = new Intent(this, SocketService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void onUploadSuccess() {
        Message msg = Message.obtain();
        msg.what = LOAD_SUCCEED;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onUploadFails(String msg_) {
        Message msg = Message.obtain();
        msg.what = LOAD_FAILED;
        msg.obj = msg_;
        mHandler.sendMessage(msg);
    }

    private class MatchingMessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String chatMessage = intent.getStringExtra("messageBean");
            Message msg = Message.obtain();
            msg.what = MATCHING_FINISH; // 消息标识
            msg.obj = chatMessage;
            mHandler.sendMessage(msg);
        }
    }

    private void doRegisterReceiver() {
        matchingMessageReceiver = new MatchingMessageReceiver();
        IntentFilter filter = new IntentFilter("gdut.edu.datingforballsports.servicecallback.matchingContent");
        registerReceiver(matchingMessageReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(matchingMessageReceiver);
    }
}
