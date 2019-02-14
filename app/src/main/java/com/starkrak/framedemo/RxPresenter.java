package com.starkrak.framedemo;

import android.content.Context;
import android.content.Intent;

import net.gtr.framework.rx.ApplicationObserverResourceHolder;
import net.gtr.framework.rx.ObserverResourceManager;
import net.gtr.framework.rx.ProgressObserverImplementation;

import org.reactivestreams.Subscription;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;

/**
 * 基于Rx的Presenter封装,控制订阅的生命周期
 *
 * @author caroline
 */
public class RxPresenter<V extends BaseView> implements BasePresenter<V>, ApplicationObserverResourceHolder {
    protected App app;

    private Reference<V> mViewRef;


    private ObserverResourceManager observerResourceManager = new ObserverResourceManager();

    protected <T> ProgressObserverImplementation<T> getProgressObserver(Class<T> classOfT) {
        return new ProgressObserverImplementation<T>(this) {
            @Override
            public void onNext(T o) {
                super.onNext(o);
            }
        };
    }

    public Object initFromIntentWith(Intent intent) {
        return null;
    }

    public void initFromIntent(Intent intent) {
    }

    /**
     * onDestroy时调用此方法
     * 切断此Activity中的观察者容器中包含的观察者
     */
    @Override
    public void clearWorkOnDestroy() {
        observerResourceManager.clearWorkOnDestroy();
    }

    /**
     * 添加disposable到Activity生命周期，Activity销毁时候，disposable执行dispose
     *
     * @param disposable disposable
     */
    @Override
    public void addDisposable(Disposable disposable) {
        observerResourceManager.addDisposable(disposable);
    }

    /**
     * 类似 addSubscription(Disposable disposable)
     *
     * @param subscription subscription
     */
    @Override
    public void addSubscription(Subscription subscription) {
        observerResourceManager.addSubscription(subscription);
    }

    @Override
    public void removeDisposable(Disposable disposable) {
        observerResourceManager.removeDisposable(disposable);
    }

    @Override
    public void removeSubscription(Subscription subscription) {
        observerResourceManager.removeSubscription(subscription);
    }

    @Override
    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        clearWorkOnDestroy();
        if (mViewRef != null) {
            mViewRef.clear();
        }
    }

    public V getView() {
        return mViewRef != null ? mViewRef.get() : null;
    }

    public boolean isAttachView() {
        return getView() != null;
    }

    @Override
    public Context getContext() {
        if (getView() != null) {
            return getView().getContext();
        }
        return App.getContext();
    }
}
