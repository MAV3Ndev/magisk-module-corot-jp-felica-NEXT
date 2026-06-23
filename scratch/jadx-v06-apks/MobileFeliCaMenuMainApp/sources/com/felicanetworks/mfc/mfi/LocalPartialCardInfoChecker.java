package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.mfi.fws.FwsException;
import com.felicanetworks.mfc.mfi.fws.IndividualSpChecker;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class LocalPartialCardInfoChecker {
    private final AtomicBoolean mIsCanceled = new AtomicBoolean(false);

    public void setCancel(boolean z) {
        this.mIsCanceled.set(z);
    }

    private void checkCancel() throws FelicaException {
        LogMgr.log(4, "000");
        if (this.mIsCanceled.get()) {
            throw new MfiClientException(3, MfiClientException.TYPE_MFI_OFFLINE_CANCELED, null);
        }
        LogMgr.log(4, "999");
    }

    public LocalPartialCardInfoJson[] getLocalPartialCardInfoList(String str, MfiChipHolder mfiChipHolder) throws FelicaException, IllegalArgumentException {
        return getLocalPartialCardInfoList(str, false, mfiChipHolder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0298 A[Catch: all -> 0x0263, TryCatch #0 {all -> 0x0263, blocks: (B:7:0x0022, B:94:0x0268, B:105:0x0298, B:106:0x029c, B:63:0x0191, B:72:0x01a9, B:74:0x01b3, B:75:0x0235, B:77:0x0241, B:79:0x0249, B:78:0x0245, B:66:0x019a, B:68:0x01a0, B:108:0x02cc), top: B:117:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x00e7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0084 A[Catch: all -> 0x0119, InterruptedException -> 0x011c, GpException -> 0x0131, TryCatch #8 {InterruptedException -> 0x011c, blocks: (B:11:0x003e, B:13:0x005c, B:15:0x0068, B:17:0x006e, B:25:0x0084, B:26:0x00e7, B:27:0x00f4, B:29:0x0100, B:31:0x0108, B:30:0x0104), top: B:123:0x003e, outer: #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0275  */
    /* JADX WARN: Type inference failed for: r13v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r13v3, types: [com.felicanetworks.mfc.mfi.omapi.GpController] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mfc.mfi.LocalPartialCardInfoJson[] getLocalPartialCardInfoList(java.lang.String r18, boolean r19, com.felicanetworks.mfc.mfi.MfiChipHolder r20) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 794
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.LocalPartialCardInfoChecker.getLocalPartialCardInfoList(java.lang.String, boolean, com.felicanetworks.mfc.mfi.MfiChipHolder):com.felicanetworks.mfc.mfi.LocalPartialCardInfoJson[]");
    }

    private int judgeServiceProvider(MfiChipHolder mfiChipHolder) throws FelicaException {
        return judgeServiceProvider(mfiChipHolder, true);
    }

    private int judgeServiceProvider(MfiChipHolder mfiChipHolder, boolean z) throws FelicaException {
        try {
            return IndividualSpChecker.identifyService(mfiChipHolder, 3, 72, 74, FlavorConst.INDIVIDUAL_SP_NODE_CODE_SERVICE1, FlavorConst.INDIVIDUAL_SP_NODE_CODE_SERVICE2, false, z);
        } catch (FwsException e) {
            int i = 1;
            int i2 = MfiClientException.TYPE_IDENTIFY_SERVICE_FAILED;
            String message = e.getMessage();
            int type = e.getType();
            if (type == 6) {
                i = 3;
                i2 = 6;
            } else if (type == 8) {
                message = FelicaException.NFC_RW_USED_MESSAGE;
                i2 = 8;
            } else if (type == 31) {
                i = 8;
                i2 = 31;
            } else if (type == 55) {
                i = 8;
                i2 = 55;
            }
            LogMgr.log(2, "700 : " + e.getClass().getSimpleName() + " : " + e.getMessage());
            throw new MfiClientException(i, i2, message);
        }
    }
}
