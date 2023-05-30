package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;
import java.util.List;

import gdut.edu.datingforballsports.model.ForumListModel;
import gdut.edu.datingforballsports.model.Listener.ForumListListener;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.ForumListView;
import gdut.edu.datingforballsports.view.View_;

public class ForumListPresenter extends BasePresenter {
    public ForumListPresenter(ForumListView forumListView) {
        this.model = new ForumListModel();
        this.viewReference = new WeakReference<View_>(forumListView);
    }

    public void getList(int userId, String token) {
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    ((ForumListModel) model).getForumList(userId, token, new ForumListListener() {
                        @Override
                        public void onSuccess(List list, String msg) {
                            //成功推荐了
                            ((ForumListView) viewReference.get()).onForumListLoadSuccess(list, msg);
                        }

                        @Override
                        public void onFails(String msg) {
                            //推荐失败了
                            ((ForumListView) viewReference.get()).onForumListLoadFail(msg);
                        }
                    });
                }
            });
        }
    }

    public void getList(int userId, String token, String ballType, String city) {
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    ((ForumListModel) model).getForumList(userId, token, ballType, city, new ForumListListener() {
                        @Override
                        public void onSuccess(List list, String msg) {
                            //成功推荐了
                            ((ForumListView) viewReference.get()).onForumListLoadSuccess(list, msg);
                        }

                        @Override
                        public void onFails(String msg) {
                            //推荐失败了
                            ((ForumListView) viewReference.get()).onForumListLoadFail(msg);
                        }
                    });
                }
            });
        }
    }

    public void collectPost(int userId, String token, int postId) {
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    ((ForumListModel) model).collectPost(userId, token,postId);
                }
            });
        }
    }
}
