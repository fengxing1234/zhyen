package com.zhyen.base.annotation.plugin;

import android.content.Context;
import android.util.Log;

import com.zhyen.base.annotation.JsonArrayAdapter;
import com.zhyen.base.annotation.McpApi;
import com.zhyen.base.annotation.McpApiType;
import com.zhyen.base.annotation.McpCallbackContext;
import com.zhyen.base.annotation.McpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommonApi extends McpBasePlugin {

    private static final String TAG = CommonApi.class.getSimpleName();
    protected McpApiType currentType = McpApiType.Common;
    private Context context;

    public CommonApi(Context context) {
        Log.d(TAG, "CommonApi: ");
        this.context = context;
    }

    @Override
    public boolean execute(String action, JsonArrayAdapter arg, McpCallbackContext context) throws JSONException {
        return super.execute(action, arg, context) || executeMcpClient(action, arg, context);
    }

    private boolean executeMcpClient(String action, JsonArrayAdapter arg, McpCallbackContext context) throws JSONException {
        for (Method method : McpClient.class.getDeclaredMethods()) {
            //获取到指定方法
            if (method.getName().equals(action)) {
                //获取当前方法的注解
                McpApi annotation = method.getAnnotation(McpApi.class);
                //判读注解是否相等
                if (annotation != null && annotation.value() == currentType) {
                    //获取该方法中所有的参数
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    //判断参数中个数是否相同
                    //第一个参数Context 倒数两个参数是成功失败回调
                    if (parameterTypes.length == arg.length() + 3) {
                        Object[] params = new Object[parameterTypes.length];
                        params[0] = this.context;
                        //循环所有的参数 获取到指定的参数类型
                        for (int i = 1; i < parameterTypes.length - 2; i++) {
                            Class clazz = parameterTypes[i];
                            int j = i - 1;
                            if (clazz == String.class) {
                                params[i] = arg.getString(j);
                            } else if (clazz == int.class) {
                                params[i] = arg.getInt(j);
                            } else if (clazz == double.class) {
                                params[i] = arg.getDouble(j);
                            } else if (clazz == long.class) {
                                params[i] = arg.getLong(j);
                            } else if (clazz == JSONObject.class) {
                                params[i] = arg.getJSONObject(j);
                            } else if (clazz == JSONArray.class) {
                                params[i] = arg.getJSONArray(j);
                            } else {
                                params[i] = arg.get(j);
                            }
                        }

                        params[params.length - 1] = context;
                        params[params.length - 2] = context;
                        try {
                            Class<McpClient> aClass = McpClient.class;
                            McpClient mcpClient = aClass.newInstance();
                            method.invoke(mcpClient, params);
                            return true;
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, String.format("invoke %s", method.getName()));
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public void login(String userId, String password, int flag, McpCallbackContext callbackContext) {
        Log.d(TAG, String.format("login: userId = %s password = %s flag = %d", userId, password, flag));
    }
}
