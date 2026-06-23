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

/* JADX INFO: loaded from: classes3.dex */
public class ExtIcCardDrawStructure extends PrimaryDrawStructure implements IActionMenu, IFuncExtIcCard, IActionFelicaPocket {
    private ExtIcCardFuncAgent _agent;

    public ExtIcCardDrawStructure(ExtIcCardFuncAgent agent, boolean isLock, boolean isEnoughExtCardServiceInfo, boolean hasNeverLoggedIn, String mfiAccountName) {
        super(StructureType.READER, isLock, isEnoughExtCardServiceInfo, hasNeverLoggedIn, mfiAccountName);
        this._agent = agent;
    }

    public void actTicketing(ExtIcCardInfoAgent info) {
        StateController.postStateEvent(StateMachine.Event.EV_TICKETING, this, info);
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
