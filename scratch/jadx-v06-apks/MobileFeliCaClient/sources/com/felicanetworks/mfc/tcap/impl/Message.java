package com.felicanetworks.mfc.tcap.impl;

import com.felicanetworks.mfc.FelicaConst;
import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class Message {
    public static final int DEVICE_ID_NULL = 0;
    public static final byte EXT_FELICA = 1;
    public static final byte EXT_STANDARD = 0;
    protected static final int HEADER_LENGTH = 6;
    public static final byte MT_ACCEPT = -127;
    public static final byte MT_CLIENT_GOOD_BYE = 33;
    public static final byte MT_CLIENT_GOOD_BYE_DONE = 34;
    public static final byte MT_CLIENT_HELLO = 33;
    public static final byte MT_CLIENT_HELLO_DONE = 34;
    public static final byte MT_CLOSE_RW_REQUEST = 5;
    public static final byte MT_CLOSE_RW_STATUS = 6;
    public static final byte MT_DEVICES = 37;
    public static final byte MT_DEVICE_RESPONSE = 38;
    public static final byte MT_FEATURES = 38;
    public static final byte MT_FELICA_COMMAND = 1;
    public static final byte MT_FELICA_COMMAND_THRURW = 6;
    public static final byte MT_FELICA_ERROR = 3;
    public static final byte MT_FELICA_ERROR_THRURW = 8;
    public static final byte MT_FELICA_EXCOMMAND = 5;
    public static final byte MT_FELICA_EXCOMMAND_THRURW = 10;
    public static final byte MT_FELICA_PRECOMMAND = 4;
    public static final byte MT_FELICA_PRECOMMAND_THRURW = 9;
    public static final byte MT_FELICA_RESPONSE = 2;
    public static final byte MT_FELICA_RESPONSE_THRURW = 7;
    public static final byte MT_FINISHED = 0;
    public static final byte MT_ILLEGAL_STATE_ERROR = 34;
    public static final byte MT_OPEN_RW_REQUEST = 1;
    public static final byte MT_OPEN_RW_STATUS = 2;
    public static final byte MT_OPERATE_DEVICE = 37;
    public static final byte MT_PACKET_FORMAT_ERROR = 33;
    public static final byte MT_PLAY_SOUND = -127;
    public static final byte MT_REQUEST_ID = 48;
    public static final byte MT_RETURN_CODE = 37;
    public static final byte MT_SELECTED_FELICA_DEVICE = 3;
    public static final byte MT_SELECT_INTERNAL_FELICA = 1;
    public static final byte MT_SERVER_GOOD_BYE = 35;
    public static final byte MT_SERVER_GOOD_BYE_DONE = 36;
    public static final byte MT_SERVER_HELLO = 35;
    public static final byte MT_SERVER_HELLO_DONE = 36;
    public static final byte MT_SET_FELICA_TIMEOUT = -127;
    public static final byte MT_SET_NETWORK_TIMEOUT = -127;
    public static final byte MT_SET_RETRY_COUNT = -126;
    public static final byte MT_UNEXPECTED_ERROR = 35;
    public static final byte MT_WARNING = 1;
    protected static final int OFFSET_TO_DATA = 6;
    protected static final int OFFSET_TO_DEVICE_ID_HIGH = 1;
    protected static final int OFFSET_TO_DEVICE_ID_LOW = 2;
    protected static final int OFFSET_TO_EXTENSION = 0;
    protected static final int OFFSET_TO_LENGTH_HIGH = 4;
    protected static final int OFFSET_TO_LENGTH_LOW = 5;
    protected static final int OFFSET_TO_MESSAGE_TYPE = 3;
    protected byte[] mData;
    protected int mDeviceId;
    protected byte mExtension;
    protected int mLength;
    protected byte mMessageType;
    protected int mOffset;
    protected int mSize;

    public boolean validateData() {
        return true;
    }

    public Message() {
    }

    public Message(Message message) {
        this.mData = message.mData;
        this.mOffset = message.mOffset;
        this.mSize = message.mSize;
        this.mExtension = message.mExtension;
        this.mDeviceId = message.mDeviceId;
        this.mMessageType = message.mMessageType;
        this.mLength = message.mLength;
    }

    public Message(byte[] bArr, int i, int i2) throws PacketFormatErrorException {
        if (i2 < 6) {
            throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_PACKET_FORMAT_ERROR, null);
        }
        this.mExtension = bArr[i + 0];
        this.mDeviceId = ((bArr[i + 1] & 255) << 8) | (bArr[i + 2] & 255);
        this.mMessageType = bArr[i + 3];
        this.mLength = ((bArr[i + 4] & 255) << 8) | (bArr[i + 5] & 255);
        int i3 = this.mLength;
        if (i2 < i3 + 6) {
            throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_PACKET_FORMAT_ERROR, null);
        }
        this.mData = bArr;
        this.mOffset = i;
        this.mSize = i3 + 6;
    }

    public Message(byte b, int i, byte b2) {
        init(b, i, b2, 0);
    }

    protected void init(byte b, int i, byte b2, int i2) {
        this.mLength = i2 & 65535;
        int i3 = this.mLength;
        this.mSize = i3 + 6;
        this.mExtension = b;
        this.mDeviceId = i;
        this.mMessageType = b2;
        this.mData = new byte[i3 + 6];
        byte[] bArr = this.mData;
        bArr[0] = this.mExtension;
        int i4 = this.mDeviceId;
        bArr[1] = (byte) ((i4 & FelicaConst.WILD_CARD_SYSTEM_CODE3) >> 8);
        bArr[2] = (byte) (i4 & 255);
        bArr[3] = this.mMessageType;
        bArr[4] = (byte) ((i3 & FelicaConst.WILD_CARD_SYSTEM_CODE3) >> 8);
        bArr[5] = (byte) (i3 & 255);
    }

    public boolean validateFormat() {
        return this.mLength == 0;
    }

    public byte getExtension() {
        return this.mExtension;
    }

    public int getDeviceId() {
        return this.mDeviceId;
    }

    public byte getType() {
        return this.mMessageType;
    }

    public int getId() {
        return (this.mMessageType & 255) | ((this.mExtension & 255) << 8);
    }

    public int getLength() {
        return this.mLength;
    }

    public int getSize() {
        return this.mSize;
    }

    public void getBytes(ByteBuffer byteBuffer) {
        byteBuffer.append(this.mData, this.mOffset, this.mSize);
    }
}
