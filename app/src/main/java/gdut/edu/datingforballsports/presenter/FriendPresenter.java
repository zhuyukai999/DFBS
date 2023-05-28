package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;
import java.util.List;

import gdut.edu.datingforballsports.domain.Friend;
import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.model.FriendModel;
import gdut.edu.datingforballsports.model.Listener.FriendListener;
import gdut.edu.datingforballsports.model.Listener.TrendsListener;
import gdut.edu.datingforballsports.model.TrendsModel;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.FriendView;
import gdut.edu.datingforballsports.view.TrendsView;
import gdut.edu.datingforballsports.view.View_;

public class FriendPresenter extends BasePresenter {
    public FriendPresenter(FriendView friendView) {
        this.model = new FriendModel();
        this.viewReference = new WeakReference<View_>(friendView);
    }

    public void getList(int userId, String token) {
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    ((FriendModel) model).getFriendList(userId, token, new FriendListener() {

                        @Override
                        public void onSuccess(List<Friend> list) {
                            ((FriendView) viewReference.get()).onLoadSuccess(list);
                        }

                        @Override
                        public void onFails(String msg) {
                            ((FriendView) viewReference.get()).onLoadFail(msg);
                        }
                    });
                }
            });
        }
    }
}
