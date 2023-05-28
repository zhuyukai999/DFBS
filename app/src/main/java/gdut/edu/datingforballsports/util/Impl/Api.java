package gdut.edu.datingforballsports.util.Impl;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {
    @Multipart
    @POST("user/register")
    Call<ResponseBody> getUserRegisterJsonData(@Part("map") RequestBody map, @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("user/modifyAccount")
    Call<ResponseBody> accountSettingJsonData(@Header("token") String token, @Body Map<String, String> userMessageList, @Part("image") MultipartBody.Part image);
}
