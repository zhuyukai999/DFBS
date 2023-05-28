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
import gdut.edu.datingforballsports.domain.ReplyDetail;
import gdut.edu.datingforballsports.model.Listener.PostDetailsListener;
import gdut.edu.datingforballsports.util.HttpUtils;
import gdut.edu.datingforballsports.util.TextUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PostDetailsModel implements Model_ {
    private String msg = null;
    private Gson gson = new Gson();

    public void determineUserIdAndToken(int userId, String token, int postId, PostDetailsListener listener) {
        String path = "http://192.168.126.1:8080/post/detailPost/" + userId;
        Map<String, Integer> map = new HashMap<>();
        map.put("postId", postId);
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
                    if(TextUtils.isEmpty(responseData)){
                        listener.onSuccess(new ArrayList<>());
                        return;
                    }
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

    public void uploadComment(int userId, String token, CommentDetail commentDetail, PostDetailsListener listener) {
        String path = "http://192.168.126.1:8080/post/uploadComment/" + userId;
        String s = gson.toJson(commentDetail);
        HttpUtils.sendHttpRequestWithTokenAndId(path, s, token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFails("上传失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                if (responseData.equals("false")) {
                    listener.onFails("上传失败");
                    return;
                }
                listener.onSuccess("上传成功");
            }
        });
    }

    public void uploadCommentReply(int userId, String token, ReplyDetail detailBean, PostDetailsListener listener) {
        String path = "http://192.168.126.1:8080/post/uploadCommentReply/" + userId;
        String s = gson.toJson(detailBean);
        HttpUtils.sendHttpRequestWithTokenAndId(path, s, token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFails("上传失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                if (responseData.equals("false")) {
                    listener.onFails("上传失败");
                    return;
                }
                listener.onSuccess("上传成功");
            }
        });
    }
}
