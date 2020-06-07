package com.zhyen.base.annotation;

import org.json.JSONArray;
import org.json.JSONObject;

public class McpCallbackContext extends CallbackContext {
    private CallbackContext callbackContext;

    public McpCallbackContext(CallbackContext callbackContext) {
        this.callbackContext = callbackContext;
    }

    public CallbackContext getOriginCallbackContext() {
        return callbackContext;
    }

    @Override
    public void success(JSONObject message) {

    }

    @Override
    public void success(String message) {

    }

    @Override
    public void success(JSONArray message) {

    }

    @Override
    public void success() {

    }

    @Override
    public void error(JSONObject message) {

    }

    @Override
    public void error(String message) {

    }

    @Override
    public void error(JSONArray message) {

    }

    @Override
    public void error() {

    }
}
