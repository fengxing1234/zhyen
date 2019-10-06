package com.zhyen.test.widget.test_animator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSeekBar;

import com.zhyen.test.R;

public class TestAnimatorDurationView extends RelativeLayout {

    private Button play;
    private ImageView imageView;
    private AppCompatSeekBar seekBar;
    private int mAnimatorState;
    private long duration;
    private TextView tvDuration;

    public TestAnimatorDurationView(Context context) {
        super(context);
    }

    public TestAnimatorDurationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestAnimatorDurationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        seekBar = findViewById(R.id.test_seek_bar);
        imageView = findViewById(R.id.test_iv);
        play = findViewById(R.id.test_btn_play);
        tvDuration = findViewById(R.id.test_tv_duration);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                duration = 300 * progress;
                tvDuration.setText((duration) + " ms");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setMax(10);
        seekBar.setProgress(1);
        play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mAnimatorState % 2) {
                    case 0:
                        imageView.animate().translationX(500).setDuration(duration);
                        break;
                    case 1:
                        imageView.animate().translationX(0).setDuration(duration);
                        break;
                }
                mAnimatorState++;
            }
        });
    }
}
