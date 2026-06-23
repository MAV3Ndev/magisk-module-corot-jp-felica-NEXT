package com.felicanetworks.mfc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: compiled from: IFSC.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class i0 extends Binder implements j0 {
    public static j0 E(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.felicanetworks.mfc.IFSC");
        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof j0)) ? new h0(iBinder) : (j0) iInterfaceQueryLocalInterface;
    }
}
