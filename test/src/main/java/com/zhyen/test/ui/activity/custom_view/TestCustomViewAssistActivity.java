package com.zhyen.test.ui.activity.custom_view;

import com.zhyen.test.R;

public class TestCustomViewAssistActivity extends TestBaseCustomViewActivity {
    {
        pageModels.add(new PageModel(R.layout.test_sample_clip_rect, "矩形裁剪", R.layout.test_sample_clip_rect));
        pageModels.add(new PageModel(R.layout.test_sample_clip_path, "路径裁剪", R.layout.test_sample_clip_rect));
        pageModels.add(new PageModel(R.layout.test_sample_translate, "平移", R.layout.test_sample_clip_rect));
        pageModels.add(new PageModel(R.layout.test_sample_scale, "缩放", R.layout.test_sample_clip_rect));
        pageModels.add(new PageModel(R.layout.test_sample_rotate, "旋转", R.layout.test_sample_clip_rect));
        pageModels.add(new PageModel(R.layout.test_sample_skew, "错切", R.layout.test_sample_skew));
        pageModels.add(new PageModel(R.layout.test_sample_matrix_translate, "矩阵平移", R.layout.test_sample_matrix_translate));
        pageModels.add(new PageModel(R.layout.test_sample_matrix_scale, "矩阵缩放", R.layout.test_sample_matrix_scale));
        pageModels.add(new PageModel(R.layout.test_sample_matrix_rotate, "矩阵旋转", R.layout.test_sample_matrix_rotate));
        pageModels.add(new PageModel(R.layout.test_sample_matrix_skew, "矩阵错切", R.layout.test_sample_matrix_skew));
        pageModels.add(new PageModel(R.layout.test_sample_camera_rotate, "相机选装", R.layout.test_sample_camera_rotate));
        pageModels.add(new PageModel(R.layout.test_sample_camera_rotate_fixed, "修正版本", R.layout.test_sample_camera_rotate));
        pageModels.add(new PageModel(R.layout.test_sample_camera_rotate_hitting_face, "糊脸修正", R.layout.test_sample_camera_rotate_hitting_face));
        pageModels.add(new PageModel(R.layout.test_sample_flipboard, "翻页效果", R.layout.test_sample_flipboard));
    }

    @Override
    protected String getTitleName() {
        return "辅助绘制";
    }
}
