package gdut.edu.datingforballsports.model;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import gdut.edu.datingforballsports.domain.MatchingItem;
import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.model.Listener.MatchingListener;
import gdut.edu.datingforballsports.util.HttpUtils;
import gdut.edu.datingforballsports.util.TextUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MatchingModel implements Model_ {

    public void getTrendsList(int userId, String token, MatchingListener listener) {
        if (userId >= 1 && token != null) {
            String path = "http://192.168.126.1:8080/matching/getMatching/" + userId;
            HttpUtils.sendHttpRequestPostWithTokenAndId(path, token, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                    if (TextUtils.isEmpty(responseData)) {
                        listener.onLoadFails("加载失败");
                        return;
                    }
                    Gson gson = new Gson();
                    MatchingItem matchingItem;
                    ArrayList<MatchingItem> matchingItemList = new ArrayList<>();
                    try {
                        JSONArray jsonArray = new JSONArray(responseData);
                        for (int i = 0; i < jsonArray.length(); i++) {
//                          将Json数组中的元素，拿出来转换成Json对象
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                          将Json对象转换成实体对象，并加入数组
                            matchingItem = gson.fromJson(String.valueOf(jsonObject), MatchingItem.class);
                            matchingItemList.add(matchingItem);
                        }
                        listener.onLoadSuccess(matchingItemList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else {
            listener.onLoadFails("加载失败");
            return;
        }
    }
}
