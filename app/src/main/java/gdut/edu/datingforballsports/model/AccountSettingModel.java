package gdut.edu.datingforballsports.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import gdut.edu.datingforballsports.model.Listener.AccountSettingListener;
import gdut.edu.datingforballsports.util.HttpUtils;
import gdut.edu.datingforballsports.util.TextUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class AccountSettingModel implements Model_ {
    public void modifyAccount(int userId, String token, String userName, String icon_uri, AccountSettingListener listener) {
        String path = "user/modifyAccount";
        HttpUtils.sendHttpRequestPost(path, userId, token, userName, icon_uri, new retrofit2.Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responseData = null;
                try {
                    responseData = response.body().string();
                    if (TextUtils.isEmpty(responseData)) {
                        listener.onFails();
                    }
                    JSONObject jsonObject = new JSONObject(responseData);
                    if (jsonObject.getBoolean("setupComplete")) {
                        listener.onSuccess();
                    }
                    listener.onFails();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFails();
            }
        });
    }
}
