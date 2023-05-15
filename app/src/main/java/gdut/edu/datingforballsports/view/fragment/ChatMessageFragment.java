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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.ChatMessage;
import gdut.edu.datingforballsports.domain.MessageBean;
import gdut.edu.datingforballsports.presenter.ChatMessagePresenter;
import gdut.edu.datingforballsports.util.TextUtils;
import gdut.edu.datingforballsports.view.ChatMessageView;
import gdut.edu.datingforballsports.view.adapter.CommonAdapter;
import gdut.edu.datingforballsports.view.viewholder.CommonViewHolder;

public class ChatMessageFragment extends BaseFragment implements ChatMessageView {
    private static final int LOAD_MESSAGEBEAN_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;

    private View view;
    private ChatMessagePresenter cPresenter;
    private RecyclerView recyclerView;
    private List<MessageBean> list = new ArrayList<>();
    private CommonAdapter<MessageBean> mCommonAdapter;
    private Intent intent;
    private int userId = -1;
    private String token;
    private String RCmsg;
    public Handler mHandler;

    public ChatMessageFragment() {
    }

    public static ChatMessageFragment newInstance() {
        ChatMessageFragment fragment = new ChatMessageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case LOAD_FAILED:
                        Toast.makeText(getActivity(), RCmsg, Toast.LENGTH_LONG).show();
                        break;
                    case LOAD_MESSAGEBEAN_SUCCEED:
                        Toast.makeText(getActivity(), "加载成功！", Toast.LENGTH_LONG).show();
                        list = TextUtils.castList(message.obj, MessageBean.class);
                        setView();
                        break;
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.message_list, container, false);
        setData();
        return view;
    }

    private void setData() {
        this.cPresenter = new ChatMessagePresenter(this, view.getContext());
        intent = this.getActivity().getIntent();
        userId = intent.getIntExtra("userId", -1);
        token = intent.getStringExtra("token");
        cPresenter.getList(userId, token);
    }

    private void setView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.message_list_forumList);
        recyclerView.setLayoutManager(linearLayoutManager);
        mCommonAdapter = new CommonAdapter<>(list, new CommonAdapter.OnMoreBindDataListener<MessageBean>() {

            @Override
            public int getItemType(int position) {
                return list.get(position).getType();
            }

            @Override
            public void onBindViewHolder(MessageBean model, CommonViewHolder viewHolder, int type, int position) {
                switch (type) {
                    case 1:
                        viewHolder.setImageResource(R.id.message_item_friend_logo, model.getOtherOrChatRoomLogo());
                        viewHolder.setText(R.id.message_item_friend_userName, model.getOtherOrChatRoomName());
                        viewHolder.setText(R.id.message_item_friend_time, model.getPublishTime());
                        viewHolder.setText(R.id.message_item_friend_content, model.getCoverContent());
                        break;
                    case 2:
                        viewHolder.setImageResource(R.id.message_item_friend_logo, model.getOtherOrChatRoomLogo());
                        viewHolder.setText(R.id.message_item_friend_userName, model.getOtherOrChatRoomName());
                        viewHolder.setText(R.id.message_item_friend_time, model.getPublishTime());
                        viewHolder.setText(R.id.message_item_friend_content, model.getCoverContent());
                        break;
                    case 3:
                        viewHolder.setImageResource(R.id.message_require_friend_item_friend_logo, model.getOtherOrChatRoomLogo());
                        viewHolder.setText(R.id.message_item_friend_userName, model.getOtherOrChatRoomName());
                        viewHolder.setText(R.id.message_item_friend_time, model.getPublishTime());
                        viewHolder.setText(R.id.message_item_friend_content, model.getCoverContent());
                        viewHolder.onItemClick((View) viewHolder.getView(R.id.button_require_friend_accept), view -> {
                            mCommonAdapter.remove(viewHolder.getLayoutPosition());
                        });

                        viewHolder.onItemClick((View) viewHolder.getView(R.id.button_require_friend_refuse), view -> {
                            mCommonAdapter.remove(viewHolder.getLayoutPosition());
                        });
                        break;
                }

            }

            @Override
            public int getLayoutId(int type) {
                switch (type) {
                    case 1:
                        return R.layout.message_list;
                    case 2:
                        return R.layout.message_list;
                    case 3:
                        return R.layout.friend_item;
                }
                return R.layout.message_list;
            }
        });
    }

    @Override
    public void onTrendsLoadSuccess(MessageBean list, String RCmsg) {
        Message msg = Message.obtain();
        msg.what = LOAD_MESSAGEBEAN_SUCCEED; // 消息标识
        msg.obj = list;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onLoadFail(String RCmsg) {

    }
}
