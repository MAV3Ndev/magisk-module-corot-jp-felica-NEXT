package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes.dex */
public class TermOfValidity {
    public String end;
    public boolean extension;
    public String key;
    public String start;

    public TermOfValidity(String str, String str2, String str3, boolean z) {
        this.key = str;
        this.start = str2;
        this.end = str3;
        this.extension = z;
    }

    public String toString() {
        return "TermOfValidity{key=" + this.key + ", start=" + this.start + ", end=" + this.end + ", extension=" + this.extension + '}';
    }
}
