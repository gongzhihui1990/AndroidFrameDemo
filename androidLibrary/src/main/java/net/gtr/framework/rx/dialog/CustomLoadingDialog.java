package net.gtr.framework.rx.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.krak.android.R;

import net.gtr.framework.rx.RxHelper;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 自定义圆角的dialog
 *
 * @author caroline
 */
public class CustomLoadingDialog extends Dialog implements LoadingDialog {


    private String textContent = "";
    private TextView messageView;
    private Disposable disposable;

    public CustomLoadingDialog(Context context) {
        super(context, R.style.dialog);
        View layout = View.inflate(context, R.layout.dialog_waiting, null);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        messageView = layout.findViewById(R.id.tvTip);
    }

    @Override
    public void show() {
        super.show();
        RxHelper.bindOnUI(RxHelper.countdown(25000, 250 * 4, TimeUnit.MILLISECONDS), new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Long integer) {
                StringBuilder str = new StringBuilder();
                for (long len = integer % 4; len <= 3; len++) {
                    if (len == 3) {
                        break;
                    }
                    str.append(".");
                }
                String loadingMsg = textContent + str;
                messageView.setText(loadingMsg);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 获取显示密度
     *
     * @param context
     * @return
     */
    private float getDensity(Context context) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        return dm.density;
    }

    @Override
    public void setDialogMessage(CharSequence mMessage) {
        if (messageView != null) {
            textContent = mMessage.toString();
            messageView.setText(mMessage);
        }
    }
}