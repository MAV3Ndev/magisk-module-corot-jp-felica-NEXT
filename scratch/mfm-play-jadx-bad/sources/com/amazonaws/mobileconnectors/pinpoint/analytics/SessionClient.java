package com.amazonaws.mobileconnectors.pinpoint.analytics;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.PinpointContext;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public class SessionClient {
    protected static final String NO_SESSION_ID = "00000000-00000000";
    public static final String SESSION_PAUSE_EVENT_TYPE = "_session.pause";
    public static final String SESSION_RESUME_EVENT_TYPE = "_session.resume";
    public static final String SESSION_START_EVENT_TYPE = "_session.start";
    public static final String SESSION_STOP_EVENT_TYPE = "_session.stop";
    protected static final String SHARED_PREFS_SESSION_KEY = "AWSPinpoint.Session";
    private static final Log log = LogFactory.getLog((Class<?>) SessionClient.class);
    protected final PinpointContext pinpointContext;
    protected Session session;

    protected enum SessionState {
        INACTIVE,
        ACTIVE,
        PAUSED
    }

    public SessionClient(PinpointContext pinpointContext) {
        Preconditions.checkNotNull(pinpointContext, "A valid PinpointContext must be provided!");
        Preconditions.checkNotNull(pinpointContext.getAnalyticsClient(), "A valid AnalyticsClient must be provided!");
        this.pinpointContext = pinpointContext;
        String string = pinpointContext.getSystem().getPreferences().getString(SHARED_PREFS_SESSION_KEY, null);
        if (string != null) {
            this.session = Session.getSessionFromSerializedSession(string);
        }
        if (this.session != null) {
            pinpointContext.getAnalyticsClient().setSessionId(this.session.getSessionID());
            pinpointContext.getAnalyticsClient().setSessionStartTime(this.session.getStartTime());
        } else if (pinpointContext.getPinpointConfiguration().getEnableTargeting()) {
            pinpointContext.getAnalyticsClient().setSessionId(NO_SESSION_ID);
            pinpointContext.getAnalyticsClient().setSessionStartTime(0L);
        }
    }

    public synchronized void startSession() {
        executeStop();
        executeStart();
    }

    public synchronized void stopSession() {
        executeStop();
    }

    public synchronized void pauseSession() {
        if (getSessionState().equals(SessionState.ACTIVE)) {
            executePause();
        }
    }

    public synchronized void resumeSession() {
        if (getSessionState().equals(SessionState.PAUSED)) {
            executeResume();
        } else {
            this.pinpointContext.getAnalyticsClient().recordEvent(this.pinpointContext.getAnalyticsClient().createEvent(SESSION_RESUME_EVENT_TYPE));
            log.warn("Session Resume Failed: No session is paused.");
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[SessionClient]\n- session: ");
        Session session = this.session;
        sb.append(session == null ? "<null>" : session.getSessionID());
        Session session2 = this.session;
        sb.append((session2 == null || !session2.isPaused()) ? "" : ": paused");
        return sb.toString();
    }

    protected void executeStart() {
        if (this.pinpointContext.getTargetingClient() != null) {
            this.pinpointContext.getTargetingClient().updateEndpointProfile();
        }
        this.session = Session.newInstance(this.pinpointContext);
        this.pinpointContext.getAnalyticsClient().setSessionId(this.session.getSessionID());
        this.pinpointContext.getAnalyticsClient().setSessionStartTime(this.session.getStartTime());
        log.info("Firing Session Event: _session.start");
        this.pinpointContext.getAnalyticsClient().recordEvent(this.pinpointContext.getAnalyticsClient().createEvent(SESSION_START_EVENT_TYPE));
    }

    protected void executeStop() {
        Session session = this.session;
        if (session == null) {
            log.info("Session Stop Failed: No session exists.");
            return;
        }
        if (!session.isPaused()) {
            this.session.pause();
        }
        log.info("Firing Session Event: _session.stop");
        this.pinpointContext.getAnalyticsClient().recordEvent(this.pinpointContext.getAnalyticsClient().createEvent(SESSION_STOP_EVENT_TYPE, this.session.getStartTime(), Long.valueOf(this.session.getStopTime() == null ? 0L : this.session.getStopTime().longValue()), this.session.getSessionDuration()));
        this.pinpointContext.getAnalyticsClient().clearEventSourceAttributes();
        this.session = null;
    }

    protected void executePause() {
        Session session = this.session;
        if (session == null) {
            log.info("Session Stop Failed: No session exists.");
            return;
        }
        session.pause();
        Log log2 = log;
        log2.debug("Session Paused: " + this.session.getSessionID());
        log2.info("Firing Session Event: _session.pause");
        this.pinpointContext.getAnalyticsClient().recordEvent(this.pinpointContext.getAnalyticsClient().createEvent(SESSION_PAUSE_EVENT_TYPE, this.session.getStartTime(), null, this.session.getSessionDuration()));
        this.pinpointContext.getSystem().getPreferences().putString(SHARED_PREFS_SESSION_KEY, this.session.toString());
    }

    protected void executeResume() {
        Session session = this.session;
        if (session == null) {
            return;
        }
        session.resume();
        Log log2 = log;
        log2.debug("Firing Session Event: _session.resume");
        this.pinpointContext.getAnalyticsClient().recordEvent(this.pinpointContext.getAnalyticsClient().createEvent(SESSION_RESUME_EVENT_TYPE));
        log2.info("Session Resumed: " + this.session.getSessionID());
    }

    protected Session getSession() {
        return this.session;
    }

    protected SessionState getSessionState() {
        Session session = this.session;
        if (session != null) {
            return session.isPaused() ? SessionState.PAUSED : SessionState.ACTIVE;
        }
        return SessionState.INACTIVE;
    }
}
