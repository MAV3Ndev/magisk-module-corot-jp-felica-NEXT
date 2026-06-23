package com.felicanetworks.mfm.main.model.info;

import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RecommendInfo extends ServiceInfo {
    private static final int STATUS_NEW_ARRIVALS = 1;
    private String categoryId;
    private String categoryTitle;
    private String details;
    private MyServiceInfo myServiceInfo;
    private String overview;
    private List<String> procedures;
    private String status;

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo
    public String toString() {
        return "RecommendInfo{status='" + this.status + "', overview='" + this.overview + "', details='" + this.details + "', procedures=" + this.procedures + ", categoryId='" + this.categoryId + "', categoryTitle='" + this.categoryTitle + "', myServiceInfo=" + this.myServiceInfo + "} " + super.toString();
    }

    public RecommendInfo(String str, String str2, String str3, String str4, Linkage linkage, String str5, String str6, String str7, List<String> list, String str8, String str9, MyServiceInfo myServiceInfo, DatabaseExpert databaseExpert) {
        super(str, str2, str3, str4, linkage, databaseExpert);
        this.status = str5;
        this.overview = str6;
        this.details = str7;
        this.procedures = list;
        this.categoryId = str8;
        this.categoryTitle = str9;
        this.myServiceInfo = myServiceInfo;
    }

    public boolean isNew() {
        return 1 == (Integer.valueOf(this.status).intValue() | 1);
    }

    public String getStatus() {
        return this.status;
    }

    public String getOverview() {
        return this.overview;
    }

    public String getDetails() {
        return this.details;
    }

    public List<String> getProcedures() {
        return this.procedures;
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo
    public Linkage getLinkage() {
        MyServiceInfo myServiceInfo = this.myServiceInfo;
        if (myServiceInfo != null) {
            return myServiceInfo.getLinkage();
        }
        return super.getLinkage();
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public String getCategoryTitle() {
        return this.categoryTitle;
    }
}
