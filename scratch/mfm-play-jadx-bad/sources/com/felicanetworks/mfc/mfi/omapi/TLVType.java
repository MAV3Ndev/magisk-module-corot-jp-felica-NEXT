package com.felicanetworks.mfc.mfi.omapi;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
class TLVType {
    private static final byte CONSTRUCTED_ENCODING_TAG_IDENTIFIER = 32;
    private static final byte MORE_TAG_IDENTIFIER = -128;
    private static final byte TWO_OCTET_TAG_IDENTIFIER = 31;
    private List<Byte> mType;

    TLVType(final List<Byte> data) throws IllegalArgumentException {
        ArrayList arrayList = new ArrayList();
        this.mType = arrayList;
        try {
            arrayList.add(data.get(0));
            if (isMultiByteType()) {
                int size = data.size();
                int i = 1;
                while (i < size) {
                    this.mType.add(data.get(i));
                    if ((data.get(i).byteValue() & (-128)) != -128) {
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
        return (this.mType.get(0).byteValue() & 31) == 31;
    }

    public final boolean isConstructed() throws IndexOutOfBoundsException {
        return (this.mType.get(0).byteValue() & 32) == 32;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TLVType)) {
            return false;
        }
        List<Byte> list = this.mType;
        List<Byte> list2 = ((TLVType) o).mType;
        return list != null ? list.equals(list2) : list2 == null;
    }

    public int hashCode() {
        List<Byte> list = this.mType;
        if (list != null) {
            return list.hashCode();
        }
        return 0;
    }
}
