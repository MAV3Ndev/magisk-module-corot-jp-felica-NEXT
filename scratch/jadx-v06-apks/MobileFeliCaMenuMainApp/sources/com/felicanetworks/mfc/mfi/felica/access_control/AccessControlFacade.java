package com.felicanetworks.mfc.mfi.felica.access_control;

import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.aim.CheckVersionUpTask;
import com.felicanetworks.mfc.mfi.fws.GetPermitTask;
import com.felicanetworks.mfc.mfi.fws.StoppableTaskBase;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Permit;
import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public class AccessControlFacade {
    private StoppableTaskBase mCurrentTask;
    private boolean mIsStarted = false;
    private OnFinishListener mListener;
    private String mSepId;

    public interface OnFinishListener {
        void onGetPermitFinished(boolean z, Permit permit, int i, String str);

        void onVersionUpCheckFinished(boolean z, boolean z2, int i, String str);
    }

    public void start(OnFinishListener onFinishListener, String str) throws MfiClientException {
        LogMgr.log(4, "%s listener=%s sepId=%s", "000", onFinishListener, str);
        if (this.mIsStarted) {
            LogMgr.log(1, "%s : Aleady running task.", "800");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        if (onFinishListener == null) {
            LogMgr.log(1, "%s : Listener is null.", "801");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        if (str == null) {
            LogMgr.log(1, "%s : Listener is null.", "801");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        this.mListener = onFinishListener;
        this.mSepId = str;
        this.mIsStarted = true;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void finish() {
        LogMgr.log(4, "%s", "000");
        this.mListener = null;
        this.mSepId = null;
        this.mIsStarted = false;
        this.mCurrentTask = null;
        LogMgr.log(4, "%s", "999");
    }

    public void stop() {
        LogMgr.log(4, "%s", "000");
        StoppableTaskBase stoppableTaskBase = this.mCurrentTask;
        if (stoppableTaskBase != null) {
            stoppableTaskBase.stop();
        }
        LogMgr.log(4, "%s", "999");
    }

    public void checkVersionUp() {
        LogMgr.log(4, "%s", "000");
        CheckVersionUpTask checkVersionUpTask = new CheckVersionUpTask(this.mSepId);
        this.mCurrentTask = checkVersionUpTask;
        checkVersionUpTask.start();
        CheckVersionUpTask.Result result2 = ((CheckVersionUpTask) this.mCurrentTask).getResult2();
        this.mListener.onVersionUpCheckFinished(result2.isSuccess, result2.needsUpdate, result2.errType, result2.errMsg);
        LogMgr.log(4, "%s", "999");
    }

    public void getPermit(JSONArray jSONArray, String str) {
        LogMgr.log(4, "%s", "000");
        GetPermitTask getPermitTask = new GetPermitTask(jSONArray, str, this.mSepId);
        this.mCurrentTask = getPermitTask;
        getPermitTask.start();
        GetPermitTask.Result result2 = ((GetPermitTask) this.mCurrentTask).getResult2();
        this.mListener.onGetPermitFinished(result2.isSuccess, result2.permit, result2.errType, result2.errMsg);
        LogMgr.log(4, "%s", "999");
    }
}
