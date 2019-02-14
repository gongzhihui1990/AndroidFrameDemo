package com.starkrak.framedemo.postboy;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.starkrak.framedemo.BaseMvpFragment;
import com.starkrak.framedemo.BuildConfig;
import com.starkrak.framedemo.LayoutID;
import com.starkrak.framedemo.R;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author caroline
 */
@LayoutID(R.layout.layout_request)
public class RequestFragment extends BaseMvpFragment<RequestFragmentPresenter> implements RequestFrementContract.View {

    private View btnSend;
    private TextView tvHost;
    private TextView tvPath;
    private TextView tvBody;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSend = view.findViewById(R.id.btnSend);
        tvPath = view.findViewById(R.id.tvPath);
        tvHost = view.findViewById(R.id.tvHost);
        tvBody = view.findViewById(R.id.tvBody);
        btnSend.setOnClickListener(v -> mPresenter.send());
        tvPath.setText("/user/login");
        tvPath.setTag("/user/login");
        tvHost.setText("/{" + "dev" + "}");
        tvHost.setTag("http://10.10.10.166:1881");
        tvBody.setText("{{\"object\":{\"username\":\"15961853707\",\"password\":\"E10ADC3949BA59ABBE56E057F20F883E\"}}");
        tvBody.setTag("{{\"object\":{\"username\":\"15961853707\",\"password\":\"E10ADC3949BA59ABBE56E057F20F883E\"}}");
    }


    @Override
    public String getUrl() {
        return tvHost.getTag().toString() + tvPath.getTag().toString();
    }

    @Override
    public String getBody() {
        return tvBody.getTag().toString();
    }

    @Override
    public Map<String, String> getHeads() {
        Map<String, String> heads = new HashMap<>(1);
        heads.put("Content-Type", "application/json");
        heads.put("App-Version", BuildConfig.VERSION_NAME);
        heads.put("Content-Type", "android");
//           .header("App-Version", BuildConfig.VERSION_NAME)
//                .header("App-Type", "android")
        return heads;
    }
}
