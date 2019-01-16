package net.gtr.framework.rx.dialog;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;


/**
 * @author caroline
 */
public class NativeMessageDialog implements MessageDialog {
    private AlertDialog msgDialog;
    private AlertDialog.Builder msgDialogBuilder;
    private CharSequence negativeText = null, positiveText = null;
    private DialogInterface.OnClickListener negativeOnClickListener = null, positiveOnClickListener = null;

    public NativeMessageDialog(@NonNull Context context) {
        msgDialogBuilder = new AlertDialog.Builder(context);
    }

    @Override
    public NativeMessageDialog setCancelable(boolean mCancelable) {
        if (msgDialogBuilder != null) {
            msgDialogBuilder.setCancelable(mCancelable);
        }
        return this;
    }

    @Override
    public NativeMessageDialog setDialogMessage(CharSequence mMessage) {
        if (msgDialogBuilder != null) {
            msgDialogBuilder.setMessage(mMessage);
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
    public NativeMessageDialog show() {
        msgDialog = msgDialogBuilder.create();
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
    public NativeMessageDialog setDialogTitle(CharSequence mTitle) {
        if (msgDialogBuilder != null) {
            msgDialogBuilder.setTitle(mTitle);
        }
        return this;
    }


    @Override
    public NativeMessageDialog setCancelButtonText(CharSequence mCancel) {
        if (msgDialogBuilder != null) {
            negativeText = mCancel;
            msgDialogBuilder.setNegativeButton(negativeText, negativeOnClickListener);
        }
        return this;
    }

    @Override
    public NativeMessageDialog setPositiveText(CharSequence btnText) {
        if (msgDialogBuilder != null) {
            positiveText = btnText;
            msgDialogBuilder.setPositiveButton(positiveText, positiveOnClickListener);
        }
        return this;
    }

    @Override
    public NativeMessageDialog onPositive(DialogInterface.OnClickListener positiveClick) {
        if (msgDialogBuilder != null) {
            positiveOnClickListener = positiveClick;
            msgDialogBuilder.setPositiveButton(positiveText, positiveOnClickListener);
        }
        return this;
    }


}