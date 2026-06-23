package com.felicanetworks.mfm.main.presenter.structure;

import android.nfc.NfcAdapter;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.action.IActionFelicaPocket;
import com.felicanetworks.mfm.main.presenter.action.IActionMenu;
import com.felicanetworks.mfm.main.presenter.agent.ExtIcCardFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.ExtIcCardInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.IFuncExtIcCard;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes.dex */
public class ExtIcCardDrawStructure extends PrimaryDrawStructure implements IActionMenu, IFuncExtIcCard, IActionFelicaPocket {
    private ExtIcCardFuncAgent _agent;

    public ExtIcCardDrawStructure(ExtIcCardFuncAgent extIcCardFuncAgent, boolean z, boolean z2, boolean z3, String str) {
        super(StructureType.READER, z, z2, z3, str);
        this._agent = extIcCardFuncAgent;
    }

    public void actTicketing(ExtIcCardInfoAgent extIcCardInfoAgent) {
        StateController.postStateEvent(StateMachine.Event.EV_TICKETING, this, extIcCardInfoAgent);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionFelicaPocket
    public final void actFpServiceList() {
        StateController.postStateEvent(StateMachine.Event.EV_FP_SERVICE_LIST, this, new Object[0]);
    }

    public final void actReaderWriterSetting() {
        StateController.postStateEvent(StateMachine.Event.EV_CARD_READER_SETTING, this, new Object[0]);
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncExtIcCard
    public boolean isEnableRWP2P() {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(PresenterData.getInstance().getContext());
        return defaultAdapter != null && defaultAdapter.isEnabled();
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncExtIcCard
    public ExtIcCardFuncAgent getExIcCardFunc() {
        return this._agent;
    }
}
