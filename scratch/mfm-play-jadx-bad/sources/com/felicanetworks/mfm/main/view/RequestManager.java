package com.felicanetworks.mfm.main.view;

import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.presenter.RequestCaster;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.StructureType;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class RequestManager implements RequestCaster.RequestObserver {
    private static RequestManager mInstance;
    private final List<RequestListener> mListenerPool = new ArrayList();

    public interface RequestListener {
        void onRequest(Structure structure);
    }

    private RequestManager() {
    }

    public static synchronized RequestManager getInstance() {
        if (mInstance == null) {
            mInstance = new RequestManager();
        }
        return mInstance;
    }

    public void initialize() throws MfmException {
        RequestCaster.registerObservers(StructureType.values(), this);
        synchronized (this.mListenerPool) {
            this.mListenerPool.clear();
        }
    }

    public void registerRequestListener(RequestListener listener) {
        synchronized (this.mListenerPool) {
            if (this.mListenerPool.contains(listener)) {
                this.mListenerPool.remove(listener);
            }
            this.mListenerPool.add(listener);
        }
    }

    public void unregisterRequestListener(RequestListener listener) {
        synchronized (this.mListenerPool) {
            try {
                this.mListenerPool.remove(listener);
            } catch (Exception unused) {
            }
        }
    }

    public void unregisterAllRequestListener() {
        synchronized (this.mListenerPool) {
            try {
                this.mListenerPool.clear();
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.presenter.RequestCaster.RequestObserver
    public void onRequest(Structure structure) {
        try {
            synchronized (this.mListenerPool) {
                if (this.mListenerPool.size() < 1) {
                    return;
                }
                List<RequestListener> list = this.mListenerPool;
                list.get(list.size() - 1).onRequest(structure);
            }
        } catch (Exception e) {
            structure.detectException(e);
        }
    }
}
