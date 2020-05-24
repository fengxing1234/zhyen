package com.zhyen.android.test.test_aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zhyen.android.MessageReceiver;
import com.zhyen.android.MessageSender;
import com.zhyen.android.R;
import com.zhyen.android.model.MessageModel;

public class TestAidlMessageActivity extends AppCompatActivity {

    private static final String TAG = "TestAidlMessageActivity";
    private MessageSender messageSender;
    private boolean connected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_aidl_message_activity);
        findViewById(R.id.btn_send_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!connected) return;
                //使用asInterface方法取得AIDL对应的操作接口
                MessageModel messageModel = new MessageModel();
                messageModel.setFrom("client user id");
                messageModel.setTo("receiver user id");
                messageModel.setContent("This is message content");
                //调用远程Service的sendMessage方法，并传递消息实体对象
                try {
                    messageSender.sendMessage(messageModel);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!connected) return;
                try {
                    messageSender.registerReceiveListener(messageReceiver);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        setupService();
    }

    private void setupService() {
        Intent intent = new Intent(this, MessageService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    /**
     * Binder可能会意外死忙（比如Service Crash），Client监听到Binder死忙后可以进行重连服务等操作
     */
    private Binder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d(TAG, "binderDied: ");
            if (messageSender != null) {
                messageSender.asBinder().unlinkToDeath(this, 0);
                messageSender = null;
            }
            //重连服务或其他操作
            setupService();
        }
    };

    //消息监听回调接口
    private MessageReceiver.Stub messageReceiver = new MessageReceiver.Stub() {
        @Override
        public void onMessageReceived(MessageModel receivedMessage) throws RemoteException {
            Log.d(TAG, "onMessageReceived: " + receivedMessage.toString());
        }
    };

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            connected = true;
            Log.d(TAG, "onServiceConnected: name = " + name);
            Log.d(TAG, "onServiceConnected: service = " + service);
            messageSender = MessageSender.Stub.asInterface(service);
            //设置Binder死亡监听
            try {
                messageReceiver.asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connected = false;
            Log.d(TAG, "onServiceDisconnected: name = " + name);
        }
    };

    @Override
    protected void onDestroy() {
        //解除消息监听接口
        if (messageSender != null && messageSender.asBinder().isBinderAlive()) {
            try {
                messageSender.unregisterReceiveListener(messageReceiver);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(connection);
        super.onDestroy();
    }
}
