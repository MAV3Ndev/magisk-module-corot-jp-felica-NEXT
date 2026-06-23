package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.RemainedCardsCache;
import com.felicanetworks.mfc.mfi.fws.UploadCardsTask;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.List;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes3.dex */
class UploadCardsToDisableTask extends UploadCardsTask {
    private static final int TASK_ID_DISABLE_CARD = 1;

    @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask
    void createUploadCardInfoList() throws FwsException {
    }

    UploadCardsToDisableTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, RemainedCardsCache remainedCards) {
        super(taskId, executor, listener, fwsClient, chipHolder, dataManager, remainedCards, -99);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask
    void startUploadRemainedCardsSubTask(List<String> linkageDataList) {
        DisableRemainedCardsSubTask disableRemainedCardsSubTask = new DisableRemainedCardsSubTask(1, this.mUploadCardInfoList, linkageDataList, new UploadCardsTask.UploadRemainedCardsSubTaskFinishListener() { // from class: com.felicanetworks.mfc.mfi.fws.UploadCardsToDisableTask.1
            @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask.UploadRemainedCardsSubTaskFinishListener
            public void onSuccess() {
                LogMgr.log(6, "000");
                UploadCardsToDisableTask.this.onFinished(true, 0, null);
                LogMgr.log(6, "999");
            }

            @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask.UploadRemainedCardsSubTaskFinishListener
            public void onError(final List<String> uploadedCardCidList, final int type, final String msg) {
                LogMgr.log(6, "000");
                UploadCardsToDisableTask.this.setResult(new UploadCardsTask.Result(uploadedCardCidList));
                UploadCardsToDisableTask.this.onFinished(false, type, msg);
                LogMgr.log(6, "999");
            }
        });
        setStoppableSubTask(disableRemainedCardsSubTask);
        disableRemainedCardsSubTask.start();
    }

    private class DisableRemainedCardsSubTask extends UploadCardsTask.UploadRemainedCardsSubTask {
        DisableRemainedCardsSubTask(int taskId, List<RemainedCardsCache.UploadCardInfo> cardInfoList, List<String> linkageDataList, UploadCardsTask.UploadRemainedCardsSubTaskFinishListener listener) {
            super(taskId, cardInfoList, linkageDataList, listener);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask.UploadRemainedCardsSubTask
        void uploadNextCard() {
            DisableRemainedCardsSubTask disableRemainedCardsSubTask;
            LogMgr.log(6, "000");
            try {
                this.mCardInfoInUploadProcess = this.mUploadCardInfoIterator.next();
                disableRemainedCardsSubTask = this;
            } catch (Exception e) {
                e = e;
                disableRemainedCardsSubTask = this;
            }
            try {
                DisableCardTask disableCardTask = new DisableCardTask(1, UploadCardsToDisableTask.this.mExecutor, disableRemainedCardsSubTask, this.mCardInfoInUploadProcess.cid, this.mCardInfoInUploadProcess.cardIdInfo, UploadCardsToDisableTask.this.mFwsClient, UploadCardsToDisableTask.this.mChipHolder, UploadCardsToDisableTask.this.mDataManager, this.mLinkageDataIterator.next());
                UploadCardsToDisableTask.this.setStoppableSubTask(disableCardTask);
                disableCardTask.start();
            } catch (Exception e2) {
                e = e2;
                LogMgr.log(1, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
                LogMgr.printStackTrace(7, e);
                disableRemainedCardsSubTask.mListener.onError(getResult2(), 200, ObfuscatedMsgUtil.exExecutionPoint(e));
            }
            LogMgr.log(6, "999");
        }
    }
}
