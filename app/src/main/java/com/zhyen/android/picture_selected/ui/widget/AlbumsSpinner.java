package com.zhyen.android.picture_selected.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.zhyen.android.R;
import com.zhyen.android.picture_selected.entity.Album;
import com.zhyen.android.utils.ScreenUtils;

/**
 * 1、查看源码，会发现PopupMenu和Spinner内部都是使用ListPopupWindow实现下拉列表效果，所以ListPopupWindow是基础。
 * 2、PopMenu的列表页面无法定制UI，只能显示光秃秃的文字；而ListPopupWindow和Spinner可以通过适配器来设置每项的布局风格，当然ListPopupWindow是最灵活的，不但可在左侧显示列表，还能在右侧显示列表。
 * 3、PopMenu可通过子菜单实现多级菜单效果，而ListPopupWindow和Spinner只有一级列表。
 * 4、ListPopupWindow和Spinner可以设置默认选中项，而PopMenu没有默认选中项。
 * 5、Spinner既可以下拉列表来展示，也可以对话框来展示；而PopupMenu和ListPopupWindow只能以下拉列表展示。
 */
public class AlbumsSpinner {

    private ListPopupWindow popupWindow;
    private CursorAdapter mAdapter;
    private TextView mSelected;
    private AdapterView.OnItemClickListener onItemClickListener;
    private static final int MAX_SHOWN_COUNT = 6;

    public AlbumsSpinner(Context context) {
        popupWindow = new ListPopupWindow(context, null, R.attr.listPopupWindowStyle);
        double density = ScreenUtils.getDensity(context);
        popupWindow.setContentWidth((int) (216 * density));
        popupWindow.setHorizontalOffset((int) (16 * density));
        popupWindow.setVerticalOffset((int) (-48 * density));
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setSelection(parent.getContext(), position);
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent, view, position, id);
                }
            }
        });
    }

    public void setOnClickListener(AdapterView.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setAdapter(CursorAdapter adapter) {
        popupWindow.setAdapter(adapter);
        mAdapter = adapter;
    }

    public void setPopupAnchorView(View view) {
        popupWindow.setAnchorView(view);
    }


    public void setSelectedTextView(TextView textView) {
        mSelected = textView;
        //设置下图片颜色
        Drawable[] compoundDrawables = mSelected.getCompoundDrawables();
        Drawable rightDrawable = compoundDrawables[2];
        TypedArray ta = mSelected.getContext().getTheme().obtainStyledAttributes(
                new int[]{R.attr.album_element_color});
        int color = ta.getColor(0, 0);
        ta.recycle();
        rightDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);

        mSelected.setVisibility(View.GONE);
        mSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //只显示6项 多了就下拉显示
                int height = v.getResources().getDimensionPixelSize(R.dimen.album_item_height);
                popupWindow.setHeight(mAdapter.getCount() > MAX_SHOWN_COUNT ? height * MAX_SHOWN_COUNT : mAdapter.getCount() * height);
                popupWindow.show();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mSelected.setOnTouchListener(popupWindow.createDragToOpenListener(mSelected));
        }
    }

    public void setSelection(Context context, int position) {
        popupWindow.setSelection(position);
        onItemSelected(context, position);
    }

    private void onItemSelected(Context context, int position) {
        popupWindow.dismiss();
        Cursor cursor = mAdapter.getCursor();
        cursor.moveToPosition(position);
        Album album = Album.valueOf(cursor);
        String displayName = album.getDisplayName(context);
        if (mSelected.getVisibility() == View.VISIBLE) {
            mSelected.setText(displayName);
        } else {
            //渐变动画显示出来
            mSelected.setAlpha(0.0f);
            mSelected.setVisibility(View.VISIBLE);
            mSelected.setText(displayName);
            mSelected.animate().alpha(1.0f).setDuration(context.getResources().getInteger(android.R.integer.config_longAnimTime)).start();
        }

    }
}
