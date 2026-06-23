package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes.dex */
public class OptionalInfo {
    private String key;
    private String value;

    public OptionalInfo(String str, String str2) {
        this.key = str;
        this.value = str2;
    }

    public String getOptionalInfoKey() {
        return this.key;
    }

    public String getOptionalInfoValue() {
        return this.value;
    }

    public String toString() {
        return "OptionalInfo{key='" + this.key + "', value='" + this.value + "'}";
    }
}
