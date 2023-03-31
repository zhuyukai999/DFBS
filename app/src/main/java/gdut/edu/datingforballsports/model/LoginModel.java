package gdut.edu.datingforballsports.model;

import gdut.edu.datingforballsports.model.Lisentener.LoginListener;

public class LoginModel implements Model_ {
    //TODO
    //等服务器弄好再完善登录功能
    private String username = "1";
    private String password = "1";
    private int userId = 1;
    public void login(String username, String password, LoginListener listener) {
        if (listener == null) {
            return;
        }
        if (this.username.equals(username)&&this.password.equals(password)){
            listener.onSuccess(userId);
        }else{
            listener.onFails();
        }
    }
}
