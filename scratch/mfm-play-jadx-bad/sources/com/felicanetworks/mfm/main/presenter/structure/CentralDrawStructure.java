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

/* JADX INFO: loaded from: classes3.dex */
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
    private BackgroundUpdateRecommendListener _recommendListener;

    public interface BackgroundUpdateMyServiceListener {
        void onCompleted();

        void onError();
    }

    public interface BackgroundUpdateRecommendListener {
        void onCompleted();
    }

    public enum Page {
        MY_SERVICE,
        RECOMMEND
    }

    public CentralDrawStructure(CentralFuncAgent cfAgent, NoticeFuncAgent nfAgent, boolean isLock, boolean isFirstPageRecommend, boolean isEnoughExtCardServiceInfo, boolean isFailTransitionExtCard, MfiCardFuncAgent mfiAgent, boolean isWarningNotLogined, boolean isWarningLackServiceInfo, boolean isWarningLackCardInfo, String mfiAccountName) {
        super(StructureType.CENTRAL, isLock, isEnoughExtCardServiceInfo, mfiAgent.hasNeverLoggedIn(), mfiAccountName);
        this._cfAgent = cfAgent;
        this._nfAgent = nfAgent;
        this._mfiAgent = mfiAgent;
        if (isLock) {
            _firstPage = Page.MY_SERVICE;
        } else {
            _firstPage = isFirstPageRecommend ? Page.RECOMMEND : Page.MY_SERVICE;
        }
        this._isFailTransitionExtCard = isFailTransitionExtCard;
        this._isWarningNotLogined = isWarningNotLogined;
        this._isWarningLackServiceInfo = isWarningLackServiceInfo;
        this._isWarningLackCardInfo = isWarningLackCardInfo;
    }

    public void actUpdate(boolean showRecommend, boolean forceCheck) {
        StateController.postStateEvent(StateMachine.Event.EV_UPDATE, this, Boolean.valueOf(showRecommend), Boolean.valueOf(forceCheck));
    }

    public void actUpdate(boolean showRecommend, boolean forceCheck, boolean forceSimCacheUpdate, boolean forceMfiCacheUpdate, boolean forceAssetCacheUpdate) {
        StateController.postStateEvent(StateMachine.Event.EV_UPDATE, this, Boolean.valueOf(showRecommend), Boolean.valueOf(forceCheck), Boolean.valueOf(forceSimCacheUpdate), Boolean.valueOf(forceMfiCacheUpdate), Boolean.valueOf(forceAssetCacheUpdate));
    }

    public void actSelectRecommend(RecommendInfoAgent info) {
        StateController.postStateEvent(StateMachine.Event.EV_RECOMMEND_DETAIL, this, info);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionFelicaPocket
    public final void actFpServiceList() {
        StateController.postStateEvent(StateMachine.Event.EV_FP_SERVICE_LIST, this, new Object[0]);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionCardDetail
    public void actCardDetail(MyServiceGroupInfoAgent groupInfo, MyServiceInfoAgent serviceInfo) {
        StateController.postStateEvent(StateMachine.Event.EV_CARD_DETAIL, this, groupInfo, serviceInfo);
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionDeleteCardList
    public void actDeleteCardList(MyServiceInfoAgent serviceInfo) {
        StateController.postStateEvent(StateMachine.Event.EV_DELETE_CARD_LIST, this, serviceInfo);
    }

    void actMfiServiceUpdate() {
        StateController.postStateEvent(StateMachine.Event.EV_MFI_SERVICE_UPDATE, this, new Object[0]);
    }

    public void actCloseMfiLoginConductor() {
        ServicePreference.getInstance().saveShowLoginConductor(PresenterData.getInstance().getContext(), false);
    }

    public void actCloseService(MyServiceInfoAgent info) {
        MyServiceDatabaseAccess.getInstance().addHiddenServiceId(info.getId());
        this._cfAgent.marshalServiceInfo();
    }

    public void actChangedSort(List<MyServiceGroupInfoAgent> groups) {
        ArrayList arrayList = new ArrayList();
        Iterator<MyServiceGroupInfoAgent> it = groups.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getGroupId());
        }
        MyServiceDatabaseAccess.getInstance().setSortOrderGroupIds(arrayList);
        this._cfAgent.marshalServiceInfo();
    }

    public boolean isCached() {
        return this._cfAgent.isCached();
    }

    public boolean isUpdateCacheRunning() {
        return this._cfAgent.isUpdateCacheRunning();
    }

    public boolean isUpdateCacheError() {
        return this._cfAgent.isUpdateCacheError();
    }

    public void updateErrorInitialization() {
        this._cfAgent.updateErrorInitialization();
    }

    public void actBackgroundUpdateMyService(final BackgroundUpdateMyServiceListener listener) {
        this._cfAgent.updateCache(new CentralFuncAgent.BackgroundUpdateMyServiceListener() { // from class: com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure.1
            @Override // com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.BackgroundUpdateMyServiceListener
            public void onCompleted() {
                listener.onCompleted();
                if (CentralDrawStructure.this._recommendListener != null) {
                    CentralDrawStructure.this._recommendListener.onCompleted();
                }
            }

            @Override // com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.BackgroundUpdateMyServiceListener
            public void onError(boolean felicaLock) {
                if (felicaLock) {
                    CentralDrawStructure.this.setFelicaLock(felicaLock);
                    CentralDrawStructure.this._cfAgent.updateErrorInitialization();
                    CentralDrawStructure.this._cfAgent.clearServiceInfo();
                    listener.onCompleted();
                    if (CentralDrawStructure.this._recommendListener != null) {
                        CentralDrawStructure.this._recommendListener.onCompleted();
                        return;
                    }
                    return;
                }
                listener.onError();
                if (CentralDrawStructure.this._recommendListener != null) {
                    CentralDrawStructure.this._recommendListener.onCompleted();
                }
            }
        });
    }

    public void registerBackgroundUpdateRecommend(BackgroundUpdateRecommendListener listener) {
        this._recommendListener = listener;
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
