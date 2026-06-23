package com.felicanetworks.tis.datatype;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import com.felicanetworks.tis.ui.CardDialogIntentCreator;

/* JADX INFO: loaded from: classes.dex */
public class NotificationInfo implements Comparable<NotificationInfo> {
    private byte[] mIdm;
    private Bitmap mLargeIcon;
    private LogInfo mLogInfo = new LogInfo();
    private String mMessage;
    private int mOrder;
    private String mServiceId;
    private int mSmallIcon;
    private Intent mStartIntent;
    private String mTitle;

    public NotificationInfo(String str, String str2, int i, Bitmap bitmap, byte[] bArr, String str3, int i2) {
        this.mTitle = str;
        this.mMessage = str2;
        this.mSmallIcon = i;
        this.mLargeIcon = bitmap;
        this.mIdm = bArr;
        this.mServiceId = str3;
        this.mOrder = i2;
    }

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

    public void setIntent(Context context, int i, int i2, int i3) {
        this.mStartIntent = CardDialogIntentCreator.createStartIntent(context, i, this.mServiceId, i2, i3);
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
}
