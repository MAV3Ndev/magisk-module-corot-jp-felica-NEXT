package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes.dex */
public class RebootStructure extends Structure {
    private String mAction;
    private ExtraInfo mExtraInfo;

    public static class ExtraInfo<T> {
        public String key;
        public T value;

        public ExtraInfo(String str, T t) {
            this.key = str;
            this.value = t;
        }
    }

    public void setAction(String str) {
        this.mAction = str;
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
