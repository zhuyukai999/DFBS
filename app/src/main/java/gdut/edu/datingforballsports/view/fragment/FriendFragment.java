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

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.Friend;
import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.presenter.FriendPresenter;
import gdut.edu.datingforballsports.presenter.TrendsPresenter;
import gdut.edu.datingforballsports.util.TextUtils;
import gdut.edu.datingforballsports.view.FriendView;
import gdut.edu.datingforballsports.view.activity.OtherHomePageActivity;
import gdut.edu.datingforballsports.view.adapter.CommonAdapter;
import gdut.edu.datingforballsports.view.viewholder.CommonViewHolder;

public class FriendFragment extends BaseFragment implements FriendView {
    private static final int LOAD_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;

    private View view;
    private FriendPresenter fPresenter;
    private RecyclerView recyclerView;
    private List<Friend> list = new ArrayList<>();
    private CommonAdapter<Friend> mCommonAdapter;
    private Intent intent;
    private int userId = -1;
    private String token;
    private String RCmsg;
    public Handler mHandler;
    private Gson gson;

    public FriendFragment() {
    }

    public static FriendFragment newInstance() {
        FriendFragment fragment = new FriendFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.friend_list, container, false);
        setData();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("FriendFragment1nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                //改变UI
                switch (message.what) {
                    case LOAD_FAILED:
                        Toast.makeText(getActivity(), RCmsg, Toast.LENGTH_LONG).show();
                        break;
                    case LOAD_SUCCEED:
                        Toast.makeText(getActivity(), "加载成功！", Toast.LENGTH_LONG).show();
                        list = TextUtils.castList(message.obj, Friend.class);
                        setView();
                        break;
                }
            }
        };
        //列表项
    }

    private void setData() {
        fPresenter = new FriendPresenter(this);
        intent = this.getActivity().getIntent();
        userId = intent.getIntExtra("userId", -1);
        token = intent.getStringExtra("token");
        gson = new Gson();
        fPresenter.getList(userId, token);
    }

    private void setView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.friend_list_forumList);
        recyclerView.setLayoutManager(linearLayoutManager);
        mCommonAdapter = new CommonAdapter<>(list, new CommonAdapter.OnBindDataListener<Friend>() {
            @Override
            public void onBindViewHolder(Friend model, CommonViewHolder viewHolder, int type, int position) {
                viewHolder.setImageResource(R.id.friend_item_logo, model.getFriendLogo());
                viewHolder.setText(R.id.friend_item_userName, model.getFriendName());
                viewHolder.onItemClick( viewHolder.itemView, view -> {
                    if(userId!=model.getFriendId()){
                        Intent intent = new Intent();
                        System.out.println("model: " + model);
                        intent.putExtra("userId", userId);
                        intent.putExtra("token", token);
                        intent.putExtra("otherUserId", model.getFriendId());
                        intent.putExtra("otherUserName", model.getFriendName());
                        intent.putExtra("otherUserIcon", model.getFriendLogo());
                        viewHolder.jumpActivity(intent, OtherHomePageActivity.class);
                    }
                });
            }
            @Override
            public int getLayoutId(int type) {
                return R.layout.friend_item;
            }
        });
        recyclerView.setAdapter(mCommonAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onLoadSuccess(List<Friend> list) {
        Message msg = Message.obtain();
        msg.what = LOAD_SUCCEED; // 消息标识
        msg.obj = list;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onLoadFail(String RCmsg) {
        Message msg = Message.obtain();
        msg.what = LOAD_FAILED;
        mHandler.sendMessage(msg);
    }
}
