package com.amazonaws.mobileconnectors.pinpoint.internal.validate;

import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
public class EncodingValidator {
    private final String encoding;

    public EncodingValidator(String str) {
        this.encoding = str;
    }

    public void validate() {
        try {
            "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~".getBytes(this.encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(this.encoding + " encoding is not supported", e);
        }
    }
}
