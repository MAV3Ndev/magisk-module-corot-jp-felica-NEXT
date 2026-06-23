package com.felicanetworks.mfc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: compiled from: IFelica.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class n0 extends Binder implements o0 {
    public static o0 E(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.felicanetworks.mfc.IFelica");
        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof o0)) ? new m0(iBinder) : (o0) iInterfaceQueryLocalInterface;
    }
}
