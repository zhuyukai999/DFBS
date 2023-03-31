package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;

import gdut.edu.datingforballsports.model.Lisentener.LoginListener;
import gdut.edu.datingforballsports.model.LoginModel;
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
            loginView = null;
            ((LoginModel) model).login(userName, password, new LoginListener() {
                @Override
                public void onSuccess(int userId) {
                    if (viewReference.get() != null) {
                        ((LoginView)viewReference.get()).onLoginSuccess(userId);
                    }
                }

                @Override
                public void onFails() {
                    if (viewReference.get() != null) {
                        ((LoginView)viewReference.get()).onLoginFails();
                    }
                }
            });
        }
    }
}
