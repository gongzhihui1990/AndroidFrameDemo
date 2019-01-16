package net.gtr.framework.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.gtr.framework.app.BaseApp;


public class ToastUtil {
    static ToastUtil td;

    public static void show(int resId) {
        show(BaseApp.getContext().getString(resId));
    }

    public static void show(String msg) {
        if (td == null) {
            td = new ToastUtil(BaseApp.getContext());
        }
        td.setText(msg);
        td.create().show();
    }

    public static void shortShow(String msg) {
        if (td == null) {
            td = new ToastUtil(BaseApp.getContext());
        }
        td.setText(msg);
        td.createShort().show();
    }


    Context context;
    Toast toast;
    String msg;

    public ToastUtil(Context context) {
        this.context = context;
    }

    private static int def_layout;
    private static int tv_toast_content;

    public static void init(int layout,int toastContentView){
        def_layout = layout;
        tv_toast_content =toastContentView;
    }
    public Toast create() {
        View contentView = View.inflate(context, def_layout, null);
//        View contentView = View.inflate(context, R.layout.dialog_toast, null);
        TextView tvMsg = (TextView) contentView.findViewById(tv_toast_content);
//        TextView tvMsg = (TextView) contentView.findViewById(R.id.tv_toast_msg);
        toast = new Toast(context);
        toast.setView(contentView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        tvMsg.setText(msg);
        return toast;
    }

    public Toast createShort() {

        View contentView = View.inflate(context, def_layout, null);
//        View contentView = View.inflate(context, R.layout.dialog_toast, null);
        TextView tvMsg = (TextView) contentView.findViewById(tv_toast_content);
//        TextView tvMsg = (TextView) contentView.findViewById(R.id.tv_toast_msg);
        toast = new Toast(context);
        toast.setView(contentView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        tvMsg.setText(msg);
        return toast;
    }

    public void show() {
        if (toast != null) {
            toast.show();
        }
    }

    public void setText(String text) {
        msg = text;
    }
}
