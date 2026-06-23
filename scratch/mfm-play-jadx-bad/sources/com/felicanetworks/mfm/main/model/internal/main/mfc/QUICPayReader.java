package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfm.main.model.info.specific.MyQUICPayInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;

/* JADX INFO: loaded from: classes3.dex */
public class QUICPayReader extends FelicaReader {
    private static final String QUICPAY_ID_QP_LOCAL = "0300";
    private static final String QUICPAY_ID_QP_MFI = "0200";
    private static final int READ_INDEX_QUICPAY_ID = 0;
    public static final String SERVICE_ID = FeliCaParams.SERVICE_ID_QP;
    private static final int[][] READ_BALANCE_INFO_LIST = FeliCaParams.READ_QUICPAY_ID;

    QUICPayReader() {
        super(new BlockList());
        for (int[] iArr : READ_BALANCE_INFO_LIST) {
            this.blocklist.add(new Block(iArr[0], iArr[1]));
        }
    }

    MyQUICPayInfo.QPType getQPType() {
        if (!isDataExist()) {
            return MyQUICPayInfo.QPType.UNKNOWN;
        }
        try {
            byte[] bArr = new byte[2];
            System.arraycopy(getByteData(this.data[0]), 0, bArr, 0, 2);
            String strBinToHexString = CommonUtil.binToHexString(bArr);
            if (QUICPAY_ID_QP_MFI.equals(strBinToHexString)) {
                return MyQUICPayInfo.QPType.QP_MFI;
            }
            if (QUICPAY_ID_QP_LOCAL.equals(strBinToHexString)) {
                return MyQUICPayInfo.QPType.QP_LOCAL;
            }
            return MyQUICPayInfo.QPType.QP_MOBILE;
        } catch (FelicaReaderException unused) {
            return MyQUICPayInfo.QPType.UNKNOWN;
        }
    }
}
