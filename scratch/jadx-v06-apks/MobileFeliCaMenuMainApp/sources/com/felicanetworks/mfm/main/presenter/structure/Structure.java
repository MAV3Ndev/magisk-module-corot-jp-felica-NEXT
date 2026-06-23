package com.felicanetworks.mfm.main.presenter.structure;

import android.content.Intent;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.internal.AppObserver;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes.dex */
public class Structure {
    protected StructureType _type;

    public Structure() {
        this._type = null;
        this._type = null;
    }

    protected Structure(StructureType structureType) {
        this._type = null;
        this._type = structureType;
    }

    public StructureType getType() {
        return this._type;
    }

    public void detectException(Exception exc) {
        LogUtil.error(exc);
        StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, this, new MfmException(getClass(), 1, exc));
    }

    public void detectOnNewIntent(Intent intent) {
        ((AppObserver) PresenterData.getInstance().getContext()).onActivityNewIntent(intent);
    }
}
