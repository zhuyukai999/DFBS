package gdut.edu.datingforballsports.view;

import java.util.List;

import gdut.edu.datingforballsports.domain.CommentDetail;

public interface PostDetailsView extends View_{

    void onLoadSuccess(List<CommentDetail> list);

    void onLoadFails(String msg_);

    void onLoadSuccess(String msg_);
}
