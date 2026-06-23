package com.felicanetworks.mfc.felica.access_control;

/* JADX INFO: loaded from: classes.dex */
public interface AccessController {
    public static final int CATEGORY_OFFLINE_ACCESS = 1;
    public static final int CATEGORY_ONLINE_ACCESS = 2;
    public static final int CATEGORY_PRIVILEGED_ACCESS_1 = 4;
    public static final int CATEGORY_PRIVILEGED_ACCESS_2 = 5;
    public static final int CATEGORY_RW_ACCESS = 3;

    void check(int i) throws AccessControllerException, NumberFormatException;

    void checkNodeCodeList(int i, int[] iArr) throws AccessControllerException, NumberFormatException;

    void checkSystemCode(int i) throws AccessControllerException, NumberFormatException;
}
