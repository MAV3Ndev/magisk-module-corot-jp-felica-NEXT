package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.mfi.CardIdBlockInfo;
import com.felicanetworks.mfc.mfi.CardIdentifiableBlockData;
import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.felica.offlineimpl.DataUtil;
import com.felicanetworks.mfc.mfi.felica.util.ByteBuffer;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.AppletManager;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.omapi.SeAppletInfo;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.ServiceTypeInfoUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class CardIdentifiableInfoChecker {
    private static final Map<CheckPattern, CheckItem[]> CheckItem_MAP;
    private static final CheckItem[] PATTERN_A_CHECK_ITEM;
    private static final CheckItem[] PATTERN_B_CHECK_ITEM;
    private static final CheckItem[] PATTERN_C_CHECK_ITEM;
    private static final CheckItem[] PATTERN_D_CHECK_ITEM;
    private Map<String, Boolean> mIsActivatedByAIDMap = new HashMap();
    private int[] mSystemCodeList;

    private enum CheckItem {
        SYSTEM_CHECK,
        IDM_CHECK,
        AREA_CHECK,
        SERVICE_CHECK,
        NODE_CHECK,
        PERSONALIZED_CHECK,
        CID_CHECK,
        ID_INFO_CHECK
    }

    private enum CheckPattern {
        PATTERN_A,
        PATTERN_B,
        PATTERN_C,
        PATTERN_D,
        PATTERN_NONE
    }

    static {
        CheckItem[] checkItemArr = {CheckItem.IDM_CHECK, CheckItem.AREA_CHECK, CheckItem.SERVICE_CHECK, CheckItem.ID_INFO_CHECK};
        PATTERN_A_CHECK_ITEM = checkItemArr;
        CheckItem[] checkItemArr2 = {CheckItem.IDM_CHECK, CheckItem.AREA_CHECK};
        PATTERN_B_CHECK_ITEM = checkItemArr2;
        CheckItem[] checkItemArr3 = {CheckItem.IDM_CHECK, CheckItem.NODE_CHECK};
        PATTERN_C_CHECK_ITEM = checkItemArr3;
        CheckItem[] checkItemArr4 = {CheckItem.PERSONALIZED_CHECK, CheckItem.CID_CHECK};
        PATTERN_D_CHECK_ITEM = checkItemArr4;
        HashMap map = new HashMap();
        CheckItem_MAP = map;
        map.put(CheckPattern.PATTERN_A, checkItemArr);
        map.put(CheckPattern.PATTERN_B, checkItemArr2);
        map.put(CheckPattern.PATTERN_C, checkItemArr3);
        map.put(CheckPattern.PATTERN_D, checkItemArr4);
    }

    public Map<String, CardIdentifiableInfo> getRemainedCardCiaInfoByCidMap(MfiChipHolder chipHolder, Collection<CardIdentifiableInfo> ciaList) throws FwsException {
        LogMgr.log(4, "%s", "000");
        try {
            Map<String, CardIdentifiableInfo> remainedCardCiaInfoByCidMapInner = getRemainedCardCiaInfoByCidMapInner(chipHolder, ciaList);
            LogMgr.log(4, "%s", "999");
            return remainedCardCiaInfoByCidMapInner;
        } catch (FwsException e) {
            throw e;
        } catch (Exception e2) {
            LogMgr.log(2, "%s %s:%S", "700", e2.getClass().getSimpleName(), e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            throw new FwsException(200, ObfuscatedMsgUtil.exExecutionPoint(e2));
        }
    }

    public LinkedHashMap<String, CompleteCardInfo> updateCardByCidMap(MfiChipHolder chipHolder, DataManager dataManager, LinkedHashMap<String, CompleteCardInfo> cardByCidMap, Map<String, CardIdentifiableInfo> ciaInfoBySidMap) throws Throwable {
        LogMgr.log(4, "%s", "000");
        try {
            LinkedHashMap<String, CompleteCardInfo> linkedHashMapUpdateCardByCidMapInner = updateCardByCidMapInner(chipHolder, dataManager, cardByCidMap, ciaInfoBySidMap);
            LogMgr.log(4, "%s", "999");
            return linkedHashMapUpdateCardByCidMapInner;
        } catch (FwsException e) {
            throw e;
        } catch (GpException e2) {
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "%s %s:%S", "700", e3.getClass().getSimpleName(), e3.getMessage());
            LogMgr.printStackTrace(7, e3);
            throw new FwsException(200, ObfuscatedMsgUtil.exExecutionPoint(e3));
        }
    }

    public LinkedHashMap<String, CompleteCardInfo> updateCachedCardByCidMap(MfiChipHolder chipHolder, DataManager dataManager, Map<String, CompleteCardInfo.Cache> cardByCidMap, Map<String, CardIdentifiableInfo.Cache> ciaInfoBySidMap) throws Throwable {
        LogMgr.log(4, "000");
        try {
            LinkedHashMap<String, CompleteCardInfo> linkedHashMapUpdateCachedCardByCidMapInner = updateCachedCardByCidMapInner(chipHolder, dataManager, cardByCidMap, ciaInfoBySidMap);
            LogMgr.log(4, "999");
            return linkedHashMapUpdateCachedCardByCidMapInner;
        } catch (FwsException e) {
            throw e;
        } catch (GpException e2) {
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "700 " + e3.getClass().getSimpleName() + ":" + e3.getMessage());
            LogMgr.printStackTrace(7, e3);
            throw new FwsException(200, ObfuscatedMsgUtil.exExecutionPoint(e3));
        }
    }

    public ReadSeResult getReadCiaBlockList(MfiChipHolder chipHolder, CardIdentifiableInfo.Cache cardIdInfo) throws FwsException {
        LogMgr.log(4, "%s", "000");
        try {
            ReadSeResult readCiaBlockListInner = getReadCiaBlockListInner(chipHolder, cardIdInfo);
            LogMgr.log(4, "%s", "999");
            return readCiaBlockListInner;
        } catch (FwsException e) {
            throw e;
        } catch (Exception e2) {
            LogMgr.log(2, "%s %s:%S", "700", e2.getClass().getSimpleName(), e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            throw new FwsException(200, ObfuscatedMsgUtil.exExecutionPoint(e2));
        }
    }

    public boolean checkIntegrityForGpPrivate(MfiChipHolder chipHolder, CompleteCardInfo.Cache cardInfo) throws FwsException, GpException {
        return getIntegrityCheckResult(CheckPattern.PATTERN_D, chipHolder, cardInfo, null);
    }

    private Map<String, CardIdentifiableInfo> getRemainedCardCiaInfoByCidMapInner(MfiChipHolder chipHolder, Collection<CardIdentifiableInfo> ciaInfoList) throws Exception {
        Map<String, List<String>> readCiaMaskedBlockListBySidMap = getReadCiaMaskedBlockListBySidMap(chipHolder, ciaInfoList);
        HashMap map = new HashMap();
        for (CardIdentifiableInfo cardIdentifiableInfo : ciaInfoList) {
            List<String> list = readCiaMaskedBlockListBySidMap.get(cardIdentifiableInfo.serviceId);
            if (list != null) {
                int size = list.size();
                for (Map.Entry<String, List<String>> entry : cardIdentifiableInfo.maskedBlockListByCidMap.entrySet()) {
                    List<String> value = entry.getValue();
                    if (size != value.size()) {
                        LogMgr.log(2, "%s Block data size is invalid. %d / %d.", "703", Integer.valueOf(list.size()), Integer.valueOf(value.size()));
                        throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                    }
                    int i = 0;
                    while (true) {
                        if (i < size) {
                            if (!value.get(i).equalsIgnoreCase(list.get(i))) {
                                LogMgr.log(6, "001 Not match cid=%s index=%d", entry.getKey(), Integer.valueOf(i));
                                LogMgr.log(7, "002   id block  :" + value.get(i));
                                LogMgr.log(7, "003   read block:" + list.get(i));
                                break;
                            }
                            i++;
                        } else {
                            map.put(entry.getKey(), cardIdentifiableInfo);
                            break;
                        }
                    }
                }
            }
        }
        return map;
    }

    private CheckPattern getCheckPattern(CompleteCardInfo cardInfo, CardIdentifiableInfo ciaInfo) throws FwsException {
        boolean zIsCommon = ServiceTypeInfoUtil.SysType.isCommon(cardInfo.getServiceType());
        boolean zIsPrivate = ServiceTypeInfoUtil.SysType.isPrivate(cardInfo.getServiceType());
        boolean zIsServersMultiple = ServiceTypeInfoUtil.MultiCardType.isServersMultiple(cardInfo.getServiceType());
        CheckPattern checkPattern = CheckPattern.PATTERN_NONE;
        if (!zIsCommon) {
            if (!zIsPrivate) {
                return checkPattern;
            }
            if (!Property.isChipGP()) {
                return CheckPattern.PATTERN_C;
            }
            return CheckPattern.PATTERN_D;
        }
        if (zIsServersMultiple) {
            if (ciaInfo.blockInfoList == null || ciaInfo.blockInfoList.size() == 0 || ciaInfo.maskedBlockListByCidMap == null || ciaInfo.maskedBlockListByCidMap.size() == 0) {
                LogMgr.log(2, "700 : No blockInfoList/maskedBlockListByCidMap in ciaInfo. ciaInfo.blockInfoList=" + ciaInfo.blockInfoList + " ciaInfo.maskedBlockListByCidMap=" + ciaInfo.maskedBlockListByCidMap);
                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
            }
            if (ciaInfo.maskedBlockListByCidMap.get(cardInfo.getCid()) == null) {
                LogMgr.log(2, "701 : No cid in maskedBlockListByCidMap. cid=" + cardInfo.getCid());
                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
            }
            return CheckPattern.PATTERN_A;
        }
        return CheckPattern.PATTERN_B;
    }

    private CheckPattern getCheckCachedPattern(CompleteCardInfo cardInfo, CardIdentifiableInfo ciaInfo) throws FwsException {
        boolean zIsCommon = ServiceTypeInfoUtil.SysType.isCommon(cardInfo.getServiceType());
        boolean zIsPrivate = ServiceTypeInfoUtil.SysType.isPrivate(cardInfo.getServiceType());
        boolean zIsServersMultiple = ServiceTypeInfoUtil.MultiCardType.isServersMultiple(cardInfo.getServiceType());
        CheckPattern checkPattern = CheckPattern.PATTERN_NONE;
        if (!zIsCommon) {
            if (!zIsPrivate) {
                return checkPattern;
            }
            if (!Property.isChipGP()) {
                return CheckPattern.PATTERN_C;
            }
            return CheckPattern.PATTERN_D;
        }
        if (zIsServersMultiple) {
            if (ciaInfo.blockInfoList == null || ciaInfo.blockInfoList.size() == 0 || ciaInfo.maskedBlockListByCidMap == null || ciaInfo.maskedBlockListByCidMap.size() == 0) {
                LogMgr.log(2, "700 : No blockInfoList/maskedBlockListByCidMap in ciaInfo. ciaInfo.blockInfoList=" + ciaInfo.blockInfoList + " ciaInfo.maskedBlockListByCidMap=" + ciaInfo.maskedBlockListByCidMap);
                return checkPattern;
            }
            if (ciaInfo.maskedBlockListByCidMap.get(cardInfo.getCid()) == null) {
                LogMgr.log(2, "701 : No cid in maskedBlockListByCidMap. cid=" + cardInfo.getCid());
                return checkPattern;
            }
            return CheckPattern.PATTERN_A;
        }
        return CheckPattern.PATTERN_B;
    }

    private boolean integrityCheck(CheckItem item, MfiFelicaWrapper selectedFelica, CompleteCardInfo.Cache cardInfo, CardIdentifiableInfo ciaInfo, SeAppletInfo seAppletInfo) throws FwsException, GpException {
        LogMgr.log(6, "000");
        boolean zCheckSystem = false;
        switch (item) {
            case SYSTEM_CHECK:
                zCheckSystem = checkSystem(selectedFelica, ciaInfo);
                break;
            case IDM_CHECK:
                zCheckSystem = checkIDm(selectedFelica, cardInfo);
                break;
            case AREA_CHECK:
                zCheckSystem = checkArea(selectedFelica, false, ciaInfo.systemCode, ciaInfo.areaCode);
                break;
            case SERVICE_CHECK:
                zCheckSystem = checkService(selectedFelica, ciaInfo);
                break;
            case NODE_CHECK:
                zCheckSystem = checkNode(selectedFelica, cardInfo);
                break;
            case PERSONALIZED_CHECK:
                zCheckSystem = seAppletInfo.isPersonalized();
                break;
            case CID_CHECK:
                zCheckSystem = checkCid(cardInfo.cid, seAppletInfo.getCidString());
                break;
            case ID_INFO_CHECK:
                zCheckSystem = checkCardIdInfo(selectedFelica, cardInfo, ciaInfo);
                break;
        }
        LogMgr.log(6, "999 " + item.name() + "=" + zCheckSystem);
        return zCheckSystem;
    }

    /* JADX WARN: Not initialized variable reg: 12, insn: 0x0090: MOVE (r11 I:??[OBJECT, ARRAY]) = (r12 I:??[OBJECT, ARRAY]), block:B:32:0x008f */
    private boolean getIntegrityCheckResult(CheckPattern checkPattern, MfiChipHolder mfiChipHolder, CompleteCardInfo.Cache cache, CardIdentifiableInfo cardIdentifiableInfo) throws Throwable {
        Throwable th;
        GpController gpController;
        GpException gpException;
        byte[] bArrHexToByteArray;
        GpController gpController2;
        CompleteCardInfo.Cache cache2 = cache;
        LogMgr.log(6, "000 pattern=" + checkPattern);
        int iOrdinal = checkPattern.ordinal();
        int i = 0;
        int i2 = 0;
        boolean z = false;
        boolean zIntegrityCheck = true;
        if (iOrdinal == 0 || iOrdinal == 1 || iOrdinal == 2) {
            MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(mfiChipHolder);
            try {
                try {
                    mfiFelicaWrapper.open();
                    if (integrityCheck(CheckItem.SYSTEM_CHECK, mfiFelicaWrapper, cache, cardIdentifiableInfo, null)) {
                        CardIdentifiableInfo cardIdentifiableInfo2 = cardIdentifiableInfo;
                        mfiFelicaWrapper.select(cardIdentifiableInfo2.systemCode);
                        CheckItem[] checkItemArr = CheckItem_MAP.get(checkPattern);
                        int length = checkItemArr.length;
                        while (i < length) {
                            zIntegrityCheck = integrityCheck(checkItemArr[i], mfiFelicaWrapper, cache, cardIdentifiableInfo2, null);
                            if (!zIntegrityCheck) {
                                break;
                            }
                            i++;
                            cardIdentifiableInfo2 = cardIdentifiableInfo;
                        }
                        z = zIntegrityCheck;
                        mfiFelicaWrapper.closeSilently();
                    } else {
                        mfiFelicaWrapper.closeSilently();
                        return false;
                    }
                } catch (MfiFelicaException e) {
                    LogMgr.log(2, "700 MfiFelicaException");
                    throw new FwsException(e.getType(), e.getMessage());
                }
            } catch (Throwable th2) {
                mfiFelicaWrapper.closeSilently();
                throw th2;
            }
        } else if (iOrdinal == 3) {
            GpController gpController3 = null;
            try {
                try {
                    try {
                        bArrHexToByteArray = StringUtil.hexToByteArray(cache2.appletInstanceAid);
                        gpController2 = mfiChipHolder.getGpController();
                    } catch (Throwable th3) {
                        th = th3;
                        if (gpController3 != null) {
                            gpController3.closeChannel();
                            throw th;
                        }
                        throw th;
                    }
                } catch (GpException e2) {
                    gpException = e2;
                } catch (InterruptedException unused) {
                }
                try {
                    SeAppletInfo seAppletInfo = new AppletManager(gpController2).getSeAppletInfo(bArrHexToByteArray);
                    this.mIsActivatedByAIDMap.put(cache2.appletInstanceAid, Boolean.valueOf(seAppletInfo.isActivated()));
                    CheckItem[] checkItemArr2 = CheckItem_MAP.get(checkPattern);
                    int length2 = checkItemArr2.length;
                    boolean z2 = true;
                    while (true) {
                        if (i2 >= length2) {
                            z = z2;
                            break;
                        }
                        boolean zIntegrityCheck2 = integrityCheck(checkItemArr2[i2], null, cache2, cardIdentifiableInfo, seAppletInfo);
                        if (!zIntegrityCheck2) {
                            z = zIntegrityCheck2;
                            break;
                        }
                        i2++;
                        cache2 = cache;
                        z2 = zIntegrityCheck2;
                    }
                    if (gpController2 != null) {
                        gpController2.closeChannel();
                    }
                } catch (GpException e3) {
                    gpException = e3;
                    LogMgr.log(1, "801 : GpException");
                    throw gpException;
                } catch (InterruptedException unused2) {
                    LogMgr.log(2, "701 : InterruptedException");
                    throw new FwsException(215, null);
                }
            } catch (Throwable th4) {
                th = th4;
                gpController3 = gpController;
            }
        }
        LogMgr.log(6, "999 match=" + z);
        return z;
    }

    private CompleteCardInfo changeCardStatus(CheckPattern pattern, boolean match, CompleteCardInfo srcCard) throws FwsException {
        CompleteCardInfo completeCardInfo = new CompleteCardInfo(srcCard);
        if (srcCard.statusIs(1, 1, CompleteCardInfo.Finish.DONE)) {
            if (pattern != CheckPattern.PATTERN_A) {
                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
            }
            if (match) {
                completeCardInfo.setPosition(2);
                return completeCardInfo;
            }
        } else if (srcCard.statusIs(1, 0, CompleteCardInfo.Finish.DONE)) {
            if (!match) {
                completeCardInfo.setFixStatus(2);
                completeCardInfo.setPosition(3);
                return completeCardInfo;
            }
            if (pattern == CheckPattern.PATTERN_D) {
                String appletInstanceAid = srcCard.getAppletInstanceAid();
                if (this.mIsActivatedByAIDMap.containsKey(appletInstanceAid) && !this.mIsActivatedByAIDMap.get(appletInstanceAid).booleanValue()) {
                    completeCardInfo.setPosition(1);
                    return completeCardInfo;
                }
            }
        } else {
            if (srcCard.statusIs(1, 0, CompleteCardInfo.Finish.NOT_YET)) {
                if (pattern == CheckPattern.PATTERN_D) {
                    throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                }
                if (match) {
                    completeCardInfo.setPosition(2);
                    return completeCardInfo;
                }
                completeCardInfo.setFixStatus(2);
                completeCardInfo.setPosition(3);
                return completeCardInfo;
            }
            if (srcCard.statusIs(3, CompleteCardInfo.Finish.DONE)) {
                completeCardInfo.setPosition(4);
                return completeCardInfo;
            }
            if (srcCard.statusIs(3, CompleteCardInfo.Finish.NOT_YET)) {
                if (match) {
                    completeCardInfo.setPosition(2);
                    return completeCardInfo;
                }
                completeCardInfo.setFixStatus(2);
                completeCardInfo.setPosition(3);
            }
        }
        return completeCardInfo;
    }

    private CompleteCardInfo changeCachedCardStatus(CheckPattern pattern, boolean match, CompleteCardInfo srcCard) throws FwsException {
        CompleteCardInfo completeCardInfo = new CompleteCardInfo(srcCard);
        if (srcCard.statusIs(1, 2)) {
            if (pattern == CheckPattern.PATTERN_D) {
                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
            }
            if (!match) {
                completeCardInfo.setFixStatus(2);
                completeCardInfo.setPosition(3);
                return completeCardInfo;
            }
        } else if (srcCard.statusIs(1, 1)) {
            if (pattern == CheckPattern.PATTERN_A) {
                if (match) {
                    completeCardInfo.setPosition(2);
                    return completeCardInfo;
                }
            } else {
                if (pattern != CheckPattern.PATTERN_D) {
                    throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                }
                if (!match) {
                    completeCardInfo.setFixStatus(2);
                    completeCardInfo.setPosition(3);
                    return completeCardInfo;
                }
                String appletInstanceAid = srcCard.getAppletInstanceAid();
                if (this.mIsActivatedByAIDMap.containsKey(appletInstanceAid) && this.mIsActivatedByAIDMap.get(appletInstanceAid).booleanValue()) {
                    completeCardInfo.setPosition(0);
                    return completeCardInfo;
                }
            }
        } else if (srcCard.statusIs(1, 0)) {
            if (!match) {
                completeCardInfo.setFixStatus(2);
                completeCardInfo.setPosition(3);
                return completeCardInfo;
            }
            if (pattern == CheckPattern.PATTERN_D) {
                String appletInstanceAid2 = srcCard.getAppletInstanceAid();
                if (this.mIsActivatedByAIDMap.containsKey(appletInstanceAid2) && !this.mIsActivatedByAIDMap.get(appletInstanceAid2).booleanValue()) {
                    completeCardInfo.setPosition(1);
                    return completeCardInfo;
                }
            }
        } else if (srcCard.statusIs(3, 2)) {
            if (!match) {
                completeCardInfo.setFixStatus(2);
                completeCardInfo.setPosition(3);
                return completeCardInfo;
            }
        } else if (srcCard.statusIs(3, 4)) {
            throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
        }
        return completeCardInfo;
    }

    private LinkedHashMap<String, CompleteCardInfo> updateCardByCidMapInner(MfiChipHolder chipHolder, DataManager dataManager, LinkedHashMap<String, CompleteCardInfo> cardByCidMap, Map<String, CardIdentifiableInfo> ciaInfoBySidMap) throws Throwable {
        int iIntValue;
        LinkedHashMap<String, CompleteCardInfo> linkedHashMap = new LinkedHashMap<>();
        if (cardByCidMap.size() > 0) {
            HashMap map = new HashMap();
            Iterator<Map.Entry<String, CompleteCardInfo>> it = cardByCidMap.entrySet().iterator();
            while (it.hasNext()) {
                CompleteCardInfo value = it.next().getValue();
                CompleteCardInfo completeCardInfo = new CompleteCardInfo(value);
                if (!value.getSeId().equalsIgnoreCase(dataManager.getSeInfo().getSeId())) {
                    if (value.statusIs(3)) {
                        completeCardInfo.setPosition(4);
                    }
                    linkedHashMap.put(completeCardInfo.getCid(), completeCardInfo);
                } else {
                    if (!value.statusIs(2)) {
                        if (!value.statusIs(0)) {
                            if (value.statusIs(3, CompleteCardInfo.Finish.DONE)) {
                                completeCardInfo.setPosition(4);
                                linkedHashMap.put(completeCardInfo.getCid(), completeCardInfo);
                            } else {
                                if (!ciaInfoBySidMap.containsKey(value.getServiceId())) {
                                    LogMgr.log(2, "700 : No ReadIdBlockData.");
                                    LogMgr.log(6, "500 : serviceId=" + value.getServiceId() + ".");
                                    throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                                }
                                CardIdentifiableInfo cardIdentifiableInfo = ciaInfoBySidMap.get(value.getServiceId());
                                CheckPattern checkPattern = getCheckPattern(value, cardIdentifiableInfo);
                                LogMgr.log(6, "001 : pattern=" + checkPattern.name());
                                boolean integrityCheckResult = getIntegrityCheckResult(checkPattern, chipHolder, value.getCacheableData(), cardIdentifiableInfo);
                                LogMgr.log(6, "002 : match=" + integrityCheckResult);
                                CompleteCardInfo completeCardInfoChangeCardStatus = changeCardStatus(checkPattern, integrityCheckResult, value);
                                LogMgr.log(6, "003 : srcCard: " + value.dump());
                                LogMgr.log(6, "004 : dstCard: " + completeCardInfoChangeCardStatus.dump());
                                if (completeCardInfoChangeCardStatus.statusIs(1, 0)) {
                                    iIntValue = map.containsKey(completeCardInfoChangeCardStatus.getServiceId()) ? ((Integer) map.get(completeCardInfoChangeCardStatus.getServiceId())).intValue() : 0;
                                    if (iIntValue != 0) {
                                        LogMgr.log(2, "702 : active-foreground and " + iIntValue);
                                        throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                                    }
                                    map.put(completeCardInfoChangeCardStatus.getServiceId(), Integer.valueOf(iIntValue | 1));
                                } else if (completeCardInfoChangeCardStatus.statusIs(1, 2)) {
                                    iIntValue = map.containsKey(completeCardInfoChangeCardStatus.getServiceId()) ? ((Integer) map.get(completeCardInfoChangeCardStatus.getServiceId())).intValue() : 0;
                                    if ((iIntValue & 1) != 0) {
                                        LogMgr.log(2, "703 : active-foreground and " + iIntValue);
                                        throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                                    }
                                    map.put(completeCardInfoChangeCardStatus.getServiceId(), Integer.valueOf(iIntValue | 2));
                                }
                                linkedHashMap.put(completeCardInfoChangeCardStatus.getCid(), completeCardInfoChangeCardStatus);
                            }
                        }
                    }
                    linkedHashMap.put(completeCardInfo.getCid(), completeCardInfo);
                }
            }
        }
        return linkedHashMap;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x01c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private LinkedHashMap<String, CompleteCardInfo> updateCachedCardByCidMapInner(MfiChipHolder chipHolder, DataManager dataManager, Map<String, CompleteCardInfo.Cache> cardByCidMap, Map<String, CardIdentifiableInfo.Cache> ciaInfoBySidMap) throws Throwable {
        int iIntValue;
        LinkedHashMap<String, CompleteCardInfo> linkedHashMap = new LinkedHashMap<>();
        if (cardByCidMap.size() > 0) {
            HashMap map = new HashMap();
            Iterator<Map.Entry<String, CompleteCardInfo.Cache>> it = cardByCidMap.entrySet().iterator();
            while (it.hasNext()) {
                CompleteCardInfo.Cache value = it.next().getValue();
                Iterator<Map.Entry<String, CompleteCardInfo.Cache>> it2 = it;
                CompleteCardInfo completeCardInfo = new CompleteCardInfo(value.cid, value.serviceId, value.walletAppId, value.state, value.position, value.task, value.reissuePossibility, value.serviceType, value.additionalInfoHash, value.cardCategory, value.appletInstanceAid, value.finish, value.reissueStatus, value.seId, value.idm, value.nodeCodeList, value.cardType);
                completeCardInfo.setSpType(value.spType);
                CompleteCardInfo completeCardInfo2 = new CompleteCardInfo(completeCardInfo);
                if (!completeCardInfo.getSeId().equalsIgnoreCase(dataManager.getSeInfo().getSeId())) {
                    linkedHashMap.put(completeCardInfo2.getCid(), completeCardInfo2);
                } else {
                    if (!ciaInfoBySidMap.containsKey(completeCardInfo.getServiceId())) {
                        LogMgr.log(2, "700 : No ReadIdBlockData.");
                        LogMgr.log(6, "500 : serviceId=" + completeCardInfo.getServiceId() + ".");
                        throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                    }
                    CardIdentifiableInfo cardIdentifiableInfo = new CardIdentifiableInfo(ciaInfoBySidMap.get(completeCardInfo.getServiceId()));
                    CheckPattern checkCachedPattern = getCheckCachedPattern(completeCardInfo, cardIdentifiableInfo);
                    LogMgr.log(6, "001 : pattern=" + checkCachedPattern.name());
                    if (checkCachedPattern == CheckPattern.PATTERN_C || checkCachedPattern == CheckPattern.PATTERN_NONE) {
                        if (completeCardInfo.statusIs(1, 1) && checkCachedPattern == CheckPattern.PATTERN_C) {
                            throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                        }
                        if (completeCardInfo.statusIs(3, 4)) {
                            throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                        }
                        completeCardInfo2.setFixStatus(4);
                        completeCardInfo2.setPosition(3);
                        LogMgr.log(6, "002 : srcCard: " + completeCardInfo.dump());
                        LogMgr.log(6, "003 : dstCard: " + completeCardInfo2.dump());
                        linkedHashMap.put(completeCardInfo2.getCid(), completeCardInfo2);
                    } else if (!completeCardInfo.statusIs(2)) {
                        if (completeCardInfo.statusIs(0)) {
                            linkedHashMap.put(completeCardInfo2.getCid(), completeCardInfo2);
                        } else {
                            boolean integrityCheckResult = getIntegrityCheckResult(checkCachedPattern, chipHolder, value, cardIdentifiableInfo);
                            LogMgr.log(6, "004 : match=" + integrityCheckResult);
                            CompleteCardInfo completeCardInfoChangeCachedCardStatus = changeCachedCardStatus(checkCachedPattern, integrityCheckResult, completeCardInfo);
                            LogMgr.log(6, "005 : srcCard: " + completeCardInfo.dump());
                            LogMgr.log(6, "006 : dstCard: " + completeCardInfoChangeCachedCardStatus.dump());
                            if (completeCardInfoChangeCachedCardStatus.statusIs(1, 0)) {
                                iIntValue = map.containsKey(completeCardInfoChangeCachedCardStatus.getServiceId()) ? ((Integer) map.get(completeCardInfoChangeCachedCardStatus.getServiceId())).intValue() : 0;
                                if (iIntValue != 0) {
                                    LogMgr.log(2, "702 : active-foreground and " + iIntValue);
                                    throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                                }
                                map.put(completeCardInfoChangeCachedCardStatus.getServiceId(), Integer.valueOf(iIntValue | 1));
                            } else if (completeCardInfoChangeCachedCardStatus.statusIs(1, 2)) {
                                iIntValue = map.containsKey(completeCardInfoChangeCachedCardStatus.getServiceId()) ? ((Integer) map.get(completeCardInfoChangeCachedCardStatus.getServiceId())).intValue() : 0;
                                if ((iIntValue & 1) != 0) {
                                    LogMgr.log(2, "703 : active-foreground and " + iIntValue);
                                    throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                                }
                                map.put(completeCardInfoChangeCachedCardStatus.getServiceId(), Integer.valueOf(iIntValue | 2));
                            }
                            linkedHashMap.put(completeCardInfoChangeCachedCardStatus.getCid(), completeCardInfoChangeCachedCardStatus);
                        }
                    }
                }
                it = it2;
            }
        }
        return linkedHashMap;
    }

    private ReadSeResult getReadCiaBlockListInner(MfiChipHolder chipHolder, CardIdentifiableInfo.Cache ciaInfo) throws Exception {
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(chipHolder);
        try {
            mfiFelicaWrapper.open();
            ReadSeResult readSeResult = new ReadSeResult();
            ArrayList arrayList = new ArrayList();
            try {
                boolean zCheckArea = checkArea(mfiFelicaWrapper, true, ciaInfo.systemCode, ciaInfo.areaCode);
                readSeResult.setAvailableArea(zCheckArea);
                if (!zCheckArea || ciaInfo.blockInfoArray == null || ciaInfo.blockInfoArray.length == 0) {
                    readSeResult.setReadCiaBlockList(null);
                } else {
                    Data[] readCiaBlockDataArray = getReadCiaBlockDataArray(mfiFelicaWrapper, ciaInfo.blockInfoArray);
                    int length = ciaInfo.blockInfoArray.length;
                    ByteBuffer byteBuffer = new ByteBuffer(16);
                    for (int i = 0; i < length; i++) {
                        byteBuffer.setLength(0);
                        CardIdentifiableBlockData cardIdentifiableBlockData = new CardIdentifiableBlockData();
                        CardIdBlockInfo.Cache cache = ciaInfo.blockInfoArray[i];
                        cardIdentifiableBlockData.serviceCode = cache.serviceCode;
                        cardIdentifiableBlockData.blockNumber = cache.blockNumber;
                        DataUtil.getInstance().append(byteBuffer, readCiaBlockDataArray[i]);
                        cardIdentifiableBlockData.data = (byte[]) byteBuffer.getBytes().clone();
                        arrayList.add(cardIdentifiableBlockData);
                    }
                    readSeResult.setReadCiaBlockList(arrayList);
                }
                mfiFelicaWrapper.close();
                return readSeResult;
            } catch (MfiFelicaException e) {
                LogMgr.log(2, "%s Felica#close : MfiFelicaException", "702");
                mfiFelicaWrapper.closeSilently();
                throw new FwsException(e.getType(), e.getMessage());
            } catch (Exception e2) {
                mfiFelicaWrapper.closeSilently();
                throw e2;
            }
        } catch (MfiFelicaException e3) {
            LogMgr.log(2, "%s Felica#open : MfiFelicaException", "701");
            throw new FwsException(e3.getType(), e3.getMessage());
        }
    }

    private Map<String, List<String>> getReadCiaMaskedBlockListBySidMap(MfiChipHolder chipHolder, Collection<CardIdentifiableInfo> ciaInfoList) throws Exception {
        LogMgr.log(6, "%s", "000");
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(chipHolder);
        try {
            mfiFelicaWrapper.open();
            HashMap map = new HashMap();
            try {
                for (CardIdentifiableInfo cardIdentifiableInfo : ciaInfoList) {
                    LogMgr.log(7, "001 Read serviceId=" + cardIdentifiableInfo.serviceId);
                    map.put(cardIdentifiableInfo.serviceId, getReadCiaMaskedBlockList(mfiFelicaWrapper, cardIdentifiableInfo, true));
                }
                mfiFelicaWrapper.close();
                LogMgr.log(6, "%s", "999");
                return map;
            } catch (MfiFelicaException e) {
                LogMgr.log(2, "%s Felica#close : MfiFelicaException", "702");
                mfiFelicaWrapper.closeSilently();
                throw new FwsException(e.getType(), e.getMessage());
            } catch (Exception e2) {
                mfiFelicaWrapper.closeSilently();
                throw e2;
            }
        } catch (MfiFelicaException e3) {
            LogMgr.log(2, "%s Felica#open : MfiFelicaException", "701");
            throw new FwsException(e3.getType(), e3.getMessage());
        }
    }

    private List<String> getReadCiaMaskedBlockList(MfiFelicaWrapper opendFelica, CardIdentifiableInfo ciaInfo, boolean needCheckArea) throws FwsException {
        List<String> list = null;
        if (needCheckArea && !checkArea(opendFelica, true, ciaInfo.systemCode, ciaInfo.areaCode)) {
            return null;
        }
        Data[] readCiaBlockDataArray = getReadCiaBlockDataArray(opendFelica, ciaInfo.getCacheableData().blockInfoArray);
        ArrayList arrayList = new ArrayList();
        int size = ciaInfo.blockInfoList.size();
        ByteBuffer byteBuffer = new ByteBuffer(16);
        int i = 0;
        while (i < size) {
            byteBuffer.setLength(0);
            DataUtil.getInstance().append(byteBuffer, readCiaBlockDataArray[i]);
            byte[] bArr = (byte[]) byteBuffer.getBytes().clone();
            byte[] bArr2 = ciaInfo.blockInfoList.get(i).blockMask;
            if (bArr.length != bArr2.length) {
                LogMgr.log(2, "%s Data length is different. :ReadData=%d, MaskData=%d", "706", Integer.valueOf(bArr.length), Integer.valueOf(bArr2.length));
                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
            }
            LogMgr.log(7, "001 : read:");
            LogMgr.logArray(7, bArr);
            LogMgr.log(7, "002 : mask:");
            LogMgr.logArray(7, bArr2);
            byte[] bArr3 = new byte[bArr.length];
            List<String> list2 = list;
            boolean z = true;
            for (int i2 = 0; i2 < bArr.length; i2++) {
                byte b = bArr[i2];
                if (b != 0) {
                    z = false;
                }
                bArr3[i2] = (byte) (b & bArr2[i2]);
            }
            if (z) {
                LogMgr.log(6, "%s : ALL0.", "501");
                return list2;
            }
            arrayList.add(StringUtil.bytesToHexString(bArr3));
            i++;
            list = list2;
        }
        LogMgr.log(6, "%s", "999");
        return arrayList;
    }

    private boolean checkArea(MfiFelicaWrapper opendFelica, boolean needSelect, int systemCode, int areaCode) throws FwsException {
        LogMgr.log(6, "%s", "000");
        if (areaCode == -1) {
            LogMgr.log(2, "700 Area code is invalid value.");
            throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
        }
        if (needSelect) {
            try {
                opendFelica.select(systemCode);
            } catch (MfiFelicaException e) {
                LogMgr.log(2, "701 Felica#select : MfiFelicaException");
                throw new FwsException(e.getType(), e.getMessage());
            }
        }
        try {
            boolean zCheckNodeCodeExists = opendFelica.checkNodeCodeExists(areaCode);
            LogMgr.log(6, "999");
            return zCheckNodeCodeExists;
        } catch (MfiFelicaException e2) {
            LogMgr.log(2, "702 Felica#getKeyVersion or getKeyVersionV2 MfiFelicaException");
            LogMgr.printStackTrace(7, e2);
            throw new FwsException(e2.getType(), e2.getMessage());
        }
    }

    private boolean checkSystem(MfiFelicaWrapper openedFelica, CardIdentifiableInfo ciaInfo) throws FwsException {
        LogMgr.log(6, "000");
        if (this.mSystemCodeList == null) {
            try {
                openedFelica.select(65039);
                this.mSystemCodeList = openedFelica.getSystemCodeList();
            } catch (MfiFelicaException e) {
                LogMgr.log(2, "700 Felica#getSystemCodeList : MfiFelicaException");
                LogMgr.printStackTrace(7, e);
                throw new FwsException(e.getType(), e.getMessage());
            }
        }
        int[] iArr = this.mSystemCodeList;
        boolean z = false;
        if (iArr != null) {
            int length = iArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (iArr[i] == ciaInfo.systemCode) {
                    z = true;
                    break;
                }
                i++;
            }
        }
        LogMgr.log(6, "999");
        return z;
    }

    private boolean checkIDm(MfiFelicaWrapper selectedFelica, CompleteCardInfo.Cache cardInfo) throws FwsException {
        LogMgr.log(6, "000");
        try {
            byte[] iDm = selectedFelica.getIDm();
            LogMgr.log(6, "999");
            return Arrays.equals(cardInfo.idm, iDm);
        } catch (MfiFelicaException e) {
            LogMgr.log(2, "700 Felica#getIDm : MfiFelicaException");
            LogMgr.printStackTrace(7, e);
            throw new FwsException(e.getType(), e.getMessage());
        }
    }

    private static boolean checkService(MfiFelicaWrapper selectedFelica, CardIdentifiableInfo ciaInfo) throws FwsException {
        LogMgr.log(6, "000");
        int[] iArr = new int[ciaInfo.blockInfoList.size()];
        boolean z = false;
        for (int i = 0; i < ciaInfo.blockInfoList.size(); i++) {
            iArr[i] = ciaInfo.blockInfoList.get(i).serviceCode;
        }
        try {
            boolean[] zArrCheckNodeCodeListExists = selectedFelica.checkNodeCodeListExists(iArr);
            int length = zArrCheckNodeCodeListExists.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    z = true;
                    break;
                }
                if (!zArrCheckNodeCodeListExists[i2]) {
                    break;
                }
                i2++;
            }
            LogMgr.log(6, "999");
            return z;
        } catch (MfiFelicaException e) {
            LogMgr.log(2, "700 Felica#getKeyVersion or getKeyVersionV2 : MfiFelicaException");
            LogMgr.printStackTrace(7, e);
            throw new FwsException(e.getType(), e.getMessage());
        }
    }

    private static boolean checkNode(MfiFelicaWrapper selectedFelica, CompleteCardInfo.Cache cardInfo) throws FwsException {
        LogMgr.log(6, "000");
        try {
            boolean[] zArrCheckNodeCodeListExists = selectedFelica.checkNodeCodeListExists(cardInfo.nodeCodeList);
            int length = zArrCheckNodeCodeListExists.length;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= length) {
                    z = true;
                    break;
                }
                if (!zArrCheckNodeCodeListExists[i]) {
                    break;
                }
                i++;
            }
            LogMgr.log(6, "999");
            return z;
        } catch (MfiFelicaException e) {
            LogMgr.log(2, "700 Felica#getKeyVersion or getKeyVersionV2 : MfiFelicaException");
            LogMgr.printStackTrace(7, e);
            throw new FwsException(e.getType(), e.getMessage());
        }
    }

    private static boolean checkCid(String cardInfoCid, String SeInfoCid) {
        LogMgr.log(6, "000");
        if ("D00000000000000000000000000000000000000000000000000000000000001".equals(cardInfoCid)) {
            if (MfiClientConst.NO_CID_INSTANCE_KEY.equals(SeInfoCid)) {
                LogMgr.log(6, "001 exist inidividual card.");
                return true;
            }
            LogMgr.log(6, "002 Not exist inidividual card.");
        }
        if (cardInfoCid.equals(SeInfoCid)) {
            return true;
        }
        LogMgr.log(6, "999");
        return false;
    }

    private boolean checkCardIdInfo(MfiFelicaWrapper selectedFelica, CompleteCardInfo.Cache cardInfo, CardIdentifiableInfo ciaInfo) throws FwsException {
        LogMgr.log(6, "000");
        List<String> list = ciaInfo.maskedBlockListByCidMap.get(cardInfo.cid);
        if (list == null) {
            LogMgr.log(2, "700 : No BlockData.");
            LogMgr.log(6, "500 : cid=" + cardInfo.cid);
            throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
        }
        List<String> readCiaMaskedBlockList = getReadCiaMaskedBlockList(selectedFelica, ciaInfo, false);
        if (readCiaMaskedBlockList == null) {
            LogMgr.log(2, "701 readCiaMdBlockList is null");
            return false;
        }
        if (list.size() != readCiaMaskedBlockList.size()) {
            LogMgr.log(2, "702 Block data size is invalid. " + readCiaMaskedBlockList.size() + " / " + list.size());
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equalsIgnoreCase(readCiaMaskedBlockList.get(i))) {
                LogMgr.log(6, "001");
                return false;
            }
        }
        LogMgr.log(6, "999");
        return true;
    }

    private Data[] getReadCiaBlockDataArray(MfiFelicaWrapper selectedFelica, CardIdBlockInfo.Cache[] ciaBlockInfoArray) throws FwsException {
        LogMgr.log(6, "%s", "000");
        if (ciaBlockInfoArray.length == 0) {
            LogMgr.log(6, "001 ciaBlockInfoArray length = 0");
            return new Data[0];
        }
        BlockList blockList = new BlockList();
        for (CardIdBlockInfo.Cache cache : ciaBlockInfoArray) {
            blockList.add(new Block(cache.serviceCode, cache.blockNumber));
        }
        try {
            Data[] dataArr = selectedFelica.read(blockList);
            if (dataArr == null) {
                LogMgr.log(2, "%s Read data is null.", "702");
                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
            }
            if (ciaBlockInfoArray.length != dataArr.length) {
                LogMgr.log(2, "%s Read data size is invalid. %d -> %d.", "703", Integer.valueOf(ciaBlockInfoArray.length), Integer.valueOf(dataArr.length));
                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
            }
            LogMgr.log(6, "%s", "999");
            return dataArr;
        } catch (MfiFelicaException e) {
            LogMgr.log(2, "%s Felica#read : MfiFelicaException", "701");
            throw new FwsException(e.getType(), e.getMessage());
        }
    }
}
