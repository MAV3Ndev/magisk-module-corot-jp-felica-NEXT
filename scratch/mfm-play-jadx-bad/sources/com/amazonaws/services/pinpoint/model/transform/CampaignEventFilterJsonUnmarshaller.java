package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CampaignEventFilter;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class CampaignEventFilterJsonUnmarshaller implements Unmarshaller<CampaignEventFilter, JsonUnmarshallerContext> {
    private static CampaignEventFilterJsonUnmarshaller instance;

    CampaignEventFilterJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public CampaignEventFilter unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        CampaignEventFilter campaignEventFilter = new CampaignEventFilter();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Dimensions")) {
                campaignEventFilter.setDimensions(EventDimensionsJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("FilterType")) {
                campaignEventFilter.setFilterType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return campaignEventFilter;
    }

    public static CampaignEventFilterJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CampaignEventFilterJsonUnmarshaller();
        }
        return instance;
    }
}
