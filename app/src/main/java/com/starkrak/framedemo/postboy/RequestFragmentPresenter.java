package com.starkrak.framedemo.postboy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.starkrak.framedemo.App;
import com.starkrak.framedemo.RxPresenter;
import com.starkrak.framedemo.postboy.utils.BaseApiRetrofit;

import net.gtr.framework.rx.ProgressObserverImplementation;
import net.gtr.framework.rx.RxHelper;
import net.gtr.framework.util.Loger;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author caroline
 */
public class RequestFragmentPresenter extends RxPresenter<RequestFrementContract.View> implements RequestFrementContract.Presenter {
    public static final MediaType JSON = MediaType.parse("application/json");
    private RequestService requestService;

    @Inject
    RequestFragmentPresenter(App app) {
        this.app = app;
        Gson gson = new GsonBuilder().setLenient().create();
        HttpUrl httpUrl = HttpUrl.parse("http://www.google.com");
        BaseApiRetrofit baseApiRetrofit = new BaseApiRetrofit();
        requestService = new Retrofit.Builder().
                baseUrl(httpUrl).
                client(baseApiRetrofit.getClient()).
                addConverterFactory(GsonConverterFactory.create(gson)).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                build().
                create(RequestService.class);
    }


    @Override
    public void send() {
        RequestBody body = RequestBody.create(JSON, getView().getBody());
        Map<String, String> headsMap = getView().getHeads();
        Request.Builder builder = new Request.Builder();

        for (Map.Entry<String, String> entry : headsMap.entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }

        builder.url(getView().getUrl()).post(body);

        final Request request = builder.build();
        Loger.i("request:" + request.toString());
        try {
            Loger.i("request:" + request.body().contentLength());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Observable<AppResponse> responseObservable = Observable.just(request)
                .map(req -> requestService.httpPost(getView().getUrl(), req.body()))
                .map(responseBodyCall -> {
                    retrofit2.Response<ResponseBody> execute = responseBodyCall.execute();
                    return new AppResponse(execute);
                });
        Observer<AppResponse> responseObserver = new ProgressObserverImplementation<AppResponse>(this) {
            @Override
            protected void onBegin() {
                super.onBegin();
                getView().getPostExecutCallBack().startExecute();
            }

            @Override
            protected void onRelease() {
                super.onRelease();
                getView().getPostExecutCallBack().endExecute();
            }

            @Override
            public void onNext(AppResponse response) {
                super.onNext(response);
                Loger.i("response:" + response.toString());
                getView().getPostExecutCallBack().handleResponse(response);
            }
        };
        RxHelper.bindOnUI(responseObservable, responseObserver);
    }
}
