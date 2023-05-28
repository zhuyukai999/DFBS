package gdut.edu.datingforballsports.view.activity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.ChatMessage;
import gdut.edu.datingforballsports.domain.MessageBean;
import gdut.edu.datingforballsports.domain.User;
import gdut.edu.datingforballsports.util.GlideEngine;
import gdut.edu.datingforballsports.util.GlideImageEngine;
import gdut.edu.datingforballsports.view.Service.SocketService;
import gdut.edu.datingforballsports.view.fragment.CollectFragment;
import gdut.edu.datingforballsports.view.fragment.TrendsFragment;
import gdut.edu.datingforballsports.view.viewholder.OtherHomePageView;

public class OtherHomePageActivity extends BaseActivity implements OtherHomePageView {
    private static final int LOAD_USER_MESSAGE_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private View view;

    private CircleImageView icon_ci;
    private User user;
    FragmentManager fm;
    private TrendsFragment trendsFragment;
    private CollectFragment collectFragment;
    private Fragment currentFragment;
    private Intent intent;
    private String userName;
    private String icon;
    private int userId = -1;
    private int otherUserId = -1;
    private String otherUserName;
    private String otherUserIcon;
    private String token;
    private String RCmsg;
    public Handler mHandler;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private ActivityResultLauncher<Intent> launcher;
    private FragmentTransaction ft;
    private ServiceConnection serviceConnection;
    private SocketService.JWebSocketClientBinder binder;
    private SocketService socketService;

    public OtherHomePageActivity() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("OtherHomePageActivitydestroy:wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        System.out.println("OtherHomePageActivityCreate1nnnnnnnnnnnnnnnnnnnnnnn");
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {

            }
        };
        setData();
        setView();
    }

    private void setData() {
        gson = new Gson();
        intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        token = intent.getStringExtra("token");
        otherUserId = intent.getIntExtra("otherUserId", -1);
        otherUserName = intent.getStringExtra("otherUserName");
        otherUserIcon = intent.getStringExtra("otherUserIcon");
        icon_ci = findViewById(R.id.homepage_profilePhoto);
        sharedPreferences = getSharedPreferences("user" + userId, MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", null);
        icon = sharedPreferences.getString("icon", null);
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
    }

    private void setView() {
        System.out.println("otherUserIcon:" + otherUserIcon);
        GlideEngine.createGlideEngine().loadNetImage(getApplicationContext(),otherUserIcon,icon_ci);
        ((TextView) (findViewById(R.id.homepage_username))).setText(otherUserName);
        buttonClick(R.id.homepage_profilePhoto, view -> {
            Intent intent = new Intent();
            intent.putExtra("userId", userId);
            intent.putExtra("token", token);
            MessageBean messageBean = new MessageBean(1, otherUserId, otherUserName, otherUserIcon);
            String json = gson.toJson(messageBean);
            intent.putExtra("messageBean", json);
            intent.setClass(this, ChatActivity.class);
            startActivity(intent);
        });
        buttonClick(R.id.homepage_publish_post, view -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
            dialog.setMessage("确认添加好友");
            dialog.setCancelable(false);//设置对话框是否可以取消
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {//确定按钮的点击事件

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        Date date = new Date();
                        JSONArray jsonArray = new JSONArray();
                        JSONObject type = new JSONObject();
                        type.put("type", "chat");
                        ChatMessage chatMessage = new ChatMessage(otherUserId, userName + "请求添加好友", sdf.format(date), 3, userId, userName, userName);
                        String json = gson.toJson(chatMessage);
                        JSONObject chat = new Gson().fromJson(json, JSONObject.class);
                        jsonArray.put(type);
                        jsonArray.put(chat);
                        String msg = gson.toJson(jsonArray);
                        socketService.sendMsg(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {//取消按钮的点击事件
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog.show();//显示对话框
        });
        buttonClick(R.id.homepage_trends, view -> {
            setTabSelection(0);
        });
        buttonClick(R.id.homepage_collect, view -> {
            setTabSelection(1);
        });
        fm = getSupportFragmentManager();
        setTabSelection(0);
    }

    private void setTabSelection(int index) {
        ft = fm.beginTransaction();
        hideFragment(ft);
        switch (index) {
            case 0:
                if (trendsFragment == null) {
                    trendsFragment = TrendsFragment.newInstance();
                    ft.add(R.id.homepage_content, trendsFragment);
                    ft.commit();
                } else {
                    ft.show(trendsFragment);
                    ft.commit();
                }
                currentFragment = trendsFragment;
                break;
            case 1:
                if (collectFragment == null) {
                    collectFragment = CollectFragment.newInstance();
                    ft.add(R.id.homepage_content, collectFragment);
                    ft.commit();
                } else {
                    ft.show(collectFragment);
                    ft.commit();
                }
                currentFragment = collectFragment;
                break;
        }
    }

    private void hideFragment(FragmentTransaction ft) {
        if (ft != null) {
            if (currentFragment != null) {
                ft.hide(currentFragment);
            }
        }
    }

}
