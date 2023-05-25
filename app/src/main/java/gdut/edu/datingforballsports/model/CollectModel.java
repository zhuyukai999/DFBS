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
import gdut.edu.datingforballsports.model.Listener.CollectListener;
import gdut.edu.datingforballsports.model.Listener.TrendsListener;
import gdut.edu.datingforballsports.util.HttpUtils;
import gdut.edu.datingforballsports.util.TextUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CollectModel implements Model_ {
    private String msg = null;

    public void getCollectList(int userId, String token, CollectListener listener) {
        if (userId >= 1 && token != null) {
            String path = "http://192.168.126.1:8080/user/getUserCollect/" + userId;
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
                    }
                    Gson gson = new Gson();
                    ArrayList<Post> postList = new ArrayList<>();
                    try {
                        JSONArray jsonArray = new JSONArray(responseData);
                        for (int i = 0; i < jsonArray.length(); i++) {
//                          将Json数组中的元素，拿出来转换成Json对象
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                          将Json对象转换成实体对象，并加入数组
                            Post post = gson.fromJson(String.valueOf(jsonObject), Post.class);
                            postList.add(post);
                            listener.onSuccess(postList, msg);
                        }
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
