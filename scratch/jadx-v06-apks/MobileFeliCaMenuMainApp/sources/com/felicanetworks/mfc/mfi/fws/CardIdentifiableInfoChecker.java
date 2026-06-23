package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.mfi.CardIdBlockInfo;
import com.felicanetworks.mfc.mfi.CardIdentifiableBlockData;
import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CardInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.felica.offlineimpl.DataUtil;
import com.felicanetworks.mfc.mfi.felica.util.ByteBuffer;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
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

/* JADX INFO: loaded from: classes.dex */
public class CardIdentifiableInfoChecker {
    private static final Map<CheckPattern, CheckItem[]> CheckItem_MAP;
    private static final CheckItem[] PATTERN_A_CHECK_ITEM = {CheckItem.IDM_CHECK, CheckItem.AREA_CHECK, CheckItem.SERVICE_CHECK, CheckItem.ID_INFO_CHECK};
    private static final CheckItem[] PATTERN_B_CHECK_ITEM = {CheckItem.IDM_CHECK, CheckItem.AREA_CHECK};
    private static final CheckItem[] PATTERN_C_CHECK_ITEM = {CheckItem.IDM_CHECK, CheckItem.NODE_CHECK};
    private static final CheckItem[] PATTERN_D_CHECK_ITEM = {CheckItem.PERSONALIZED_CHECK, CheckItem.CID_CHECK};
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
        HashMap map = new HashMap();
        CheckItem_MAP = map;
        map.put(CheckPattern.PATTERN_A, PATTERN_A_CHECK_ITEM);
        CheckItem_MAP.put(CheckPattern.PATTERN_B, PATTERN_B_CHECK_ITEM);
        CheckItem_MAP.put(CheckPattern.PATTERN_C, PATTERN_C_CHECK_ITEM);
        CheckItem_MAP.put(CheckPattern.PATTERN_D, PATTERN_D_CHECK_ITEM);
    }

    public Map<String, CardIdentifiableInfo> getRemainedCardCiaInfoByCidMap(MfiChipHolder mfiChipHolder, Collection<CardIdentifiableInfo> collection) throws FwsException {
        LogMgr.log(4, "%s", "000");
        try {
            Map<String, CardIdentifiableInfo> remainedCardCiaInfoByCidMapInner = getRemainedCardCiaInfoByCidMapInner(mfiChipHolder, collection);
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

    public LinkedHashMap<String, CompleteCardInfo> updateCardByCidMap(MfiChipHolder mfiChipHolder, DataManager dataManager, LinkedHashMap<String, CompleteCardInfo> linkedHashMap, Map<String, CardIdentifiableInfo> map) throws Throwable {
        LogMgr.log(4, "%s", "000");
        try {
            LinkedHashMap<String, CompleteCardInfo> linkedHashMapUpdateCardByCidMapInner = updateCardByCidMapInner(mfiChipHolder, dataManager, linkedHashMap, map);
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

    public LinkedHashMap<String, CompleteCardInfo> updateCachedCardByCidMap(MfiChipHolder mfiChipHolder, DataManager dataManager, Map<String, CompleteCardInfo.Cache> map, Map<String, CardIdentifiableInfo.Cache> map2) throws Throwable {
        LogMgr.log(4, "000");
        try {
            LinkedHashMap<String, CompleteCardInfo> linkedHashMapUpdateCachedCardByCidMapInner = updateCachedCardByCidMapInner(mfiChipHolder, dataManager, map, map2);
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

    public ReadSeResult getReadCiaBlockList(MfiChipHolder mfiChipHolder, CardIdentifiableInfo.Cache cache) throws FwsException {
        LogMgr.log(4, "%s", "000");
        try {
            ReadSeResult readCiaBlockListInner = getReadCiaBlockListInner(mfiChipHolder, cache);
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

    public boolean checkIntegrityForGpPrivate(MfiChipHolder mfiChipHolder, CompleteCardInfo.Cache cache) throws FwsException, GpException {
        return getIntegrityCheckResult(CheckPattern.PATTERN_D, mfiChipHolder, cache, null);
    }

    private Map<String, CardIdentifiableInfo> getRemainedCardCiaInfoByCidMapInner(MfiChipHolder mfiChipHolder, Collection<CardIdentifiableInfo> collection) throws Exception {
        Map<String, List<String>> readCiaMaskedBlockListBySidMap = getReadCiaMaskedBlockListBySidMap(mfiChipHolder, collection);
        HashMap map = new HashMap();
        for (CardIdentifiableInfo cardIdentifiableInfo : collection) {
            List<String> list = readCiaMaskedBlockListBySidMap.get(cardIdentifiableInfo.serviceId);
            if (list != null) {
                int size = list.size();
                for (Map.Entry<String, List<String>> entry : cardIdentifiableInfo.maskedBlockListByCidMap.entrySet()) {
                    List<String> value = entry.getValue();
                    if (size != value.size()) {
                        LogMgr.log(2, "%s Block data size is invalid. %d / %d.", "703", Integer.valueOf(list.size()), Integer.valueOf(value.size()));
                        throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                    }
                    boolean z = false;
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            z = true;
                            break;
                        }
                        if (!value.get(i).equalsIgnoreCase(list.get(i))) {
                            LogMgr.log(6, "001 Not match cid=%s index=%d", entry.getKey(), Integer.valueOf(i));
                            LogMgr.log(7, "002   id block  :" + value.get(i));
                            LogMgr.log(7, "003   read block:" + list.get(i));
                            break;
                        }
                        i++;
                    }
                    if (z) {
                        map.put(entry.getKey(), cardIdentifiableInfo);
                    }
                }
            }
        }
        return map;
    }

    private CheckPattern getCheckPattern(CompleteCardInfo completeCardInfo, CardIdentifiableInfo cardIdentifiableInfo) throws FwsException {
        boolean zIsCommon = ServiceTypeInfoUtil.SysType.isCommon(completeCardInfo.getServiceType());
        boolean zIsPrivate = ServiceTypeInfoUtil.SysType.isPrivate(completeCardInfo.getServiceType());
        boolean zIsServersMultiple = ServiceTypeInfoUtil.MultiCardType.isServersMultiple(completeCardInfo.getServiceType());
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
            if (cardIdentifiableInfo.blockInfoList == null || cardIdentifiableInfo.blockInfoList.size() == 0 || cardIdentifiableInfo.maskedBlockListByCidMap == null || cardIdentifiableInfo.maskedBlockListByCidMap.size() == 0) {
                LogMgr.log(2, "700 : No blockInfoList/maskedBlockListByCidMap in ciaInfo. ciaInfo.blockInfoList=" + cardIdentifiableInfo.blockInfoList + " ciaInfo.maskedBlockListByCidMap=" + cardIdentifiableInfo.maskedBlockListByCidMap);
                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
            }
            if (cardIdentifiableInfo.maskedBlockListByCidMap.get(completeCardInfo.getCid()) == null) {
                LogMgr.log(2, "701 : No cid in maskedBlockListByCidMap. cid=" + completeCardInfo.getCid());
                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
            }
            return CheckPattern.PATTERN_A;
        }
        return CheckPattern.PATTERN_B;
    }

    private CheckPattern getCheckCachedPattern(CompleteCardInfo completeCardInfo, CardIdentifiableInfo cardIdentifiableInfo) throws FwsException {
        boolean zIsCommon = ServiceTypeInfoUtil.SysType.isCommon(completeCardInfo.getServiceType());
        boolean zIsPrivate = ServiceTypeInfoUtil.SysType.isPrivate(completeCardInfo.getServiceType());
        boolean zIsServersMultiple = ServiceTypeInfoUtil.MultiCardType.isServersMultiple(completeCardInfo.getServiceType());
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
            if (cardIdentifiableInfo.blockInfoList == null || cardIdentifiableInfo.blockInfoList.size() == 0 || cardIdentifiableInfo.maskedBlockListByCidMap == null || cardIdentifiableInfo.maskedBlockListByCidMap.size() == 0) {
                LogMgr.log(2, "700 : No blockInfoList/maskedBlockListByCidMap in ciaInfo. ciaInfo.blockInfoList=" + cardIdentifiableInfo.blockInfoList + " ciaInfo.maskedBlockListByCidMap=" + cardIdentifiableInfo.maskedBlockListByCidMap);
                return checkPattern;
            }
            if (cardIdentifiableInfo.maskedBlockListByCidMap.get(completeCardInfo.getCid()) == null) {
                LogMgr.log(2, "701 : No cid in maskedBlockListByCidMap. cid=" + completeCardInfo.getCid());
                return checkPattern;
            }
            return CheckPattern.PATTERN_A;
        }
        return CheckPattern.PATTERN_B;
    }

    private boolean integrityCheck(CheckItem checkItem, MfiFelicaWrapper mfiFelicaWrapper, CompleteCardInfo.Cache cache, CardIdentifiableInfo cardIdentifiableInfo, SeAppletInfo seAppletInfo) throws FwsException, GpException {
        LogMgr.log(6, "000");
        boolean zCheckSystem = false;
        switch (AnonymousClass1.$SwitchMap$com$felicanetworks$mfc$mfi$fws$CardIdentifiableInfoChecker$CheckItem[checkItem.ordinal()]) {
            case 1:
                zCheckSystem = checkSystem(mfiFelicaWrapper, cardIdentifiableInfo);
                break;
            case 2:
                zCheckSystem = checkIDm(mfiFelicaWrapper, cache);
                break;
            case 3:
                zCheckSystem = checkArea(mfiFelicaWrapper, false, cardIdentifiableInfo.systemCode, cardIdentifiableInfo.areaCode);
                break;
            case 4:
                zCheckSystem = checkService(mfiFelicaWrapper, cardIdentifiableInfo);
                break;
            case 5:
                zCheckSystem = checkNode(mfiFelicaWrapper, cache);
                break;
            case 6:
                zCheckSystem = seAppletInfo.isPersonalized();
                break;
            case 7:
                zCheckSystem = checkCid(cache.cid, seAppletInfo.getCidString());
                break;
            case 8:
                zCheckSystem = checkCardIdInfo(mfiFelicaWrapper, cache, cardIdentifiableInfo);
                break;
        }
        LogMgr.log(6, "999 " + checkItem.name() + "=" + zCheckSystem);
        return zCheckSystem;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00b5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean getIntegrityCheckResult(com.felicanetworks.mfc.mfi.fws.CardIdentifiableInfoChecker.CheckPattern r18, com.felicanetworks.mfc.mfi.MfiChipHolder r19, com.felicanetworks.mfc.mfi.CompleteCardInfo.Cache r20, com.felicanetworks.mfc.mfi.CardIdentifiableInfo r21) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 307
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.fws.CardIdentifiableInfoChecker.getIntegrityCheckResult(com.felicanetworks.mfc.mfi.fws.CardIdentifiableInfoChecker$CheckPattern, com.felicanetworks.mfc.mfi.MfiChipHolder, com.felicanetworks.mfc.mfi.CompleteCardInfo$Cache, com.felicanetworks.mfc.mfi.CardIdentifiableInfo):boolean");
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfc.mfi.fws.CardIdentifiableInfoChecker$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfc$mfi$fws$CardIdentifiableInfoChecker$CheckItem;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfc$mfi$fws$CardIdentifiableInfoChecker$CheckPattern;

        static {
            int[] iArr = new int[CheckPattern.values().length];
            $SwitchMap$com$felicanetworks$mfc$mfi$fws$CardIdentifiableInfoChecker$CheckPattern = iArr;
            try {
                iArr[CheckPattern.PATTERN_A.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$fws$CardIdentifiableInfoChecker$CheckPattern[CheckPattern.PATTERN_B.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$fws$CardIdentifiableInfoChecker$CheckPattern[CheckPattern.PATTERN_C.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$fws$CardIdentifiableInfoChecker$CheckPattern[CheckPattern.PATTERN_D.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[CheckItem.values().length];
            $SwitchMap$com$felicanetworks$mfc$mfi$fws$CardIdentifiableInfoChecker$CheckItem = iArr2;
            try {
                iArr2[CheckItem.SYSTEM_CHECK.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$fws$CardIdentifiableInfoChecker$CheckItem[CheckItem.IDM_CHECK.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$fws$CardIdentifiableInfoChecker$CheckItem[CheckItem.AREA_CHECK.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$fws$CardIdentifiableInfoChecker$CheckItem[CheckItem.SERVICE_CHECK.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$fws$CardIdentifiableInfoChecker$CheckItem[CheckItem.NODE_CHECK.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$fws$CardIdentifiableInfoChecker$CheckItem[CheckItem.PERSONALIZED_CHECK.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$fws$CardIdentifiableInfoChecker$CheckItem[CheckItem.CID_CHECK.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$fws$CardIdentifiableInfoChecker$CheckItem[CheckItem.ID_INFO_CHECK.ordinal()] = 8;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    private CompleteCardInfo changeCardStatus(CheckPattern checkPattern, boolean z, CompleteCardInfo completeCardInfo) throws FwsException {
        CompleteCardInfo completeCardInfo2 = new CompleteCardInfo(completeCardInfo);
        if (completeCardInfo.statusIs(1, 1, CompleteCardInfo.Finish.DONE)) {
            if (checkPattern != CheckPattern.PATTERN_A) {
                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
            }
            if (z) {
                completeCardInfo2.setPosition(2);
            }
        } else if (completeCardInfo.statusIs(1, 0, CompleteCardInfo.Finish.DONE)) {
            if (!z) {
                completeCardInfo2.setFixStatus(2);
                completeCardInfo2.setPosition(3);
            } else if (checkPattern == CheckPattern.PATTERN_D) {
                String appletInstanceAid = completeCardInfo.getAppletInstanceAid();
                if (this.mIsActivatedByAIDMap.containsKey(appletInstanceAid) && !this.mIsActivatedByAIDMap.get(appletInstanceAid).booleanValue()) {
                    completeCardInfo2.setPosition(1);
                }
            }
        } else if (completeCardInfo.statusIs(1, 0, CompleteCardInfo.Finish.NOT_YET)) {
            if (checkPattern == CheckPattern.PATTERN_D) {
                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
            }
            if (z) {
                completeCardInfo2.setPosition(2);
            } else {
                completeCardInfo2.setFixStatus(2);
                completeCardInfo2.setPosition(3);
            }
        } else if (completeCardInfo.statusIs(3, CompleteCardInfo.Finish.DONE)) {
            completeCardInfo2.setPosition(4);
        } else if (completeCardInfo.statusIs(3, CompleteCardInfo.Finish.NOT_YET)) {
            if (z) {
                completeCardInfo2.setPosition(2);
            } else {
                completeCardInfo2.setFixStatus(2);
                completeCardInfo2.setPosition(3);
            }
        }
        return completeCardInfo2;
    }

    private CompleteCardInfo changeCachedCardStatus(CheckPattern checkPattern, boolean z, CompleteCardInfo completeCardInfo) throws FwsException {
        CompleteCardInfo completeCardInfo2 = new CompleteCardInfo(completeCardInfo);
        if (completeCardInfo.statusIs(1, 2)) {
            if (checkPattern == CheckPattern.PATTERN_D) {
                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
            }
            if (!z) {
                completeCardInfo2.setFixStatus(2);
                completeCardInfo2.setPosition(3);
            }
            return completeCardInfo2;
        }
        if (completeCardInfo.statusIs(1, 1)) {
            if (checkPattern == CheckPattern.PATTERN_A) {
                if (z) {
                    completeCardInfo2.setPosition(2);
                }
            } else {
                if (checkPattern != CheckPattern.PATTERN_D) {
                    throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                }
                if (!z) {
                    completeCardInfo2.setFixStatus(2);
                    completeCardInfo2.setPosition(3);
                } else {
                    String appletInstanceAid = completeCardInfo.getAppletInstanceAid();
                    if (this.mIsActivatedByAIDMap.containsKey(appletInstanceAid) && this.mIsActivatedByAIDMap.get(appletInstanceAid).booleanValue()) {
                        completeCardInfo2.setPosition(0);
                    }
                }
            }
            return completeCardInfo2;
        }
        if (!completeCardInfo.statusIs(1, 0)) {
            if (!completeCardInfo.statusIs(3, 2)) {
                if (completeCardInfo.statusIs(3, 4)) {
                    throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                }
                return completeCardInfo2;
            }
            if (!z) {
                completeCardInfo2.setFixStatus(2);
                completeCardInfo2.setPosition(3);
            }
            return completeCardInfo2;
        }
        if (!z) {
            completeCardInfo2.setFixStatus(2);
            completeCardInfo2.setPosition(3);
        } else if (checkPattern == CheckPattern.PATTERN_D) {
            String appletInstanceAid2 = completeCardInfo.getAppletInstanceAid();
            if (this.mIsActivatedByAIDMap.containsKey(appletInstanceAid2) && !this.mIsActivatedByAIDMap.get(appletInstanceAid2).booleanValue()) {
                completeCardInfo2.setPosition(1);
            }
        }
        return completeCardInfo2;
    }

    private LinkedHashMap<String, CompleteCardInfo> updateCardByCidMapInner(MfiChipHolder mfiChipHolder, DataManager dataManager, LinkedHashMap<String, CompleteCardInfo> linkedHashMap, Map<String, CardIdentifiableInfo> map) throws Throwable {
        int iIntValue;
        LinkedHashMap<String, CompleteCardInfo> linkedHashMap2 = new LinkedHashMap<>();
        if (linkedHashMap.size() > 0) {
            HashMap map2 = new HashMap();
            Iterator<Map.Entry<String, CompleteCardInfo>> it = linkedHashMap.entrySet().iterator();
            while (it.hasNext()) {
                CompleteCardInfo value = it.next().getValue();
                CompleteCardInfo completeCardInfo = new CompleteCardInfo(value);
                if (!value.getSeId().equalsIgnoreCase(dataManager.getSeInfo().getSeId())) {
                    if (value.statusIs(3)) {
                        completeCardInfo.setPosition(4);
                    }
                    linkedHashMap2.put(completeCardInfo.getCid(), completeCardInfo);
                } else {
                    if (!value.statusIs(2)) {
                        if (!value.statusIs(0)) {
                            if (!map.containsKey(value.getServiceId())) {
                                LogMgr.log(2, "700 : No ReadIdBlockData.");
                                LogMgr.log(6, "500 : serviceId=" + value.getServiceId() + ".");
                                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                            }
                            CardIdentifiableInfo cardIdentifiableInfo = map.get(value.getServiceId());
                            CheckPattern checkPattern = getCheckPattern(value, cardIdentifiableInfo);
                            LogMgr.log(6, "001 : pattern=" + checkPattern.name());
                            boolean integrityCheckResult = getIntegrityCheckResult(checkPattern, mfiChipHolder, value.getCacheableData(), cardIdentifiableInfo);
                            LogMgr.log(6, "002 : match=" + integrityCheckResult);
                            CompleteCardInfo completeCardInfoChangeCardStatus = changeCardStatus(checkPattern, integrityCheckResult, value);
                            LogMgr.log(6, "003 : srcCard: " + value.dump());
                            LogMgr.log(6, "004 : dstCard: " + completeCardInfoChangeCardStatus.dump());
                            if (completeCardInfoChangeCardStatus.statusIs(1, 0)) {
                                iIntValue = map2.containsKey(completeCardInfoChangeCardStatus.getServiceId()) ? ((Integer) map2.get(completeCardInfoChangeCardStatus.getServiceId())).intValue() : 0;
                                if (iIntValue != 0) {
                                    LogMgr.log(2, "702 : active-foreground and " + iIntValue);
                                    throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                                }
                                map2.put(completeCardInfoChangeCardStatus.getServiceId(), Integer.valueOf(iIntValue | 1));
                            } else if (completeCardInfoChangeCardStatus.statusIs(1, 2)) {
                                iIntValue = map2.containsKey(completeCardInfoChangeCardStatus.getServiceId()) ? ((Integer) map2.get(completeCardInfoChangeCardStatus.getServiceId())).intValue() : 0;
                                if ((iIntValue & 1) != 0) {
                                    LogMgr.log(2, "703 : active-foreground and " + iIntValue);
                                    throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                                }
                                map2.put(completeCardInfoChangeCardStatus.getServiceId(), Integer.valueOf(iIntValue | 2));
                            }
                            linkedHashMap2.put(completeCardInfoChangeCardStatus.getCid(), completeCardInfoChangeCardStatus);
                        }
                    }
                    linkedHashMap2.put(completeCardInfo.getCid(), completeCardInfo);
                }
            }
        }
        return linkedHashMap2;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x01dc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.util.LinkedHashMap<java.lang.String, com.felicanetworks.mfc.mfi.CompleteCardInfo> updateCachedCardByCidMapInner(com.felicanetworks.mfc.mfi.MfiChipHolder r25, com.felicanetworks.mfc.mfi.DataManager r26, java.util.Map<java.lang.String, com.felicanetworks.mfc.mfi.CompleteCardInfo.Cache> r27, java.util.Map<java.lang.String, com.felicanetworks.mfc.mfi.CardIdentifiableInfo.Cache> r28) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 638
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.fws.CardIdentifiableInfoChecker.updateCachedCardByCidMapInner(com.felicanetworks.mfc.mfi.MfiChipHolder, com.felicanetworks.mfc.mfi.DataManager, java.util.Map, java.util.Map):java.util.LinkedHashMap");
    }

    private ReadSeResult getReadCiaBlockListInner(MfiChipHolder mfiChipHolder, CardIdentifiableInfo.Cache cache) throws Exception {
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(mfiChipHolder);
        try {
            mfiFelicaWrapper.open();
            ReadSeResult readSeResult = new ReadSeResult();
            ArrayList arrayList = new ArrayList();
            try {
                boolean zCheckArea = checkArea(mfiFelicaWrapper, true, cache.systemCode, cache.areaCode);
                readSeResult.setAvailableArea(zCheckArea);
                if (!zCheckArea || cache.blockInfoArray == null || cache.blockInfoArray.length == 0) {
                    readSeResult.setReadCiaBlockList(null);
                } else {
                    Data[] readCiaBlockDataArray = getReadCiaBlockDataArray(mfiFelicaWrapper, cache.blockInfoArray);
                    int length = cache.blockInfoArray.length;
                    ByteBuffer byteBuffer = new ByteBuffer(16);
                    for (int i = 0; i < length; i++) {
                        byteBuffer.setLength(0);
                        CardIdentifiableBlockData cardIdentifiableBlockData = new CardIdentifiableBlockData();
                        CardIdBlockInfo.Cache cache2 = cache.blockInfoArray[i];
                        cardIdentifiableBlockData.serviceCode = cache2.serviceCode;
                        cardIdentifiableBlockData.blockNumber = cache2.blockNumber;
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

    private Map<String, List<String>> getReadCiaMaskedBlockListBySidMap(MfiChipHolder mfiChipHolder, Collection<CardIdentifiableInfo> collection) throws Exception {
        LogMgr.log(6, "%s", "000");
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(mfiChipHolder);
        try {
            mfiFelicaWrapper.open();
            HashMap map = new HashMap();
            try {
                for (CardIdentifiableInfo cardIdentifiableInfo : collection) {
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

    private List<String> getReadCiaMaskedBlockList(MfiFelicaWrapper mfiFelicaWrapper, CardIdentifiableInfo cardIdentifiableInfo, boolean z) throws FwsException {
        if (z && !checkArea(mfiFelicaWrapper, true, cardIdentifiableInfo.systemCode, cardIdentifiableInfo.areaCode)) {
            return null;
        }
        Data[] readCiaBlockDataArray = getReadCiaBlockDataArray(mfiFelicaWrapper, cardIdentifiableInfo.getCacheableData().blockInfoArray);
        ArrayList arrayList = new ArrayList();
        int size = cardIdentifiableInfo.blockInfoList.size();
        ByteBuffer byteBuffer = new ByteBuffer(16);
        for (int i = 0; i < size; i++) {
            byteBuffer.setLength(0);
            DataUtil.getInstance().append(byteBuffer, readCiaBlockDataArray[i]);
            byte[] bArr = (byte[]) byteBuffer.getBytes().clone();
            byte[] bArr2 = cardIdentifiableInfo.blockInfoList.get(i).blockMask;
            if (bArr.length != bArr2.length) {
                LogMgr.log(2, "%s Data length is different. :ReadData=%d, MaskData=%d", "706", Integer.valueOf(bArr.length), Integer.valueOf(bArr2.length));
                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
            }
            LogMgr.log(7, "001 : read:");
            LogMgr.logArray(7, bArr);
            LogMgr.log(7, "002 : mask:");
            LogMgr.logArray(7, bArr2);
            byte[] bArr3 = new byte[bArr.length];
            boolean z2 = true;
            for (int i2 = 0; i2 < bArr.length; i2++) {
                if (bArr[i2] != 0) {
                    z2 = false;
                }
                bArr3[i2] = (byte) (bArr[i2] & bArr2[i2]);
            }
            if (z2) {
                LogMgr.log(6, "%s : ALL0.", "501");
                return null;
            }
            arrayList.add(StringUtil.bytesToHexString(bArr3));
        }
        LogMgr.log(6, "%s", "999");
        return arrayList;
    }

    private boolean checkArea(MfiFelicaWrapper mfiFelicaWrapper, boolean z, int i, int i2) throws FwsException {
        LogMgr.log(6, "%s", "000");
        if (i2 == -1) {
            LogMgr.log(2, "700 Area code is invalid value.");
            throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
        }
        if (z) {
            try {
                mfiFelicaWrapper.select(i);
            } catch (MfiFelicaException e) {
                LogMgr.log(2, "701 Felica#select : MfiFelicaException");
                throw new FwsException(e.getType(), e.getMessage());
            }
        }
        try {
            boolean zCheckNodeCodeExists = mfiFelicaWrapper.checkNodeCodeExists(i2);
            LogMgr.log(6, "999");
            return zCheckNodeCodeExists;
        } catch (MfiFelicaException e2) {
            LogMgr.log(2, "702 Felica#getKeyVersion or getKeyVersionV2 MfiFelicaException");
            LogMgr.printStackTrace(7, e2);
            throw new FwsException(e2.getType(), e2.getMessage());
        }
    }

    private boolean checkSystem(MfiFelicaWrapper mfiFelicaWrapper, CardIdentifiableInfo cardIdentifiableInfo) throws FwsException {
        LogMgr.log(6, "000");
        if (this.mSystemCodeList == null) {
            try {
                mfiFelicaWrapper.select(65039);
                this.mSystemCodeList = mfiFelicaWrapper.getSystemCodeList();
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
                if (iArr[i] == cardIdentifiableInfo.systemCode) {
                    z = true;
                    break;
                }
                i++;
            }
        }
        LogMgr.log(6, "999");
        return z;
    }

    private boolean checkIDm(MfiFelicaWrapper mfiFelicaWrapper, CompleteCardInfo.Cache cache) throws FwsException {
        LogMgr.log(6, "000");
        try {
            byte[] iDm = mfiFelicaWrapper.getIDm();
            LogMgr.log(6, "999");
            return Arrays.equals(cache.idm, iDm);
        } catch (MfiFelicaException e) {
            LogMgr.log(2, "700 Felica#getIDm : MfiFelicaException");
            LogMgr.printStackTrace(7, e);
            throw new FwsException(e.getType(), e.getMessage());
        }
    }

    private static boolean checkService(MfiFelicaWrapper mfiFelicaWrapper, CardIdentifiableInfo cardIdentifiableInfo) throws FwsException {
        LogMgr.log(6, "000");
        int[] iArr = new int[cardIdentifiableInfo.blockInfoList.size()];
        boolean z = false;
        for (int i = 0; i < cardIdentifiableInfo.blockInfoList.size(); i++) {
            iArr[i] = cardIdentifiableInfo.blockInfoList.get(i).serviceCode;
        }
        try {
            boolean[] zArrCheckNodeCodeListExists = mfiFelicaWrapper.checkNodeCodeListExists(iArr);
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

    private static boolean checkNode(MfiFelicaWrapper mfiFelicaWrapper, CompleteCardInfo.Cache cache) throws FwsException {
        LogMgr.log(6, "000");
        try {
            boolean[] zArrCheckNodeCodeListExists = mfiFelicaWrapper.checkNodeCodeListExists(cache.nodeCodeList);
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

    private static boolean checkCid(String str, String str2) {
        LogMgr.log(6, "000");
        if (CardInfo.CID_UNSUPPORT_MFI_SERVICE_1.equals(str)) {
            if (MfiClientConst.NO_CID_INSTANCE_KEY.equals(str2)) {
                LogMgr.log(6, "001 exist inidividual card.");
                return true;
            }
            LogMgr.log(6, "002 Not exist inidividual card.");
        }
        if (str.equals(str2)) {
            return true;
        }
        LogMgr.log(6, "999");
        return false;
    }

    private boolean checkCardIdInfo(MfiFelicaWrapper mfiFelicaWrapper, CompleteCardInfo.Cache cache, CardIdentifiableInfo cardIdentifiableInfo) throws FwsException {
        LogMgr.log(6, "000");
        List<String> list = cardIdentifiableInfo.maskedBlockListByCidMap.get(cache.cid);
        if (list == null) {
            LogMgr.log(2, "700 : No BlockData.");
            LogMgr.log(6, "500 : cid=" + cache.cid);
            throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
        }
        List<String> readCiaMaskedBlockList = getReadCiaMaskedBlockList(mfiFelicaWrapper, cardIdentifiableInfo, false);
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

    private Data[] getReadCiaBlockDataArray(MfiFelicaWrapper mfiFelicaWrapper, CardIdBlockInfo.Cache[] cacheArr) throws FwsException {
        LogMgr.log(6, "%s", "000");
        if (cacheArr.length == 0) {
            LogMgr.log(6, "001 ciaBlockInfoArray length = 0");
            return new Data[0];
        }
        BlockList blockList = new BlockList();
        for (CardIdBlockInfo.Cache cache : cacheArr) {
            blockList.add(new Block(cache.serviceCode, cache.blockNumber));
        }
        try {
            Data[] dataArr = mfiFelicaWrapper.read(blockList);
            if (dataArr == null) {
                LogMgr.log(2, "%s Read data is null.", "702");
                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
            }
            if (cacheArr.length != dataArr.length) {
                LogMgr.log(2, "%s Read data size is invalid. %d -> %d.", "703", Integer.valueOf(cacheArr.length), Integer.valueOf(dataArr.length));
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
