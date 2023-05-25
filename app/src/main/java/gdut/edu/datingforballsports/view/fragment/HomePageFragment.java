package gdut.edu.datingforballsports.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.domain.User;
import gdut.edu.datingforballsports.presenter.HomePagePresenter;
import gdut.edu.datingforballsports.view.HomePageView;
import gdut.edu.datingforballsports.view.activity.AccountSettingActivity;
import gdut.edu.datingforballsports.view.activity.EditPostActivity;
import gdut.edu.datingforballsports.view.activity.LoginActivity;
import gdut.edu.datingforballsports.view.activity.MainActivity;
import gdut.edu.datingforballsports.view.adapter.CommonAdapter;

public class HomePageFragment extends BaseFragment implements HomePageView {
    private static final int LOAD_USER_MESSAGE_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;

    private View view;

    private HomePagePresenter hPresenter;
    private CircleImageView icon_ci;
    private RecyclerView recyclerView;
    private List<Post> list = new ArrayList<>();
    private User user;
    private CommonAdapter<Post> mCommonAdapter;
    FragmentManager fm;
    private TrendsFragment trendsFragment;
    private CollectFragment collectFragment;
    private Intent intent;
    private int userId = -1;
    private String token;
    private String RCmsg;
    public Handler mHandler;
    private SharedPreferences sharedPreferences;
    private ActivityResultLauncher<Intent> launcher;
    private FragmentTransaction ft;

    public HomePageFragment() {
    }

    public static HomePageFragment newInstance() {
        HomePageFragment fragment = new HomePageFragment();
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("HomePageFragmentdestroy:wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                //改变UI
                switch (message.what) {
                    case LOAD_FAILED:
                        Toast.makeText(getActivity(), RCmsg, Toast.LENGTH_LONG).show();
                        //intent.setClass(getActivity(), LoginActivity.class);
                        break;
                    case LOAD_USER_MESSAGE_SUCCEED:
                        // FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        //transaction.replace(R.id.main_frame_layout, f1);
                        // transaction.add(R.id.main_frame_layout, f4);
                        user = (User) message.obj;
                        break;
                }
            }
        };
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                icon_ci.setImageURI(Uri.parse(sharedPreferences.getString("icon", null)));
                ((TextView) (view.findViewById(R.id.homepage_username))).setText(sharedPreferences.getString("userName", null));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homepage, container, false);
        setData();
        setView();
        return view;
    }

    private void setData() {
        this.hPresenter = new HomePagePresenter(this);
        intent = this.getActivity().getIntent();
        userId = intent.getIntExtra("userId", -1);
        token = intent.getStringExtra("token");
        icon_ci = view.findViewById(R.id.homepage_profilePhoto);
        sharedPreferences = getActivity().getSharedPreferences("user" + userId, getContext().MODE_PRIVATE);
        //hPresenter.getUserMessage(userId, token);
    }

    private void setView() {
        icon_ci.setImageURI(Uri.parse(sharedPreferences.getString("icon", null)));
        ((TextView) (view.findViewById(R.id.homepage_username))).setText(sharedPreferences.getString("userName", null));
        buttonClick(icon_ci, view -> {
            System.out.println("ddddddddddddddddddddddddddddddddd");
            intent.putExtra("userId", userId);
            intent.putExtra("token", token);
            intent.setClass(this.getActivity(), AccountSettingActivity.class);
            launcher.launch(intent);
        });
        buttonClick(view.findViewById(R.id.homepage_publish_post), view -> {
            intent.putExtra("userId", userId);
            intent.putExtra("token", token);
            intent.setClass(this.getActivity(), EditPostActivity.class);
            startActivity(intent);
        });
        buttonClick(view.findViewById(R.id.homepage_trends), view -> {
            setTabSelection(0);
        });
        buttonClick(view.findViewById(R.id.homepage_collect), view -> {
            setTabSelection(1);
        });

        fm = getChildFragmentManager();
        ft = fm.beginTransaction();
        setTabSelection(0);
    }

    private void setTabSelection(int index) {
        hideFragment(ft);
        switch (index) {
            case 0:
                if (trendsFragment == null) {
                    trendsFragment = TrendsFragment.newInstance();
                    ft.add(R.id.homepage_content, trendsFragment);
                } else {
                    ft.show(trendsFragment);
                }
                break;
            case 1:
                if (collectFragment == null) {
                    collectFragment = CollectFragment.newInstance();
                    ft.add(R.id.homepage_content, collectFragment);
                } else {
                    ft.show(collectFragment);
                }
                break;
        }
    }

    private void hideFragment(FragmentTransaction ft) {
        if (trendsFragment != null) {
            ft.hide(trendsFragment);
        }
        if (collectFragment != null) {
            ft.hide(collectFragment);
        }
    }


    @Override
    public void onLoadUserMessageSuccess(User user) {
        Message msg = Message.obtain();
        msg.what = LOAD_USER_MESSAGE_SUCCEED; // 消息标识
        msg.obj = user;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onLoadFails(String RCmsg) {
        this.RCmsg = RCmsg;
    }

}
