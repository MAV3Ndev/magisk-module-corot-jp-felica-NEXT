package com.felicanetworks.mfw.i.fbl;

import com.felicanetworks.mfc.felica.access_control.AccessController;
import com.felicanetworks.mfc.felica.access_control.AccessControllerException;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.mfw.i.cmn.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class AccessRightChecker implements AccessController {
    private static final int CATEGORY_OFFLINE_ACCESS_ATTRIBUTE = 1;
    private static final int CATEGORY_ONLINE_ACCESS_ATTRIBUTE = 2;
    private static final int CATEGORY_PRIVILEGED_ACCESS_1_ATTRIBUTE = 1;
    private static final int CATEGORY_PRIVILEGED_ACCESS_2_ATTRIBUTE = 2;
    private static final int CATEGORY_RW_ACCESS_ATTRIBUTE = 4;
    private static final long MAX_VALUE_NODE_CODE = 4294967295L;
    private static final int WILD_CARD_SYSTEM_CODE = 65535;
    private static ArrayList<OfflineAccessTarget> sOfflineAccessTargetList = new ArrayList<>();
    private Permit mPermit = null;

    @Override // com.felicanetworks.mfc.felica.access_control.AccessController
    public void check(int i) throws AccessControllerException, NumberFormatException {
        LogMgr.log(4, "%s In type = %s : mPermit = %s", "000", Integer.valueOf(i), this.mPermit);
        Permit permit = this.mPermit;
        if (permit == null) {
            LogMgr.log(1, "%s mPermit data is null.", "800");
            throw new AccessControllerException(1);
        }
        FeliCaAccessData feliCaAccessData = (FeliCaAccessData) permit.getExtension(ExtensionParameter.FELICA_ACCESS);
        if (i == 1) {
            LogMgr.log(7, "%s", "001");
            if ((Integer.parseInt(StringUtil.toHexString(feliCaAccessData.getOrdinaryCommandCategory()), 16) & 1) != 1) {
                LogMgr.log(1, "%s category != CATEGORY_OFFLINE_ACCESS_ATTRIBUTE", "801");
                throw new AccessControllerException(1);
            }
        } else if (i == 2) {
            LogMgr.log(7, "%s", "002");
            if ((Integer.parseInt(StringUtil.toHexString(feliCaAccessData.getOrdinaryCommandCategory()), 16) & 2) != 2) {
                LogMgr.log(1, "%s category != CATEGORY_ONLINE_ACCESS_ATTRIBUTE", "802");
                throw new AccessControllerException(1);
            }
        } else if (i == 3) {
            LogMgr.log(7, "%s", "003");
            if ((Integer.parseInt(StringUtil.toHexString(feliCaAccessData.getOrdinaryCommandCategory()), 16) & 4) != 4) {
                LogMgr.log(1, "%s category != CATEGORY_RW_ACCESS_ATTRIBUTE", "803");
                throw new AccessControllerException(1);
            }
        } else if (i == 4) {
            LogMgr.log(7, "%s", "004");
            if ((Integer.parseInt(StringUtil.toHexString(feliCaAccessData.getPrivilegedCommandCategory()), 16) & 1) != 1) {
                LogMgr.log(1, "%s category != CATEGORY_PRIVILEGED_ACCESS_1_ATTRIBUTE", "804");
                throw new AccessControllerException(1);
            }
        } else if (i == 5) {
            LogMgr.log(7, "%s", "005");
            if ((Integer.parseInt(StringUtil.toHexString(feliCaAccessData.getPrivilegedCommandCategory()), 16) & 2) != 2) {
                LogMgr.log(1, "%s category != CATEGORY_PRIVILEGED_ACCESS_1_ATTRIBUTE", "805");
                throw new AccessControllerException(1);
            }
        } else {
            LogMgr.log(1, "%s type is unknow", "806");
            throw new AccessControllerException(1);
        }
        LogMgr.log(4, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.felica.access_control.AccessController
    public void checkSystemCode(int i) throws AccessControllerException, NumberFormatException {
        LogMgr.log(4, "%s In systemCode = %s : mPermit = %s", "000", Integer.valueOf(i), this.mPermit);
        FeliCaAccessData feliCaAccessData = (FeliCaAccessData) this.mPermit.getExtension(ExtensionParameter.FELICA_ACCESS);
        int iOfflineAccessTargetSize = feliCaAccessData.offlineAccessTargetSize();
        for (int i2 = 0; i2 < iOfflineAccessTargetSize; i2++) {
            int i3 = Integer.parseInt(feliCaAccessData.getOfflineAccessTarget(i2).getSystemCode(), 16);
            if (i3 == 65535 || i3 == i) {
                LogMgr.log(4, "%s", "999");
                return;
            }
        }
        LogMgr.log(1, "%s ", "800");
        throw new AccessControllerException(2);
    }

    @Override // com.felicanetworks.mfc.felica.access_control.AccessController
    public void checkNodeCodeList(int i, int[] iArr) throws AccessControllerException, NumberFormatException {
        int i2;
        int i3;
        int i4;
        LogMgr.log(4, "%s In currentSystemCode = %s : nodeCodeList = %s : mPermit = %s", "000", Integer.valueOf(i), iArr, this.mPermit);
        FeliCaAccessData feliCaAccessData = (FeliCaAccessData) this.mPermit.getExtension(ExtensionParameter.FELICA_ACCESS);
        int iOfflineAccessTargetSize = feliCaAccessData.offlineAccessTargetSize();
        sOfflineAccessTargetList.clear();
        int i5 = 0;
        while (true) {
            i2 = 7;
            if (i5 >= iOfflineAccessTargetSize) {
                break;
            }
            OfflineAccessTarget offlineAccessTarget = feliCaAccessData.getOfflineAccessTarget(i5);
            int i6 = Integer.parseInt(offlineAccessTarget.getSystemCode(), 16);
            if (i == i6 || 65535 == i6) {
                LogMgr.log(7, "%s", "001");
                sOfflineAccessTargetList.add(offlineAccessTarget);
            }
            i5++;
        }
        if (sOfflineAccessTargetList.size() == 0) {
            LogMgr.log(1, "%s ", "800");
            throw new AccessControllerException(2);
        }
        int length = iArr.length;
        int i7 = 0;
        while (i7 < length) {
            int i8 = iArr[i7];
            Iterator<OfflineAccessTarget> it = sOfflineAccessTargetList.iterator();
            int i9 = 0;
            while (true) {
                if (!it.hasNext()) {
                    i3 = i2;
                    i4 = 1;
                    break;
                }
                OfflineAccessTarget next = it.next();
                int i10 = 0;
                while (true) {
                    if (i10 >= next.nodeCodeRangeSize()) {
                        i3 = i2;
                        i4 = 1;
                        break;
                    }
                    long j = Long.parseLong(next.getNodeCodeRange(i10).getLowerNode(), 16);
                    long j2 = Long.parseLong(next.getNodeCodeRange(i10).getUpperNode(), 16);
                    long j3 = ((long) i8) & MAX_VALUE_NODE_CODE;
                    if (j <= j3 && j3 <= j2) {
                        i3 = 7;
                        LogMgr.log(7, "%s", "002");
                        i4 = 1;
                        i9 = 1;
                        break;
                    }
                    i10++;
                    i2 = 7;
                }
                if (i9 == i4) {
                    LogMgr.log(i3, "%s", "003");
                    break;
                }
                i2 = i3;
            }
            if (i9 == 0) {
                LogMgr.log(i4, "%s ", "801");
                throw new AccessControllerException(0);
            }
            i7++;
            i2 = i3;
        }
        LogMgr.log(4, "%s", "999");
    }

    public void setUp(Permit permit) {
        this.mPermit = permit;
    }
}
