package com.felicanetworks.mfc.tcap;

import com.felicanetworks.tcap.IFelicaDevice;

/* JADX INFO: loaded from: classes.dex */
public interface FeliCaClient {
    void addDevice(InternalDevice internalDevice, int i) throws IllegalArgumentException, NullPointerException, FeliCaClientException;

    void addDevice(InternalDevice internalDevice, int i, IFelicaDevice iFelicaDevice) throws IllegalArgumentException, NullPointerException, FeliCaClientException;

    InternalDevice getDevice(String str, String str2);

    FeliCaClientEventListener getEventListener();

    String getUrl();

    String getUserAgent();

    boolean isStarted();

    void removeAllDevices() throws FeliCaClientException;

    InternalDevice removeDevice(String str, String str2) throws FeliCaClientException;

    void setEventListener(FeliCaClientEventListener feliCaClientEventListener) throws FeliCaClientException;

    void setUrl(String str) throws NullPointerException, FeliCaClientException;

    void setUserAgent(String str) throws NullPointerException;

    void start(boolean z, byte[] bArr) throws FeliCaClientException;

    void stop(boolean z);
}
