package gdut.edu.datingforballsports.view.activity;

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
import android.widget.Toast;

import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.presenter.AccountSettingPresenter;
import gdut.edu.datingforballsports.util.GlideEngine;
import gdut.edu.datingforballsports.util.TextUtils;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.AccountSettingView;
import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;

public class AccountSettingActivity extends BaseActivity implements AccountSettingView {
    private static final int SETTING_SUCCEED = 1;
    private static final int SETTING_FAILED = 2;
    private Intent intent;
    private AccountSettingPresenter aPresenter;
    private String icon_uri;
    private CircleImageView icon_ci;
    private Button icon_select;
    private EditText userName_et;
    private Button confirm_bt;
    private Button exit_bt;
    private Button logout_bt;

    private int userId = -1;
    private String token;
    public Handler mHandler;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_setting);
        System.out.println("AccountSettingActivityonCreate:innnnnnnnnnnnnnnnnnnnnnnnnnn");
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case SETTING_SUCCEED:
                        intent.putExtra("userId", userId);
                        intent.putExtra("token", token);
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString("userName", userName_et.getText().toString());
                        edit.putString("icon", saveIcon(userId));
                        edit.commit();
                        Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case SETTING_FAILED:
                        Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        setData();
        setView();
    }

    private void setData() {
        System.out.println("11111111111111111111111111111111111");
        icon_ci = findViewById(R.id.account_setting_personal_icon);
        icon_select = findViewById(R.id.account_setting_change_icon);
        userName_et = findViewById(R.id.account_setting_edit_username);
        confirm_bt = findViewById(R.id.account_setting_confirm);
        exit_bt = findViewById(R.id.account_setting_exit);
        logout_bt = findViewById(R.id.account_setting_logout);
        intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        token = intent.getStringExtra("token");
        sharedPreferences = getApplicationContext().getSharedPreferences("user" + userId, MODE_PRIVATE);
        icon_uri = sharedPreferences.getString("icon", null);
        icon_ci.setImageURI(Uri.parse(icon_uri));
        this.aPresenter = new AccountSettingPresenter(this, getApplicationContext());
    }

    private void setView() {
        System.out.println("2222222222222222222222222222222222");
        buttonClick(R.id.account_setting_change_icon, view -> {
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
                            String imageSrc = result.get(0).getCompressPath();
                            icon_uri = getApplicationContext().getFilesDir().getAbsolutePath() + "/user" + "/icon" + "/user" + ".png";
                            File fileSrc = new File(imageSrc);
                            File fileDes = new File(icon_uri);
                            TextUtils.copyDir(fileSrc, fileDes);
                            icon_ci.setImageURI(Uri.parse(imageSrc));
                            /*Intent intent = new Intent();
                            intent.putExtra("icon_uri", icon_uri);
                            setResult(PictureConfig.CHOOSE_REQUEST, intent);*/
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
        });

        buttonClick(R.id.account_setting_confirm, view -> {
            aPresenter.modify(userId, token, userName_et.getText().toString(), icon_uri);
        });

        buttonClick(R.id.account_setting_exit, view -> {
            intent.setClass(getApplicationContext(), LoginActivity.class);
            intent.putExtra("userId", userId);
            intent.putExtra("token", token);
            startActivity(intent);
        });

        buttonClick(R.id.account_setting_logout, view -> {

        });
        System.out.println("33333333333333333333333333333333333333");
    }

    private String saveIcon(int userId) {
        String storePath = getApplicationContext().getFilesDir().getAbsolutePath() + "/user" + userId + "/icon" + "/user" + userId + ".png";
        File file = new File(icon_uri);
        File file1 = new File(storePath);
        TextUtils.copyDir(file, file1);
        //GlideEngine.createGlideEngine().saveImage(this, icon_uri, storePath);
        return storePath;
    }

    @Override
    public void onSettingSuccess() {
        Message msg = Message.obtain();
        msg.what = SETTING_SUCCEED; // 消息标识
        mHandler.sendMessage(msg);
    }

    @Override
    public void onSettingFails(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
