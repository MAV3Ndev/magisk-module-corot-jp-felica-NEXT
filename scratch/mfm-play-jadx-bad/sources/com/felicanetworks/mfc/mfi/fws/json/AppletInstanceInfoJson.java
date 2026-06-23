package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
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

    public void setAppletInstanceInfo(String appletInstanceAid, String binaryVersion, String appletVersion, String packageKeyVersion) throws JSONException {
        if (appletInstanceAid == null || binaryVersion == null || appletVersion == null || packageKeyVersion == null) {
            return;
        }
        put(NAME_APPLET_INSTANCE_AID, appletInstanceAid);
        put(NAME_APPLET_INSTANCE_BINARY_VERSION, binaryVersion);
        put(NAME_APPLET_INSTANCE_APPLET_VERSION, appletVersion);
        put(NAME_APPLET_INSTANCE_PACKAGE_KEY_VERSION, packageKeyVersion);
    }

    public void setHasCid(Boolean hasCid) throws JSONException {
        if (hasCid != null) {
            put(NAME_HAS_CID, hasCid.booleanValue());
        }
    }
}
