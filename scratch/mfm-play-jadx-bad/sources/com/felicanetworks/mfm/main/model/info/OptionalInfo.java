package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes3.dex */
public class OptionalInfo {
    private String key;
    private String value;

    public OptionalInfo(String key, String value) {
        this.key = key;
        this.value = value;
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
