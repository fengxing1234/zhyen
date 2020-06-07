package com.zhyen.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zhyen.base.annotation.Test;
import com.zhyen.test.video_view_demo.DemoVideoViewActivity;

public class TestMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main_activity);
        findViewById(R.id.btn_video_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestMainActivity.this, DemoVideoViewActivity.class));
            }
        });
        findViewById(R.id.btn_annotation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Test test = new Test();
                test.main(TestMainActivity.this);
            }
        });
    }
}
