package com.felicanetworks.mfs;

import com.felicanetworks.common.cmnview.AppData;

/* JADX INFO: loaded from: classes.dex */
public class MfsAppData extends AppData {
    private static AppStatus appStatus;

    public static AppStatus getAppStatus() {
        return appStatus;
    }

    public static void setAppStatus(AppStatus appStatus2) {
        appStatus = appStatus2;
    }
}
