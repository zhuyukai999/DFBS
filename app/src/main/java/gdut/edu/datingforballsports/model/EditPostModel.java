package gdut.edu.datingforballsports.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gdut.edu.datingforballsports.model.Listener.EditPostListener;
import gdut.edu.datingforballsports.util.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EditPostModel implements Model_ {

    public void uploadPost(int userId, String token, String postContent, String ballType, String city, EditPostListener listener) {
        String path = "http://192.168.126.1:8080/forum/uploadPost/" + userId;
        Map<String, String> map = new HashMap<>();
        map.put("postContent", postContent);
        map.put("ballType", ballType);
        map.put("city", city);
        HttpUtils.sendHttpRequestPostWithTokenAndId(path, map, token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("e:" + e);
                listener.onFails("上传失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                /*if (response.body().string() != null) {
                    listener.onSuccess();
                }*/
                System.out.println("response:" + response);
                System.out.println("call:" + call);
                if (response.body().string().equals("true")) {
                    listener.onSuccess();
                    return;
                }
                listener.onFails("上传失败");
            }
        });
    }
}
