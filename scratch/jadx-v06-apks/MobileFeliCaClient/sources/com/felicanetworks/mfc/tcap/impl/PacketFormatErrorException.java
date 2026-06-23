package com.felicanetworks.mfc.tcap.impl;

/* JADX INFO: loaded from: classes.dex */
public class PacketFormatErrorException extends TcapException {
    private static final String MESSAGE = "Packet format error.";
    public static final byte TYPE_DATA_FORMAT_ERROR = 71;
    public static final byte TYPE_INVALID_DEVICE_ID = 67;
    public static final byte TYPE_MESSAGE_FORMAT_ERROR = 69;
    public static final byte TYPE_MESSAGE_ORDER_ERROR = 70;
    public static final byte TYPE_PACKET_FORMAT_ERROR = 66;
    public static final byte TYPE_UNSUPPORTED_MESSAGE = 68;
    public static final byte TYPE_UNSUPPORTED_SUBPROTOCOL = 65;
    public static final byte TYPE_UNSUPPORTED_VERSION = 64;
    private static final long serialVersionUID = 6920228102997441711L;
    private String mDescription;

    public PacketFormatErrorException(byte b, byte[] bArr) {
        super(b, MESSAGE);
        this.mDescription = formatErrorDescription(b, bArr);
    }

    public String getErrorDescription() {
        return this.mDescription;
    }
}
