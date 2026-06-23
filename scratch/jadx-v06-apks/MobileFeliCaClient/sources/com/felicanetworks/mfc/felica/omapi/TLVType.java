package com.felicanetworks.mfc.felica.omapi;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class TLVType {
    private static final byte CONSTRUCTED_ENCODING_TAG_IDENTIFIER = 32;
    private static final byte MORE_TAG_IDENTIFIER = -128;
    private static final byte TWO_OCTET_TAG_IDENTIFIER = 31;
    private List<Byte> mType = new ArrayList();

    TLVType(List<Byte> list) throws IllegalArgumentException {
        try {
            this.mType.add(list.get(0));
            if (isMultiByteType()) {
                int size = list.size();
                int i = 1;
                while (i < size) {
                    this.mType.add(list.get(i));
                    if ((list.get(i).byteValue() & (-128)) != -128) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (i != size) {
                } else {
                    throw new IllegalArgumentException();
                }
            }
        } catch (Exception unused) {
            throw new IllegalArgumentException();
        }
    }

    public final int size() {
        return this.mType.size();
    }

    private boolean isMultiByteType() throws IndexOutOfBoundsException {
        return (this.mType.get(0).byteValue() & TWO_OCTET_TAG_IDENTIFIER) == 31;
    }

    public final boolean isConstructed() throws IndexOutOfBoundsException {
        return (this.mType.get(0).byteValue() & CONSTRUCTED_ENCODING_TAG_IDENTIFIER) == 32;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TLVType)) {
            return false;
        }
        TLVType tLVType = (TLVType) obj;
        List<Byte> list = this.mType;
        return list != null ? list.equals(tLVType.mType) : tLVType.mType == null;
    }

    public int hashCode() {
        List<Byte> list = this.mType;
        if (list != null) {
            return list.hashCode();
        }
        return 0;
    }
}
