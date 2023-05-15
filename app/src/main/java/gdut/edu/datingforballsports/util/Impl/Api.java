package gdut.edu.datingforballsports.util.Impl;

import android.icu.text.IDNA;

import java.util.List;
import java.util.Map;

import gdut.edu.datingforballsports.domain.Data;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {
    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseBody> getUserRegisterJsonData(@Body Map<String, String> userMessageList, @Part("image") MultipartBody.Part image);

    @FormUrlEncoded
    @POST("user/modifyAccount")
    Call<ResponseBody> accountSettingJsonData(@Header("token") String token, @Body Map<String, String> userMessageList, @Part("image") MultipartBody.Part image);
}
