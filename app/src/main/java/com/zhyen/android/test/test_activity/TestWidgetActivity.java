package com.zhyen.android.test.test_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zhyen.android.R;


public class TestWidgetActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnXfermode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_widget);
        btnXfermode = findViewById(R.id.btn_xfermode_test);
        btnXfermode.setOnClickListener(this);
        findViewById(R.id.btn_circular_image).setOnClickListener(this);
        findViewById(R.id.btn_test_custom_draw).setOnClickListener(this);
        findViewById(R.id.btn_test_custom_paint).setOnClickListener(this);
        findViewById(R.id.btn_test_practice_paint).setOnClickListener(this);
        findViewById(R.id.btn_test_practice_draw_text).setOnClickListener(this);
        findViewById(R.id.btn_test_practice_draw_assist).setOnClickListener(this);
        findViewById(R.id.btn_test_draw_order).setOnClickListener(this);
        findViewById(R.id.btn_test_animator).setOnClickListener(this);
        findViewById(R.id.btn_test_animator_evaluator).setOnClickListener(this);
        findViewById(R.id.btn_test_hardware_accelerated).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        if (v == btnXfermode) {
//            Intent intent = new Intent(this, TestXfermodeActivity.class);
//            startActivity(intent);
//        }
//        if (v.getId() == R.id.btn_circular_image) {
//            Intent intent = new Intent(this, TestCircularImageView.class);
//            startActivity(intent);
//        }
//
//        if (v.getId() == R.id.btn_test_custom_draw) {
//            Intent intent = new Intent(this, TestCustomViewDrawActivity.class);
//            startActivity(intent);
//        }
//
//        if (v.getId() == R.id.btn_test_custom_paint) {
//            Intent intent = new Intent(this, TestCustomViewPaintActivity.class);
//            startActivity(intent);
//        }
//
//        if (v.getId() == R.id.btn_test_practice_paint) {
//            Intent intent = new Intent(this, TestViewPaintPracticeActivity.class);
//            startActivity(intent);
//        }
//
//        if (v.getId() == R.id.btn_test_practice_draw_text) {
//            Intent intent = new Intent(this, TestCustomViewTextActivity.class);
//            startActivity(intent);
//        }
//
//        if (v.getId() == R.id.btn_test_practice_draw_assist) {
//            Intent intent = new Intent(this, TestCustomViewAssistActivity.class);
//            startActivity(intent);
//        }
//
//        if (v.getId() == R.id.btn_test_draw_order) {
//            Intent intent = new Intent(this, TestViewDrawOrderActivity.class);
//            startActivity(intent);
//        }
//
//        if (v.getId() == R.id.btn_test_animator) {
//            Intent intent = new Intent(this, TestViewAnimationActivity.class);
//            startActivity(intent);
//        }
//
//        if (v.getId() == R.id.btn_test_animator_evaluator) {
//            Intent intent = new Intent(this, TestAnimatorEvaluatorActivity.class);
//            startActivity(intent);
//        }
//
//        if (v.getId() == R.id.btn_test_hardware_accelerated) {
//            Intent intent = new Intent(this, TestHardwareAcceleratedActivity.class);
//            startActivity(intent);
//        }
    }
}
