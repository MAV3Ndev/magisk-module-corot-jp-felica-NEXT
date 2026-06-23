package com.felicanetworks.mfm.main.model.info;

import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
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

    public RecommendInfo(String id, String version, String name, String provider, Linkage linkage, String status, String overview, String details, List<String> procedures, String categoryId, String categoryTitle, MyServiceInfo myServiceInfo, DatabaseExpert db) {
        super(id, version, name, provider, linkage, db);
        this.status = status;
        this.overview = overview;
        this.details = details;
        this.procedures = procedures;
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
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
