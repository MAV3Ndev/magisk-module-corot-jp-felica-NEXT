package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.main.mfc.SasDefinition;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SasReader extends FelicaReader {
    SasReader() {
        super(new BlockList());
        int i = -1;
        int i2 = -1;
        for (int i3 = 0; i3 < SasDefinition.READING_INFO.size(); i3++) {
            SasDefinition.ReadingInfo readingInfo = SasDefinition.READING_INFO.get(i3);
            if (i != readingInfo.nodeCode || i2 != readingInfo.blockNumber) {
                this.blocklist.add(new Block(readingInfo.nodeCode, readingInfo.blockNumber));
                i = readingInfo.nodeCode;
                i2 = readingInfo.blockNumber;
            }
        }
    }

    public List<SasReadItem> getSasReadResultList() {
        ArrayList arrayList = new ArrayList(0);
        if (!isDataExist()) {
            return arrayList;
        }
        int i = -1;
        byte[] byteData = null;
        int i2 = -1;
        int i3 = 0;
        boolean z = false;
        for (int i4 = 0; i4 < SasDefinition.READING_INFO.size(); i4++) {
            SasDefinition.ReadingInfo readingInfo = SasDefinition.READING_INFO.get(i4);
            if (i != readingInfo.nodeCode || i2 != readingInfo.blockNumber) {
                try {
                    byteData = getByteData(this.data[i3]);
                    z = true;
                } catch (FelicaReaderException unused) {
                    z = false;
                }
                i = readingInfo.nodeCode;
                i2 = readingInfo.blockNumber;
                i3++;
            }
            if (z) {
                SasReadItem sasReadItem = new SasReadItem(CommonUtil.binToHexString(getTargetByte(byteData, readingInfo.startPosition, readingInfo.readingLength)), String.valueOf(readingInfo.blockName));
                if (!arrayList.contains(sasReadItem)) {
                    arrayList.add(sasReadItem);
                }
            }
        }
        return arrayList;
    }
}
