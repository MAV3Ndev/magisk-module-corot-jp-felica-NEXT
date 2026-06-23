package com.felicanetworks.mfw.i.fbl;

/* JADX INFO: loaded from: classes.dex */
public class ChipIssuerInfo implements ExtensionParameter {
    private String mChipIssuerCode;

    @Override // com.felicanetworks.mfw.i.fbl.ExtensionParameter
    public String getId() {
        return "0001";
    }

    public String getChipIssuerCode() {
        return this.mChipIssuerCode;
    }

    public void setChipIssuerCode(String str) {
        this.mChipIssuerCode = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ChipIssuerInfo chipIssuerCode = " + this.mChipIssuerCode);
        return stringBuffer.toString();
    }
}
