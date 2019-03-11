package com.starkrak.framedemo.logboy;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.starkrak.framedemo.BaseActivity;
import com.starkrak.framedemo.LayoutID;
import com.starkrak.framedemo.R;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import net.gtr.framework.util.ToastUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import okhttp3.Request;


/**
 * @author caroline
 */
@LayoutID(R.layout.activity_log_boy)
public class LogBoyActivity extends BaseActivity {
    public static final int REQUEST_CODE_SCAN = 512;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private TextView tvHost;
    private TextView tvContent;
    //    private Button btnStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvHost = findViewById(R.id.tvHost);
//        btnStart = findViewById(R.id.btnStart);
        tvContent = findViewById(R.id.tvContent);
        tvHost.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                } else {
                    startActivityForResult(new Intent(getContext(), CaptureActivity.class), REQUEST_CODE_SCAN);
                }
            } else {
                startActivityForResult(new Intent(getContext(), CaptureActivity.class), REQUEST_CODE_SCAN);

            }
        });
        LogBoy.getInstance().attach(new LogBoy.Outer() {
            @Override
            public void outPut(String out) {
                runOnUiThread(() -> tvContent.setText(tvContent.getText().toString() + "\n\n" + out));
            }
        });

//        btnStart.setEnabled(false);
//        btnStart.setOnClickListener(view -> start());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String permissions[], @NotNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Start your camera handling here
                    startActivityForResult(new Intent(getContext(), CaptureActivity.class), REQUEST_CODE_SCAN);
                } else {
                    ToastUtil.show("You declined to allow the app to access your camera");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_SCAN) {
            assert data != null;
            String text = data.getStringExtra(String.class.getName());
            tvHost.setText(text);
            start();
//            if (text.startsWith("ws")) {
//                btnStart.setEnabled(true);
//            } else {
//                btnStart.setEnabled(false);
//            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogBoy.getInstance().detach();
    }

    private void start() {
        Request request = new Request.Builder().url(tvHost.getText().toString()).build();
        if (!LogBoy.getInstance().initialized()) {
            LogBoy.getInstance().init(request);
        }

//        clientForLoger.dispatcher().executorService().shutdown();
    }


}
