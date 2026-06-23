package com.felicanetworks.mfc.tcap.impl;

import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
public class TcapException extends Exception {
    private static final String DESCRPTION_CHARSET = "ISO-8859-1";
    public static final byte TYPE_PROTOCOL = 0;
    private static final long serialVersionUID = 5476166977813930960L;
    private byte mType;

    public TcapException() {
    }

    public TcapException(String str) {
        super(str);
        this.mType = (byte) 0;
    }

    public TcapException(byte b, String str) {
        super(str);
        this.mType = b;
    }

    public byte getType() {
        return this.mType;
    }

    static String formatErrorDescription(byte b, byte[] bArr) {
        int i = 2;
        byte[] bArr2 = new byte[bArr == null ? 2 : ((bArr.length + 1) * 2) + bArr.length];
        byte b2 = (byte) ((b & 240) >> 4);
        byte b3 = (byte) (b & 15);
        bArr2[0] = (byte) (b2 < 10 ? b2 + 48 : (b2 + PacketFormatErrorException.TYPE_UNSUPPORTED_SUBPROTOCOL) - 10);
        bArr2[1] = (byte) (b3 < 10 ? b3 + 48 : (b3 + PacketFormatErrorException.TYPE_UNSUPPORTED_SUBPROTOCOL) - 10);
        if (bArr != null) {
            for (int i2 = 0; i2 < bArr.length; i2++) {
                int i3 = i + 1;
                bArr2[i] = 32;
                byte b4 = (byte) ((bArr[i2] & 240) >> 4);
                byte b5 = (byte) (bArr[i2] & 15);
                int i4 = i3 + 1;
                bArr2[i3] = (byte) (b4 < 10 ? b4 + 48 : (b4 + PacketFormatErrorException.TYPE_UNSUPPORTED_SUBPROTOCOL) - 10);
                i = i4 + 1;
                bArr2[i4] = (byte) (b5 < 10 ? b5 + 48 : (b5 + PacketFormatErrorException.TYPE_UNSUPPORTED_SUBPROTOCOL) - 10);
            }
        }
        try {
            return new String(bArr2, DESCRPTION_CHARSET);
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }
}
