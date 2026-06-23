package com.felicanetworks.mfsctrl;

import com.felicanetworks.common.cmnctrl.chip.FelicaAccessListener;
import com.felicanetworks.common.cmnctrl.data.FelicaErrorInfo;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;

/* JADX INFO: loaded from: classes.dex */
public class FeliCaAccessImpl implements FelicaAccessListener, FunctionCodeInterface {
    public static final int DATA_NONE = 0;
    public static final int RESULT_COMMUNICATIONERROR = 9;
    public static final int RESULT_FAILED = 12;
    public static final int RESULT_FAILED_FELICATIMEOUT = 6;
    public static final int RESULT_FAILED_INUSE = 2;
    public static final int RESULT_FAILED_LOCK = 3;
    public static final int RESULT_FAILED_MFCOTHER = 7;
    public static final int RESULT_FAILED_MFCPERMINSPECT = 5;
    public static final int RESULT_FAILED_OTHER = 4;
    public static final int RESULT_FELICA_HTTP_ERROR = 11;
    public static final int RESULT_MFIC_NOT_FOUND = 8;
    public static final int RESULT_NONE = 0;
    public static final int RESULT_OVERCROWDING = 10;
    public static final int RESULT_SUCCESS = 1;
    private boolean _pauseFlg = true;
    private int _result = 0;
    private int _pid = 0;
    private String _errorId = null;
    private FelicaErrorInfo _felicaErrInfo = null;

    @Override // com.felicanetworks.common.cmnctrl.chip.FelicaAccessListener
    public void completeActivate() {
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 3;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 33;
    }

    @Override // com.felicanetworks.common.cmnctrl.chip.FelicaAccessListener
    public void finishResult() {
        this._result = 1;
        restart();
    }

    @Override // com.felicanetworks.common.cmnctrl.chip.FelicaAccessListener
    public void errorResult(int i, String str, int i2, FelicaErrorInfo felicaErrorInfo) {
        this._errorId = str;
        this._felicaErrInfo = felicaErrorInfo;
        switch (i) {
            case 0:
                this._result = 2;
                this._pid = i2;
                break;
            case 1:
                this._result = 3;
                break;
            case 2:
            default:
                this._result = 4;
                break;
            case 3:
                this._result = 5;
                break;
            case 4:
                this._result = 6;
                break;
            case 5:
                this._result = 7;
                break;
            case 6:
                this._result = 8;
                break;
            case 7:
                this._result = 9;
                break;
            case 8:
                this._result = 10;
                break;
            case 9:
                this._result = 11;
                break;
            case 10:
                this._result = 12;
                break;
        }
        restart();
    }

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

    public int getPid() {
        return this._pid;
    }

    public String getErrorId() {
        return this._errorId;
    }

    public FelicaErrorInfo getFelicaErrInfo() {
        return this._felicaErrInfo;
    }
}
