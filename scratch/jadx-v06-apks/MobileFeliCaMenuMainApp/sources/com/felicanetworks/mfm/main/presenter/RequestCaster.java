package com.felicanetworks.mfm.main.presenter;

import android.os.Handler;
import android.os.Looper;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.StructureType;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class RequestCaster {
    private static RequestCaster _this = new RequestCaster();
    private Structure _lastStructure;
    private Map<StructureType, RequestObserver> _observers = new HashMap();

    public interface RequestObserver {
        void onRequest(Structure structure);
    }

    public static RequestCaster getInstance() {
        return _this;
    }

    private RequestCaster() {
    }

    private Runnable createRequestExecuter(final Structure structure) {
        return new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.RequestCaster.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    RequestObserver requestObserver = (RequestObserver) RequestCaster.this._observers.get(structure.getType());
                    if (requestObserver != null) {
                        RequestCaster.this._lastStructure = structure;
                        requestObserver.onRequest(RequestCaster.this._lastStructure);
                    }
                } catch (Exception e) {
                    StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, RequestCaster.this._lastStructure, new MfmException(RequestCaster.class, 1, e));
                }
            }
        };
    }

    public void request(Structure structure) {
        try {
            new Handler(Looper.getMainLooper()).post(createRequestExecuter(structure));
        } catch (Exception e) {
            MfmException mfmException = new MfmException(StateController.class, 136, e);
            LogUtil.error(mfmException);
            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, structure, mfmException);
        }
    }

    public void reset() {
        this._lastStructure = null;
    }

    public static void registerObservers(StructureType[] structureTypeArr, RequestObserver requestObserver) throws MfmException {
        try {
            for (StructureType structureType : structureTypeArr) {
                _this._observers.put(structureType, requestObserver);
            }
        } catch (Exception e) {
            throw new MfmException(RequestCaster.class, 3, e);
        }
    }

    public static void unregisterObserver(StructureType[] structureTypeArr) {
        try {
            for (StructureType structureType : structureTypeArr) {
                _this._observers.remove(structureType);
            }
        } catch (Exception e) {
            LogUtil.warning(e);
        }
    }

    public static void unregisterAllObserver() {
        try {
            _this._observers = new HashMap();
        } catch (Exception e) {
            LogUtil.warning(e);
        }
    }

    public static Structure getLastRequestStructure() {
        RequestCaster requestCaster = _this;
        if (requestCaster != null) {
            return requestCaster._lastStructure;
        }
        return null;
    }
}
