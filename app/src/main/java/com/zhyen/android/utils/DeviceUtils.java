package com.zhyen.android.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Locale;

public class DeviceUtils {

    public static void showSystemParameter(Context context) {
        String TAG = "系统参数：";
        Log.d(TAG, "手机厂商：" + DeviceUtils.getDeviceBrand());
        Log.d(TAG, "手机型号：" + DeviceUtils.getSystemModel());
        Log.d(TAG, "手机当前系统语言：" + DeviceUtils.getSystemLanguage());
        Log.d(TAG, "Android系统版本号：" + DeviceUtils.getSystemVersion());
        Log.d(TAG, "手机IMEI：" + DeviceUtils.getIMEI(context));
        Log.d(TAG, "硬件名称 : " + DeviceUtils.getDevice());
        Log.d(TAG, "硬件制造商: " + DeviceUtils.getManufacturer());
        Log.d(TAG, "手机制造商: " + DeviceUtils.getProduct());
        Log.d(TAG, "主板名称: " + DeviceUtils.getBoard());
        Log.d(TAG, "硬件名称: " + DeviceUtils.getHardWare());
        Log.d(TAG, "显示屏参数: " + DeviceUtils.getDisplay());
        Log.d(TAG, "设备唯一识别码: " + DeviceUtils.getFingerprint());
        Log.d(TAG, "修订版本列表  硬件序列号 : " + DeviceUtils.getId());
        Log.d(TAG, "描述build的标签: " + DeviceUtils.getTags());
        Log.d(TAG, "硬件序列号: " + DeviceUtils.getSerial());
        Log.d(TAG, "版本号: " + DeviceUtils.getDeviceSDK());
        Log.d(TAG, "AndroidId: " + DeviceUtils.getAndroidId(context));
        Log.d(TAG, "Time: " + DeviceUtils.getTime());

        Log.d(TAG, "sim卡号: " + DeviceUtils.getSimSerialNumber(context));
        Log.d(TAG, "Imei: " + DeviceUtils.getImei(context));
        Log.d(TAG, "ApkVersion: " + DeviceUtils.getVersion(context));
        Log.d(TAG, "getMacAddress: " + DeviceUtils.getMacAddress(context));


    }

    public static String getSimSerialNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "没有权限";
        }
        return tm.getSimSerialNumber();
    }

    public static String getImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "没有权限";
        }
        return tm.getDeviceId();
    }

    public static int getVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            return info.versionCode;
        }
        return 0;
    }

    public static String getMacAddress(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 版本号
     *
     * @return
     */
    public static int getDeviceSDK() {
        return Build.VERSION.SDK_INT;
    }

    public static long getTime() {
        return Build.TIME;
    }

    /**
     * 描述build的标签（TAGS）
     *
     * @return
     */
    public static String getTags() {
        return Build.TAGS;
    }

    /**
     * 硬件序列号（SERIAL）
     */
    public static String getSerial() {
        return Build.SERIAL;
    }

    /**
     * 修订版本列表
     *
     * @return
     */
    public static String getId() {
        return Build.ID;
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    /**
     * 设备唯一识别码（FINGERPRINT）
     */
    public static String getFingerprint() {
        return Build.FINGERPRINT;
    }

    /**
     * 显示屏参数（DISPLAY）
     */
    public static String getDisplay() {
        return Build.DISPLAY;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     * 型号（MODEL）:即用户可见的名称
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 手机制造商
     * 产品名称（PRODUCT）：即手机厂商
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取手机厂商
     * 系统定制商
     * 品牌名称
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 主板名称
     *
     * @return
     */
    public static String getBoard() {
        return Build.BOARD;
    }

    /**
     * 硬件名称
     *
     * @return
     */
    public static String getHardWare() {
        return Build.HARDWARE;
    }

    /**
     * 设备名
     *
     * @return
     */
    public static String getDevice() {
        return Build.DEVICE;
    }

    /**
     * 硬件制造商
     *
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }


    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "没有权限";
            }
            return tm.getDeviceId();
        }
        return null;
    }

    // CPU 指令集，可以查看是否支持64位
    public static String getCpuAbi() {
        return Build.CPU_ABI;
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }
}
