package com.felicanetworks.mfm.main.policy.fix;

/* JADX INFO: loaded from: classes.dex */
public enum MfiLoginResultCode {
    CANCELLED_BY_ANDROID(0),
    SUCCESS(1),
    UNEXPECTED_ERROR(2),
    ILLEGAL_CODE(3),
    USED_BY_OTHER_APP(4),
    CANCELLED_BY_USER_DISAGREE(5),
    ILLEGAL_ACCOUNT(6),
    UNAVAILABLE_LIBRARY(7),
    FAILED_COMMUNICATION(8),
    HTTP_ERROR(9),
    CANCELLED_BY_USER_SKIPPED(10),
    INVALID(-1),
    NONE(Integer.MAX_VALUE);

    private final int code;

    MfiLoginResultCode(int i) {
        this.code = i;
    }

    public static MfiLoginResultCode resolve(int i) {
        for (MfiLoginResultCode mfiLoginResultCode : values()) {
            if (mfiLoginResultCode.code == i) {
                return mfiLoginResultCode;
            }
        }
        return INVALID;
    }

    public int getCode() {
        return this.code;
    }
}
