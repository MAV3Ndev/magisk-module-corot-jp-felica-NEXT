package com.felicanetworks.tis.datatype;

import android.util.Base64;
import com.felicanetworks.tis.datatype.NotificationInfoConst;
import com.felicanetworks.tis.util.LogMgr;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class NotificationJsonInfo {
    public static final String JSON_IF_VERSION = "1.00";
    public static final String KEY_NAME_AMOUNT = "amount";
    public static final String KEY_NAME_CARD_ID = "cardId";
    public static final String KEY_NAME_CARD_NO = "cardNo";
    public static final String KEY_NAME_CURRENCY_CODE = "currencyCode";
    public static final String KEY_NAME_JSON_IF_VERSION = "ifVersion";
    public static final String KEY_NAME_NEW_BALANCE = "newBalance";
    public static final String KEY_NAME_NOTIFICATION_SETTING = "optedInToRenderNotification";
    public static final String KEY_NAME_SERVICE_ID = "serviceId";
    public static final String KEY_NAME_TAP_TYPE = "tapType";
    public static final String OBJECT_NAME = "felicaCardTapEvent";
    private final int mAmount;
    private final int mBalance;
    private byte[] mCardId;
    private byte[] mCardNo;
    private final String mCurrencyCode;
    private final String mServiceId;
    private final NotificationInfoConst.TapType mTapType;

    public NotificationJsonInfo(String str, NotificationInfoConst.TapType tapType, int i, int i2, String str2) {
        LogMgr.log(5, "000\u3000serviceId: " + str + ", tapType: " + tapType + ", amount: " + i + ", balance: " + i2 + ", currencyCode: " + str2);
        this.mServiceId = str;
        this.mTapType = tapType;
        this.mAmount = i;
        this.mBalance = i2;
        this.mCurrencyCode = str2;
        LogMgr.log(5, "999");
    }

    public void setCid(byte[] bArr) {
        LogMgr.log(5, "000");
        this.mCardId = bArr;
        LogMgr.log(5, "999");
    }

    public void setCardNo(byte[] bArr) {
        LogMgr.log(5, "000");
        this.mCardNo = bArr;
        LogMgr.log(5, "999");
    }

    public JSONObject createJson(boolean z) {
        String strConvertMoneyToString;
        LogMgr.log(5, "000");
        JSONObject jSONObject = null;
        if (this.mCardId == null && this.mCardNo == null) {
            LogMgr.log(2, "700 cardId and cardNo do not exist.");
            return null;
        }
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        try {
            String string = this.mTapType.getString();
            String str = this.mCurrencyCode;
            String strConvertMoneyToString2 = "";
            switch (AnonymousClass1.$SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[this.mTapType.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    strConvertMoneyToString2 = convertMoneyToString(this.mAmount);
                    strConvertMoneyToString = convertMoneyToString(this.mBalance);
                    break;
                case 8:
                    strConvertMoneyToString2 = convertMoneyToString(this.mAmount);
                    strConvertMoneyToString = "";
                    break;
                case 9:
                case 10:
                case 11:
                    strConvertMoneyToString2 = "0";
                    strConvertMoneyToString = convertMoneyToString(this.mBalance);
                    break;
                default:
                    str = "";
                    strConvertMoneyToString = str;
                    break;
            }
            jSONObject3.put("serviceId", this.mServiceId);
            jSONObject3.put(KEY_NAME_TAP_TYPE, string);
            jSONObject3.put(KEY_NAME_AMOUNT, strConvertMoneyToString2);
            jSONObject3.put(KEY_NAME_NEW_BALANCE, strConvertMoneyToString);
            jSONObject3.put(KEY_NAME_CURRENCY_CODE, str);
            jSONObject3.put(KEY_NAME_CARD_ID, encodeBase64(this.mCardId));
            jSONObject3.put(KEY_NAME_CARD_NO, encodeBase64(this.mCardNo));
            jSONObject3.put(KEY_NAME_NOTIFICATION_SETTING, z);
            jSONObject3.put(KEY_NAME_JSON_IF_VERSION, JSON_IF_VERSION);
            jSONObject2.put(OBJECT_NAME, jSONObject3);
            LogMgr.log(6, "json: " + jSONObject2.toString(4));
            jSONObject = jSONObject2;
        } catch (Exception e) {
            LogMgr.log(2, "701 json create failed. " + e);
        }
        LogMgr.log(5, "999");
        return jSONObject;
    }

    /* JADX INFO: renamed from: com.felicanetworks.tis.datatype.NotificationJsonInfo$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType;

        static {
            int[] iArr = new int[NotificationInfoConst.TapType.values().length];
            $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType = iArr;
            try {
                iArr[NotificationInfoConst.TapType.PAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.CHARGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.GIFT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.IN_PAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.IN_CHARGE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.OUT_PAY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.OUT_CHARGE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.TAPPED_PAY.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.IN_ONLY.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.IN_NO_PAY.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.OUT_NO_PAY.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.TAPPED.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.TEST_PAY.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    public String getServiceId() {
        return this.mServiceId;
    }

    public String getCardId() {
        if (this.mCardId == null) {
            return null;
        }
        return new String(this.mCardId, StandardCharsets.UTF_8);
    }

    public int getBalance() {
        return this.mBalance;
    }

    private String convertMoneyToString(int i) throws IllegalArgumentException {
        LogMgr.log(5, "000");
        String strValueOf = String.valueOf(i);
        if (i < 0 || strValueOf.length() > 6) {
            throw new IllegalArgumentException("001: Failed to convert amount information.");
        }
        LogMgr.log(5, "999");
        return String.valueOf(i);
    }

    private String encodeBase64(byte[] bArr) {
        LogMgr.log(5, "000");
        if (bArr == null) {
            return "";
        }
        LogMgr.log(5, "999");
        return Base64.encodeToString(bArr, 0);
    }
}
