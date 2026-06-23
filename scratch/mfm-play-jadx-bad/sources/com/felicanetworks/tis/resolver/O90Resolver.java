package com.felicanetworks.tis.resolver;

import com.felicanetworks.tis.R;

/* JADX INFO: loaded from: classes3.dex */
class O90Resolver extends TrafficServiceResolver {
    private static final String SERVICE_ID = "o90";
    private static final int TITLE_RESOURCE_ID = R.string.tis_title_o90;
    private static final int ICON_RESOURCE_ID = R.drawable.tis_o90_icon;
    private static final int CARD_IMAGE_RESOURCE_ID = R.drawable.tis_o90_card;

    public O90Resolver() {
        super(SERVICE_ID, "SV000253", TITLE_RESOURCE_ID, ICON_RESOURCE_ID, CARD_IMAGE_RESOURCE_ID);
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    String getMenuAppServiceId() {
        return "SV000253";
    }
}
