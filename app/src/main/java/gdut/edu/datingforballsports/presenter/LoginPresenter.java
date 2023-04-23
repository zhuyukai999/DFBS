package gdut.edu.datingforballsports.presenter;

import android.os.Looper;

import java.lang.ref.WeakReference;

import gdut.edu.datingforballsports.model.Listener.LoginListener;
import gdut.edu.datingforballsports.model.LoginModel;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.LoginView;
import gdut.edu.datingforballsports.view.View_;

public class LoginPresenter extends BasePresenter {
    public LoginPresenter(LoginView loginView) {
        this.model = new LoginModel();
        this.viewReference = new WeakReference<View_>(loginView);
    }

    public void login() {
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            LoginView loginView = (LoginView) viewReference.get();
            String userName = loginView.getUserName();
            String password = loginView.getPassword();
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    ((LoginModel) model).sendLoginRequest(userName, password, new LoginListener() {
                        @Override
                        public void onSuccess(int userId, String token, String msg) {
                            if (viewReference.get() != null) {
                                loginView.onLoginSuccess(userId, token);
                            }
                        }

                        @Override
                        public void onFails(String mag) {
                            System.out.println("Presenter:" + Looper.myLooper() + "||||||||" + Looper.getMainLooper());
                            System.out.println("Presenter:" + Thread.currentThread().getId());
                            if (viewReference.get() != null) {
                                loginView.onLoginFails();
                            }
                        }
                    });
                }
            });
        }
    }

}
