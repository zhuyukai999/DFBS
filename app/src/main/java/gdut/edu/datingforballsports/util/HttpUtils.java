package gdut.edu.datingforballsports.util;

import android.os.Looper;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

import gdut.edu.datingforballsports.domain.Post;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtils {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public static final Gson gson = new Gson();

    public static void sendHttpRequestPost(String path, String json, Callback callback) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(JSON, json);


        Request request = new Request.Builder()
                .url(path)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendHttpRequestPost(String path, Map map, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        JSONObject json = new JSONObject(map);
        FormBody formBody = new FormBody.Builder().add("msg", String.valueOf(json)).build();
        Request request = new Request.Builder()
                .url(path)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendHttpRequestPostWithToken(String path, Map map,String token, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        JSONObject json = new JSONObject(map);
        FormBody formBody = new FormBody.Builder().add("msg", String.valueOf(json)).build();
        Request request = new Request.Builder()
                .url(path)
                .header("token",token)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendHttpRequestPostWithToken(String path, Post post, String token, Callback callback) {
        if (post != null) {
            OkHttpClient client = new OkHttpClient();
            String s = gson.toJson(post);
            List<String> imagePaths = post.getImagePaths();
            MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
            multipartBodyBuilder.setType(MultipartBody.FORM);
            multipartBodyBuilder.addFormDataPart("post", s);
            if (imagePaths.size() != 0) {
                for (int i = 1; i <= imagePaths.size(); i++) {
                    File file = new File(imagePaths.get(i - 1));
                    multipartBodyBuilder.addFormDataPart("images", "image" + i, RequestBody.create(MEDIA_TYPE_PNG, file));
                }
            }
            RequestBody requestBody = multipartBodyBuilder.build();
            Request.Builder RequestBuilder = new Request.Builder();
            RequestBuilder.url(path);// 添加URL地址
            RequestBuilder.header("token",token);
            RequestBuilder.post(requestBody);
            Request request = RequestBuilder.build();
            client.newCall(request).enqueue(callback);
        }

    }
}
