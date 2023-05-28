package gdut.edu.datingforballsports.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.CommentDetail;

import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.domain.ReplyDetail;
import gdut.edu.datingforballsports.presenter.PostDetailsPresenter;
import gdut.edu.datingforballsports.util.DateFormatUtil;
import gdut.edu.datingforballsports.util.GlideEngine;
import gdut.edu.datingforballsports.view.CommentExpandableListView;
import gdut.edu.datingforballsports.view.PostDetailsView;
import gdut.edu.datingforballsports.view.adapter.CommentExpandAdapter;

public class PostDetailsActivity extends BaseActivity implements View.OnClickListener, PostDetailsView {
    private static final String TAG = "MainActivity";
    private static final int LOAD_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;
    private static final int UPLOAD_SUCCEED = 3;
    private androidx.appcompat.widget.Toolbar toolbar;
    private TextView bt_comment;
    private CommentExpandableListView expandableListView;
    private CommentExpandAdapter adapter;
    private List<CommentDetail> commentsList;
    private BottomSheetDialog dialog;
    private static boolean isExpanded;

    private PostDetailsPresenter pPresenter;
    private Post post;
    private Intent intent;
    private int userId = -1;
    private String username;
    private String logo_url;
    private int postId;
    private String token;
    private String RCmsg;
    public Handler mHandler;
    private Gson gson;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_main);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case LOAD_SUCCEED:
                        commentsList = gdut.edu.datingforballsports.util.TextUtils.castList(message.obj, CommentDetail.class);
                        initExpandableListView(commentsList);
                        break;
                    case LOAD_FAILED:
                        Toast.makeText(getApplicationContext(), String.valueOf(message.obj), Toast.LENGTH_LONG).show();
                        break;
                    case UPLOAD_SUCCEED:
                        Toast.makeText(getApplicationContext(), String.valueOf(message.obj), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
        setData();
        setView();
    }


    private void setData() {
        this.pPresenter = new PostDetailsPresenter(this);
        intent = getIntent();
        gson = new Gson();
        String post_s = intent.getStringExtra("post");
        post = gson.fromJson(post_s, Post.class);
        userId = intent.getIntExtra("userId", -1);
        token = intent.getStringExtra("token");
        postId = post.getId();
        sharedPreferences = getSharedPreferences("user" + userId, MODE_PRIVATE);
        logo_url = sharedPreferences.getString("icon", null);
        username = sharedPreferences.getString("userName", null);
    }

    private void setView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        expandableListView = findViewById(R.id.detail_page_lv_comment);
        bt_comment = (TextView) findViewById(R.id.detail_page_do_comment);
        bt_comment.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("详情");
        GlideEngine.createGlideEngine().loadNetImage(getApplicationContext(), post.getPublisherLogo(), findViewById(R.id.detail_page_userLogo));
        ((TextView) findViewById(R.id.detail_page_userName)).setText(post.getPublisherName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ((TextView) findViewById(R.id.detail_page_time)).setText(post.getCreateTime());
        ((TextView) findViewById(R.id.detail_page_story)).setText(post.getContent());
        ((TextView) findViewById(R.id.post_detail_like_num)).setText(String.valueOf(post.getLikeNum()));
        if (post.isIfLike()) {
            findViewById(R.id.post_detail_like_image).setSelected(true);
        }
        if (post.isIfCollect()) {
            findViewById(R.id.post_detail_collect_image).setSelected(true);
        }
        buttonClick(R.id.post_detail_like_image, view -> {

        });
        buttonClick(R.id.post_detail_collect_image, view -> {

        });
        pPresenter.generateTestData(userId, token, post.getId());
    }

    private void initExpandableListView(final List<CommentDetail> commentList) {
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new CommentExpandAdapter(this, commentList);
        expandableListView.setAdapter(adapter);
        for (int i = 0; i < commentList.size(); i++) {
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                Log.e(TAG, "onGroupClick: 当前的评论id>>>" + commentList.get(groupPosition).getId());
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }
                showReplyDialog(groupPosition);
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast.makeText(getApplicationContext(), "点击了回复", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.detail_page_do_comment) {
            showCommentDialog();
        }
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

                    //commentOnWork(commentContent);
                    dialog.dismiss();
                    CommentDetail detailBean = new CommentDetail(userId, username, logo_url, commentContent, 0, 0, false, DateFormatUtil.getCurrentDateString("yyyy-MM-dd HH:mm"), postId);
                    adapter.addTheCommentData(detailBean);
                    pPresenter.uploadComment(userId, token, detailBean);
                    Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "评论内容不能为空", Toast.LENGTH_SHORT).show();
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

    /**
     * by moos on 2018/04/20
     * func:弹出回复框
     */
    private void showReplyDialog(final int position) {
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.a_comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentsList.get(position).getPublisherName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {
                    dialog.dismiss();
                    ReplyDetail detailBean = new ReplyDetail(username, userId, replyContent, 0, false, DateFormatUtil.getCurrentDateString("yyyy-MM-dd HH:mm"), commentsList.get(position).getId());
                    adapter.addTheReplyData(detailBean, position);
                    pPresenter.uploadCommentReply(userId, token, detailBean);
                    expandableListView.expandGroup(position);
                    Toast.makeText(getApplicationContext(), "回复成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "回复内容不能为空", Toast.LENGTH_SHORT).show();
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
    public void onLoadSuccess(List<CommentDetail> list) {
        Message msg = Message.obtain();
        msg.what = LOAD_SUCCEED; // 消息标识
        msg.obj = list;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onLoadFails(String msg_) {
        Message msg = Message.obtain();
        msg.what = LOAD_FAILED; // 消息标识
        msg.obj = msg_;
        mHandler.sendMessage(msg);

    }

    @Override
    public void onLoadSuccess(String msg_) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_SUCCEED; // 消息标识
        msg.obj = msg_;
        mHandler.sendMessage(msg);
    }


}
