package com.felicanetworks.mfm.main.presenter.agent;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.MfiCardFunc;
import com.felicanetworks.mfm.main.model.info.BannerInfo;
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

/* JADX INFO: loaded from: classes.dex */
public class CentralFuncAgent {
    private final CentralFunc _centralFunc;
    private final MfiCardFunc _mfiCardFunc;
    private MarshalledMyServiceGroups marshalledMyServiceGroups = new MarshalledMyServiceGroups();
    private List<RecommendCategoryInfoAgent> marshalledRecommendCategories = new ArrayList();

    public interface CardFaceImageListener {

        public enum Type {
            SERVICE_ICON,
            FACE_CARD
        }

        void onGetImage(Bitmap bitmap, Type type);
    }

    public interface ChangeDataListener {
        void onNfcSettingChange(boolean z);
    }

    public interface OrderBannerImageListener {
        void onCompleteBannerImage(Bitmap bitmap);
    }

    public interface OrderBannerListener {
        void onComplete(List<BannerInfoAgent> list);
    }

    public interface ProgressListener {
        void onProgress(int i, int i2);
    }

    public CentralFuncAgent(CentralFunc centralFunc, MfiCardFunc mfiCardFunc) {
        this._centralFunc = centralFunc;
        this._mfiCardFunc = mfiCardFunc;
    }

    public void marshalServiceInfo() {
        this._centralFunc.marshalModelData(this._mfiCardFunc);
        MarshalledMyServiceGroups marshalledMyServiceGroupsMarshalMyServiceGroup = marshalMyServiceGroup();
        this.marshalledMyServiceGroups = marshalledMyServiceGroupsMarshalMyServiceGroup;
        this.marshalledRecommendCategories = marshalRecommendCategory(marshalledMyServiceGroupsMarshalMyServiceGroup.registerStates);
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

    public void setCompileProgressListener(final ProgressListener progressListener) {
        try {
            this._centralFunc.setCompileProgressListener(new CentralFunc.ProgressListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.1
                @Override // com.felicanetworks.mfm.main.model.CentralFunc.ProgressListener
                public void onProgress(final int i, final int i2) {
                    AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                progressListener.onProgress(i, i2);
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

    public void orderBanner(final OrderBannerListener orderBannerListener) {
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
                                    orderBannerListener.onComplete(arrayList);
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

    public void getCardFaceImage(final MyServiceInfoAgent myServiceInfoAgent, final CardFaceImageListener cardFaceImageListener) {
        MyCardInfoAgent mainMyCardInfoAgent = myServiceInfoAgent.getMainMyCardInfoAgent();
        if (mainMyCardInfoAgent == null || !mainMyCardInfoAgent.isActiveForeground()) {
            AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Bitmap cardFaceImage = myServiceInfoAgent.getCardFaceImage();
                        if (cardFaceImage != null) {
                            cardFaceImageListener.onGetImage(cardFaceImage, CardFaceImageListener.Type.FACE_CARD);
                        } else {
                            cardFaceImageListener.onGetImage(myServiceInfoAgent.getIcon(), CardFaceImageListener.Type.SERVICE_ICON);
                        }
                    } catch (Exception e) {
                        LogUtil.error(new MfmException(getClass(), 378, e));
                    }
                }
            });
        } else {
            this._centralFunc.getCardFaceImage(mainMyCardInfoAgent.getCid(), mainMyCardInfoAgent.getCardFaceImageUrl(), new CentralFunc.CardFaceImageListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.4
                @Override // com.felicanetworks.mfm.main.model.CentralFunc.CardFaceImageListener
                public void onGetImage(final Bitmap bitmap) {
                    AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                cardFaceImageListener.onGetImage(bitmap != null ? bitmap : myServiceInfoAgent.getCardFaceImage(), CardFaceImageListener.Type.FACE_CARD);
                            } catch (Exception e) {
                                LogUtil.error(new MfmException(getClass(), 377, e));
                            }
                        }
                    });
                }
            });
        }
    }

    public void registerChangeDataListener(Context context, ChangeDataListener changeDataListener) {
        NfcStatusChangeReceiver.setChangeDataListener(context, changeDataListener);
    }

    public void unregisterChangeDataListener(Context context) {
        NfcStatusChangeReceiver.setChangeDataListener(context, null);
    }

    public void orderBannerImage(final OrderBannerImageListener orderBannerImageListener) {
        try {
            this._centralFunc.orderBannerOsaifuLifePlus(new CentralFunc.OrderBannerOsaifuLifePlusListener() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.5
                @Override // com.felicanetworks.mfm.main.model.CentralFunc.OrderBannerOsaifuLifePlusListener
                public void onSuccess(final Bitmap bitmap) {
                    try {
                        AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.5.1
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    orderBannerImageListener.onCompleteBannerImage(bitmap);
                                } catch (Exception e) {
                                    MfmException mfmException = new MfmException(CentralFuncAgent.class, 512, e);
                                    LogUtil.error(mfmException);
                                    StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                                }
                            }
                        });
                    } catch (Exception e) {
                        MfmException mfmException = new MfmException(CentralFuncAgent.class, InputDeviceCompat.SOURCE_DPAD, e);
                        LogUtil.error(mfmException);
                        StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                    }
                }
            });
        } catch (Exception e) {
            MfmException mfmException = new MfmException(CentralFuncAgent.class, 514, e);
            LogUtil.error(mfmException);
            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
        }
    }

    public void cancelBannerImage() {
        this._centralFunc.cancelOrderBannerOsaifuLifePlus();
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
                linkedHashMap.put(entry.getKey(), linkedHashMap2.get(entry.getKey()));
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
                MyServiceInfoAgent myServiceInfoAgentCreate = MyServiceInfoAgent.Factory.create(myServiceInfo3, list2, this._mfiCardFunc.isLoggedIn());
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
                        myServiceGroupInfoAgent.addGroupState(new MyServiceGroupInfoAgent.DeleteCardState(MyServiceInfoAgent.Factory.create(myServiceInfo4, list3, this._mfiCardFunc.isLoggedIn())));
                    }
                }
            }
            if (!myServiceGroupInfoAgent.isHidden()) {
                marshalledMyServiceGroups.myServiceGroups.add(myServiceGroupInfoAgent);
            }
        }
        final List<String> sortOrderGroupIds = MyServiceDatabaseAccess.getInstance().getSortOrderGroupIds();
        Collections.sort(marshalledMyServiceGroups.myServiceGroups, new Comparator<MyServiceGroupInfoAgent>() { // from class: com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent.6
            @Override // java.util.Comparator
            public int compare(MyServiceGroupInfoAgent myServiceGroupInfoAgent2, MyServiceGroupInfoAgent myServiceGroupInfoAgent3) {
                String groupId = myServiceGroupInfoAgent2.getGroupId();
                String groupId2 = myServiceGroupInfoAgent3.getGroupId();
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

    private List<RecommendCategoryInfoAgent> marshalRecommendCategory(Map<String, MyServiceInfo.RegisterState> map) {
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
            if (MyServiceInfo.RegisterState.REGISTERED == map.get(recommendInfoAgent.getId()) || hasDeleteNotExistTCard(recommendInfoAgent.getId())) {
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

    private MyServiceInfo.RegisterState createRegisterState(MyServiceInfoAgent myServiceInfoAgent) {
        if (!this._mfiCardFunc.isLoggedIn() && myServiceInfoAgent.isType2()) {
            return MyServiceInfo.RegisterState.NO_REGISTER;
        }
        if (MyServiceInfo.RegisterState.REGISTERED == myServiceInfoAgent.getClient().getRegisterState()) {
            return MyServiceInfo.RegisterState.REGISTERED;
        }
        if (!myServiceInfoAgent.getMyCardInfoAgentList().isEmpty()) {
            return MyServiceInfo.RegisterState.REGISTERED;
        }
        if (MyServiceInfo.RegisterState.NONE == myServiceInfoAgent.getClient().getRegisterState() && MyServiceInfo.RegisterState.REGISTERED == myServiceInfoAgent.getClient().getAppInstalledState()) {
            return MyServiceInfo.RegisterState.REGISTERED;
        }
        return MyServiceInfo.RegisterState.NO_REGISTER;
    }

    public boolean hasDeleteNotExistTCard(String str) {
        Iterator<MyServiceInfo> it = this._centralFunc.getDeleteServiceInfoList().iterator();
        while (it.hasNext()) {
            if (it.next().sid().equals(str)) {
                return true;
            }
        }
        return false;
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

        CompiledState(CentralFunc.CompiledState compiledState) {
            this._client = compiledState;
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
