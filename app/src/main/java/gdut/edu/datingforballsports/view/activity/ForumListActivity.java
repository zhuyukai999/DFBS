package gdut.edu.datingforballsports.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.presenter.ForumListPresenter;
import gdut.edu.datingforballsports.view.ForumListView;
import gdut.edu.datingforballsports.view.adapter.CommonAdapter;
import gdut.edu.datingforballsports.view.viewholder.ViewHolder;

public class ForumListActivity extends BaseActivity implements ForumListView {
    private ForumListPresenter fPresenter;
    private ListView listView;
    private List<Post> list = new ArrayList<>();
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_list);
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        //列表项
        listView = (ListView) findViewById(R.id.forumList);
        setData();
        setView();
    }


    private void setData() {
        this.fPresenter = new ForumListPresenter(this);
        listView.setAdapter(new CommonAdapter<Post>(this, list) {
            //TODO
            //还没做完，设置列表项目的内容
            @Override
            public void convert(ViewHolder holder, Post item) {
                holder.setText(R.id.show_forumItem_main_text,item.getText());
                //设置图片的
                /*if (item.getImageUrl() != null){
                    holder.setImageByUrl(R.id.show_forumItem_image,item.getImageUrl());
                }else {
                    holder.setImageResource(R.id.show_forumItem_image,item.getImageId());
                }*/
            }
        });
    }

    private void setView() {
        list = fPresenter.getList();
    }


    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void onForumListLoadSuccess() {
        Toast.makeText(getApplicationContext(), "加载成功！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onForumListLoadFail() {
        Toast.makeText(getApplicationContext(), "加载失败！", Toast.LENGTH_LONG).show();
    }


}
