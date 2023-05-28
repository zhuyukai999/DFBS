package gdut.edu.datingforballsports.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.presenter.LoginPresenter;
import gdut.edu.datingforballsports.util.GlideEngine;
import gdut.edu.datingforballsports.util.SharedPreferenceUtils;
import gdut.edu.datingforballsports.util.TextUtils;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.LoginView;


public class LoginActivity extends BaseActivity implements LoginView {
    private static final int LOGIN_SUCCEED = 1;
    private static final int CALLBACK_FAILED = 2;
    private String icon_uri;
    private LoginPresenter lPresenter;
    private Intent intent;
    public Handler mHandler;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                //改变UI
                switch (message.what) {
                    case CALLBACK_FAILED:
                        Toast.makeText(getApplicationContext(), "登陆失败！", Toast.LENGTH_LONG).show();
                        break;
                    case LOGIN_SUCCEED:
                        Toast.makeText(getApplicationContext(), "登陆成功！", Toast.LENGTH_LONG).show();
                        intent = getIntent();
                        // FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        //transaction.replace(R.id.main_frame_layout, f1);
                        // transaction.add(R.id.main_frame_layout, f4);
                        List<String> list = TextUtils.castList(message.obj, String.class);
                        intent.setClass(getApplicationContext(), MainActivity.class);
                        intent.putExtra("userId", message.arg1);
                        intent.putExtra("token", list.get(0));
                        icon_uri = list.get(1);
                        sharedPreferences = getApplicationContext().getSharedPreferences("user" + message.arg1, MODE_PRIVATE);
                        edit = sharedPreferences.edit();
                        edit.putString("userName", getUserName());
                        edit.putString("password", getPassword());
                        edit.putInt("userId", message.arg1);
                        edit.putString("token", list.get(0));
                        edit.putString("icon", saveIcon(message.arg1));
                        edit.commit();
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        };
        setData();
        setView();

    }

    private void setData() {
        this.lPresenter = new LoginPresenter(this, getApplicationContext());
    }

    private void setView() {
        buttonClick(R.id.buttonLogin_login_1, view -> {
            lPresenter.login();
        });
        buttonClick(R.id.buttonRegister_login_2, view -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private String saveIcon(int userId) {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
            }
        });
        String storePath = this.getFilesDir().getAbsolutePath() + "/user" + userId + "/icon" + "/user" + userId + ".png";
        System.out.println("icon_uri:" + icon_uri);
        GlideEngine.createGlideEngine().saveImage(this, icon_uri, storePath);
        return storePath;
    }

    @Override
    public String getUserName() {
        EditText username_et = (EditText) findViewById(R.id.enterUsername_login_1);
        return username_et.getText().toString();
    }

    @Override
    public String getPassword() {
        EditText password_et = (EditText) findViewById(R.id.enterPassword_login_2);
        return password_et.getText().toString();
    }

    @Override
    public void onLoginSuccess(int userId, String token, String icon, String userName) {
        Message msg = Message.obtain();
        List<String> list = new ArrayList<>();
        list.add(token);
        list.add(icon);
        list.add(userName);
        msg.what = LOGIN_SUCCEED; // 消息标识
        msg.arg1 = userId;
        msg.obj = list;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onLoginFails() {
        Message msg = Message.obtain();
        msg.what = CALLBACK_FAILED; // 消息标识
        mHandler.sendMessage(msg);
    }
}
