package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.CardIdentifiableBlockData;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ReadSeResult {
    private boolean mAvailableArea = false;
    private List<CardIdentifiableBlockData> mReadCiaBlockList = null;

    public void setAvailableArea(boolean z) {
        this.mAvailableArea = z;
    }

    public void setReadCiaBlockList(List<CardIdentifiableBlockData> list) {
        this.mReadCiaBlockList = list;
    }

    public boolean isAvailableArea() {
        return this.mAvailableArea;
    }

    public List<CardIdentifiableBlockData> getReadCiaBlockList() {
        return this.mReadCiaBlockList;
    }
}
