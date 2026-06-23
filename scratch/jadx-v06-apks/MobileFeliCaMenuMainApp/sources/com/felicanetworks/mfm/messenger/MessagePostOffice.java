package com.felicanetworks.mfm.messenger;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/* JADX INFO: loaded from: classes.dex */
public class MessagePostOffice extends FirebaseMessagingService {
    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onNewToken(String str) {
        Logger.info(str);
        MessageSender.sendToken(this, str);
    }

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Logger.info(remoteMessage);
        MessageSender.sendMessage(this, remoteMessage.getData());
    }

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onDeletedMessages() {
        Logger.info(new Object[0]);
        MessageSender.sendDeletedMessage(this);
    }
}
