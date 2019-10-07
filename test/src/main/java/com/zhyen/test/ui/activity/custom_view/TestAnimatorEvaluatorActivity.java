package com.zhyen.test.ui.activity.custom_view;

import com.zhyen.test.R;

/**
 * 额外简单说一下 ValuesAnimator。很多时候，你用不到它，只是在你使用一些第三方库的控件，而你想要做动画的属性却没有 setter / getter 方法的时候，会需要用到它。
 *
 * 除了 ViewPropertyAnimator 和 ObjectAnimator，还有第三个选择是 ValueAnimator。ValueAnimator 并不常用，因为它的功能太基础了。ValueAnimator 是 ObjectAnimator 的父类，实际上，ValueAnimator 就是一个不能指定目标对象版本的 ObjectAnimator。ObjectAnimator 是自动调用目标对象的 setter 方法来更新目标属性的值，以及很多的时候还会以此来改变目标对象的 UI，而 ValueAnimator 只是通过渐变的方式来改变一个独立的数据，这个数据不是属于某个对象的，至于在数据更新后要做什么事，全都由你来定，你可以依然是去调用某个对象的 setter 方法（别这么为难自己），也可以做其他的事，不管要做什么，都是要你自己来写的，ValueAnimator 不会帮你做。功能最少、最不方便，但有时也是束缚最少、最灵活。比如有的时候，你要给一个第三方控件做动画，你需要更新的那个属性没有 setter 方法，只能直接修改，这样的话 ObjectAnimator 就不灵了啊。怎么办？这个时候你就可以用 ValueAnimator，在它的 onUpdate() 里面更新这个属性的值，并且手动调用 invalidate()。
 *
 * 所以你看，ViewPropertyAnimator、ObjectAnimator、ValueAnimator 这三种 Animator，它们其实是一种递进的关系：从左到右依次变得更加难用，也更加灵活。但我要说明一下，它们的性能是一样的，因为 ViewPropertyAnimator 和 ObjectAnimator 的内部实现其实都是 ValueAnimator，ObjectAnimator 更是本来就是 ValueAnimator 的子类，它们三个的性能并没有差别。它们的差别只是使用的便捷性以及功能的灵活性。所以在实际使用时候的选择，只要遵循一个原则就行：尽量用简单的。能用 View.animate() 实现就不用 ObjectAnimator，能用 ObjectAnimator 就不用 ValueAnimator。
 */
public class TestAnimatorEvaluatorActivity extends TestBaseCustomViewActivity {
    {
        pageModels.add(new PageModel(R.layout.test_animator_evluator_webview, "动画进阶", R.layout.test_sample_clip_rect));
        pageModels.add(new PageModel(R.layout.test_argb_evaluator_view, "ARGBEvaluator", R.layout.test_sample_clip_rect));
        pageModels.add(new PageModel(R.layout.test_hsv_evaluator_view, "HsvEvaluator", R.layout.test_sample_clip_rect));
        pageModels.add(new PageModel(R.layout.test_of_object_evaluator_view, "ofObject", R.layout.test_sample_clip_rect));
        pageModels.add(new PageModel(R.layout.test_property_values_holder_view, "PropertyValuesHolder", R.layout.test_sample_clip_rect));
        pageModels.add(new PageModel(R.layout.test_animator_set_view, "AnimatorSet", R.layout.test_sample_clip_rect));
        pageModels.add(new PageModel(R.layout.test_key_frame_view, "Keyframe关键帧", R.layout.test_sample_clip_rect));

    }

    @Override
    protected String getTitleName() {
        return "动画Evaluator";
    }
}
