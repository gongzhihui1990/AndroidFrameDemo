package net.gtr.framework.rx.dialog;

import net.gtr.framework.rx.ApplicationObserverResourceHolder;

import androidx.fragment.app.FragmentManager;

/**
 * Created by caroline on 2018/4/24.
 */

public interface DialogObserverHolder extends ApplicationObserverResourceHolder {
    FragmentManager getSupportFragmentManager();

    void showDialog(Actions4SimpleDlg actions4SimpleDlg);
}
