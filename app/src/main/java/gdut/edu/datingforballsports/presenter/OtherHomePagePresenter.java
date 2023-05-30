package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;
import java.util.List;

import gdut.edu.datingforballsports.domain.Friend;
import gdut.edu.datingforballsports.model.FriendModel;
import gdut.edu.datingforballsports.model.Listener.FriendListener;
import gdut.edu.datingforballsports.model.Listener.OtherHomePageListener;
import gdut.edu.datingforballsports.model.OtherHomePageModel;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.FriendView;
import gdut.edu.datingforballsports.view.View_;
import gdut.edu.datingforballsports.view.viewholder.OtherHomePageView;

public class OtherHomePagePresenter extends BasePresenter {
    public OtherHomePagePresenter(OtherHomePageView otherHomePageView) {
        this.model = new OtherHomePageModel();
        this.viewReference = new WeakReference<View_>(otherHomePageView);
    }

    public void getList(int userId, String token) {
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    ((OtherHomePageModel) model).sendAddFriendRequest(userId, token, new OtherHomePageListener() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onFails(String msg) {

                        }
                    });
                }
            });
        }
    }
}
