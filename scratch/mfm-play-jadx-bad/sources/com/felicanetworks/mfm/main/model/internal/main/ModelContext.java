package com.felicanetworks.mfm.main.model.internal.main;

import android.content.Context;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmlib.MfmAppContext;
import com.felicanetworks.mfm.main.model.internal.main.FuncUtil;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;

/* JADX INFO: loaded from: classes3.dex */
public class ModelContext {
    private static CentralFuncEntity _centralFunc;
    private static ModelContext _mainMoelContext;
    private static MfcExpert _mfcExpert;
    private Context _androidContext;
    private MfmAppContext _legacyContext;
    private NetworkExpert _netExpert = null;
    private DatabaseExpert _dbExpert = null;
    private FuncUtil.AsyncRunner _mfiCardFuncRunner = null;
    private FuncUtil.AsyncRunner _cardDetailFuncRunner = null;
    private FuncUtil.AsyncRunner _orderUpdateCacheRunner = null;

    public ModelContext(Context context) {
        MfmAppContext mfmAppContext = new MfmAppContext();
        this._legacyContext = mfmAppContext;
        this._androidContext = context;
        mfmAppContext.androidContext = context.getApplicationContext();
    }

    protected ModelContext() {
    }

    public final Context getAndroidContext() {
        return this._androidContext;
    }

    public final MfmAppContext getLegacyContext() {
        return this._legacyContext;
    }

    public static final void setInitializedMfcExpert(MfcExpert mfcExpert) {
        _mfcExpert = mfcExpert;
    }

    public static void setCentralFunc(CentralFuncEntity centralFunc) {
        _centralFunc = centralFunc;
    }

    public CentralFuncEntity getCentralFunc() {
        return _centralFunc;
    }

    public static final MfcExpert getInitializedMfcExpert() {
        return _mfcExpert;
    }

    final void setNetworkExpert(NetworkExpert netExpert) {
        this._netExpert = netExpert;
    }

    public final NetworkExpert getNetworkExpert() {
        return this._netExpert;
    }

    public final void setOpenedDatabaseExpert(DatabaseExpert de) {
        this._dbExpert = de;
    }

    public final DatabaseExpert getOpenedDatabaseExpert() {
        return this._dbExpert;
    }

    public final void cancellation() {
        if (isUpdateCacheRunning()) {
            return;
        }
        MfcExpert mfcExpert = _mfcExpert;
        if (mfcExpert != null) {
            mfcExpert.finishFeliCaAccess();
        }
        NetworkExpert networkExpert = this._netExpert;
        if (networkExpert != null) {
            networkExpert.disconnect();
        }
    }

    public final void forceFinishFeliCaAccess() {
        MfcExpert mfcExpert = _mfcExpert;
        if (mfcExpert != null) {
            mfcExpert.finishFeliCaAccess();
        }
    }

    public void setMfiCardFuncRunner(FuncUtil.AsyncRunner runner) {
        this._mfiCardFuncRunner = runner;
    }

    public void shutdownMfiCardFuncRunner() {
        FuncUtil.AsyncRunner asyncRunner = this._mfiCardFuncRunner;
        if (asyncRunner == null) {
            return;
        }
        asyncRunner.shutdown();
    }

    public void setCardDetailFuncRunner(FuncUtil.AsyncRunner runner) {
        this._cardDetailFuncRunner = runner;
    }

    public void shutdownCardDetailFuncRunner() {
        FuncUtil.AsyncRunner asyncRunner = this._cardDetailFuncRunner;
        if (asyncRunner == null) {
            return;
        }
        asyncRunner.shutdown();
    }

    public void setOrderUpdateCacheRunner(FuncUtil.AsyncRunner runner) {
        this._orderUpdateCacheRunner = runner;
    }

    public boolean isUpdateCacheRunning() {
        FuncUtil.AsyncRunner asyncRunner = this._orderUpdateCacheRunner;
        if (asyncRunner == null) {
            return false;
        }
        return asyncRunner.isRunning();
    }

    public void shutdownOrderUpdateCacheRunner() {
        FuncUtil.AsyncRunner asyncRunner = this._orderUpdateCacheRunner;
        if (asyncRunner == null) {
            return;
        }
        asyncRunner.shutdown();
    }

    public void setMainMoelContext(ModelContext modelContext) {
        _mainMoelContext = modelContext;
    }

    public static final ModelContext getMainModelContext() {
        return _mainMoelContext;
    }
}
