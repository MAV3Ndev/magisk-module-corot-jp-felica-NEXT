package com.felicanetworks.tis.resolver;

import com.felicanetworks.tis.R;

/* JADX INFO: loaded from: classes3.dex */
class S11Resolver extends TrafficServiceResolver {
    private static final String SERVICE_ID = "s11";
    private static final int TITLE_RESOURCE_ID = R.string.tis_title_s11;
    private static final int ICON_RESOURCE_ID = R.drawable.tis_s11_icon;
    private static final int CARD_IMAGE_RESOURCE_ID = R.drawable.tis_s11_card;

    public S11Resolver() {
        super(SERVICE_ID, "SV000027", TITLE_RESOURCE_ID, ICON_RESOURCE_ID, CARD_IMAGE_RESOURCE_ID);
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    String getMenuAppServiceId() {
        return "SV000027";
    }
}
