package com.felicanetworks.mfsctrl;

import com.felicanetworks.common.cmnctrl.chip.FelicaAccessException;
import com.felicanetworks.common.cmnctrl.data.FelicaErrorInfo;
import com.felicanetworks.common.cmnctrl.net.NetworkAccess;
import com.felicanetworks.common.cmnctrl.net.NetworkAccessListener;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.mfsctrl.chip.FelicaInitializeListener;
import com.felicanetworks.mfsctrl.chip.MfsFelicaAccess;
import com.felicanetworks.mfslib.MfsAppContext;

/* JADX INFO: loaded from: classes.dex */
public class RunInitializationThread implements FunctionCodeInterface {
    private MfsAppContext _context;
    private RunInitWorkerThread _thread = null;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 4;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 33;
    }

    public RunInitializationThread(MfsAppContext mfsAppContext) {
        this._context = null;
        this._context = mfsAppContext;
    }

    public synchronized void startThread(RunInitializationListener runInitializationListener) {
        if (!isAliveThread()) {
            RunInitWorkerThread runInitWorkerThread = new RunInitWorkerThread(runInitializationListener);
            this._thread = runInitWorkerThread;
            runInitWorkerThread.start();
        }
    }

    public synchronized void stopThread() {
        if (this._thread != null) {
            this._thread.setCancel();
            this._thread = null;
        }
    }

    public synchronized boolean isAliveThread() {
        return this._thread != null ? this._thread.isAlive() : false;
    }

    protected class RunInitWorkerThread extends Thread {
        private static final int NOTIFY_PROGRESS_1ST = 0;
        private static final int NOTIFY_PROGRESS_2ND = 20;
        private static final int NOTIFY_PROGRESS_3RD = 40;
        private static final int NOTIFY_PROGRESS_4TH = 60;
        private static final int NOTIFY_PROGRESS_5TH = 90;
        private static final int RESULT_KIND_CANCELED = 3;
        private static final int RESULT_KIND_COMPLETE = 0;
        private static final int RESULT_KIND_ERROR = 2;
        private static final int RESULT_KIND_WARNING = 1;
        private RunInitializationListener _listener;
        private NetworkAccess _na;
        private MfsFelicaAccess _mfa = null;
        private MfiFeliCaAccessImpl _faImpl = new MfiFeliCaAccessImpl();
        private FeliCaInitImpl _fiImpl = new FeliCaInitImpl();
        private int _resultKind = 0;
        private int _warnCode = 0;
        private int _warnData = 0;
        private String _errorId = null;
        private int _errorType = 0;
        private FelicaErrorInfo _felicaErrInfo = null;

        RunInitWorkerThread(RunInitializationListener runInitializationListener) {
            this._listener = null;
            this._na = new NetworkAccess(RunInitializationThread.this._context);
            this._listener = runInitializationListener;
        }

        private void setResultComplete() {
            this._resultKind = 0;
        }

        private void setResultWarning(int i, int i2) {
            this._resultKind = 1;
            this._warnCode = i;
            this._warnData = i2;
        }

        private void setResultError(String str, int i, FelicaErrorInfo felicaErrorInfo) {
            this._resultKind = 2;
            this._errorId = str;
            this._errorType = i;
            this._felicaErrInfo = felicaErrorInfo;
        }

        private void notifyResult() {
            RunInitializationListener runInitializationListener = this._listener;
            if (runInitializationListener != null) {
                int i = this._resultKind;
                if (i == 0) {
                    runInitializationListener.onComplete();
                } else if (i == 1) {
                    runInitializationListener.onWarning(this._warnCode, this._warnData);
                } else if (i == 2) {
                    runInitializationListener.onError(this._errorType, this._errorId, this._felicaErrInfo);
                }
                this._listener = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void notifyProgress(int i) {
            try {
                checkCancel();
                if (this._listener != null) {
                    this._listener.onProgress(i);
                }
            } catch (InterruptedException unused) {
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                try {
                    try {
                        try {
                            try {
                                checkCancel();
                                notifyProgress(0);
                                synchronized (this) {
                                    checkCancel();
                                    MfsFelicaAccess mfsFelicaAccess = new MfsFelicaAccess(RunInitializationThread.this._context);
                                    this._mfa = mfsFelicaAccess;
                                    mfsFelicaAccess.mfiUseStart(this._faImpl);
                                }
                                this._faImpl.pause();
                                checkCancel();
                                switch (this._faImpl.getResult()) {
                                    case 1:
                                        checkCancel();
                                        notifyProgress(40);
                                        checkCancel();
                                        this._mfa.initializeFelica(this._fiImpl);
                                        this._fiImpl.pause();
                                        checkCancel();
                                        checkCancel();
                                        int result = this._fiImpl.getResult();
                                        if (result == 1) {
                                            setResultComplete();
                                        } else if (result == 2) {
                                            setResultWarning(5, 0);
                                        } else if (result == 3) {
                                            setResultWarning(8, 0);
                                        } else if (result == 5) {
                                            setResultWarning(7, 0);
                                        } else if (result == 6) {
                                            setResultWarning(3, 0);
                                        } else if (result != 7) {
                                            setResultError(this._fiImpl.getErrorId(), 9, null);
                                        } else {
                                            setResultWarning(16, 0);
                                        }
                                        synchronized (RunInitializationThread.this) {
                                            if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                                                this._listener = null;
                                            } else {
                                                RunInitializationThread.this._thread = null;
                                            }
                                            break;
                                        }
                                        if (isInterrupted()) {
                                            return;
                                        }
                                        finishFeliCaAccess();
                                        notifyResult();
                                        return;
                                    case 2:
                                        setResultWarning(6, this._faImpl.getPid());
                                        synchronized (RunInitializationThread.this) {
                                            if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                                                this._listener = null;
                                            } else {
                                                RunInitializationThread.this._thread = null;
                                            }
                                            break;
                                        }
                                        try {
                                            if (isInterrupted()) {
                                                return;
                                            }
                                            finishFeliCaAccess();
                                            notifyResult();
                                            return;
                                        } catch (Exception e) {
                                            RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, e);
                                            return;
                                        }
                                    case 3:
                                        setResultWarning(7, 0);
                                        synchronized (RunInitializationThread.this) {
                                            if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                                                this._listener = null;
                                            } else {
                                                RunInitializationThread.this._thread = null;
                                            }
                                            break;
                                        }
                                        try {
                                            if (isInterrupted()) {
                                                return;
                                            }
                                            finishFeliCaAccess();
                                            notifyResult();
                                            return;
                                        } catch (Exception e2) {
                                            RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, e2);
                                            return;
                                        }
                                    case 4:
                                        setResultError(this._faImpl.getErrorId(), 9, null);
                                        synchronized (RunInitializationThread.this) {
                                            if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                                                this._listener = null;
                                            } else {
                                                RunInitializationThread.this._thread = null;
                                            }
                                            break;
                                        }
                                        try {
                                            if (isInterrupted()) {
                                                return;
                                            }
                                            finishFeliCaAccess();
                                            notifyResult();
                                            return;
                                        } catch (Exception e3) {
                                            RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, e3);
                                            return;
                                        }
                                    case 5:
                                        setResultError(this._faImpl.getErrorId(), 11, this._faImpl.getFelicaErrInfo());
                                        synchronized (RunInitializationThread.this) {
                                            if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                                                this._listener = null;
                                            } else {
                                                RunInitializationThread.this._thread = null;
                                            }
                                            break;
                                        }
                                        try {
                                            if (isInterrupted()) {
                                                return;
                                            }
                                            finishFeliCaAccess();
                                            notifyResult();
                                            return;
                                        } catch (Exception e4) {
                                            RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, e4);
                                            return;
                                        }
                                    case 6:
                                        setResultError(this._faImpl.getErrorId(), 12, this._faImpl.getFelicaErrInfo());
                                        synchronized (RunInitializationThread.this) {
                                            if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                                                this._listener = null;
                                            } else {
                                                RunInitializationThread.this._thread = null;
                                            }
                                            break;
                                        }
                                        try {
                                            if (isInterrupted()) {
                                                return;
                                            }
                                            finishFeliCaAccess();
                                            notifyResult();
                                            return;
                                        } catch (Exception e5) {
                                            RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, e5);
                                            return;
                                        }
                                    case 7:
                                        setResultError(this._faImpl.getErrorId(), 15, this._faImpl.getFelicaErrInfo());
                                        synchronized (RunInitializationThread.this) {
                                            if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                                                this._listener = null;
                                            } else {
                                                RunInitializationThread.this._thread = null;
                                            }
                                            break;
                                        }
                                        try {
                                            if (isInterrupted()) {
                                                return;
                                            }
                                            finishFeliCaAccess();
                                            notifyResult();
                                            return;
                                        } catch (Exception e6) {
                                            RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, e6);
                                            return;
                                        }
                                    case 8:
                                        setResultError(this._faImpl.getErrorId(), 13, this._faImpl.getFelicaErrInfo());
                                        synchronized (RunInitializationThread.this) {
                                            if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                                                this._listener = null;
                                            } else {
                                                RunInitializationThread.this._thread = null;
                                            }
                                            break;
                                        }
                                        try {
                                            if (isInterrupted()) {
                                                return;
                                            }
                                            finishFeliCaAccess();
                                            notifyResult();
                                            return;
                                        } catch (Exception e7) {
                                            RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, e7);
                                            return;
                                        }
                                    case 9:
                                        setResultWarning(8, this._faImpl.getPid());
                                        synchronized (RunInitializationThread.this) {
                                            if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                                                this._listener = null;
                                            } else {
                                                RunInitializationThread.this._thread = null;
                                            }
                                            break;
                                        }
                                        try {
                                            if (isInterrupted()) {
                                                return;
                                            }
                                            finishFeliCaAccess();
                                            notifyResult();
                                            return;
                                        } catch (Exception e8) {
                                            RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, e8);
                                            return;
                                        }
                                    case 10:
                                        setResultWarning(3, this._faImpl.getPid());
                                        synchronized (RunInitializationThread.this) {
                                            if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                                                this._listener = null;
                                            } else {
                                                RunInitializationThread.this._thread = null;
                                            }
                                            break;
                                        }
                                        try {
                                            if (isInterrupted()) {
                                                return;
                                            }
                                            finishFeliCaAccess();
                                            notifyResult();
                                            return;
                                        } catch (Exception e9) {
                                            RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, e9);
                                            return;
                                        }
                                    case 11:
                                        setResultWarning(8, this._faImpl.getPid());
                                        synchronized (RunInitializationThread.this) {
                                            if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                                                this._listener = null;
                                            } else {
                                                RunInitializationThread.this._thread = null;
                                            }
                                            break;
                                        }
                                        try {
                                            if (isInterrupted()) {
                                                return;
                                            }
                                            finishFeliCaAccess();
                                            notifyResult();
                                            return;
                                        } catch (Exception e10) {
                                            RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, e10);
                                            return;
                                        }
                                    case 12:
                                        setResultWarning(5, 0);
                                        synchronized (RunInitializationThread.this) {
                                            if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                                                this._listener = null;
                                            } else {
                                                RunInitializationThread.this._thread = null;
                                            }
                                            break;
                                        }
                                        try {
                                            if (isInterrupted()) {
                                                return;
                                            }
                                            finishFeliCaAccess();
                                            notifyResult();
                                            return;
                                        } catch (Exception e11) {
                                            RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, e11);
                                            return;
                                        }
                                    default:
                                        setResultError(RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, new ControlFunctionException("Unknown Result : FeliCa Access", 3)), 9, null);
                                        synchronized (RunInitializationThread.this) {
                                            if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                                                this._listener = null;
                                            } else {
                                                RunInitializationThread.this._thread = null;
                                            }
                                            break;
                                        }
                                        try {
                                            if (isInterrupted()) {
                                                return;
                                            }
                                            finishFeliCaAccess();
                                            notifyResult();
                                            return;
                                        } catch (Exception e12) {
                                            RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, e12);
                                            return;
                                        }
                                }
                            } catch (Exception e13) {
                                RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, e13);
                            }
                        } catch (FelicaAccessException e14) {
                            if (e14.getErrorId() == 3) {
                                setResultError(e14.getErrIdentifierCode(), 15, e14.getFelicaErrInfo());
                            } else {
                                setResultError(e14.getErrIdentifierCode(), 9, null);
                            }
                            synchronized (RunInitializationThread.this) {
                                if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                                    this._listener = null;
                                } else {
                                    RunInitializationThread.this._thread = null;
                                }
                                if (isInterrupted()) {
                                    return;
                                }
                                finishFeliCaAccess();
                                notifyResult();
                            }
                        }
                    } catch (Exception e15) {
                        setResultError(RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, e15), 9, null);
                        synchronized (RunInitializationThread.this) {
                            if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                                this._listener = null;
                            } else {
                                RunInitializationThread.this._thread = null;
                            }
                            if (isInterrupted()) {
                                return;
                            }
                            finishFeliCaAccess();
                            notifyResult();
                        }
                    }
                } catch (InterruptedException unused) {
                    this._resultKind = 3;
                    synchronized (RunInitializationThread.this) {
                        if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                            this._listener = null;
                        } else {
                            RunInitializationThread.this._thread = null;
                        }
                        if (isInterrupted()) {
                            return;
                        }
                        finishFeliCaAccess();
                        notifyResult();
                    }
                }
            } catch (Throwable th) {
                synchronized (RunInitializationThread.this) {
                    if (RunInitializationThread.this._thread == null || !RunInitializationThread.this._thread.equals(this)) {
                        this._listener = null;
                    } else {
                        RunInitializationThread.this._thread = null;
                    }
                    try {
                        if (!isInterrupted()) {
                            finishFeliCaAccess();
                            notifyResult();
                        }
                    } catch (Exception e16) {
                        RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, e16);
                    }
                    throw th;
                }
            }
        }

        public synchronized void finishFeliCaAccess() {
            if (this._mfa != null) {
                this._mfa.mfiUseEnd();
            }
        }

        public synchronized void setCancel() {
            interrupt();
            this._faImpl.restart();
            this._fiImpl.restart();
            finishFeliCaAccess();
            this._na.cancel();
        }

        public synchronized void checkCancel() throws InterruptedException {
            if (isInterrupted()) {
                throw new InterruptedException();
            }
        }

        protected class NetworkConnImpl implements NetworkAccessListener {
            @Override // com.felicanetworks.common.cmnctrl.net.NetworkAccessListener
            public void receiveRatio(int i) {
            }

            @Override // com.felicanetworks.common.cmnctrl.net.NetworkAccessListener
            public void session() {
            }

            protected NetworkConnImpl() {
            }
        }

        protected class FeliCaInitImpl implements FelicaInitializeListener {
            public static final int COMMUNICATION_ERROR = 3;
            public static final int FAILED = 2;
            public static final int FAITAL_ERROR = 4;
            public static final int FELICA_LOCK_ERROR = 5;
            public static final int NOT_APPLICABLE_DEVICE = 7;
            public static final int OVERCROWDING = 6;
            public static final int RESULT_NONE = 0;
            public static final int SUCCESS = 1;
            private int _result = 0;
            private String _errorId = null;
            private boolean _pauseFlg = true;

            protected FeliCaInitImpl() {
            }

            @Override // com.felicanetworks.mfsctrl.chip.FelicaInitializeListener
            public void completeActivate() {
                RunInitWorkerThread.this.notifyProgress(20);
            }

            @Override // com.felicanetworks.mfsctrl.chip.FelicaInitializeListener
            public void completeLinkageData() {
                RunInitWorkerThread.this.notifyProgress(60);
            }

            @Override // com.felicanetworks.mfsctrl.chip.FelicaInitializeListener
            public void onComplete() {
                RunInitWorkerThread.this.notifyProgress(90);
                this._result = 1;
                restart();
            }

            @Override // com.felicanetworks.mfsctrl.chip.FelicaInitializeListener
            public void onError(String str) {
                this._result = 4;
                this._errorId = str;
                restart();
            }

            @Override // com.felicanetworks.mfsctrl.chip.FelicaInitializeListener
            public void onWarning(int i) {
                if (i == 0) {
                    this._result = 3;
                } else if (i == 1) {
                    this._result = 2;
                } else if (i == 4) {
                    this._result = 5;
                } else if (i == 5) {
                    this._result = 6;
                } else if (i != 6) {
                    this._errorId = RunInitializationThread.this._context.logMgr.out(LogMgr.CatExp.ERR, RunInitializationThread.this, new ControlFunctionException("Illegal Warning Code", 3));
                    this._result = 4;
                } else {
                    this._result = 7;
                }
                restart();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void pause() throws InterruptedException {
                synchronized (this) {
                    while (this._pauseFlg) {
                        wait();
                    }
                }
            }

            public void restart() {
                synchronized (this) {
                    this._pauseFlg = false;
                    notifyAll();
                }
            }

            public int getResult() {
                return this._result;
            }

            public String getErrorId() {
                return this._errorId;
            }
        }

        public class MfiFeliCaAccessImpl extends FeliCaAccessImpl {
            public MfiFeliCaAccessImpl() {
            }

            @Override // com.felicanetworks.mfsctrl.FeliCaAccessImpl, com.felicanetworks.common.cmnctrl.chip.FelicaAccessListener
            public void completeActivate() {
                RunInitWorkerThread.this.notifyProgress(20);
            }
        }
    }
}
