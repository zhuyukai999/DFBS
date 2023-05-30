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
import gdut.edu.datingforballsports.model.Listener.ForumListListener;
import gdut.edu.datingforballsports.util.HttpUtils;
import gdut.edu.datingforballsports.util.TextUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ForumListModel implements Model_ {
    private String msg = null;

    public void getForumList(int userId, String token, ForumListListener listener) {
        if (userId >= 1 && token != null) {
            String path = "http://192.168.126.1:8080/forum/getForumPost/" + userId;
            Map<String, String> map = new HashMap<>();
            HttpUtils.sendHttpRequestPostWithTokenAndId(path, map, token, new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    msg = "请求失败";
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
                        listener.onSuccess(postList, msg);
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

    public void getForumList(int userId, String token, String ballType, String city, ForumListListener listener) {
        if (userId >= 1) {
            String path = "http://192.168.126.1:8080/forum/getScreenPost/" + userId;
            Map<String, String> map = new HashMap<>();
            if (ballType != null) {
                map.put("ballType", ballType);
            }
            if (city != null) {
                map.put("city", city);
            }
            HttpUtils.sendHttpRequestPostWithTokenAndId(path, map, token, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    msg = "请求失败";
                    listener.onFails(msg);
                    return;
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
                    ArrayList<Post> postList = new ArrayList<>();
                    try {
                        System.out.println("responseData:"+responseData);
                        JSONArray jsonArray = new JSONArray(responseData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            Post post = gson.fromJson(String.valueOf(jsonObject), Post.class);
                            postList.add(post);
                        }
                        listener.onSuccess(postList, msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            listener.onFails(msg);
        }

    }

    public void collectPost(int userId, String token, int postId) {
        String path = "http://192.168.126.1:8080/forum/collectPost/" + userId;
        Map<String, String> map = new HashMap<>();
        map.put("postId", String.valueOf(postId));
        HttpUtils.sendHttpRequestPostWithTokenAndId(path, map, token, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("collectPostresponse:"+response.body());
            }
        });
    }
}
