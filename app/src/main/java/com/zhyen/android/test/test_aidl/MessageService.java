package com.zhyen.android.test.test_aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.zhyen.android.MessageReceiver;
import com.zhyen.android.MessageSender;
import com.zhyen.android.model.MessageModel;

import java.util.concurrent.atomic.AtomicBoolean;

public class MessageService extends Service {

    private static final String TAG = "MessageService";
    private AtomicBoolean serviceStop = new AtomicBoolean();
    //RemoteCallbackList专门用来管理多进程回调接口
    private RemoteCallbackList<MessageReceiver> listenerList = new RemoteCallbackList<>();

    public MessageService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //自定义permission方式检查权限
        if (checkCallingOrSelfPermission("com.zhyen.android.permission.REMOTE_SERVICE_PERMISSION") == PackageManager.PERMISSION_DENIED) {
            Log.d(TAG, "onBind: 没有设置权限");
            return null;
        }
        return stub;
    }

    private MessageSender.Stub stub = new MessageSender.Stub() {
        @Override
        public void sendMessage(MessageModel messageModel) throws RemoteException {
            Log.d(TAG, "sendMessage: " + messageModel);
            if (messageModel == null) return;
            Log.d(TAG, "sendMessage: " + messageModel.toString());
        }

        @Override
        public void registerReceiveListener(MessageReceiver messageReceiver) throws RemoteException {
            listenerList.register(messageReceiver);
        }

        @Override
        public void unregisterReceiveListener(MessageReceiver messageReceiver) throws RemoteException {
            listenerList.unregister(messageReceiver);
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            /**
             * 包名验证方式
             */
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            if (packageName == null || !packageName.startsWith("com.zhyen.android")) {
                Log.d("onTransact", "拒绝调用：" + packageName);
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new FakeTCPTask()).start();
    }

    @Override
    public void onDestroy() {
        serviceStop.set(true);
        super.onDestroy();
    }

    //模拟长连接，通知客户端有新消息到达
    private class FakeTCPTask implements Runnable {
        @Override
        public void run() {
            while (!serviceStop.get()) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MessageModel messageModel = new MessageModel();
                messageModel.setFrom("Service");
                messageModel.setTo("Client");
                messageModel.setContent(String.valueOf(System.currentTimeMillis()));
                /**
                 * RemoteCallbackList的遍历方式
                 * beginBroadcast和finishBroadcast一定要配对使用
                 */
                final int listenerCount = listenerList.beginBroadcast();
                Log.d(TAG, "listenerCount == " + listenerCount);
                for (int i = 0; i < listenerCount; i++) {
                    MessageReceiver messageReceiver = listenerList.getBroadcastItem(i);
                    if (messageReceiver != null) {
                        try {
                            messageReceiver.onMessageReceived(messageModel);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
                listenerList.finishBroadcast();
            }
        }
    }
}
