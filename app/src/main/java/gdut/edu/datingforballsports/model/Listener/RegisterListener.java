package gdut.edu.datingforballsports.model.Listener;

public interface RegisterListener {
    void onSuccess(String token,int userId);

    void onFails();
}
