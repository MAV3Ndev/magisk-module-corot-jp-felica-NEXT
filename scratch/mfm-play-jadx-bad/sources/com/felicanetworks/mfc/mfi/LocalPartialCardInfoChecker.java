package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.fws.FwsException;
import com.felicanetworks.mfc.mfi.fws.IndividualSpChecker;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.AppletManager;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class LocalPartialCardInfoChecker {
    private final AtomicBoolean mIsCanceled = new AtomicBoolean(false);

    public void setCancel(boolean isCancel) {
        this.mIsCanceled.set(isCancel);
    }

    private void checkCancel() throws FelicaException {
        LogMgr.log(4, "000");
        if (this.mIsCanceled.get()) {
            throw new MfiClientException(3, MfiClientException.TYPE_MFI_OFFLINE_CANCELED, null);
        }
        LogMgr.log(4, "999");
    }

    public LocalPartialCardInfoJson[] getLocalPartialCardInfoList(String serviceId, MfiChipHolder chipHolder) throws FelicaException, IllegalArgumentException {
        return getLocalPartialCardInfoList(serviceId, false, chipHolder);
    }

    public LocalPartialCardInfoJson[] getLocalPartialCardInfoList(String[] serviceIdList, MfiChipHolder chipHolder) throws FelicaException, IllegalArgumentException {
        return getLocalPartialCardInfoList(serviceIdList, false, chipHolder);
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [226=4, 228=4] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0254 A[Catch: all -> 0x022b, TryCatch #4 {all -> 0x022b, blocks: (B:7:0x0022, B:10:0x0032, B:29:0x0107, B:50:0x016e, B:51:0x0174, B:35:0x0121, B:48:0x0165, B:78:0x0230, B:92:0x0254, B:93:0x0258, B:52:0x0175, B:54:0x017a, B:63:0x0190, B:65:0x019a, B:66:0x0210, B:68:0x021c, B:70:0x0224, B:69:0x0220, B:57:0x0183, B:59:0x0187, B:95:0x0288), top: B:107:0x0022, inners: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public LocalPartialCardInfoJson[] getLocalPartialCardInfoList(String serviceId, boolean isErrorTypeCallback, MfiChipHolder chipHolder) throws FelicaException, IllegalArgumentException {
        MfiClientException mfiClientException;
        int i;
        int i2;
        String message;
        boolean z;
        LocalPartialCardInfoJson[] localPartialCardInfoJsonArr;
        LocalPartialCardInfoJson[] localPartialCardInfoJsonArr2;
        int i3;
        int i4;
        int i5 = 4;
        LogMgr.log(4, "000");
        if (chipHolder == null) {
            LogMgr.log(2, "700 chipHolder is null.");
            throw new IllegalArgumentException();
        }
        FlavorConst.ServiceParam serviceParam = FlavorConst.SERVICE_ID_PREFERENCE_MAP_OLD.get(serviceId);
        if (serviceParam == null) {
            LogMgr.log(2, "702 : serviceId : " + serviceId);
            throw new MfiClientException(105, 171, null);
        }
        checkCancel();
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(chipHolder);
        try {
            try {
                try {
                } catch (JSONException e) {
                    LogMgr.log(2, "704 : localPartialCardInfo is illegal.");
                    LogMgr.printStackTrace(2, e);
                    mfiClientException = new MfiClientException(1, MfiClientException.TYPE_GET_LOCAL_PARTIAL_CARD_INFO_LIST_FAILED, null);
                }
                try {
                    if (Property.isChipGP()) {
                        mfiFelicaWrapper.close();
                        GpController gpController = new GpController();
                        FelicaAdapter felicaAdapter = FelicaAdapter.getInstance();
                        try {
                            try {
                                checkCancel();
                                gpController.init(felicaAdapter);
                                LocalPartialCardInfoJson[] localPartialCardInfoList = new AppletManager(gpController).getLocalPartialCardInfoList(serviceId, serviceParam);
                                checkCancel();
                                gpController.close();
                                ArrayList arrayList = new ArrayList();
                                int length = localPartialCardInfoList.length;
                                int i6 = 0;
                                while (i6 < length) {
                                    LocalPartialCardInfoJson localPartialCardInfoJson = localPartialCardInfoList[i6];
                                    if (localPartialCardInfoJson.getLocalPartialCardInfo().getCid() == null) {
                                        localPartialCardInfoJsonArr2 = localPartialCardInfoList;
                                        if (serviceParam == FlavorConst.ServiceParam.INDIVIDUAL_SP_1) {
                                            i3 = i6;
                                            i4 = length;
                                            if (judgeServiceProvider(chipHolder, false) == 1) {
                                            }
                                        } else {
                                            i3 = i6;
                                            i4 = length;
                                        }
                                        checkCancel();
                                        i6 = i3 + 1;
                                        length = i4;
                                        localPartialCardInfoList = localPartialCardInfoJsonArr2;
                                    } else {
                                        localPartialCardInfoJsonArr2 = localPartialCardInfoList;
                                        i3 = i6;
                                        i4 = length;
                                    }
                                    arrayList.add(localPartialCardInfoJson);
                                    LocalPartialCardInfo localPartialCardInfo = localPartialCardInfoJson.getLocalPartialCardInfo();
                                    LogMgr.log(6, "LocalPartialCardInfo: cid = " + localPartialCardInfo.getCid());
                                    LogMgr.log(6, "LocalPartialCardInfo: idm = " + localPartialCardInfo.getIDm());
                                    LogMgr.log(6, "LocalPartialCardInfo: service id = " + localPartialCardInfo.getServiceId());
                                    LogMgr.log(6, "LocalPartialCardInfo: card position = " + localPartialCardInfo.getCardPosition());
                                    checkCancel();
                                    i6 = i3 + 1;
                                    length = i4;
                                    localPartialCardInfoList = localPartialCardInfoJsonArr2;
                                }
                                localPartialCardInfoJsonArr = new LocalPartialCardInfoJson[arrayList.size()];
                                if (arrayList.isEmpty()) {
                                    LogMgr.log(6, "LocalPartialCardInfo is empty.");
                                } else {
                                    arrayList.toArray(localPartialCardInfoJsonArr);
                                }
                                checkCancel();
                                gpController.close();
                                mfiFelicaWrapper.close();
                            } catch (Throwable th) {
                                th = th;
                                gpController.close();
                                mfiFelicaWrapper.close();
                                throw th;
                            }
                        } catch (GpException e2) {
                            LogMgr.log(2, "703 : Catch GpException message =" + e2.getMessage());
                            String message2 = isErrorTypeCallback ? e2.getMessage() : null;
                            try {
                                mfiClientException = e2.getType() == 225 ? new MfiClientException(3, MfiClientException.TYPE_SE_ACCESS_ERROR, message2) : new MfiClientException(1, MfiClientException.TYPE_GET_LOCAL_PARTIAL_CARD_INFO_LIST_FAILED, message2);
                                gpController.close();
                                mfiFelicaWrapper.close();
                                throw mfiClientException;
                            } catch (Throwable th2) {
                                th = th2;
                                gpController.close();
                                mfiFelicaWrapper.close();
                                throw th;
                            }
                        } catch (InterruptedException unused) {
                            mfiClientException = new MfiClientException(3, MfiClientException.TYPE_MFI_OFFLINE_CANCELED, null);
                            gpController.close();
                            mfiFelicaWrapper.close();
                            throw mfiClientException;
                        }
                    } else {
                        if (serviceParam == FlavorConst.ServiceParam.INDIVIDUAL_SP_1) {
                            z = judgeServiceProvider(chipHolder) == 1;
                        } else {
                            if (serviceParam == FlavorConst.ServiceParam.INDIVIDUAL_SP_2 && judgeServiceProvider(chipHolder) == 2) {
                            }
                        }
                        ArrayList arrayList2 = new ArrayList();
                        checkCancel();
                        if (z) {
                            mfiFelicaWrapper.open();
                            mfiFelicaWrapper.select(serviceParam.systemCode);
                            String strBytesToHexString = StringUtil.bytesToHexString(mfiFelicaWrapper.getIDm());
                            mfiFelicaWrapper.close();
                            LocalPartialCardInfoJson localPartialCardInfoJson2 = new LocalPartialCardInfoJson(new LocalPartialCardInfo(null, strBytesToHexString, 0, serviceId));
                            arrayList2.add(localPartialCardInfoJson2);
                            LocalPartialCardInfo localPartialCardInfo2 = localPartialCardInfoJson2.getLocalPartialCardInfo();
                            LogMgr.log(6, "LocalPartialCardInfo: cid = " + localPartialCardInfo2.getCid());
                            LogMgr.log(6, "LocalPartialCardInfo: idm = " + localPartialCardInfo2.getIDm());
                            LogMgr.log(6, "LocalPartialCardInfo: service id = " + localPartialCardInfo2.getServiceId());
                            LogMgr.log(6, "LocalPartialCardInfo: card position = " + localPartialCardInfo2.getCardPosition());
                        }
                        localPartialCardInfoJsonArr = new LocalPartialCardInfoJson[arrayList2.size()];
                        if (arrayList2.isEmpty()) {
                            LogMgr.log(6, "LocalPartialCardInfo is empty.");
                        } else {
                            arrayList2.toArray(localPartialCardInfoJsonArr);
                        }
                        checkCancel();
                    }
                    return localPartialCardInfoJsonArr;
                } catch (MfiFelicaException e3) {
                    e = e3;
                    int type = e.getType();
                    if (type != 6) {
                        i = 8;
                        if (type != 8) {
                            int i7 = 31;
                            if (type != 31) {
                                i7 = 55;
                                if (type != 55) {
                                    message = null;
                                    i = 1;
                                    i2 = MfiClientException.TYPE_GET_LOCAL_PARTIAL_CARD_INFO_LIST_FAILED;
                                }
                            }
                            i2 = i7;
                        } else {
                            message = FelicaException.NFC_RW_USED_MESSAGE;
                            i2 = 8;
                            i = 1;
                        }
                        if (isErrorTypeCallback) {
                            message = e.getMessage();
                        }
                        LogMgr.log(2, "705 : " + e.getClass().getSimpleName() + " : " + e.getMessage());
                        mfiClientException = new MfiClientException(i, i2, message);
                        throw mfiClientException;
                    }
                    i = i5;
                    i2 = 6;
                    message = null;
                    if (isErrorTypeCallback) {
                    }
                    LogMgr.log(2, "705 : " + e.getClass().getSimpleName() + " : " + e.getMessage());
                    mfiClientException = new MfiClientException(i, i2, message);
                    throw mfiClientException;
                }
            } catch (MfiFelicaException e4) {
                e = e4;
                i5 = 3;
            }
        } finally {
            mfiFelicaWrapper.closeSilently();
            LogMgr.log(4, "999");
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [461=4, 463=4] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x02c6 A[Catch: all -> 0x029d, TryCatch #9 {all -> 0x029d, blocks: (B:13:0x0050, B:16:0x0060, B:60:0x01af, B:97:0x02a2, B:111:0x02c6, B:112:0x02ca, B:65:0x01bb, B:66:0x01c1, B:46:0x0169, B:36:0x0149, B:67:0x01c2, B:75:0x01d8, B:76:0x01e0, B:78:0x01e6, B:80:0x01f2, B:82:0x01fa, B:84:0x0204, B:85:0x0282, B:87:0x028e, B:89:0x0296, B:88:0x0292, B:72:0x01d1, B:73:0x01d4, B:114:0x02fa), top: B:126:0x0050, inners: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0204 A[Catch: MfiFelicaException -> 0x029b, all -> 0x029d, JSONException -> 0x02f9, TryCatch #5 {JSONException -> 0x02f9, blocks: (B:13:0x0050, B:16:0x0060, B:60:0x01af, B:65:0x01bb, B:66:0x01c1, B:46:0x0169, B:36:0x0149, B:67:0x01c2, B:75:0x01d8, B:76:0x01e0, B:78:0x01e6, B:80:0x01f2, B:82:0x01fa, B:84:0x0204, B:85:0x0282, B:87:0x028e, B:89:0x0296, B:88:0x0292, B:72:0x01d1, B:73:0x01d4), top: B:122:0x0050, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x028e A[Catch: MfiFelicaException -> 0x029b, all -> 0x029d, JSONException -> 0x02f9, TryCatch #5 {JSONException -> 0x02f9, blocks: (B:13:0x0050, B:16:0x0060, B:60:0x01af, B:65:0x01bb, B:66:0x01c1, B:46:0x0169, B:36:0x0149, B:67:0x01c2, B:75:0x01d8, B:76:0x01e0, B:78:0x01e6, B:80:0x01f2, B:82:0x01fa, B:84:0x0204, B:85:0x0282, B:87:0x028e, B:89:0x0296, B:88:0x0292, B:72:0x01d1, B:73:0x01d4), top: B:122:0x0050, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0292 A[Catch: MfiFelicaException -> 0x029b, all -> 0x029d, JSONException -> 0x02f9, TryCatch #5 {JSONException -> 0x02f9, blocks: (B:13:0x0050, B:16:0x0060, B:60:0x01af, B:65:0x01bb, B:66:0x01c1, B:46:0x0169, B:36:0x0149, B:67:0x01c2, B:75:0x01d8, B:76:0x01e0, B:78:0x01e6, B:80:0x01f2, B:82:0x01fa, B:84:0x0204, B:85:0x0282, B:87:0x028e, B:89:0x0296, B:88:0x0292, B:72:0x01d1, B:73:0x01d4), top: B:122:0x0050, outer: #9 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public LocalPartialCardInfoJson[] getLocalPartialCardInfoList(String[] serviceIdList, boolean isErrorTypeCallback, MfiChipHolder chipHolder) throws FelicaException, IllegalArgumentException {
        MfiClientException mfiClientException;
        int i;
        int i2;
        String message;
        GpController gpController;
        GpController gpController2;
        FelicaAdapter felicaAdapter;
        LocalPartialCardInfoJson[] localPartialCardInfoJsonArr;
        LocalPartialCardInfoJson[] localPartialCardInfoJsonArr2;
        HashMap map;
        String str;
        ArrayList arrayList;
        int i3 = 4;
        LogMgr.log(4, "000");
        if (chipHolder == null) {
            LogMgr.log(2, "700 chipHolder is null.");
            throw new IllegalArgumentException();
        }
        HashMap map2 = new HashMap();
        for (String str2 : serviceIdList) {
            FlavorConst.ServiceParam serviceParam = FlavorConst.SERVICE_ID_PREFERENCE_MAP.get(str2);
            if (serviceParam == null) {
                LogMgr.log(2, "701 : serviceId : " + str2);
                throw new MfiClientException(105, 171, null);
            }
            map2.put(str2, serviceParam);
        }
        checkCancel();
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(chipHolder);
        try {
            try {
                try {
                } catch (MfiFelicaException e) {
                    e = e;
                    i3 = 3;
                }
            } catch (JSONException e2) {
                LogMgr.log(2, "703 : localPartialCardInfo is illegal.");
                LogMgr.printStackTrace(2, e2);
                mfiClientException = new MfiClientException(1, MfiClientException.TYPE_GET_LOCAL_PARTIAL_CARD_INFO_LIST_FAILED, null);
            }
            try {
                if (Property.isChipGP()) {
                    try {
                        mfiFelicaWrapper.close();
                        gpController2 = new GpController();
                        felicaAdapter = FelicaAdapter.getInstance();
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        checkCancel();
                        gpController2.init(felicaAdapter);
                        LocalPartialCardInfoJson[] localPartialCardInfoList = new AppletManager(gpController2).getLocalPartialCardInfoList(map2);
                        checkCancel();
                        gpController2.close();
                        ArrayList arrayList2 = new ArrayList();
                        int length = localPartialCardInfoList.length;
                        int i4 = 0;
                        while (i4 < length) {
                            LocalPartialCardInfoJson localPartialCardInfoJson = localPartialCardInfoList[i4];
                            try {
                                if (localPartialCardInfoJson.getLocalPartialCardInfo().getCid() == null) {
                                    localPartialCardInfoJsonArr2 = localPartialCardInfoList;
                                    map = map2;
                                    if (((FlavorConst.ServiceParam) map2.get(localPartialCardInfoJson.getLocalPartialCardInfo().getServiceId())) == FlavorConst.ServiceParam.INDIVIDUAL_SP_1) {
                                        gpController = gpController2;
                                        if (judgeServiceProvider(chipHolder, false) == 1) {
                                        }
                                    } else {
                                        gpController = gpController2;
                                    }
                                    checkCancel();
                                    i4++;
                                    map2 = map;
                                    localPartialCardInfoList = localPartialCardInfoJsonArr2;
                                    gpController2 = gpController;
                                } else {
                                    localPartialCardInfoJsonArr2 = localPartialCardInfoList;
                                    map = map2;
                                    gpController = gpController2;
                                }
                                arrayList2.add(localPartialCardInfoJson);
                                LocalPartialCardInfo localPartialCardInfo = localPartialCardInfoJson.getLocalPartialCardInfo();
                                LogMgr.log(6, "LocalPartialCardInfo: cid = " + localPartialCardInfo.getCid());
                                LogMgr.log(6, "LocalPartialCardInfo: idm = " + localPartialCardInfo.getIDm());
                                LogMgr.log(6, "LocalPartialCardInfo: service id = " + localPartialCardInfo.getServiceId());
                                LogMgr.log(6, "LocalPartialCardInfo: card position = " + localPartialCardInfo.getCardPosition());
                                checkCancel();
                                i4++;
                                map2 = map;
                                localPartialCardInfoList = localPartialCardInfoJsonArr2;
                                gpController2 = gpController;
                            } catch (GpException e3) {
                                e = e3;
                                LogMgr.log(2, "702 : Catch GpException message =" + e.getMessage());
                                String message2 = isErrorTypeCallback ? e.getMessage() : null;
                                try {
                                    mfiClientException = e.getType() == 225 ? new MfiClientException(3, MfiClientException.TYPE_SE_ACCESS_ERROR, message2) : new MfiClientException(1, MfiClientException.TYPE_GET_LOCAL_PARTIAL_CARD_INFO_LIST_FAILED, message2);
                                    gpController.close();
                                    mfiFelicaWrapper.close();
                                    throw mfiClientException;
                                } catch (Throwable th2) {
                                    th = th2;
                                    gpController.close();
                                    mfiFelicaWrapper.close();
                                    throw th;
                                }
                            } catch (InterruptedException unused) {
                                mfiClientException = new MfiClientException(3, MfiClientException.TYPE_MFI_OFFLINE_CANCELED, null);
                                gpController.close();
                                mfiFelicaWrapper.close();
                                throw mfiClientException;
                            }
                        }
                        gpController = gpController2;
                        localPartialCardInfoJsonArr = new LocalPartialCardInfoJson[arrayList2.size()];
                        if (arrayList2.isEmpty()) {
                            LogMgr.log(6, "LocalPartialCardInfo is empty.");
                        } else {
                            arrayList2.toArray(localPartialCardInfoJsonArr);
                        }
                        checkCancel();
                        gpController.close();
                        mfiFelicaWrapper.close();
                    } catch (GpException e4) {
                        e = e4;
                        gpController = gpController2;
                    } catch (InterruptedException unused2) {
                        gpController = gpController2;
                    } catch (Throwable th3) {
                        th = th3;
                        gpController = gpController2;
                        gpController.close();
                        mfiFelicaWrapper.close();
                        throw th;
                    }
                } else {
                    int iJudgeServiceProvider = judgeServiceProvider(chipHolder);
                    FlavorConst.ServiceParam serviceParam2 = iJudgeServiceProvider != 1 ? iJudgeServiceProvider != 2 ? null : FlavorConst.ServiceParam.INDIVIDUAL_SP_2 : FlavorConst.ServiceParam.INDIVIDUAL_SP_1;
                    if (serviceParam2 != null) {
                        for (Map.Entry entry : map2.entrySet()) {
                            if (serviceParam2 == entry.getValue()) {
                                str = (String) entry.getKey();
                                break;
                            }
                        }
                        str = null;
                        checkCancel();
                        arrayList = new ArrayList();
                        if (str != null) {
                            mfiFelicaWrapper.open();
                            mfiFelicaWrapper.select(serviceParam2.systemCode);
                            LocalPartialCardInfo localPartialCardInfo2 = new LocalPartialCardInfo(null, StringUtil.bytesToHexString(mfiFelicaWrapper.getIDm()), 0, str);
                            arrayList.add(new LocalPartialCardInfoJson(localPartialCardInfo2));
                            LogMgr.log(6, "LocalPartialCardInfo: cid = " + localPartialCardInfo2.getCid());
                            LogMgr.log(6, "LocalPartialCardInfo: idm = " + localPartialCardInfo2.getIDm());
                            LogMgr.log(6, "LocalPartialCardInfo: service id = " + localPartialCardInfo2.getServiceId());
                            LogMgr.log(6, "LocalPartialCardInfo: card position = " + localPartialCardInfo2.getCardPosition());
                            mfiFelicaWrapper.close();
                        }
                        localPartialCardInfoJsonArr = new LocalPartialCardInfoJson[arrayList.size()];
                        if (arrayList.isEmpty()) {
                            arrayList.toArray(localPartialCardInfoJsonArr);
                        } else {
                            LogMgr.log(6, "LocalPartialCardInfo is empty.");
                        }
                        checkCancel();
                    } else {
                        str = null;
                        checkCancel();
                        arrayList = new ArrayList();
                        if (str != null) {
                        }
                        localPartialCardInfoJsonArr = new LocalPartialCardInfoJson[arrayList.size()];
                        if (arrayList.isEmpty()) {
                        }
                        checkCancel();
                    }
                }
                return localPartialCardInfoJsonArr;
            } catch (MfiFelicaException e5) {
                e = e5;
                int type = e.getType();
                if (type != 6) {
                    i = 8;
                    if (type != 8) {
                        int i5 = 31;
                        if (type != 31) {
                            i5 = 55;
                            if (type != 55) {
                                i = 1;
                                message = null;
                                i2 = MfiClientException.TYPE_GET_LOCAL_PARTIAL_CARD_INFO_LIST_FAILED;
                            }
                        }
                        i2 = i5;
                    } else {
                        message = FelicaException.NFC_RW_USED_MESSAGE;
                        i2 = 8;
                        i = 1;
                    }
                    if (isErrorTypeCallback) {
                        message = e.getMessage();
                    }
                    LogMgr.log(2, "704 : " + e.getClass().getSimpleName() + " : " + e.getMessage());
                    mfiClientException = new MfiClientException(i, i2, message);
                    throw mfiClientException;
                }
                i = i3;
                i2 = 6;
                message = null;
                if (isErrorTypeCallback) {
                }
                LogMgr.log(2, "704 : " + e.getClass().getSimpleName() + " : " + e.getMessage());
                mfiClientException = new MfiClientException(i, i2, message);
                throw mfiClientException;
            }
        } finally {
            mfiFelicaWrapper.closeSilently();
            LogMgr.log(4, "999");
        }
    }

    private int judgeServiceProvider(MfiChipHolder chipHolder) throws FelicaException {
        return judgeServiceProvider(chipHolder, true);
    }

    private int judgeServiceProvider(MfiChipHolder chipHolder, boolean needCheckExistSystem) throws FelicaException {
        int i;
        int i2;
        try {
            return IndividualSpChecker.identifyService(chipHolder, 3, 72, 74, FlavorConst.INDIVIDUAL_SP_NODE_CODE_SERVICE1, FlavorConst.INDIVIDUAL_SP_NODE_CODE_SERVICE2, false, needCheckExistSystem);
        } catch (FwsException e) {
            String message = e.getMessage();
            int type = e.getType();
            if (type != 6) {
                if (type != 8) {
                    i = 31;
                    if (type != 31) {
                        i = 55;
                        i2 = type != 55 ? MfiClientException.TYPE_IDENTIFY_SERVICE_FAILED : 8;
                    }
                } else {
                    message = FelicaException.NFC_RW_USED_MESSAGE;
                }
                i = i2;
                i2 = 1;
            } else {
                i = 6;
                i2 = 3;
            }
            LogMgr.log(2, "700 : " + e.getClass().getSimpleName() + " : " + e.getMessage());
            throw new MfiClientException(i2, i, message);
        }
    }
}
