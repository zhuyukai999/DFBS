package gdut.edu.datingforballsports.view;

public interface LoginView extends View_ {
    String getUserName();
    String getPassword();
    void onLoginSuccess(int userId);
    void onLoginFails();
}
