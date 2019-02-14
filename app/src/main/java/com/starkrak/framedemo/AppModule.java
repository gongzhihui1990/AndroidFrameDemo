package com.starkrak.framedemo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 使用@Module标注的注解类，主要用来提供依赖，里面定义一些用@Provides注解的以provide开头的方法，
 * 这些方法就是所提供的依赖
 * AppComponent的module
 * @author caroline
 */
@Module
class AppModule {
    private final App application;

    AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }

}
