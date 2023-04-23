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

    public void getList() {
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            String token = null;
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    ((ForumListModel) model).getForumList(((ForumListView) viewReference).getUserId(), token, new ForumListListener() {
                        @Override
                        public void onSuccess(List list, String msg) {
                            //成功推荐了
                            ((ForumListView) viewReference).onForumListLoadSuccess(list, msg);
                        }

                        @Override
                        public void onFails(String msg) {
                            //推荐失败了
                            ((ForumListView) viewReference).onForumListLoadFail(msg);
                        }
                    });
                }
            });

        }
    }
}
