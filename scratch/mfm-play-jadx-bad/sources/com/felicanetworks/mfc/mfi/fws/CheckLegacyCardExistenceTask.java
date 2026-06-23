package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.LocalPartialCardInfoChecker;
import com.felicanetworks.mfc.mfi.LocalPartialCardInfoJson;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.UnsupportedMfiService1CardCache;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes3.dex */
public class CheckLegacyCardExistenceTask extends AsyncParentTaskBase<Result> {
    private final MfiChipHolder mChipHolder;
    private final DataManager mDataManager;
    private final FwsClient mFwsClient;
    private boolean mIsNeededCheckPrimaryIssue;
    private final a mJwsCreator;
    private final LocalPartialCardInfoChecker mLocalPartialCardInfoChecker;
    private Result mResult;
    private String mServiceId;

    static class Result {
        boolean exist;
        String localPartialCardInfoJson;

        Result() {
        }
    }

    private class ExistsManagementCardChecker implements AsyncTaskBase.Listener {
        private CheckExistsManagementCardTask mTask;

        private ExistsManagementCardChecker() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void check() {
            LogMgr.log(6, "000");
            CheckExistsManagementCardTask checkExistsManagementCardTask = new CheckExistsManagementCardTask(0, CheckLegacyCardExistenceTask.this.mExecutor, this, CheckLegacyCardExistenceTask.this.mFwsClient, CheckLegacyCardExistenceTask.this.mChipHolder, CheckLegacyCardExistenceTask.this.mDataManager, CheckLegacyCardExistenceTask.this.mJwsCreator, CheckLegacyCardExistenceTask.this.mServiceId);
            this.mTask = checkExistsManagementCardTask;
            CheckLegacyCardExistenceTask.this.setStoppableSubTask(checkExistsManagementCardTask);
            this.mTask.start();
            LogMgr.log(6, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase.Listener
        public void onFinishTask(TaskBase task, boolean isSuccess, int errType, String errMsg) {
            LogMgr.log(6, "000");
            if (isSuccess) {
                CheckLegacyCardExistenceTask.this.doLegacyCardCheck(this.mTask.getResult2().exist);
                LogMgr.log(6, "999");
            } else {
                LogMgr.log(6, "700");
                CheckLegacyCardExistenceTask.this.onFinished(false, errType, errMsg);
            }
        }
    }

    CheckLegacyCardExistenceTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, String serviceId, boolean isNeededCheckPrimaryIssue) {
        this(taskId, executor, listener, fwsClient, chipHolder, dataManager, null, serviceId, isNeededCheckPrimaryIssue);
    }

    CheckLegacyCardExistenceTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, a jwsCreator, String serviceId, boolean isNeededCheckPrimaryIssue) {
        super(taskId, executor, listener);
        this.mLocalPartialCardInfoChecker = new LocalPartialCardInfoChecker();
        this.mResult = new Result();
        this.mFwsClient = fwsClient;
        this.mChipHolder = chipHolder;
        this.mDataManager = dataManager;
        this.mJwsCreator = jwsCreator;
        this.mServiceId = serviceId;
        this.mIsNeededCheckPrimaryIssue = isNeededCheckPrimaryIssue;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public Result getResult2() {
        return this.mResult;
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(Result result) {
        this.mResult = result;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncParentTaskBase, com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    public synchronized void stop() {
        super.stop();
        this.mLocalPartialCardInfoChecker.setCancel(true);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        LogMgr.log(6, "000");
        try {
            if (isStopped()) {
                LogMgr.log(2, "700 Already has stopped.");
                onFinished(false, 215, null);
                return;
            }
            if (this.mIsNeededCheckPrimaryIssue) {
                try {
                    checkPrimaryIssue();
                    if (isStopped()) {
                        LogMgr.log(2, "702 Already has stopped.");
                        onFinished(false, 215, null);
                        return;
                    }
                } catch (MfiFelicaException e) {
                    LogMgr.log(2, "701 ERR: checkPrimaryIssue");
                    onFinished(false, e.getType(), e.getMessage());
                    return;
                }
            }
            if (UnsupportedMfiService1CardCache.getInstance().existUnsupportedMfiService1Card()) {
                new ExistsManagementCardChecker().check();
            } else {
                LogMgr.log(6, "998");
                onFinished(true, 0, null);
                return;
            }
        } catch (Exception e2) {
            LogMgr.log(2, e2.getClass().getSimpleName() + ":" + e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e2));
        }
        LogMgr.log(6, "999");
    }

    private void checkPrimaryIssue() throws MfiFelicaException {
        LogMgr.log(6, "000");
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
        try {
            mfiFelicaWrapper.open();
            mfiFelicaWrapper.select(65039);
            mfiFelicaWrapper.getContainerIssueInformationWithCheckIssued();
            mfiFelicaWrapper.close();
            mfiFelicaWrapper.closeSilently();
            LogMgr.log(6, "999");
        } catch (Throwable th) {
            mfiFelicaWrapper.closeSilently();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0135, code lost:
    
        if (r12.getType() == 169) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0146, code lost:
    
        if (r12.getType() == 55) goto L55;
     */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0162  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void doLegacyCardCheck(boolean isExistOnMfi) {
        LogMgr.log(6, "000");
        int i = 215;
        String message = null;
        try {
        } catch (MfiClientException e) {
            LogMgr.log(2, e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            String strFelicaExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
            if (e.getID() == 3) {
                if (e.getType() == 6) {
                    message = e.getMessage();
                    i = 6;
                } else if (e.getType() == 161) {
                    message = e.getMessage();
                    i = 225;
                }
            } else if (e.getID() == 8) {
                i = 55;
            } else if (e.getID() != 1) {
                message = strFelicaExExecutionPoint;
                i = 200;
            } else if (e.getType() == 8) {
                i = 8;
            } else {
                if (e.getMessage() != null) {
                    message = e.getMessage();
                }
                i = 200;
            }
            onFinished(false, i, message);
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, e2.getClass().getSimpleName() + ":" + e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e2));
        } catch (Exception e3) {
            LogMgr.log(2, e3.getClass().getSimpleName() + ":" + e3.getMessage());
            LogMgr.printStackTrace(7, e3);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e3));
        }
        if (isStopped()) {
            LogMgr.log(2, "700 Already has stopped.");
            onFinished(false, 215, null);
            return;
        }
        if (isExistOnMfi) {
            LogMgr.log(6, "996");
            onFinished(true, 0, null);
            return;
        }
        LogMgr.log(6, "001");
        LocalPartialCardInfoJson[] localPartialCardInfoList = this.mLocalPartialCardInfoChecker.getLocalPartialCardInfoList(new String[]{this.mServiceId}, true, this.mChipHolder);
        if (localPartialCardInfoList == null) {
            LogMgr.log(2, "701");
            onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
            return;
        }
        if (localPartialCardInfoList.length == 0) {
            UnsupportedMfiService1CardCache.getInstance().cacheNotExistUnsupportedMfiService1Card();
            LogMgr.log(6, "997");
            onFinished(true, 0, null);
        } else {
            if (localPartialCardInfoList.length == 1) {
                LogMgr.log(6, "998");
                this.mResult.localPartialCardInfoJson = localPartialCardInfoList[0].toString();
                this.mResult.exist = true;
                onFinished(true, 0, null);
                return;
            }
            LogMgr.log(2, "702");
            onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
            LogMgr.log(6, "999");
        }
    }
}
