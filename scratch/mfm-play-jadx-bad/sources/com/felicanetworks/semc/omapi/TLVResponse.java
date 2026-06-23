package com.felicanetworks.semc.omapi;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
class TLVResponse {
    private List<TLVData> mTLVDataList;

    TLVResponse(List<Byte> list) throws IllegalArgumentException {
        LogMgr.log(7, "000");
        this.mTLVDataList = new ArrayList();
        if (list != null) {
            int size = list.size();
            int data = 0;
            while (data < size) {
                data += parseData(null, list.subList(data, size));
            }
            if (data != size) {
                throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
            }
        }
        LogMgr.log(7, "999");
    }

    private int parseData(TLVData tLVData, List<Byte> list) throws IllegalArgumentException {
        int i;
        LogMgr.log(7, "000");
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
                throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
            }
            iNextTLVEntryOffset = data;
        }
        if (tLVData == null) {
            this.mTLVDataList.add(tLVData2);
        } else {
            tLVData.addChild(tLVData2);
        }
        LogMgr.log(7, "999");
        return iNextTLVEntryOffset;
    }

    public byte[] getValue(byte... bArr) throws IllegalArgumentException {
        return getValue(this.mTLVDataList, toByteList(bArr));
    }

    private byte[] getValue(List<TLVData> list, List<Byte> list2) throws IllegalArgumentException {
        LogMgr.log(7, "000");
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
            LogMgr.log(7, "999");
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    public List<byte[]> getValueList(byte... bArr) throws IllegalArgumentException {
        return getValueList(this.mTLVDataList, toByteList(bArr));
    }

    private List<byte[]> getValueList(List<TLVData> list, List<Byte> list2) throws IllegalArgumentException {
        LogMgr.log(7, "000");
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
            LogMgr.log(7, "999");
            return arrayList;
        } catch (Exception e) {
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    protected static List<Byte> toByteList(byte[] bArr) {
        LogMgr.log(7, "000");
        if (bArr == null) {
            LogMgr.log(7, "998");
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (byte b : bArr) {
            arrayList.add(Byte.valueOf(b));
        }
        LogMgr.log(7, "999");
        return arrayList;
    }

    private static byte[] toPrimitive(List<Byte> list) {
        LogMgr.log(7, "000");
        if (list == null) {
            LogMgr.log(7, "998");
            return null;
        }
        byte[] bArr = new byte[list.size()];
        Iterator<Byte> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            bArr[i] = it.next().byteValue();
            i++;
        }
        LogMgr.log(7, "999");
        return bArr;
    }
}
