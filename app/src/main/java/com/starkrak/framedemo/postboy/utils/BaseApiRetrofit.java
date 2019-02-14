package com.starkrak.framedemo.postboy.utils;

import com.starkrak.framedemo.App;

import net.gtr.framework.app.BaseApp;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * @author caroline
 */
public class BaseApiRetrofit {

    private final OkHttpClient mClient;
    private final OkHttpClient mFileClient;


    BaseApiRetrofit() {
        //cache
        File httpCacheDir = new File(BaseApp.getContext().getCacheDir(), "response");
        // 10 MiB
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDir, cacheSize);
        // cookieJar配置
        PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.getContext()));
        OkHttpClient.Builder mClientBuilder = new OkHttpClient.Builder();
        // 请求头配置
        Interceptor rewriteHeaderControlInterceptor = chain -> {
            Request request = chain.request().newBuilder().addHeader("Content-Type", "application/json").build();
            return chain.proceed(request);
        };

        try {
            //尝试跳过SS认证
            X509TrustManager xtm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    X509Certificate[] x509Certificates = new X509Certificate[0];
                    return x509Certificates;
                }
            };
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
            HostnameVerifier DO_NOT_VERIFY = (hostname, session) -> true;
            mClientBuilder.sslSocketFactory(sslContext.getSocketFactory())
                    .hostnameVerifier(DO_NOT_VERIFY);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //OkHttpClient
        mClient = mClientBuilder.
                addInterceptor(chain -> {
                    Request request = chain.request();
//                    HttpUrl url = request.url();
//                    if (url.toString().startsWith(XiLaiHost)) {
//                        //TODO 给喜来微服务生产服务器Url加后缀（PS:用这种方式替换，我也很无奈，希望有人找到更加合适的方法）
//                        String urlString = url.toString().replace(XiLaiHost, XiLaiHostPro);
//                        request = request.newBuilder().url(urlString).build();
//                    }
//                    if (url.toString().startsWith(XiLaiHostBeta)) {
//                        //TODO 给喜来微服务生产服务器Url加后缀（PS:用这种方式替换，我也很无奈，希望有人找到更加合适的方法）
//                        String urlString = url.toString().replace(XiLaiHostBeta, XiLaiHostProBeta);
//                        request = request.newBuilder().url(urlString).build();
//                    }
//                    if (url.toString().startsWith(XiLaiHostPre)) {
//                        //TODO 给喜来微服务生产服务器Url加后缀（PS:用这种方式替换，我也很无奈，希望有人找到更加合适的方法）
//                        String urlString = url.toString().replace(XiLaiHostPre, XiLaiHostPrePro);
//                        request = request.newBuilder().url(urlString).build();
//                    }

                    return chain.proceed(request);

                })

                .addInterceptor(rewriteHeaderControlInterceptor).
                        addInterceptor(new AppHttpLoggingInterceptor().setLevel(AppHttpLoggingInterceptor.Level.BODY)).
                //设置客户端和服务器建立连接的超时时间
                        connectTimeout(10, TimeUnit.SECONDS).
                //设置客户端上传数据到服务器的超时时间
                        readTimeout(30, TimeUnit.SECONDS).
                //设置客户端从服务器下载响应数据的超时时间。
                        writeTimeout(30, TimeUnit.SECONDS).
                        cache(cache).
                        cookieJar(cookieJar).
                //retryOnConnectionFailure(true).
                        build();
        mFileClient = new OkHttpClient.Builder().
                addInterceptor(rewriteHeaderControlInterceptor).
                addInterceptor(new HttpLoggingInterceptor().
                        setLevel(HttpLoggingInterceptor.Level.BODY)).
                //设置客户端和服务器建立连接的超时时间
                        connectTimeout(20, TimeUnit.SECONDS).
                //设置客户端上传数据到服务器的超时时间
                        readTimeout(60, TimeUnit.SECONDS).
                //设置客户端从服务器下载响应数据的超时时间。
                        writeTimeout(60, TimeUnit.SECONDS).build();
    }

    public OkHttpClient getClient() {
        return mClient;
    }

    Boolean clearCookieJar() {
        try {
            PersistentCookieJar cookieJar = (PersistentCookieJar) mClient.cookieJar();
            if (cookieJar != null) {
                cookieJar.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public OkHttpClient getFileClient() {
        return mFileClient;
    }

    /**
     * 检查返回结果是否正确
     *
     * @param observable
     * @param <T>
     * @return
     */
//    protected <T extends AbstractResponseModel<BaseModel>> Observable<T> checkResp(Observable<T> observable) {
//        return observable.map(response -> {
//            if (response.isResponseOK()) {
//                return response;
//            }
//            String msg = response.getErrDesc();
//            if ("AUTH09".equals(response.getErrCode())) {
//                if (!TextUtils.isEmpty(msg)) {
//                    throw new IServerAuthException(msg);
//                }
//                throw new IServerAuthException();
//            }
//            if ("40004".equals(response.getErrCode())) {
//                if (!TextUtils.isEmpty(msg)) {
//                    throw new IAbortPayException(msg);
//                }
//                throw new IAbortPayException("errCode:40004 没有返回失败原因");
//            }
//            throw new IServerException(!TextUtils.isEmpty(msg) ? msg : "服务器返回未知错误" + response.getErrCode(), response.getErrCode());
//        });
//    }

}
