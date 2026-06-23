package com.felicanetworks.tis.resolver;

import com.felicanetworks.tis.R;

/* JADX INFO: loaded from: classes3.dex */
class C17Resolver extends TrafficServiceResolver {
    private static final String SERVICE_ID = "c17";
    private static final int TITLE_RESOURCE_ID = R.string.tis_title_c17;
    private static final int ICON_RESOURCE_ID = R.drawable.tis_c17_icon;
    private static final int CARD_IMAGE_RESOURCE_ID = R.drawable.tis_c17_card;

    public C17Resolver() {
        super(SERVICE_ID, "SV000271", TITLE_RESOURCE_ID, ICON_RESOURCE_ID, CARD_IMAGE_RESOURCE_ID);
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    String getMenuAppServiceId() {
        return "SV000271";
    }
}
