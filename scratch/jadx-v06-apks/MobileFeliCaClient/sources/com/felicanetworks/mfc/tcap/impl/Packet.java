package com.felicanetworks.mfc.tcap.impl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class Packet {
    protected static final int DEFAULT_MAJOR_VERSION = 2;
    protected static final int DEFAULT_MINOR_VERSION = 1;
    protected static final int HEADER_LENGTH = 5;
    protected static final int OFFSET_TO_LENGTH_HIGH = 3;
    protected static final int OFFSET_TO_LENGTH_LOW = 4;
    protected static final int OFFSET_TO_MAJOR_VERSION = 0;
    protected static final int OFFSET_TO_MINOR_VERSION = 1;
    protected static final int OFFSET_TO_SUB_PROTOCOL_TYPE = 2;
    public static final byte SPT_APPLICATION_DATA_TRANSFER = 4;
    public static final byte SPT_ERROR = 3;
    public static final byte SPT_FAREWELL = 2;
    public static final byte SPT_HANDSHAKE = 1;
    public static final byte SPT_OPERATE_ENTITY = 6;
    public static final byte SPT_UPDATE_ENTITY = 5;
    protected byte[] mData;
    protected int mLength;
    protected byte mMajorVersion;
    protected Vector<Message> mMessages;
    protected byte mMinorVersion;
    protected int mOffset;
    protected int mSize;
    protected byte mSubProtocolType;

    public Packet(Packet packet) {
        this.mData = packet.mData;
        this.mOffset = packet.mOffset;
        this.mSize = packet.mSize;
        this.mMajorVersion = packet.mMajorVersion;
        this.mMinorVersion = packet.mMinorVersion;
        this.mSubProtocolType = packet.mSubProtocolType;
        this.mLength = packet.mLength;
    }

    public Packet(byte[] bArr, int i, int i2) throws PacketFormatErrorException {
        this.mData = bArr;
        this.mOffset = i;
        if (i2 - i < 5) {
            throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_PACKET_FORMAT_ERROR, null);
        }
        this.mMajorVersion = bArr[i + 0];
        this.mMinorVersion = bArr[i + 1];
        this.mSubProtocolType = bArr[i + 2];
        this.mLength = ((bArr[i + 3] & 255) << 8) | (bArr[i + 4] & 255);
        int i3 = this.mLength;
        if (i3 < 0 || i2 < i3 + 5) {
            throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_PACKET_FORMAT_ERROR, null);
        }
        this.mSize = i3 + 5;
        int i4 = i + 5;
        while (true) {
            int i5 = this.mSize;
            if (i4 >= i + i5) {
                if (i4 != i + i5) {
                    throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_PACKET_FORMAT_ERROR, null);
                }
                return;
            } else {
                if ((i5 + i) - i4 < 6) {
                    throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_PACKET_FORMAT_ERROR, null);
                }
                i4 += (((bArr[i4 + 4] & 255) << 8) | (bArr[i4 + 5] & 255)) + 6;
            }
        }
    }

    public Packet(int i, byte b) {
        this.mMajorVersion = (byte) ((i >> 8) & 255);
        this.mMinorVersion = (byte) (i & 255);
        this.mSubProtocolType = b;
    }

    public void parseMessages(InternalDeviceList internalDeviceList) throws PacketFormatErrorException {
        throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_PACKET_FORMAT_ERROR, null);
    }

    public byte getMajorVersion() {
        return this.mMajorVersion;
    }

    public byte getMinorVersion() {
        return this.mMinorVersion;
    }

    public int getVersion() {
        return (this.mMinorVersion & 255) | (this.mMajorVersion << 8);
    }

    public byte getSubProtocolType() {
        return this.mSubProtocolType;
    }

    public int getLength() {
        if (this.mData != null) {
            return this.mLength;
        }
        if (this.mMessages == null) {
            return 0;
        }
        int size = 0;
        for (int i = 0; i < this.mMessages.size(); i++) {
            size += this.mMessages.elementAt(i).getSize();
        }
        return size;
    }

    public int getSize() {
        if (this.mData != null) {
            return this.mSize;
        }
        return getLength() + 5;
    }

    public int getMessageNum() {
        return this.mMessages.size();
    }

    public Message getMessage(int i) {
        return this.mMessages.elementAt(i);
    }

    public void addMessage(Message message) {
        if (this.mMessages == null) {
            this.mMessages = new Vector<>();
        }
        this.mMessages.addElement(message);
    }

    public void getBytes(ByteBuffer byteBuffer) {
        int length = getLength();
        byteBuffer.append(new byte[]{this.mMajorVersion, this.mMinorVersion, this.mSubProtocolType, (byte) ((65280 & length) >> 8), (byte) (length & 255)});
        if (this.mMessages != null) {
            for (int i = 0; i < this.mMessages.size(); i++) {
                this.mMessages.elementAt(i).getBytes(byteBuffer);
            }
        }
    }
}
