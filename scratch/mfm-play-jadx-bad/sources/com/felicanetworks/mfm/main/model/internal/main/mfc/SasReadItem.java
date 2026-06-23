package com.felicanetworks.mfm.main.model.internal.main.mfc;

/* JADX INFO: loaded from: classes3.dex */
public class SasReadItem {
    public final String blockName;
    public final String cpidSid;

    public SasReadItem(final String cpidSid, final String blockName) {
        this.cpidSid = cpidSid;
        this.blockName = blockName;
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
