package net.gtr.framework.rx;

import androidx.fragment.app.FragmentManager;

/**
 * Created by caroline on 2018/4/24.
 */

public interface DialogObserverHolder extends ApplicationObserverResourceHolder{
    FragmentManager getSupportFragmentManager();

    void showDialog(Actions4SimpleDlg actions4SimpleDlg);
}
