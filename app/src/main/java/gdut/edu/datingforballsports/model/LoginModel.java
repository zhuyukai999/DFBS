package gdut.edu.datingforballsports.model;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gdut.edu.datingforballsports.model.Listener.LoginListener;
import gdut.edu.datingforballsports.util.HttpUtils;
import gdut.edu.datingforballsports.util.TextUtils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginModel implements Model_ {
    //TODO
    //等服务器弄好再完善登录功能
    private String username = "1";
    private String password = "1";
    private String msg = null;

    public void sendLoginRequest(String username, String password, LoginListener listener) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            msg = "账号或密码不能为空";
            listener.onFails(msg);
            return;
        }
        String path = "http://192.168.126.1:8080/user/login";
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        HttpUtils.sendHttpRequestPost(path, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                msg = "ERROR";
                listener.onFails(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                if (TextUtils.isEmpty(responseData)) {
                    msg = "用户名或密码错误";
                    listener.onFails(msg);
                }
                HashMap<String, String> dataMap = new HashMap<>();
                int userId = -1;
                String token = null;
                String icon = null;
                try {
                    System.out.println("responseData:" + responseData);
                    /*JsonObject returnData = new JsonParser().parse(responseData).getAsJsonObject();
                    returnData.get("userId").getAsString();
                    returnData.get("token").getAsString();*/
                    JSONArray jsonArray = new JSONArray(responseData);
                    userId = Integer.parseInt(String.valueOf(jsonArray.getJSONObject(0)));
                    token = String.valueOf(jsonArray.getJSONObject(1));
                    icon = String.valueOf(jsonArray.getJSONObject(2));
                } catch (JSONException e) {
                    msg = "ERROR";
                    listener.onFails(msg);
                    e.printStackTrace();
                }
                msg = "登录成功";
                //还要有token
                listener.onSuccess(userId, token, msg, icon);
            }
        });
    }
}
