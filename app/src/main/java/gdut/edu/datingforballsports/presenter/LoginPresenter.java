package gdut.edu.datingforballsports.presenter;

import android.content.Context;
import android.os.Looper;

import java.lang.ref.WeakReference;

import gdut.edu.datingforballsports.model.Listener.LoginListener;
import gdut.edu.datingforballsports.model.LoginModel;
import gdut.edu.datingforballsports.util.GlideEngine;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.LoginView;
import gdut.edu.datingforballsports.view.View_;

public class LoginPresenter extends BasePresenter {
    private Context context;

    public LoginPresenter(LoginView loginView, Context context) {
        this.model = new LoginModel();
        this.viewReference = new WeakReference<View_>(loginView);
        this.context = context;
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
                        public void onSuccess(int userId, String token, String msg, String icon, String userName) {
                            if (viewReference.get() != null) {
                                String storePath = context.getFilesDir().getAbsolutePath() + "/user" + userId + "/icon" + "/user" + userId + ".png";
                                GlideEngine.createGlideEngine().saveImage(context, icon, storePath);
                                ((LoginView) viewReference.get()).onLoginSuccess(userId, token, storePath, userName);
                            }
                        }

                        @Override
                        public void onFails(String mag) {
                            System.out.println("Presenter:" + Thread.currentThread().getId());
                            if (viewReference.get() != null) {
                                ((LoginView) viewReference.get()).onLoginFails();
                            }
                        }
                    });
                }
            });
        }
    }

}
