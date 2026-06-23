package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes3.dex */
public class TermOfValidity {
    public String end;
    public boolean extension;
    public String key;
    public String start;

    public TermOfValidity(String key, String start, String end, boolean extension) {
        this.key = key;
        this.start = start;
        this.end = end;
        this.extension = extension;
    }

    public String toString() {
        return "TermOfValidity{key=" + this.key + ", start=" + this.start + ", end=" + this.end + ", extension=" + this.extension + '}';
    }
}
