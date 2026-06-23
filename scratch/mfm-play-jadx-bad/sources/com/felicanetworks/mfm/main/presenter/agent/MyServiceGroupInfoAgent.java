package com.felicanetworks.mfm.main.presenter.agent;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.felicanetworks.mfm.main.policy.service.ServiceGroupType;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MyServiceGroupInfoAgent extends InfoAgent {
    private final String id;
    private final List<MyServiceInfoAgent> services = new ArrayList();
    private final List<GroupState> states;
    private final ServiceGroupType type;

    public static abstract class GroupState {
    }

    public String toString() {
        return "MyServiceGroupInfoAgent{id='" + this.id + "', type=" + this.type + ", services=" + this.services + ", states=" + this.states + '}';
    }

    public MyServiceGroupInfoAgent(final List<MyServiceInfoAgent> services) {
        ArrayList arrayList = new ArrayList();
        for (MyServiceInfoAgent myServiceInfoAgent : services) {
            arrayList.add(myServiceInfoAgent.getId());
            if (myServiceInfoAgent.hasActiveForegroundCard()) {
                this.services.add(0, myServiceInfoAgent);
            } else {
                this.services.add(myServiceInfoAgent);
            }
        }
        ServiceGroupType serviceGroupTypeResolve = ServiceGroupType.resolve(arrayList);
        this.type = serviceGroupTypeResolve;
        this.id = serviceGroupTypeResolve.groupId(services);
        this.states = new ArrayList();
    }

    public ServiceGroupType getType() {
        return this.type;
    }

    public boolean hasHeader() {
        return getType() != ServiceGroupType.NONE;
    }

    public boolean isChangeableCard() {
        if (!hasMoreInformation()) {
            return false;
        }
        Iterator<MyServiceInfoAgent> it = this.services.iterator();
        while (it.hasNext()) {
            if (!it.next().getMyCardInfoAgentList().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMoreInformation() {
        Iterator<MyServiceInfoAgent> it = this.services.iterator();
        while (it.hasNext()) {
            if (it.next().hasMoreInformation()) {
                return true;
            }
        }
        return false;
    }

    public void addGroupState(GroupState state) {
        this.states.add(state);
    }

    public boolean hasDeleteCard() {
        Iterator<GroupState> it = this.states.iterator();
        while (it.hasNext()) {
            if (it.next() instanceof DeleteCardState) {
                return true;
            }
        }
        return false;
    }

    public String getGroupId() {
        return this.id;
    }

    public List<MyServiceInfoAgent> getServices() {
        return this.services;
    }

    public MyServiceInfoAgent findService(String sid) {
        for (MyServiceInfoAgent myServiceInfoAgent : this.services) {
            if (TextUtils.equals(sid, myServiceInfoAgent.getId())) {
                return myServiceInfoAgent;
            }
        }
        return null;
    }

    public MyServiceInfoAgent findDeleteService(String sid) {
        for (GroupState groupState : this.states) {
            if (groupState instanceof DeleteCardState) {
                MyServiceInfoAgent myServiceInfoAgent = ((DeleteCardState) groupState).getMyServiceInfoAgent();
                if (TextUtils.equals(sid, myServiceInfoAgent.getId())) {
                    return myServiceInfoAgent;
                }
            }
        }
        return null;
    }

    public MyServiceInfoAgent findServiceWithCid(String cid) {
        for (MyServiceInfoAgent myServiceInfoAgent : this.services) {
            if (myServiceInfoAgent.findCard(cid) != null) {
                return myServiceInfoAgent;
            }
        }
        return null;
    }

    public MyCardInfoAgent findCard(String cid) {
        Iterator<MyServiceInfoAgent> it = this.services.iterator();
        while (it.hasNext()) {
            MyCardInfoAgent myCardInfoAgentFindCard = it.next().findCard(cid);
            if (myCardInfoAgentFindCard != null) {
                return myCardInfoAgentFindCard;
            }
        }
        return null;
    }

    public MyCardInfoAgent findDeleteCard(String cid) {
        MyCardInfoAgent myCardInfoAgentFindCard;
        for (GroupState groupState : this.states) {
            if ((groupState instanceof DeleteCardState) && (myCardInfoAgentFindCard = ((DeleteCardState) groupState).getMyServiceInfoAgent().findCard(cid)) != null) {
                return myCardInfoAgentFindCard;
            }
        }
        return null;
    }

    public MyServiceInfoAgent getHighPriorityService() {
        for (MyServiceInfoAgent myServiceInfoAgent : this.services) {
            if (myServiceInfoAgent.isActiveService()) {
                return myServiceInfoAgent;
            }
        }
        return this.services.get(0);
    }

    public String getGroupTitle(Context context) {
        if (this.type.titleRes != -1) {
            return context.getResources().getString(this.type.titleRes);
        }
        if (this.type == ServiceGroupType.QP) {
            return (String) Sg.getValue(Sg.Key.SETTING_QP_GROUP_NAME);
        }
        return mainService().getName();
    }

    public Bitmap getGroupIcon(Context context) {
        if (this.type.iconRes != -1) {
            return BitmapFactory.decodeResource(context.getResources(), this.type.iconRes);
        }
        if (this.type == ServiceGroupType.QP) {
            Resources resources = context.getResources();
            return BitmapFactory.decodeResource(resources, resources.getIdentifier((String) Sg.getValue(Sg.Key.SETTING_QP_GROUP_ICON), "drawable", context.getPackageName()));
        }
        return mainService().getIcon();
    }

    public boolean isHidden() {
        if (hasDeleteCard()) {
            return false;
        }
        Iterator<MyServiceInfoAgent> it = this.services.iterator();
        while (it.hasNext()) {
            if (!it.next().isHidden()) {
                return false;
            }
        }
        return true;
    }

    private MyServiceInfoAgent mainService() {
        return this.services.get(0);
    }

    public List<GroupState> getGroupStates() {
        return this.states;
    }

    public static class DeleteCardState extends GroupState {
        private final MyServiceInfoAgent _service;

        public DeleteCardState(MyServiceInfoAgent service) {
            this._service = service;
        }

        public MyServiceInfoAgent getMyServiceInfoAgent() {
            return this._service;
        }
    }
}
