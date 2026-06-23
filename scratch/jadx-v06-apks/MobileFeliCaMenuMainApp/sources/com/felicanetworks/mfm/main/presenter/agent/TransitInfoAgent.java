package com.felicanetworks.mfm.main.presenter.agent;

import com.felicanetworks.mfm.main.model.info.TransitInfo;
import com.felicanetworks.mfm.main.model.info.TransitPassInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TransitInfoAgent extends InfoAgent {
    private TransitInfo _client;

    public TransitInfoAgent(TransitInfo transitInfo) {
        this._client = transitInfo;
    }

    public String getIdiDisplayName() {
        return this._client.cardNumber.displayName;
    }

    public String getIdiValue() {
        return this._client.cardNumber.value;
    }

    public List<TransitPassInfoAgent> getTransitPassInfoList() {
        ArrayList arrayList = new ArrayList();
        Iterator<TransitPassInfo> it = this._client.transitPassInfoList.iterator();
        while (it.hasNext()) {
            arrayList.add(new TransitPassInfoAgent(it.next()));
        }
        return arrayList;
    }
}
