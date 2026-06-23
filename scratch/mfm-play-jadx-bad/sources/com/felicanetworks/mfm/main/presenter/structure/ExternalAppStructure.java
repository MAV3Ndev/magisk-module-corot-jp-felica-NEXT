package com.felicanetworks.mfm.main.presenter.structure;

import android.content.Intent;
import com.felicanetworks.mfm.main.presenter.action.IActionAppResult;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ExternalAppStructure extends Structure implements IActionAppResult {
    public abstract Intent getDefaultIntent();

    protected ExternalAppStructure(StructureType type) {
        super(type);
    }
}
