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
class UploadCardsToDeleteTask extends UploadCardsTask {
    private static final int TASK_ID_DELETE_CARD = 1;

    UploadCardsToDeleteTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, RemainedCardsCache remainedCards) {
        super(taskId, executor, listener, fwsClient, chipHolder, dataManager, remainedCards, 2);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask, com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        LogMgr.log(6, "000");
        super.run();
        if (this.mIsContinue) {
            UploadCardsTask.GetLinkageDataSubTask getLinkageDataSubTask = new UploadCardsTask.GetLinkageDataSubTask(0, createUploadCardCidList(), new UploadCardsTask.GetLinkageDataSubTaskFinishListener() { // from class: com.felicanetworks.mfc.mfi.fws.UploadCardsToDeleteTask.1
                @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask.GetLinkageDataSubTaskFinishListener
                public void onSuccess(final List<String> linkageDataList) {
                    LogMgr.log(6, "000");
                    UploadCardsToDeleteTask.this.startUploadRemainedCardsSubTask(linkageDataList);
                    LogMgr.log(6, "999");
                }

                @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask.GetLinkageDataSubTaskFinishListener
                public void onError(final int type, final String msg) {
                    LogMgr.log(6, "000");
                    UploadCardsToDeleteTask.this.onFinished(false, type, msg);
                    LogMgr.log(6, "999");
                }
            });
            setStoppableSubTask(getLinkageDataSubTask);
            getLinkageDataSubTask.start();
            LogMgr.log(6, "999");
        }
    }

    @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask
    void createUploadCardInfoList() throws FwsException {
        this.mUploadCardInfoList.addAll(this.mRemainedCards.createDeleteCardInfoList());
    }

    @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask
    void startUploadRemainedCardsSubTask(List<String> linkageDataList) {
        DeleteRemainedCardsSubTask deleteRemainedCardsSubTask = new DeleteRemainedCardsSubTask(1, this.mUploadCardInfoList, linkageDataList, new UploadCardsTask.UploadRemainedCardsSubTaskFinishListener() { // from class: com.felicanetworks.mfc.mfi.fws.UploadCardsToDeleteTask.2
            @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask.UploadRemainedCardsSubTaskFinishListener
            public void onSuccess() {
                LogMgr.log(6, "000");
                UploadCardsToDeleteTask.this.onFinished(true, 0, null);
                LogMgr.log(6, "999");
            }

            @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask.UploadRemainedCardsSubTaskFinishListener
            public void onError(final List<String> uploadedCardCidList, final int type, final String msg) {
                LogMgr.log(6, "000");
                UploadCardsToDeleteTask.this.setResult(new UploadCardsTask.Result(uploadedCardCidList));
                UploadCardsToDeleteTask.this.onFinished(false, type, msg);
                LogMgr.log(6, "999");
            }
        });
        setStoppableSubTask(deleteRemainedCardsSubTask);
        deleteRemainedCardsSubTask.start();
    }

    private class DeleteRemainedCardsSubTask extends UploadCardsTask.UploadRemainedCardsSubTask {
        DeleteRemainedCardsSubTask(int taskId, List<RemainedCardsCache.UploadCardInfo> cardInfoList, List<String> linkageDataList, UploadCardsTask.UploadRemainedCardsSubTaskFinishListener listener) {
            super(taskId, cardInfoList, linkageDataList, listener);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.UploadCardsTask.UploadRemainedCardsSubTask
        void uploadNextCard() {
            DeleteRemainedCardsSubTask deleteRemainedCardsSubTask;
            LogMgr.log(6, "000");
            try {
                this.mCardInfoInUploadProcess = this.mUploadCardInfoIterator.next();
                deleteRemainedCardsSubTask = this;
            } catch (Exception e) {
                e = e;
                deleteRemainedCardsSubTask = this;
            }
            try {
                DeleteCardTask deleteCardTask = new DeleteCardTask(1, UploadCardsToDeleteTask.this.mExecutor, deleteRemainedCardsSubTask, this.mCardInfoInUploadProcess.cid, this.mCardInfoInUploadProcess.cardIdInfo, this.mLinkageDataIterator.next(), UploadCardsToDeleteTask.this.mFwsClient, UploadCardsToDeleteTask.this.mChipHolder, UploadCardsToDeleteTask.this.mDataManager, this.mCardInfoInUploadProcess.serviceType);
                UploadCardsToDeleteTask.this.setStoppableSubTask(deleteCardTask);
                deleteCardTask.start();
            } catch (Exception e2) {
                e = e2;
                LogMgr.log(1, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
                LogMgr.printStackTrace(7, e);
                deleteRemainedCardsSubTask.mListener.onError(getResult2(), 200, ObfuscatedMsgUtil.exExecutionPoint(e));
            }
            LogMgr.log(6, "999");
        }
    }
}
