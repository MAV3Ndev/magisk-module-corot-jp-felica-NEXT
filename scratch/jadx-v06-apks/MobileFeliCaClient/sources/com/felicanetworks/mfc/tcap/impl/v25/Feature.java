package com.felicanetworks.mfc.tcap.impl.v25;

import com.felicanetworks.mfc.tcap.impl.TcapException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class Feature {
    private static final int MAX_OPTION_LENGTH = 255;
    private static final int MIN_OPTION_LENGTH = 1;
    private Vector<String> mOptions = new Vector<>();
    private int mVersion;

    public Feature(int i) {
        this.mVersion = i;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public int getOptionNum() {
        return this.mOptions.size();
    }

    public String getOption(int i) {
        return this.mOptions.elementAt(i);
    }

    public void addOption(String str) throws TcapException {
        try {
            ByteBuffer byteBufferEncode = StandardCharsets.ISO_8859_1.newEncoder().encode(CharBuffer.wrap(str));
            byte[] bArr = new byte[byteBufferEncode.limit()];
            byteBufferEncode.get(bArr);
            for (int i = 0; i < bArr.length; i++) {
                if (bArr[i] < 32 || bArr[i] > 126) {
                    throw new Exception();
                }
            }
            if (bArr.length < 1 || bArr.length > 255) {
                throw new Exception();
            }
            this.mOptions.addElement(str);
        } catch (Exception unused) {
            throw new TcapException("Invalid option.");
        }
    }
}
