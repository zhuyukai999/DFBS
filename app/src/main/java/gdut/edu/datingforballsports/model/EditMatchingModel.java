package gdut.edu.datingforballsports.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gdut.edu.datingforballsports.domain.MatchingItem;
import gdut.edu.datingforballsports.model.Listener.EditMatchingListener;
import gdut.edu.datingforballsports.util.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EditMatchingModel implements Model_ {
    private Gson gson = new Gson();

    public void uploadMatching(int userId, String token, String ballType, String memberNum,String city, EditMatchingListener listener) {
        String path = "http://192.168.126.1:8080/matching/uploadMatch/" + userId;

        MatchingItem matchingItem = new MatchingItem(Integer.valueOf(memberNum),1,userId,ballType,city);
        String s = gson.toJson(matchingItem);
        HttpUtils.sendHttpRequestWithTokenAndId(path, s, token, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFails("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body().string().equals("true")) {
                    listener.onSuccess("请求成功");
                    return;
                }
                listener.onFails("请求失败");
            }
        });
    }

    public void cancelMatching(int userId, EditMatchingListener editMatchingListener) {

    }
}
