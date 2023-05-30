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
import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.presenter.TrendsPresenter;
import gdut.edu.datingforballsports.util.TextUtils;
import gdut.edu.datingforballsports.view.TrendsView;
import gdut.edu.datingforballsports.view.activity.ChatActivity;
import gdut.edu.datingforballsports.view.activity.OtherHomePageActivity;
import gdut.edu.datingforballsports.view.activity.PostDetailsActivity;
import gdut.edu.datingforballsports.view.adapter.CommonAdapter;
import gdut.edu.datingforballsports.view.viewholder.CommonViewHolder;

public class TrendsFragment extends BaseFragment implements TrendsView {
    private static final int LOAD_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;

    private View view;
    private TrendsPresenter tPresenter;
    private RecyclerView recyclerView;
    private List<Post> list = new ArrayList<>();
    private CommonAdapter<Post> mCommonAdapter;
    private Intent intent;
    private int userId = -1;
    private int otherUserId = -1;
    private String token;
    private String RCmsg;
    public Handler mHandler;
    private Gson gson;

    public TrendsFragment() {
    }

    public static TrendsFragment newInstance() {
        TrendsFragment fragment = new TrendsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("TrendsFragment1nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
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
                        list = TextUtils.castList(message.obj, Post.class);
                        setView();
                        break;
                }
            }
        };
        //列表项
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.trends_list, container, false);
        setData();
        return view;
    }

    private void setData() {
        this.tPresenter = new TrendsPresenter(this);
        intent = this.getActivity().getIntent();
        userId = intent.getIntExtra("userId", -1);
        otherUserId = intent.getIntExtra("otherUserId", -1);
        token = intent.getStringExtra("token");
        gson = new Gson();
        tPresenter.getList(otherUserId == -1 ? userId : otherUserId, token);
    }

    private void setView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.trends_recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        mCommonAdapter = new CommonAdapter<>(list, new CommonAdapter.OnBindDataListener<Post>() {
            @Override
            public void onBindViewHolder(Post model, CommonViewHolder viewHolder, int type, int position) {
                viewHolder.setImageResource(R.id.post_item_logo, model.getPublisherLogo());
                viewHolder.setText(R.id.post_item_userName, model.getPublisherName());
                viewHolder.setText(R.id.post_item_time, model.getCreateTime());
                viewHolder.setText(R.id.post_item_content, model.getContent());
                viewHolder.setText(R.id.post_item_comment_num, String.valueOf(model.getCommentNum()));
                viewHolder.setText(R.id.post_item_like_num, String.valueOf(model.getLikeNum()));
                if (model.isIfCollect()) {
                    viewHolder.setSelect(R.id.post_item_collect_image, true);
                }
                if (model.isIfLike()) {
                    viewHolder.setSelect(R.id.post_item_like_image, true);
                }
                viewHolder.onItemClick((View) viewHolder.getView(R.id.post_item_logo), view -> {
                    if(userId!=model.getPublisherId()){
                        Intent intent = new Intent();
                        System.out.println("model: " + model);
                        intent.putExtra("userId", userId);
                        intent.putExtra("token", token);
                        intent.putExtra("otherUserId", model.getPublisherId());
                        intent.putExtra("otherUserName", model.getPublisherName());
                        intent.putExtra("otherUserIcon", model.getPublisherLogo());
                        viewHolder.jumpActivity(intent, OtherHomePageActivity.class);
                    }
                });
                viewHolder.onItemClick((View) viewHolder.getView(R.id.post_item_collect_image), view -> {
                    viewHolder.changeSelect(R.id.post_item_collect_image);
                    model.setIfCollect(!model.isIfCollect());
                    if (model.isIfCollect()) {
                        tPresenter.collectPost(userId,token,model.getId());
                    }
                });
                viewHolder.onItemClick((View) viewHolder.getView(R.id.post_item_like_image), view -> {
                    viewHolder.changeSelect(R.id.post_item_like_image);
                    model.setIfCollect(!model.isIfLike());
                    if (model.isIfLike()) {
                        model.setLikeNum(model.getLikeNum() - 1);
                        viewHolder.setSelect(R.id.post_item_collect_image, false);
                    } else {
                        model.setLikeNum(model.getLikeNum() + 1);
                        viewHolder.setSelect(R.id.post_item_collect_image, true);
                    }
                    viewHolder.setText(R.id.post_item_like_num, String.valueOf(model.getLikeNum()));
                    viewHolder.setSelect(R.id.post_item_like_image, model.isIfLike());
                });
                viewHolder.onItemClick(viewHolder.itemView, view -> {
                    Intent intent = new Intent();
                    String post = gson.toJson(model);
                    intent.putExtra("post", post);
                    intent.putExtra("userId", userId);
                    intent.putExtra("token", token);
                    viewHolder.jumpActivity(intent, PostDetailsActivity.class);
                });
                viewHolder.onItemClick((View) viewHolder.getView(R.id.post_item_comment_image), view -> {
                    Intent intent = new Intent();
                    String post = gson.toJson(model);
                    intent.putExtra("post", post);
                    intent.putExtra("userId", userId);
                    intent.putExtra("token", token);
                    viewHolder.jumpActivity(intent, PostDetailsActivity.class);
                });

                viewHolder.onItemClick((View) viewHolder.getView(R.id.post_item_report), view -> {

                });
            }

            @Override
            public int getLayoutId(int type) {
                return R.layout.a_post_item;
            }
        });
        recyclerView.setAdapter(mCommonAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public String getToken() {
        return token;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void onTrendsLoadSuccess(Object list, String RCmsg) {
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
