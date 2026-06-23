package com.felicanetworks.common.cmnctrl.chip;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.felicanetworks.common.cmnlib.AppContext;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.mfc.Felica;

/* JADX INFO: loaded from: classes.dex */
public class BindFelica implements FunctionCodeInterface {
    private FelicaServiceConnection connection = new FelicaServiceConnection();
    private AppContext context;
    private BindFelicaListener listener;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 1;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 3;
    }

    public BindFelica(AppContext appContext) {
        this.context = appContext;
    }

    public void bindFelica(BindFelicaListener bindFelicaListener) throws BindException {
        try {
            if (bindFelicaListener == null) {
                throw new IllegalArgumentException("listener parameter is null.");
            }
            if (this.context.felica == null) {
                this.listener = bindFelicaListener;
                if (this.context.androidContext.bindService(new Intent(this.context.androidContext, (Class<?>) Felica.class), this.connection, 1)) {
                    return;
                }
                this.listener = null;
                throw new IllegalStateException("Failed to connect for Felica Service.");
            }
            throw new IllegalStateException("Felica Service is already connected.");
        } catch (Exception e) {
            throw new BindException(e, this.context.logMgr.out(LogMgr.CatExp.ERR, this, e));
        }
    }

    public void unbindFelica() {
        try {
            this.context.androidContext.unbindService(this.connection);
        } catch (Exception e) {
            this.context.logMgr.out(LogMgr.CatExp.WAR, this, e);
        }
    }

    private class FelicaServiceConnection implements ServiceConnection {
        private FelicaServiceConnection() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r4v4, types: [com.felicanetworks.common.cmnctrl.chip.BindFelica] */
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (BindFelica.this.context.felica == null) {
                try {
                    if (BindFelica.this.listener != null) {
                        try {
                            BindFelica.this.context.felica = ((Felica.LocalBinder) iBinder).getInstance();
                            BindFelica.this.listener.notifySucceeded();
                        } catch (Exception e) {
                            BindFelica.this.listener.notifyFailed(BindFelica.this.context.logMgr.out(LogMgr.CatExp.ERR, BindFelica.this, e));
                        }
                        return;
                    }
                } finally {
                    BindFelica.this.listener = null;
                }
            }
            BindFelica.this.context.androidContext.unbindService(this);
            BindFelica.this.context.felica = null;
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            BindFelica.this.context.felica = null;
        }
    }
}
