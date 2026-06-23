package com.felicanetworks.mfm.main.model.internal.main;

import android.content.Context;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmlib.MfmAppContext;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;

/* JADX INFO: loaded from: classes.dex */
public class ModelContext {
    private static MfcExpert _mfcExpert;
    private Context _androidContext;
    private MfmAppContext _legacyContext;
    private NetworkExpert _netExpert = null;
    private DatabaseExpert _dbExpert = null;

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

    public static final MfcExpert getInitializedMfcExpert() {
        return _mfcExpert;
    }

    final void setNetworkExpert(NetworkExpert networkExpert) {
        this._netExpert = networkExpert;
    }

    public final NetworkExpert getNetworkExpert() {
        return this._netExpert;
    }

    public final void setOpenedDatabaseExpert(DatabaseExpert databaseExpert) {
        this._dbExpert = databaseExpert;
    }

    public final DatabaseExpert getOpenedDatabaseExpert() {
        return this._dbExpert;
    }

    public final void cancellation() {
        MfcExpert mfcExpert = _mfcExpert;
        if (mfcExpert != null) {
            mfcExpert.finishFeliCaAccess();
        }
        NetworkExpert networkExpert = this._netExpert;
        if (networkExpert != null) {
            networkExpert.disconnect();
        }
    }
}
