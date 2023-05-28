package gdut.edu.datingforballsports.model.Listener;

import java.util.List;

import gdut.edu.datingforballsports.domain.MatchingItem;

public interface MatchingListener {
    void onLoadSuccess(List<MatchingItem> list);

    void onLoadFails(String msg);
}
