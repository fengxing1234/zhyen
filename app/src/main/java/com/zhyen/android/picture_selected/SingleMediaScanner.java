package com.zhyen.android.picture_selected;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

public class SingleMediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {

    private MediaScannerConnection mMsc;
    private String mPath;
    private ScanListener mListener;

    public SingleMediaScanner(Context context, String mPath, ScanListener mListener) {
        this.mPath = mPath;
        this.mListener = mListener;
        this.mMsc = new MediaScannerConnection(context, this);
        this.mMsc.connect();
    }

    @Override
    public void onMediaScannerConnected() {
        mMsc.scanFile(mPath, null);
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        mMsc.disconnect();
        if (mListener != null) {
            mListener.onScanFinish();
        }
    }

    public interface ScanListener {

        /**
         * scan finish
         */
        void onScanFinish();
    }
}
