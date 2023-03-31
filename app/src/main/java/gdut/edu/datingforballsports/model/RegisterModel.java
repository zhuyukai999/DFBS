package gdut.edu.datingforballsports.model;

import gdut.edu.datingforballsports.domain.User;
import gdut.edu.datingforballsports.model.Lisentener.RegisterListener;

public class RegisterModel implements Model_ {
    //TODO
    //等服务器做好再完善注册功能
    public void register(User user, RegisterListener listener) {
        if (listener == null) {
            return;
        }
        listener.onSuccess();
    }
}
