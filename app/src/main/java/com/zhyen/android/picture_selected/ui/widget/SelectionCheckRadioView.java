package com.zhyen.android.picture_selected.ui.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.res.ResourcesCompat;

import com.zhyen.android.R;

public class SelectionCheckRadioView extends AppCompatImageView {

    private int mUnSelectUdColor;
    private int mSelectedColor;
    private Drawable mDrawable;

    public SelectionCheckRadioView(Context context) {
        this(context, null);
    }

    public SelectionCheckRadioView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectionCheckRadioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSelectedColor = ResourcesCompat.getColor(getResources(), R.color.picture_item_checkCircle_backgroundColor, getContext().getTheme());
        mUnSelectUdColor = ResourcesCompat.getColor(getResources(), R.color.picture_check_original_radio_disable, getContext().getTheme());
        setChecked(false);
    }

    public void setChecked(boolean enable) {
        if (enable) {
            setImageResource(R.drawable.ic_preview_radio_on);
            mDrawable = getDrawable();
            mDrawable.setColorFilter(mSelectedColor, PorterDuff.Mode.SRC_IN);
        } else {
            setImageResource(R.drawable.ic_preview_radio_off);
            mDrawable = getDrawable();
            mDrawable.setColorFilter(mUnSelectUdColor, PorterDuff.Mode.SRC_IN);
        }
    }

    public void setColor(int color) {
        if (mDrawable == null) {
            mDrawable = getDrawable();
        }
        mDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }
}
