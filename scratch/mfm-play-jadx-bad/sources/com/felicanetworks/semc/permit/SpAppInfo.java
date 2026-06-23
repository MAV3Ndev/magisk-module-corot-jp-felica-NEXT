package com.felicanetworks.semc.permit;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class SpAppInfo {
    public List<String> mAidListForCrsOperation;
    public List<AllowedServiceList> mAllowedServiceLists;
    public String mSigningCertHash;
    public String mSpAppId;

    public static class AllowedServiceList {
        public List<String> sepIdList;
        public String serviceId;
        public List<String> useCaseList;
    }
}
