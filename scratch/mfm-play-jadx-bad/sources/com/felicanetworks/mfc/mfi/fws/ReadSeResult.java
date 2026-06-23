package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.CardIdentifiableBlockData;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class ReadSeResult {
    private boolean mAvailableArea = false;
    private List<CardIdentifiableBlockData> mReadCiaBlockList = null;

    public void setAvailableArea(boolean availableArea) {
        this.mAvailableArea = availableArea;
    }

    public void setReadCiaBlockList(List<CardIdentifiableBlockData> readCiaBlockList) {
        this.mReadCiaBlockList = readCiaBlockList;
    }

    public boolean isAvailableArea() {
        return this.mAvailableArea;
    }

    public List<CardIdentifiableBlockData> getReadCiaBlockList() {
        return this.mReadCiaBlockList;
    }
}
