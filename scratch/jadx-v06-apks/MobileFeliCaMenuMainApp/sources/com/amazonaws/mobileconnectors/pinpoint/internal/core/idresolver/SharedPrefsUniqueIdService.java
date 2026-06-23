package com.amazonaws.mobileconnectors.pinpoint.internal.core.idresolver;

import android.content.Context;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.PinpointContext;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.system.AndroidPreferences;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class SharedPrefsUniqueIdService {
    protected static final String UNIQUE_ID_KEY = "UniqueId";
    private static final Log log = LogFactory.getLog((Class<?>) SharedPrefsUniqueIdService.class);
    private String appId;
    private Context applicationContext;

    public SharedPrefsUniqueIdService(String str, Context context) {
        this.appId = null;
        this.applicationContext = null;
        this.appId = str;
        this.applicationContext = context;
    }

    public String getUniqueId(PinpointContext pinpointContext) {
        if (pinpointContext == null || pinpointContext.getSystem() == null || pinpointContext.getSystem().getPreferences() == null) {
            log.debug("Unable to generate unique id, pinpointContext has not been fully initialized.");
            return "";
        }
        String idFromPreferences = getIdFromPreferences(pinpointContext.getSystem().getPreferences());
        if (idFromPreferences != null && idFromPreferences != "") {
            return idFromPreferences;
        }
        String string = UUID.randomUUID().toString();
        storeUniqueId(pinpointContext.getSystem().getPreferences(), string);
        return string;
    }

    private String getIdFromPreferences(AndroidPreferences androidPreferences) {
        if (getLegacyId() != "") {
            return getLegacyId();
        }
        return androidPreferences.getString(UNIQUE_ID_KEY, null);
    }

    private String getLegacyId() {
        Context context;
        String string;
        String str = this.appId;
        return (str == null || (context = this.applicationContext) == null || (string = context.getSharedPreferences(str, 0).getString(UNIQUE_ID_KEY, null)) == null) ? "" : string;
    }

    private void storeUniqueId(AndroidPreferences androidPreferences, String str) {
        try {
            androidPreferences.putString(UNIQUE_ID_KEY, str);
        } catch (Exception unused) {
            log.error("There was an exception when trying to store the unique id into the Preferences.");
        }
    }
}
