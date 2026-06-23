package com.felicanetworks.mfm.main.presenter.agent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.felicanetworks.mfm.main.policy.service.ServiceGroupType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
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

    public MyServiceGroupInfoAgent(List<MyServiceInfoAgent> list) {
        ArrayList arrayList = new ArrayList();
        for (MyServiceInfoAgent myServiceInfoAgent : list) {
            arrayList.add(myServiceInfoAgent.getId());
            if (myServiceInfoAgent.hasActiveForegroundCard()) {
                this.services.add(0, myServiceInfoAgent);
            } else {
                this.services.add(myServiceInfoAgent);
            }
        }
        ServiceGroupType serviceGroupTypeResolve = ServiceGroupType.resolve(arrayList);
        this.type = serviceGroupTypeResolve;
        this.id = serviceGroupTypeResolve.groupId(list);
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

    public void addGroupState(GroupState groupState) {
        this.states.add(groupState);
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

    public MyServiceInfoAgent findService(String str) {
        for (MyServiceInfoAgent myServiceInfoAgent : this.services) {
            if (TextUtils.equals(str, myServiceInfoAgent.getId())) {
                return myServiceInfoAgent;
            }
        }
        return null;
    }

    public MyServiceInfoAgent findDeleteService(String str) {
        for (GroupState groupState : this.states) {
            if (groupState instanceof DeleteCardState) {
                MyServiceInfoAgent myServiceInfoAgent = ((DeleteCardState) groupState).getMyServiceInfoAgent();
                if (TextUtils.equals(str, myServiceInfoAgent.getId())) {
                    return myServiceInfoAgent;
                }
            }
        }
        return null;
    }

    public MyServiceInfoAgent findServiceWithCid(String str) {
        for (MyServiceInfoAgent myServiceInfoAgent : this.services) {
            if (myServiceInfoAgent.findCard(str) != null) {
                return myServiceInfoAgent;
            }
        }
        return null;
    }

    public MyCardInfoAgent findCard(String str) {
        Iterator<MyServiceInfoAgent> it = this.services.iterator();
        while (it.hasNext()) {
            MyCardInfoAgent myCardInfoAgentFindCard = it.next().findCard(str);
            if (myCardInfoAgentFindCard != null) {
                return myCardInfoAgentFindCard;
            }
        }
        return null;
    }

    public MyCardInfoAgent findDeleteCard(String str) {
        MyCardInfoAgent myCardInfoAgentFindCard;
        for (GroupState groupState : this.states) {
            if ((groupState instanceof DeleteCardState) && (myCardInfoAgentFindCard = ((DeleteCardState) groupState).getMyServiceInfoAgent().findCard(str)) != null) {
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
        return mainService().getName();
    }

    public Bitmap getGroupIcon(Context context) {
        if (this.type.iconRes != -1) {
            return BitmapFactory.decodeResource(context.getResources(), this.type.iconRes);
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

        public DeleteCardState(MyServiceInfoAgent myServiceInfoAgent) {
            this._service = myServiceInfoAgent;
        }

        public MyServiceInfoAgent getMyServiceInfoAgent() {
            return this._service;
        }
    }
}
