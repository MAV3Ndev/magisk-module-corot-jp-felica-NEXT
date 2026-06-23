package com.felicanetworks.semc.config;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.felicanetworks.semc.SemClientException;
import com.felicanetworks.semc.util.CommonConfig;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

/* JADX INFO: loaded from: classes3.dex */
public final class AccessConfig {
    private static final String CHIP_TYPE_KEY = "00000013";
    private static final String DEVICE_IDENTIFICATION_INFO = "00000018";
    public static final String DEVICE_IDENTIFICATION_VALUE_DOMESTIC = "1";
    private static final String GP_ESE_READER_NAME_KEY = "00000011";
    private static final String SKU_KEY_VALUE_KEY = "00000015";
    private static final String SKU_URL_KEY = "00000014";
    private static final String SYSTEM_FILE_CAREER_IDENTIFY_CODE_KEY = "00000002";
    public static final String SYSTEM_FILE_CHIP_ISSUER_ID_KEY = "00000001";

    AccessConfig() {
        LogMgr.log(7, "000");
        LogMgr.log(7, "999");
    }

    public static String getChipIssuerId() {
        String value;
        LogMgr.log(7, "000");
        try {
            value = CommonConfig.getInstance().getValue(SYSTEM_FILE_CHIP_ISSUER_ID_KEY);
        } catch (FileNotFoundException unused) {
            LogMgr.log(9, "001 FileNotFoundException occurred.");
            value = null;
        } catch (IOException unused2) {
            LogMgr.log(9, "003 IOException occurred.");
            value = null;
        } catch (ParseException unused3) {
            LogMgr.log(9, "002 ParseException occurred.");
            value = null;
        }
        LogMgr.log(7, "999");
        return value;
    }

    public static String getCareerIdentifyCode() {
        String value;
        LogMgr.log(7, "000");
        try {
            value = CommonConfig.getInstance().getValue(SYSTEM_FILE_CAREER_IDENTIFY_CODE_KEY);
        } catch (FileNotFoundException unused) {
            LogMgr.log(9, "001 FileNotFoundException occurred.");
            value = null;
        } catch (IOException unused2) {
            LogMgr.log(9, "003 IOException occurred.");
            value = null;
        } catch (ParseException unused3) {
            LogMgr.log(9, "002 ParseException occurred.");
            value = null;
        }
        LogMgr.log(7, "999");
        return value;
    }

    public static String getGpEseReaderName() {
        String value;
        LogMgr.log(7, "000");
        try {
            value = CommonConfig.getInstance().getValue(GP_ESE_READER_NAME_KEY);
        } catch (FileNotFoundException unused) {
            LogMgr.log(9, "001 FileNotFoundException occurred.");
            value = null;
        } catch (IOException unused2) {
            LogMgr.log(9, "003 IOException occurred.");
            value = null;
        } catch (ParseException unused3) {
            LogMgr.log(9, "002 ParseException occurred.");
            value = null;
        }
        LogMgr.log(7, "999");
        return value;
    }

    public static String getChipType() {
        String value;
        LogMgr.log(7, "000");
        try {
            value = CommonConfig.getInstance().getValue(CHIP_TYPE_KEY);
        } catch (FileNotFoundException unused) {
            LogMgr.log(9, "001 FileNotFoundException occurred.");
            value = null;
        } catch (IOException unused2) {
            LogMgr.log(9, "003 IOException occurred.");
            value = null;
        } catch (ParseException unused3) {
            LogMgr.log(9, "002 ParseException occurred.");
            value = null;
        }
        LogMgr.log(7, "999");
        return value;
    }

    public static String getSkuUrl() {
        String value;
        LogMgr.log(7, "000");
        try {
            value = CommonConfig.getInstance().getValue(SKU_URL_KEY);
        } catch (FileNotFoundException unused) {
            LogMgr.log(9, "001 FileNotFoundException occurred.");
            value = null;
        } catch (IOException unused2) {
            LogMgr.log(9, "003 IOException occurred.");
            value = null;
        } catch (ParseException unused3) {
            LogMgr.log(9, "002 ParseException occurred.");
            value = null;
        }
        LogMgr.log(7, "999");
        return value;
    }

    public static String getSkuKeyValue() {
        String value;
        LogMgr.log(7, "000");
        try {
            value = CommonConfig.getInstance().getValue(SKU_KEY_VALUE_KEY);
        } catch (FileNotFoundException unused) {
            LogMgr.log(9, "001 FileNotFoundException occurred.");
            value = null;
        } catch (IOException unused2) {
            LogMgr.log(9, "003 IOException occurred.");
            value = null;
        } catch (ParseException unused3) {
            LogMgr.log(9, "002 ParseException occurred.");
            value = null;
        }
        LogMgr.log(7, "999");
        return value;
    }

    public static String getDeviceIdentificationInfo() {
        String value;
        LogMgr.log(7, "000");
        try {
            value = CommonConfig.getInstance().getValue(DEVICE_IDENTIFICATION_INFO);
        } catch (FileNotFoundException unused) {
            LogMgr.log(9, "001 FileNotFoundException occurred.");
            value = null;
        } catch (IOException unused2) {
            LogMgr.log(9, "003 IOException occurred.");
            value = null;
        } catch (ParseException unused3) {
            LogMgr.log(9, "002 ParseException occurred.");
            value = null;
        }
        LogMgr.log(7, "999");
        return value;
    }

    public static String getDeviceIdentificationData(Context context) throws SemClientException {
        String deviceIdentificationInfo;
        LogMgr.log(8, "000");
        String skuUrl = getSkuUrl();
        LogMgr.log(8, "001 skuUrl=" + skuUrl);
        String skuKeyValue = getSkuKeyValue();
        LogMgr.log(8, "002 SKU skuKeyValue=" + skuKeyValue);
        if (skuUrl != null && skuKeyValue != null) {
            Cursor cursorQuery = context.getContentResolver().query(Uri.parse(skuUrl), null, null, null, null);
            if (cursorQuery == null) {
                LogMgr.log(1, "800 Fail to get Cursor For Get deviceIdentificationData");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            cursorQuery.moveToFirst();
            int columnIndex = cursorQuery.getColumnIndex(skuKeyValue);
            int type = cursorQuery.getType(columnIndex);
            LogMgr.log(8, "003 SKU FieldType=" + type);
            if (type == 1) {
                deviceIdentificationInfo = Integer.toString(cursorQuery.getInt(columnIndex));
            } else if (type == 3) {
                deviceIdentificationInfo = cursorQuery.getString(columnIndex);
            } else {
                LogMgr.log(1, "801 Invalid skuFieldType");
                cursorQuery.close();
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            if (deviceIdentificationInfo == null) {
                LogMgr.log(1, "802 Fail to get deviceIdentificationData From skuKeyUrl and skuKeyValue");
                cursorQuery.close();
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            LogMgr.log(8, "004 deviceIdentificationData=" + deviceIdentificationInfo);
            cursorQuery.close();
        } else {
            if (skuUrl != null || skuKeyValue != null) {
                LogMgr.log(1, "803 Either skuUrl or skuKeyValue is null.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            deviceIdentificationInfo = getDeviceIdentificationInfo();
        }
        LogMgr.log(8, "999");
        return deviceIdentificationInfo;
    }
}
