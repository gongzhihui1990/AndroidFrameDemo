package com.starkrak.framedemo;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import dagger.Module;
import dagger.Provides;

/**
 *
 * @author Fubao-D67
 * @date 2017/9/5
 */
@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
