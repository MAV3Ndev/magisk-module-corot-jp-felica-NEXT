package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AppletInstanceInfoJson extends JSONObject implements IJsonMember {
    private static final String NAME = "appletInstanceInfo";
    private static final String NAME_APPLET_INSTANCE_AID = "appletInstanceAid";
    private static final String NAME_APPLET_INSTANCE_APPLET_VERSION = "appletInstanceAppletVersion";
    private static final String NAME_APPLET_INSTANCE_BINARY_VERSION = "appletInstanceBinaryVersion";
    private static final String NAME_APPLET_INSTANCE_PACKAGE_KEY_VERSION = "appletInstancePackageKeyVersion";
    private static final String NAME_HAS_CID = "hasCid";

    @Override // com.felicanetworks.mfc.mfi.fws.json.IJsonMember
    public String getName() {
        return NAME;
    }

    public void setAppletInstanceInfo(String str, String str2, String str3, String str4) throws JSONException {
        if (str == null || str2 == null || str3 == null || str4 == null) {
            return;
        }
        put(NAME_APPLET_INSTANCE_AID, str);
        put(NAME_APPLET_INSTANCE_BINARY_VERSION, str2);
        put(NAME_APPLET_INSTANCE_APPLET_VERSION, str3);
        put(NAME_APPLET_INSTANCE_PACKAGE_KEY_VERSION, str4);
    }

    public void setHasCid(Boolean bool) throws JSONException {
        if (bool != null) {
            put(NAME_HAS_CID, bool.booleanValue());
        }
    }
}
