package com.felicanetworks.mfc.mfi.omapi;

/* JADX INFO: loaded from: classes3.dex */
public class FelicaInstanceInfoEx extends FelicaInstanceInfo {
    private final String mServiceId;

    public FelicaInstanceInfoEx(InstanceStatus instanceStatus, String serviceId) {
        super(instanceStatus);
        this.mServiceId = serviceId;
    }

    public FelicaInstanceInfoEx(SelectResponse selectResponse, String serviceId) {
        super(selectResponse);
        this.mServiceId = serviceId;
    }

    public String getServiceId() {
        return this.mServiceId;
    }
}
