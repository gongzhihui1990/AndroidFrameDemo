package com.starkrak.framedemo.postboy;

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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author caroline
 */
public class RequestFragmentPresenter extends RxPresenter<RequestFrementContract.View> implements RequestFrementContract.Presenter {
    public static final MediaType JSON = MediaType.parse("application/json");

    @Inject
    RequestFragmentPresenter(App app) {
        this.app = app;
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

        Request request = builder.build();
        Loger.i("request:" + request.toString());
        try {
            Loger.i("request:" + request.body().contentLength());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Observable<Response> responseObservable = Observable.just(request)
                .map(req ->       new OkHttpClient().newCall(req).execute());
        Observer<Response> responseObserver = new ProgressObserverImplementation<Response>(this) {
            @Override
            public void onNext(Response response) {
                super.onNext(response);
                Loger.i("response:" + response.toString());
            }
        };
        RxHelper.bindOnUI(responseObservable, responseObserver);
    }
}
