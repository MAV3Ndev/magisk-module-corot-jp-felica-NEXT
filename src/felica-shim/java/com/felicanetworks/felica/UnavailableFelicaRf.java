package com.felicanetworks.felica;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

final class UnavailableFelicaRf extends Binder implements IFelicaRf {
    private static final int TYPE_NFC_GENERIC_ERROR = -99;
    private static final String SERVICE_UNAVAILABLE = "FeliCa RF service is not available on this module";

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    public Bundle open(String packageName, IBinder token) throws RemoteException {
        return errorBundle(TYPE_NFC_GENERIC_ERROR);
    }

    @Override
    public void close(String packageName, int handle, IBinder token) throws RemoteException {
        throw new RemoteException(SERVICE_UNAVAILABLE);
    }

    @Override
    public int connect(String packageName, int handle, int timeout) throws RemoteException {
        return TYPE_NFC_GENERIC_ERROR;
    }

    @Override
    public void disconnect(String packageName, int handle) throws RemoteException {
        throw new RemoteException(SERVICE_UNAVAILABLE);
    }

    @Override
    public Bundle transceive(String packageName, int handle, byte[] command, int timeout) throws RemoteException {
        return errorBundle(TYPE_NFC_GENERIC_ERROR);
    }

    @Override
    public void cancel(String packageName, int handle) throws RemoteException {
        throw new RemoteException(SERVICE_UNAVAILABLE);
    }

    private static Bundle errorBundle(int error) {
        Bundle bundle = new Bundle();
        bundle.putInt("e", error);
        return bundle;
    }
}
