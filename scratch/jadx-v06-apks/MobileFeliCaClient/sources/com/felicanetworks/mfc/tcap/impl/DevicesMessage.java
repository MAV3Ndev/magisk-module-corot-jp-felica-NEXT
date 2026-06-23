package com.felicanetworks.mfc.tcap.impl;

import com.felicanetworks.mfc.FelicaConst;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes.dex */
public class DevicesMessage extends Message {
    public DevicesMessage(InternalDeviceList internalDeviceList) throws TcapException {
        CharsetEncoder charsetEncoderNewEncoder = StandardCharsets.ISO_8859_1.newEncoder();
        int length = 0;
        for (int i = 0; i < internalDeviceList.size(); i++) {
            try {
                DeviceWrapper deviceWrapper = internalDeviceList.get(i);
                ByteBuffer byteBufferEncode = charsetEncoderNewEncoder.encode(CharBuffer.wrap(deviceWrapper.getType()));
                byte[] bArr = new byte[byteBufferEncode.limit()];
                byteBufferEncode.get(bArr);
                ByteBuffer byteBufferEncode2 = charsetEncoderNewEncoder.encode(CharBuffer.wrap(deviceWrapper.getName()));
                byte[] bArr2 = new byte[byteBufferEncode2.limit()];
                byteBufferEncode2.get(bArr2);
                length += (bArr.length & 255) + 5 + 1 + (65535 & bArr2.length);
            } catch (Exception unused) {
                throw new TcapException("Unsupported device name/type.");
            }
        }
        init((byte) 0, 0, (byte) 37, length);
        int i2 = 6;
        for (int i3 = 0; i3 < internalDeviceList.size(); i3++) {
            DeviceWrapper deviceWrapper2 = internalDeviceList.get(i3);
            ByteBuffer byteBufferEncode3 = charsetEncoderNewEncoder.encode(CharBuffer.wrap(deviceWrapper2.getType()));
            byte[] bArr3 = new byte[byteBufferEncode3.limit()];
            byteBufferEncode3.get(bArr3);
            ByteBuffer byteBufferEncode4 = charsetEncoderNewEncoder.encode(CharBuffer.wrap(deviceWrapper2.getName()));
            byte[] bArr4 = new byte[byteBufferEncode4.limit()];
            byteBufferEncode4.get(bArr4);
            int length2 = bArr3.length & 255;
            int length3 = bArr4.length & 65535;
            this.mData[i2] = (byte) ((deviceWrapper2.getId() & FelicaConst.WILD_CARD_SYSTEM_CODE3) >> 8);
            this.mData[i2 + 1] = (byte) (deviceWrapper2.getId() & 255);
            int i4 = i2 + 2;
            this.mData[i4] = 0;
            this.mData[i4 + 1] = 0;
            int i5 = i4 + 2;
            this.mData[i5] = (byte) length2;
            int i6 = i5 + 1;
            System.arraycopy(bArr3, 0, this.mData, i6, length2);
            int i7 = i6 + length2;
            this.mData[i7] = (byte) length3;
            int i8 = i7 + 1;
            System.arraycopy(bArr4, 0, this.mData, i8, length3);
            i2 = i8 + length3;
        }
    }
}
