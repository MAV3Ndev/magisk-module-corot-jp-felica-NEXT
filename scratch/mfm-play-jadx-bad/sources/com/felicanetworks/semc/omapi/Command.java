package com.felicanetworks.semc.omapi;

/* JADX INFO: loaded from: classes3.dex */
abstract class Command {
    protected byte[] mAid;
    protected GpController mGpController;

    abstract Response execute() throws Exception;

    Command() {
    }

    void setGpController(GpController gpController) {
        this.mGpController = gpController;
    }

    void setAid(byte[] bArr) {
        this.mAid = bArr;
    }
}
