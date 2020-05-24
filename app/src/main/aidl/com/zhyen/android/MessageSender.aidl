// MessageSender.aidl
package com.zhyen.android;
import com.zhyen.android.model.MessageModel;
import com.zhyen.android.MessageReceiver;
// Declare any non-default types here with import statements

interface MessageSender {
    void sendMessage(in MessageModel messageModel);

    void registerReceiveListener(MessageReceiver messageReceiver);

    void unregisterReceiveListener(MessageReceiver messageReceiver);
}
