package gdut.edu.datingforballsports.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.domain.User;
import gdut.edu.datingforballsports.model.Listener.HomePageListener;
import gdut.edu.datingforballsports.util.HttpUtils;
import gdut.edu.datingforballsports.util.TextUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomePageModel implements Model_ {

    private String msg = null;

    public void getUserMessageByUserIdAndToken(int userId, String token, HomePageListener listener) {
        if (userId == -1) {
            msg = "请先登录";
            listener.onFails(msg);
        }
        String path = "http://192.168.126.1:8080/user/loginByIdAndToken";
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));
        HttpUtils.sendHttpRequestPostWithTokenAndId(path, map,token, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                msg = "ERROR";
                listener.onFails(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                if (TextUtils.isEmpty(responseData)) {
                    msg = "ERROR";
                    listener.onFails(msg);
                }
                Gson gson = new Gson();
                JSONObject returnData = null;
                try {
                    returnData = new JSONObject(responseData);
                    User user = gson.fromJson(String.valueOf(returnData), User.class);
                    listener.onSuccess(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
