package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiControlInfoCache;
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

/* JADX INFO: loaded from: classes.dex */
public class IndividualSpChecker {
    public static boolean checkIndividualApplicationId(String str) throws FwsException {
        LogMgr.log(6, "000");
        try {
            List<String> walletAppIdAppIdList = new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getWalletAppIdAppIdList();
            LogMgr.log(6, "999");
            return walletAppIdAppIdList.contains(str);
        } catch (JSONException unused) {
            LogMgr.log(2, "700 failed to parse MfiControlInfoCache data.");
            throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
        }
    }

    public static List<String>[] separateByMigrationState(String str, Map<String, CompleteCardInfo> map, Collection<CompleteCardInfo> collection) {
        boolean z;
        LogMgr.log(6, "000");
        ArrayList[] arrayListArr = {new ArrayList(), new ArrayList()};
        for (CompleteCardInfo completeCardInfo : map.values()) {
            Iterator<CompleteCardInfo> it = collection.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                CompleteCardInfo next = it.next();
                if (str.equalsIgnoreCase(next.getSeId()) && completeCardInfo.getServiceId().equals(next.getServiceId())) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                LogMgr.log(6, "001");
                arrayListArr[0].add(completeCardInfo.getCid());
            } else {
                LogMgr.log(6, "002");
                arrayListArr[1].add(completeCardInfo.getServiceId());
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

    public static int identifyService(MfiChipHolder mfiChipHolder, int i, int i2, int i3, int i4, int i5) throws FwsException {
        LogMgr.log(6, "000");
        int iIdentifyService = identifyService(mfiChipHolder, i, i2, i3, i4, i5, true);
        LogMgr.log(6, "999");
        return iIdentifyService;
    }

    public static int identifyService(MfiChipHolder mfiChipHolder, int i, int i2, int i3, int i4, int i5, boolean z) throws FwsException {
        return identifyService(mfiChipHolder, i, i2, i3, i4, i5, z, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f A[Catch: all -> 0x0084, MfiFelicaException -> 0x0086, TryCatch #0 {MfiFelicaException -> 0x0086, blocks: (B:3:0x000c, B:5:0x0013, B:7:0x001f, B:9:0x0023, B:15:0x002f, B:17:0x0036, B:12:0x0029, B:21:0x003f, B:23:0x0058, B:24:0x005b, B:26:0x0065), top: B:49:0x000c, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0070  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int identifyService(com.felicanetworks.mfc.mfi.MfiChipHolder r7, int r8, int r9, int r10, int r11, int r12, boolean r13, boolean r14) throws com.felicanetworks.mfc.mfi.fws.FwsException {
        /*
            r0 = 6
            java.lang.String r1 = "000"
            com.felicanetworks.mfc.util.LogMgr.log(r0, r1)
            com.felicanetworks.mfc.mfi.MfiFelicaWrapper r1 = new com.felicanetworks.mfc.mfi.MfiFelicaWrapper
            r1.<init>(r7)
            r7 = 2
            r1.open()     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            r2 = 0
            r3 = 1
            if (r14 == 0) goto L3f
            r14 = 65039(0xfe0f, float:9.1139E-41)
            r1.select(r14)     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            int[] r14 = r1.getSystemCodeList()     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            if (r14 == 0) goto L2c
            int r4 = r14.length     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            r5 = 0
        L21:
            if (r5 >= r4) goto L2c
            r6 = r14[r5]     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            if (r6 != r8) goto L29
            r14 = 1
            goto L2d
        L29:
            int r5 = r5 + 1
            goto L21
        L2c:
            r14 = 0
        L2d:
            if (r14 != 0) goto L3f
            java.lang.String r8 = "001"
            com.felicanetworks.mfc.util.LogMgr.log(r0, r8)     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            if (r13 == 0) goto L39
            r1.close()     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
        L39:
            if (r13 == 0) goto L3e
            r1.closeSilently()
        L3e:
            return r2
        L3f:
            r1.select(r8)     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            r8 = 4
            int[] r8 = new int[r8]     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            r8[r2] = r9     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            r8[r3] = r10     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            r8[r7] = r11     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            r9 = 3
            r8[r9] = r12     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            com.felicanetworks.mfc.mfi.MfiFelicaWrapper$ResultNodeCodeListInformation r8 = r1.checkNodeCodeListExistsWithCheckKeys(r8)     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            boolean[] r10 = r8.getNodeCodeExists()     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            if (r13 == 0) goto L5b
            r1.close()     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
        L5b:
            boolean r11 = r10[r2]     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            boolean r12 = r10[r3]     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            boolean r14 = r10[r7]     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            boolean r9 = r10[r9]     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            if (r11 != 0) goto L6e
            boolean r7 = r8.isServiceProvider1()     // Catch: java.lang.Throwable -> L84 com.felicanetworks.mfc.mfi.MfiFelicaException -> L86
            if (r7 == 0) goto L6c
            goto L70
        L6c:
            r7 = 0
            goto L79
        L6e:
            if (r12 != 0) goto L72
        L70:
            r7 = 1
            goto L79
        L72:
            if (r14 != r3) goto L75
            goto L70
        L75:
            if (r9 != r3) goto L78
            goto L79
        L78:
            r7 = -1
        L79:
            if (r13 == 0) goto L7e
            r1.closeSilently()
        L7e:
            java.lang.String r8 = "999"
            com.felicanetworks.mfc.util.LogMgr.log(r0, r8)
            return r7
        L84:
            r7 = move-exception
            goto L9a
        L86:
            r8 = move-exception
            java.lang.String r9 = "700 MfiFelicaException"
            com.felicanetworks.mfc.util.LogMgr.log(r7, r9)     // Catch: java.lang.Throwable -> L84
            com.felicanetworks.mfc.mfi.fws.FwsException r7 = new com.felicanetworks.mfc.mfi.fws.FwsException     // Catch: java.lang.Throwable -> L84
            int r9 = r8.getType()     // Catch: java.lang.Throwable -> L84
            java.lang.String r8 = r8.getMessage()     // Catch: java.lang.Throwable -> L84
            r7.<init>(r9, r8)     // Catch: java.lang.Throwable -> L84
            throw r7     // Catch: java.lang.Throwable -> L84
        L9a:
            if (r13 == 0) goto L9f
            r1.closeSilently()
        L9f:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.fws.IndividualSpChecker.identifyService(com.felicanetworks.mfc.mfi.MfiChipHolder, int, int, int, int, int, boolean, boolean):int");
    }
}
