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
import okhttp3.Callback;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class RegisterModel implements Model_ {
    //TODO
    //等服务器做好再完善注册功能
    public void register(User user, RegisterListener listener) {
        if (listener == null ||user == null) {
            listener.onFails();
        }
        String path = "user/register";
        HttpUtils.sendHttpRequestPost(path, user, new retrofit2.Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String responseData = response.body().string();
                    if (TextUtils.isEmpty(responseData)) {
                        listener.onFails();
                    }
                    JSONArray jsonArray = new JSONArray(responseData);
                    int userId = Integer.parseInt(String.valueOf(jsonArray.getJSONObject(0)));
                    String token = String.valueOf(jsonArray.getJSONObject(1));
                    listener.onSuccess(token,userId);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        } );
    }
}
