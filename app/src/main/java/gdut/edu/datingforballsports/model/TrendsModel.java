package gdut.edu.datingforballsports.model;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.model.Listener.TrendsListener;
import gdut.edu.datingforballsports.util.HttpUtils;
import gdut.edu.datingforballsports.util.TextUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TrendsModel implements Model_ {
    private String msg = null;

    public void getTrendsList(int userId, String token, TrendsListener listener) {
        if (userId >= 1 && token != null) {
            String path = "http://192.168.126.1:8080/forum/getUserTrends/" + userId;
            Map<String, String> map = new HashMap<>();
            HttpUtils.sendHttpRequestPostWithTokenAndId(path, map, token, new Callback() {

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
                        return;
                    }
                    Gson gson = new Gson();
                    Post post;
                    ArrayList<Post> postList = new ArrayList<>();
                    try {
                        JSONArray jsonArray = new JSONArray(responseData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            post = gson.fromJson(String.valueOf(jsonObject), Post.class);
                            postList.add(post);
                        }
                        listener.onSuccess(postList, "获取成功");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            msg = "请求失败";
            listener.onFails(msg);
        }
    }
}
