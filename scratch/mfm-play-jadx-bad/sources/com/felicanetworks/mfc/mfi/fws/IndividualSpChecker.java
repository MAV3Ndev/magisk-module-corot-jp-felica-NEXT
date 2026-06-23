package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiControlInfoCache;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.UnsupportedMfiService1CardCache;
import com.felicanetworks.mfc.mfi.fws.json.GetMfiControlInfoResponseJson;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.AppletManager;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.omapi.SeAppletInfo;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class IndividualSpChecker {
    public static boolean checkIndividualApplicationId(String applicationId) throws FwsException {
        LogMgr.log(6, "000");
        try {
            List<String> walletAppIdAppIdList = new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getWalletAppIdAppIdList();
            LogMgr.log(6, "999");
            return walletAppIdAppIdList.contains(applicationId);
        } catch (JSONException unused) {
            LogMgr.log(2, "700 failed to parse MfiControlInfoCache data.");
            throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
        }
    }

    public static List<String>[] separateByMigrationState(String seId, Map<String, CompleteCardInfo> individualSpCardByCidMap, Collection<CompleteCardInfo> fwsCardList) {
        LogMgr.log(6, "000");
        ArrayList[] arrayListArr = {new ArrayList(), new ArrayList()};
        for (CompleteCardInfo completeCardInfo : individualSpCardByCidMap.values()) {
            Iterator<CompleteCardInfo> it = fwsCardList.iterator();
            while (true) {
                if (it.hasNext()) {
                    CompleteCardInfo next = it.next();
                    if (seId.equalsIgnoreCase(next.getSeId()) && completeCardInfo.getServiceId().equals(next.getServiceId())) {
                        LogMgr.log(6, "002");
                        arrayListArr[1].add(completeCardInfo.getServiceId());
                        break;
                    }
                } else {
                    LogMgr.log(6, "001");
                    arrayListArr[0].add(completeCardInfo.getCid());
                    break;
                }
            }
        }
        LogMgr.log(6, "999");
        return arrayListArr;
    }

    public static List<CompleteCardInfo> acquireIndividualSpCards(MfiChipHolder mfiChipHolder, Map<String, CompleteCardInfo> map, Map<String, CardIdentifiableInfo> map2, List<String> list, String str, String str2) throws InterruptedException, FwsException, GpException {
        LogMgr.log(6, "000");
        ArrayList arrayList = new ArrayList();
        if (list.isEmpty()) {
            LogMgr.log(6, "001");
            return arrayList;
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            CompleteCardInfo completeCardInfo = map.get(it.next());
            if (completeCardInfo.getServiceId().equals(str2)) {
                if (map2.get(completeCardInfo.getServiceId()) == null) {
                    LogMgr.log(2, "700 failed to load ciaInfo. serviceId = " + completeCardInfo.getServiceId());
                    throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                }
                if (identifyService(mfiChipHolder, 3, 72, 74, FlavorConst.INDIVIDUAL_SP_NODE_CODE_SERVICE1, FlavorConst.INDIVIDUAL_SP_NODE_CODE_SERVICE2) == 1) {
                    LogMgr.log(6, "002");
                    completeCardInfo.setSpType(CompleteCardInfo.SpType.INDIVIDUAL_SP);
                    completeCardInfo.setSeId(str);
                    if (Property.isChipGP()) {
                        SeAppletInfo individualSpCardAppletInfo = new AppletManager(mfiChipHolder.getGpController()).getIndividualSpCardAppletInfo();
                        if (individualSpCardAppletInfo == null) {
                            LogMgr.log(2, "701 failed to get appletInfo. cid = ALL_00");
                            throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                        }
                        completeCardInfo.setAppletInstanceAid(StringUtil.bytesToHexString(individualSpCardAppletInfo.getAid()));
                        int i = !individualSpCardAppletInfo.isActivated() ? 1 : 0;
                        completeCardInfo.setIdm(individualSpCardAppletInfo.getIdm());
                        completeCardInfo.setPosition(i);
                    }
                    arrayList.add(completeCardInfo);
                }
            }
        }
        LogMgr.log(6, "999");
        return arrayList;
    }

    public static int identifyService(MfiChipHolder chipHolder, int identifySystemCode, int firstNodeCodeService1, int endNodeCodeService1, int nodeCodeService1, int nodeCodeService2) throws FwsException {
        LogMgr.log(6, "000");
        int iIdentifyService = identifyService(chipHolder, identifySystemCode, firstNodeCodeService1, endNodeCodeService1, nodeCodeService1, nodeCodeService2, true);
        LogMgr.log(6, "999");
        return iIdentifyService;
    }

    public static int identifyService(MfiChipHolder chipHolder, int identifySystemCode, int firstNodeCodeService1, int endNodeCodeService1, int nodeCodeService1, int nodeCodeService2, boolean needClose) throws FwsException {
        return identifyService(chipHolder, identifySystemCode, firstNodeCodeService1, endNodeCodeService1, nodeCodeService1, nodeCodeService2, needClose, true);
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    public static int identifyService(MfiChipHolder chipHolder, int identifySystemCode, int firstNodeCodeService1, int endNodeCodeService1, int nodeCodeService1, int nodeCodeService2, boolean needClose, boolean needCheckExistSystem) throws FwsException {
        LogMgr.log(6, "000");
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(chipHolder);
        int i = 2;
        try {
            try {
                mfiFelicaWrapper.open();
                if (needCheckExistSystem) {
                    mfiFelicaWrapper.select(65039);
                    int[] systemCodeList = mfiFelicaWrapper.getSystemCodeList();
                    if (systemCodeList != null) {
                        for (int i2 : systemCodeList) {
                            if (i2 != identifySystemCode) {
                            }
                        }
                    }
                    LogMgr.log(6, "001");
                    UnsupportedMfiService1CardCache.getInstance().cacheNotExistUnsupportedMfiService1Card();
                    if (needClose) {
                        mfiFelicaWrapper.close();
                    }
                    return 0;
                }
                mfiFelicaWrapper.select(identifySystemCode);
                MfiFelicaWrapper.ResultNodeCodeListInformation resultNodeCodeListInformationCheckNodeCodeListExistsWithCheckKeys = mfiFelicaWrapper.checkNodeCodeListExistsWithCheckKeys(new int[]{firstNodeCodeService1, endNodeCodeService1, nodeCodeService1, nodeCodeService2});
                boolean[] nodeCodeExists = resultNodeCodeListInformationCheckNodeCodeListExistsWithCheckKeys.getNodeCodeExists();
                if (needClose) {
                    mfiFelicaWrapper.close();
                }
                boolean z = nodeCodeExists[0];
                boolean z2 = nodeCodeExists[1];
                boolean z3 = nodeCodeExists[2];
                boolean z4 = nodeCodeExists[3];
                if (z) {
                    if (!z2 || z3) {
                        i = 1;
                    } else if (z4) {
                        UnsupportedMfiService1CardCache.getInstance().cacheNotExistUnsupportedMfiService1Card();
                    } else {
                        i = -1;
                    }
                } else if (resultNodeCodeListInformationCheckNodeCodeListExistsWithCheckKeys.isServiceProvider1()) {
                    i = 1;
                } else {
                    UnsupportedMfiService1CardCache.getInstance().cacheNotExistUnsupportedMfiService1Card();
                    i = 0;
                }
                if (needClose) {
                    mfiFelicaWrapper.closeSilently();
                }
                LogMgr.log(6, "999 ret = " + i);
                return i;
            } catch (MfiFelicaException e) {
                LogMgr.log(2, "700 MfiFelicaException");
                throw new FwsException(e.getType(), e.getMessage());
            }
        } finally {
            if (needClose) {
                mfiFelicaWrapper.closeSilently();
            }
        }
    }
}
