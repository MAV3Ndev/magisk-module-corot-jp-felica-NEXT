package com.felicanetworks.mfsctrl;

import com.felicanetworks.common.cmnctrl.chip.BindFelica;
import com.felicanetworks.common.cmnctrl.chip.BindFelicaListener;
import com.felicanetworks.common.cmnctrl.database.DatabaseAccess;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.mfsctrl.chip.BindFsc;
import com.felicanetworks.mfsctrl.chip.BindFscListener;
import com.felicanetworks.mfslib.MfsAppContext;

/* JADX INFO: loaded from: classes.dex */
public class ControlFunctionLibrary implements FunctionCodeInterface {
    private static ControlFunctionLibrary _thisInstance = new ControlFunctionLibrary();
    private MfsAppContext _mfsContext = null;
    private CheckInitializationThread _ciThread = null;
    private RunInitializationThread _riThread = null;
    private InitializeApplicationListener _initAppListener = null;
    private BindFelica _bindFelica = null;
    private BindFsc _bindFsc = null;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 2;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 33;
    }

    private ControlFunctionLibrary() {
    }

    public static ControlFunctionLibrary getInstance() {
        return _thisInstance;
    }

    public void initializeApplication(MfsAppContext mfsAppContext, InitializeApplicationListener initializeApplicationListener) throws ControlFunctionException {
        this._mfsContext = mfsAppContext;
        try {
            if (initializeApplicationListener == null) {
                throw new IllegalArgumentException("Bad Param : Listener is null");
            }
            if ((this._ciThread != null && this._ciThread.isAliveThread()) || (this._riThread != null && this._riThread.isAliveThread())) {
                throw new IllegalThreadStateException("Running Other Process");
            }
            this._initAppListener = initializeApplicationListener;
            if (this._bindFelica != null) {
                this._bindFelica.unbindFelica();
            }
            if (this._bindFsc != null) {
                this._bindFsc.unbindFsc();
            }
            this._bindFelica = new BindFelica(mfsAppContext);
            this._bindFsc = new BindFsc(mfsAppContext);
            this._bindFelica.bindFelica(new FeliCaConnImpl());
        } catch (Exception e) {
            this._initAppListener = null;
            throw new ControlFunctionException(e.getMessage(), this._mfsContext.logMgr.out(LogMgr.CatExp.ERR, this, e), 3);
        }
    }

    public void deinitializeApplication() {
        try {
            this._initAppListener = null;
            if (this._bindFsc != null) {
                this._bindFsc.unbindFsc();
                this._bindFsc = null;
            }
            if (this._bindFelica != null) {
                this._bindFelica.unbindFelica();
                this._bindFelica = null;
            }
            DatabaseAccess.clearInstance();
        } catch (Exception unused) {
        }
    }

    public void startCheckInitialization(CheckInitializationListener checkInitializationListener) throws ControlFunctionException {
        try {
            if (checkInitializationListener == null) {
                throw new IllegalArgumentException("Bad Param : Listener is null");
            }
            if ((this._ciThread != null && this._ciThread.isAliveThread()) || (this._riThread != null && this._riThread.isAliveThread())) {
                throw new IllegalThreadStateException("Running Process");
            }
            CheckInitializationThread checkInitializationThread = new CheckInitializationThread(this._mfsContext);
            this._ciThread = checkInitializationThread;
            checkInitializationThread.startThread(checkInitializationListener);
        } catch (Exception e) {
            throw new ControlFunctionException(e.getMessage(), this._mfsContext.logMgr.out(LogMgr.CatExp.ERR, this, e), 3);
        }
    }

    public void stopCheckInitialization() throws ControlFunctionException {
        try {
            if (this._ciThread == null || !this._ciThread.isAliveThread()) {
                return;
            }
            this._ciThread.stopThread();
        } catch (Exception e) {
            throw new ControlFunctionException(e.getMessage(), this._mfsContext.logMgr.out(LogMgr.CatExp.ERR, this, e), 3);
        }
    }

    public void startRunInitialization(RunInitializationListener runInitializationListener) throws ControlFunctionException {
        try {
            if (runInitializationListener == null) {
                throw new IllegalArgumentException("Bad Param : Listener is null");
            }
            if ((this._ciThread != null && this._ciThread.isAliveThread()) || (this._riThread != null && this._riThread.isAliveThread())) {
                throw new IllegalThreadStateException("Running Process");
            }
            RunInitializationThread runInitializationThread = new RunInitializationThread(this._mfsContext);
            this._riThread = runInitializationThread;
            runInitializationThread.startThread(runInitializationListener);
        } catch (Exception e) {
            throw new ControlFunctionException(e.getMessage(), this._mfsContext.logMgr.out(LogMgr.CatExp.ERR, this, e), 3);
        }
    }

    public void stopRunInitialization() throws ControlFunctionException {
        try {
            if (this._riThread == null || !this._riThread.isAliveThread()) {
                return;
            }
            this._riThread.stopThread();
        } catch (Exception e) {
            throw new ControlFunctionException(e.getMessage(), this._mfsContext.logMgr.out(LogMgr.CatExp.ERR, this, e), 3);
        }
    }

    protected class FeliCaConnImpl implements BindFelicaListener {
        protected FeliCaConnImpl() {
        }

        @Override // com.felicanetworks.common.cmnctrl.chip.BindFelicaListener
        public void notifySucceeded() {
            try {
                if (ControlFunctionLibrary.this._initAppListener != null) {
                    ControlFunctionLibrary.this._bindFsc.bindFsc(ControlFunctionLibrary.this.new FscConnImpl());
                } else {
                    ControlFunctionLibrary.this.deinitializeApplication();
                }
            } catch (Exception e) {
                String strOut = ControlFunctionLibrary.this._mfsContext.logMgr.out(LogMgr.CatExp.ERR, ControlFunctionLibrary.this, e);
                if (ControlFunctionLibrary.this._initAppListener != null) {
                    ControlFunctionLibrary.this._initAppListener.onError(strOut);
                    ControlFunctionLibrary.this._initAppListener = null;
                }
                ControlFunctionLibrary.this.deinitializeApplication();
            }
        }

        @Override // com.felicanetworks.common.cmnctrl.chip.BindFelicaListener
        public void notifyFailed(String str) {
            if (ControlFunctionLibrary.this._initAppListener != null) {
                ControlFunctionLibrary.this._initAppListener.onError(str);
                ControlFunctionLibrary.this._initAppListener = null;
            }
            ControlFunctionLibrary.this.deinitializeApplication();
        }
    }

    protected class FscConnImpl implements BindFscListener {
        protected FscConnImpl() {
        }

        @Override // com.felicanetworks.mfsctrl.chip.BindFscListener
        public void notifySucceeded() {
            if (ControlFunctionLibrary.this._initAppListener != null) {
                ControlFunctionLibrary.this._initAppListener.onComplete();
                ControlFunctionLibrary.this._initAppListener = null;
            } else {
                ControlFunctionLibrary.this.deinitializeApplication();
            }
        }

        @Override // com.felicanetworks.mfsctrl.chip.BindFscListener
        public void notifyFailed(String str) {
            if (ControlFunctionLibrary.this._initAppListener != null) {
                ControlFunctionLibrary.this._initAppListener.onError(str);
                ControlFunctionLibrary.this._initAppListener = null;
            }
            ControlFunctionLibrary.this.deinitializeApplication();
        }
    }
}
