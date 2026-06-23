package com.felicanetworks.mfm.main.model.internal.main;

import com.felicanetworks.mfm.main.model.TermsOfServiceFunc;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.TosVersionProtocol;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;

/* JADX INFO: loaded from: classes.dex */
public class TermsOfServiceFuncEntity implements TermsOfServiceFunc {
    private final ModelContext _context;
    private DatabaseExpert _db;
    private boolean _displayed_tos = false;
    private TosVersionProtocol.Result _tos_result;

    TermsOfServiceFuncEntity(ModelContext modelContext) {
        this._context = modelContext;
        this._db = modelContext.getOpenedDatabaseExpert();
    }

    @Override // com.felicanetworks.mfm.main.model.TermsOfServiceFunc
    public void setTosVersionProtocolResult(TosVersionProtocol.Result result) {
        this._tos_result = result;
    }

    @Override // com.felicanetworks.mfm.main.model.TermsOfServiceFunc
    public void savePolicyConsent() {
        if (this._tos_result == null) {
            return;
        }
        try {
            this._db.setTosVersionSettings(new DatabaseExpert.TosVersionSettings(this._tos_result.interval, FuncUtil.convertFromCalendarToString(DateFormatter.DATE_MINUTE, FuncUtil.getCurrentCalendar()), 0, this._tos_result.maxCount, this._tos_result.version));
            AnalysisManager.setPolicyConsent(true);
        } catch (DatabaseException unused) {
        }
    }

    @Override // com.felicanetworks.mfm.main.model.TermsOfServiceFunc
    public int getTosVersion() {
        TosVersionProtocol.Result result = this._tos_result;
        if (result != null) {
            return result.version;
        }
        return 0;
    }

    @Override // com.felicanetworks.mfm.main.model.TermsOfServiceFunc
    public boolean isUpgrade() {
        TosVersionProtocol.Result result = this._tos_result;
        return result != null && result.status == Integer.valueOf("1").intValue();
    }

    @Override // com.felicanetworks.mfm.main.model.TermsOfServiceFunc
    public void setDisplayedTos(boolean z) {
        this._displayed_tos = z;
    }

    @Override // com.felicanetworks.mfm.main.model.TermsOfServiceFunc
    public boolean getDisplayedTos() {
        return this._displayed_tos;
    }

    @Override // com.felicanetworks.mfm.main.model.TermsOfServiceFunc
    public void cancel() {
        this._context.cancellation();
    }
}
