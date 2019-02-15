package com.starkrak.framedemo.postboy;

import com.starkrak.framedemo.BaseModel;

import net.gtr.framework.util.Loger;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author caroline
 */
public class AppResponse extends BaseModel {
    private int code;
    private String body;
    private long time;
    private Headers headers;

    AppResponse(Response<ResponseBody> execute) {
        super();
        ResponseBody responseBody = execute.body();
        try {
            this.body = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        okhttp3.Response response = execute.raw();
        this.time = response.receivedResponseAtMillis() - response.sentRequestAtMillis();
        this.code = response.code();
        this.headers = response.headers();
    }

    public Headers getHeaders() {
        return headers;
    }

    public int getCode() {
        return code;
    }

    public long getTime() {
        return time;
    }

    public String getBody() {
        return body;
    }


}
