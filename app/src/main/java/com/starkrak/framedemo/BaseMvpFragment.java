package com.starkrak.framedemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import javax.inject.Inject;

import androidx.annotation.Nullable;

/**
 * @author Administrator
 * @date 2018/2/7 0007
 */

public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseLazyFragment implements BaseView {
    @Inject
    protected T mPresenter;

    /**
     * initInject
     */
    protected abstract void initInject();


    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder().appComponent(App.getAppComponent()).fragmentModule(getFragmentModule()).build();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initInject();
        if (mPresenter != null  ) {
             mPresenter.attachView(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
    }

    @Override
    public void initErrorQuit(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        //onBackPressed();
    }
}
