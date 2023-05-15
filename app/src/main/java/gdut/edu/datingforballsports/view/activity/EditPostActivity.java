package gdut.edu.datingforballsports.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.presenter.EditPostPresenter;
import gdut.edu.datingforballsports.util.GPSUtils;
import gdut.edu.datingforballsports.view.EditPostView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EditPostActivity extends BaseActivity implements EditPostView {
    private static final int PUBLISH_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;
    private EditPostPresenter ePresenter;
    private EditText postContent;
    private EditText ballType;
    private Switch locationSwitch;
    private Button confirmPost;

    private Intent intent;
    private int userId = -1;
    private String token;
    private String city_buffer = null;
    private String city = null;
    public Handler mHandler;

    public EditPostActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_post);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case PUBLISH_SUCCEED:
                        Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case LOAD_FAILED:
                        Toast.makeText(getApplicationContext(), String.valueOf(message.obj), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
        setData();
        setView();
    }

    private void setData() {
        postContent = findViewById(R.id.edit_post_et_content);
        ballType = findViewById(R.id.edit_post_et_type);
        locationSwitch = findViewById(R.id.edit_post_switch);
        confirmPost = findViewById(R.id.edit_post_button);
        intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        token = intent.getStringExtra("token");
        this.ePresenter = new EditPostPresenter(this);
    }

    private void setView() {
        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (city_buffer == null) {
                        GPSUtils.getInstance().getProvince(getApplicationContext(), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String responseData = response.body().string();
                                try {
                                    JSONObject jsonObjectALL = new JSONObject(responseData);
                                    JSONObject result = jsonObjectALL.getJSONObject("result");
                                    city = String.valueOf(result.getJSONObject("addressComponent").getJSONObject("city"));
                                    city_buffer = city;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        city = city_buffer;
                    }
                } else {
                    city = null;
                }
            }
        });
        buttonClick(R.id.edit_post_button, view -> {
            ePresenter.uploadPost(userId,token,postContent.getText().toString(), ballType.getText().toString(), city);
        });
    }

    @Override
    public void onPostSuccess() {
        Message msg = Message.obtain();
        msg.what = PUBLISH_SUCCEED; // 消息标识
        mHandler.sendMessage(msg);
    }

    @Override
    public void onPostFails(String RCmsg) {
        Message msg = Message.obtain();
        msg.what = LOAD_FAILED; // 消息标识
        msg.obj = RCmsg;
        mHandler.sendMessage(msg);
    }
}
