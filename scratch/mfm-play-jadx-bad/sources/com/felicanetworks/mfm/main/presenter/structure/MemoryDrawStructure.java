package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.presenter.agent.IFuncMemoryUsage;
import com.felicanetworks.mfm.main.presenter.agent.MemoryUsageFuncAgent;

/* JADX INFO: loaded from: classes3.dex */
public class MemoryDrawStructure extends CloseDrawStructure implements IFuncMemoryUsage {
    private MemoryUsageFuncAgent _memoryUsageFuncAgent;

    public MemoryDrawStructure(MemoryUsageFuncAgent memoryUsageFuncAgent) {
        super(StructureType.MEMORY);
        this._memoryUsageFuncAgent = memoryUsageFuncAgent;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncMemoryUsage
    public MemoryUsageFuncAgent getMemoryUsageFunc() {
        return this._memoryUsageFuncAgent;
    }
}
