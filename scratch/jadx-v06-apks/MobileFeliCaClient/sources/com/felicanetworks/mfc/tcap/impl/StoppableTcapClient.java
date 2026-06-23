package com.felicanetworks.mfc.tcap.impl;

import com.felicanetworks.mfc.tcap.FeliCaClient;
import com.felicanetworks.mfc.tcap.FeliCaClientEventListener;
import com.felicanetworks.mfc.tcap.FeliCaClientException;
import com.felicanetworks.mfc.tcap.InternalDevice;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.tcap.IFelicaDevice;

/* JADX INFO: loaded from: classes.dex */
public class StoppableTcapClient implements FeliCaClient {
    InnerTcapClient mClient = new InnerTcapClient();
    private String mUserAgent;

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public FeliCaClientEventListener getEventListener() {
        return this.mClient.getEventListener();
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public void removeAllDevices() throws FeliCaClientException {
        this.mClient.removeAllDevices();
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public String getUrl() {
        return this.mClient.getUrl();
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public void setEventListener(FeliCaClientEventListener feliCaClientEventListener) throws FeliCaClientException {
        this.mClient.setEventListener(feliCaClientEventListener);
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public boolean isStarted() {
        return this.mClient.isStarted();
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public void stop(boolean z) {
        if (this.mClient.stopCutOff(z)) {
            this.mClient = new InnerTcapClient();
            this.mClient.setUserAgent(this.mUserAgent);
        }
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public void setUrl(String str) throws NullPointerException, FeliCaClientException {
        this.mClient.setUrl(str);
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public synchronized void start(boolean z, byte[] bArr) throws FeliCaClientException {
        this.mClient.start(z, bArr);
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public InternalDevice getDevice(String str, String str2) {
        return this.mClient.getDevice(str, str2);
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public InternalDevice removeDevice(String str, String str2) throws FeliCaClientException {
        return this.mClient.removeDevice(str, str2);
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public void addDevice(InternalDevice internalDevice, int i) throws IllegalArgumentException, NullPointerException, FeliCaClientException {
        this.mClient.addDevice(internalDevice, i);
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public String getUserAgent() {
        return this.mUserAgent;
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public void setUserAgent(String str) throws NullPointerException {
        if (str == null) {
            LogMgr.log(1, "%s", "700");
            throw new NullPointerException();
        }
        this.mUserAgent = str;
        this.mClient.setUserAgent(str);
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public void addDevice(InternalDevice internalDevice, int i, IFelicaDevice iFelicaDevice) throws IllegalArgumentException, NullPointerException, FeliCaClientException {
        this.mClient.addDevice(internalDevice, i, iFelicaDevice);
    }
}
