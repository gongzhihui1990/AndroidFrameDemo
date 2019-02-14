package com.starkrak.framedemo;

import net.gtr.framework.app.BaseApp;
import net.gtr.framework.util.Loger;

/**
 * @author caroline
 */
public class App extends BaseApp {
    private static AppComponent appComponent;
    private static App instance;

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .build();
        }
        return appComponent;
    }

    @Override
    public void initApk() {
        instance = this;
        Loger.i("initApk success");
    }
}
