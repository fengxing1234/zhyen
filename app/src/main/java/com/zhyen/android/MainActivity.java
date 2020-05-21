package com.zhyen.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zhyen.android.test.TestAIDLActivity;
import com.zhyen.android.test.test_activity.TestSamplePictureActivity;
import com.zhyen.android.test.test_activity.TestWidgetActivity;
import com.zhyen.android.test.test_interview.TestAnnotation;
import com.zhyen.android.test.test_interview.TestAnnotationRunTime;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestSamplePictureActivity.class));
            }
        });
        findViewById(R.id.btn_widget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestWidgetActivity.class));
            }
        });
        findViewById(R.id.btn_imitation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, ImitationMainActivity.class));
            }
        });

        Class<TestAnnotationRunTime> clazz = TestAnnotationRunTime.class;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            TestAnnotation annotation = field.getAnnotation(TestAnnotation.class);
            String name = annotation.name();
            int index = annotation.index();
            String value = annotation.value();
            Log.d(TAG, "name = " + name + " ,index = " + index + ", value = " + value);
        }

        findViewById(R.id.btn_aidl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestAIDLActivity.class));
            }
        });

        for (Method method : clazz.getMethods()) {
            String methodName = method.getName();
            Log.d(TAG, "方法名: " + methodName);
            boolean is = method.isAnnotationPresent(TestAnnotation.class);
            if (is) {
                TestAnnotation annotation = method.getAnnotation(TestAnnotation.class);
                String name = annotation.name();
                int index = annotation.index();
                String value = annotation.value();
                Log.d(TAG, "name = " + name + " ,index = " + index + ", value = " + value);
            }
        }

    }
}
