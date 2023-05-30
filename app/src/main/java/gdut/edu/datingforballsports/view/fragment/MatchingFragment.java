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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.ChatMessage;
import gdut.edu.datingforballsports.domain.MatchingItem;
import gdut.edu.datingforballsports.domain.MessageBean;
import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.presenter.EditMatchingPresenter;
import gdut.edu.datingforballsports.presenter.MatchingPresenter;
import gdut.edu.datingforballsports.presenter.TrendsPresenter;
import gdut.edu.datingforballsports.util.TextUtils;
import gdut.edu.datingforballsports.view.MatchingView;
import gdut.edu.datingforballsports.view.Service.SocketService;
import gdut.edu.datingforballsports.view.activity.ChatActivity;
import gdut.edu.datingforballsports.view.activity.EditMatchingActivity;
import gdut.edu.datingforballsports.view.activity.EditPostActivity;
import gdut.edu.datingforballsports.view.activity.PostDetailsActivity;
import gdut.edu.datingforballsports.view.adapter.CommonAdapter;
import gdut.edu.datingforballsports.view.viewholder.CommonViewHolder;

public class MatchingFragment extends BaseFragment implements MatchingView {
    private static final int LOAD_MATCHINGMESSAGE_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;
    //private MatchingReceiver matchingReceiver;

    private View view;

    private Intent intent;
    private MatchingPresenter mPresenter;
    private RecyclerView recyclerView;
    private CommonAdapter<MatchingItem> mCommonAdapter;
    private int userId = -1;
    private String token;
    private String ballType;
    private String memberNum;
    private String city;
    private List<MatchingItem> matchingItemList;
    public Handler mHandler;
    private Gson gson;
    private ServiceConnection serviceConnection;
    private SocketService.JWebSocketClientBinder binder;
    private SocketService socketService;

    public MatchingFragment() {
    }

    public static MatchingFragment newInstance() {
        MatchingFragment fragment = new MatchingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case LOAD_MATCHINGMESSAGE_SUCCEED:
                        Toast.makeText(getActivity(), "加载成功！", Toast.LENGTH_LONG).show();
                        matchingItemList = TextUtils.castList(message.obj, MatchingItem.class);
                        setView();
                        break;
                    case LOAD_FAILED:
                        Toast.makeText(getActivity(), String.valueOf(message.obj), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.matching, container, false);
        setData();
        setView();
        return view;
    }

    private void setData() {
        intent = this.getActivity().getIntent();
        this.mPresenter = new MatchingPresenter(this);
        userId = intent.getIntExtra("userId", -1);
        token = intent.getStringExtra("token");
        gson = new Gson();
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
        city = "广州";
        mPresenter.getList(userId, token, city);
        //doRegisterReceiver();
    }

    private void setView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.matching_recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        mCommonAdapter = new CommonAdapter<>(matchingItemList, new CommonAdapter.OnBindDataListener<MatchingItem>() {
            @Override
            public void onBindViewHolder(MatchingItem model, CommonViewHolder viewHolder, int type, int position) {
                if (model.getRequireNum() == 2) {//一对一的
                    viewHolder.setImageResource(R.id.matching_item_logo, model.getOtherOrChatRoomLogo());
                    viewHolder.setText(R.id.matching_item_userName, model.getOtherOrChatRoomName());
                    viewHolder.setText(R.id.matching_item_ball_type, model.getCoverContent());
                    viewHolder.onItemClick((View) viewHolder.getView(R.id.matching_item_join), view -> {
                        Intent intent = new Intent();
                        intent.putExtra("userId", userId);
                        intent.putExtra("token", token);
                        MessageBean messageBean = new MessageBean(1,model.getPublisherId(),model.getOtherOrChatRoomName(),model.getOtherOrChatRoomLogo());
                        String json = gson.toJson(messageBean);
                        intent.putExtra("messageBean", json);
                        try {
                            JSONArray jsonArray = new JSONArray();
                            JSONObject matchingMsg = gson.fromJson(json, JSONObject.class);
                            jsonArray.put(0,"matching");
                            jsonArray.put(1,json);
                            jsonArray.put(2,model.getId());
                            String msg = gson.toJson(jsonArray);
                            socketService.sendMsg(msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        viewHolder.jumpActivity(intent, ChatActivity.class);
                    });
                }

            }

            @Override
            public int getLayoutId(int type) {
                return R.layout.matching_item;
            }
        });
        recyclerView.setAdapter(mCommonAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        buttonClick(view.findViewById(R.id.homepage_publish_request), view -> {
            Intent intent = new Intent();
            intent.putExtra("userId", userId);
            intent.putExtra("token", token);
            intent.setClass(this.getActivity(), EditMatchingActivity.class);
            startActivity(intent);
        });
        buttonClick((Button)view.findViewById(R.id.matching_button), view -> {
            if(((TextView) view.findViewById(R.id.matching_ball_type_content)).getText()!=null
            &&((TextView) view.findViewById(R.id.num_of_people)).getText()!=null){
                ballType = ((TextView) view.findViewById(R.id.matching_ball_type_content)).getText().toString();
                memberNum = ((TextView) view.findViewById(R.id.num_of_people)).getText().toString();
                mPresenter.getList(userId, token, ballType, memberNum, city);
            }
        });
    }

    @Override
    public void onLoadSuccess(List<MatchingItem> list) {
        Message msg = Message.obtain();
        msg.what = LOAD_MATCHINGMESSAGE_SUCCEED; // 消息标识
        msg.obj = list;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onLoadFails(String msgRC) {
        Message msg = Message.obtain();
        msg.what = LOAD_FAILED;
        msg.obj = msgRC;
        mHandler.sendMessage(msg);
    }

/*    private class MatchingReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            MatchingItem matchingItem = (MatchingItem) intent.getSerializableExtra("MatchingList");
            Message msg = Message.obtain();
            msg.what = LOAD_MATCHINGMESSAGE_SUCCEED; // 消息标识
            msg.obj = matchingItem;
            mHandler.sendMessage(msg);
        }
    }

    private void doRegisterReceiver() {
        matchingReceiver = new MatchingReceiver();
        IntentFilter filter = new IntentFilter("gdut.edu.datingforballsports.servicecallback.MatchingContent");
        getActivity().registerReceiver(matchingReceiver, filter);
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        //getActivity().unregisterReceiver(matchingReceiver);
    }
}
