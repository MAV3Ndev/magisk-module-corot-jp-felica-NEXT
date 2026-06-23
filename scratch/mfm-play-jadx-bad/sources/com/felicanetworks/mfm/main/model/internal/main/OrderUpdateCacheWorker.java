package com.felicanetworks.mfm.main.model.internal.main;

import android.content.Intent;
import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.FelicaPocketFunc;
import com.felicanetworks.mfm.main.model.MfiCardFunc;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.internal.main.FuncUtil;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.fix.MfiLoginResultCode;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes3.dex */
public class OrderUpdateCacheWorker extends Thread {
    public static String NAME = "OrderUpdateCacheWorker";
    private OrderUpdateCacheListener _OrderUpdateCache_listener;
    private CentralFuncEntity _centralFunc;
    private ModelContext _context;
    private MfiCardFunc _mfiCardFunc;
    private ModelErrorInfo _modelErrorInfo = null;

    interface OrderUpdateCacheListener {
        void onCompleted();

        void onError(ModelErrorInfo e);
    }

    OrderUpdateCacheWorker(ModelContext context, CentralFuncEntity centralFunc, MfiCardFunc mfiCardFunc, OrderUpdateCacheListener orderUpdateCacheListener) {
        this._context = context;
        this._centralFunc = centralFunc;
        this._mfiCardFunc = mfiCardFunc;
        this._OrderUpdateCache_listener = orderUpdateCacheListener;
    }

    protected OrderUpdateCacheWorker() {
    }

    public void cancel() {
        this._mfiCardFunc.cancel();
    }

    public boolean isError() {
        return this._modelErrorInfo != null;
    }

    public void updateErrorInitialization() {
        this._modelErrorInfo = null;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        this._modelErrorInfo = null;
        try {
            try {
                try {
                    final CountDownLatch countDownLatch = new CountDownLatch(1);
                    this._centralFunc.compile(NAME + "_compile", true, true, new CentralFunc.CompileListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.OrderUpdateCacheWorker.1
                        @Override // com.felicanetworks.mfm.main.model.CentralFunc.CompileListener
                        public void onCompleted() {
                            if (OrderUpdateCacheWorker.this._centralFunc.getCompiledState().getFelicaState() != CentralFunc.CompiledState.FelicaState.LOCK_ERROR) {
                                if (OrderUpdateCacheWorker.this._centralFunc.getCompiledState().getFelicaState() != CentralFunc.CompiledState.FelicaState.OPEN_ERROR) {
                                    if (OrderUpdateCacheWorker.this._centralFunc.getCompiledState().getFelicaState() == CentralFunc.CompiledState.FelicaState.TIMEOUT_ERROR) {
                                        OrderUpdateCacheWorker.this._modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.FELICA_TIMEOUT_ERROR, new MfmException(getClass(), 259));
                                    }
                                } else {
                                    OrderUpdateCacheWorker.this._modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.FELICA_OPEN_ERROR, new MfmException(getClass(), 258));
                                }
                            } else {
                                OrderUpdateCacheWorker.this._modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.LOCKED_FELICA, new MfmException(getClass(), 257));
                            }
                            countDownLatch.countDown();
                        }

                        @Override // com.felicanetworks.mfm.main.model.CentralFunc.CompileListener
                        public void onError(ModelErrorInfo modelErrorInfo) {
                            OrderUpdateCacheWorker.this._modelErrorInfo = modelErrorInfo;
                            countDownLatch.countDown();
                        }
                    });
                    try {
                        countDownLatch.await();
                        ModelErrorInfo modelErrorInfo = this._modelErrorInfo;
                        if (modelErrorInfo != null) {
                            this._OrderUpdateCache_listener.onError(modelErrorInfo);
                            this._centralFunc.mfcFinish();
                        } else {
                            checkInterrupted();
                            final CountDownLatch countDownLatch2 = new CountDownLatch(1);
                            String strLoadMfiAccountHash = ServicePreference.getInstance().loadMfiAccountHash(this._context.getAndroidContext());
                            final FuncUtil.AsyncPacket asyncPacket = new FuncUtil.AsyncPacket();
                            asyncPacket.set(false);
                            this._mfiCardFunc.compile(NAME + "_mfi_compile", this._centralFunc, strLoadMfiAccountHash, MfiLoginResultCode.NONE.getCode(), 0, true, new MfiCardFunc.CompileListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.OrderUpdateCacheWorker.2
                                @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.CompileListener
                                public void onChangedAccount(String newAccountHash, String oldAccountHash) {
                                    OrderUpdateCacheWorker.this._modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(OrderUpdateCacheWorker.class, 257));
                                    countDownLatch2.countDown();
                                }

                                @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.CompileListener
                                public void onRequestActivity(Intent intent) {
                                    OrderUpdateCacheWorker.this._modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(OrderUpdateCacheWorker.class, 258));
                                    countDownLatch2.countDown();
                                }

                                @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.CompileListener
                                public void onCompleted(List<MyCardInfo> myCardInfoList, MfiCardFunc.CompiledState compiledState) {
                                    if (MfiCardFunc.CompiledState.MfiCardState.NEED_CARD_RECOVERY == compiledState.getMfiCardState()) {
                                        asyncPacket.set(true);
                                    } else {
                                        OrderUpdateCacheWorker.this._modelErrorInfo = compiledState.getMficWarningState();
                                    }
                                    countDownLatch2.countDown();
                                }

                                @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.CompileListener
                                public void onError(ModelErrorInfo modelErrorInfo2) {
                                    OrderUpdateCacheWorker.this._modelErrorInfo = modelErrorInfo2;
                                    countDownLatch2.countDown();
                                }
                            });
                            try {
                                countDownLatch2.await();
                                ModelErrorInfo modelErrorInfo2 = this._modelErrorInfo;
                                if (modelErrorInfo2 != null) {
                                    if (modelErrorInfo2.getType() != ModelErrorInfo.Type.MFIC_INTERRUPTED_ERROR) {
                                        this._centralFunc.mfcFinish();
                                    }
                                    this._OrderUpdateCache_listener.onError(this._modelErrorInfo);
                                } else {
                                    if (Boolean.TRUE.equals(asyncPacket.get())) {
                                        executeCardRecovery();
                                    } else {
                                        this._modelErrorInfo = orderAssetAndReadQPType();
                                    }
                                    if (isInterrupted()) {
                                        this._OrderUpdateCache_listener.onError(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(OrderUpdateCacheWorker.class, 290)));
                                    } else {
                                        this._centralFunc.mfcFinish();
                                        ModelErrorInfo modelErrorInfo3 = this._modelErrorInfo;
                                        if (modelErrorInfo3 == null) {
                                            this._OrderUpdateCache_listener.onCompleted();
                                        } else {
                                            this._OrderUpdateCache_listener.onError(modelErrorInfo3);
                                        }
                                    }
                                }
                            } catch (InterruptedException e) {
                                throw e;
                            }
                        }
                    } catch (InterruptedException e2) {
                        throw e2;
                    }
                } catch (InterruptedException e3) {
                    this._OrderUpdateCache_listener.onError(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(OrderUpdateCacheWorker.class, 289, e3)));
                }
            } catch (Exception e4) {
                this._OrderUpdateCache_listener.onError(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(OrderUpdateCacheWorker.class, 288, e4)));
                this._centralFunc.mfcFinish();
            }
        } finally {
            this._centralFunc.clearFelicaFpState();
            this._mfiCardFunc.finishBackground();
        }
    }

    @Override // java.lang.Thread
    public void interrupt() {
        super.interrupt();
    }

    private void checkInterrupted() throws InterruptedException {
        if (isInterrupted()) {
            throw new InterruptedException();
        }
    }

    private ModelErrorInfo orderAssetAndReadQPType() throws InterruptedException {
        checkInterrupted();
        this._centralFunc.orderAsset(this._mfiCardFunc);
        FelicaPocketFunc.CompiledFpState compiledFpState = this._centralFunc.getCompiledFpState();
        if (compiledFpState.getFelicaFPState() != FelicaPocketFunc.CompiledFpState.FelicaFpState.NO_PROBLEM) {
            return compiledFpState.getModelErrorInfo();
        }
        checkInterrupted();
        this._centralFunc.readQPType(this._mfiCardFunc.getMyCardDetectSet());
        return compiledFpState.getModelErrorInfo();
    }

    private void executeCardRecovery() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this._mfiCardFunc.executeCardRecovery(new MfiCardFunc.RecoveryListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.OrderUpdateCacheWorker.3
            @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.RecoveryListener
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        final CountDownLatch countDownLatch2 = new CountDownLatch(1);
        String strLoadMfiAccountHash = ServicePreference.getInstance().loadMfiAccountHash(this._context.getAndroidContext());
        this._mfiCardFunc.compile(NAME + "_mfi_recovery_compile", this._centralFunc, strLoadMfiAccountHash, MfiLoginResultCode.NONE.getCode(), 0, true, new MfiCardFunc.CompileListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.OrderUpdateCacheWorker.4
            @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.CompileListener
            public void onChangedAccount(String newAccountHash, String oldAccountHash) {
                OrderUpdateCacheWorker.this._modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(OrderUpdateCacheWorker.class, 305));
                countDownLatch2.countDown();
            }

            @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.CompileListener
            public void onRequestActivity(Intent intent) {
                OrderUpdateCacheWorker.this._modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(OrderUpdateCacheWorker.class, 306));
                countDownLatch2.countDown();
            }

            @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.CompileListener
            public void onCompleted(List<MyCardInfo> myCardInfoList, MfiCardFunc.CompiledState compiledState) {
                MfiCardFunc.CompiledState.MfiCardState mfiCardState = MfiCardFunc.CompiledState.MfiCardState.NEED_CARD_RECOVERY;
                compiledState.getMfiCardState();
                OrderUpdateCacheWorker.this._modelErrorInfo = compiledState.getMficWarningState();
                countDownLatch2.countDown();
            }

            @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.CompileListener
            public void onError(ModelErrorInfo modelErrorInfo) {
                OrderUpdateCacheWorker.this._modelErrorInfo = modelErrorInfo;
                countDownLatch2.countDown();
            }
        });
        countDownLatch2.await();
        if (this._modelErrorInfo == null) {
            try {
                this._modelErrorInfo = orderAssetAndReadQPType();
            } catch (InterruptedException e) {
                this._modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.MFIC_INTERRUPTED_ERROR, new MfmException(OrderUpdateCacheWorker.class, 307, e));
            }
        }
    }
}
