package gdut.edu.datingforballsports.model.Listener;

import java.util.List;

import gdut.edu.datingforballsports.domain.CommentDetail;

public interface PostDetailsListener {
    void onSuccess(List<CommentDetail> list);
    void onSuccess(String msg);
    void onFails(String msg);
}
