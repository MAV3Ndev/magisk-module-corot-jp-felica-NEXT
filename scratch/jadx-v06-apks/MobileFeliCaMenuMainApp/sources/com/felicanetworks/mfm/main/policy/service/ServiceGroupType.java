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

/* JADX INFO: loaded from: classes.dex */
public enum ServiceGroupType {
    TRANSPORTATION(R.drawable.detail_category_traffic, R.string.card_item_text_traffic_card, Arrays.asList(SupportServiceType.T1, SupportServiceType.S1), FeliCaParams.SERVICE_ID_SUICA),
    ID(-1, -1, Collections.emptyList(), FeliCaParams.SERVICE_ID_DCARD),
    NONE(-1, -1, Collections.emptyList(), new String[0]);

    public static final int INVALID_RES = -1;
    public final int iconRes;
    public final int titleRes;
    public final List<SupportServiceType> targetTypes = new ArrayList();
    public final List<String> targetIds = new ArrayList();

    ServiceGroupType(int i, int i2, List list, String... strArr) {
        this.iconRes = i;
        this.titleRes = i2;
        this.targetTypes.addAll(list);
        this.targetIds.addAll(Arrays.asList(strArr));
    }

    public String groupId(String str) {
        return this.targetIds.isEmpty() ? str : this.targetIds.get(0);
    }

    public String groupId(List<MyServiceInfoAgent> list) {
        if (this.targetIds.isEmpty()) {
            if (list.isEmpty()) {
                throw new IllegalStateException("This is Bug !!");
            }
            return list.get(0).getId();
        }
        return this.targetIds.get(0);
    }

    public static ServiceGroupType resolve(String str) {
        for (ServiceGroupType serviceGroupType : values()) {
            if (serviceGroupType.targetIds.contains(str)) {
                return serviceGroupType;
            }
        }
        return NONE;
    }

    public static ServiceGroupType resolve(List<String> list) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            ServiceGroupType serviceGroupTypeResolve = resolve(it.next());
            if (serviceGroupTypeResolve != NONE) {
                return serviceGroupTypeResolve;
            }
        }
        return NONE;
    }

    public static void inject(List<Service> list) {
        for (Service service : list) {
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
