package com.felicanetworks.mfm.main.presenter.internal.notification;

import com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent;

/* JADX INFO: loaded from: classes.dex */
public class NoticeChangeDataListenerLazyKeeper {
    private static NoticeFuncAgent.ChangeDataListener listener;

    public static synchronized void setListener(NoticeFuncAgent.ChangeDataListener changeDataListener) {
        listener = changeDataListener;
    }

    public static synchronized NoticeFuncAgent.ChangeDataListener getListener() {
        return listener;
    }
}
