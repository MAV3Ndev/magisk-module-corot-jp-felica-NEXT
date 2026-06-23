package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.LocalPartialCardInfoChecker;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes.dex */
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
        public void onFinishTask(TaskBase taskBase, boolean z, int i, String str) throws Throwable {
            LogMgr.log(6, "000");
            if (z) {
                CheckLegacyCardExistenceTask.this.doLegacyCardCheck(this.mTask.getResult2().exist);
                LogMgr.log(6, "999");
            } else {
                LogMgr.log(6, "700");
                CheckLegacyCardExistenceTask.this.onFinished(false, i, str);
            }
        }
    }

    CheckLegacyCardExistenceTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, FwsClient fwsClient, MfiChipHolder mfiChipHolder, DataManager dataManager, String str, boolean z) {
        this(i, executorService, listener, fwsClient, mfiChipHolder, dataManager, null, str, z);
    }

    CheckLegacyCardExistenceTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, FwsClient fwsClient, MfiChipHolder mfiChipHolder, DataManager dataManager, a aVar, String str, boolean z) {
        super(i, executorService, listener);
        this.mLocalPartialCardInfoChecker = new LocalPartialCardInfoChecker();
        this.mResult = new Result();
        this.mFwsClient = fwsClient;
        this.mChipHolder = mfiChipHolder;
        this.mDataManager = dataManager;
        this.mJwsCreator = aVar;
        this.mServiceId = str;
        this.mIsNeededCheckPrimaryIssue = z;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public Result getResult2() {
        return this.mResult;
    }

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
            new ExistsManagementCardChecker().check();
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
    /* JADX WARN: Code restructure failed: missing block: B:38:0x012e, code lost:
    
        if (r12.getType() == 169) goto L55;
     */
    /* JADX WARN: Removed duplicated region for block: B:53:0x015a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void doLegacyCardCheck(boolean r12) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 358
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.fws.CheckLegacyCardExistenceTask.doLegacyCardCheck(boolean):void");
    }
}
