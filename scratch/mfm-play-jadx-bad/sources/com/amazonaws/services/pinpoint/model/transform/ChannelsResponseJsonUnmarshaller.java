package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ChannelsResponse;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class ChannelsResponseJsonUnmarshaller implements Unmarshaller<ChannelsResponse, JsonUnmarshallerContext> {
    private static ChannelsResponseJsonUnmarshaller instance;

    ChannelsResponseJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public ChannelsResponse unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        ChannelsResponse channelsResponse = new ChannelsResponse();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("Channels")) {
                channelsResponse.setChannels(new MapUnmarshaller(ChannelResponseJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return channelsResponse;
    }

    public static ChannelsResponseJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ChannelsResponseJsonUnmarshaller();
        }
        return instance;
    }
}
