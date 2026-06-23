package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: compiled from: IMfiFelica.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class m extends Binder implements n {
    public static n E(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.felicanetworks.mfc.mfi.IMfiFelica");
        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof n)) ? new l(iBinder) : (n) iInterfaceQueryLocalInterface;
    }
}
