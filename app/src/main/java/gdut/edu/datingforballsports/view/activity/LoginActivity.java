package gdut.edu.datingforballsports.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.EditText;
import android.widget.Toast;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.presenter.LoginPresenter;
import gdut.edu.datingforballsports.util.SharedPreferenceUtils;
import gdut.edu.datingforballsports.view.LoginView;


public class LoginActivity extends BaseActivity implements LoginView {
    private static final int LOGIN_SUCCEED =1;
    private static final int CALLBACK_FAILED=2;
    private LoginPresenter lPresenter;
    public Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                //改变UI
                switch (message.what){
                    case CALLBACK_FAILED:
                        Toast.makeText(getApplicationContext(), "登陆失败！", Toast.LENGTH_LONG).show();
                        break;
                    case LOGIN_SUCCEED:
                        Toast.makeText(getApplicationContext(), "登陆成功！", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        // FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        //transaction.replace(R.id.main_frame_layout, f1);
                        // transaction.add(R.id.main_frame_layout, f4);
                        intent.setClass(getApplicationContext(), ForumListActivity.class);
                        intent.putExtra("userId", message.arg1);
                        intent.putExtra("token", String.valueOf(message.obj));
                        //存储token
                        SharedPreferenceUtils.putString(getApplicationContext(), String.valueOf(message.arg1), String.valueOf(message.obj));
                        SharedPreferenceUtils.putString(getApplicationContext(), "main", String.valueOf(message.arg1));
                        startActivity(intent);
                        break;
                }
            }
        };
        setData();
        setView();

    }

    private void setData() {
        this.lPresenter = new LoginPresenter(this);
    }

    private void setView() {
        buttonClick(R.id.buttonLogin_login_1, view -> {
            lPresenter.login();
        });
        buttonClick(R.id.buttonRegister_login_2, view -> {
            startActivity(new Intent(this, HomePageActivity.class));
        });
    }

    @Override
    public String getUserName() {
        EditText username_et = (EditText) findViewById(R.id.enterUsername_login_1);
        return String.valueOf(username_et.getText());
    }

    @Override
    public String getPassword() {
        EditText password_et = (EditText) findViewById(R.id.enterPassword_login_2);
        return String.valueOf(password_et.getText());
    }

    @Override
    public void onLoginSuccess(int userId, String token) {
        Message msg = Message.obtain();
        msg.what = LOGIN_SUCCEED; // 消息标识
        msg.arg1 = userId;
        msg.obj = token;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onLoginFails() {
        Message msg = Message.obtain();
        msg.what = CALLBACK_FAILED; // 消息标识
        mHandler.sendMessage(msg);
    }
}
