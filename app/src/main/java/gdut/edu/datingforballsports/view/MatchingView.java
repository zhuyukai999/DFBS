package gdut.edu.datingforballsports.view;

import java.util.List;

import gdut.edu.datingforballsports.domain.MatchingItem;

public interface MatchingView extends View_{
    void onLoadSuccess(List<MatchingItem> list);

    void onLoadFails(String msg);
}
