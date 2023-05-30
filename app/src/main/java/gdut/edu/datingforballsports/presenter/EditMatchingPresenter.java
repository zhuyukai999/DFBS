package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;

import gdut.edu.datingforballsports.model.EditMatchingModel;
import gdut.edu.datingforballsports.model.Listener.EditMatchingListener;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.EditMatchingView;
import gdut.edu.datingforballsports.view.View_;

public class EditMatchingPresenter extends BasePresenter {
    public EditMatchingPresenter(EditMatchingView editMatchingView) {
        this.model = new EditMatchingModel();
        this.viewReference = new WeakReference<View_>(editMatchingView);
    }

    public void uploadMatchingRequest(int userId, String token, String ballType, String memberNum,String city) {
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    ((EditMatchingModel) model).uploadMatching(userId, token,ballType,memberNum,city, new EditMatchingListener() {

                        @Override
                        public void onSuccess(String msg) {
                            ((EditMatchingView) viewReference.get()).onUploadSuccess();
                        }

                        @Override
                        public void onFails(String msg) {
                            ((EditMatchingView) viewReference.get()).onUploadFails(msg);
                        }
                    });
                }
            });
        }

    }

    public void cancelMatchingRequest(int userId) {
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    ((EditMatchingModel) model).cancelMatching(userId, new EditMatchingListener() {

                        @Override
                        public void onSuccess(String msg) {
                        }

                        @Override
                        public void onFails(String msg) {
                            ((EditMatchingView) viewReference.get()).onUploadFails(msg);
                        }
                    });
                }
            });

        }
    }
}
