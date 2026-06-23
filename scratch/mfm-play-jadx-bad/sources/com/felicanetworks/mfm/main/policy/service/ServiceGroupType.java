package com.felicanetworks.mfm.main.policy.service;

import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.model.info.Service;
import com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public enum ServiceGroupType {
    TRANSPORTATION(R.drawable.detail_category_traffic, R.string.card_item_text_traffic_card, Arrays.asList(SupportServiceType.T1, SupportServiceType.S1), FeliCaParams.SERVICE_ID_SUICA),
    ID(-1, -1, Collections.EMPTY_LIST, FeliCaParams.SERVICE_ID_DCARD),
    QP(-1, -1, Collections.EMPTY_LIST, FeliCaParams.SERVICE_ID_QP),
    NONE(-1, -1, Collections.EMPTY_LIST, new String[0]);

    public static final int INVALID_RES = -1;
    public final int iconRes;
    public final List<String> targetIds;
    public final List<SupportServiceType> targetTypes;
    public final int titleRes;

    ServiceGroupType(int iconRes, int titleRes, List targetTypes, String... targetIds) {
        ArrayList arrayList = new ArrayList();
        this.targetTypes = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.targetIds = arrayList2;
        this.iconRes = iconRes;
        this.titleRes = titleRes;
        arrayList.addAll(targetTypes);
        arrayList2.addAll(Arrays.asList(targetIds));
    }

    public String groupId(String sid) {
        return this.targetIds.isEmpty() ? sid : this.targetIds.get(0);
    }

    public String groupId(List<MyServiceInfoAgent> services) {
        if (this.targetIds.isEmpty()) {
            if (services.isEmpty()) {
                throw new IllegalStateException("This is Bug !!");
            }
            return services.get(0).getId();
        }
        return this.targetIds.get(0);
    }

    public static ServiceGroupType resolve(String sid) {
        for (ServiceGroupType serviceGroupType : values()) {
            if (serviceGroupType.targetIds.contains(sid)) {
                return serviceGroupType;
            }
        }
        return NONE;
    }

    public static ServiceGroupType resolve(List<String> sids) {
        Iterator<String> it = sids.iterator();
        while (it.hasNext()) {
            ServiceGroupType serviceGroupTypeResolve = resolve(it.next());
            if (serviceGroupTypeResolve != NONE) {
                return serviceGroupTypeResolve;
            }
        }
        return NONE;
    }

    public static void inject(List<Service> services) {
        for (Service service : services) {
            for (ServiceGroupType serviceGroupType : values()) {
                if (!serviceGroupType.targetTypes.isEmpty()) {
                    SupportServiceType supportServiceTypeResolve = SupportServiceType.resolve(service.getServiceId());
                    Iterator<SupportServiceType> it = serviceGroupType.targetTypes.iterator();
                    while (it.hasNext()) {
                        if (it.next() == supportServiceTypeResolve && !serviceGroupType.targetIds.contains(service.getServiceId())) {
                            serviceGroupType.targetIds.add(service.getServiceId());
                        }
                    }
                }
            }
        }
    }
}
