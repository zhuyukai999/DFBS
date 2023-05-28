package gdut.edu.datingforballsports.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.presenter.ForumListPresenter;
import gdut.edu.datingforballsports.util.GPSUtils;
import gdut.edu.datingforballsports.util.TextUtils;
import gdut.edu.datingforballsports.view.ForumListView;
import gdut.edu.datingforballsports.view.Service.SocketService;
import gdut.edu.datingforballsports.view.activity.OtherHomePageActivity;
import gdut.edu.datingforballsports.view.activity.PostDetailsActivity;
import gdut.edu.datingforballsports.view.adapter.CommonAdapter;
import gdut.edu.datingforballsports.view.viewholder.CommonViewHolder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ForumListFragment extends BaseFragment implements ForumListView {
    private static final int LOAD_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;

    private View view;

    private ForumListPresenter fPresenter;

    private List<Post> list = null;
    private CommonAdapter<Post> mCommonAdapter = null;
    private Intent intent;
    private int userId = -1;
    private String token;
    private String RCmsg;
    private String ballType;
    private String city;
    private String city_buffer;
    private RecyclerView recyclerView;
    public Handler mHandler;
    private Gson gson;

    public ForumListFragment() {
    }

    public static ForumListFragment newInstance() {
        ForumListFragment fragment = new ForumListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("ForumListFragmentCreate:wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
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
                        if (list != null) {
                            if (mCommonAdapter == null) {
                                setView();
                            } else {
                                mCommonAdapter.changeAll(list);
                            }
                        }
                        break;
                }
            }
        };
        //列表项
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forum_list, container, false);
        setData();
        return view;
    }

    private void setData() {
        this.fPresenter = new ForumListPresenter(this);
        intent = this.getActivity().getIntent();
        userId = intent.getIntExtra("userId", -1);
        token = intent.getStringExtra("token");
        gson = new Gson();
        fPresenter.getList(userId, token);
    }

    private void setView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.forum_list_forumList);
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
                    }
                    viewHolder.setSelect(R.id.post_item_collect_image, model.isIfCollect());
                });
                viewHolder.onItemClick((View) viewHolder.getView(R.id.post_item_like_image), view -> {
                    viewHolder.changeSelect(R.id.post_item_like_image);
                    model.setIfCollect(!model.isIfLike());
                    if (model.isIfLike()) {
                        model.setLikeNum(model.getLikeNum() + 1);
                    } else {
                        model.setLikeNum(model.getLikeNum() - 1);
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
                    intent.putExtra("postId", model.getId());
                    viewHolder.jumpActivity(intent, PostDetailsActivity.class);
                });
                viewHolder.onItemClick((View) viewHolder.getView(R.id.post_item_comment_image), view -> {
                    Intent intent = new Intent();
                    String post = gson.toJson(model);
                    intent.putExtra("post", post);
                    intent.putExtra("userId", userId);
                    intent.putExtra("token", token);
                    intent.putExtra("postId", model.getId());
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

        Switch viewById = view.findViewById(R.id.forum_list_switch);
        viewById.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (city_buffer == null) {
                        GPSUtils.getInstance().getProvince(view.getContext(), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String responseData = response.body().string();
                                try {
                                    JSONObject jsonObjectALL = new JSONObject(responseData);
                                    JSONObject result = jsonObjectALL.getJSONObject("result");
                                    city = result.getJSONObject("addressComponent").getJSONObject("city").toString();
                                    city_buffer = city;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        city = city_buffer;
                    }
                } else {
                    city = null;
                }
            }
        });
        buttonClick(R.id.screen_post_button, view -> {
            ballType = getActivity().findViewById(R.id.forum_list_content).getContext().toString();
            fPresenter.getList(userId, token, ballType, city);
        });
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
    public void onForumListLoadSuccess(Object list, String RCmsg) {
        Message msg = Message.obtain();
        msg.what = LOAD_SUCCEED; // 消息标识
        msg.obj = list;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("ForumListFragmentdestroy:wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");

    }

    @Override
    public void onForumListLoadFail(String RCmsg) {
        this.RCmsg = RCmsg;
    }
}
