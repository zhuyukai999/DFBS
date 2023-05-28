package gdut.edu.datingforballsports.model;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gdut.edu.datingforballsports.domain.Friend;
import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.model.Listener.FriendListener;
import gdut.edu.datingforballsports.model.Listener.TrendsListener;
import gdut.edu.datingforballsports.util.HttpUtils;
import gdut.edu.datingforballsports.util.TextUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FriendModel implements Model_ {
    public void getFriendList(int userId, String token, FriendListener listener) {
        if (userId >= 1 && token != null) {
            String path = "http://192.168.126.1:8080/friend/getFriendList/" + userId;
            Map<String, String> map = new HashMap<>();
            HttpUtils.sendHttpRequestPostWithTokenAndId(path, map, token, new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    listener.onFails("加载失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                    if (TextUtils.isEmpty(responseData)) {
                        listener.onFails("加载失败");
                        return;
                    }
                    Gson gson = new Gson();
                    Friend friend;
                    ArrayList<Friend> friendList = new ArrayList<>();
                    try {
                        JSONArray jsonArray = new JSONArray(responseData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            friend = gson.fromJson(String.valueOf(jsonObject), Friend.class);
                            friendList.add(friend);
                        }
                        listener.onSuccess(friendList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            listener.onFails("加载失败");
        }
    }
}
