package gdut.edu.datingforballsports.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.User;
import gdut.edu.datingforballsports.presenter.CoverPresenter;
import gdut.edu.datingforballsports.presenter.LoginPresenter;
import gdut.edu.datingforballsports.util.SharedPreferenceUtils;
import gdut.edu.datingforballsports.view.CoverView;


public class CoverActivity extends BaseActivity implements CoverView {
    private static final int LOAD_SUCCEED =1;
    private static final int LOAD_FAILED=2;
    private int userId = -1;
    private String token;
    private String RCmsg ;
    private CoverPresenter lPresenter;
    public Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cover);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                Intent intent = new Intent();
                //改变UI
                switch (message.what) {
                    case LOAD_FAILED:
                        Toast.makeText(getApplicationContext(), RCmsg, Toast.LENGTH_LONG).show();
                        intent.setClass(getApplicationContext(), LoginActivity.class);
                        break;
                    case LOAD_SUCCEED:
                        Toast.makeText(getApplicationContext(), RCmsg, Toast.LENGTH_LONG).show();
                        // FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        //transaction.replace(R.id.main_frame_layout, f1);
                        // transaction.add(R.id.main_frame_layout, f4);
                        intent.setClass(getApplicationContext(), MainActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("token", String.valueOf(token));
                        //存储token
                        startActivity(intent);
                        break;
                }
            }
        };
        getData();

        setView();
    }

    private void getData() {
        String mainUserId = SharedPreferenceUtils.getString(getApplicationContext(), "main", null);
        if(mainUserId == null){
            Message msg = Message.obtain();
            msg.what = LOAD_FAILED; // 消息标识
            mHandler.sendMessage(msg);
            return;
        }
        token = SharedPreferenceUtils.getString(getApplicationContext(), String.valueOf(mainUserId), null);
        if(token == null){
            Message msg = Message.obtain();
            msg.what = LOAD_FAILED; // 消息标识
            mHandler.sendMessage(msg);
            return;
        }
        userId = Integer.parseInt(mainUserId);
        this.lPresenter = new CoverPresenter(this);
        lPresenter.getUserMessage(mainUserId,token);
    }

    private void setView() {

    }


    @Override
    public void onLoginSuccess(String RCmsg) {
        this.RCmsg = RCmsg;
        Message msg = Message.obtain();
        msg.what = LOAD_SUCCEED; // 消息标识
        mHandler.sendMessage(msg);
    }

    @Override
    public void onLoginFails(String RCmsg) {
        this.RCmsg = RCmsg;
        Message msg = Message.obtain();
        msg.what = LOAD_FAILED; // 消息标识
        mHandler.sendMessage(msg);
    }
}
