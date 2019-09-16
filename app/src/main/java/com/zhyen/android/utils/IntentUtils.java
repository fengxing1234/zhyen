package com.zhyen.android.utils;

import android.content.Intent;
import android.provider.MediaStore;

public class IntentUtils {

    public static Intent getSystemPicturesIntent(){
        Intent albumIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return albumIntent;
    }
}
