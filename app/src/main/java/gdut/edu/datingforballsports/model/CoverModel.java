package gdut.edu.datingforballsports.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gdut.edu.datingforballsports.model.Listener.CoverListener;
import gdut.edu.datingforballsports.util.HttpUtils;
import gdut.edu.datingforballsports.util.TextUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CoverModel implements Model_ {
    private String msg = null;

    public void determineUserIdAndToken(String userId, String token, CoverListener listener) {
        String path = "http://192.168.126.1:8080/user/loginByIdAndToken";
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        HttpUtils.sendHttpRequestPost(path, map, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                msg = "请重新登录";
                listener.onFails(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                if (TextUtils.isEmpty(responseData)) {
                    msg = "请重新登录";
                    listener.onFails(msg);
                }
                if(Boolean.parseBoolean(responseData)){
                    msg = "登录成功";
                    listener.onSuccess(msg);
                }
                msg = "请重新登录";
                listener.onFails(msg);
            }
        });
    }
}
