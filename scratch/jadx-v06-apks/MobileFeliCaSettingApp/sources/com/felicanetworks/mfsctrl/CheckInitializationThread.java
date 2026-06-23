package com.felicanetworks.mfsctrl;

import com.felicanetworks.common.cmnctrl.data.FelicaErrorInfo;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.mfsctrl.chip.MfsFelicaAccess;
import com.felicanetworks.mfslib.MfsAppContext;

/* JADX INFO: loaded from: classes.dex */
public class CheckInitializationThread implements FunctionCodeInterface {
    private MfsAppContext _context;
    private CheckInitWorkerThread _thread = null;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 1;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 33;
    }

    public CheckInitializationThread(MfsAppContext mfsAppContext) {
        this._context = null;
        this._context = mfsAppContext;
    }

    public synchronized void startThread(CheckInitializationListener checkInitializationListener) {
        if (!isAliveThread()) {
            CheckInitWorkerThread checkInitWorkerThread = new CheckInitWorkerThread(checkInitializationListener);
            this._thread = checkInitWorkerThread;
            checkInitWorkerThread.start();
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

    protected class CheckInitWorkerThread extends Thread {
        private static final int RESULT_KIND_CANCELED = 3;
        private static final int RESULT_KIND_COMPLETE = 0;
        private static final int RESULT_KIND_ERROR = 2;
        private static final int RESULT_KIND_WARNING = 1;
        private CheckInitializationListener _listener;
        String mficVersion;
        private MfsFelicaAccess _mfa = null;
        private FeliCaAccessImpl _faImpl = new FeliCaAccessImpl();
        private int _resultKind = 0;
        private int _compState = 0;
        private int _warnCode = 0;
        private int _warnData = 0;
        private String _errorId = null;
        private int _errorType = 0;
        private FelicaErrorInfo _felicaErrInfo = null;

        CheckInitWorkerThread(CheckInitializationListener checkInitializationListener) {
            this._listener = null;
            this._listener = checkInitializationListener;
        }

        private void setResultComplete(int i) {
            this._resultKind = 0;
            this._compState = i;
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
            CheckInitializationListener checkInitializationListener = this._listener;
            if (checkInitializationListener != null) {
                int i = this._resultKind;
                if (i == 0) {
                    checkInitializationListener.onComplete(this._compState);
                } else if (i == 1) {
                    checkInitializationListener.onWarning(this._warnCode, this._warnData);
                } else if (i == 2) {
                    checkInitializationListener.onError(this._errorType, this._errorId, this._felicaErrInfo);
                }
                this._listener = null;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:111:0x01d4 A[Catch: all -> 0x060a, Exception -> 0x060e, FelicaAccessException -> 0x0655, InterruptedException -> 0x06c7, TryCatch #26 {Exception -> 0x060e, blocks: (B:3:0x000b, B:109:0x01c4, B:111:0x01d4, B:112:0x01d7, B:133:0x0234, B:154:0x0286, B:175:0x02d8, B:196:0x032a, B:217:0x037d, B:238:0x03c9, B:259:0x040f, B:280:0x045c, B:283:0x0470, B:285:0x0477, B:286:0x047b, B:307:0x04b3, B:308:0x04b4, B:408:0x0609), top: B:511:0x000b, outer: #6 }] */
        /* JADX WARN: Removed duplicated region for block: B:308:0x04b4 A[Catch: all -> 0x060a, Exception -> 0x060e, FelicaAccessException -> 0x0655, InterruptedException -> 0x06c7, TRY_LEAVE, TryCatch #26 {Exception -> 0x060e, blocks: (B:3:0x000b, B:109:0x01c4, B:111:0x01d4, B:112:0x01d7, B:133:0x0234, B:154:0x0286, B:175:0x02d8, B:196:0x032a, B:217:0x037d, B:238:0x03c9, B:259:0x040f, B:280:0x045c, B:283:0x0470, B:285:0x0477, B:286:0x047b, B:307:0x04b3, B:308:0x04b4, B:408:0x0609), top: B:511:0x000b, outer: #6 }] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void run() {
            /*
                Method dump skipped, instruction units count: 1892
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfsctrl.CheckInitializationThread.CheckInitWorkerThread.run():void");
        }

        private synchronized void finishFeliCaAccess() {
            if (this._mfa != null) {
                this._mfa.felicaUseEnd();
                this._mfa = null;
            }
        }

        public synchronized void setCancel() {
            interrupt();
            this._faImpl.restart();
            finishFeliCaAccess();
        }

        private synchronized void checkCancel() throws InterruptedException {
            if (isInterrupted()) {
                throw new InterruptedException();
            }
        }
    }
}
