package com.felicanetworks.tis.resolver;

import com.felicanetworks.tis.R;

/* JADX INFO: loaded from: classes3.dex */
class T18Resolver extends TrafficServiceResolver {
    private static final String SERVICE_ID = "t18";
    private static final int TITLE_RESOURCE_ID = R.string.tis_title_t18;
    private static final int ICON_RESOURCE_ID = R.drawable.tis_t18_icon;
    private static final int CARD_IMAGE_RESOURCE_ID = R.drawable.tis_t18_card;

    public T18Resolver() {
        super(SERVICE_ID, "SV000276", TITLE_RESOURCE_ID, ICON_RESOURCE_ID, CARD_IMAGE_RESOURCE_ID);
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    String getMenuAppServiceId() {
        return "SV000276";
    }
}
