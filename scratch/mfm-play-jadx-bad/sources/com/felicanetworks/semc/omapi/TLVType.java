package com.felicanetworks.semc.omapi;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
class TLVType {
    private static final byte CONSTRUCTED_ENCODING_TAG_IDENTIFIER = 32;
    private static final byte MORE_TAG_IDENTIFIER = -128;
    private static final byte TWO_OCTET_TAG_IDENTIFIER = 31;
    private List<Byte> mType;

    TLVType(List<Byte> list) throws IllegalArgumentException {
        LogMgr.log(7, "000");
        ArrayList arrayList = new ArrayList();
        this.mType = arrayList;
        try {
            arrayList.add(list.get(0));
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
                if (i == size) {
                    throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
                }
            }
            LogMgr.log(7, "999");
        } catch (Exception e) {
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint(e));
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

    public boolean equals(Object obj) {
        LogMgr.log(7, "000");
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TLVType)) {
            return false;
        }
        LogMgr.log(7, "999");
        return Objects.equals(this.mType, ((TLVType) obj).mType);
    }

    public int hashCode() {
        List<Byte> list = this.mType;
        if (list != null) {
            return list.hashCode();
        }
        return 0;
    }
}
