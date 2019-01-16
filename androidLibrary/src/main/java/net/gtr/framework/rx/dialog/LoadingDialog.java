/*
 * Copyright (c) 2017. heisenberg.gong
 */

package net.gtr.framework.rx.dialog;

/**
  * Created by heisenberg on 2017/10/20.
 * heisenberg.gong@koolpos.com
 */

public interface LoadingDialog extends BaseDialog{
    void setTitle(CharSequence mTitle);
    void setCancelable(boolean mCancelable);
    void setDialogMessage(CharSequence mMessage);
    void show();
}
