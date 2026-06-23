package com.felicanetworks.mfw.i.cmn;

/* JADX INFO: loaded from: classes.dex */
public class Asn1Object {
    public static final byte BITSTRING = 3;
    public static final byte GENERALIZEDTIME = 24;
    public static final byte IA5STRING = 22;
    public static final byte INTEGER = 2;
    public static final byte NULL = 5;
    public static final byte OCTETSTRING = 4;
    public static final byte OID = 6;
    public static final byte ROOT = 0;
    public static final byte SEQUENCE = 48;
    private byte mTagType;
    private int mLengthOfLengthField = 0;
    private byte[] mTagAndLengthData = null;
    private byte[] mData = null;
    private Asn1Object[] mChildren = null;

    public Asn1Object(byte b) {
        this.mTagType = (byte) 0;
        this.mTagType = b;
    }

    public byte getTagType() {
        return this.mTagType;
    }

    public void setTagType(byte b) {
        this.mTagType = b;
    }

    public int getLengthOfLengthField() {
        return this.mLengthOfLengthField;
    }

    public void setLengthOfLengthField(int i) {
        this.mLengthOfLengthField = i;
    }

    public byte[] getTagAndLengthData() {
        return this.mTagAndLengthData;
    }

    public void setTagAndLengthData(byte[] bArr) {
        this.mTagAndLengthData = bArr;
    }

    public byte[] getData() {
        return this.mData;
    }

    public void setData(byte[] bArr) {
        this.mData = bArr;
    }

    public Asn1Object[] getChildren() {
        return this.mChildren;
    }

    public void setChildren(Asn1Object[] asn1ObjectArr) {
        this.mChildren = asn1ObjectArr;
    }
}
