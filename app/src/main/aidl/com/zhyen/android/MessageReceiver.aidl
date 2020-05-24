// MessageReceiver.aidl
package com.zhyen.android;
import com.zhyen.android.model.MessageModel;
// Declare any non-default types here with import statements
//消息回调接口
interface MessageReceiver {
    void onMessageReceived(in MessageModel receivedMessage);
}
