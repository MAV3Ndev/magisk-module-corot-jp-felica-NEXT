package com.felicanetworks.mfc.mfi;

import android.content.Context;
import android.content.Intent;
import com.felicanetworks.mfc.mfi.fws.json.GetMfiControlInfoResponseJson;
import com.felicanetworks.mfc.mfi.util.Base64Util;
import com.felicanetworks.mfc.mfi.util.SignatureUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class EventBroadcastSender {
    private static final String ACTION_NOTIFY_EVENT = "com.felicanetworks.mfc.mfi.action.NOTIFY_EVENT";
    private static final String EVENT_ACCOUNT_CHANGE = "MFIC_EVENT_ACCOUNT_CHANGE";
    private static final String EVENT_CARD_ACCESS = "MFIC_EVENT_CARD_ACCESS";
    private static final String EVENT_CARD_CHANGE = "MFIC_EVENT_CARD_CHANGE";
    private static final String EVENT_CARD_DELETE = "MFIC_EVENT_CARD_DELETE";
    private static final String EVENT_CARD_ISSUE = "MFIC_EVENT_CARD_ISSUE";
    private static final String KEY_EVENT_NAME = "eventName";
    private static final String KEY_IS_START = "isStart";
    private static final String KEY_IS_SUCCESSFUL = "isSuccessful";
    private static final String KEY_RANDOM = "random";
    private static final String KEY_SERVICE_ID = "serviceId";
    private static final int RANDOM_BYTE_LEN = 16;

    /* JADX INFO: Access modifiers changed from: protected */
    public static class Event {
        private final String name;
        private final List<NotifyInfo> notifyInfoList = new ArrayList();
        private SendStatus sendStatus = SendStatus.UNSENT;
        private final String serviceId;

        public enum SendStatus {
            UNSENT,
            SENT_START_EVENT,
            SENT_FINISH_EVENT
        }

        protected Event(String str, String str2) {
            this.name = str;
            this.serviceId = str2;
        }
    }

    private static class NotifyInfo {
        private final NotifyEventTargetAppInfo notifyEventTargetAppInfo;
        private final String random;

        private NotifyInfo(NotifyEventTargetAppInfo notifyEventTargetAppInfo) {
            this.random = createRandomNumber();
            this.notifyEventTargetAppInfo = notifyEventTargetAppInfo;
        }

        private String createRandomNumber() {
            byte[] bArr = new byte[16];
            new SecureRandom().nextBytes(bArr);
            return Base64Util.encode(bArr);
        }
    }

    public static class CardDeleteEvent extends Event {
        public CardDeleteEvent(String str) {
            super(EventBroadcastSender.EVENT_CARD_DELETE, str);
        }
    }

    public static class CardIssueEvent extends Event {
        public CardIssueEvent(String str) {
            super(EventBroadcastSender.EVENT_CARD_ISSUE, str);
        }
    }

    public static class CardChangeEvent extends Event {
        public CardChangeEvent(String str) {
            super(EventBroadcastSender.EVENT_CARD_CHANGE, str);
        }
    }

    public static class CardAccessEvent extends Event {
        public CardAccessEvent(String str) {
            super(EventBroadcastSender.EVENT_CARD_ACCESS, str);
        }
    }

    public static class AccountChangeEvent extends Event {
        public AccountChangeEvent() {
            super(EventBroadcastSender.EVENT_ACCOUNT_CHANGE, null);
        }
    }

    public static void sendStartEventBroadcast(Context context, Event event) {
        boolean zContains;
        LogMgr.log(6, "000");
        if (event == null) {
            LogMgr.log(2, "700 event is null.");
            return;
        }
        try {
            String infoCache = MfiControlInfoCache.getInstance().getInfoCache();
            if (infoCache == null) {
                LogMgr.log(2, "701 MfiControlInfoCache is null.");
                return;
            }
            List<NotifyEventTargetAppInfo> notifyEventTargetAppInfoList = new GetMfiControlInfoResponseJson(infoCache).getNotifyEventTargetAppInfoList();
            for (int i = 0; i < notifyEventTargetAppInfoList.size(); i++) {
                NotifyEventTargetAppInfo notifyEventTargetAppInfo = notifyEventTargetAppInfoList.get(i);
                if (event.name.equals(EVENT_ACCOUNT_CHANGE)) {
                    LogMgr.log(6, "001 event.name=MFIC_EVENT_ACCOUNT_CHANGE");
                } else if (notifyEventTargetAppInfo.getGetCardListUnlimited()) {
                    LogMgr.log(6, "002 getCardListUnlimited is true");
                } else {
                    zContains = notifyEventTargetAppInfo.getTargetServiceIdList().contains(event.serviceId);
                    LogMgr.log(6, "003 event.serviceId=" + event.serviceId);
                    LogMgr.log(6, "004 containServiceId=" + zContains);
                    boolean zCheckAppCertHashBase64 = SignatureUtil.checkAppCertHashBase64(context, notifyEventTargetAppInfo.getWalletAppCallerInfo(), notifyEventTargetAppInfo.getWalletAppIdentifiableInfo());
                    LogMgr.log(6, "005 isMatchSignInfo=" + zCheckAppCertHashBase64);
                    if (!zContains && zCheckAppCertHashBase64) {
                        NotifyInfo notifyInfo = new NotifyInfo(notifyEventTargetAppInfo);
                        try {
                            if (sendEvent(context, event, true, false, notifyInfo)) {
                                event.notifyInfoList.add(notifyInfo);
                            }
                        } catch (Exception e) {
                            LogMgr.log(2, "703 sendStartEvent Failed. event:" + event.name + "," + notifyInfo.random + "," + event.serviceId);
                            LogMgr.printStackTrace(7, e);
                        }
                    }
                }
                zContains = true;
                LogMgr.log(6, "004 containServiceId=" + zContains);
                boolean zCheckAppCertHashBase642 = SignatureUtil.checkAppCertHashBase64(context, notifyEventTargetAppInfo.getWalletAppCallerInfo(), notifyEventTargetAppInfo.getWalletAppIdentifiableInfo());
                LogMgr.log(6, "005 isMatchSignInfo=" + zCheckAppCertHashBase642);
                if (!zContains) {
                }
            }
            event.sendStatus = Event.SendStatus.SENT_START_EVENT;
            LogMgr.log(6, "999");
        } catch (JSONException e2) {
            LogMgr.log(2, "702 MfiControlInfo is in the wrong format.");
            LogMgr.printStackTrace(7, e2);
        }
    }

    public static void sendFinishEventBroadcast(Context context, Event event, boolean z) {
        LogMgr.log(6, "000");
        if (event != null) {
            if (event.sendStatus != Event.SendStatus.SENT_FINISH_EVENT) {
                for (int i = 0; i < event.notifyInfoList.size(); i++) {
                    NotifyInfo notifyInfo = (NotifyInfo) event.notifyInfoList.get(i);
                    boolean zCheckAppCertHashBase64 = SignatureUtil.checkAppCertHashBase64(context, notifyInfo.notifyEventTargetAppInfo.getWalletAppCallerInfo(), notifyInfo.notifyEventTargetAppInfo.getWalletAppIdentifiableInfo());
                    LogMgr.log(6, "002 isMatchSignInfo=" + zCheckAppCertHashBase64);
                    if (zCheckAppCertHashBase64) {
                        try {
                            sendEvent(context, event, false, z, notifyInfo);
                        } catch (Exception e) {
                            LogMgr.log(2, "702 sendFinishEvent Failed. event:" + event.name + "," + notifyInfo.random + "," + event.serviceId);
                            LogMgr.printStackTrace(7, e);
                        }
                    }
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

    private static boolean sendEvent(Context context, Event event, boolean z, boolean z2, NotifyInfo notifyInfo) {
        LogMgr.log(6, "000");
        if (context == null) {
            LogMgr.log(2, "700 context is null.");
            return false;
        }
        if (event == null) {
            LogMgr.log(2, "701 event is null.");
            return false;
        }
        LogMgr.log(6, "001 event:" + event.name + "," + z + "," + z2 + "," + notifyInfo.random + "," + event.serviceId);
        StringBuilder sb = new StringBuilder();
        sb.append("002 notifyEventTargetAppInfo:");
        sb.append(notifyInfo.notifyEventTargetAppInfo.getWalletAppIdentifiableInfo());
        sb.append(",");
        sb.append(notifyInfo.notifyEventTargetAppInfo.getClassName());
        LogMgr.log(6, sb.toString());
        Intent intent = new Intent();
        intent.setAction(ACTION_NOTIFY_EVENT);
        intent.putExtra(KEY_EVENT_NAME, event.name);
        intent.putExtra(KEY_IS_START, z);
        if (!z) {
            intent.putExtra(KEY_IS_SUCCESSFUL, z2);
        }
        intent.putExtra(KEY_RANDOM, notifyInfo.random);
        if (!event.name.equals(EVENT_ACCOUNT_CHANGE)) {
            intent.putExtra(KEY_SERVICE_ID, event.serviceId);
        }
        intent.setClassName(notifyInfo.notifyEventTargetAppInfo.getWalletAppIdentifiableInfo(), notifyInfo.notifyEventTargetAppInfo.getClassName());
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
