package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;

import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.model.Listener.PublishPostListener;
import gdut.edu.datingforballsports.model.LoginModel;
import gdut.edu.datingforballsports.model.PublishPostModel;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.LoginView;
import gdut.edu.datingforballsports.view.PublishPostView;
import gdut.edu.datingforballsports.view.View_;

public class PublishPostPresenter extends BasePresenter {
    public PublishPostPresenter(PublishPostView publishPostView) {
        this.model = new PublishPostModel();
        this.viewReference = new WeakReference<View_>(publishPostView);
    }

    public void PublishPost(Post post, String token) {
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            PublishPostView publishPostView = (PublishPostView) viewReference.get();
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    ((PublishPostModel) model).uploadPost(post, token, new PublishPostListener() {
                        @Override
                        public void onSuccess() {
                            publishPostView.onLoginSuccess();
                        }

                        @Override
                        public void onFails(String msg) {
                            publishPostView.onLoginFails(msg);
                        }
                    });
                }
            });
        }
    }
}
