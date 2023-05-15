package gdut.edu.datingforballsports.model;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gdut.edu.datingforballsports.domain.CommentDetail;
import gdut.edu.datingforballsports.model.Listener.PostDetailsListener;
import gdut.edu.datingforballsports.util.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PostDetailsModel implements Model_ {
    private String msg = null;

    public void determineUserIdAndToken(int userId, String token, int postId, PostDetailsListener listener) {
        String path = "http://192.168.126.1:8080/user/detailPost";
        Map<String, Integer> map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", postId);
        HttpUtils.sendHttpRequestPostWithTokenAndId(path, map, token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFails("获取失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Gson gson = new Gson();
                    List<CommentDetail> list = new ArrayList<>();
                    String responseData = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        list.add(gson.fromJson(String.valueOf(jsonObject), CommentDetail.class));
                    }
                    listener.onSuccess(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
