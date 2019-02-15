package com.starkrak.framedemo.postboy;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.starkrak.framedemo.BaseFragment;
import com.starkrak.framedemo.LayoutID;
import com.starkrak.framedemo.R;

import androidx.annotation.NonNull;

/**
 * @author caroline
 */
@LayoutID(R.layout.layout_response)
public class ResponseFragment extends BaseFragment {
    private TextView tvCode;
    private TextView tvTimes;
    private TextView tvHeads;
    private TextView tvBody;

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvCode = view.findViewById(R.id.respCode);
        tvTimes = view.findViewById(R.id.respTimes);
        tvHeads = view.findViewById(R.id.respHeads);
        tvBody = view.findViewById(R.id.respBody);
    }

    public void handleResponse(AppResponse response) {
        tvCode.setText(String.valueOf(response.getCode()));
        tvTimes.setText(String.valueOf(response.getTime()));
        tvHeads.setText(String.valueOf(response.getHeaders().toString()));
        tvBody.setText(String.valueOf(response.getBody()));
    }
}
