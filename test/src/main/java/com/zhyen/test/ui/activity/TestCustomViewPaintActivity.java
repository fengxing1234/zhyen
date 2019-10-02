package com.zhyen.test.ui.activity;


import com.zhyen.test.R;

public class TestCustomViewPaintActivity extends TestBaseCustomViewActivity {

    {
        pageModels.add(new PageModel(R.layout.test_view_paint_demo, "Paint练习", R.layout.test_view_practice_color));
        /**
         * 除了直接设置颜色， Paint 还可以使用 Shader 。
         *
         * Shader 这个英文单词很多人没有见过，它的中文叫做「着色器」，也是用于设置绘制颜色的。「着色器」不是 Android 独有的，它是图形领域里一个通用的概念，它和直接设置颜色的区别是，着色器设置的是一个颜色方案，或者说是一套着色规则。当设置了 Shader 之后，Paint 在绘制图形和文字时就不使用  setColor/ARGB() 设置的颜色了，而是使用 Shader 的方案中的颜色。
         *
         * 在 Android 的绘制里使用 Shader ，并不直接用 Shader 这个类，而是用它的几个子类。具体来讲有  LinearGradient RadialGradient SweepGradient BitmapShader ComposeShader 这么几个：
         */
        pageModels.add(new PageModel(R.layout.test_view_paint_linear_gradient, "线性渐变", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_view_paint_radial_gradient, "辐射渐变", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_view_paint_sweep_gradient, "扫描渐变", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_view_paint_bitmap_shader, "位图着色", R.layout.test_view_practice_color));
        //颜色过滤器
        /**
         * 除了使用 setColor/ARGB() 和 setShader() 来设置基本颜色， Paint 还可以来设置 ColorFilter，来对颜色进行第二层处理。
         *
         * ColorFilter 这个类，它的名字已经足够解释它的作用：为绘制设置颜色过滤。颜色过滤的意思，就是为绘制的内容设置一个统一的过滤策略，然后 Canvas.drawXXX() 方法会对每个像素都进行过滤后再绘制出来.
         * 在 Paint 里设置 ColorFilter ，使用的是 Paint.setColorFilter(ColorFilter filter) 方法。  ColorFilter 并不直接使用，而是使用它的子类。
         * 它共有三个子类：LightingColorFilter PorterDuffColorFilter 和 ColorMatrixColorFilter。
         */
        pageModels.add(new PageModel(R.layout.test_view_lighting_color_filter, "颜色过滤器Lighting", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_view_porter_duff_color_filter, "颜色过滤器PorterDuff", R.layout.test_view_practice_color));
        //轮廓效果
        /**
         * PathEffect 在有些情况下不支持硬件加速，需要关闭硬件加速才能正常使用：
         *
         * Canvas.drawLine() 和 Canvas.drawLines() 方法画直线时，setPathEffect() 是不支持硬件加速的；
         * PathDashPathEffect 对硬件加速的支持也有问题，所以当使用 PathDashPathEffect 的时候，最好也把硬件加速关了。
         */
        pageModels.add(new PageModel(R.layout.test_view_corner_path_effect, "圆角的路径效果", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_view_discrete_path_effect, "离散的路径效果", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_view_dash_path_effect, "虚线的路径效果", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_view_path_dash_path_effect, "以路径虚线的路径效果", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_view_sumh_path_effect, "两种路径效果同时绘制", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_view_compose_path_effect, "两种路径效果限购绘制", R.layout.test_view_practice_color));

        //绘制内容下面加一层阴影
        pageModels.add(new PageModel(R.layout.test_view_shadow_layout, "阴影", R.layout.test_view_practice_color));

        /**
         * 为之后的绘制设置 MaskFilter。上一个方法 setShadowLayer() 是设置的在绘制层下方的附加效果；而这个 MaskFilter 和它相反，设置的是在绘制层上方的附加效果。
         *
         * 到现在已经有两个 setXxxFilter(filter) 了。前面有一个 setColorFilter(filter) ，是对每个像素的颜色进行过滤；而这里的 setMaskFilter(filter) 则是基于整个画面来进行过滤。
         *
         * MaskFilter 有两种： BlurMaskFilter 和 EmbossMaskFilter。
         */
        pageModels.add(new PageModel(R.layout.test_view_mask_filter, "模糊效果", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_view_emboss_mask_filter, "浮雕效果", R.layout.test_view_practice_color));

        /**
         * 这组方法做的事是，根据 paint 的设置，计算出绘制 Path 或文字时的实际 Path。
         *
         * 所谓实际 Path ，指的就是 drawPath() 的绘制内容的轮廓，要算上线条宽度和设置的 PathEffect。
         *
         */
        pageModels.add(new PageModel(R.layout.test_view_get_fill_path, "获取Path路径", R.layout.test_view_practice_color));
        pageModels.add(new PageModel(R.layout.test_view_text_fill_path, "获取文字路径", R.layout.test_view_practice_color));

    }

    @Override
    protected String getTitleName() {
        return "Paint练习";
    }
}
