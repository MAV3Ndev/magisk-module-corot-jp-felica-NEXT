package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class ReadCiaBlockListTask extends StoppableTaskBase<Result> {
    private final MfiChipHolder mChipHolder;
    private final CardIdentifiableInfo.Cache mIdentifiableInfo;
    private Result mResult;

    public class Result {
        public boolean isSuccess = false;
        public ReadSeResult readSeResult = null;
        public int errType = 200;
        public String errMsg = null;

        public Result() {
        }
    }

    public ReadCiaBlockListTask(int i, CardIdentifiableInfo.Cache cache, MfiChipHolder mfiChipHolder) {
        super(i);
        this.mIdentifiableInfo = cache;
        this.mChipHolder = mfiChipHolder;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
    public void start() {
        LogMgr.log(4, "000");
        this.mResult = new Result();
        if (isStopped()) {
            LogMgr.log(2, "700 Already has stopped.");
            this.mResult.isSuccess = false;
            this.mResult.errType = 215;
            this.mResult.errMsg = null;
            return;
        }
        try {
            LogMgr.log(7, "001");
            CardIdentifiableInfoChecker cardIdentifiableInfoChecker = new CardIdentifiableInfoChecker();
            this.mResult.readSeResult = cardIdentifiableInfoChecker.getReadCiaBlockList(this.mChipHolder, this.mIdentifiableInfo);
            LogMgr.log(7, "002");
            if (isStopped()) {
                LogMgr.log(2, "702 Already has stopped.");
                this.mResult.isSuccess = false;
                this.mResult.errType = 215;
                this.mResult.errMsg = null;
                return;
            }
            this.mResult.isSuccess = true;
            LogMgr.log(4, "999");
        } catch (FwsException e) {
            LogMgr.log(2, "%s %s:%s", "701", e.getClass().getSimpleName(), e.getMessage());
            this.mResult.isSuccess = false;
            this.mResult.errType = e.getType();
            this.mResult.errMsg = e.getMessage();
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public Result getResult2() {
        return this.mResult;
    }
}
