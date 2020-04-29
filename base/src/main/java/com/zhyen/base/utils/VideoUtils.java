package com.zhyen.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

public class VideoUtils {

    /**
     * 从raw目录中获取视频缩略图
     *
     * @param uriString uriString = "android.resource://" + getPackageName() + "/" + R.raw.fengxing;
     * @return
     */
    public static Bitmap getVideoThumbnailForRaw(Context context, String uriString) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(context, Uri.parse(uriString));
        //获取指定位置的原尺寸图片 注意这里传的timeUs是微秒
        Bitmap thumb = retriever.getFrameAtTime(8000000, MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);
        return thumb;
    }
}
