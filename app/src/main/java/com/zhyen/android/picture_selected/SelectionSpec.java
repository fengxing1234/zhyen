package com.zhyen.android.picture_selected;

import com.zhyen.android.picture_selected.engine.ImageEngine;
import com.zhyen.android.picture_selected.engine.impl.GlideEngine;
import com.zhyen.android.picture_selected.filter.Filter;
import com.zhyen.android.picture_selected.listener.OnSelectedListener;

import java.util.List;
import java.util.Set;

/**
 * unspecified	默认值。系统自动选择屏幕方向
 * behind	跟activity堆栈中的下面一个activity的方向一致
 * landscape	横屏方向，显示的宽比高长
 * portrait	竖屏方向，显示的高比宽长
 * sensor	由设备的物理方向传感器决定，如果用户旋转设备，这屏幕就会横竖屏切换
 * nosensor	忽略物理方向传感器，这样就不会随着用户旋转设备而横竖屏切换了（"unspecified"设置除外）
 * user	用户当前首选的方向
 * reverseLandscape	API 9 以上，反向横屏
 * reversePortrait	API 9 以上，反向竖屏
 * sensorLandscape	API 9 以上，横屏，但是可以根据 物理方向传感器来切换正反向横屏
 * sensorPortrait	API 9 以上，竖屏，但是可以根据 物理方向传感器来切换正反向竖屏
 * fullSensor	API 9 以上，上下左右四个方向，由物理方向传感器决定
 * locked	API 18 以上，锁死当前屏幕的方向
 */
public class SelectionSpec {

    //选中的图片是否显示数字
    public boolean countable = true;
    //可选择的最大数值 如：微信9张
    public int maxSelectable = 9;
    //图像的最大可选计数。
    public int maxImageSelectable = 5;
    //视频的最大可选计数。
    public int maxVideoSelectable = 5;

    public boolean originEnable = true;
    //显示拍照按钮
    public boolean capture = true;
    //grid尺寸 与 spanCount 设置一个即可
    public int gridExpectedSize;
    //一行显示几张图片
    public int spanCount = 3;
    public ImageEngine imageEngine = new GlideEngine();
    public float thumbnailScale = 0.5f;
    public Set<MimeType> mimeTypeSet = MimeType.ofAll();
    public List<Filter> filters;
    public boolean mediaTypeExclusive;
    public OnSelectedListener onSelectedListener;

    private SelectionSpec() {

    }

    public boolean onlyShowGif() {
        return false;
    }

    public boolean onlyShowImages() {
        return false;
    }

    public boolean onlyShowVideos() {
        return false;
    }

    //自定义显示类型
    public boolean customShowList() {
        return true;
    }


    private static final class InstanceHolder {
        private static final SelectionSpec INSTANCE = new SelectionSpec();
    }

    public static SelectionSpec getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public boolean singleSelectionModeEnabled() {
        return !countable && (maxSelectable == 1 || (maxImageSelectable == 1 && maxVideoSelectable == 1));
    }
}
