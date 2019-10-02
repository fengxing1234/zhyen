package com.zhyen.test.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zhyen.test.R;


public class TestCustomViewDrawActivity extends TestBaseCustomViewActivity {


    {
        pageModels.add(new PageModel(R.layout.test_view_practice_color, "画颜色", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_view_practice_circle, "画圆", R.layout.test_view_practice_circle));
//        pageModels.add(new PageModel(R.layout.sample_rect, "", R.layout.practice_rect));
        pageModels.add(new PageModel(R.layout.test_view_practice_point, "画点", R.layout.test_view_practice_point));
//        pageModels.add(new PageModel(R.layout.sample_oval, "", R.layout.practice_oval));
//        pageModels.add(new PageModel(R.layout.sample_line, "", R.layout.practice_line));
//        pageModels.add(new PageModel(R.layout.sample_round_rect, "", R.layout.practice_round_rect));
        pageModels.add(new PageModel(R.layout.test_view_practice_arc, "画圆弧", R.layout.test_view_practice_arc));
//        pageModels.add(new PageModel(R.layout.sample_path, "", R.layout.practice_path));
        pageModels.add(new PageModel(R.layout.test_view_practice_histogram, "直方图", R.layout.test_view_practice_histogram));
        pageModels.add(new PageModel(R.layout.test_view_practice_histogram_2, "直方图2", R.layout.test_view_practice_histogram));
        pageModels.add(new PageModel(R.layout.test_view_practice_histogram_3, "直方图3", R.layout.test_view_practice_histogram));
        pageModels.add(new PageModel(R.layout.test_view_practice_pie_chart, "饼状图", R.layout.test_view_practice_pie_chart));
        pageModels.add(new PageModel(R.layout.test_view_practice_pie_chart_2, "饼状图2", R.layout.test_view_practice_pie_chart));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getTitleName() {
        return "Draw练习";
    }
}
