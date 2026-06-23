package com.felicanetworks.mfm.oss;

/* JADX INFO: loaded from: classes3.dex */
public class OssLicensesInfo {
    public static final String OSS_LIBRARY_URL_VIEW_ACTIVITY_CLASS_NAME = "com.felicanetworks.mfm.oss.OssLicensesActivity";
    public static final String OSS_LIBRARY_URL_VIEW_ACTIVITY_PACKAGE_NAME = "com.felicanetworks.mfm.main";
    public static final String OSS_LIBRARY_VIEW_ACTION = "ossLicense_view";
    private final int mLength;
    private final String mLibraryName;
    private final int mOffset;

    public OssLicensesInfo(int i, String str, int i2) {
        this.mLength = i;
        this.mLibraryName = str;
        this.mOffset = i2;
    }

    public int getLength() {
        return this.mLength;
    }

    public String getLibraryName() {
        return this.mLibraryName;
    }

    public int getOffset() {
        return this.mOffset;
    }
}
