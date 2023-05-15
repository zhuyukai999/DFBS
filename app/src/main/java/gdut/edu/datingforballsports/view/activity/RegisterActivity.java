package gdut.edu.datingforballsports.view.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.presenter.RegisterPresenter;
import gdut.edu.datingforballsports.util.GlideEngine;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.RegisterView;
import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;


public class RegisterActivity extends BaseActivity implements RegisterView {
    private static final int REGISTER_SUCCEED = 1;
    private static final int CALLBACK_FAILED = 2;
    private RegisterPresenter rPresenter;
    private CircleImageView icon_ci;
    private String icon_uri;
    private Button icon_select;
    private EditText userName_et;
    private EditText password_et;
    private EditText passwordAG_et;
    private Button birthday_sc;
    private EditText email_et;
    private EditText phoneNumber_et;

    public Handler mHandler;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        icon_ci = (CircleImageView) findViewById(R.id.iv_personal_icon);
        icon_select = (Button) findViewById(R.id.btn_change);
        userName_et = (EditText) findViewById(R.id.enterUserName_register_1);
        password_et = (EditText) findViewById(R.id.enterPassword_register_2);
        passwordAG_et = (EditText) findViewById(R.id.enterPasswordAG_register_3);
        birthday_sc = (Button) findViewById(R.id.selectBirthday_register_1);
        email_et = (EditText) findViewById(R.id.enterEmail_register_4);
        phoneNumber_et = (EditText) findViewById(R.id.enterPhoneNumber_register_5);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case REGISTER_SUCCEED:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("userId", message.arg1);
                        intent.putExtra("token", (String) message.obj);
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user" + message.arg1, MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString("userName", getUserName());
                        edit.putString("password", getPassword());
                        edit.putInt("userId", message.arg1);
                        edit.putString("token", (String) message.obj);
                        edit.putString("icon", saveIcon(message.arg1));
                        edit.commit();
                    case CALLBACK_FAILED:
                }
            }
        };
        setData();
        setView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    icon_uri = data.getStringExtra("icon_uri");
                    icon_ci.setImageURI(Uri.parse(icon_uri));
            }
        }
    }

    private void setData() {
        this.rPresenter = new RegisterPresenter(this);
    }

    private void setView() {
        buttonClick(R.id.btn_change, view -> {
            PictureSelector.create(this)
                    .openSystemGallery(SelectMimeType.ofImage())
                    .setCompressEngine(new CompressFileEngine() {
                        @Override
                        public void onStartCompress(Context context, ArrayList<Uri> source, OnKeyValueResultCallbackListener call) {
                            Luban.with(context).load(source).ignoreBy(100).setCompressListener(new OnNewCompressListener() {
                                @Override
                                public void onStart() {
                                }

                                @Override
                                public void onSuccess(String source, File compressFile) {
                                    if (call != null) {
                                        call.onCallback(source, compressFile.getAbsolutePath());
                                    }
                                }

                                @Override
                                public void onError(String source, Throwable e) {
                                    if (call != null) {
                                        call.onCallback(source, null);
                                    }
                                }
                            }).launch();
                        }
                    })
                    .forSystemResult(new OnResultCallbackListener<LocalMedia>() {
                        @Override
                        public void onResult(ArrayList<LocalMedia> result) {
                            icon_uri = result.get(0).getCompressPath();
                            icon_ci.setImageURI(Uri.parse(icon_uri));
                            /*Intent intent = new Intent();
                            intent.putExtra("icon_uri", icon_uri);
                            setResult(PictureConfig.CHOOSE_REQUEST, intent);*/
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
        });

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

    private String saveIcon(int userId) {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
            }
        });
        String storePath = this.getFilesDir().getAbsolutePath() + "/user" + userId + "/icon" + "/user" + userId + ".png";
        GlideEngine.createGlideEngine().saveImage(this, icon_uri, storePath);
        return storePath;
    }

    @Override
    public String getImagePath() {
        return icon_uri;
    }

    @Override
    public String getUserName() {
        return userName_et.getText().toString();
    }

    @Override
    public String getPassword() {
        return password_et.getText().toString();
    }

    @Override
    public String getPasswordAG() {
        return passwordAG_et.getText().toString();
    }

    @Override
    public String getSex() {
        int sex_id = ((RadioGroup) findViewById(R.id.selectSex_Register_1)).getCheckedRadioButtonId();
        String sex = sex_id == -1 ? "" : ((RadioButton) findViewById(sex_id)).getText().toString();
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
        return birthday_sc.getText().toString();
    }

    @Override
    public String getEmail() {
        return email_et.getText().toString();
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber_et.getText().toString();
    }

    @Override
    public void onRegisterSuccess(String token, int userId) {
        Message msg = Message.obtain();
        msg.what = REGISTER_SUCCEED;
        msg.obj = token;
        msg.arg1 = userId;
        this.token = token;
        mHandler.sendMessage(msg);
        new AlertDialog.Builder(this).setTitle("真的").setMessage("注册成功").show();
    }

    @Override
    public void onRegisterFails() {
        Message msg = Message.obtain();
        msg.what = CALLBACK_FAILED; // 消息标识
        mHandler.sendMessage(msg);
        new AlertDialog.Builder(this).setTitle("有问题").setMessage("有问题！").setPositiveButton("确定", null).show();
    }

    @Override
    public void EmailFalse() {
        Toast.makeText(getApplicationContext(), "邮箱格式错误", Toast.LENGTH_SHORT).show();
    }
}
