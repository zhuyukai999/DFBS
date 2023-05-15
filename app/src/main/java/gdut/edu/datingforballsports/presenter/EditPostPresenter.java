package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;

import gdut.edu.datingforballsports.model.EditPostModel;
import gdut.edu.datingforballsports.model.Listener.EditPostListener;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.View_;
import gdut.edu.datingforballsports.view.activity.EditPostActivity;

public class EditPostPresenter extends BasePresenter {
    public EditPostPresenter(EditPostActivity editPostActivity) {
        this.model = new EditPostModel();
        this.viewReference = new WeakReference<View_>(editPostActivity);
    }

    public void uploadPost(int userId, String token, String postContent, String ballType, String city) {
        if(postContent == null){
            ((EditPostActivity)viewReference.get()).onPostFails("帖子内容不能为空");
        }
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    ((EditPostModel) model).uploadPost(userId, token, postContent, ballType, city, new EditPostListener() {
                        @Override
                        public void onSuccess() {
                            ((EditPostActivity)viewReference.get()).onPostSuccess();
                        }

                        @Override
                        public void onFails(String msg) {
                            ((EditPostActivity)viewReference.get()).onPostFails(msg);
                        }
                    });
                }
            });
        }
    }
}
