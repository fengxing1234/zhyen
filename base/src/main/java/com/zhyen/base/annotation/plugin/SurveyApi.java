package com.zhyen.base.annotation.plugin;

import android.content.Context;
import android.util.Log;

import com.zhyen.base.annotation.McpApiType;
import com.zhyen.base.annotation.McpCallbackContext;

import org.json.JSONArray;
import org.json.JSONObject;

public class SurveyApi extends CommonApi {

    private static final String TAG = SurveyApi.class.getSimpleName();

    public SurveyApi(Context context) {
        super(context);
        Log.d(TAG, "SurveyApi: ");
        currentType = McpApiType.Survey;
    }

    public void survey(long id, String name, JSONObject jsonObject, JSONArray array, McpCallbackContext context) {
        Log.d(TAG, "survey: ");
        Log.d(TAG, "id: " + id);
        Log.d(TAG, "name: " + name);
        Log.d(TAG, "jsonObject: " + jsonObject.toString());
        Log.d(TAG, "JSONArray: " + array.toString());
        Log.d(TAG, "McpCallbackContext: " + context);
    }
}
