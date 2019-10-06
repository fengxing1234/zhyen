package com.zhyen.test.ui.activity.custom_view;

import com.zhyen.test.R;

/**
 * Android 里动画是有一些分类的：动画可以分为两类：
 * Animation 和 Transition；
 * <p>
 * 其中 Animation 又可以再分为 View Animation 和 Property Animation 两类：
 * View Animation 是纯粹基于 framework 的绘制转变
 * Property Animation，属性动画，属性动画不仅可以使用自带的 API 来实现最常用的动画，而且通过自定义 View 的方式来做出定制化的动画。
 * <p>
 * 除了这两种 Animation，还有一类动画是 Transition。 Transition 这个词的本意是转换，在 Android 里指的是切换界面时的动画效果，这个在逻辑上要复杂一点，不过它的重点是在于切换而不是动画，
 */
public class TestViewAnimationActivity extends TestBaseCustomViewActivity {
    {
        pageModels.add(new PageModel(R.layout.test_animator_translation_view, "平移动画XYZ", R.layout.test_view_after_on_draw));

        pageModels.add(new PageModel(R.layout.test_animator_rotation_view, "旋转动画XY", R.layout.test_view_after_on_draw));

        pageModels.add(new PageModel(R.layout.test_animator_scale_view, "缩放动画XY", R.layout.test_view_after_on_draw));

        pageModels.add(new PageModel(R.layout.test_animator_alpha_view, "透明度动画", R.layout.test_view_after_on_draw));

        pageModels.add(new PageModel(R.layout.test_animator_more_animator_view, "多属性动画", R.layout.test_view_after_on_draw));

        pageModels.add(new PageModel(R.layout.test_animator_duration_view, "动画时间", R.layout.test_view_after_on_draw));

        pageModels.add(new PageModel(R.layout.test_animator_interpolator_view, "插值器", R.layout.test_view_after_on_draw));

        pageModels.add(new PageModel(R.layout.test_object_animator_view, "ObjectAnimator", R.layout.test_view_after_on_draw));

    }

    @Override
    protected String getTitleName() {
        return "动画";
    }
}
