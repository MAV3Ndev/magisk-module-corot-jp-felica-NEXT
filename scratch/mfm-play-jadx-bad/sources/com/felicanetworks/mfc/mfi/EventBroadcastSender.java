package com.felicanetworks.mfc.mfi;

import android.content.Context;
import android.content.Intent;
import com.felicanetworks.mfc.mfi.util.Base64Util;
import com.felicanetworks.mfc.util.LogMgr;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class EventBroadcastSender {
    private static final String ACTION_NOTIFY_EVENT = "com.felicanetworks.mfc.mfi.action.NOTIFY_EVENT";
    private static final String EVENT_ACCOUNT_CHANGE = "MFIC_EVENT_ACCOUNT_CHANGE";
    public static final String EVENT_BROADCAST_CLASSNAME = "com.felicanetworks.mfm.MficStatusChangeReceiver";
    private static final String EVENT_CARD_ACCESS = "MFIC_EVENT_CARD_ACCESS";
    private static final String EVENT_CARD_CHANGE = "MFIC_EVENT_CARD_CHANGE";
    private static final String EVENT_CARD_DELETE = "MFIC_EVENT_CARD_DELETE";
    private static final String EVENT_CARD_ISSUE = "MFIC_EVENT_CARD_ISSUE";
    private static final String EVENT_NEW_ACCOUNT_LOGIN = "MFIC_EVENT_NEW_ACCOUNT_LOGIN";
    private static final String KEY_CARD_ID = "cid";
    private static final String KEY_EVENT_NAME = "eventName";
    private static final String KEY_IS_START = "isStart";
    private static final String KEY_IS_SUCCESSFUL = "isSuccessful";
    private static final String KEY_PKG_NAME = "callerPackageName";
    private static final String KEY_RANDOM = "random";
    private static final String KEY_SERVICE_ID = "serviceId";
    private static final int RANDOM_BYTE_LEN = 16;

    /* JADX INFO: Access modifiers changed from: protected */
    public static class Event {
        private final String callerPackageName;
        private String cid;
        private final String name;
        private final List<NotifyInfo> notifyInfoList = new ArrayList();
        private SendStatus sendStatus = SendStatus.UNSENT;
        private final String serviceId;

        public enum SendStatus {
            UNSENT,
            SENT_START_EVENT,
            SENT_FINISH_EVENT
        }

        protected Event(String name, String serviceId, String packageName) {
            this.name = name;
            this.serviceId = serviceId;
            this.callerPackageName = packageName;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
    }

    private static class NotifyInfo {
        private final String className;
        private final String packageName;
        private final String random;

        private NotifyInfo(String packageName, String className) {
            this.random = createRandomNumber();
            this.packageName = packageName;
            this.className = className;
        }

        private String createRandomNumber() {
            byte[] bArr = new byte[16];
            new SecureRandom().nextBytes(bArr);
            return Base64Util.encode(bArr);
        }
    }

    public static class CardDeleteEvent extends Event {
        @Override // com.felicanetworks.mfc.mfi.EventBroadcastSender.Event
        public /* bridge */ /* synthetic */ void setCid(String cid) {
            super.setCid(cid);
        }

        public CardDeleteEvent(String serviceId) {
            super(EventBroadcastSender.EVENT_CARD_DELETE, serviceId, null);
        }
    }

    public static class CardIssueEvent extends Event {
        @Override // com.felicanetworks.mfc.mfi.EventBroadcastSender.Event
        public /* bridge */ /* synthetic */ void setCid(String cid) {
            super.setCid(cid);
        }

        public CardIssueEvent(String serviceId, String callerPackageName) {
            super(EventBroadcastSender.EVENT_CARD_ISSUE, serviceId, callerPackageName);
        }
    }

    public static class CardChangeEvent extends Event {
        @Override // com.felicanetworks.mfc.mfi.EventBroadcastSender.Event
        public /* bridge */ /* synthetic */ void setCid(String cid) {
            super.setCid(cid);
        }

        public CardChangeEvent(String serviceId) {
            super(EventBroadcastSender.EVENT_CARD_CHANGE, serviceId, null);
        }
    }

    public static class CardAccessEvent extends Event {
        @Override // com.felicanetworks.mfc.mfi.EventBroadcastSender.Event
        public /* bridge */ /* synthetic */ void setCid(String cid) {
            super.setCid(cid);
        }

        public CardAccessEvent(String serviceId) {
            super(EventBroadcastSender.EVENT_CARD_ACCESS, serviceId, null);
        }
    }

    public static class AccountChangeEvent extends Event {
        @Override // com.felicanetworks.mfc.mfi.EventBroadcastSender.Event
        public /* bridge */ /* synthetic */ void setCid(String cid) {
            super.setCid(cid);
        }

        public AccountChangeEvent(String appCallerInfo) {
            super(EventBroadcastSender.EVENT_ACCOUNT_CHANGE, null, appCallerInfo);
        }
    }

    public static class NewAccountLoginEvent extends Event {
        @Override // com.felicanetworks.mfc.mfi.EventBroadcastSender.Event
        public /* bridge */ /* synthetic */ void setCid(String cid) {
            super.setCid(cid);
        }

        public NewAccountLoginEvent(String appCallerInfo) {
            super(EventBroadcastSender.EVENT_NEW_ACCOUNT_LOGIN, null, appCallerInfo);
        }
    }

    public static void sendStartEventBroadcast(Context context, Event event) {
        LogMgr.log(6, "000");
        if (event == null) {
            LogMgr.log(2, "700 event is null.");
            return;
        }
        NotifyInfo notifyInfo = new NotifyInfo("com.felicanetworks.mfm.main", EVENT_BROADCAST_CLASSNAME);
        try {
            if (sendEvent(context, event, true, false, notifyInfo)) {
                event.notifyInfoList.add(notifyInfo);
            }
        } catch (Exception e) {
            LogMgr.log(2, "703 sendStartEvent Failed. event:" + event.name + "," + notifyInfo.random + "," + event.serviceId);
            LogMgr.printStackTrace(7, e);
        }
        event.sendStatus = Event.SendStatus.SENT_START_EVENT;
        LogMgr.log(6, "999");
    }

    public static void sendFinishEventBroadcast(Context context, Event event, boolean isSuccessful) {
        LogMgr.log(6, "000");
        if (event != null) {
            if (event.sendStatus != Event.SendStatus.SENT_FINISH_EVENT) {
                if (event.notifyInfoList.size() != 1) {
                    LogMgr.log(2, "702 Invalid size of notifyInfoList.");
                    return;
                }
                try {
                    sendEvent(context, event, false, isSuccessful, (NotifyInfo) event.notifyInfoList.get(0));
                } catch (Exception e) {
                    LogMgr.log(2, "703 sendFinishEvent Failed. event:" + event.name + "," + ((NotifyInfo) event.notifyInfoList.get(0)).random + "," + event.serviceId);
                    LogMgr.printStackTrace(7, e);
                }
                event.sendStatus = Event.SendStatus.SENT_FINISH_EVENT;
                LogMgr.log(6, "999");
                return;
            }
            LogMgr.log(2, "701 event is already sent.");
            return;
        }
        LogMgr.log(6, "998 start event has not been sent.");
    }

    private static boolean sendEvent(Context context, Event event, boolean isStart, boolean isSuccessful, NotifyInfo notifyInfo) {
        LogMgr.log(6, "000");
        if (context == null) {
            LogMgr.log(2, "700 context is null.");
            return false;
        }
        if (event == null) {
            LogMgr.log(2, "701 event is null.");
            return false;
        }
        if (context.getPackageManager().hasSystemFeature("android.hardware.type.watch")) {
            LogMgr.log(7, "001 device is Wear.");
            return false;
        }
        LogMgr.log(6, "002 event:" + event.name + "," + isStart + "," + isSuccessful + "," + notifyInfo.random + "," + event.serviceId);
        StringBuilder sb = new StringBuilder("003 notifyEventTargetAppInfo:");
        sb.append(notifyInfo.packageName);
        sb.append(",");
        sb.append(notifyInfo.className);
        LogMgr.log(6, sb.toString());
        Intent intent = new Intent();
        intent.setAction(ACTION_NOTIFY_EVENT);
        intent.putExtra(KEY_EVENT_NAME, event.name);
        intent.putExtra(KEY_IS_START, isStart);
        if (!isStart) {
            intent.putExtra(KEY_IS_SUCCESSFUL, isSuccessful);
        }
        intent.putExtra(KEY_RANDOM, notifyInfo.random);
        if (event.name.equals(EVENT_ACCOUNT_CHANGE) || event.name.equals(EVENT_NEW_ACCOUNT_LOGIN)) {
            intent.putExtra(KEY_PKG_NAME, event.callerPackageName);
        } else {
            intent.putExtra("serviceId", event.serviceId);
        }
        if (event.name.equals(EVENT_CARD_ISSUE)) {
            intent.putExtra(KEY_PKG_NAME, event.callerPackageName);
            if (isSuccessful) {
                intent.putExtra(KEY_CARD_ID, event.cid);
            }
        }
        intent.setClassName(notifyInfo.packageName, notifyInfo.className);
        try {
            context.sendBroadcast(intent);
            LogMgr.log(6, "999");
            return true;
        } catch (Exception e) {
            LogMgr.log(2, "702 sendBroadcast Failed");
            LogMgr.printStackTrace(7, e);
            return false;
        }
    }
}
