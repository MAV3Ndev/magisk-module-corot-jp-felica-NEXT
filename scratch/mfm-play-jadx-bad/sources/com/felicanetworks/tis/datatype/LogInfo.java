package com.felicanetworks.tis.datatype;

import com.felicanetworks.tis.util.LogMgr;
import com.felicanetworks.tis.util.StringUtil;
import com.google.android.material.timepicker.TimeModel;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public class LogInfo {
    private static final int BLOCK_DATA_LENGTH = 32;
    private static final String DATA_SEPARATOR = ",";
    private static final int SEPARATOR_LENGTH = 3;
    private static final int SERVICE_CODE_LENGTH = 4;
    private static final int TAG_LENGTH = 2;
    private static final int USAGE_DATA_MAX_LENGTH = 200;
    private String mPayType = "";
    private String mPayAmount = "";
    private String mPayBalance = "";
    private String mUsageData = "";

    public String getPayType() {
        return this.mPayType;
    }

    public String getPayAmount() {
        return this.mPayAmount;
    }

    public String getPayBalance() {
        return this.mPayBalance;
    }

    public String getUsageData() {
        return this.mUsageData;
    }

    public void setPaymentLogInfo(int i, int i2, int i3) {
        LogMgr.log(5, "000");
        try {
            byte payTypeCode = LogInfoData.getPayTypeCode(i);
            String str = String.format(Locale.JAPAN, TimeModel.NUMBER_FORMAT, Integer.valueOf(i2));
            String str2 = String.format(Locale.JAPAN, TimeModel.NUMBER_FORMAT, Integer.valueOf(i3));
            if (payTypeCode != 0) {
                this.mPayType = StringUtil.byteToHexString(payTypeCode);
                if ((payTypeCode & 2) == 2) {
                    this.mPayAmount = str;
                }
                if ((payTypeCode & 1) == 1) {
                    this.mPayBalance = str2;
                }
            }
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        LogMgr.log(5, "999");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    public void addUsageData(TransactionInfo transactionInfo) {
        LogMgr.log(5, "000");
        if (transactionInfo == null) {
            LogMgr.log(2, "700 UsageData is null");
            return;
        }
        if (this.mUsageData.length() + 41 > 200) {
            LogMgr.log(2, "701 UsageData is too long. Ignore new data.");
            return;
        }
        try {
            String strIntToHexString = StringUtil.intToHexString(transactionInfo.getTag(), 2);
            String strIntToHexString2 = StringUtil.intToHexString(transactionInfo.getServiceCode(), 4);
            String strBytesToHexString = StringUtil.bytesToHexString(transactionInfo.getBlockData());
            StringBuilder sb = new StringBuilder();
            sb.append(strIntToHexString);
            sb.append(DATA_SEPARATOR);
            sb.append(strIntToHexString2);
            sb.append(DATA_SEPARATOR);
            sb.append(strBytesToHexString);
            LogMgr.log(6, "001 UsageData = " + sb.toString());
            if (this.mUsageData.length() == 0) {
                this.mUsageData = sb.toString();
            } else {
                this.mUsageData += DATA_SEPARATOR + sb.toString();
            }
        } catch (Exception e) {
            LogMgr.log(2, "702 " + e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        LogMgr.log(5, "999");
    }
}
