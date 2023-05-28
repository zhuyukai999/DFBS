package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;
import java.util.List;

import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.model.CollectModel;
import gdut.edu.datingforballsports.model.Listener.CollectListener;
import gdut.edu.datingforballsports.model.Listener.TrendsListener;
import gdut.edu.datingforballsports.model.TrendsModel;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.CollectView;
import gdut.edu.datingforballsports.view.TrendsView;
import gdut.edu.datingforballsports.view.View_;

public class CollectPresenter extends BasePresenter {
    public CollectPresenter(CollectView CollectView) {
        this.model = new CollectModel();
        this.viewReference = new WeakReference<View_>(CollectView);
    }

    public void getList(int userId, String token) {
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    ((CollectModel) model).getCollectList(((CollectView) viewReference.get()).getUserId(),
                            ((CollectView) viewReference.get()).getToken(), new CollectListener() {

                                @Override
                                public void onSuccess(List<Post> list, String msg) {
                                    ((CollectView) viewReference.get()).onTrendsLoadSuccess(list, msg);
                                }

                                @Override
                                public void onFails(String msg) {
                                    ((CollectView) viewReference.get()).onLoadFail(msg);
                                }
                            });
                }
            });
        }
    }
}
