package gdut.edu.datingforballsports.view;

public interface CollectView extends View_{
    String getToken();
    int getUserId();
    void onTrendsLoadSuccess(Object list, String RCmsg);
    void onLoadFail(String RCmsg);
}
