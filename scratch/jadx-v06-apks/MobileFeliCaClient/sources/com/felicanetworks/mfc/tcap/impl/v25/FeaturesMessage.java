package com.felicanetworks.mfc.tcap.impl.v25;

import com.felicanetworks.mfc.FelicaConst;
import com.felicanetworks.mfc.tcap.impl.Message;
import com.felicanetworks.mfc.tcap.impl.TcapException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes.dex */
class FeaturesMessage extends Message {
    FeaturesMessage(Feature feature) throws TcapException {
        CharsetEncoder charsetEncoderNewEncoder = StandardCharsets.ISO_8859_1.newEncoder();
        int length = 3;
        for (int i = 0; i < feature.getOptionNum(); i++) {
            try {
                ByteBuffer byteBufferEncode = charsetEncoderNewEncoder.encode(CharBuffer.wrap(feature.getOption(i)));
                byte[] bArr = new byte[byteBufferEncode.limit()];
                byteBufferEncode.get(bArr);
                length += (bArr.length & 255) + 1;
            } catch (Exception unused) {
                throw new TcapException("Invalid option(feature).");
            }
        }
        init((byte) 0, 0, (byte) 38, length);
        this.mData[6] = (byte) ((feature.getVersion() & FelicaConst.WILD_CARD_SYSTEM_CODE3) >> 8);
        this.mData[7] = (byte) (feature.getVersion() & 255);
        this.mData[8] = (byte) (feature.getOptionNum() & 255);
        int i2 = 9;
        for (int i3 = 0; i3 < feature.getOptionNum(); i3++) {
            ByteBuffer byteBufferEncode2 = charsetEncoderNewEncoder.encode(CharBuffer.wrap(feature.getOption(i3)));
            byte[] bArr2 = new byte[byteBufferEncode2.limit()];
            byteBufferEncode2.get(bArr2);
            int length2 = bArr2.length & 255;
            this.mData[i2] = (byte) length2;
            int i4 = i2 + 1;
            System.arraycopy(bArr2, 0, this.mData, i4, length2);
            i2 = i4 + length2;
        }
    }
}
