package com.zhyen.base.annotation.plugin;

import android.text.TextUtils;
import android.util.Log;

import com.zhyen.base.annotation.CallbackContext;
import com.zhyen.base.annotation.JsonArrayAdapter;
import com.zhyen.base.annotation.McpCallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

public class McpBasePlugin implements IPlugin {
    private static final String TAG = McpBasePlugin.class.getSimpleName();
    private final LinkedHashMap<String, McpBasePlugin> pluginMap = new LinkedHashMap<String, McpBasePlugin>();

    public McpBasePlugin() {
        Log.d(TAG, "McpBasePlugin: ");
    }

    public McpBasePlugin getPlugin(String service) {
        McpBasePlugin ret = pluginMap.get(service);
        if (ret == null) {
            ret = instantiatePlugin(service);
            pluginMap.put(service, ret);
        }
        return ret;
    }

    private McpBasePlugin instantiatePlugin(String className) {
        McpBasePlugin ret = null;
        try {
            Class<?> c = null;
            if ((className != null) && !("".equals(className))) {
                c = Class.forName(className);
            }
            if (c != null & McpBasePlugin.class.isAssignableFrom(c)) {
                ret = (McpBasePlugin) c.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding plugin " + className + ".");
        }
        return ret;
    }


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, String.format("%s execute: %s", getClass().getSimpleName(), action));
        JsonArrayAdapter adapter = new JsonArrayAdapter(args);
        Log.d(TAG, String.format("CordovaArgs: %s", args.toString()));
        return execute(action, adapter, new McpCallbackContext(callbackContext));
    }

    public boolean execute(String action, JsonArrayAdapter arg, McpCallbackContext context) throws JSONException {
        if (TextUtils.isEmpty(action))
            return false;
        for (Method method : getClass().getDeclaredMethods()) {
            if (action.equals(method.getName())) {
                Class<?>[] types = method.getParameterTypes();
                //方法的参数最后一个多加一个回调
                if (types.length == arg.length() + 1) {
                    Object[] params = new Object[types.length];
                    for (int i = 0; i < types.length-1; i++) {
                        Class type = types[i];
                        if (type == String.class) {
                            params[i] = arg.getString(i);
                        } else if (type == int.class) {
                            params[i] = arg.getInt(i);
                        } else if (type == boolean.class) {
                            params[i] = arg.getBoolean(i);
                        } else if (type == double.class) {
                            params[i] = arg.getDouble(i);
                        } else if (type == long.class) {
                            params[i] = arg.getLong(i);
                        } else if (type == JSONObject.class) {
                            params[i] = arg.getJSONObject(i);
                        } else if (type == JSONArray.class) {
                            params[i] = arg.getJSONArray(i);
                        } else {
                            Log.d(TAG, String.format("%s/%d 意外类型 %s：", action, i, type.getName()));
                            params[i] = arg.get(i);
                        }
                    }
                    params[params.length - 1] = context;
                    try {
                        Log.d(TAG, String.format("invoke %s", method.getName()));
                        method.invoke(this, params);
                        return true;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }
}
