package com.felicanetworks.mfc.mfi.mfw.i.fbl;

import com.felicanetworks.mfc.mfi.felica.access_control.AccessController;
import com.felicanetworks.mfc.mfi.felica.access_control.AccessControllerException;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public class AccessRightChecker implements AccessController {
    private static final long MAX_VALUE_NODE_CODE = 4294967295L;
    private static final int WILD_CARD_SYSTEM_CODE = 65535;
    private static ArrayList<OfflineAccessRange> sOfflineAccessTargetList = new ArrayList<>();
    private Permit mPermit = null;

    @Override // com.felicanetworks.mfc.mfi.felica.access_control.AccessController
    public void check(int i) throws AccessControllerException {
        LogMgr.log(4, "%s In type = %s : mPermit = %s", "000", Integer.valueOf(i), this.mPermit);
        Permit permit = this.mPermit;
        if (permit == null) {
            LogMgr.log(1, "%s mPermit data is null.", "800");
            throw new AccessControllerException(1);
        }
        if (i == 16) {
            LogMgr.log(7, "015");
            if (!this.mPermit.getMfiPermit().getFunctionCategory().isAccess()) {
                LogMgr.log(7, "815 category != CATEGORY_CARD_ACCESS");
                throw new AccessControllerException(1);
            }
        } else if (i != 32) {
            switch (i) {
                case 1:
                    LogMgr.log(7, "%s", "001");
                    if (!this.mPermit.getMfcPermit().getCommandCategory().isOffline()) {
                        LogMgr.log(1, "%s category != CATEGORY_OFFLINE_ACCESS_ATTRIBUTE", "801");
                        throw new AccessControllerException(1);
                    }
                    break;
                case 2:
                    LogMgr.log(7, "%s", "002");
                    if (!this.mPermit.getMfcPermit().getCommandCategory().isOnline()) {
                        LogMgr.log(1, "%s category != CATEGORY_ONLINE_ACCESS_ATTRIBUTE", "802");
                        throw new AccessControllerException(1);
                    }
                    break;
                case 3:
                    LogMgr.log(7, "%s", "003");
                    if (!this.mPermit.getMfcPermit().getCommandCategory().isReaderWriter()) {
                        LogMgr.log(1, "%s category != CATEGORY_RW_ACCESS_ATTRIBUTE", "803");
                        throw new AccessControllerException(1);
                    }
                    break;
                case 4:
                    LogMgr.log(7, "%s", "004");
                    if (!this.mPermit.getMfcPermit().getCommandCategory().isPrivilege1()) {
                        LogMgr.log(7, "%s category != CATEGORY_PRIVILEGED_ACCESS_1_ATTRIBUTE", "804");
                        throw new AccessControllerException(1);
                    }
                    break;
                case 5:
                    LogMgr.log(7, "%s", "005");
                    if (!this.mPermit.getMfcPermit().getCommandCategory().isPrivilege2()) {
                        LogMgr.log(7, "%s category != CATEGORY_PRIVILEGED_ACCESS_2_ATTRIBUTE", "805");
                        throw new AccessControllerException(1);
                    }
                    break;
                case 6:
                    LogMgr.log(7, "%s", "006");
                    if (!this.mPermit.getMfiPermit().getFunctionCategory().isGetInfo()) {
                        LogMgr.log(1, "%s category != CATEGORY_CARD_GET_INFO", "806");
                        throw new AccessControllerException(1);
                    }
                    break;
                case 7:
                    LogMgr.log(7, "%s", "008");
                    if (!this.mPermit.getMfiPermit().getFunctionCategory().isIssueDelete()) {
                        LogMgr.log(1, "%s category != CATEGORY_CARD_ISSUE_DELETE", "808");
                        throw new AccessControllerException(1);
                    }
                    break;
                case 8:
                    LogMgr.log(7, "%s", "007");
                    if (!this.mPermit.getMfiPermit().getFunctionCategory().isEnableDisable()) {
                        LogMgr.log(1, "%s category != CATEGORY_CARD_ENABLE_DISABLE", "807");
                        throw new AccessControllerException(1);
                    }
                    break;
                case 9:
                    LogMgr.log(7, "%s", "009");
                    if (!this.mPermit.getMfiPermit().getFunctionCategory().isClearAccount()) {
                        LogMgr.log(1, "%s category != CATEGORY_CLEAR_ACCOUNT", "809");
                        throw new AccessControllerException(1);
                    }
                    break;
                case 10:
                    LogMgr.log(7, "%s", "010");
                    if (!this.mPermit.getMfiPermit().getStart().isSkipAgreementPage()) {
                        LogMgr.log(7, "%s category != CATEGORY_SKIP_AGREEMENT_PAGE", "810");
                        throw new AccessControllerException(1);
                    }
                    break;
                case 11:
                    LogMgr.log(7, "%s", "011");
                    if (!this.mPermit.getMfiPermit().getGetCardList().isUnlimited()) {
                        LogMgr.log(7, "%s category != CATEGORY_GET_CARD_LIST_UNLIMITED", "811");
                        throw new AccessControllerException(1);
                    }
                    break;
                case 12:
                    LogMgr.log(7, "%s", "012");
                    if (!this.mPermit.getMfiPermit().getGetCardList().isAdditionalInfo1()) {
                        LogMgr.log(1, "%s category != CATEGORY_GET_CARD_LIST_ADDITIONAL_INFO_1", "812");
                        throw new AccessControllerException(1);
                    }
                    break;
                case 13:
                    LogMgr.log(7, "013");
                    if (!this.mPermit.getMfiPermit().getFunctionCategory().isInitialize()) {
                        LogMgr.log(1, "809 category != CATEGORY_INITIALIZE");
                        throw new AccessControllerException(1);
                    }
                    break;
                case 14:
                    LogMgr.log(7, "014", "014");
                    if (!this.mPermit.getMfiPermit().getStart().isAdminStart()) {
                        LogMgr.log(7, "810 category != CATEGORY_ADMIN_START");
                        throw new AccessControllerException(1);
                    }
                    break;
                default:
                    switch (i) {
                        case 48:
                            LogMgr.log(7, "017");
                            if (!this.mPermit.getMfiPermit().getGetCardList().isSpSync()) {
                                LogMgr.log(7, "817 category != CATEGORY_GET_CARD_LIST_SP_SYNC");
                                throw new AccessControllerException(1);
                            }
                            break;
                        case 49:
                            LogMgr.log(7, "018");
                            if (!this.mPermit.getMfiPermit().getFunctionCategory().isUseUnsupportMfiService1Card()) {
                                LogMgr.log(7, "818 category != CATEGORY_USE_UNSUPPORT_MFI_SERVICE1_CARD");
                                throw new AccessControllerException(1);
                            }
                            break;
                        case 50:
                            LogMgr.log(7, "019");
                            if (!this.mPermit.getMfiPermit().getFunctionCategory().isPrivileged()) {
                                LogMgr.log(7, "819 category != CATEGORY_PRIVILEGE");
                                throw new AccessControllerException(1);
                            }
                            break;
                        case 51:
                            if (!permit.getMfiPermit().getFunctionCategory().isExistEmptySlot()) {
                                LogMgr.log(7, "819 category != CATEGORY_EXIST_EMPTY_SLOT");
                                throw new AccessControllerException(1);
                            }
                            break;
                        default:
                            LogMgr.log(1, "%s type is unknown", "813");
                            throw new AccessControllerException(1);
                    }
                    break;
            }
        } else {
            LogMgr.log(7, "016");
            if (!this.mPermit.getMfiPermit().getFunctionCategory().isIdentifyService()) {
                LogMgr.log(7, "816 category != CATEGORY_IDENTIFY_SERVICE");
                throw new AccessControllerException(1);
            }
        }
        LogMgr.log(4, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.mfi.felica.access_control.AccessController
    public void checkSystemCode(int i) throws AccessControllerException {
        LogMgr.log(4, "%s In systemCode = %s : mPermit = %s", "000", Integer.valueOf(i), this.mPermit);
        Iterator<OfflineAccessRange> it = this.mPermit.getMfcPermit().getOfflineAccessRangeList().iterator();
        while (it.hasNext()) {
            int systemCode = it.next().getSystemCode();
            if (systemCode == 65535 || systemCode == i) {
                LogMgr.log(4, "%s", "999");
                return;
            }
        }
        LogMgr.log(1, "%s ", "800");
        throw new AccessControllerException(2);
    }

    @Override // com.felicanetworks.mfc.mfi.felica.access_control.AccessController
    public void checkNodeCodeList(int i, int[] iArr) throws AccessControllerException {
        int[] iArr2 = iArr;
        LogMgr.log(4, "%s In currentSystemCode = %s : nodeCodeList = %s : mPermit = %s", "000", Integer.valueOf(i), iArr, this.mPermit);
        sOfflineAccessTargetList.clear();
        for (OfflineAccessRange offlineAccessRange : this.mPermit.getMfcPermit().getOfflineAccessRangeList()) {
            int systemCode = offlineAccessRange.getSystemCode();
            if (i == systemCode || 65535 == systemCode) {
                LogMgr.log(7, "%s", "001");
                sOfflineAccessTargetList.add(offlineAccessRange);
            }
        }
        if (sOfflineAccessTargetList.size() == 0) {
            LogMgr.log(1, "%s ", "800");
            throw new AccessControllerException(2);
        }
        int length = iArr2.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = iArr2[i2];
            Iterator<OfflineAccessRange> it = sOfflineAccessTargetList.iterator();
            boolean z = false;
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Iterator<NodeCodeRange> it2 = it.next().getNodeCodeRangeList().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    NodeCodeRange next = it2.next();
                    long lowerLimit = next.getLowerLimit();
                    long upperLimit = next.getUpperLimit();
                    long j = ((long) i3) & MAX_VALUE_NODE_CODE;
                    if (lowerLimit <= j && j <= upperLimit) {
                        LogMgr.log(7, "%s", "002");
                        z = true;
                        break;
                    }
                }
                if (z) {
                    LogMgr.log(7, "%s", "003");
                    break;
                }
            }
            if (!z) {
                LogMgr.log(1, "%s ", "801");
                throw new AccessControllerException(0);
            }
            i2++;
            iArr2 = iArr;
        }
        LogMgr.log(4, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.mfi.felica.access_control.AccessController
    public void checkMfiServiceId(String str) throws AccessControllerException {
        LogMgr.log(4, "000 : In serviceId = " + str + " : mPermit = " + this.mPermit);
        Permit permit = this.mPermit;
        if (permit == null) {
            LogMgr.log(1, "800 : mPermit data is null.");
            throw new AccessControllerException(3);
        }
        Iterator<OfflineAccessServiceId> it = permit.getMfiPermit().getOfflineAccessServiceIdList().iterator();
        while (it.hasNext()) {
            if (it.next().getServiceId().equals(str)) {
                LogMgr.log(4, "999");
                return;
            }
        }
        LogMgr.log(1, "800");
        throw new AccessControllerException(3);
    }

    @Override // com.felicanetworks.mfc.mfi.felica.access_control.AccessController
    public JSONArray getWalletAppCertHashList() {
        if (this.mPermit == null) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(this.mPermit.getWalletAppCallerInfo());
        return jSONArray;
    }

    @Override // com.felicanetworks.mfc.mfi.felica.access_control.AccessController
    public String getWalletAppCallerInfo() {
        Permit permit = this.mPermit;
        if (permit != null) {
            return permit.getWalletAppCallerInfo();
        }
        return null;
    }

    @Override // com.felicanetworks.mfc.mfi.felica.access_control.AccessController
    public String getWalletAppIdentifiableInfo() {
        Permit permit = this.mPermit;
        if (permit != null) {
            return permit.getWalletAppIdentifiableInfo();
        }
        return null;
    }

    @Override // com.felicanetworks.mfc.mfi.felica.access_control.AccessController
    public String getWalletAppId() {
        Permit permit = this.mPermit;
        if (permit != null) {
            return permit.getWalletAppId();
        }
        return null;
    }

    public void setUp(Permit permit) {
        this.mPermit = permit;
    }
}
