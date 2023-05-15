package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;

import gdut.edu.datingforballsports.domain.User;
import gdut.edu.datingforballsports.model.HomePageModel;
import gdut.edu.datingforballsports.model.Listener.HomePageListener;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.HomePageView;
import gdut.edu.datingforballsports.view.View_;

public class HomePagePresenter extends BasePresenter {
    public HomePagePresenter(HomePageView HomePageView) {
        this.model = new HomePageModel();
        this.viewReference = new WeakReference<View_>(HomePageView);
    }

    public void getUserMessage(int userId, String token) {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                ((HomePageModel) model).getUserMessageByUserIdAndToken(userId, token, new HomePageListener() {

                    @Override
                    public void onSuccess(Object object) {
                        ((HomePageView) viewReference.get()).onLoadUserMessageSuccess((User) object);
                    }

                    @Override
                    public void onFails(String msg) {
                        ((HomePageView) viewReference.get()).onLoadFails(msg);
                    }
                });
            }
        });
    }
}
