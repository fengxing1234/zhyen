package com.zhyen.android.test;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListPopupWindow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zhyen.android.R;

public class TestListPopupWindowActivity extends AppCompatActivity {

    private Button btnShow;
    private String[] mData = {"pencil", "potato", "peanut", "carrot", "cabbage", "cat"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_list_popup_window);
        btnShow = findViewById(R.id.test_btn_show);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListPopupWindow();
            }
        });
    }

    private void showListPopupWindow() {
        ListPopupWindow popupWindow = new ListPopupWindow(this);
        popupWindow.setModal(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            popupWindow.setDropDownGravity(Gravity.END);
        }
        popupWindow.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, mData));//适配，直接匿名内部类
        popupWindow.setAnchorView(btnShow);
        popupWindow.setWidth(ListPopupWindow.WRAP_CONTENT);
        popupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        float density = getResources().getDisplayMetrics().density;
        popupWindow.setContentWidth((int) (216 * density));
        popupWindow.setHorizontalOffset((int) (16 * density));
        popupWindow.setVerticalOffset((int) (-48 * density));
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        popupWindow.show();
    }
}
