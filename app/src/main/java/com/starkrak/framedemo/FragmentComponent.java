package com.starkrak.framedemo;

import com.starkrak.framedemo.postboy.RequestFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    App getContext();

    /**
     * 注入 ResponseFragment
     *
     * @param fragment ResponseFragment
     */
//    void inject(ResponseFragment fragment);

    void inject(RequestFragment fragment);
}
