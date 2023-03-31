package gdut.edu.datingforballsports.view;

import android.view.View;

public interface RegisterView extends View_ {
    String getUserName();
    String getPassword();
    String getPasswordAG();
    String getSex();
    String getBirthday();
    String getEmail();
    String getPhoneNumber();
    void onRegisterSuccess();
    void onRegisterFails();
    void EmailFalse();
}
