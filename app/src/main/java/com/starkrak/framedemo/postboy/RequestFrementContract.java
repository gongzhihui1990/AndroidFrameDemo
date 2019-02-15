package com.starkrak.framedemo.postboy;

import com.starkrak.framedemo.BasePresenter;
import com.starkrak.framedemo.BaseView;

import java.util.Map;

public interface RequestFrementContract {
    interface View extends BaseView{

        /**
         *
         * @return 请求接口
         */
        String getUrl();
        String getBody();

        Map<String, String> getHeads();
        PostBoyActivity.PostExecutCallBack getPostExecutCallBack();
    }
    interface Presenter extends BasePresenter<View>{

        /**
         * 发出请求
         */
        void send();
    }
}
