package com.zhyen.android.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * author lihui
 * version 1.0
 * date: on 2017/7/12
 */

public class ToastUtil {

    private static Toast mToast = null;
    private static View mToastView = null;
    private static TextView mTextView = null;

    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showMiddleScreenToast(Context context, String toastMsg) {
//        if (mToast == null) {
//            //加载自定义Toast布局
//            mToastView = LayoutInflater.from(context).inflate(R.layout.toast, null);
//            mToast = new Toast(context);
//            mToast.setView(mToastView);
//            mToast.setDuration(Toast.LENGTH_SHORT);
//            mToast.setGravity(Gravity.CENTER, 0, 0);
//            mTextView = (TextView) mToastView.findViewById(R.id.message);
//        }
//        mTextView.setText(toastMsg);
//        mToast.show();
    }
}
