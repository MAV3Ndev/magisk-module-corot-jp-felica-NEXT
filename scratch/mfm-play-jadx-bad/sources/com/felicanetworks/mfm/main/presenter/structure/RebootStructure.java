package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public class RebootStructure extends Structure {
    private String mAction;
    private ExtraInfo mExtraInfo;

    public static class ExtraInfo<T> {
        public String key;
        public T value;

        public ExtraInfo(String key, T value) {
            this.key = key;
            this.value = value;
        }
    }

    public void setAction(String action) {
        this.mAction = action;
    }

    public String getAction() {
        return this.mAction;
    }

    public ExtraInfo getExtraInfo() {
        return this.mExtraInfo;
    }

    public void setExtraInfo(ExtraInfo extraInfo) {
        this.mExtraInfo = extraInfo;
    }

    public RebootStructure() {
        super(StructureType.REBOOT);
        this.mExtraInfo = null;
    }

    public void actCompleted() {
        StateController.postStateEvent(StateMachine.Event.EV_REBOOT_COMPLETE, this, new Object[0]);
    }
}
