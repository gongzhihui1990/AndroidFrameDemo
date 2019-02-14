package com.starkrak.framedemo;

/**
 *
 * @author caroline
 * @date 2018/2/12
 */

public interface BasePresenter< T extends BaseView> {
    /**
     * 布局依附
     * @param view
     */
    void attachView(final T view);
    /**
     * 布局解除
     */
    void detachView();
}