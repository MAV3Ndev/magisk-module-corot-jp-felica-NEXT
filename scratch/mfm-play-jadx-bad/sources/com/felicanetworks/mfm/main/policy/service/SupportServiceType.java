package com.felicanetworks.mfm.main.policy.service;

import android.text.TextUtils;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.Service;
import com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public enum SupportServiceType {
    A1(null, "UU", null, Arrays.asList(MyServiceInfo.DetectionType.AREA, MyServiceInfo.DetectionType.APP)),
    A2(null, "UU", null, Arrays.asList(MyServiceInfo.DetectionType.AREA)),
    B1(null, "UU", null, Arrays.asList(MyServiceInfo.DetectionType.SAS, MyServiceInfo.DetectionType.APP)),
    B2(null, "UU", null, Arrays.asList(MyServiceInfo.DetectionType.SAS)),
    C1(null, "UU", null, Arrays.asList(MyServiceInfo.DetectionType.APP)),
    F1(FeliCaParams.SERVICE_ID_FP, "UU", null, Arrays.asList(MyServiceInfo.DetectionType.AREA)),
    M1(null, Service.SERVICE_TYPE_2_SIMPLE, null, Arrays.asList(MyServiceInfo.DetectionType.AREA, MyServiceInfo.DetectionType.APP)),
    M2(null, Service.SERVICE_TYPE_2_SIMPLE, null, Arrays.asList(MyServiceInfo.DetectionType.AREA)),
    T1(null, "MFPLS2", FeliCaParams.CARD_CATEGORY_TRAFFIC, Arrays.asList(MyServiceInfo.DetectionType.AREA, MyServiceInfo.DetectionType.APP)),
    S1(FeliCaParams.SERVICE_ID_SUICA, null, null, Arrays.asList(MyServiceInfo.DetectionType.AREA, MyServiceInfo.DetectionType.APP)),
    US(null, null, null, null);

    public static final List<String> excludeServiceIdList = Arrays.asList(FeliCaParams.SERVICE_ID_SUICA, FeliCaParams.SERVICE_ID_FP);
    public final String cardCategory;
    public final List<MyServiceInfo.DetectionType> detectionTypeList;
    public final String serviceId;
    public final String serviceType;
    public final List<String> targetIds = new ArrayList();

    SupportServiceType(String serviceId, String serviceType, String cardCategory, List detectionTypeList) {
        this.serviceId = serviceId;
        this.serviceType = serviceType;
        this.cardCategory = cardCategory;
        this.detectionTypeList = detectionTypeList;
    }

    public static SupportServiceType resolve(String sid) {
        for (SupportServiceType supportServiceType : values()) {
            if (supportServiceType.targetIds.contains(sid)) {
                return supportServiceType;
            }
        }
        return US;
    }

    public static SupportServiceType resolve(List<String> sids) {
        Iterator<String> it = sids.iterator();
        while (it.hasNext()) {
            SupportServiceType supportServiceTypeResolve = resolve(it.next());
            if (supportServiceTypeResolve != US) {
                return supportServiceTypeResolve;
            }
        }
        return US;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void inject(List<Service> services, List<String> areaFamily, List<String> appFamily, List<String> sasFamily, List<String> uiccFamily) {
        List<MyServiceInfo.DetectionType> list;
        String strSubstring;
        clearTargetIds();
        for (Service service : services) {
            for (SupportServiceType supportServiceType : values()) {
                if (!supportServiceType.equals(US)) {
                    if ((TextUtils.isEmpty(supportServiceType.serviceId) || supportServiceType.serviceId.equals(service.getServiceId())) && (!TextUtils.isEmpty(supportServiceType.serviceId) || !excludeServiceIdList.contains(service.getServiceId()))) {
                        if (!TextUtils.isEmpty(supportServiceType.serviceType)) {
                            if (TextUtils.isEmpty(service.getServiceType())) {
                                strSubstring = "UU";
                            } else {
                                strSubstring = service.getServiceType().substring(0, supportServiceType.serviceType.length());
                            }
                            if (!supportServiceType.serviceType.equals(strSubstring)) {
                                continue;
                            }
                        } else if ((TextUtils.isEmpty(supportServiceType.cardCategory) || supportServiceType.cardCategory.equals(service.getCardCategory())) && ((list = supportServiceType.detectionTypeList) == null || ((!list.contains(MyServiceInfo.DetectionType.AREA) || areaFamily.contains(service.getServiceId())) && ((supportServiceType.detectionTypeList.contains(MyServiceInfo.DetectionType.AREA) || !areaFamily.contains(service.getServiceId())) && ((!supportServiceType.detectionTypeList.contains(MyServiceInfo.DetectionType.APP) || appFamily.contains(service.getServiceId())) && ((supportServiceType.detectionTypeList.contains(MyServiceInfo.DetectionType.APP) || !appFamily.contains(service.getServiceId())) && ((!supportServiceType.detectionTypeList.contains(MyServiceInfo.DetectionType.SAS) || sasFamily.contains(service.getServiceId())) && (supportServiceType.detectionTypeList.contains(MyServiceInfo.DetectionType.SAS) || (!sasFamily.contains(service.getServiceId()) && !uiccFamily.contains(service.getServiceId())))))))))) {
                        }
                    }
                }
                if (!supportServiceType.targetIds.contains(service.getServiceId())) {
                    supportServiceType.targetIds.add(service.getServiceId());
                }
            }
        }
    }

    private static void clearTargetIds() {
        for (SupportServiceType supportServiceType : values()) {
            supportServiceType.targetIds.clear();
        }
    }

    public static boolean isMfiSupportService(String sid) {
        switch (resolve(sid).ordinal()) {
            case 6:
            case 7:
            case 8:
            case 9:
                return true;
            default:
                return false;
        }
    }
}
