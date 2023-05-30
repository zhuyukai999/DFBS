package gdut.edu.datingforballsports.view.fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.dao.Impl.MessageDaoImpl;
import gdut.edu.datingforballsports.dao.MessageDao;
import gdut.edu.datingforballsports.domain.ChatMessage;
import gdut.edu.datingforballsports.domain.MessageBean;
import gdut.edu.datingforballsports.domain.User;
import gdut.edu.datingforballsports.presenter.ChatMessagePresenter;
import gdut.edu.datingforballsports.util.TextUtils;
import gdut.edu.datingforballsports.view.ChatMessageView;
import gdut.edu.datingforballsports.view.Service.SocketService;
import gdut.edu.datingforballsports.view.activity.ChatActivity;
import gdut.edu.datingforballsports.view.activity.PostDetailsActivity;
import gdut.edu.datingforballsports.view.adapter.CommonAdapter;
import gdut.edu.datingforballsports.view.viewholder.CommonViewHolder;

public class ChatMessageFragment extends BaseFragment implements ChatMessageView {
    private static final int LOAD_MESSAGEBEAN_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;
    private static final int LOAD_ONLINEMESSAGE_SUCCEED = 3;
    private ChatMessageReceiver chatMessageReceiver;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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
    private Gson gson;
    MessageDao messageDao;
    private ServiceConnection serviceConnection;
    private SocketService.JWebSocketClientBinder binder;
    private SocketService socketService;

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
                    case LOAD_ONLINEMESSAGE_SUCCEED:
                        ChatMessage chatMessage = (ChatMessage) message.obj;
                        switch (chatMessage.getType()) {
                            case 1:
                                if (messageDao.getMessageBeanCountByIdAndType(1, chatMessage.getOtherOrChatRoomId()) == 1) {
                                    mCommonAdapter.insert(new MessageBean(chatMessage.getType(), chatMessage.getOtherOrChatRoomId(),
                                            chatMessage.getOtherOrChatRoomName(), chatMessage.getOtherOrChatRoomLogo(),
                                            chatMessage.getPublishTime(), chatMessage.getContent()), list.size());
                                }
                                break;
                            case 2:
                                if (messageDao.getMessageBeanCountByIdAndType(2, chatMessage.getOtherOrChatRoomId()) == 1) {
                                    mCommonAdapter.insert(new MessageBean(chatMessage.getType(), chatMessage.getOtherOrChatRoomId(),
                                            chatMessage.getOtherOrChatRoomName(), chatMessage.getOtherOrChatRoomLogo(),
                                            chatMessage.getPublishTime(), chatMessage.getContent()), list.size());
                                }
                                break;
                            case 3:
                                mCommonAdapter.insert(new MessageBean(chatMessage.getType(), chatMessage.getOtherOrChatRoomId(),
                                        chatMessage.getOtherOrChatRoomName(), chatMessage.getOtherOrChatRoomLogo(),
                                        chatMessage.getPublishTime(), "请求添加为好友"), list.size());
                                mCommonAdapter.notifyItemChanged(list.size());
                                recyclerView.notifyAll();
                                break;
                        }
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
        gson = new Gson();
        this.cPresenter = new ChatMessagePresenter(this, view.getContext());
        intent = this.getActivity().getIntent();
        messageDao = new MessageDaoImpl(getActivity().getApplicationContext());
        userId = intent.getIntExtra("userId", -1);
        token = intent.getStringExtra("token");
        cPresenter.getList();
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
        doRegisterReceiver();
    }

    private void setView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.message_list_forumList);
        recyclerView.setLayoutManager(linearLayoutManager);
        mCommonAdapter = new CommonAdapter<>(list, new CommonAdapter.OnMoreBindDataListener<MessageBean>() {
            @Override
            public void onBindViewHolder(MessageBean model, CommonViewHolder viewHolder, int type, int position) {
                switch (type) {
                    case 1:
                        viewHolder.setImageResource(R.id.message_item_friend_logo, model.getOtherOrChatRoomLogo());
                        viewHolder.setText(R.id.message_item_friend_userName, model.getOtherOrChatRoomName());
                        viewHolder.setText(R.id.message_item_friend_time, model.getPublishTime());
                        viewHolder.setText(R.id.message_item_friend_content, model.getCoverContent());
                        viewHolder.onItemClick(viewHolder.itemView, view -> {
                            Intent intent = new Intent();
                            intent.putExtra("userId", userId);
                            intent.putExtra("token", token);
                            String json = gson.toJson(model);
                            intent.putExtra("messageBean", json);
                            viewHolder.jumpActivity(intent, ChatActivity.class);
                        });
                        break;
                    case 2:
                        viewHolder.setImageResource(R.id.message_item_friend_logo, model.getOtherOrChatRoomLogo());
                        viewHolder.setText(R.id.message_item_friend_userName, model.getOtherOrChatRoomName());
                        viewHolder.setText(R.id.message_item_friend_time, model.getPublishTime());
                        viewHolder.setText(R.id.message_item_friend_content, model.getCoverContent());
                        viewHolder.onItemClick(viewHolder.itemView, view -> {

                        });
                        break;
                    case 3:
                        viewHolder.setImageResource(R.id.message_require_friend_item_friend_logo, model.getOtherOrChatRoomLogo());
                        viewHolder.setText(R.id.message_item_friend_userName, model.getOtherOrChatRoomName());
                        viewHolder.setText(R.id.message_item_friend_time, model.getPublishTime());
                        viewHolder.setText(R.id.message_item_friend_content, model.getCoverContent());
                        viewHolder.onItemClick((View) viewHolder.getView(R.id.button_require_friend_accept), view -> {
                            mCommonAdapter.remove(viewHolder.getLayoutPosition());
                            try {
                                JSONArray jsonArray = new JSONArray();
                                MessageBean messageBean = new MessageBean(4, model.getOtherOrChatRoomId());
                                String json = gson.toJson(messageBean);
                                JSONObject agree = new Gson().fromJson(json, JSONObject.class);
                                jsonArray.put(0, "agree");
                                jsonArray.put(1, agree);
                                String msg = gson.toJson(jsonArray);
                                socketService.sendMsg(msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        });

                        viewHolder.onItemClick((View) viewHolder.getView(R.id.button_require_friend_refuse), view -> {
                            mCommonAdapter.remove(viewHolder.getLayoutPosition());
                        });
                        break;
                }

            }

            @Override
            public int getItemType(int position) {
                return list.get(position).getType();
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
        recyclerView.setAdapter(mCommonAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private class ChatMessageReceiver extends BroadcastReceiver {

        public ChatMessageReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            ChatMessage chatMessage = (ChatMessage) intent.getSerializableExtra("chatMessage");
            Message msg = Message.obtain();
            msg.what = LOAD_ONLINEMESSAGE_SUCCEED; // 消息标识
            msg.obj = chatMessage;
            mHandler.sendMessage(msg);
        }
    }

    private void doRegisterReceiver() {
        chatMessageReceiver = new ChatMessageReceiver();
        IntentFilter filter = new IntentFilter("gdut.edu.datingforballsports.servicecallback.chatContent");
        getActivity().getApplicationContext().registerReceiver(chatMessageReceiver, filter);
    }

    @Override
    public void onLoadMessageBeanSuccess(List<MessageBean> list, String RCmsg) {
        Message msg = Message.obtain();
        msg.what = LOAD_MESSAGEBEAN_SUCCEED; // 消息标识
        msg.obj = list;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onLoadFail(String RCmsg) {

    }
}
