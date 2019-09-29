package com.zhyen.android.test.test_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zhyen.android.R;
import com.zhyen.android.test.test_widget.TestCircularImageView;

public class TestWidgetActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnXfermode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_widget);
        btnXfermode = findViewById(R.id.btn_xfermode_test);
        btnXfermode.setOnClickListener(this);
        findViewById(R.id.btn_circular_image).setOnClickListener(this);
        findViewById(R.id.btn_test_custom_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnXfermode) {
            Intent intent = new Intent(this, TestXfermodeActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btn_circular_image) {
            Intent intent = new Intent(this, TestCircularImageView.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.btn_test_custom_view) {
            Intent intent = new Intent(this, TestCustomViewActivity.class);
            startActivity(intent);
        }
    }
}
