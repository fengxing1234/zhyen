package com.zhyen.test.ui.activity.custom_view;

import com.zhyen.test.R;

/**
 * Android 里面的绘制都是按顺序的，先绘制的内容会被后绘制的盖住。比如你在重叠的位置先画圆再画方，和先画方再画圆所呈现出来的结果肯定是不同的：
 * <p>
 * 绘制过程简述:
 * 绘制过程中最典型的两个部分是上面讲到的主体和子 View，但它们并不是绘制过程的全部。除此之外，绘制过程还包含一些其他内容的绘制。具体来讲，一个完整的绘制过程会依次绘制以下几个内容：
 * <p>
 * 1. 背景
 * 2. 主体（onDraw()）
 * 3. 子 View（dispatchDraw()）
 * 4. 滑动边缘渐变和滑动条
 * 5. 前景
 * <p>
 * 一般来说，一个 View（或 ViewGroup）的绘制不会这几项全都包含，但必然逃不出这几项，并且一定会严格遵守这个顺序。
 * 例如通常一个 LinearLayout 只有背景和子 View，那么它会先绘制背景再绘制子 View；一个 ImageView 有主体，有可能会再加上一层半透明的前景作为遮罩，那么它的前景也会在主体之后进行绘制。
 * 需要注意，前景的支持是在 Android 6.0（也就是 API 23）才加入的；之前其实也有，不过只支持 FrameLayout，而直到 6.0 才把这个支持放进了 View 类里。
 * <p>
 * 这其中的第 2、3 两步，前面已经讲过了；onDraw练习和dispatchDraw练习
 * 第 1 步——背景，它的绘制发生在一个叫 drawBackground() 的方法里，但这个方法是 private 的，不能重写，
 * 你如果要设置背景，只能用自带的 API 去设置（xml 布局文件的 android:background 属性以及 Java 代码的 View.setBackgroundXxx() 方法，这个每个人都用得很 6 了），
 * 而不能自定义绘制；而第 4、5 两步——滑动边缘渐变和滑动条以及前景，这两部分被合在一起放在了 onDrawForeground() 方法里，这个方法是可以重写的。
 * <p>
 * 滑动边缘渐变和滑动条可以通过 xml 的 android:scrollbarXXX 系列属性或 Java 代码的  View.setXXXScrollbarXXX() 系列方法来设置；
 * 前景可以通过 xml 的 android:foreground 属性或 Java 代码的 View.setForeground() 方法来设置。
 * 而重写 onDrawForeground() 方法，并在它的  super.onDrawForeground() 方法的上面或下面插入绘制代码，则可以控制绘制内容和滑动边缘渐变、滑动条以及前景的遮盖关系。
 * <p>
 * 在 onDrawForeground() 中，会依次绘制滑动边缘渐变、滑动条和前景。所以如果你重写  onDrawForeground() ：
 * <p>
 * 除了 onDraw() dispatchDraw() 和 onDrawForeground() 之外，还有一个可以用来实现自定义绘制的方法： draw()。
 * <p>
 * <p>
 * <p>
 * <p>
 * draw() 是绘制过程的总调度方法。一个 View 的整个绘制过程都发生在 draw() 方法里。前面讲到的背景、主体、子 View 、滑动相关以及前景的绘制，它们其实都是在 draw() 方法里的。
 * <p>
 * // View.java 的 draw() 方法的简化版大致结构（是大致结构，不是源码哦）：
 * <p>
 * public void draw(Canvas canvas) {
 * ...
 * <p>
 * drawBackground(Canvas); // 绘制背景（不能重写）
 * onDraw(Canvas); // 绘制主体
 * dispatchDraw(Canvas); // 绘制子 View
 * onDrawForeground(Canvas); // 绘制滑动相关和前景
 * <p>
 * ...
 * }
 * <p>
 * 从上面的代码可以看出，onDraw() dispatchDraw() onDrawForeground() 这三个方法在 draw() 中被依次调用，因此它们的遮盖关系也就像前面所说的——dispatchDraw() 绘制的内容盖住 onDraw() 绘制的内容；
 * onDrawForeground() 绘制的内容盖住 dispatchDraw() 绘制的内容。而在它们的外部，则是由  draw() 这个方法作为总的调度。所以，你也可以重写 draw() 方法来做自定义的绘制。
 * <p>
 * <p>
 * 背景    drawBackground
 * 主体    onDraw
 * 子view  dispatchDraw
 * <p>
 * 前景    onDrawForeground
 * 滑动边缘渐变和滚动条
 * <p>
 * <p>
 * 关于绘制方法，有两点需要注意一下：
 * <p>
 * 1. 出于效率的考虑，ViewGroup 默认会绕过 draw() 方法，换而直接执行 dispatchDraw()，以此来简化绘制流程。所以如果你自定义了某个 ViewGroup 的子类（比如 LinearLayout）并且需要在它的除  dispatchDraw() 以外的任何一个绘制方法内绘制内容，你可能会需要调用  View.setWillNotDraw(false) 这行代码来切换到完整的绘制流程（是「可能」而不是「必须」的原因是，有些 ViewGroup 是已经调用过 setWillNotDraw(false) 了的，例如 ScrollView）。
 * 2. 有的时候，一段绘制代码写在不同的绘制方法中效果是一样的，这时你可以选一个自己喜欢或者习惯的绘制方法来重写。但有一个例外：如果绘制代码既可以写在 onDraw() 里，也可以写在其他绘制方法里，那么优先写在 onDraw() ，因为 Android 有相关的优化，可以在不需要重绘的时候自动跳过  onDraw() 的重复执行，以提升开发效率。享受这种优化的只有 onDraw() 一个方法。
 * <p>
 * 两个注意事项：
 * <p>
 * 1. 在 ViewGroup 的子类中重写除 dispatchDraw() 以外的绘制方法时，可能需要调用  setWillNotDraw(false)；
 * 2. 在重写的方法有多个选择时，优先选择 onDraw()。
 * <p>
 * 总结：
 * super.onDraw(canvas) 之前 背景和主体内容 之间
 * super.onDraw(canvas) 之后 主体内容和子view 之间
 * <p>
 * super.dispatchDraw(canvas) 之前 主体内容和子view 之间
 * super.dispatchDraw(canvas) 之后 子view和前景之前
 * <p>
 * super.onDrawForeground(canvas); 之前  子view和前景之前
 * super.onDrawForeground(canvas); 之后  覆盖前景
 * <p>
 * super.draw(canvas); 之前  被背景覆盖
 * super.draw(canvas); 之后  盖住前景
 */
public class TestViewDrawOrderActivity extends TestBaseCustomViewActivity {
    {
        pageModels.add(new PageModel(R.layout.test_view_after_on_draw, "onDraw之后", R.layout.test_view_after_on_draw));
        pageModels.add(new PageModel(R.layout.test_view_before_on_draw, "onDraw之前", R.layout.test_view_after_on_draw));
        pageModels.add(new PageModel(R.layout.test_view_on_draw_layout, "onDraw在Layout中", R.layout.test_view_after_on_draw));
        pageModels.add(new PageModel(R.layout.test_view_dispath_draw, "dispatchDraw", R.layout.test_view_after_on_draw));
        pageModels.add(new PageModel(R.layout.test_view_after_on_draw_foreground, "AfterOnDrawForeground", R.layout.test_view_after_on_draw));
        pageModels.add(new PageModel(R.layout.test_view_before_on_draw_foreground, "BeforeOnDrawForeground", R.layout.test_view_after_on_draw));
        pageModels.add(new PageModel(R.layout.test_view_after_draw, "AfterDraw", R.layout.test_view_after_on_draw));
        pageModels.add(new PageModel(R.layout.test_view_before_draw, "BeforeDraw", R.layout.test_view_after_on_draw));
    }

    @Override
    protected String getTitleName() {
        return "绘制顺序";
    }
}
