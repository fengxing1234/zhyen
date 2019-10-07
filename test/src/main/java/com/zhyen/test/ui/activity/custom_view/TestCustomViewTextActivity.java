package com.zhyen.test.ui.activity.custom_view;

import com.zhyen.test.R;

public class TestCustomViewTextActivity extends TestBaseCustomViewActivity {
    {
        pageModels.add(new PageModel(R.layout.test_draw_text_webview, "绘制文字", R.layout.test_sample_draw_text));
        pageModels.add(new PageModel(R.layout.test_sample_draw_text, "drawText", R.layout.test_sample_draw_text));
        pageModels.add(new PageModel(R.layout.test_sample_static_layout, "staticLayout", R.layout.test_sample_draw_text));
        pageModels.add(new PageModel(R.layout.test_sample_set_text_size, "setTextSize", R.layout.test_sample_draw_text));
        pageModels.add(new PageModel(R.layout.test_sample_set_typeface, "setTypeface", R.layout.test_sample_draw_text));
        pageModels.add(new PageModel(R.layout.test_sample_set_fake_bold_text, "setFakeBoldText", R.layout.test_sample_draw_text));
        pageModels.add(new PageModel(R.layout.test_sample_set_strike_thru_text, "setStrikeThruText", R.layout.test_sample_draw_text));
        pageModels.add(new PageModel(R.layout.test_sample_set_underline_text, "setUnderlineText", R.layout.test_sample_draw_text));
        pageModels.add(new PageModel(R.layout.test_sample_set_text_skew_x, "setTextSkewX", R.layout.test_sample_draw_text));
        pageModels.add(new PageModel(R.layout.test_sample_set_text_scale_x, "setTextScaleX", R.layout.test_sample_draw_text));
        pageModels.add(new PageModel(R.layout.test_sample_set_text_align, "setTextAlign", R.layout.test_sample_draw_text));
        pageModels.add(new PageModel(R.layout.test_sample_get_font_spacing, "getFontSpacing", R.layout.test_sample_draw_text));
        pageModels.add(new PageModel(R.layout.test_sample_measure_text, "measureText", R.layout.test_sample_draw_text));
        pageModels.add(new PageModel(R.layout.test_sample_get_text_bounds, "getTextBounds", R.layout.test_sample_draw_text));
        pageModels.add(new PageModel(R.layout.test_sample_get_font_metrics, "getFontMetrics", R.layout.test_sample_draw_text));
    }

    @Override
    protected String getTitleName() {
        return "绘制文字";
    }
}
