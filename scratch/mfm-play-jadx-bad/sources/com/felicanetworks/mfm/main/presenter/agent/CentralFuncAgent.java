package com.felicanetworks.mfm.main.presenter.agent;

import android.content.Context;
import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.MfiCardFunc;
import com.felicanetworks.mfm.main.model.NoticeFunc;
import com.felicanetworks.mfm.main.model.info.BannerInfo;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.RecommendInfo;
import com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.service.ServiceGroupType;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceGroupInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess;
import com.felicanetworks.mfm.main.presenter.internal.rwp2p.NfcStatusChangeReceiver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public class CentralFuncAgent {
    private final CentralFunc _centralFunc;
    private final StateController.ModelDataUpdateManager _mdum;
    private final MfiCardFunc _mfiCardFunc;
    private final NoticeFunc _noticeFunc;
    private MarshalledMyServiceGroups marshalledMyServiceGroups = new MarshalledMyServiceGroups();
    private List<RecommendCategoryInfoAgent> marshalledRecommendCategories = new ArrayList();

    public interface BackgroundUpdateMyServiceListener {
        void onCompleted();

        void onError(boolean felicaLock);
    }

    public interface CardFaceImageListener {

        public enum Type {
            SERVICE_ICON,
            FACE_CARD
        }

        void onGetImage(Bitmap image, Type type);
    }

    public interface ChangeDataListener {
        void onNfcSettingChange(boolean isNfcEnable);
    }

    public interface OrderBannerListener {
        void onComplete(List<BannerInfoAgent> list);
    }

    public interface ProgressListener {
        void onProgress(int numerator, int denominator);
    }

    public CentralFuncAgent(CentralFunc centralFunc, MfiCardFunc mfiCardFunc, NoticeFunc noticeFunc, StateController.ModelDataUpdateManager mdum) {
        this._centralFunc = centralFunc;
        this._mfiCardFunc = mfiCardFunc;
        this._noticeFunc = noticeFunc;
        this._mdum = mdum;
    }

    public void marshalServiceInfo() {
        this._centralFunc.marshalModelData(this._mfiCardFunc);
        MarshalledMyServiceGroups marshalledMyServiceGroupsMarshalMyServiceGroup = marshalMyServiceGroup();
        this.marshalledMyServiceGroups = marshalledMyServiceGroupsMarshalMyServiceGroup;
        this.marshalledRecommendCategories = marshalRecommendCategory(marshalledMyServiceGroupsMarshalMyServiceGroup.registerStates);
    }

    public void clearServiceInfo() {
        this.marshalledMyServiceGroups = new MarshalledMyServiceGroups();
        this.marshalledRecommendCategories = new ArrayList();
    }

    public List<MyServiceGroupInfoAgent> getMyServiceGroupInfoList() {
        return this.marshalledMyServiceGroups.myServiceGroups;
    }

    public Map<String, MyServiceInfo.RegisterState> getRegisterStatus() {
        return this.marshalledMyServiceGroups.registerStates;
    }

    public List<RecommendCategoryInfoAgent> getRecommendInfoList() {
        return this.marshalledRecommendCategories;
    }

    public void setCompileProgressListener(final ProgressListener listener) {
        try {
            this._centralFunc.setCompileProgressListener(new CentralFunc.ProgressListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.1
                @Override // com.felicanetworks.mfm.main.model.CentralFunc.ProgressListener
                public void onProgress(final int numerator, final int denominator) {
                    AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                listener.onProgress(numerator, denominator);
                            } catch (Exception e) {
                                MfmException mfmException = new MfmException(getClass(), 2, e);
                                LogUtil.error(mfmException);
                                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            MfmException mfmException = new MfmException(getClass(), 1, e);
            LogUtil.error(mfmException);
            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
        }
    }

    public CompiledState getCompiledState() {
        return new CompiledState(this._centralFunc.getCompiledState());
    }

    public Boolean isCompiled() {
        return this._centralFunc.isCompiled();
    }

    public boolean hasRegisteredService() {
        Iterator<MyServiceInfo> it = this._centralFunc.getMyServiceInfoList().iterator();
        while (it.hasNext()) {
            if (it.next().getRegisterState() != MyServiceInfo.RegisterState.NO_REGISTER) {
                return true;
            }
        }
        return false;
    }

    public boolean hasDeleteNotExistTService() {
        return !this._centralFunc.getDeleteServiceInfoList().isEmpty();
    }

    public FelicaPocketFuncAgent getFelicaPocketFuncAgent() {
        return new FelicaPocketFuncAgent(this._centralFunc);
    }

    public void orderBanner(final OrderBannerListener listener) {
        try {
            this._centralFunc.orderBanner(new CentralFunc.OrderBannerListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.2
                @Override // com.felicanetworks.mfm.main.model.CentralFunc.OrderBannerListener
                public void onSuccess(List<BannerInfo> list) {
                    try {
                        final ArrayList arrayList = new ArrayList();
                        Iterator<BannerInfo> it = list.iterator();
                        while (it.hasNext()) {
                            arrayList.add(new BannerInfoAgent(it.next()));
                        }
                        AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    listener.onComplete(arrayList);
                                } catch (Exception e) {
                                    MfmException mfmException = new MfmException(getClass(), 375, e);
                                    LogUtil.error(mfmException);
                                    StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                                }
                            }
                        });
                    } catch (Exception e) {
                        MfmException mfmException = new MfmException(getClass(), 391, e);
                        LogUtil.error(mfmException);
                        StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                    }
                }
            });
        } catch (Exception e) {
            MfmException mfmException = new MfmException(getClass(), 404, e);
            LogUtil.error(mfmException);
            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
        }
    }

    public void getCardFaceImage(final MyServiceInfoAgent agentInfo, final CardFaceImageListener listener) {
        MyCardInfoAgent mainMyCardInfoAgent = agentInfo.getMainMyCardInfoAgent();
        if (mainMyCardInfoAgent == null || !mainMyCardInfoAgent.isActiveForeground()) {
            AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Bitmap cardFaceImage = agentInfo.getCardFaceImage();
                        if (cardFaceImage != null) {
                            listener.onGetImage(cardFaceImage, CardFaceImageListener.Type.FACE_CARD);
                        } else {
                            listener.onGetImage(agentInfo.getIcon(), CardFaceImageListener.Type.SERVICE_ICON);
                        }
                    } catch (Exception e) {
                        LogUtil.error(new MfmException(getClass(), 378, e));
                    }
                }
            });
        } else {
            this._centralFunc.getCardFaceImage(mainMyCardInfoAgent.getCid(), agentInfo.getCardImageUrls(mainMyCardInfoAgent.getCid()), new CentralFunc.CardFaceImageListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.4
                @Override // com.felicanetworks.mfm.main.model.CentralFunc.CardFaceImageListener
                public void onGetImage(final Bitmap image) {
                    AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                if (image != null) {
                                    listener.onGetImage(image, CardFaceImageListener.Type.FACE_CARD);
                                    return;
                                }
                                Bitmap cardFaceImage = agentInfo.getCardFaceImage();
                                if (cardFaceImage != null) {
                                    listener.onGetImage(cardFaceImage, CardFaceImageListener.Type.FACE_CARD);
                                } else {
                                    listener.onGetImage(agentInfo.getIcon(), CardFaceImageListener.Type.SERVICE_ICON);
                                }
                            } catch (Exception e) {
                                LogUtil.error(new MfmException(getClass(), 377, e));
                            }
                        }
                    });
                }
            });
        }
    }

    public void registerChangeDataListener(Context context, final ChangeDataListener listener) {
        NfcStatusChangeReceiver.setChangeDataListener(context, listener);
    }

    public void unregisterChangeDataListener(Context context) {
        NfcStatusChangeReceiver.setChangeDataListener(context, null);
    }

    private static class MarshalledMyServiceGroups {
        final List<MyServiceGroupInfoAgent> myServiceGroups;
        final Map<String, MyServiceInfo.RegisterState> registerStates;

        private MarshalledMyServiceGroups() {
            this.myServiceGroups = new ArrayList();
            this.registerStates = new LinkedHashMap();
        }
    }

    private MarshalledMyServiceGroups marshalMyServiceGroup() {
        MarshalledMyServiceGroups marshalledMyServiceGroups = new MarshalledMyServiceGroups();
        List<String> hiddenServiceIds = MyServiceDatabaseAccess.getInstance().getHiddenServiceIds();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (MyServiceInfo myServiceInfo : this._centralFunc.getMyServiceInfoList()) {
            String strGroupId = ServiceGroupType.resolve(myServiceInfo.getId()).groupId(myServiceInfo.getId());
            List arrayList = (List) linkedHashMap.get(strGroupId);
            if (arrayList == null) {
                arrayList = new ArrayList();
            }
            arrayList.add(myServiceInfo);
            if (myServiceInfo.getId().equals(FeliCaParams.SERVICE_ID_SUICA)) {
                linkedHashMap.remove(strGroupId);
            }
            linkedHashMap.put(strGroupId, arrayList);
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (MyServiceInfo myServiceInfo2 : this._centralFunc.getDeleteServiceInfoList()) {
            String strGroupId2 = ServiceGroupType.resolve(myServiceInfo2.getId()).groupId(myServiceInfo2.getId());
            List arrayList2 = (List) linkedHashMap2.get(strGroupId2);
            if (arrayList2 == null) {
                arrayList2 = new ArrayList();
            }
            arrayList2.add(myServiceInfo2);
            linkedHashMap2.put(strGroupId2, arrayList2);
        }
        for (Map.Entry entry : linkedHashMap2.entrySet()) {
            if (!linkedHashMap.containsKey(entry.getKey())) {
                linkedHashMap.put((String) entry.getKey(), (List) linkedHashMap2.get(entry.getKey()));
            }
        }
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        for (MyCardInfo myCardInfo : this._mfiCardFunc.getMyCardInfoList()) {
            List arrayList3 = (List) linkedHashMap3.get(myCardInfo.getServiceId());
            if (arrayList3 == null) {
                arrayList3 = new ArrayList();
            }
            arrayList3.add(myCardInfo);
            linkedHashMap3.put(myCardInfo.getServiceId(), arrayList3);
        }
        LinkedHashMap linkedHashMap4 = new LinkedHashMap();
        for (MyCardInfo myCardInfo2 : this._mfiCardFunc.getDeleteCardInfoList()) {
            List arrayList4 = (List) linkedHashMap4.get(myCardInfo2.getServiceId());
            if (arrayList4 == null) {
                arrayList4 = new ArrayList();
            }
            arrayList4.add(myCardInfo2);
            linkedHashMap4.put(myCardInfo2.getServiceId(), arrayList4);
        }
        for (List<MyServiceInfo> list : linkedHashMap.values()) {
            ArrayList arrayList5 = new ArrayList();
            for (MyServiceInfo myServiceInfo3 : list) {
                List list2 = (List) linkedHashMap3.get(myServiceInfo3.getId());
                MyServiceInfoAgent myServiceInfoAgentCreate = MyServiceInfoAgent.Factory.create(myServiceInfo3, list2, this._mfiCardFunc.isLoggedIn(), this._mfiCardFunc.isChaced());
                MyServiceInfo.RegisterState registerStateCreateRegisterState = createRegisterState(myServiceInfoAgentCreate);
                marshalledMyServiceGroups.registerStates.put(myServiceInfoAgentCreate.getId(), registerStateCreateRegisterState);
                arrayList5.add(myServiceInfoAgentCreate);
                if (hiddenServiceIds.contains(myServiceInfoAgentCreate.getId())) {
                    if (registerStateCreateRegisterState == MyServiceInfo.RegisterState.NO_REGISTER) {
                        myServiceInfoAgentCreate.setHidden(true);
                    } else {
                        MyServiceDatabaseAccess.getInstance().removeHiddenServiceId(myServiceInfoAgentCreate.getId());
                    }
                }
                if (list2 == null && linkedHashMap4.containsKey(myServiceInfo3.getId())) {
                    myServiceInfoAgentCreate.setHidden(true);
                }
            }
            MyServiceGroupInfoAgent myServiceGroupInfoAgent = new MyServiceGroupInfoAgent(arrayList5);
            if (linkedHashMap2.containsKey(myServiceGroupInfoAgent.getGroupId())) {
                for (MyServiceInfo myServiceInfo4 : (List) linkedHashMap2.get(myServiceGroupInfoAgent.getGroupId())) {
                    List list3 = (List) linkedHashMap4.get(myServiceInfo4.getId());
                    if (list3 != null) {
                        myServiceGroupInfoAgent.addGroupState(new MyServiceGroupInfoAgent.DeleteCardState(MyServiceInfoAgent.Factory.create(myServiceInfo4, list3, this._mfiCardFunc.isLoggedIn(), this._mfiCardFunc.isChaced())));
                    }
                }
            }
            if (!myServiceGroupInfoAgent.isHidden()) {
                marshalledMyServiceGroups.myServiceGroups.add(myServiceGroupInfoAgent);
            }
        }
        Iterator it = linkedHashMap2.values().iterator();
        while (it.hasNext()) {
            for (MyServiceInfo myServiceInfo5 : (List) it.next()) {
                MyServiceInfoAgent myServiceInfoAgentCreate2 = MyServiceInfoAgent.Factory.create(myServiceInfo5, (List) linkedHashMap4.get(myServiceInfo5.getId()), this._mfiCardFunc.isLoggedIn(), this._mfiCardFunc.isChaced());
                MyServiceInfo.RegisterState registerStateCreateRegisterState2 = createRegisterState(myServiceInfoAgentCreate2);
                if (!marshalledMyServiceGroups.registerStates.containsKey(myServiceInfoAgentCreate2.getId())) {
                    marshalledMyServiceGroups.registerStates.put(myServiceInfoAgentCreate2.getId(), registerStateCreateRegisterState2);
                }
            }
        }
        final List<String> sortOrderGroupIds = MyServiceDatabaseAccess.getInstance().getSortOrderGroupIds();
        Collections.sort(marshalledMyServiceGroups.myServiceGroups, new Comparator<MyServiceGroupInfoAgent>() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.5
            /* JADX DEBUG: Method merged with bridge method: compare(Ljava/lang/Object;Ljava/lang/Object;)I */
            @Override // java.util.Comparator
            public int compare(MyServiceGroupInfoAgent o1, MyServiceGroupInfoAgent o2) {
                String groupId = o1.getGroupId();
                String groupId2 = o2.getGroupId();
                if (sortOrderGroupIds.contains(groupId) && sortOrderGroupIds.contains(groupId2)) {
                    return sortOrderGroupIds.indexOf(groupId) - sortOrderGroupIds.indexOf(groupId2);
                }
                if (sortOrderGroupIds.contains(groupId)) {
                    return -1;
                }
                return sortOrderGroupIds.contains(groupId2) ? 1 : 0;
            }
        });
        return marshalledMyServiceGroups;
    }

    private List<RecommendCategoryInfoAgent> marshalRecommendCategory(Map<String, MyServiceInfo.RegisterState> registerStateMap) {
        List<RecommendInfo> recommendInfoList = this._centralFunc.getRecommendInfoList();
        ArrayList<RecommendInfoAgent> arrayList = new ArrayList();
        Iterator<RecommendInfo> it = recommendInfoList.iterator();
        while (it.hasNext()) {
            arrayList.add(new RecommendInfoAgent(it.next()));
        }
        if (CompiledState.FelicaState.NO_PROBLEM != getCompiledState().getFelicaState()) {
            return new ArrayList();
        }
        ArrayList arrayList2 = new ArrayList();
        for (RecommendInfoAgent recommendInfoAgent : arrayList) {
            if (MyServiceInfo.RegisterState.REGISTERED == registerStateMap.get(recommendInfoAgent.getId()) || hasDeleteNotExistTCard(recommendInfoAgent.getId())) {
                arrayList2.add(recommendInfoAgent.getId());
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (RecommendInfoAgent recommendInfoAgent2 : arrayList) {
            if (!arrayList2.contains(recommendInfoAgent2.getId())) {
                if (linkedHashMap.containsKey(recommendInfoAgent2.getCategoryId())) {
                    ((RecommendCategoryInfoAgent) Objects.requireNonNull((RecommendCategoryInfoAgent) linkedHashMap.get(recommendInfoAgent2.getCategoryId()))).addRecommend(recommendInfoAgent2);
                } else {
                    ArrayList arrayList3 = new ArrayList();
                    arrayList3.add(recommendInfoAgent2);
                    linkedHashMap.put(recommendInfoAgent2.getCategoryId(), new RecommendCategoryInfoAgent(arrayList3));
                }
            }
        }
        return new ArrayList(linkedHashMap.values());
    }

    private MyServiceInfo.RegisterState createRegisterState(MyServiceInfoAgent service) {
        if (service.isType2() && !this._mfiCardFunc.isLoggedIn() && !this._mfiCardFunc.isChaced()) {
            return MyServiceInfo.RegisterState.NO_REGISTER;
        }
        if (MyServiceInfo.RegisterState.REGISTERED == service.getClient().getRegisterState()) {
            return MyServiceInfo.RegisterState.REGISTERED;
        }
        if (!service.getMyCardInfoAgentList().isEmpty()) {
            return MyServiceInfo.RegisterState.REGISTERED;
        }
        if (MyServiceInfo.RegisterState.NONE == service.getClient().getRegisterState() && MyServiceInfo.RegisterState.REGISTERED == service.getClient().getAppInstalledState()) {
            return MyServiceInfo.RegisterState.REGISTERED;
        }
        return MyServiceInfo.RegisterState.NO_REGISTER;
    }

    public boolean hasDeleteNotExistTCard(String serviceId) {
        Iterator<MyServiceInfo> it = this._centralFunc.getDeleteServiceInfoList().iterator();
        while (it.hasNext()) {
            if (it.next().sid().equals(serviceId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCached() {
        if (this._mfiCardFunc.isChaced()) {
            return true;
        }
        return this._centralFunc.isCached() && this._mfiCardFunc.isCompiled().booleanValue();
    }

    public boolean isUpdateCacheRunning() {
        return this._centralFunc.isUpdateCacheRunning();
    }

    public boolean isUpdateCacheError() {
        return this._centralFunc.isUpdateCacheError();
    }

    public void interrupt() {
        this._centralFunc.interrupt();
    }

    public void updateErrorInitialization() {
        this._centralFunc.updateErrorInitialization();
    }

    public void updateCache(final BackgroundUpdateMyServiceListener listener) {
        try {
            this._centralFunc.registBackgroundUpdateMyServiceListener(new CentralFunc.BackgroundUpdateMyServiceListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.6
                @Override // com.felicanetworks.mfm.main.model.CentralFunc.BackgroundUpdateMyServiceListener
                public void onCompleted() {
                    CentralFuncAgent.this.marshalServiceInfo();
                    try {
                        LinkedHashMap linkedHashMap = new LinkedHashMap();
                        for (Map.Entry<String, MyServiceInfo.RegisterState> entry : CentralFuncAgent.this.getRegisterStatus().entrySet()) {
                            MyServiceInfo.RegisterState registerState = CentralFuncAgent.this.getRegisterStatus().get(entry.getKey());
                            if (MyServiceInfo.RegisterState.NO_REGISTER == registerState && CentralFuncAgent.this.hasDeleteNotExistTCard(entry.getKey())) {
                                registerState = MyServiceInfo.RegisterState.REGISTERED;
                            }
                            linkedHashMap.put(entry.getKey(), registerState);
                        }
                        CentralFuncAgent.this._noticeFunc.setServiceRegisterState(linkedHashMap, CentralFuncAgent.this._centralFunc.getCompiledState(), CentralFuncAgent.this._mfiCardFunc.getCompiledState());
                        CentralFuncAgent.this._mdum.init();
                        AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.6.2
                            @Override // java.lang.Runnable
                            public void run() {
                                listener.onCompleted();
                            }
                        });
                    } catch (MfmException unused) {
                        AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.6.1
                            @Override // java.lang.Runnable
                            public void run() {
                                listener.onError(false);
                            }
                        });
                    }
                }

                @Override // com.felicanetworks.mfm.main.model.CentralFunc.BackgroundUpdateMyServiceListener
                public void onError(final ModelErrorInfo error) {
                    AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.6.3
                        @Override // java.lang.Runnable
                        public void run() {
                            if (error.getType() == ModelErrorInfo.Type.LOCKED_FELICA) {
                                listener.onError(true);
                            } else {
                                listener.onError(false);
                            }
                        }
                    });
                }
            });
            this._centralFunc.updateCache(this._mfiCardFunc);
        } catch (Exception e) {
            MfmException mfmException = new MfmException(getClass(), 404, e);
            LogUtil.error(mfmException);
            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
        }
    }

    public void registBackgroundUpdateMyServiceListener(CentralFunc.BackgroundUpdateMyServiceListener listener) {
        this._centralFunc.registBackgroundUpdateMyServiceListener(listener);
    }

    public void unRegistBackgroundUpdateMyServiceListener(CentralFunc.BackgroundUpdateMyServiceListener listener) {
        this._centralFunc.unRegistBackgroundUpdateMyServiceListener(listener);
    }

    public static class CompiledState {
        private CentralFunc.CompiledState _client;

        public enum FelicaState {
            NO_ACCESS,
            NO_PROBLEM,
            LOCK_ERROR,
            OPEN_ERROR,
            TIMEOUT_ERROR,
            USED_BY_OTHER_APP,
            RUNNING_BY_MFIC
        }

        public enum NetworkState {
            NO_CONNECT,
            NO_PROBLEM,
            CONNECT_ERROR
        }

        CompiledState(CentralFunc.CompiledState client) {
            this._client = client;
        }

        public FelicaState getFelicaState() {
            return FelicaState.valueOf(this._client.getFelicaState().name());
        }

        public NetworkState getNetworkState() {
            return NetworkState.valueOf(this._client.getNetworkState().name());
        }

        boolean isWarningLackDispInfo() {
            return this._client.isWarningLackDispInfo();
        }

        boolean isWarningContainOldDispInfo() {
            return this._client.isWarningContainOldDispInfo();
        }

        public boolean isConditionalComplete() {
            return isWarningLackDispInfo() || isWarningContainOldDispInfo() || getNetworkState() == NetworkState.CONNECT_ERROR;
        }
    }
}
