package com.zhyen.base.annotation.plugin;

import com.zhyen.base.annotation.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

public interface IPlugin {

    boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException;
}
