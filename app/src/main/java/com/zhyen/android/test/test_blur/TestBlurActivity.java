package com.zhyen.android.test.test_blur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zhyen.android.R;

public class TestBlurActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_blur);
        findViewById(R.id.btn_blur_test_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestBlurActivity.this, TestSimpleBlurActivity.class));
            }
        });

        findViewById(R.id.btn_blur_test_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestBlurActivity.this, TestDynamicBlurActivity.class));
            }
        });

        findViewById(R.id.btn_blur_test_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestBlurActivity.this, BlurWeatherActivity.class));
            }
        });
    }
}
