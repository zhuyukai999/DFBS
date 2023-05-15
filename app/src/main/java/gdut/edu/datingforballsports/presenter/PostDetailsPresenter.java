package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;
import java.util.List;

import gdut.edu.datingforballsports.domain.CommentDetail;
import gdut.edu.datingforballsports.model.CoverModel;
import gdut.edu.datingforballsports.model.Listener.PostDetailsListener;
import gdut.edu.datingforballsports.model.PostDetailsModel;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.CoverView;
import gdut.edu.datingforballsports.view.PostDetailsView;
import gdut.edu.datingforballsports.view.View_;

public class PostDetailsPresenter extends BasePresenter {
    public PostDetailsPresenter(PostDetailsView postDetailsView) {
        this.model = new PostDetailsModel();
        this.viewReference = new WeakReference<View_>(postDetailsView);
    }

    public void generateTestData(int userId, String token, int postId) {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                ((PostDetailsModel) model).determineUserIdAndToken(userId,token,postId, new PostDetailsListener(){

                    @Override
                    public void onSuccess(List<CommentDetail> list) {
                        ((PostDetailsView) viewReference.get()).onLoadSuccess(list);
                    }

                    @Override
                    public void onFails(String msg) {
                        ((PostDetailsView) viewReference.get()).onLoadFails(msg);
                    }
                });
            }
        });
    }
}
