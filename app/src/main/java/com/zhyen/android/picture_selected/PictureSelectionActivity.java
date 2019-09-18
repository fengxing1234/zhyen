package com.zhyen.android.picture_selected;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhyen.android.R;

public class PictureSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Selection_Dracula);
        setContentView(R.layout.activity_picture_selection);

        Toolbar toolbar = findViewById(R.id.toolbar);

        LinearLayout llOriginLayout = findViewById(R.id.ll_origin_layout);
        llOriginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TextView tvPreview = findViewById(R.id.tv_preview);
        tvPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TextView tvEmploy = findViewById(R.id.tv_employ);
        tvEmploy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
