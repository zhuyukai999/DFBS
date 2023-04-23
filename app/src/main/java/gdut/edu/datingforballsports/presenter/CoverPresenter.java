package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;

import gdut.edu.datingforballsports.model.CoverModel;
import gdut.edu.datingforballsports.model.Listener.CoverListener;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.CoverView;
import gdut.edu.datingforballsports.view.View_;

public class CoverPresenter extends BasePresenter{
    public CoverPresenter(CoverView coverView) {
        this.model = new CoverModel();
        this.viewReference = new WeakReference<View_>(coverView);
    }


    public void getUserMessage(String userId, String token) {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                ((CoverModel) model).determineUserIdAndToken(userId, token, new CoverListener() {

                    @Override
                    public void onSuccess(String msg) {
                        ((CoverView) viewReference.get()).onLoginSuccess(msg);
                    }

                    @Override
                    public void onFails(String msg) {
                        if (viewReference.get() != null) {
                            ((CoverView) viewReference.get()).onLoginFails(msg);
                        }
                    }
                });
            }
        });
    }
}
