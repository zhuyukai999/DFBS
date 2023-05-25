package gdut.edu.datingforballsports.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.ChatMessage;
import gdut.edu.datingforballsports.domain.CommentDetail;
import gdut.edu.datingforballsports.domain.MessageBean;
import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.model.ChatPresenter;
import gdut.edu.datingforballsports.view.ChatView;
import gdut.edu.datingforballsports.view.adapter.CommentExpandAdapter;
import gdut.edu.datingforballsports.view.adapter.CommonAdapter;
import gdut.edu.datingforballsports.view.fragment.ChatMessageFragment;
import gdut.edu.datingforballsports.view.viewholder.CommonViewHolder;

public class ChatActivity extends BaseActivity implements ChatView {
    private static final int LOAD_HISTORY_CHATMESSAGE_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;
    private static final int LOAD_CHATMESSAGE_SUCCEED = 3;
    private ChatMessageReceiver chatMessageReceiver;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private androidx.appcompat.widget.Toolbar toolbar;
    private TextView bt_comment;
    private RecyclerView recyclerView;
    private List<ChatMessage> list = new ArrayList<>();
    private CommonAdapter<ChatMessage> mCommonAdapter;
    private BottomSheetDialog dialog;

    private ChatPresenter cPresenter;
    private List<ChatMessage> msgList;
    private MessageBean messageBean;
    private int otherId;
    private Intent intent;
    private int userId = -1;
    private String token;
    private String icon;
    private String userName = null;
    private String RCmsg;
    public Handler mHandler;
    private Gson gson;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case LOAD_HISTORY_CHATMESSAGE_SUCCEED:
                        list = gdut.edu.datingforballsports.util.TextUtils.castList(message.obj, ChatMessage.class);
                        break;
                    case LOAD_FAILED:
                        break;
                    case LOAD_CHATMESSAGE_SUCCEED:
                        ChatMessage chatMessage = (ChatMessage) message.obj;
                        if (chatMessage.getType() == 1 && chatMessage.getOtherOrChatRoomId() == otherId) {
                            mCommonAdapter.insert(chatMessage, list.size());
                        }
                        break;
                }
            }
        };
        setData();
        setView();
    }

    private void setData() {
        intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        token = intent.getStringExtra("token");
        messageBean = (MessageBean) getIntent().getSerializableExtra("messageBean");
        otherId = messageBean.getOtherOrChatRoomId();
        sharedPreferences = getApplicationContext().getSharedPreferences("user" + userId, MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", "~~~");
        icon = sharedPreferences.getString("icon", null);
        cPresenter = new ChatPresenter(this, getApplicationContext());
        cPresenter.getList(userId, token, otherId);
    }

    private void setView() {
        toolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        bt_comment = (TextView) findViewById(R.id.chat_edit_message);
        buttonClick(R.id.chat_edit_message, view -> {
            if (view.getId() == R.id.detail_page_do_comment) {
                showCommentDialog();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.chat_collapsing_toolbar);
        collapsingToolbar.setTitle("详情");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView = findViewById(R.id.msg_recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        mCommonAdapter = new CommonAdapter<>(list, new CommonAdapter.OnMoreBindDataListener<ChatMessage>() {

            @Override
            public void onBindViewHolder(ChatMessage model, CommonViewHolder viewHolder, int type, int position) {
                switch (type) {
                    case 1:
                        viewHolder.setImageResource(R.id.left_layout_chat_msg_logo, model.getOtherOrChatRoomLogo());
                        viewHolder.setText(R.id.left_layout_chat_msg_name, model.getOtherOrChatRoomName());
                        viewHolder.setText(R.id.left_layout_chat_msg_time, model.getPublishTime());
                        viewHolder.setText(R.id.left_msg, model.getContent());
                        break;
                    case 2:
                        viewHolder.setImageResource(R.id.right_layout_chat_msg_logo, model.getOtherOrChatRoomLogo());
                        viewHolder.setText(R.id.right_layout_chat_msg_name, model.getOtherOrChatRoomName());
                        viewHolder.setText(R.id.right_layout_chat_msg_time, model.getPublishTime());
                        viewHolder.setText(R.id.right_msg, model.getContent());
                        break;
                }
            }

            @Override
            public int getItemType(int position) {
                if (list.get(position).getPublisherId() != userId) {
                    return 1;
                } else {
                    return 2;
                }

            }

            @Override
            public int getLayoutId(int type) {
                switch (type) {
                    case 1:
                        return R.id.left_layout_chat_msg_item;
                    case 2:
                        return R.id.right_layout_chat_msg_item;
                }
                return 0;
            }
        });
        recyclerView.setAdapter(mCommonAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    private void showCommentDialog() {
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.a_comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0, 0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(commentContent)) {

                    Date date = new Date();
                    ChatMessage chatMessage = new ChatMessage(userId, commentContent, sdf.format(date), 1, userId, userName, icon, userName, icon);
                    //TODO
                    mCommonAdapter.insert(chatMessage, list.size());
                    cPresenter.storeMessage(chatMessage, otherId);
                    //commentOnWork(commentContent);
                    dialog.dismiss();

                    Toast.makeText(getApplicationContext(), "pub成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "pub内容不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    @Override
    public void onTrendsLoadSuccess(MessageBean list, String RCmsg) {

    }

    @Override
    public void onLoadFail(String RCmsg) {

    }

    private class ChatMessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ChatMessage chatMessage = (ChatMessage) intent.getSerializableExtra("chatMessage");
            Message msg = Message.obtain();
            msg.what = LOAD_CHATMESSAGE_SUCCEED; // 消息标识
            msg.obj = chatMessage;
            mHandler.sendMessage(msg);
        }
    }

    private void doRegisterReceiver() {
        chatMessageReceiver = new ChatMessageReceiver();
        IntentFilter filter = new IntentFilter("gdut.edu.datingforballsports.servicecallback.chatContent");
        registerReceiver(chatMessageReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(chatMessageReceiver);
    }
}
