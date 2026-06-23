package com.felicanetworks.tis.datatype;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import com.felicanetworks.tis.ui.CardDialogIntentCreator;
import com.felicanetworks.tis.util.LogMgr;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class NotificationInfo implements Comparable<NotificationInfo> {
    private final byte[] mIdm;
    private final Bitmap mLargeIcon;
    private LogInfo mLogInfo = new LogInfo();
    private final String mMessage;
    private NotificationJsonInfo mNotificationJsonInfo;
    private final int mOrder;
    private final String mServiceId;
    private final int mSmallIcon;
    private Intent mStartIntent;
    private final String mTitle;

    public NotificationInfo(String str, String str2, int i, Bitmap bitmap, byte[] bArr, String str3, int i2) {
        this.mTitle = str;
        this.mMessage = str2;
        this.mSmallIcon = i;
        this.mLargeIcon = bitmap;
        this.mIdm = bArr;
        this.mServiceId = str3;
        this.mOrder = i2;
    }

    /* JADX DEBUG: Method merged with bridge method: compareTo(Ljava/lang/Object;)I */
    @Override // java.lang.Comparable
    public int compareTo(NotificationInfo notificationInfo) {
        return Integer.compare(this.mOrder, notificationInfo.getOrder());
    }

    public int hashCode() {
        return this.mOrder;
    }

    public boolean equals(Object obj) {
        return (obj instanceof NotificationInfo) && compareTo((NotificationInfo) obj) == 0;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public int getSmallIcon() {
        return this.mSmallIcon;
    }

    public Bitmap getLargeIcon() {
        return this.mLargeIcon;
    }

    public byte[] getIdm() {
        return this.mIdm;
    }

    public String getServiceId() {
        return this.mServiceId;
    }

    public int getOrder() {
        return this.mOrder;
    }

    public void setIntent(Context context, int i, int i2, int i3, int i4, int i5) {
        this.mStartIntent = CardDialogIntentCreator.createStartIntent(context, i, this.mServiceId, i2, i3, i4, i5, false, null);
    }

    public void setIntent(Context context, int i, int i2, int i3, int i4, int i5, boolean z, String str) {
        this.mStartIntent = CardDialogIntentCreator.createStartIntent(context, i, this.mServiceId, i2, i3, i4, i5, z, str);
    }

    public Intent getStartIntent() {
        return this.mStartIntent;
    }

    public PendingIntent getContextIntent(Context context) {
        if (this.mStartIntent != null) {
            return CardDialogIntentCreator.createContextIntent(context, new Intent(this.mStartIntent));
        }
        return null;
    }

    public void setExtraUuid(String str) {
        Intent intent = this.mStartIntent;
        if (intent != null) {
            CardDialogIntentCreator.setExtraUuid(intent, str);
        }
    }

    public void setLogInfo(LogInfo logInfo) {
        this.mLogInfo = logInfo;
    }

    public LogInfo getLogInfo() {
        return this.mLogInfo;
    }

    public void setNotificationJsonInfo(NotificationJsonInfo notificationJsonInfo) {
        LogMgr.log(5, "000");
        this.mNotificationJsonInfo = notificationJsonInfo;
        LogMgr.log(5, "999");
    }

    public void setCidJsonItem(byte[] bArr) {
        LogMgr.log(5, "000");
        NotificationJsonInfo notificationJsonInfo = this.mNotificationJsonInfo;
        if (notificationJsonInfo != null) {
            notificationJsonInfo.setCid(bArr);
        }
        LogMgr.log(5, "999");
    }

    public void setCardNoJsonItem(byte[] bArr) {
        LogMgr.log(5, "000");
        NotificationJsonInfo notificationJsonInfo = this.mNotificationJsonInfo;
        if (notificationJsonInfo != null) {
            notificationJsonInfo.setCardNo(bArr);
        }
        LogMgr.log(5, "999");
    }

    public JSONObject createNotificationJson(boolean z) {
        LogMgr.log(5, "000");
        if (this.mNotificationJsonInfo == null) {
            LogMgr.log(2, "700 mNotificationJsonInfo is null.");
            return null;
        }
        LogMgr.log(5, "999");
        return this.mNotificationJsonInfo.createJson(z);
    }

    public String getServiceIdJson() {
        NotificationJsonInfo notificationJsonInfo = this.mNotificationJsonInfo;
        if (notificationJsonInfo == null) {
            return null;
        }
        return notificationJsonInfo.getServiceId();
    }

    public String getCardId() {
        NotificationJsonInfo notificationJsonInfo = this.mNotificationJsonInfo;
        if (notificationJsonInfo == null) {
            return null;
        }
        return notificationJsonInfo.getCardId();
    }

    public int getBalance() {
        NotificationJsonInfo notificationJsonInfo = this.mNotificationJsonInfo;
        if (notificationJsonInfo == null) {
            return -1;
        }
        return notificationJsonInfo.getBalance();
    }
}
