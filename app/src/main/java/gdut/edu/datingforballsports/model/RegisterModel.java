package gdut.edu.datingforballsports.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Handler;

import gdut.edu.datingforballsports.domain.User;
import gdut.edu.datingforballsports.model.Listener.RegisterListener;
import gdut.edu.datingforballsports.util.HttpUtils;
import gdut.edu.datingforballsports.util.TextUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RegisterModel implements Model_ {

    public void register(User user, RegisterListener listener) {
        if (listener == null || user == null) {
            listener.onFails();
        }
        String path = "http://192.168.126.1:8080/user/register";
        System.out.println(user);
        HttpUtils.sendHttpRequestPost(path, user, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFails();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.body() == null) {
                        listener.onFails();
                        return;
                    }
                    String responseData = response.body().string();
                    System.out.println("responseData:"+responseData);
                    if (TextUtils.isEmpty(responseData)) {
                        listener.onFails();
                        return;
                    }
                    JSONObject jsonObject = new JSONObject(responseData);
                    int userId = Integer.parseInt(String.valueOf(jsonObject.get("userId")));
                    String token = String.valueOf(jsonObject.get("token"));
                    String path = "http://192.168.126.1:8080/user/uploadImage";
                    HttpUtils.sendImage(path, userId, token, user.getIcon(), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            System.out.println(e);
                            System.out.println("areyouwin");
                            listener.onFails();
                            return;
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String iconPath = response.body().string();
                            System.out.println("iconPath:"+iconPath);
                            listener.onSuccess(token, userId);
                        }
                    });

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
       /* {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("1111111111");
                try {
                    if (response.body() == null) {
                        listener.onFails();
                        return;
                    }
                    String responseData = response.body().string();
                    if (TextUtils.isEmpty(responseData)) {
                        listener.onFails();
                        return;
                    }
                    JSONArray jsonArray = new JSONArray(responseData);
                    int userId = Integer.parseInt(String.valueOf(jsonArray.getJSONObject(0)));
                    String token = String.valueOf(jsonArray.getJSONObject(1));
                    listener.onSuccess(token, userId);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("3333333333333");
                listener.onFails();
            }
        });*/
    }
}
