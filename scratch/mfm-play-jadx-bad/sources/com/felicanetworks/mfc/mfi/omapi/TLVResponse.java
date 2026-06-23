package com.felicanetworks.mfc.mfi.omapi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
class TLVResponse {
    private List<TLVData> mTLVDataList;

    TLVResponse(final List<Byte> dataList) throws IllegalArgumentException {
        this.mTLVDataList = null;
        this.mTLVDataList = new ArrayList();
        if (dataList != null) {
            int size = dataList.size();
            int data = 0;
            while (data < size) {
                data += parseData(null, dataList.subList(data, size));
            }
            if (data != size) {
                throw new IllegalArgumentException();
            }
        }
    }

    private int parseData(final TLVData parentTLVData, final List<Byte> dataList) throws IllegalArgumentException {
        int i;
        TLVData tLVData = new TLVData(dataList);
        int iNextTLVEntryOffset = tLVData.nextTLVEntryOffset();
        if (tLVData.hasChild()) {
            int length = tLVData.getLength();
            int data = iNextTLVEntryOffset;
            while (true) {
                i = iNextTLVEntryOffset + length;
                if (data >= i) {
                    break;
                }
                data += parseData(tLVData, dataList.subList(data, i));
            }
            if (data != i) {
                throw new IllegalArgumentException();
            }
            iNextTLVEntryOffset = data;
        }
        if (parentTLVData == null) {
            this.mTLVDataList.add(tLVData);
            return iNextTLVEntryOffset;
        }
        parentTLVData.addChild(tLVData);
        return iNextTLVEntryOffset;
    }

    public byte[] getValue(final byte... typeList) throws IllegalArgumentException {
        return getValue(this.mTLVDataList, toByteList(typeList));
    }

    private byte[] getValue(final List<TLVData> tLVDataList, final List<Byte> typeList) throws IllegalArgumentException {
        try {
            TLVType tLVType = new TLVType(typeList);
            for (TLVData tLVData : tLVDataList) {
                if (tLVData.getType().equals(tLVType)) {
                    if (typeList.size() == tLVData.getType().size()) {
                        return toPrimitive(tLVData.getValue());
                    }
                    return getValue(tLVData.getChildDataList(), typeList.subList(tLVData.getType().size(), typeList.size()));
                }
            }
            return null;
        } catch (Exception unused) {
            throw new IllegalArgumentException();
        }
    }

    public List<byte[]> getValueList(final byte... typeList) throws IllegalArgumentException {
        return getValueList(this.mTLVDataList, toByteList(typeList));
    }

    private List<byte[]> getValueList(final List<TLVData> tLVDataList, final List<Byte> typeList) throws IllegalArgumentException {
        try {
            ArrayList arrayList = new ArrayList();
            TLVType tLVType = new TLVType(typeList);
            for (TLVData tLVData : tLVDataList) {
                if (tLVData.getType().equals(tLVType)) {
                    if (typeList.size() == tLVData.getType().size()) {
                        arrayList.add(toPrimitive(tLVData.getValue()));
                    } else {
                        return getValueList(tLVData.getChildDataList(), typeList.subList(tLVData.getType().size(), typeList.size()));
                    }
                }
            }
            return arrayList;
        } catch (Exception unused) {
            throw new IllegalArgumentException();
        }
    }

    protected static List<Byte> toByteList(final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (byte b : bytes) {
            arrayList.add(Byte.valueOf(b));
        }
        return arrayList;
    }

    private static byte[] toPrimitive(final List<Byte> list) {
        if (list == null) {
            return null;
        }
        byte[] bArr = new byte[list.size()];
        Iterator<Byte> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            bArr[i] = it.next().byteValue();
            i++;
        }
        return bArr;
    }
}
