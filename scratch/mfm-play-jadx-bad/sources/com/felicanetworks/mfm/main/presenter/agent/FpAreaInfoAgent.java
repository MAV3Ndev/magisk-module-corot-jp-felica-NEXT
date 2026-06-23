package com.felicanetworks.mfm.main.presenter.agent;

import com.felicanetworks.mfm.main.model.info.FpAreaInfo;
import com.felicanetworks.mfm.main.model.info.FpServiceInfo;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class FpAreaInfoAgent extends InfoAgent {
    private FpAreaInfo _client;

    public FpAreaInfoAgent(FpAreaInfo client) {
        if (client == null) {
            throw new IllegalArgumentException("client is null");
        }
        this._client = client;
    }

    public long getFpNum() {
        return this._client.getFpNum();
    }

    public int getTotalPocket() {
        return this._client.getTotalPocket();
    }

    public int getUsedPocket() {
        return this._client.getUsedPocket();
    }

    public List<FpServiceInfoAgent> getFpServiceList() {
        ArrayList arrayList = new ArrayList();
        List<FpServiceInfo> fpServiceList = this._client.getFpServiceList();
        if (fpServiceList != null) {
            try {
                Iterator<FpServiceInfo> it = fpServiceList.iterator();
                while (it.hasNext()) {
                    arrayList.add(new FpServiceInfoAgent(it.next()));
                }
            } catch (Exception e) {
                MfmException mfmException = new MfmException(getClass(), 117, e);
                LogUtil.error(mfmException);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
            }
        }
        return arrayList;
    }
}
