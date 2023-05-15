package gdut.edu.datingforballsports.model.Listener;

public interface LoginListener {
    void onSuccess(int userId, String token, String msg, String icon);

    void onFails(String msg);
}
