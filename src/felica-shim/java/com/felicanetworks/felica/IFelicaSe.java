package com.felicanetworks.felica;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

public interface IFelicaSe extends IInterface {
    Bundle open(String packageName, IBinder token) throws RemoteException;

    void close(String packageName, int handle, IBinder token) throws RemoteException;

    int connect(String packageName, int handle) throws RemoteException;

    void disconnect(String packageName, int handle) throws RemoteException;

    Bundle transceive(String packageName, int handle, byte[] command, int timeout) throws RemoteException;

    void cancel(String packageName, int handle) throws RemoteException;
}
