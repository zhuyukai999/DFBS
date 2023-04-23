package gdut.edu.datingforballsports.model;

import java.io.IOException;

import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.model.Listener.PublishPostListener;
import gdut.edu.datingforballsports.util.HttpUtils;
import gdut.edu.datingforballsports.util.TextUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PublishPostModel implements Model_ {
    private String msg = null;

    public void uploadPost(Post post, String token, PublishPostListener listener) {
        String path = "http://192.168.126.1:8080/forum/uploadPost";
        if (post == null) {
            msg = "账号或密码不能为空";
            listener.onFails(msg);
            return;
        }
        HttpUtils.sendHttpRequestPostWithToken(path, post, token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                msg = "ERROR";
                listener.onFails(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.onSuccess();
            }
        });
    }
}
