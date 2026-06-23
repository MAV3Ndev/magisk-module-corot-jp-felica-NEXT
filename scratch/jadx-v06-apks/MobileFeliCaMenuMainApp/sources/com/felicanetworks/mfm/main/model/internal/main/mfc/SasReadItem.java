package com.felicanetworks.mfm.main.model.internal.main.mfc;

/* JADX INFO: loaded from: classes.dex */
public class SasReadItem {
    public final String blockName;
    public final String cpidSid;

    SasReadItem(String str, String str2) {
        this.cpidSid = str;
        this.blockName = str2;
    }

    public String toString() {
        return "SasReadItem[" + this.cpidSid + ", " + this.blockName + "]";
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SasReadItem)) {
            return false;
        }
        SasReadItem sasReadItem = (SasReadItem) obj;
        return sasReadItem.cpidSid.equals(this.cpidSid) && sasReadItem.blockName.equals(this.blockName);
    }

    public int hashCode() {
        return (this.cpidSid + this.blockName).hashCode();
    }
}
