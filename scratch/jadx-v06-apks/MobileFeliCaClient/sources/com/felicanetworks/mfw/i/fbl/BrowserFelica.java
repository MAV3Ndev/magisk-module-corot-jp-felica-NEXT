package com.felicanetworks.mfw.i.fbl;

/* JADX INFO: loaded from: classes.dex */
public class BrowserFelica implements ExtensionParameter {
    private String mAllowUrl;

    @Override // com.felicanetworks.mfw.i.fbl.ExtensionParameter
    public String getId() {
        return ExtensionParameter.BROWSER_FELICA;
    }

    public String getAllowUrl() {
        return this.mAllowUrl;
    }

    public void setAllowUrl(String str) {
        this.mAllowUrl = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("BrowserFelica allowUrl = " + this.mAllowUrl);
        return stringBuffer.toString();
    }
}
