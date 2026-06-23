package com.felicanetworks.mfm.main.presenter.agent;

import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MyFavoriteServiceInfoAgent extends MyServiceInfoAgent {
    MyFavoriteServiceInfoAgent(MyServiceInfo service, List<MyCardInfo> cards, boolean isMfiLoggedIn, boolean isChaced) {
        super(service, cards, isMfiLoggedIn, isChaced);
    }
}
