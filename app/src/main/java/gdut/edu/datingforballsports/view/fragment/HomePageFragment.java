package gdut.edu.datingforballsports.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.domain.User;
import gdut.edu.datingforballsports.presenter.HomePagePresenter;
import gdut.edu.datingforballsports.view.HomePageView;
import gdut.edu.datingforballsports.view.activity.AccountSettingActivity;
import gdut.edu.datingforballsports.view.activity.EditPostActivity;
import gdut.edu.datingforballsports.view.activity.LoginActivity;
import gdut.edu.datingforballsports.view.adapter.CommonAdapter;

public class HomePageFragment extends BaseFragment implements HomePageView {
    private static final int LOAD_USER_MESSAGE_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;

    private View view;

    private HomePagePresenter hPresenter;
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

    public HomePageFragment() {
    }

    public static HomePageFragment newInstance() {
        HomePageFragment fragment = new HomePageFragment();
        return fragment;
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
        //hPresenter.getUserMessage(userId, token);
    }

    private void setView() {
        //设置发布帖子的点击事件
        buttonClick(view.findViewById(R.id.homepage_publish_post), view -> {
            startActivity(new Intent(this.getActivity(), EditPostActivity.class));
        });
        buttonClick(R.id.homepage_profilePhoto, view -> {
            startActivity(new Intent(this.getActivity(), AccountSettingActivity.class));
        });
        buttonClick(R.id.homepage_trends, view -> {
            setTabSelection(0);
        });
        buttonClick(R.id.homepage_collect, view -> {
            setTabSelection(1);
        });

        fm = getChildFragmentManager();
        setTabSelection(0);
    }

    private void setTabSelection(int index) {
        FragmentTransaction ft = fm.beginTransaction();
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
