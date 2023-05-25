package gdut.edu.datingforballsports.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gdut.edu.datingforballsports.model.Listener.AccountSettingListener;
import gdut.edu.datingforballsports.util.HttpUtils;
import gdut.edu.datingforballsports.util.TextUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AccountSettingModel implements Model_ {
    public void modifyAccount(int userId, String token, String username, String icon_uri, AccountSettingListener listener) {
        String path = "http://192.168.126.1:8080/user/modifyAccount";
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));
        map.put("username", username);
        HttpUtils.sendHttpRequestPostWithTokenAndId(path, map, token, new Callback() {
            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                String responseData;
                try {
                    responseData = response.body().string();
                    System.out.println("responseData:"+responseData);
                    if (TextUtils.isEmpty(responseData)) {
                        listener.onFails();
                        return;
                    }
                    if (responseData.equals("true")) {
                        String path = "http://192.168.126.1:8080/user/uploadImage";
                        HttpUtils.sendImage(path, userId, token, icon_uri, new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String iconPath = response.body().string();
                                System.out.println("iconPath:" + iconPath);
                                listener.onSuccess();
                            }

                            @Override
                            public void onFailure(Call call, IOException e) {
                                System.out.println(e);
                                listener.onFails();
                                return;
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                listener.onFails();
            }
        });

    }
}
