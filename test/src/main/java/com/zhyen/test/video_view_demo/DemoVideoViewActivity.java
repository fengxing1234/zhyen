package com.zhyen.test.video_view_demo;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;

import com.zhyen.base.utils.VideoUtils;
import com.zhyen.test.R;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;


public class DemoVideoViewActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private static final String TAG = DemoVideoViewActivity.class.getSimpleName();
    private static final int REQUEST_CODE_WRITE_SETTINGS = 0x001;
    private VideoView videoView;
    private ImageView ivThumb;
    private String uriString;
    private float brightness;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_video_view_activity);
        uriString = "android.resource://" + getPackageName() + "/" + R.raw.fengxing;
        ivThumb = findViewById(R.id.test_iv_thumb);
        initVideoView();
        findViewById(R.id.test_btn_rotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "getRequestedOrientation() : " + getRequestedOrientation());
                if (getRequestedOrientation() == SCREEN_ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
                } else if (getRequestedOrientation() == SCREEN_ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });

        AppCompatSeekBar brightnessSeekBar = findViewById(R.id.test_seek_bar_brightness);
        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "onProgressChanged: " + progress);
                setScreenBrightness(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        try {
            /*
             * 获得当前屏幕亮度的模式
             * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
             * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
             */
            int screenMode = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
            Log.d(TAG, "screenMode: " + screenMode);

            int brightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            Log.d(TAG, "brightness: " + brightness);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                boolean canWrite = Settings.System.canWrite(this);
                if (canWrite) {
                    // 保存设置的屏幕亮度值
                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, (int) brightness);
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS);
                }
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initVideoView() {
        videoView = findViewById(R.id.test_video_view);
        videoView.setMediaController(new MediaController(this));
        //设置视频源播放res/raw中的文件,文件名小写字母,格式: 3gp,mp4等,flv的不一定支持;
        Uri rawUri = Uri.parse(uriString);
        videoView.setVideoURI(rawUri);
        videoView.setOnPreparedListener(this);
        videoView.setOnErrorListener(this);
        videoView.setOnCompletionListener(this);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        videoView.start();
        videoView.requestFocus();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion: ");
        getVideoThumbnail();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (videoView == null) {
            return;
        }
        Log.d(TAG, "onConfigurationChanged: " + getResources().getConfiguration().orientation);
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {//横屏
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getWindow().getDecorView().invalidate();
//            float heightInPx = ScreenUtils.getHeightInPx(this);
//            Log.d(TAG, "heightInPx: " + heightInPx);
//            float widthInPx = ScreenUtils.getWidthInPx(this);
//            Log.d(TAG, "widthInPx: " + widthInPx);
//            ViewGroup.LayoutParams layoutParams = videoView.getLayoutParams();
//            layoutParams.height = (int) heightInPx;
//            layoutParams.width = (int) widthInPx;
//            videoView.setLayoutParams(layoutParams);
//        } else {
//            WindowManager.LayoutParams attributes = getWindow().getAttributes();
//            attributes.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getWindow().setAttributes(attributes);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//
//            float width = ScreenUtils.getWidthInPx(this);
//            //float height = ScreenUtils.dip2px(this, 200.f);
//            float height = ScreenUtils.getHeightInPx(this);
//            videoView.getLayoutParams().height = (int) height;
//            videoView.getLayoutParams().width = (int) width;
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void getVideoThumbnail() {
        Bitmap videoThumbnailForRaw = VideoUtils.getVideoThumbnailForRaw(this, uriString);
        ivThumb.setImageBitmap(videoThumbnailForRaw);
    }

    /*设置当前屏幕亮度值 0--255，并使之生效*/
    private void setScreenBrightness(float value) {

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        brightness = value / 100;
        Log.d(TAG, "brightness: " + brightness);
        lp.screenBrightness = brightness;
        Log.d(TAG, "setScreenBrightness: " + lp.screenBrightness);
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_WRITE_SETTINGS) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.System.canWrite(this)) {
                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, (int) brightness);
                }
            }
        }
    }
}
