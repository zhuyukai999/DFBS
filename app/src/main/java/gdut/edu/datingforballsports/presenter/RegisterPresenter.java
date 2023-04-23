package gdut.edu.datingforballsports.presenter;

import java.lang.ref.WeakReference;

import gdut.edu.datingforballsports.domain.User;
import gdut.edu.datingforballsports.model.Listener.RegisterListener;
import gdut.edu.datingforballsports.model.RegisterModel;
import gdut.edu.datingforballsports.util.EmailUtils;
import gdut.edu.datingforballsports.view.RegisterView;
import gdut.edu.datingforballsports.view.View_;

public class RegisterPresenter extends BasePresenter {
    public RegisterPresenter(RegisterView registerView) {
        this.model = new RegisterModel();
        this.viewReference = new WeakReference<View_>(registerView);
    }

    public void register() {
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            RegisterView registerView = (RegisterView) viewReference.get();
            if (EmailUtils.isEmail(registerView.getEmail().trim()) && registerView.getEmail().trim().length() <= 31) {
                if (registerView.getPassword().equals(registerView.getPasswordAG())
                        && !registerView.getUserName().equals("") && !registerView.getPassword().equals("")
                        && registerView.getSex().equals("") && !registerView.getBirthday().equals("")
                        && !registerView.getEmail().equals("") && !registerView.getPhoneNumber().equals("")) {
                    User user = new User(registerView.getUserName(), registerView.getPassword(), registerView.getSex(),
                            registerView.getBirthday(), registerView.getEmail(), registerView.getPhoneNumber());
                    ((RegisterModel) model).register(user, new RegisterListener() {  //数据经过筛选后交由M进行判断是否注册成功
                        @Override
                        public void onSuccess() {
                            registerView.onRegisterSuccess();
                        }

                        @Override
                        public void onFails() {
                            registerView.onRegisterFails();
                        }
                    });
                    //TODO
                    //还没有将注册信息储存起来
                    registerView.onRegisterSuccess();
                } else {
                    registerView.onRegisterFails();
                }
            } else {
                registerView.EmailFalse();
            }
        }//TODO
        // 以后完善一下else的内容，感觉是要抛错误
    }

}
