package com.amazonaws.mobileconnectors.pinpoint.analytics;

/* JADX INFO: loaded from: classes.dex */
public class PinpointSession {
    private final Long sessionDuration;
    private final String sessionId;
    private final Long sessionStart;
    private final Long sessionStop;

    public PinpointSession(String str, Long l, Long l2, Long l3) {
        this.sessionId = str;
        this.sessionStart = l;
        this.sessionStop = l2;
        this.sessionDuration = l3;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public Long getSessionStart() {
        return this.sessionStart;
    }

    public Long getSessionStop() {
        return this.sessionStop;
    }

    public Long getSessionDuration() {
        return this.sessionDuration;
    }
}
