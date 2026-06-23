package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.action.IActionCardDetail;
import com.felicanetworks.mfm.main.presenter.action.IActionDeleteCardList;
import com.felicanetworks.mfm.main.presenter.action.IActionFelicaPocket;
import com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.IFuncCentaral;
import com.felicanetworks.mfm.main.presenter.agent.IFuncMfiCard;
import com.felicanetworks.mfm.main.presenter.agent.IFuncNotice;
import com.felicanetworks.mfm.main.presenter.agent.MfiCardFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceGroupInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.RecommendInfoAgent;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CentralDrawStructure extends NfcStatusDrawStructre implements IFuncCentaral, IFuncNotice, IActionFelicaPocket, IFuncMfiCard, IActionCardDetail, IActionDeleteCardList {
    private static Page _firstPage;
    private static Page _requestPage;
    private CentralFuncAgent _cfAgent;
    private boolean _isFailTransitionExtCard;
    private boolean _isWarningLackCardInfo;
    private boolean _isWarningLackServiceInfo;
    private boolean _isWarningNotLogined;
    private MfiCardFuncAgent _mfiAgent;
    private NoticeFuncAgent _nfAgent;

    public enum Page {
        MY_SERVICE,
        RECOMMEND
    }

    public CentralDrawStructure(CentralFuncAgent centralFuncAgent, NoticeFuncAgent noticeFuncAgent, boolean z, boolean z2, boolean z3, boolean z4, MfiCardFuncAgent mfiCardFuncAgent, boolean z5, boolean z6, boolean z7, String str) {
        super(StructureType.CENTRAL, z, z3, mfiCardFuncAgent.hasNeverLoggedIn(), str);
        this._cfAgent = centralFuncAgent;
        this._nfAgent = noticeFuncAgent;
        this._mfiAgent = mfiCardFuncAgent;
        if (z) {
            _firstPage = Page.MY_SERVICE;
        } else {
            _firstPage = z2 ? Page.RECOMMEND : Page.MY_SERVICE;
        }
        this._isFailTransitionExtCard = z4;
        this._isWarningNotLogined = z5;
        this._isWarningLackServiceInfo = z6;
        this._isWarningLackCardInfo = z7;
    }

    public void actUpdate(boolean z, boolean z2) {
        StateController.postStateEvent(StateMachine.Event.EV_UPDATE, this, Boolean.valueOf(z), Boolean.valueOf(z2));
    }

    public void actSelectRecommend(RecommendInfoAgent recommendInfoAgent) {
        StateController.postStateEvent(StateMachine.Event.EV_RECOMMEND_DETAIL, this, recommendInfoAgent);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionFelicaPocket
    public final void actFpServiceList() {
        StateController.postStateEvent(StateMachine.Event.EV_FP_SERVICE_LIST, this, new Object[0]);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionCardDetail
    public void actCardDetail(MyServiceGroupInfoAgent myServiceGroupInfoAgent, MyServiceInfoAgent myServiceInfoAgent) {
        StateController.postStateEvent(StateMachine.Event.EV_CARD_DETAIL, this, myServiceGroupInfoAgent, myServiceInfoAgent);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionDeleteCardList
    public void actDeleteCardList(MyServiceInfoAgent myServiceInfoAgent) {
        StateController.postStateEvent(StateMachine.Event.EV_DELETE_CARD_LIST, this, myServiceInfoAgent);
    }

    void actMfiServiceUpdate() {
        StateController.postStateEvent(StateMachine.Event.EV_MFI_SERVICE_UPDATE, this, new Object[0]);
    }

    public void actCloseMfiLoginConductor() {
        ServicePreference.getInstance().saveShowLoginConductor(PresenterData.getInstance().getContext(), false);
    }

    public void actCloseService(MyServiceInfoAgent myServiceInfoAgent) {
        MyServiceDatabaseAccess.getInstance().addHiddenServiceId(myServiceInfoAgent.getId());
        this._cfAgent.marshalServiceInfo();
    }

    public void actChangedSort(List<MyServiceGroupInfoAgent> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<MyServiceGroupInfoAgent> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getGroupId());
        }
        MyServiceDatabaseAccess.getInstance().setSortOrderGroupIds(arrayList);
        this._cfAgent.marshalServiceInfo();
    }

    public Page getFirstPage() {
        return _firstPage;
    }

    public void setRequestPage(Page page) {
        _requestPage = page;
    }

    public Page getRequestPage() {
        return _requestPage;
    }

    public boolean isWarningNotEnoughExtCardServiceInfo() {
        return this._isFailTransitionExtCard;
    }

    public boolean isLogin() {
        return this._mfiAgent.isLoggedIn();
    }

    public boolean isShowLinkGoogleAccount() {
        return ServicePreference.getInstance().loadShowLoginConductor(PresenterData.getInstance().getContext()) && hasNeverLoggedIn();
    }

    public boolean isWarningNotLogined() {
        return this._isWarningNotLogined;
    }

    public boolean isWarningLackServiceInfo() {
        return this._isWarningLackServiceInfo;
    }

    public boolean isWarningLackCardInfo() {
        return this._isWarningLackCardInfo;
    }

    public boolean hasRegisteredServiceOrDeleteCardService() {
        return this._cfAgent.hasRegisteredService() || this._cfAgent.hasDeleteNotExistTService();
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncCentaral
    public CentralFuncAgent getCentralFunc() {
        return this._cfAgent;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncNotice
    public NoticeFuncAgent getNoticeFunc() {
        return this._nfAgent;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.IFuncMfiCard
    public MfiCardFuncAgent getMfiCardFunc() {
        return this._mfiAgent;
    }
}
