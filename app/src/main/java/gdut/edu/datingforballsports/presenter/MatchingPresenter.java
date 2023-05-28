package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import gdut.edu.datingforballsports.domain.MatchingItem;
import gdut.edu.datingforballsports.model.Listener.MatchingListener;
import gdut.edu.datingforballsports.model.Listener.TrendsListener;
import gdut.edu.datingforballsports.model.MatchingModel;
import gdut.edu.datingforballsports.model.TrendsModel;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.MatchingView;
import gdut.edu.datingforballsports.view.TrendsView;
import gdut.edu.datingforballsports.view.View_;

public class MatchingPresenter extends BasePresenter {
    public MatchingPresenter(MatchingView matchingView) {
        this.model = new MatchingModel();
        this.viewReference = new WeakReference<View_>(matchingView);
    }

    public void getList(int userId, String token) {
        ((MatchingView) viewReference.get()).onLoadSuccess(new ArrayList<>());
        /*if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    ((MatchingModel) model).getTrendsList(userId, token, new MatchingListener() {

                        @Override
                        public void onLoadSuccess(List<MatchingItem> list) {
                            ((MatchingView) viewReference.get()).onLoadSuccess(list);
                        }

                        @Override
                        public void onLoadFails(String msg) {
                            ((MatchingView) viewReference.get()).onLoadFails(msg);
                        }
                    });
                }
            });
        }*/
    }
}
