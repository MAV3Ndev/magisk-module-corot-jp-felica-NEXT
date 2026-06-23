package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.RemainedCardsCache;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes3.dex */
abstract class UploadCardsTask extends AsyncParentTaskBase<Result> {
    private static final List<Integer> CONTINUE_GET_DELETE_SCRIPT_ERROR_MAP;
    private static final List<Integer> NEED_ROUNDING_ERROR_LIST;
    protected static final int TASK_ID_GET_LINKAGE_DATA_LIST = 0;
    private final int mActionType;
    protected final MfiChipHolder mChipHolder;
    protected final DataManager mDataManager;
    protected final FwsClient mFwsClient;
    protected boolean mIsContinue;
    protected final RemainedCardsCache mRemainedCards;
    private Result mResult;
    protected final List<RemainedCardsCache.UploadCardInfo> mUploadCardInfoList;

    interface GetLinkageDataSubTaskFinishListener {
        void onError(final int type, final String msg);

        void onSuccess(final List<String> linkageDataList);
    }

    interface UploadRemainedCardsSubTaskFinishListener {
        void onError(final List<String> uploadedCardCidList, final int type, final String msg);

        void onSuccess();
    }

    abstract void createUploadCardInfoList() throws FwsException;

    abstract void startUploadRemainedCardsSubTask(List<String> linkageDataList);

    static {
        ArrayList arrayList = new ArrayList();
        NEED_ROUNDING_ERROR_LIST = arrayList;
        arrayList.add(209);
        arrayList.add(210);
        ArrayList arrayList2 = new ArrayList();
        CONTINUE_GET_DELETE_SCRIPT_ERROR_MAP = arrayList2;
        arrayList2.add(208);
    }

    static class Result {
        final List<String> uploadedCidList;

        Result(List<String> cidList) {
            this.uploadedCidList = cidList;
        }
    }

    UploadCardsTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, RemainedCardsCache remainedCards, int actionType) {
        super(taskId, executor, listener);
        this.mUploadCardInfoList = new ArrayList();
        this.mIsContinue = false;
        this.mFwsClient = fwsClient;
        this.mChipHolder = chipHolder;
        this.mDataManager = dataManager;
        this.mRemainedCards = remainedCards;
        this.mActionType = actionType;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        LogMgr.log(6, "000");
        try {
            createUploadCardInfoList();
            if (this.mUploadCardInfoList.isEmpty()) {
                LogMgr.log(6, "998");
                onFinished(true, 0, null);
                return;
            }
            if (isStopped()) {
                LogMgr.log(2, "702 Already has stopped.");
                onFinished(false, 215, null);
            } else if (createSeInfo()) {
                if (isStopped()) {
                    LogMgr.log(2, "703 Already has stopped.");
                    onFinished(false, 215, null);
                } else {
                    this.mIsContinue = true;
                    LogMgr.log(6, "999");
                }
            }
        } catch (FwsException e) {
            LogMgr.log(2, "700 CardIdInfoMap does not contain serviceType.");
            LogMgr.printStackTrace(7, e);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e));
        } catch (Exception e2) {
            LogMgr.log(2, "701 CardInfo or CardIdInfo is invalid.");
            LogMgr.printStackTrace(7, e2);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e2));
        }
    }

    private boolean createSeInfo() {
        LogMgr.log(6, "000");
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
        boolean z = false;
        try {
            try {
                this.mDataManager.createSeInfo(mfiFelicaWrapper);
                mfiFelicaWrapper.close();
                mfiFelicaWrapper.closeSilently();
                z = true;
            } catch (MfiFelicaException e) {
                LogMgr.log(2, "700 MfiFelicaException");
                LogMgr.printStackTrace(7, e);
                onFinished(false, e.getType(), e.getMessage());
            } catch (GpException e2) {
                LogMgr.log(2, "701 GpException");
                onFinished(false, e2.getType(), e2.getMessage());
            } catch (Exception e3) {
                LogMgr.log(2, "702 " + e3.getClass().getSimpleName() + ":" + e3.getMessage());
                LogMgr.printStackTrace(7, e3);
                onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e3));
            }
            LogMgr.log(6, "999");
            return z;
        } finally {
            mfiFelicaWrapper.closeSilently();
        }
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public synchronized void setResult(Result result) {
        this.mResult = result;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized Result getResult2() {
        return this.mResult;
    }

    protected String[] createUploadCardCidList() {
        ArrayList arrayList = new ArrayList();
        Iterator<RemainedCardsCache.UploadCardInfo> it = this.mUploadCardInfoList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().cid);
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    protected class GetLinkageDataSubTask extends StoppableTaskBase<List<String>> implements AsyncTaskBase.Listener {
        String[] mCidList;
        private a mJwsCreator;
        List<String> mLinkageDataList;
        private final GetLinkageDataSubTaskFinishListener mListener;

        GetLinkageDataSubTask(int taskId, String[] cidList, GetLinkageDataSubTaskFinishListener listener) {
            super(taskId);
            this.mCidList = cidList;
            this.mListener = listener;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
        public void start() {
            LogMgr.log(5, "000");
            a aVarPrepareJwsCreator = prepareJwsCreator();
            this.mJwsCreator = aVarPrepareJwsCreator;
            if (aVarPrepareJwsCreator == null) {
                return;
            }
            if (isStopped()) {
                LogMgr.log(2, "700 Already has stopped.");
                clearJwsCreator(this.mJwsCreator);
                this.mListener.onError(215, null);
            } else {
                GetLinkageDataListTask getLinkageDataListTask = new GetLinkageDataListTask(0, UploadCardsTask.this.mExecutor, this, UploadCardsTask.this.mActionType, this.mCidList, UploadCardsTask.this.mDataManager, this.mJwsCreator, UploadCardsTask.this.mFwsClient, UploadCardsTask.this.mChipHolder);
                UploadCardsTask.this.setStoppableSubTask(getLinkageDataListTask);
                getLinkageDataListTask.start();
                LogMgr.log(5, "999");
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase.Listener
        public void onFinishTask(TaskBase task, boolean isSuccess, int errType, String errMsg) {
            LogMgr.log(5, "000 isSuccess = " + isSuccess);
            clearJwsCreator(this.mJwsCreator);
            if (!isSuccess) {
                if (UploadCardsTask.NEED_ROUNDING_ERROR_LIST.contains(Integer.valueOf(errType))) {
                    LogMgr.log(2, "700 TYPE = " + errType);
                    errMsg = ObfuscatedMsgUtil.executionPoint();
                    errType = 200;
                }
                this.mListener.onError(errType, errMsg);
            } else {
                this.mLinkageDataList = Arrays.asList((String[]) task.getResult());
                if (UploadCardsTask.this.mUploadCardInfoList.size() == this.mLinkageDataList.size()) {
                    this.mListener.onSuccess(this.mLinkageDataList);
                } else {
                    LogMgr.log(1, "800 LinkageDataList size is invalid.");
                    this.mListener.onError(200, "Unknown error.");
                }
            }
            LogMgr.log(5, "999");
        }

        /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
        /* JADX INFO: renamed from: getResult, reason: avoid collision after fix types in other method */
        public List<String> getResult2() {
            return this.mLinkageDataList;
        }

        private a prepareJwsCreator() {
            LogMgr.log(6, "000");
            a aVar = new a();
            try {
                if (Property.isChipGP()) {
                    GpController gpController = UploadCardsTask.this.mChipHolder.getGpController();
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "a", "a");
                    aVar.a(gpController);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "a", "a");
                } else {
                    Felica felica = UploadCardsTask.this.mChipHolder.getFelica();
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_MFC, "a", "a");
                    aVar.a(felica);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_MFC, "a", "a");
                }
                LogMgr.log(6, "999");
                return aVar;
            } catch (Exception e) {
                LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
                LogMgr.printStackTrace(2, e);
                this.mListener.onError(200, "Unknown error.");
                LogMgr.log(6, "998");
                return null;
            }
        }

        private void clearJwsCreator(a jwsCreator) {
            LogMgr.log(6, "000");
            try {
                jwsCreator.a();
            } catch (Exception e) {
                LogMgr.log(2, "700 : Exception");
                LogMgr.printStackTrace(2, e);
            }
            LogMgr.log(6, "999");
        }
    }

    protected abstract class UploadRemainedCardsSubTask extends StoppableTaskBase<List<String>> implements AsyncTaskBase.Listener {
        protected RemainedCardsCache.UploadCardInfo mCardInfoInUploadProcess;
        private final List<String> mCompletedCardCidList;
        protected final Iterator<String> mLinkageDataIterator;
        protected final UploadRemainedCardsSubTaskFinishListener mListener;
        protected final Iterator<RemainedCardsCache.UploadCardInfo> mUploadCardInfoIterator;

        abstract void uploadNextCard();

        UploadRemainedCardsSubTask(int taskId, List<RemainedCardsCache.UploadCardInfo> cardInfoList, List<String> linkageDataList, UploadRemainedCardsSubTaskFinishListener listener) {
            super(taskId);
            this.mCompletedCardCidList = new ArrayList();
            this.mUploadCardInfoIterator = cardInfoList.iterator();
            this.mLinkageDataIterator = linkageDataList.iterator();
            this.mListener = listener;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
        public void start() {
            LogMgr.log(5, "000");
            uploadNextCard();
            LogMgr.log(5, "999");
        }

        private boolean isContinueErrorCode(int errType) {
            LogMgr.log(6, "000 errType = " + errType);
            boolean zContains = UploadCardsTask.CONTINUE_GET_DELETE_SCRIPT_ERROR_MAP.contains(Integer.valueOf(errType));
            LogMgr.log(6, "999 ret = " + zContains);
            return zContains;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase.Listener
        public void onFinishTask(TaskBase task, boolean isSuccess, int errType, String errMsg) {
            LogMgr.log(5, "000 isSuccess= " + isSuccess + ", errType = " + errType);
            if (!isSuccess && !isContinueErrorCode(errType)) {
                if (UploadCardsTask.NEED_ROUNDING_ERROR_LIST.contains(Integer.valueOf(errType))) {
                    LogMgr.log(2, "700 ErrType = " + errType);
                    errMsg = ObfuscatedMsgUtil.executionPoint();
                    errType = 200;
                }
                this.mListener.onError(getResult2(), errType, errMsg);
            } else {
                this.mCompletedCardCidList.add(this.mCardInfoInUploadProcess.cid);
                if (this.mUploadCardInfoIterator.hasNext()) {
                    uploadNextCard();
                } else {
                    LogMgr.log(7, "001");
                    this.mListener.onSuccess();
                }
            }
            LogMgr.log(5, "999");
        }

        /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
        /* JADX INFO: renamed from: getResult, reason: avoid collision after fix types in other method */
        public List<String> getResult2() {
            return this.mCompletedCardCidList;
        }
    }
}
