package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;
import java.util.List;

import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.model.ForumListModel;
import gdut.edu.datingforballsports.model.Lisentener.ForumListListener;
import gdut.edu.datingforballsports.view.ForumListView;
import gdut.edu.datingforballsports.view.View_;

public class ForumListPresenter extends BasePresenter {
    public ForumListPresenter(ForumListView forumListView) {
        this.model = new ForumListModel();
        this.viewReference = new WeakReference<View_>(forumListView);
    }

    public List<Post> getList() {
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            return ((ForumListModel) model).getForumList(((ForumListView) viewReference).getUserId(), new ForumListListener() {
                @Override
                public void onSuccess() {
                    //成功推荐了
                    ((ForumListView) viewReference).onForumListLoadSuccess();
                }

                @Override
                public void onFails() {
                    //推荐失败了
                    ((ForumListView) viewReference).onForumListLoadFail();
                }
            });
        }
        return null;
    }
}
