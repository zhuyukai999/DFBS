package gdut.edu.datingforballsports.util;

import android.icu.text.IDNA;
import android.os.Looper;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gdut.edu.datingforballsports.domain.Data;
import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.domain.User;
import gdut.edu.datingforballsports.util.Impl.Api;
import retrofit2.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HttpUtils {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public static final Gson gson = new Gson();
    private static Retrofit mRetrofit = new Retrofit.Builder().baseUrl("http://192.168.126.1:8080/").build();

    public static void sendHttpRequestPost(String path, String json, Callback callback) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(JSON, json);


        Request request = new Request.Builder()
                .url(path)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //无id,传图片用的，用在注册
    public static void sendHttpRequestPost(String path, User user, retrofit2.Callback<ResponseBody> callback) {
        File file = new File(user.getIcon());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        Map<String,String> map = new HashMap<>();
        map.put("userName",user.getUserName());
        map.put("password",user.getPassword());
        map.put("sex",user.getSex());
        map.put("birthday",user.getBirthday());
        map.put("Email",user.getEmail());
        map.put("phoneNumber",user.getPhone());
        mRetrofit.create(Api.class).getUserRegisterJsonData(map,imageBody).enqueue(callback);
    }

    //更改用户的信息用的
    public static void sendHttpRequestPost(String path, int userId, String token, String userName, String icon_uri, retrofit2.Callback<ResponseBody> callback) {
        File file = new File(icon_uri);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        Map<String,String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));
        map.put("userName",userName);
        mRetrofit.create(Api.class).accountSettingJsonData(token,map,imageBody).enqueue(callback);
    }

    //没什么要传给服务端的，用这个
    public static void sendHttpRequestPost(String path, Map map, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        JSONObject json = new JSONObject(map);
        FormBody formBody = new FormBody.Builder().add("msg", json.toString()).build();
        Request request = new Request.Builder()
                .url(path)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //需要传点数据的用这个
    public static void sendHttpRequestPostWithTokenAndId(String path, Map map, String token, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        JSONObject json = new JSONObject(map);
        FormBody formBody = new FormBody.Builder().add("msg", json.toString()).build();
        Request request = new Request.Builder()
                .url(path)
                .header("token", token)
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
            RequestBuilder.header("token", token);
            RequestBuilder.post(requestBody);
            Request request = RequestBuilder.build();
            client.newCall(request).enqueue(callback);
        }
    }

}
