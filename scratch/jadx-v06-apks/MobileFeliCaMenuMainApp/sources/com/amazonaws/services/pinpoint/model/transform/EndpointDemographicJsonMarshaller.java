package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.cognito.clientcontext.datacollection.DataRecordKey;
import com.amazonaws.services.pinpoint.model.EndpointDemographic;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class EndpointDemographicJsonMarshaller {
    private static EndpointDemographicJsonMarshaller instance;

    EndpointDemographicJsonMarshaller() {
    }

    public void marshall(EndpointDemographic endpointDemographic, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (endpointDemographic.getAppVersion() != null) {
            String appVersion = endpointDemographic.getAppVersion();
            awsJsonWriter.name("AppVersion");
            awsJsonWriter.value(appVersion);
        }
        if (endpointDemographic.getLocale() != null) {
            String locale = endpointDemographic.getLocale();
            awsJsonWriter.name("Locale");
            awsJsonWriter.value(locale);
        }
        if (endpointDemographic.getMake() != null) {
            String make = endpointDemographic.getMake();
            awsJsonWriter.name("Make");
            awsJsonWriter.value(make);
        }
        if (endpointDemographic.getModel() != null) {
            String model = endpointDemographic.getModel();
            awsJsonWriter.name("Model");
            awsJsonWriter.value(model);
        }
        if (endpointDemographic.getModelVersion() != null) {
            String modelVersion = endpointDemographic.getModelVersion();
            awsJsonWriter.name("ModelVersion");
            awsJsonWriter.value(modelVersion);
        }
        if (endpointDemographic.getPlatform() != null) {
            String platform = endpointDemographic.getPlatform();
            awsJsonWriter.name(DataRecordKey.PLATFORM);
            awsJsonWriter.value(platform);
        }
        if (endpointDemographic.getPlatformVersion() != null) {
            String platformVersion = endpointDemographic.getPlatformVersion();
            awsJsonWriter.name("PlatformVersion");
            awsJsonWriter.value(platformVersion);
        }
        if (endpointDemographic.getTimezone() != null) {
            String timezone = endpointDemographic.getTimezone();
            awsJsonWriter.name("Timezone");
            awsJsonWriter.value(timezone);
        }
        awsJsonWriter.endObject();
    }

    public static EndpointDemographicJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EndpointDemographicJsonMarshaller();
        }
        return instance;
    }
}
