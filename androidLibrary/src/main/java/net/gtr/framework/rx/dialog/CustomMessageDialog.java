package net.gtr.framework.rx.dialog;

import android.content.Context;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import androidx.annotation.NonNull;


/**
 * @author caroline
 */
public class CustomMessageDialog implements MessageDialog {
    private MaterialDialog msgDialog;
    private MaterialDialog.Builder msgDialogBuilder;

    public CustomMessageDialog(@NonNull Context context) {
        msgDialogBuilder = new MaterialDialog.Builder(context);
    }

    @Override
    public CustomMessageDialog setCancelable(boolean mCancelable) {
        if (msgDialogBuilder != null) {
            msgDialogBuilder.cancelable(mCancelable);
        }
        return this;
    }

    @Override
    public CustomMessageDialog setDialogMessage(CharSequence mMessage) {
        if (msgDialogBuilder != null) {
            msgDialogBuilder.content(mMessage);
        }
        return this;
    }


    @Override
    public boolean isShowing() {
        if (msgDialog == null) {
            return false;
        }
        return msgDialog.isShowing();
    }

    @Override
    public CustomMessageDialog show() {
        msgDialog = msgDialogBuilder.build();
        msgDialog.show();

        return this;
    }

    @Override
    public void dismiss() {
        if (msgDialog != null) {
            msgDialog.dismiss();
        }
    }

    @Override
    public CustomMessageDialog setDialogTitle(CharSequence mTitle) {
        if (msgDialogBuilder != null) {
            msgDialogBuilder.title(mTitle);
        }
        return this;
    }


    @Override
    public CustomMessageDialog setCancelButtonText(CharSequence mCancel) {
        if (msgDialogBuilder != null) {
            msgDialogBuilder.negativeText(mCancel);
        }
        return this;
    }

    @Override
    public CustomMessageDialog setPositiveText(CharSequence btnText) {
        if (msgDialogBuilder != null) {
            msgDialogBuilder.positiveText(btnText);
        }
        return this;
    }

    @Override
    public CustomMessageDialog onPositive(View.OnClickListener positiveClick) {
        if (msgDialogBuilder != null) {
            msgDialogBuilder.onPositive((dialog, which) -> positiveClick.onClick(null));
        }
        return this;
    }


}