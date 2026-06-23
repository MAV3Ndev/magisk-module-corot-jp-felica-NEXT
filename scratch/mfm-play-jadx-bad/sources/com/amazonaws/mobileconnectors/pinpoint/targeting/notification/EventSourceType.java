package com.amazonaws.mobileconnectors.pinpoint.targeting.notification;

import android.os.Bundle;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class EventSourceType {
    private static final String AWS_EVENT_TYPE_OPENED = "opened_notification";
    private static final String AWS_EVENT_TYPE_RECEIVED_BACKGROUND = "received_background";
    private static final String AWS_EVENT_TYPE_RECEIVED_FOREGROUND = "received_foreground";
    private static EventSourceType CAMPAIGN = null;
    private static final String CAMPAIGN_EVENT_SOURCE_NAME = "campaign";
    private static final String CAMPAIGN_EVENT_SOURCE_PREFIX = "_campaign";
    private static EventSourceType JOURNEY = null;
    private static final String JOURNEY_ATTRIBUTE_KEY = "journey";
    private static final String JOURNEY_EVENT_SOURCE_NAME = "journey";
    private static final String JOURNEY_EVENT_SOURCE_PREFIX = "_journey";
    private static final String PINPOINT_ATTRIBUTE_KEY = "pinpoint";
    private static final String TAG = "EventSourceType";
    private static EventSourceType UNKNOWN = new EventSourceType("unknown", "", "", "", "", new EventSourceAttributeParser());
    private static final String UNKNOWN_EVENT_SOURCE_NAME = "unknown";
    private final EventSourceAttributeParser attributeParser;
    private final String eventSourceActivityIdAttributeKey;
    private final String eventSourceIdAttributeKey;
    private final String eventSourceKeyPrefix;
    private final String eventSourceName;
    private final String eventTypeOpenend;
    private final String eventTypeReceivedBackground;
    private final String eventTypeReceivedForeground;

    static {
        CAMPAIGN = new EventSourceType("campaign", CAMPAIGN_EVENT_SOURCE_PREFIX, FirebaseAnalytics.Param.CAMPAIGN_ID, "campaign_activity_id", "pinpoint.campaign.", new CampaignAttributeParser());
        JOURNEY = new EventSourceType("journey", JOURNEY_EVENT_SOURCE_PREFIX, "journey_id", "journey_activity_id", null, new JourneyAttributeParser());
    }

    static EventSourceType getEventSourceType(Bundle bundle) {
        if (bundle == null) {
            return UNKNOWN;
        }
        if (bundle.containsKey(CAMPAIGN.getEventSourceKeyPrefix() + CAMPAIGN.getEventSourceIdAttributeKey())) {
            return CAMPAIGN;
        }
        if (bundle.containsKey(PINPOINT_ATTRIBUTE_KEY) && bundle.getString(PINPOINT_ATTRIBUTE_KEY).matches(".*\"journey_id\".*")) {
            return JOURNEY;
        }
        return UNKNOWN;
    }

    private EventSourceType(String str, String str2, String str3, String str4, String str5, EventSourceAttributeParser eventSourceAttributeParser) {
        this.eventSourceName = str;
        this.eventTypeOpenend = str2 + ".opened_notification";
        this.eventTypeReceivedBackground = str2 + ".received_background";
        this.eventTypeReceivedForeground = str2 + ".received_foreground";
        this.eventSourceIdAttributeKey = str3;
        this.eventSourceActivityIdAttributeKey = str4;
        this.eventSourceKeyPrefix = str5;
        this.attributeParser = eventSourceAttributeParser;
    }

    boolean isUnkown() {
        return "unknown".equals(this.eventSourceName);
    }

    EventSourceAttributeParser getAttributeParser() {
        return this.attributeParser;
    }

    String getEventSourceName() {
        return this.eventSourceName;
    }

    String getEventTypeOpenend() {
        return this.eventTypeOpenend;
    }

    String getEventTypeReceivedForeground() {
        return this.eventTypeReceivedForeground;
    }

    String getEventTypeReceivedBackground() {
        return this.eventTypeReceivedBackground;
    }

    String getEventSourceIdAttributeKey() {
        return this.eventSourceIdAttributeKey;
    }

    String getEventSourceActivityIdAttributeKey() {
        return this.eventSourceActivityIdAttributeKey;
    }

    String getEventSourceKeyPrefix() {
        return this.eventSourceKeyPrefix;
    }

    public static class EventSourceAttributeParser {
        public Map<String, String> parseAttributes(Bundle bundle) {
            return Collections.EMPTY_MAP;
        }
    }

    private static final class CampaignAttributeParser extends EventSourceAttributeParser {
        private CampaignAttributeParser() {
        }

        @Override // com.amazonaws.mobileconnectors.pinpoint.targeting.notification.EventSourceType.EventSourceAttributeParser
        public Map<String, String> parseAttributes(Bundle bundle) {
            HashMap map = new HashMap();
            if (bundle != null) {
                String eventSourceKeyPrefix = EventSourceType.CAMPAIGN.getEventSourceKeyPrefix();
                for (String str : bundle.keySet()) {
                    if (str.startsWith(eventSourceKeyPrefix)) {
                        map.put(str.replace(eventSourceKeyPrefix, ""), bundle.getString(str));
                    }
                }
            }
            return map;
        }
    }

    private static final class JourneyAttributeParser extends EventSourceAttributeParser {
        private JourneyAttributeParser() {
        }

        @Override // com.amazonaws.mobileconnectors.pinpoint.targeting.notification.EventSourceType.EventSourceAttributeParser
        public Map<String, String> parseAttributes(Bundle bundle) {
            HashMap map = new HashMap();
            String string = bundle.getString(EventSourceType.PINPOINT_ATTRIBUTE_KEY);
            if (string != null) {
                try {
                    JsonElement jsonElement = new JsonParser().parse(string).getAsJsonObject().get("journey");
                    if (jsonElement == null) {
                        return null;
                    }
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        map.put(entry.getKey(), ((JsonPrimitive) entry.getValue()).getAsString());
                    }
                } catch (JsonSyntaxException e) {
                    Log.w(EventSourceType.TAG, "Exception attempting to parse pinpoint JSON payload.", e);
                    Log.v(EventSourceType.TAG, "Payload: " + string);
                    return map;
                }
            }
            return map;
        }
    }
}
