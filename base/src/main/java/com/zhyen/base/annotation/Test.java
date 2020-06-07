package com.zhyen.base.annotation;

import android.content.Context;

import com.zhyen.base.annotation.plugin.CommonApi;
import com.zhyen.base.annotation.plugin.McpBasePlugin;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Iterator;

public class Test {
    public void main(Context context) {
        try {
            HashMap<String, JSONArray> map = new HashMap<>();
            JSONArray array = new JSONArray();
            array.put("123456");
            array.put("fengxing");
            array.put(4);
            map.put("login", array);
            array = new JSONArray();
            JSONArray homeInitArray = new JSONArray();
            homeInitArray.put("init");
            homeInitArray.put(1123);
            homeInitArray.put(true);
            array.put(homeInitArray);
            map.put("homeInit", array);

            McpBasePlugin plugin = new CommonApi(context);
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                JSONArray jsonArray = map.get(next);

                plugin.execute(next, jsonArray, new CallbackContext());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
