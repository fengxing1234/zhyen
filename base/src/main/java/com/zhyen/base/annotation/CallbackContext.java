package com.zhyen.base.annotation;

import org.json.JSONArray;
import org.json.JSONObject;

public class CallbackContext {
    void success(JSONObject message){}

    void success(String message){}

    void success(JSONArray message){}

    void success(){}

    void error(JSONObject message){}

    void error(String message){}

    void error(JSONArray message){}

    void error(){}
}
