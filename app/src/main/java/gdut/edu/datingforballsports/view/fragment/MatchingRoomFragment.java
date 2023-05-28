package gdut.edu.datingforballsports.view.fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.ChatMessage;
import gdut.edu.datingforballsports.view.Service.SocketService;

public class MatchingRoomFragment extends BaseFragment {
    private static final int LOAD_MATCHINGMESSAGE_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;
    private MatchingReceiver matchingReceiver;

    private View view;

    private Intent intent;
    private int userId = -1;
    private String token;
    public Handler mHandler;
    private ServiceConnection serviceConnection;
    private SocketService.JWebSocketClientBinder binder;
    private SocketService socketService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.matching, container, false);
        setData();
        setView();
        return view;
    }

    private void setData() {
        intent = this.getActivity().getIntent();
        userId = intent.getIntExtra("userId", -1);
        token = intent.getStringExtra("token");
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

    }

    private class MatchingReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ChatMessage chatMessage = (ChatMessage) intent.getSerializableExtra("chatMessage");
            Message msg = Message.obtain();
            msg.what = LOAD_MATCHINGMESSAGE_SUCCEED; // 消息标识
            msg.obj = chatMessage;
            mHandler.sendMessage(msg);
        }
    }

    private void doRegisterReceiver() {
        matchingReceiver = new MatchingReceiver();
        IntentFilter filter = new IntentFilter("gdut.edu.datingforballsports.servicecallback.chatContent");
        getActivity().registerReceiver(matchingReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(matchingReceiver);
    }
}
