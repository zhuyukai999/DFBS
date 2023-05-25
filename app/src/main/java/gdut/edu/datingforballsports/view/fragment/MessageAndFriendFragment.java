package gdut.edu.datingforballsports.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.Friend;
import gdut.edu.datingforballsports.domain.User;
import gdut.edu.datingforballsports.presenter.MessagePresenter;
import gdut.edu.datingforballsports.view.MessageView;
import gdut.edu.datingforballsports.view.adapter.CommonAdapter;

public class MessageAndFriendFragment extends BaseFragment implements MessageView {
    private static final int LOAD_MESSAGE_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;

    private View view;

    private MessagePresenter mPresenter;
    private RecyclerView recyclerView;
    private List<Friend> list = new ArrayList<>();
    private Friend friend;
    private CommonAdapter<Friend> mCommonAdapter;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ChatMessageFragment chatMessageFragment;
    private FriendFragment friendFragment;
    private Intent intent;
    private int userId = -1;
    private String token;
    private String RCmsg;
    public Handler mHandler;
    private Bundle savedInstanceState;

    public MessageAndFriendFragment() {
    }

    public static MessageAndFriendFragment newInstance() {
        MessageAndFriendFragment fragment = new MessageAndFriendFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
            }
        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.message_and_friend_list, container, false);
        setData();
        setView();
        return view;
    }

    private void setData() {
        this.mPresenter = new MessagePresenter(this);
        intent = this.getActivity().getIntent();
        userId = intent.getIntExtra("userId", -1);
        token = intent.getStringExtra("token");
    }

    private void setView() {
        buttonClick(view.findViewById(R.id.message_list_buttonT), view -> {
            setTabSelection(0);
        });
        buttonClick(view.findViewById(R.id.friend_list_buttonT), view -> {
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
                if (savedInstanceState == null && chatMessageFragment == null) {
                    chatMessageFragment = ChatMessageFragment.newInstance();
                    ft.add(R.id.MessageAndFriend_list_forumList, chatMessageFragment);
                    ft.commit();
                } else {
                    ft.show(chatMessageFragment);
                }
                break;
            case 1:
                if (savedInstanceState == null && friendFragment == null) {
                    friendFragment = FriendFragment.newInstance();
                    ft.add(R.id.MessageAndFriend_list_forumList, friendFragment);
                    ft.commit();
                } else {
                    ft.show(friendFragment);
                }
                break;
        }
    }

    private void hideFragment(FragmentTransaction ft) {
        if (chatMessageFragment != null) {
            ft.hide(chatMessageFragment);
        }
        if (friendFragment != null) {
            ft.hide(friendFragment);
        }
    }

    @Override
    public void onLoadSuccess(User user) {

    }

    @Override
    public void onLoadFails(String RCmsg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*if (chatMessageFragment != null) {
            ft.detach(chatMessageFragment);
        }
        if (friendFragment != null) {
            ft.detach(friendFragment);
        }*/
        System.out.println("MessageAndFriendFragmentdestroywwwwwwwwwwwwwwwwwwwwwwwwww");
    }
}