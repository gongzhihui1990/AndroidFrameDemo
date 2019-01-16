/*
 * Copyright (c) 2017. heisenberg.gong
 */

package net.gtr.framework.rx.dialog;

import android.content.Context;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by heisenberg on 2018/10/20.
 * heisenberg.gong@koolpos.com
 */

public interface MessageDialog extends BaseDialog {

    MessageDialog setDialogTitle(CharSequence mTitle);

    MessageDialog setCancelButtonText(CharSequence mCancel);

    MessageDialog setPositiveText(CharSequence btnText);

    MessageDialog onPositive(View.OnClickListener positiveClick);

    MessageDialog setCancelable(boolean mCancelable);

    MessageDialog setDialogMessage(CharSequence mMessage);

    MessageDialog show();


}
