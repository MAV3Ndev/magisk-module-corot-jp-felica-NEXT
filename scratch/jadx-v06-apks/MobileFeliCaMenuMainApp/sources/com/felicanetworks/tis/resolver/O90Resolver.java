package com.felicanetworks.tis.resolver;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import com.felicanetworks.tis.R;
import com.felicanetworks.tis.datatype.LogInfo;
import com.felicanetworks.tis.datatype.NotificationInfo;
import com.felicanetworks.tis.resolver.S11Resolver;

/* JADX INFO: loaded from: classes.dex */
class O90Resolver extends S11Resolver {
    private static final String SERVICE_ID = "o90";

    O90Resolver() {
    }

    @Override // com.felicanetworks.tis.resolver.S11Resolver
    protected NotificationInfo createInNotificationInfo(int i, int i2, int i3, Resources resources, byte[] bArr, LogInfo logInfo) {
        String strCreateMessage;
        int i4;
        int i5;
        String strCreateMessage2;
        int i6;
        String strCreateMessage3;
        int i7 = i;
        int i8 = i3;
        int i9 = 0;
        if (i7 < 0) {
            strCreateMessage = null;
            i7 = 0;
            i4 = 0;
            i5 = 0;
        } else if (i2 < 0) {
            i5 = 5;
            strCreateMessage = createMessage(resources, S11Resolver.PAY_TYPE.IN_ONLY, 0, i7);
            i4 = 0;
        } else if (i8 >= 0) {
            int i10 = i2 - i8;
            if (i10 > 0) {
                strCreateMessage3 = createMessage(resources, S11Resolver.PAY_TYPE.IN_PAY, i10, i8);
                i9 = 7;
            } else if (i10 < 0) {
                i10 = -i10;
                strCreateMessage3 = createMessage(resources, S11Resolver.PAY_TYPE.IN_CHARGE, i10, i8);
                i9 = 8;
            } else {
                strCreateMessage3 = null;
                i10 = 0;
                i8 = 0;
            }
            strCreateMessage = strCreateMessage3;
            i4 = i10;
            i7 = i8;
            i5 = i9;
        } else {
            int i11 = i7 - i2;
            if (i11 > 0) {
                strCreateMessage2 = createMessage(resources, S11Resolver.PAY_TYPE.IN_PAY, i11, i2);
                i6 = 7;
            } else if (i11 < 0) {
                i11 = -i11;
                strCreateMessage2 = createMessage(resources, S11Resolver.PAY_TYPE.IN_CHARGE, i11, i2);
                i6 = 8;
            } else {
                strCreateMessage2 = createMessage(resources, S11Resolver.PAY_TYPE.IN_NO_PAY, i11, i2);
                i6 = 6;
            }
            strCreateMessage = strCreateMessage2;
            i5 = i6;
            i4 = i11;
            i7 = i2;
        }
        if (strCreateMessage == null) {
            return null;
        }
        NotificationInfo notificationInfo = new NotificationInfo(resources.getString(R.string.tis_title_o90), strCreateMessage, R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_o90_icon), bArr, SERVICE_ID, 0);
        notificationInfo.setIntent(this.mContext, i5, i4, i7);
        logInfo.setPaymentLogInfo(i5, i4, i7);
        notificationInfo.setLogInfo(logInfo);
        return notificationInfo;
    }

    @Override // com.felicanetworks.tis.resolver.S11Resolver
    protected NotificationInfo createOutNotificationInfo(int i, int i2, int i3, Resources resources, byte[] bArr, LogInfo logInfo) {
        String strCreateMessage;
        int i4;
        String strCreateMessage2;
        String strCreateMessage3;
        int i5 = i;
        int i6 = i3;
        int i7 = 9;
        int i8 = 0;
        if (i5 < 0) {
            strCreateMessage = null;
            i5 = 0;
            i4 = 0;
            i7 = 0;
        } else if (i2 >= 0) {
            if (i6 >= 0) {
                int i9 = i2 - i6;
                if (i9 > 0) {
                    strCreateMessage3 = createMessage(resources, S11Resolver.PAY_TYPE.OUT_PAY, i9, i6);
                    i8 = i9;
                    i7 = 10;
                } else {
                    strCreateMessage3 = null;
                    i6 = 0;
                    i7 = 0;
                }
                strCreateMessage = strCreateMessage3;
                i5 = i6;
            } else {
                int i10 = i5 - i2;
                if (i10 > 0) {
                    strCreateMessage2 = createMessage(resources, S11Resolver.PAY_TYPE.OUT_PAY, i10, i2);
                    i8 = i10;
                    i7 = 10;
                } else {
                    if (i10 < 0) {
                        i10 = -i10;
                        strCreateMessage2 = createMessage(resources, S11Resolver.PAY_TYPE.OUT_CHARGE, i10, i2);
                        i7 = 11;
                    } else {
                        strCreateMessage2 = createMessage(resources, S11Resolver.PAY_TYPE.OUT_NO_PAY, i10, i2);
                    }
                    i8 = i10;
                }
                i5 = i2;
                strCreateMessage = strCreateMessage2;
            }
            i4 = i8;
        } else {
            strCreateMessage = createMessage(resources, S11Resolver.PAY_TYPE.OUT_NO_PAY, 0, i5);
            i4 = 0;
        }
        if (strCreateMessage == null) {
            return null;
        }
        NotificationInfo notificationInfo = new NotificationInfo(resources.getString(R.string.tis_title_o90), strCreateMessage, R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_o90_icon), bArr, SERVICE_ID, 0);
        notificationInfo.setIntent(this.mContext, i7, i4, i5);
        logInfo.setPaymentLogInfo(i7, i4, i5);
        notificationInfo.setLogInfo(logInfo);
        return notificationInfo;
    }

    @Override // com.felicanetworks.tis.resolver.S11Resolver
    protected NotificationInfo createNotificationInfo(int i, int i2, int i3, Resources resources, byte[] bArr, LogInfo logInfo) {
        if (i >= 0 && i2 >= 0 && i3 < 0) {
            int i4 = i - i2;
            if (i4 > 0) {
                NotificationInfo notificationInfo = new NotificationInfo(resources.getString(R.string.tis_title_o90), createMessage(resources, S11Resolver.PAY_TYPE.PAY, i4, i2), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_o90_icon), bArr, SERVICE_ID, 0);
                notificationInfo.setIntent(this.mContext, 1, i4, i2);
                logInfo.setPaymentLogInfo(1, i4, i2);
                notificationInfo.setLogInfo(logInfo);
                return notificationInfo;
            }
            if (i4 < 0) {
                int i5 = -i4;
                NotificationInfo notificationInfo2 = new NotificationInfo(resources.getString(R.string.tis_title_o90), createMessage(resources, S11Resolver.PAY_TYPE.CHARGE, i5, i2), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_o90_icon), bArr, SERVICE_ID, 0);
                notificationInfo2.setIntent(this.mContext, 2, i5, i2);
                logInfo.setPaymentLogInfo(2, i5, i2);
                notificationInfo2.setLogInfo(logInfo);
                return notificationInfo2;
            }
        }
        return null;
    }
}
