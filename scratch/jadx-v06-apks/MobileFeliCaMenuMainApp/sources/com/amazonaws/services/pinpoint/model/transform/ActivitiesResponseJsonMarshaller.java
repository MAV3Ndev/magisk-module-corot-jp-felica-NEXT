package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ActivitiesResponse;
import com.amazonaws.services.pinpoint.model.ActivityResponse;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class ActivitiesResponseJsonMarshaller {
    private static ActivitiesResponseJsonMarshaller instance;

    ActivitiesResponseJsonMarshaller() {
    }

    public void marshall(ActivitiesResponse activitiesResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (activitiesResponse.getItem() != null) {
            List<ActivityResponse> item = activitiesResponse.getItem();
            awsJsonWriter.name("Item");
            awsJsonWriter.beginArray();
            for (ActivityResponse activityResponse : item) {
                if (activityResponse != null) {
                    ActivityResponseJsonMarshaller.getInstance().marshall(activityResponse, awsJsonWriter);
                }
            }
            awsJsonWriter.endArray();
        }
        if (activitiesResponse.getNextToken() != null) {
            String nextToken = activitiesResponse.getNextToken();
            awsJsonWriter.name("NextToken");
            awsJsonWriter.value(nextToken);
        }
        awsJsonWriter.endObject();
    }

    public static ActivitiesResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ActivitiesResponseJsonMarshaller();
        }
        return instance;
    }
}
