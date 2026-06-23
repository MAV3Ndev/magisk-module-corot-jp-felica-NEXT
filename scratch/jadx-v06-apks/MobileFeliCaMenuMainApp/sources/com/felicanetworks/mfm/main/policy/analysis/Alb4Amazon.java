package com.felicanetworks.mfm.main.policy.analysis;

import android.content.Context;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsClient;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsEvent;
import com.amazonaws.mobileconnectors.pinpoint.analytics.SessionClient;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
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
            Context context = this._context;
            _pinpointManagerInstance = new PinpointManager(new PinpointConfiguration(context, new CognitoCachingCredentialsProvider(context, aWSConfiguration), aWSConfiguration));
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
        try {
            getAc().submitEvents();
        } catch (AmazonServiceException e) {
            throw e;
        }
    }

    @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
    public void stampUserAction(String str, Map<String, String> map) throws Exception {
        recode(str, map);
    }

    @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
    public void stampAutoDump(String str, Map<String, String> map) throws Exception {
        recode(str, map);
    }

    @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
    public void stampScreen(String str, Map<String, String> map) throws Exception {
        recode(str, map);
    }

    private void recode(String str, Map<String, String> map) {
        AnalyticsEvent analyticsEventCreateEvent = getAc().createEvent(str);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue();
            if (value == null || value.isEmpty()) {
                value = "";
            }
            analyticsEventCreateEvent.addAttribute(entry.getKey(), value);
        }
        getAc().recordEvent(analyticsEventCreateEvent);
    }
}
