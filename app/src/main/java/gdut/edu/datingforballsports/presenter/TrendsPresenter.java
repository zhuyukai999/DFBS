package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;
import java.util.List;

import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.model.ForumListModel;
import gdut.edu.datingforballsports.model.Listener.TrendsListener;
import gdut.edu.datingforballsports.model.TrendsModel;
import gdut.edu.datingforballsports.presenter.BasePresenter;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.ForumListView;
import gdut.edu.datingforballsports.view.TrendsView;
import gdut.edu.datingforballsports.view.View_;

public class TrendsPresenter extends BasePresenter {
    public TrendsPresenter(TrendsView trendsView) {
        this.model = new TrendsModel();
        this.viewReference = new WeakReference<View_>(trendsView);
    }

    public void getList(int userId, String token) {
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    ((TrendsModel) model).getTrendsList(userId, token, new TrendsListener() {

                        @Override
                        public void onSuccess(List<Post> list, String msg) {
                            ((TrendsView) viewReference.get()).onTrendsLoadSuccess(list, msg);
                        }

                        @Override
                        public void onFails(String msg) {
                            ((TrendsView) viewReference.get()).onLoadFail(msg);
                        }
                    });
                }
            });
        }
    }

    public void collectPost(int userId, String token, int id) {
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    ((TrendsModel) model).collectPost(userId, token,id);
                }
            });
        }
    }
}
