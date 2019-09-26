package com.zhyen.android.picture_selected;

import android.content.pm.ActivityInfo;

import androidx.annotation.StyleRes;

import com.zhyen.android.R;
import com.zhyen.android.picture_selected.engine.ImageEngine;
import com.zhyen.android.picture_selected.engine.impl.GlideEngine;
import com.zhyen.android.picture_selected.entity.CaptureStrategy;
import com.zhyen.android.picture_selected.filter.Filter;
import com.zhyen.android.picture_selected.listener.OnCheckedListener;
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
    public boolean countable;
    //可选择的最大数值 如：微信9张
    public int maxSelectable;
    //图像的最大可选计数。
    public int maxImageSelectable;
    //视频的最大可选计数。
    public int maxVideoSelectable;

    //显示拍照按钮
    public boolean capture;
    //grid尺寸 与 spanCount 设置一个即可
    public int gridExpectedSize;
    //一行显示几张图片
    public int spanCount;
    public ImageEngine imageEngine;
    public float thumbnailScale;
    public Set<MimeType> mimeTypeSet;
    public List<Filter> filters;
    public boolean mediaTypeExclusive;
    public OnSelectedListener onSelectedListener;
    public OnCheckedListener onCheckedListener;
    public CaptureStrategy captureStrategy;
    public int originalMaxSize;
    @StyleRes
    public int themeId;
    public boolean hasInited;
    public int orientation;
    public boolean originEnable;
    public boolean autoHideToolbar;
    public boolean showSingleMediaType;

    private SelectionSpec() {
    }

    public static SelectionSpec getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static SelectionSpec getCleanInstance() {
        SelectionSpec selectionSpec = getInstance();
        selectionSpec.reset();
        return selectionSpec;
    }

    private void reset() {
        mimeTypeSet = null;
        mediaTypeExclusive = true;
        showSingleMediaType = false;
        themeId = R.style.Selection_Zhihu;
        orientation = 0;
        countable = false;
        maxSelectable = 1;
        maxImageSelectable = 0;
        maxVideoSelectable = 0;
        filters = null;
        capture = false;
        captureStrategy = null;
        spanCount = 3;
        gridExpectedSize = 0;
        thumbnailScale = 0.5f;
        imageEngine = new GlideEngine();
        hasInited = true;
        originEnable = false;
        autoHideToolbar = false;
        originalMaxSize = Integer.MAX_VALUE;
    }

    public boolean singleSelectionModeEnabled() {
        return !countable && (maxSelectable == 1 || (maxImageSelectable == 1 && maxVideoSelectable == 1));
    }

    public boolean needOrientationRestriction() {
        return orientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }

    public boolean onlyShowImages() {
        return showSingleMediaType && MimeType.ofImage().containsAll(mimeTypeSet);
    }

    public boolean onlyShowVideos() {
        return showSingleMediaType && MimeType.ofVideo().containsAll(mimeTypeSet);
    }

    public boolean onlyShowGif() {
        return showSingleMediaType && MimeType.ofGif().equals(mimeTypeSet);
    }

    private static final class InstanceHolder {
        private static final SelectionSpec INSTANCE = new SelectionSpec();
    }
}
