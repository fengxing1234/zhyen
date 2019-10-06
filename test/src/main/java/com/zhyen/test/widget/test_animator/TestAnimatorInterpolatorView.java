package com.zhyen.test.widget.test_animator;

import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.view.animation.PathInterpolatorCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.zhyen.test.R;

/**
 * 1. AccelerateDecelerateInterpolator
 * <p>
 * 先加速再减速。这是默认的 Interpolator，也就是说如果你不设置的话，那么动画将会使用这个  Interpolator。
 * 视频里已经说过了，这个是一种最符合现实中物体运动的 Interpolator，它的动画效果看起来就像是物体从速度为 0 开始逐渐加速，然后再逐渐减速直到 0 的运动。它的速度 / 时间曲线以及动画完成度 / 时间曲线都是一条正弦 / 余弦曲线（这句话看完就忘掉就行，没用）。
 * 用途：就像上面说的，它是一种最符合物理世界的模型，所以如果你要做的是最简单的状态变化（位移、放缩、旋转等等），那么一般不用设置 Interpolator，就用这个默认的最好。
 * <p>
 * <p>
 * <p>
 * 2. LinearInterpolator
 * 匀速。
 * <p>
 * <p>
 * <p>
 * 3. AccelerateInterpolator
 * 持续加速。
 * 在整个动画过程中，一直在加速，直到动画结束的一瞬间，直接停止。
 * 别看见它加速骤停就觉得这是个神经病模型哦，它很有用的。它主要用在离场效果中，比如某个物体从界面中飞离，就可以用这种效果。它给人的感觉就会是「这货从零起步，加速飞走了」。到了最后动画骤停的时候，物体已经飞出用户视野，看不到了，所以他们是并不会察觉到这个骤停的。
 * <p>
 * <p>
 * <p>
 * 4. DecelerateInterpolator
 * 持续减速直到 0。
 * 动画开始的时候是最高速度，然后在动画过程中逐渐减速，直到动画结束的时候恰好减速到 0。
 * 它的效果和上面这个 AccelerateInterpolator 相反，适用场景也和它相反：它主要用于入场效果，比如某个物体从界面的外部飞入界面后停在某处。它给人的感觉会是「咦飞进来个东西，让我仔细看看，哦原来是 XXX」。
 * <p>
 * <p>
 * <p>
 * 5. AnticipateInterpolator
 * 先回拉一下再进行正常动画轨迹。效果看起来有点像投掷物体或跳跃等动作前的蓄力。
 * 如果是图中这样的平移动画，那么就是位置上的回拉；如果是放大动画，那么就是先缩小一下再放大；其他类型的动画同理。
 * 这个 Interpolator 就有点耍花样了。没有通用的适用场景，根据具体需求和设计师的偏好而定。
 * <p>
 * <p>
 * <p>
 * 6. OvershootInterpolator
 * 动画会超过目标值一些，然后再弹回来。效果看起来有点像你一屁股坐在沙发上后又被弹起来一点的感觉。
 * <p>
 * <p>
 * <p>
 * 7. AnticipateOvershootInterpolator
 * 上面这两个的结合版：开始前回拉，最后超过一些然后回弹。
 * <p>
 * <p>
 * <p>
 * 8. BounceInterpolator
 * 在目标值处弹跳。有点像玻璃球掉在地板上的效果。
 * <p>
 * <p>
 * <p>
 * 9. CycleInterpolator
 * 这个也是一个正弦 / 余弦曲线，不过它和 AccelerateDecelerateInterpolator 的区别是，它可以自定义曲线的周期，所以动画可以不到终点就结束，也可以到达终点后回弹，回弹的次数由曲线的周期决定，曲线的周期由 CycleInterpolator() 构造方法的参数决定。
 * <p>
 * <p>
 * <p>
 * 10. PathInterpolator
 * 自定义动画完成度 / 时间完成度曲线。
 * 用这个 Interpolator 你可以定制出任何你想要的速度模型。定制的方式是使用一个 Path 对象来绘制出你要的动画完成度 / 时间完成度曲线。例如：
 * interpolatorPath.lineTo(1, 1);
 * 你根据需求，绘制出自己需要的 Path，就能定制出你要的速度模型。
 * 不过要注意，这条 Path 描述的其实是一个 y = f(x) (0 ≤ x ≤ 1) （y 为动画完成度，x 为时间完成度）的曲线，所以同一段时间完成度上不能有两段不同的动画完成度（这个好理解吧？因为内容不能出现分身术呀），而且每一个时间完成度的点上都必须要有对应的动画完成度（因为内容不能在某段时间段内消失呀）。所以，下面这样的 Path 是非法的，会导致程序 FC：
 * 出现重复的动画完成度，即动画内容出现「分身」——程序 FC
 * <p>
 * <p>
 * 除了上面的这些，Android 5.0 （API 21）引入了三个新的 Interpolator 模型，并把它们加入了 support v4 包中。这三个新的 Interpolator 每个都和之前的某个已有的 Interpolator 规则相似，只有略微的区别。
 * <p>
 * <p>
 * <p>
 * 11. FastOutLinearInInterpolator
 * 加速运动。
 * 这个 Interpolator 的作用你不能看它的名字，一会儿 fast 一会儿 linear 的，完全看不懂。其实它和  AccelerateInterpolator 一样，都是一个持续加速的运动路线。只不过  FastOutLinearInInterpolator 的曲线公式是用的贝塞尔曲线，而 AccelerateInterpolator 用的是指数曲线。具体来说，它俩最主要的区别是 FastOutLinearInInterpolator 的初始阶段加速度比  AccelerateInterpolator 要快一些。
 * <p>
 * <p>
 * <p>
 * 12. FastOutSlowInInterpolator
 * 先加速再减速。
 * 同样也是先加速再减速的还有前面说过的 AccelerateDecelerateInterpolator，不过它们的效果是明显不一样的。FastOutSlowInInterpolator 用的是贝塞尔曲线，AccelerateDecelerateInterpolator 用的是正弦 / 余弦曲线。具体来讲， FastOutSlowInInterpolator 的前期加速度要快得多。
 * 不论是从动图还是从曲线都可以看出，这二者比起来，FastOutSlowInInterpolator 的前期加速更猛一些，后期的减速过程的也减得更迅速。用更直观一点的表达就是，AccelerateDecelerateInterpolator 像是物体的自我移动，而 FastOutSlowInInterpolator 则看起来像有一股强大的外力「推」着它加速，在接近目标值之后又「拽」着它减速。总之，FastOutSlowInterpolator 看起来有一点「着急」的感觉。
 * <p>
 * <p>
 * <p>
 * 13. LinearOutSlowInInterpolator
 * 持续减速。
 * <p>
 * 它和 DecelerateInterpolator 比起来，同为减速曲线，主要区别在于 LinearOutSlowInInterpolator 的初始速度更高。对于人眼的实际感觉，区别其实也不大，不过还是能看出来一些的。
 */
public class TestAnimatorInterpolatorView extends LinearLayout {

    private Interpolator[] interpolators = new Interpolator[13];
    private Button btn;
    private AppCompatSpinner spinner;
    private ImageView imageView;
    Path interpolatorPath;

    {
        interpolatorPath = new Path();
        interpolatorPath.lineTo(0.25f, 0.25f);
        interpolatorPath.moveTo(0.25f, 1.5f);
        interpolatorPath.lineTo(1, 1);

        interpolators[0] = new AccelerateDecelerateInterpolator();
        interpolators[1] = new LinearInterpolator();
        interpolators[2] = new AccelerateInterpolator();
        interpolators[3] = new DecelerateInterpolator();
        interpolators[4] = new AnticipateInterpolator();
        interpolators[5] = new OvershootInterpolator();
        interpolators[6] = new AnticipateOvershootInterpolator();
        interpolators[7] = new BounceInterpolator();
        interpolators[8] = new CycleInterpolator(0.5f);
        interpolators[9] = PathInterpolatorCompat.create(interpolatorPath);
        interpolators[10] = new FastOutLinearInInterpolator();
        interpolators[11] = new FastOutSlowInInterpolator();
        interpolators[12] = new LinearOutSlowInInterpolator();
    }

    public TestAnimatorInterpolatorView(Context context) {
        super(context);
    }

    public TestAnimatorInterpolatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestAnimatorInterpolatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        spinner = findViewById(R.id.test_spinner);
        btn = findViewById(R.id.test_btn_play);
        imageView = findViewById(R.id.test_iv);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.animate()
                        .setDuration(600)
                        .translationX(500)
                        .setInterpolator(interpolators[spinner.getSelectedItemPosition()])
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                imageView.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        imageView.setTranslationX(0);
                                    }
                                }, 500);
                            }
                        });
            }
        });
    }
}
