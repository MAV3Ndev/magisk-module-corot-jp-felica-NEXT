package com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.AppContext;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.mfcutil.mfc.Felica;

/* JADX INFO: loaded from: classes.dex */
public class BindFelica {
    private FelicaServiceConnection connection = new FelicaServiceConnection();
    private AppContext context;
    private BindFelicaListener listener;

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
            throw new BindException(getClass(), 257, e);
        }
    }

    public void unbindFelica() {
        try {
            this.context.androidContext.unbindService(this.connection);
        } catch (Exception e) {
            LogUtil.warning(e);
        }
    }

    private class FelicaServiceConnection implements ServiceConnection {
        private FelicaServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (BindFelica.this.context.felica == null && BindFelica.this.listener != null) {
                try {
                    try {
                        BindFelica.this.context.felica = ((Felica.LocalBinder) iBinder).getInstance();
                        BindFelica.this.listener.notifySucceeded();
                    } catch (Exception e) {
                        BindFelica.this.listener.notifyFailed(new BindException(BindFelica.class, InputDeviceCompat.SOURCE_DPAD, e));
                    }
                    return;
                } finally {
                    BindFelica.this.listener = null;
                }
            }
            LogUtil.error(new MfmException(BindFelica.class, 1, new RuntimeException()));
            BindFelica.this.context.androidContext.unbindService(this);
            BindFelica.this.context.felica = null;
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtil.error(new MfmException(BindFelica.class, 2, new RuntimeException()));
            BindFelica.this.context.felica = null;
        }
    }
}
