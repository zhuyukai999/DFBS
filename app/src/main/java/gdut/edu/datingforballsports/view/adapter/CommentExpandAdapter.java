package gdut.edu.datingforballsports.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.CommentDetail;
import gdut.edu.datingforballsports.domain.ReplyDetail;
import gdut.edu.datingforballsports.util.GlideEngine;
import gdut.edu.datingforballsports.util.TextUtils;

public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CommentExpandAdapter";
    private List<CommentDetail> commentBeanList;
    private Context context;

    public CommentExpandAdapter(Context context, List<CommentDetail> commentBeanList) {
        this.context = context;
        this.commentBeanList = commentBeanList;
    }

    @Override
    public int getGroupCount() {
        return commentBeanList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (commentBeanList.get(groupPosition).getReplyList() == null) {
            return 0;
        } else {
            return commentBeanList.get(groupPosition).getReplyList().size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return commentBeanList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return commentBeanList.get(groupPosition).getReplyList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    boolean isLike = false;

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupHolder groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.a_comment_item_layout, parent, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        GlideEngine.createGlideEngine().loadNetImage(context,commentBeanList.get(groupPosition).getPublisherLogo(),groupHolder.logo);
        groupHolder.name.setText(commentBeanList.get(groupPosition).getPublisherName());
        groupHolder.content.setText(commentBeanList.get(groupPosition).getContent());
        groupHolder.time.setText(commentBeanList.get(groupPosition).getCreateTime());
        int likeNum = commentBeanList.get(groupPosition).getLikeNum();
        groupHolder.likeNum.setText(likeNum);
        groupHolder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ifLike = commentBeanList.get(groupPosition).isIfLike();
                commentBeanList.get(groupPosition).setIfLike(!ifLike);
                if (ifLike) {
                    groupHolder.likeNum.setText(likeNum - 1);
                    groupHolder.likeIcon.setSelected(false);
                } else {
                    groupHolder.likeNum.setText(likeNum + 1);
                    groupHolder.likeIcon.setSelected(true);
                }
            }
        });
        /*groupHolder.commentIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.a_comment_reply_item_layout, parent, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        String replyUser = commentBeanList.get(groupPosition).getReplyList().get(childPosition).getUserName();
        if (!TextUtils.isEmpty(replyUser)) {
            childHolder.name.setText(replyUser + ":");
        }
        childHolder.content.setText(commentBeanList.get(groupPosition).getReplyList().get(childPosition).getContent());
        childHolder.createTime.setText(commentBeanList.get(groupPosition).getReplyList().get(childPosition).getCreateDate());
        int likeNum = commentBeanList.get(groupPosition).getReplyList().get(childPosition).getLikeNum();
        childHolder.likeNum.setText(likeNum);
        childHolder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ifLike = commentBeanList.get(groupPosition).getReplyList().get(childPosition).isIfLike();
                commentBeanList.get(groupPosition).getReplyList().get(childPosition).setIfLike(!ifLike);
                if (ifLike) {
                    childHolder.likeNum.setText(likeNum - 1);
                    childHolder.likeIcon.setSelected(false);
                } else {
                    childHolder.likeNum.setText(likeNum + 1);
                    childHolder.likeIcon.setSelected(true);
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class GroupHolder {
        private CircleImageView logo;
        private TextView name, content, time, likeNum, commentNum;
        private ImageView likeIcon, commentIcon;

        public GroupHolder(View view) {
            logo = view.findViewById(R.id.comment_item_logo);
            content = view.findViewById(R.id.comment_item_content);
            name = view.findViewById(R.id.comment_item_userName);
            time = view.findViewById(R.id.comment_item_time);
            likeIcon = view.findViewById(R.id.comment_item_like_image);
            likeNum = view.findViewById(R.id.comment_item_like_num);
            commentIcon = view.findViewById(R.id.comment_item_comment_image);
            commentNum = view.findViewById(R.id.comment_item_comment_num);
        }
    }

    private class ChildHolder {
        private TextView name, content, createTime, likeNum;
        private ImageView likeIcon;

        public ChildHolder(View view) {
            name = (TextView) view.findViewById(R.id.reply_item_user);
            content = (TextView) view.findViewById(R.id.reply_item_content);
            createTime = (TextView) view.findViewById(R.id.comment_item_time);
            likeIcon = view.findViewById(R.id.forum_item_good_image);
            likeNum = (TextView) view.findViewById(R.id.forum_item_good_num);
        }
    }

    /**
     * by moos on 2018/04/20
     * func:评论成功后插入一条数据
     *
     * @param commentDetailBean 新的评论数据
     */
    public void addTheCommentData(CommentDetail commentDetailBean) {
        if (commentDetailBean != null) {

            commentBeanList.add(commentDetailBean);
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("评论数据为空!");
        }

    }

    /**
     * by moos on 2018/04/20
     * func:回复成功后插入一条数据
     *
     * @param replyDetail 新的回复数据
     */
    public void addTheReplyData(ReplyDetail replyDetail, int groupPosition) {
        if (replyDetail != null) {
            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:" + replyDetail.toString());
            if (commentBeanList.get(groupPosition).getReplyList() != null) {
                commentBeanList.get(groupPosition).getReplyList().add(replyDetail);
            } else {
                List<ReplyDetail> replyList = new ArrayList<>();
                replyList.add(replyDetail);
                commentBeanList.get(groupPosition).setReplyList(replyList);
            }
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("回复数据为空!");
        }

    }
}
