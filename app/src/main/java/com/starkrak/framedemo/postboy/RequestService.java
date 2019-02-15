package com.starkrak.framedemo.postboy;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @author caroline
 */
public interface RequestService {
    @GET
    Call<ResponseBody> httpGet(@Url String url);

    @POST
    Call<ResponseBody> httpPost(@Url String url, @Body RequestBody body);
}
