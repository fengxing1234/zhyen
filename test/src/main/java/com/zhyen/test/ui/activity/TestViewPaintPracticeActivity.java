package com.zhyen.test.ui.activity;

import com.zhyen.test.R;

public class TestViewPaintPracticeActivity extends TestBaseCustomViewActivity {

    {
        pageModels.add(new PageModel(R.layout.test_practice_linear_gradient, "线性渐变", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_practice_radial_gradient, "辐射渐变", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_practice_sweep_gradient, "扫描渐变", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_practice_bitmap_shader, "位图渐变", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_practice_compose_shader, "混合着色器", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_practice_lighting_color_filter, "光照颜色过滤器", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_practice_color_matrix_color_filter, "矩阵颜色过滤器", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_practice_xfermode, "setXfermode", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_practice_stroke_cap, "线头形状", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_practice_stroke_join, "拐角形状", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_practice_stroke_miter, "拐角形状补充", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_practice_path_effect, "图形轮廓", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_practice_shadow_layer, "阴影效果", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_practice_mask_filter, "模糊效果", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_practice_fill_path, "实际路径", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_practice_text_path, "文字实际效果", R.layout.test_view_practice_color));

    }

    @Override
    protected String getTitleName() {
        return "Paint作业练习";
    }
}
