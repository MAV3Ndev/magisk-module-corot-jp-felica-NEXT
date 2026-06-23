package com.felicanetworks.mfc.mfi.felica.access_control;

import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public interface AccessController {
    public static final int CATEGORY_ADMIN_START = 14;
    public static final int CATEGORY_CARD_ACCESS = 16;
    public static final int CATEGORY_CARD_ENABLE_DISABLE = 8;
    public static final int CATEGORY_CARD_GET_INFO = 6;
    public static final int CATEGORY_CARD_ISSUE_DELETE = 7;
    public static final int CATEGORY_CLEAR_ACCOUNT = 9;
    public static final int CATEGORY_EXIST_EMPTY_SLOT = 51;
    public static final int CATEGORY_GET_CARD_LIST_ADDITIONAL_INFO_1 = 12;
    public static final int CATEGORY_GET_CARD_LIST_SP_SYNC = 48;
    public static final int CATEGORY_GET_CARD_LIST_UNLIMITED = 11;
    public static final int CATEGORY_IDENTIFY_SERVICE = 32;
    public static final int CATEGORY_INITIALIZE = 13;
    public static final int CATEGORY_OFFLINE_ACCESS = 1;
    public static final int CATEGORY_ONLINE_ACCESS = 2;
    public static final int CATEGORY_PRIVILEGE = 50;
    public static final int CATEGORY_PRIVILEGED_ACCESS_1 = 4;
    public static final int CATEGORY_PRIVILEGED_ACCESS_2 = 5;
    public static final int CATEGORY_RW_ACCESS = 3;
    public static final int CATEGORY_SKIP_AGREEMENT_PAGE = 10;
    public static final int CATEGORY_USE_UNSUPPORT_MFI_SERVICE1_CARD = 49;

    void check(int i) throws AccessControllerException;

    void checkMfiServiceId(String str) throws AccessControllerException;

    void checkNodeCodeList(int i, int[] iArr) throws AccessControllerException;

    void checkSystemCode(int i) throws AccessControllerException;

    String getWalletAppCallerInfo();

    JSONArray getWalletAppCertHashList();

    String getWalletAppId();

    String getWalletAppIdentifiableInfo();
}
