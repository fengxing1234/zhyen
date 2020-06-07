package com.zhyen.base.annotation;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class McpClient {

    private static final String TAG = McpClient.class.getSimpleName();

    @McpApi(McpApiType.Common)
    public void homeInit(Context context, JSONArray array, McpCallbackContext success, McpCallbackContext error) {
        Log.d(TAG, "homeInit: " + context + ":::" + array.toString() + " success " + success + ":::" + error);
    }

    public void homeCommit(Context context, JSONObject object, McpCallbackContext success, McpCallbackContext error) {
        Log.d(TAG, "homeCommit: " + context + ":::" + object.toString() + " success " + success + ":::" + error);
    }

    @McpApi(McpApiType.Survey)
    public void test() {
        Log.d(TAG, "test: ");
    }
}
