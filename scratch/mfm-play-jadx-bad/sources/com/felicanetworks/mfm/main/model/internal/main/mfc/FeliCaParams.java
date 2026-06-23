package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.tis.resolver.ResolverConst;

/* JADX INFO: loaded from: classes3.dex */
public class FeliCaParams {
    private static final int BLOCK_NO_DCARD_ISSURE_MANAGE = 2;
    private static final int BLOCK_NO_DCARD_ISSURE_SETTING1 = 2;
    private static final int BLOCK_NO_DCARD_ISSURE_SETTING2 = 3;
    private static final int BLOCK_NO_DCARD_ISSURE_SETTING3 = 4;
    public static final int BLOCK_NO_EDY = 0;
    public static final int BLOCK_NO_FELICAPOCKET_LITE_CARD_IDS = 13;
    public static final int BLOCK_NO_FELICAPOCKET_LITE_DATA_FORMAT_CODE = 130;
    public static final int BLOCK_NO_FELICAPOCKET_LITE_INDEX_A = 12;
    public static final int BLOCK_NO_FELICAPOCKET_STD_CARD_IDS = 0;
    private static final int BLOCK_NO_QUICPAY_ID = 4;
    public static final int BLOCK_NO_SUICA = 0;
    public static final String CARD_CATEGORY_TRAFFIC = "GC000001";
    public static final int EX_IC_CARD_RETRY_COUNT = 0;
    public static final int EX_IC_CARD_START_APP_INTERVAL = 3000;
    public static final int EX_IC_CARD_TIMEOUT_VALUE = 1000;
    public static final byte IC_CODE_LITE_CARD = -15;
    private static final int READ_POSITION_QUICPAY_ID = 0;
    private static final int READ_SIZE_QUICPAY_ID = 10;
    public static final int RETRY_COUNT_FOR_ACTIVATE_ON_RUNNING_BY_MFIC = 5;
    public static final int RETRY_INTERVAL_FOR_ACTIVATE_ON_RUNNING_BY_MFIC = 1000;
    public static final int SERVICE_CODE_FELICAPOCKET_LITE = 11;
    public static final int SERVICE_CODE_FELICAPOCKET_STD_CARD_IDS = 14667;
    public static final int SERVICE_CODE_FELICAPOCKET_STD_INDEX = 14793;
    public static final int SERVICE_CODE_FELICAPOCKET_STD_KEYVER = 14728;
    public static final int SERVICE_CODE_SUICA = 139;
    private static final int SERVICE_CODE_TRAFFIC_BALANCE = 139;
    private static final int SERVICE_CODE_WAON_FLAGS2 = 26635;
    private static final int SERVICE_CODE_WAON_HISTORY = 26635;
    public static String SERVICE_ID_DCARD = "SV000006";
    public static final String SERVICE_ID_EDY = "SV000013";
    public static final String SERVICE_ID_FP = "SV000066";
    public static final String SERVICE_ID_NANACO = "SV000075";
    public static String SERVICE_ID_QP = "SV000024";
    public static String SERVICE_ID_SUICA = "SV000027";
    public static final String SERVICE_ID_WAON = "SV000011";
    public static final int SYSTEM_CODE_COMMON = 65024;
    public static final int SYSTEM_CODE_LITE_CARD = 34996;
    public static final int SYSTEM_CODE_MANAGEMENT = 65039;
    public static final int SYSTEM_CODE_STD_CARD = 65024;
    public static final int SYSTEM_CODE_TRAFFIC = 3;
    public static final int[] VALID_SYSTEM_CODES = {65024, 3};
    private static int SERVICE_CODE_DCARD_ISSURE_MANAGE = 16523;
    private static int SERVICE_CODE_DCARD_ISSURE_SETTING = 16587;
    private static int SERVICE_CODE_QUICPAY_ID = ResolverConst.Q16.SERVICE_CODE;
    public static int[][] READ_QUICPAY_ID = {new int[]{ResolverConst.Q16.SERVICE_CODE, 4, 0, 10}};
    public static final int[][] READ_BALANCE_INFO_LIST_TRAFFIC = {new int[]{139, 0}};
    public static final int SERVICE_CODE_EDY_BALANCE = 4887;
    public static final int[][] READ_BALANCE_INFO_LIST_EDY = {new int[]{SERVICE_CODE_EDY_BALANCE, 0}};
    private static final int SERVICE_CODE_NANACO_BALANCE = 21911;
    private static final int SERVICE_CODE_NANACO_POINT = 22027;
    public static final int[][] READ_BALANCE_INFO_LIST_NANACO = {new int[]{SERVICE_CODE_NANACO_BALANCE, 0}, new int[]{SERVICE_CODE_NANACO_POINT, 1}};
    private static final int SERVICE_CODE_WAON_BALANCE = 26647;
    private static final int SERVICE_CODE_WAON_FLAGS1 = 26571;
    private static final int SERVICE_CODE_WAON_FLAGS3 = 26699;
    public static final int[][] READ_BALANCE_INFO_LIST_WAON = {new int[]{SERVICE_CODE_WAON_BALANCE, 0}, new int[]{SERVICE_CODE_WAON_FLAGS1, 1}, new int[]{26635, 7}, new int[]{SERVICE_CODE_WAON_FLAGS3, 0}};
    public static int[][] READ_BALANCE_INFO_LIST_DCARD = {new int[]{16523, 2}, new int[]{16587, 2}, new int[]{16587, 3}, new int[]{16587, 4}};
    private static final int SERVICE_CODE_TRAFFIC_HISTORY = 2319;
    public static final int[][] READ_HISTORY_INFO_LIST_TRAFFIC = {new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 0}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 1}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 2}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 3}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 4}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 5}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 6}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 7}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 8}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 9}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 10}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 11}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 12}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 13}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 14}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 15}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 16}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 17}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 18}, new int[]{SERVICE_CODE_TRAFFIC_HISTORY, 19}};
    private static final int SERVICE_CODE_EDY_HISTORY = 5903;
    public static final int[][] READ_HISTORY_INFO_LIST_EDY = {new int[]{SERVICE_CODE_EDY_HISTORY, 0}, new int[]{SERVICE_CODE_EDY_HISTORY, 1}, new int[]{SERVICE_CODE_EDY_HISTORY, 2}, new int[]{SERVICE_CODE_EDY_HISTORY, 3}, new int[]{SERVICE_CODE_EDY_HISTORY, 4}, new int[]{SERVICE_CODE_EDY_HISTORY, 5}};
    private static final int SERVICE_CODE_NANACO_HISTORY = 22095;
    public static final int[][] READ_HISTORY_INFO_LIST_NANACO = {new int[]{SERVICE_CODE_NANACO_HISTORY, 0}, new int[]{SERVICE_CODE_NANACO_HISTORY, 1}, new int[]{SERVICE_CODE_NANACO_HISTORY, 2}, new int[]{SERVICE_CODE_NANACO_HISTORY, 3}, new int[]{SERVICE_CODE_NANACO_HISTORY, 4}};
    public static final int[][] READ_HISTORY_INFO_LIST_WAON = {new int[]{26635, 1}, new int[]{26635, 3}, new int[]{26635, 5}};
    public static final String[][] READ_INFO_SAS = {new String[]{"S1", "000074CB", "2", "0", "4"}, new String[]{"S1", "000074CB", "2", "8", "4"}, new String[]{"S1", "000074CB", "3", "0", "4"}, new String[]{"S1", "000074CB", "3", "8", "4"}, new String[]{"S1", "000074CB", "4", "0", "4"}, new String[]{"S1", "000074CB", "4", "8", "4"}, new String[]{"S1", "000074CB", "5", "0", "4"}, new String[]{"S1", "000074CB", "5", "8", "4"}, new String[]{"S2", "000074CB", "6", "0", "4"}, new String[]{"S2", "000074CB", "6", "8", "4"}, new String[]{"S2", "000074CB", "7", "0", "4"}, new String[]{"S2", "000074CB", "7", "8", "4"}, new String[]{"S2", "000074CB", "8", "0", "4"}, new String[]{"S2", "000074CB", "8", "8", "4"}, new String[]{"KT", "000074CB", "9", "0", "4"}, new String[]{"T1", "00007549", "0", "0", "4"}, new String[]{"T2", "00007589", "0", "0", "4"}};

    @FunctionalInterface
    public interface Consumer<T> {
        void accept(T t);
    }

    public static void setServiceIdDcard(Object serviceIdDcard) {
        SERVICE_ID_DCARD = (String) serviceIdDcard;
    }

    public static void setServiceIdSuica(Object serviceIdSuica) {
        SERVICE_ID_SUICA = (String) serviceIdSuica;
    }

    public static void setServiceIdQp(Object serviceIdQp) {
        SERVICE_ID_QP = (String) serviceIdQp;
    }

    public static void setServiceCodeDcardIssureManage(Object serviceCodeDcardIssureManage) {
        SERVICE_CODE_DCARD_ISSURE_MANAGE = ((Integer) serviceCodeDcardIssureManage).intValue();
    }

    public static void setServiceCodeDcardIssureSetting(Object serviceCodeDcardIssureSetting) {
        SERVICE_CODE_DCARD_ISSURE_SETTING = ((Integer) serviceCodeDcardIssureSetting).intValue();
    }

    public static void setServiceCodeQp(Object serviceCodeQp) {
        SERVICE_CODE_QUICPAY_ID = ((Integer) serviceCodeQp).intValue();
    }

    public static void updateReadInfo() {
        int[] iArr = {SERVICE_CODE_DCARD_ISSURE_MANAGE, 2};
        int i = SERVICE_CODE_DCARD_ISSURE_SETTING;
        READ_BALANCE_INFO_LIST_DCARD = new int[][]{iArr, new int[]{i, 2}, new int[]{i, 3}, new int[]{i, 4}};
        READ_QUICPAY_ID = new int[][]{new int[]{SERVICE_CODE_QUICPAY_ID, 4, 0, 10}};
    }
}
