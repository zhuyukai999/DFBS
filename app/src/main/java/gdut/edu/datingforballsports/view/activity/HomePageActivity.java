package gdut.edu.datingforballsports.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.domain.User;
import gdut.edu.datingforballsports.presenter.HomePagePresenter;
import gdut.edu.datingforballsports.util.TextUtils;
import gdut.edu.datingforballsports.view.HomePageView;
import gdut.edu.datingforballsports.view.adapter.CommonAdapter;
import gdut.edu.datingforballsports.view.viewholder.CommonViewHolder;

public class HomePageActivity extends BaseActivity implements HomePageView {
    private static final int LOAD_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;
    private HomePagePresenter hPresenter;
    private RecyclerView recyclerView;
    private List<Post> list = new ArrayList<>();
    private CommonAdapter<Post> mCommonAdapter;
    private Intent intent;
    private int userId = -1;
    private String token;
    private String RCmsg;
    public Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                Intent intent = new Intent();
                //改变UI
                switch (message.what) {
                    case LOAD_FAILED:
                        Toast.makeText(getApplicationContext(), RCmsg, Toast.LENGTH_LONG).show();
                        intent.setClass(getApplicationContext(), LoginActivity.class);
                        break;
                    case LOAD_SUCCEED:
                        // FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        //transaction.replace(R.id.main_frame_layout, f1);
                        // transaction.add(R.id.main_frame_layout, f4);
                        list = TextUtils.castList(message.obj,Post.class);
                        break;
                }
            }
        };
        getData();

        setView();
    }

    private void getData() {
        this.hPresenter = new HomePagePresenter(this);
        intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        token = intent.getStringExtra("token");
        hPresenter.getUserMessage(userId, token);
    }

    private void setView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView = (RecyclerView) findViewById(R.id.homepage_forumList);
        recyclerView.setLayoutManager(linearLayoutManager);
        mCommonAdapter = new CommonAdapter<>(list, new CommonAdapter.OnMoreBindDataListener<Post>() {
            @Override
            public void onBindViewHolder(Post model, CommonViewHolder viewHolder, int type, int position) {
                //在这里根据图片数量进行Item选择
                switch (type) {
                    case 0:
                        //viewHolder.setImageResource(R.id.profilePhoto_forum_item_image0,);头像
                        //viewHolder.setText(R.id.username_forum_item_image0,);发布者
                        //viewHolder.setText(R.id.publish_time_forum_item_image0,);发布时间
                        viewHolder.setText(R.id.show_forumItem_main_text_forum_item_image0, model.getContent());
                        viewHolder.setText(R.id.forum_item_comment_num_image0, String.valueOf(model.getCommentNum()));
                        viewHolder.setText(R.id.forum_item_good_num_image0, String.valueOf(model.getLikeNum()));
                        List<View> list0 = new ArrayList<>();
                        list0.add((View) viewHolder.getView(R.id.profilePhoto_forum_item_image0));
                        list0.add((View) viewHolder.getView(R.id.username_forum_item_image0));
                        viewHolder.onItemClick(list0, view->{
                        });//别人的个人空间
                        viewHolder.onItemClick((View) viewHolder.getView(R.id.forum_item_report_image0), view->{
                        });//举办了
                        viewHolder.onItemClick((View) viewHolder.getView(R.id.forum_item_collect_image0), view->{
                        });//收藏了
                        viewHolder.onItemClick((View) viewHolder.getView(R.id.forum_item_good_image0), view->{
                        });//点赞了
                        break;
                    case 1:
                        //viewHolder.setImageResource(R.id.profilePhoto_forum_item_image1,);头像
                        //viewHolder.setText(R.id.username_forum_item_image1,);发布者
                        //viewHolder.setText(R.id.publish_time_forum_item_image1,);发布时间
                        viewHolder.setText(R.id.show_forumItem_main_text_forum_item_image1, model.getContent());
                        viewHolder.setText(R.id.forum_item_comment_num_image1, String.valueOf(model.getCommentNum()));
                        viewHolder.setText(R.id.forum_item_good_num_image1, String.valueOf(model.getLikeNum()));
                        //viewHolder.setImageResource(R.id.show_forumItem_image_forum_item_image1, model.getImage1());
                        List<View> list1 = new ArrayList<>();
                        list1.add((View) viewHolder.getView(R.id.profilePhoto_forum_item_image1));
                        list1.add((View) viewHolder.getView(R.id.username_forum_item_image1));
                        viewHolder.onItemClick(list1, view->{
                        });//别人的个人空间
                        viewHolder.onItemClick((View) viewHolder.getView(R.id.forum_item_report_image1), view->{
                        });//举办了
                        viewHolder.onItemClick((View) viewHolder.getView(R.id.forum_item_collect_image1), view->{
                        });//收藏了
                        viewHolder.onItemClick((View) viewHolder.getView(R.id.forum_item_good_image1), view->{
                        });//点赞了
                        break;
                    case 2:
                        //viewHolder.setImageResource(R.id.profilePhoto_forum_item_image2,);头像
                        //viewHolder.setText(R.id.username_forum_item_image2,);发布者
                        //viewHolder.setText(R.id.publish_time_forum_item_image2,);发布时间
                        viewHolder.setText(R.id.show_forumItem_main_text_forum_item_image2, model.getContent());
                        viewHolder.setText(R.id.forum_item_comment_num_image2, String.valueOf(model.getCommentNum()));
                        viewHolder.setText(R.id.forum_item_good_num_image2, String.valueOf(model.getLikeNum()));
                        //viewHolder.setImageResource(R.id.show_forumItem_image1_forum_item_image2, model.getImage1());
                        //viewHolder.setImageResource(R.id.show_forumItem_image2_forum_item_image2, model.getImage1());
                        List<View> list2 = new ArrayList<>();
                        list2.add((View) viewHolder.getView(R.id.profilePhoto_forum_item_image2));
                        list2.add((View) viewHolder.getView(R.id.username_forum_item_image2));
                        viewHolder.onItemClick(list2, view->{
                        });//别人的个人空间
                        viewHolder.onItemClick((View) viewHolder.getView(R.id.forum_item_report_image2), view->{
                        });//举办了
                        viewHolder.onItemClick((View) viewHolder.getView(R.id.forum_item_collect_image2), view->{
                        });//收藏了
                        viewHolder.onItemClick((View) viewHolder.getView(R.id.forum_item_good_image2), view->{
                        });//点赞了
                        break;
                    case 3:
                        //viewHolder.setImageResource(R.id.profilePhoto_forum_item_image3,);头像
                        //viewHolder.setText(R.id.username_forum_item_image3,);发布者
                        //viewHolder.setText(R.id.publish_time_forum_item_image3,);发布时间
                        viewHolder.setText(R.id.show_forumItem_main_text_forum_item_image3, model.getContent());
                        viewHolder.setText(R.id.forum_item_comment_num_image3, String.valueOf(model.getCommentNum()));
                        viewHolder.setText(R.id.forum_item_good_num_image3, String.valueOf(model.getLikeNum()));
                        //viewHolder.setImageResource(R.id.show_forumItem_image1_forum_item_image3, model.getImage1());
                        //viewHolder.setImageResource(R.id.show_forumItem_image2_forum_item_image3, model.getImage1());
                        //viewHolder.setImageResource(R.id.show_forumItem_image3_forum_item_image3, model.getImage1());
                        List<View> list3 = new ArrayList<>();
                        list3.add((View) viewHolder.getView(R.id.profilePhoto_forum_item_image3));
                        list3.add((View) viewHolder.getView(R.id.username_forum_item_image3));
                        viewHolder.onItemClick(list3, view->{
                        });//别人的个人空间
                        viewHolder.onItemClick((View) viewHolder.getView(R.id.forum_item_report_image3), view->{
                        });//举办了
                        viewHolder.onItemClick((View) viewHolder.getView(R.id.forum_item_collect_image3), view->{
                        });//收藏了
                        viewHolder.onItemClick((View) viewHolder.getView(R.id.forum_item_good_image3), view->{
                        });//点赞了
                        break;
                    default:
                        break;
                }

            }

            @Override
            public int getLayoutId(int type) {
                switch (type) {
                    case 0:
                        return R.layout.forum_item_image0;
                    case 1:
                        return R.layout.forum_item_image1;
                    case 2:
                        return R.layout.forum_item_image2;
                    case 3:
                        return R.layout.forum_item_image3;
                    default:
                        return 0;
                }
            }

            @Override
            public int getItemType(int position) {
                Post post = list.get(position);
                return post.getImagePaths().size();
            }
        });
        recyclerView.setAdapter(mCommonAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置发布帖子的点击事件
        buttonClick(R.id.homepage_publish_post,view->{
           // startActivity(new Intent(this, PublishPostActivity.class));
        });
    }

    @Override
    public void onLoadUserMessageSuccess(User user) {
        Message msg = Message.obtain();
        msg.what = LOAD_SUCCEED; // 消息标识
        msg.obj = user;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onLoadFails(String RCmsg) {
        this.RCmsg = RCmsg;
    }

}
