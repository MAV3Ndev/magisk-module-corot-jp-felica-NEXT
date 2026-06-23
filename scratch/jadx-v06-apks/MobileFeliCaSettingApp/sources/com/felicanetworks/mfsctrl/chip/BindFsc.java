package com.felicanetworks.mfsctrl.chip;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.felicanetworks.common.cmnctrl.chip.BindException;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.mfc.FSC;
import com.felicanetworks.mfslib.MfsAppContext;

/* JADX INFO: loaded from: classes.dex */
public class BindFsc implements FunctionCodeInterface {
    private MfsAppContext _context;
    private FscServiceConnection _connection = new FscServiceConnection();
    private BindFscListener _listener = null;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 1;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 34;
    }

    public BindFsc(MfsAppContext mfsAppContext) {
        this._context = null;
        this._context = mfsAppContext;
    }

    public void bindFsc(BindFscListener bindFscListener) throws BindException {
        try {
            if (bindFscListener == null) {
                throw new IllegalArgumentException("listener parameter is null.");
            }
            if (this._context.fsc != null) {
                throw new IllegalArgumentException("Already Bind to FSC Service.");
            }
            this._listener = bindFscListener;
            this._context.androidContext.bindService(new Intent(this._context.androidContext, (Class<?>) FSC.class), this._connection, 1);
        } catch (Exception e) {
            throw new BindException(e, this._context.logMgr.out(LogMgr.CatExp.ERR, this, e));
        }
    }

    public void unbindFsc() {
        try {
            this._listener = null;
            if (this._context.fsc != null) {
                this._context.androidContext.unbindService(this._connection);
            }
        } catch (Exception e) {
            this._context.logMgr.out(LogMgr.CatExp.ERR, this, e);
        }
    }

    private class FscServiceConnection implements ServiceConnection {
        private FscServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                try {
                } catch (Exception e) {
                    BindFsc.this._listener.notifyFailed(BindFsc.this._context.logMgr.out(LogMgr.CatExp.ERR, BindFsc.this, e));
                }
                if (BindFsc.this._listener == null) {
                    BindFsc.this._context.androidContext.unbindService(this);
                    return;
                }
                BindFsc.this._context.fsc = ((FSC.LocalBinder) iBinder).getInstance();
                BindFsc.this._listener.notifySucceeded();
            } finally {
                BindFsc.this._listener = null;
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            BindFsc.this._context.fsc = null;
        }
    }
}
