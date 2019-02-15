package com.starkrak.framedemo.postboy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.starkrak.framedemo.BaseActivity;
import com.starkrak.framedemo.LayoutID;
import com.starkrak.framedemo.R;
import com.starkrak.framedemo.postboy.utils.JsonUtils;

import org.jetbrains.annotations.Nullable;

/**
 * @author caroline
 */
@LayoutID(R.layout.activity_edit_json)
public class JsonEditActivity extends BaseActivity {

    TextView tvContent;
    Button btnScan;
    Button btnComplete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = getIntent().getStringExtra(String.class.getName());
        tvContent = findViewById(R.id.tvContent);
        btnScan = findViewById(R.id.btnScan);
        btnComplete = findViewById(R.id.btnComplete);
        setDataToTextView(data);
        btnScan.setOnClickListener(v -> {
            //TODO
            String srcData = "{\"object\":{\"username\":\"15961853707\",\"password\":\"E10ADC3949BA59ABBE56E057F20F883E\",\"childs\":[{\"name\":\"max\",\"sex\":1},{\"name\":\"lee\",\"sex\":0},{\"name\":\"black\",\"age\":12}]}}";
            setDataToTextView(srcData);
        });
        btnComplete.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(String.class.getName(), tvContent.getText().toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }

    private void setDataToTextView(String srcData) {
        tvContent.setText(JsonUtils.stringToJSON(srcData));
        tvContent.setTag(srcData);
    }
}
