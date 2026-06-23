package com.felicanetworks.mfc.mfi.omapi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class TLVResponse {
    private List<TLVData> mTLVDataList;

    TLVResponse(List<Byte> list) throws IllegalArgumentException {
        this.mTLVDataList = null;
        this.mTLVDataList = new ArrayList();
        if (list != null) {
            int size = list.size();
            int data = 0;
            while (data < size) {
                data += parseData(null, list.subList(data, size));
            }
            if (data != size) {
                throw new IllegalArgumentException();
            }
        }
    }

    private int parseData(TLVData tLVData, List<Byte> list) throws IllegalArgumentException {
        int i;
        TLVData tLVData2 = new TLVData(list);
        int iNextTLVEntryOffset = tLVData2.nextTLVEntryOffset();
        if (tLVData2.hasChild()) {
            int length = tLVData2.getLength();
            int data = iNextTLVEntryOffset;
            while (true) {
                i = iNextTLVEntryOffset + length;
                if (data >= i) {
                    break;
                }
                data += parseData(tLVData2, list.subList(data, i));
            }
            if (data != i) {
                throw new IllegalArgumentException();
            }
            iNextTLVEntryOffset = data;
        }
        if (tLVData == null) {
            this.mTLVDataList.add(tLVData2);
        } else {
            tLVData.addChild(tLVData2);
        }
        return iNextTLVEntryOffset;
    }

    public byte[] getValue(byte... bArr) throws IllegalArgumentException {
        return getValue(this.mTLVDataList, toByteList(bArr));
    }

    private byte[] getValue(List<TLVData> list, List<Byte> list2) throws IllegalArgumentException {
        try {
            TLVType tLVType = new TLVType(list2);
            for (TLVData tLVData : list) {
                if (tLVData.getType().equals(tLVType)) {
                    if (list2.size() == tLVData.getType().size()) {
                        return toPrimitive(tLVData.getValue());
                    }
                    return getValue(tLVData.getChildDataList(), list2.subList(tLVData.getType().size(), list2.size()));
                }
            }
            return null;
        } catch (Exception unused) {
            throw new IllegalArgumentException();
        }
    }

    public List<byte[]> getValueList(byte... bArr) throws IllegalArgumentException {
        return getValueList(this.mTLVDataList, toByteList(bArr));
    }

    private List<byte[]> getValueList(List<TLVData> list, List<Byte> list2) throws IllegalArgumentException {
        try {
            ArrayList arrayList = new ArrayList();
            TLVType tLVType = new TLVType(list2);
            for (TLVData tLVData : list) {
                if (tLVData.getType().equals(tLVType)) {
                    if (list2.size() == tLVData.getType().size()) {
                        arrayList.add(toPrimitive(tLVData.getValue()));
                    } else {
                        return getValueList(tLVData.getChildDataList(), list2.subList(tLVData.getType().size(), list2.size()));
                    }
                }
            }
            return arrayList;
        } catch (Exception unused) {
            throw new IllegalArgumentException();
        }
    }

    protected static List<Byte> toByteList(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (byte b : bArr) {
            arrayList.add(Byte.valueOf(b));
        }
        return arrayList;
    }

    private static byte[] toPrimitive(List<Byte> list) {
        if (list == null) {
            return null;
        }
        byte[] bArr = new byte[list.size()];
        int i = 0;
        Iterator<Byte> it = list.iterator();
        while (it.hasNext()) {
            bArr[i] = it.next().byteValue();
            i++;
        }
        return bArr;
    }
}
