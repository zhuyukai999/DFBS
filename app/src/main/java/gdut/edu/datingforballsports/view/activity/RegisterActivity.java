package gdut.edu.datingforballsports.view.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;

import java.util.Calendar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.presenter.RegisterPresenter;
import gdut.edu.datingforballsports.view.RegisterView;


public class RegisterActivity extends BaseActivity implements RegisterView {
    private RegisterPresenter rPresenter;
    private EditText userName_et;
    private EditText password_et;
    private EditText passwordAG_et;
    private Button birthday_sc;
    private EditText email_et;
    private EditText phoneNumber_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        userName_et = (EditText) findViewById(R.id.enterUserName_register_1);
        password_et = (EditText) findViewById(R.id.enterPassword_register_2);
        passwordAG_et = (EditText) findViewById(R.id.enterPasswordAG_register_3);
        birthday_sc = (Button) findViewById(R.id.selectBirthday_register_1);
        email_et = (EditText) findViewById(R.id.enterEmail_register_4);
        phoneNumber_et = (EditText) findViewById(R.id.enterPhoneNumber_register_5);
        setData();
        setView();
    }

    private void setData() {
        this.rPresenter = new RegisterPresenter(this);
    }

    private void setView() {
        buttonClick(R.id.button_register_register_2, view -> {
            rPresenter.register();
        });

        buttonClick(R.id.button_clear_register_3, view -> {
            userName_et.setText("");
            password_et.setText("");
            passwordAG_et.setText("");
            birthday_sc.setText("选择");
            email_et.setText("");
            phoneNumber_et.setText("");
        });
    }

    @Override
    public String getUserName() {
        return String.valueOf(userName_et.getText());
    }

    @Override
    public String getPassword() {
        return String.valueOf(password_et.getText());
    }

    @Override
    public String getPasswordAG() {
        return String.valueOf(passwordAG_et.getText());
    }

    @Override
    public String getSex() {
        int sex_id = ((RadioGroup) findViewById(R.id.selectSex_Register_1)).getCheckedRadioButtonId();
        String sex = sex_id == -1 ? "" : String.valueOf(((RadioButton) findViewById(sex_id)).getText());
        return sex;
    }

    @Override
    public String getBirthday() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, day) -> {
            Calendar birth = Calendar.getInstance();
            Date date = birth.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(date);
            birthday_sc.setText(format);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        buttonClick(R.id.selectBirthday_register_1, view -> {
            dialog.show();
        });
        return String.valueOf(birthday_sc.getText());
    }

    @Override
    public String getEmail() {
        return String.valueOf(email_et.getText());
    }

    @Override
    public String getPhoneNumber() {
        return String.valueOf(phoneNumber_et.getText());
    }

    @Override
    public void onRegisterSuccess() {
        new AlertDialog.Builder(this).setTitle("真的").setMessage("注册成功").show();
    }

    @Override
    public void onRegisterFails() {
        new AlertDialog.Builder(this).setTitle("有问题").setMessage("有问题！").setPositiveButton("确定", null).show();
    }

    @Override
    public void EmailFalse() {
        Toast.makeText(this, "邮箱格式错误", Toast.LENGTH_SHORT).show();
    }
}
