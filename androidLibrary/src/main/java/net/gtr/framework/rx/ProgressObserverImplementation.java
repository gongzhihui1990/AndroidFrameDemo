/*
 * Copyright (c) 2017. heisenberg.gong
 */

package net.gtr.framework.rx;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.krak.android.R;

import net.gtr.framework.app.BaseApp;
import net.gtr.framework.exception.IServerAuthException;
import net.gtr.framework.exception.IgnoreShow;
import net.gtr.framework.rx.dialog.CustomLoadingDialog;
import net.gtr.framework.rx.dialog.CustomMessageDialog;
import net.gtr.framework.rx.dialog.LoadingDialog;
import net.gtr.framework.rx.dialog.MessageDialog;
import net.gtr.framework.util.Loger;

import androidx.annotation.StringRes;
import io.reactivex.annotations.NonNull;

/**
 * AbstractProgressObserver 的基础实现
 *
 * @author heisenberg
 * @date 2017/4/18
 */
public class ProgressObserverImplementation<T> extends AbstractProgressResourceSubscriber<T> {

    private static ObserverResourceHolder defaultHolder;

    private LoadingDialog loadingDialog;
    private MessageDialog messageDialog;

    private boolean mCancelable;
    private boolean mShow = true;
    private CharSequence pMessage;
    private Context context;

    public interface ThrowableInterceptor {
        /**
         * 是否提前处理掉此错误
         * @param e
         * @return
         */
        boolean intercept(Throwable e);

    }
    public interface ThrowableParser {

        /**
         * 异常内容转换
         * @param e
         * @return
         */
        String doParse(Throwable e);
    }
    private static ThrowableInterceptor throwableInterceptor = e -> false;
    private static ThrowableParser throwableParser = e -> null;

    /**
     * 设置统一异常处理，一般放在应用初始化的时候
     *
     * @param throwableInterceptor
     */
    public static void setUnifiedThrowableHandler(ThrowableInterceptor throwableInterceptor) {
        ProgressObserverImplementation.throwableInterceptor = throwableInterceptor;
    }
    /**
     * 设置统一异常处理，一般放在应用初始化的时候
     *
     * @param throwableInterceptor
     */
    public static void setUnifiedThrowableParser(ThrowableParser throwableInterceptor) {
        ProgressObserverImplementation.throwableParser = throwableParser;
    }

    /**
     * @param holder ApplicationObserverResourceHolder
     */
    public ProgressObserverImplementation(@NonNull ApplicationObserverResourceHolder holder) {
        if (holder != null) {
            setObserverHolder(holder);
            context = holder.getContext();
            if (Looper.myLooper() == Looper.getMainLooper()) {
                // UI主线程
                loadingDialog = new CustomLoadingDialog(holder.getContext());
                loadingDialog.setTitle("提示");
                pMessage = "加载中...";
                loadingDialog.setCancelable(mCancelable);
            }
        }
    }

    /**
     * @deprecated 无applicationObserverHolder 不建议
     */
    public ProgressObserverImplementation() {
        if (defaultHolder == null) {
            defaultHolder = new DefaultObserverResourceHolder();
        }
        setObserverHolder(defaultHolder);
    }

    @Override
    public void onError(Throwable t) {
        super.onError(t);
        showError(t);
    }

    private void showError(Throwable t) {
        if (throwableInterceptor != null && throwableInterceptor.intercept(t)) {
            return;
        }
        if (t instanceof IgnoreShow) {
            return;
        }
        t.printStackTrace();
        if (getContext() == null) {
            return;
        }
        setDialogConfirmBtn(getContext().getText(R.string.sure), null);
        if (t instanceof IServerAuthException) {
//            if (checkDialog()) {
//                messageDialog = msgDialogBuilder.
//                        title(getContext().getString(R.string.hint)).
//                        content(t.getMessage()).
//                        positiveText(getContext().getString(R.string.sure)).
//                        onPositive((dialog, which) -> {
//                            Intent intent = new Intent(getContext(), LoginActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            getContext().startActivity(intent);
//                            dialog.dismiss();
//                        }).show();
//            }
        } else {
            String parsedError = parseException(t);
            showDialogByMessage(parsedError);
            onParsedError(parsedError);
        }
    }

    public void onParsedError(String msg) {
        Loger.e(msg);
    }

    protected String parseException(Throwable e) {
        String errMsg;
        if (throwableParser != null && !TextUtils.isEmpty(throwableParser.doParse(e))) {
            errMsg = throwableParser.doParse(e);
        } else {
            errMsg = e.getMessage();
        }
        if (TextUtils.isEmpty(errMsg)) {
            errMsg = "未知错误" + e.toString();
        }
        return errMsg;
    }


    /**
     * Sets whether this dialog is cancelable with the
     * {@link KeyEvent#KEYCODE_BACK BACK} key.
     */
    public ProgressObserverImplementation<T> setCancelable(boolean flag) {
        mCancelable = flag;
        return this;
    }

    public ProgressObserverImplementation<T> setMessage(CharSequence message) {
        pMessage = message;
        return this;
    }

    public ProgressObserverImplementation<T> setMessageWithSymbol(CharSequence message) {
        pMessage = message + getContext().getString(R.string.progress_symbol);
        return this;
    }

    public ProgressObserverImplementation<T> setMessageWithSymbol(@StringRes int message) {
        pMessage = getContext().getString(message) + getContext().getString(R.string.progress_symbol);
        return this;
    }

    public ProgressObserverImplementation<T> setMessage(@StringRes int messageID) {
        pMessage = BaseApp.getContext().getString(messageID);
        return this;
    }

    public ProgressObserverImplementation<T> setShow(boolean show) {
        mShow = show;
        return this;
    }

    private boolean checkDialog() {
        if (getContext() == null) {
            return false;
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return false;
        }
        if (messageDialog != null && messageDialog.isShowing()) {
            try {
                messageDialog.dismiss();
            } catch (Exception e) {
                //底层比较渣
                Loger.e("底层比较渣" + e.getClass());
            }
            //TODO msgDialogBuilder = new MaterialDialog.Builder(getContext());
        }else {
            messageDialog = new CustomMessageDialog(getContext());
        }
        return true;

    }

    public void setDialogConfirmBtn(CharSequence btnText, final View.OnClickListener onClickListener) {

        if (checkDialog()) {
            messageDialog.setPositiveText(btnText).onPositive(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(null);
                    }
                    messageDialog.dismiss();
                }
            });
        }

    }

    public void showDialogByMessage(CharSequence message) {
        if (checkDialog()) {
            messageDialog.setDialogTitle(getContext().getString(R.string.hint)).setDialogMessage(message).show();
        }
    }

    @Override
    protected void showProgress() {
        if (loadingDialog != null && mShow) {
            loadingDialog.setCancelable(mCancelable);
            loadingDialog.setDialogMessage(pMessage);
            loadingDialog.show();
            return;
        }
        if (!mShow) {
            dismissProgress();
        }
    }

    @Override
    protected void dismissProgress() {
        if (loadingDialog != null) {
            try {
                loadingDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Context getContext() {
        return context;
    }
}
