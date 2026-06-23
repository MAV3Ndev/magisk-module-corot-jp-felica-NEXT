package com.felicanetworks.mfm.main.policy.analysis;

import android.content.Context;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsClient;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsEvent;
import com.amazonaws.mobileconnectors.pinpoint.analytics.SessionClient;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class Alb4Amazon implements AnalysisLibBridgeInterface {
    private static PinpointManager _pinpointManagerInstance;
    private Context _context;

    @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
    public int getMaxDataSize() {
        return 200;
    }

    Alb4Amazon() {
    }

    private PinpointManager getPMInstance() {
        return _pinpointManagerInstance;
    }

    private void initializePM() {
        if (_pinpointManagerInstance == null) {
            AWSConfiguration aWSConfiguration = new AWSConfiguration(this._context);
            _pinpointManagerInstance = new PinpointManager(new PinpointConfiguration(this._context, new CognitoCachingCredentialsProvider(this._context, aWSConfiguration), aWSConfiguration));
        }
    }

    private SessionClient getSc() {
        return getPMInstance().getSessionClient();
    }

    private AnalyticsClient getAc() {
        return getPMInstance().getAnalyticsClient();
    }

    @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
    public void initialize(Context context) throws Exception {
        this._context = context;
        initializePM();
    }

    @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
    public void sendLogs() throws Exception {
        getAc().submitEvents();
    }

    @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
    public void stampUserAction(String event, Map<String, String> data) throws Exception {
        recode(event, data);
    }

    @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
    public void stampAutoDump(String event, Map<String, String> data) throws Exception {
        recode(event, data);
    }

    @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
    public void stampScreen(String event, Map<String, String> data) throws Exception {
        recode(event, data);
    }

    private void recode(String event, Map<String, String> data) {
        AnalyticsEvent analyticsEventCreateEvent = getAc().createEvent(event);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String value = entry.getValue();
            if (value == null || value.isEmpty()) {
                value = "";
            }
            analyticsEventCreateEvent.addAttribute(entry.getKey(), value);
        }
        getAc().recordEvent(analyticsEventCreateEvent);
    }
}
