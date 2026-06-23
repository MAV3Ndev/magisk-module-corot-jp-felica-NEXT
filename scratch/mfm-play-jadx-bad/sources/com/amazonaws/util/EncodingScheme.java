package com.amazonaws.util;

/* JADX INFO: loaded from: classes3.dex */
public interface EncodingScheme {
    byte[] decode(String str);

    String encodeAsString(byte[] bArr);
}
