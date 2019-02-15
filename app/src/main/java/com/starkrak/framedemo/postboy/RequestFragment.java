package com.starkrak.framedemo.postboy;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.starkrak.framedemo.BaseMvpFragment;
import com.starkrak.framedemo.BuildConfig;
import com.starkrak.framedemo.LayoutID;
import com.starkrak.framedemo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

/**
 * @author caroline
 */
@LayoutID(R.layout.layout_request)
public class RequestFragment extends BaseMvpFragment<RequestFragmentPresenter> implements RequestFrementContract.View {

    private View btnSend;
    private TextView tvHost;
    private TextView tvPath;
    private TextView tvBody;
    private TextView postType;
    private View svBody;

    private PostBoyActivity.PostExecutCallBack postExecutCallBack;

    private int req10 = 10;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postType = view.findViewById(R.id.postType);
        btnSend = view.findViewById(R.id.btnSend);
        tvPath = view.findViewById(R.id.tvPath);
        tvHost = view.findViewById(R.id.tvHost);
        tvBody = view.findViewById(R.id.tvBody);
        btnSend.setOnClickListener(v -> mPresenter.send());
        tvPath.setText("/user/login");
        tvPath.setTag("/user/login");
        tvHost.setText("/{" + "dev" + "}");
        tvHost.setTag("http://10.10.10.166:1881");
        try {
            JSONObject body = new JSONObject();
            JSONObject bodyObject = new JSONObject();
            bodyObject.put("username", "15961853707");
            bodyObject.put("password", "E10ADC3949BA59ABBE56E057F20F883E");
            body.put("object", bodyObject);
            setBody(body.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tvBody.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), JsonEditActivity.class);
            intent.putExtra(String.class.getName(), tvBody.getTag().toString());
            startActivityForResult(intent, req10);
        });
        postType.setOnClickListener(v -> {
            final String items[] = {"Post", "Get"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("请求方式");
            // builder.setMessage("是否确认退出?"); //设置内容
            builder.setIcon(R.mipmap.ic_launcher);
            // 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
            builder.setItems(items, (dialog, which) -> {
                dialog.dismiss();
                postType.setText(items[which]);
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "确定", Toast.LENGTH_SHORT)
                            .show();
                }
            });
            builder.create().show();

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (req10 == requestCode && resultCode == Activity.RESULT_OK && data != null && data.hasExtra(String.class.getName())) {
            setBody(data.getStringExtra(String.class.getName()));
        }
    }

    @Override
    public String getUrl() {
        return tvHost.getTag().toString() + tvPath.getTag().toString();
    }

    @Override
    public String getBody() {
        return tvBody.getTag().toString();
    }

    private void setBody(String srcData) {
        tvBody.setTag(srcData);
        tvBody.setText(srcData);
    }

    @Override
    public Map<String, String> getHeads() {
        Map<String, String> heads = new HashMap<>(1);
        heads.put("Content-Type", "application/json");
        heads.put("App-Version", BuildConfig.VERSION_NAME);
        heads.put("Content-Type", "android");
        return heads;
    }


    public void setPostCallback(PostBoyActivity.PostExecutCallBack callback) {
        this.postExecutCallBack = callback;
    }

    @Override
    public PostBoyActivity.PostExecutCallBack getPostExecutCallBack() {
        return postExecutCallBack;
    }
}
