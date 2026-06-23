package com.felicanetworks.mfw.a.cmn;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.felicanetworks.mfc.Felica;

/* JADX INFO: compiled from: DevcCoperateUtil.java */
/* JADX INFO: loaded from: classes.dex */
class a0 implements ServiceConnection {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Felica f142a;
    private Context b;
    final /* synthetic */ b0 c;

    private a0(b0 b0Var) {
        this.c = b0Var;
        this.f142a = null;
        this.b = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void b() {
        if (this.f142a != null && this.b != null) {
            this.b.unbindService(this);
            this.f142a.stopSelf();
            this.f142a = null;
            this.b = null;
        }
    }

    public synchronized Felica c(Context context) {
        if (!this.c.u() && this.f142a == null) {
            this.b = context;
            Intent intent = new Intent();
            intent.setClass(context, Felica.class);
            if (!context.bindService(intent, this, 1)) {
                return null;
            }
            while (this.f142a == null) {
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
            return this.f142a;
        }
        return this.f142a;
    }

    @Override // android.content.ServiceConnection
    public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.f142a = ((com.felicanetworks.mfc.t) iBinder).a();
        notify();
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
    }
}
