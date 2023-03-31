package gdut.edu.datingforballsports.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.presenter.LoginPresenter;
import gdut.edu.datingforballsports.view.LoginView;


public class LoginActivity extends BaseActivity implements LoginView {
    private LoginPresenter lPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
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
            startActivity(new Intent(this, RegisterActivity.class));
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
        return String.valueOf(password_et);
    }

    @Override
    public void onLoginSuccess(int userId) {
        Toast.makeText(getApplicationContext(), "登陆成功！", Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.setClass(this,ForumListActivity.class);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }

    @Override
    public void onLoginFails() {
        Toast.makeText(getApplicationContext(), "登陆失败！", Toast.LENGTH_LONG).show();
    }
}
