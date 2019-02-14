package com.starkrak.framedemo;


import javax.inject.Singleton;

import dagger.Component;

/**
 * @author caroline
 * @Component 用来将@Inject和@Module联系起来的桥梁，通过方法返回值在modules中寻找对应值自动填入被依赖的dagger中对AppConponent的依赖关系和继承关系类似
 * @SingleTon 全局单例注解
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    /**
     * getContext
     * @return 提供App的Context
     */
    App getContext();

   // DataManager getDataManager();
//
//    RetrofitHelper retrofitHelper();//提供http的帮助类
//
//    RealmHelper realmHelper();//提供数据库帮助类
//
//    ImplPreferencesHelper preferencesHelper();//提供sp帮助类
}
