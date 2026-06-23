package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.4.0 */
/* JADX INFO: loaded from: classes3.dex */
public abstract class zai {
    public final int zac;

    public zai(int i) {
        this.zac = i;
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0024: CONSTRUCTOR 
  (19 int)
  (wrap:java.lang.String:STR_CONCAT 
  (wrap:java.lang.String:0x0009: INVOKE 
  (wrap:java.lang.Class<?>:0x0005: INVOKE (r2v0 android.os.RemoteException) VIRTUAL call: java.lang.Object.getClass():java.lang.Class A[MD:():java.lang.Class<?> (c), WRAPPED])
 VIRTUAL call: java.lang.Class.getSimpleName():java.lang.String A[MD:():java.lang.String (c), WRAPPED] (LINE:2))
  (": ")
  (wrap:java.lang.String:0x0015: INVOKE (r2v0 android.os.RemoteException) VIRTUAL call: android.os.RemoteException.getLocalizedMessage():java.lang.String A[MD:():java.lang.String (s), WRAPPED])
 A[MD:():java.lang.String (c), SYNTHETIC, WRAPPED])
 A[MD:(int, java.lang.String):void (m)] (LINE:3) call: com.google.android.gms.common.api.Status.<init>(int, java.lang.String):void type: CONSTRUCTOR */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static /* bridge */ /* synthetic */ Status zah(RemoteException remoteException) {
        return new Status(19, remoteException.getClass().getSimpleName() + ": " + remoteException.getLocalizedMessage());
    }

    public abstract void zad(Status status);

    public abstract void zae(Exception exc);

    public abstract void zaf(zabq zabqVar) throws DeadObjectException;

    public abstract void zag(zaad zaadVar, boolean z);
}
