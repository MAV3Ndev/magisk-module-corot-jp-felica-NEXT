package com.felicanetworks.tis.datatype;

import com.felicanetworks.tis.util.LogMgr;
import com.felicanetworks.tis.util.StringUtil;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
class LogInfoData {
    static final byte AMOUNT_FLAG = 2;
    static final byte BALANCE_FLAG = 1;
    private static final byte CHARGE_CODE = 15;
    static final byte CHARGE_FLAG = 12;
    private static final byte IN_CHARGE_CODE = 47;
    static final byte IN_FLAG = 32;
    private static final byte IN_NO_PAY_CODE = 49;
    private static final byte IN_ONLY_CODE = 33;
    private static final byte IN_PAY_CODE = 43;
    static final byte NO_PAY_FLAG = 16;
    private static final byte OUT_CHARGE_CODE = 79;
    static final byte OUT_FLAG = 64;
    private static final byte OUT_NO_PAY_CODE = 81;
    private static final byte OUT_PAY_CODE = 75;
    private static final byte PAY_CODE = 11;
    static final byte PAY_FLAG = 8;
    private static final byte TAPPED_CODE = 4;
    static final byte TAPPED_FLAG = 4;
    private static final byte TAPPED_PAY_CODE = 10;
    private static final byte UNDEFINED_CODE = 0;
    private static HashMap<Integer, Byte> sPayTypeCodeMap;

    LogInfoData() {
    }

    static {
        HashMap<Integer, Byte> map = new HashMap<>();
        sPayTypeCodeMap = map;
        map.put(1, Byte.valueOf(PAY_CODE));
        sPayTypeCodeMap.put(2, Byte.valueOf(CHARGE_CODE));
        sPayTypeCodeMap.put(3, (byte) 4);
        sPayTypeCodeMap.put(4, Byte.valueOf(TAPPED_PAY_CODE));
        sPayTypeCodeMap.put(5, Byte.valueOf(IN_ONLY_CODE));
        sPayTypeCodeMap.put(6, Byte.valueOf(IN_NO_PAY_CODE));
        sPayTypeCodeMap.put(7, Byte.valueOf(IN_PAY_CODE));
        sPayTypeCodeMap.put(8, Byte.valueOf(IN_CHARGE_CODE));
        sPayTypeCodeMap.put(9, Byte.valueOf(OUT_NO_PAY_CODE));
        sPayTypeCodeMap.put(10, Byte.valueOf(OUT_PAY_CODE));
        sPayTypeCodeMap.put(11, Byte.valueOf(OUT_CHARGE_CODE));
        sPayTypeCodeMap.put(12, (byte) 0);
        sPayTypeCodeMap.put(0, (byte) 0);
    }

    static byte getPayTypeCode(int i) {
        LogMgr.log(5, "000");
        HashMap<Integer, Byte> map = sPayTypeCodeMap;
        byte bByteValue = (map == null || !map.containsKey(Integer.valueOf(i))) ? (byte) 0 : sPayTypeCodeMap.get(Integer.valueOf(i)).byteValue();
        LogMgr.log(5, "999 payCode = " + StringUtil.byteToHexString(bByteValue));
        return bByteValue;
    }
}
