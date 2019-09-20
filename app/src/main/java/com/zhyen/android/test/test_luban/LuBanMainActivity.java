package com.zhyen.android.test.test_luban;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.zhyen.android.R;
import com.zhyen.android.luban.CompressionPredicate;
import com.zhyen.android.luban.Luban;
import com.zhyen.android.luban.OnCompressListener;
import com.zhyen.android.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class LuBanMainActivity extends AppCompatActivity {
    private static final String TAG = "Luban";

    private List<File> originPhotos = new ArrayList<>();
    private CompositeDisposable mDisposable;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_luban);
        mContext = this;
        String a = FileUtils.getRootDir().getAbsolutePath();
        Log.d(TAG, "onCreate: " + a);
        Button btn = findViewById(R.id.btn_luban);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Luban
                        .with(mContext)
                        .load(assetsToFiles())
                        .ignoreBy(100)
                        .setFocusAlpha(false)
                        .setTargetDir(getPath())
                        .filter(new CompressionPredicate() {
                            @Override
                            public boolean apply(String path) {
                                Log.d(TAG, "apply: ");
                                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                            }
                        })
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                Log.d(TAG, "onStart: ");
                            }

                            @Override
                            public void onSuccess(File file) {
                                Log.d(TAG, "onSuccess: " + file.getAbsolutePath());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError: ");
                            }
                        })
                        .launch();
            }
        });
        mDisposable = new CompositeDisposable();

    }

    private List<File> assetsToFiles() {
        final List<File> files = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            try {
                InputStream is = getResources().getAssets().open("img_" + i + ".jpg");
                File file = new File(getExternalFilesDir("FX"), "test_" + i + ".jpg");
                FileOutputStream fos = new FileOutputStream(file);

                byte[] buffer = new byte[4096];
                int len = is.read(buffer);
                while (len > 0) {
                    fos.write(buffer, 0, len);
                    len = is.read(buffer);
                }
                fos.close();
                is.close();

                files.add(file);
                originPhotos.add(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return files;
    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/fengxing/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
    }


}
