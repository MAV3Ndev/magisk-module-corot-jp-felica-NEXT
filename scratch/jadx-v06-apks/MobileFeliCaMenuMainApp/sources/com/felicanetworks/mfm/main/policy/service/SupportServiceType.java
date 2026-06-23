package com.felicanetworks.mfm.main.policy.service;

import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.Service;
import com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
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

    SupportServiceType(String str, String str2, String str3, List list) {
        this.serviceId = str;
        this.serviceType = str2;
        this.cardCategory = str3;
        this.detectionTypeList = list;
    }

    public static SupportServiceType resolve(String str) {
        for (SupportServiceType supportServiceType : values()) {
            if (supportServiceType.targetIds.contains(str)) {
                return supportServiceType;
            }
        }
        return US;
    }

    public static SupportServiceType resolve(List<String> list) {
        Iterator<String> it = list.iterator();
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
        To view partially-correct add '--show-bad-code' argument
    */
    public static void inject(java.util.List<com.felicanetworks.mfm.main.model.info.Service> r8, java.util.List<java.lang.String> r9, java.util.List<java.lang.String> r10, java.util.List<java.lang.String> r11, java.util.List<java.lang.String> r12) {
        /*
            Method dump skipped, instruction units count: 315
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.policy.service.SupportServiceType.inject(java.util.List, java.util.List, java.util.List, java.util.List, java.util.List):void");
    }

    private static void clearTargetIds() {
        for (SupportServiceType supportServiceType : values()) {
            supportServiceType.targetIds.clear();
        }
    }

    public static List<String> getM1M2TypeNotID() {
        ArrayList<String> arrayList = new ArrayList();
        arrayList.addAll(M1.targetIds);
        arrayList.addAll(M2.targetIds);
        ArrayList arrayList2 = new ArrayList();
        for (String str : arrayList) {
            if (!FeliCaParams.SERVICE_ID_DCARD.equals(str)) {
                arrayList2.add(str);
            }
        }
        return arrayList2;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.policy.service.SupportServiceType$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType;

        static {
            int[] iArr = new int[SupportServiceType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType = iArr;
            try {
                iArr[SupportServiceType.M1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.M2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.T1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.S1.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static boolean isMfiSupportService(String str) {
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[resolve(str).ordinal()];
        return i == 1 || i == 2 || i == 3 || i == 4;
    }
}
