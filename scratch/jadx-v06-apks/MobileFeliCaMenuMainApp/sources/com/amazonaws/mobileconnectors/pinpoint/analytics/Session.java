package com.amazonaws.mobileconnectors.pinpoint.analytics;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.PinpointContext;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONBuilder;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONSerializable;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.Preconditions;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class Session implements JSONSerializable {
    private static final int INDENTATION = 4;
    private static final String JSON_SESSION_ID_ATTR = "session_id";
    private static final String JSON_START_TIME_ATTR = "start_time";
    private static final String JSON_STOP_TIME_ATTR = "stop_time";
    private static final Log LOGGER = LogFactory.getLog((Class<?>) Session.class);
    protected static final int SESSION_ID_APPKEY_LENGTH = 8;
    protected static final String SESSION_ID_DATE_FORMAT = "yyyyMMdd";
    protected static final char SESSION_ID_DELIMITER = '-';
    protected static final char SESSION_ID_PAD_CHAR = '_';
    protected static final String SESSION_ID_TIME_FORMAT = "HHmmssSSS";
    protected static final int SESSION_ID_UNIQID_LENGTH = 8;
    private final String sessionID;
    private final DateFormat sessionIdTimeFormat;
    private final Long startTime;
    private Long stopTime;

    protected Session(PinpointContext pinpointContext) {
        this.stopTime = null;
        Preconditions.checkNotNull(pinpointContext, "A valid PinpointContext must be provided!");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd-HHmmssSSS", Locale.US);
        this.sessionIdTimeFormat = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.startTime = Long.valueOf(System.currentTimeMillis());
        this.stopTime = null;
        this.sessionID = generateSessionID(pinpointContext);
    }

    protected Session(String str, String str2, String str3) {
        this.stopTime = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd-HHmmssSSS", Locale.US);
        this.sessionIdTimeFormat = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.startTime = Long.valueOf(new Scanner(str2).nextLong());
        Long lValueOf = Long.valueOf(new Scanner(str3).nextLong());
        this.stopTime = lValueOf;
        this.sessionID = str;
        if (lValueOf.longValue() == Long.MIN_VALUE) {
            this.stopTime = null;
        }
    }

    public static Session newInstance(PinpointContext pinpointContext) {
        return new Session(pinpointContext);
    }

    public static Session getSessionFromSerializedSession(String str) {
        if (StringUtil.isBlank(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            return new Session(jSONObject.getString(JSON_SESSION_ID_ATTR), jSONObject.getString(JSON_START_TIME_ATTR), jSONObject.getString(JSON_STOP_TIME_ATTR));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isPaused() {
        return this.stopTime != null;
    }

    public void pause() {
        if (isPaused()) {
            return;
        }
        this.stopTime = Long.valueOf(System.currentTimeMillis());
    }

    public void resume() {
        this.stopTime = null;
    }

    public Long getSessionDuration() {
        Long lValueOf = this.stopTime;
        if (lValueOf == null) {
            lValueOf = Long.valueOf(System.currentTimeMillis());
        }
        if (lValueOf.longValue() < this.startTime.longValue()) {
            return 0L;
        }
        long j = -1L;
        try {
            return Long.valueOf(lValueOf.longValue() - this.startTime.longValue());
        } catch (NumberFormatException e) {
            LOGGER.error("error parsing session duration", e);
            return j;
        }
    }

    public String generateSessionID(PinpointContext pinpointContext) {
        String uniqueId = pinpointContext.getUniqueId();
        return StringUtil.trimOrPadString(uniqueId, 8, SESSION_ID_PAD_CHAR) + SESSION_ID_DELIMITER + this.sessionIdTimeFormat.format(this.startTime);
    }

    @Override // com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONSerializable
    public JSONObject toJSONObject() {
        long j = this.stopTime;
        if (j == null) {
            j = Long.MIN_VALUE;
        }
        JSONBuilder jSONBuilder = new JSONBuilder(this);
        jSONBuilder.withAttribute(JSON_SESSION_ID_ATTR, getSessionID());
        jSONBuilder.withAttribute(JSON_START_TIME_ATTR, Long.valueOf(getStartTime()));
        jSONBuilder.withAttribute(JSON_STOP_TIME_ATTR, j);
        return jSONBuilder.toJSONObject();
    }

    public String toString() {
        JSONObject jSONObject = toJSONObject();
        try {
            return jSONObject.toString(4);
        } catch (JSONException unused) {
            return jSONObject.toString();
        }
    }

    public String getSessionID() {
        return this.sessionID;
    }

    public long getStartTime() {
        return this.startTime.longValue();
    }

    public Long getStopTime() {
        return this.stopTime;
    }
}
