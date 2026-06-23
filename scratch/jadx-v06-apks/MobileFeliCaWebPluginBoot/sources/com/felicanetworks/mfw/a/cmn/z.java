package com.felicanetworks.mfw.a.cmn;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.felicanetworks.mfc.FSC;

/* JADX INFO: compiled from: DevcCoperateUtil.java */
/* JADX INFO: loaded from: classes.dex */
class z implements ServiceConnection {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private FSC f184a;
    private Context b;
    final /* synthetic */ b0 c;

    private z(b0 b0Var) {
        this.c = b0Var;
        this.f184a = null;
        this.b = null;
    }

    public synchronized void a() {
        if (this.f184a != null && this.b != null) {
            this.b.unbindService(this);
            this.f184a.stopSelf();
            this.f184a = null;
            this.b = null;
        }
    }

    public synchronized FSC b(Context context) {
        if (!this.c.u() && this.f184a == null) {
            this.b = context;
            Intent intent = new Intent();
            intent.setClass(context, FSC.class);
            if (!context.bindService(intent, this, 1)) {
                return null;
            }
            while (this.f184a == null) {
                try {
                    wait();
                } catch (InterruptedException unused) {
                    if (!this.c.u()) {
                        throw new c1(b0.class, "FelicaConnector.getFelica");
                    }
                    b0.v();
                    throw null;
                }
            }
            return this.f184a;
        }
        return this.f184a;
    }

    @Override // android.content.ServiceConnection
    public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.f184a = ((com.felicanetworks.mfc.o) iBinder).a();
        notify();
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
    }
}
