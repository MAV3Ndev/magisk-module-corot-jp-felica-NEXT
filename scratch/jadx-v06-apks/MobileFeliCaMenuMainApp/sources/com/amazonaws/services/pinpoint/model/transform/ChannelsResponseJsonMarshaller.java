package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ChannelResponse;
import com.amazonaws.services.pinpoint.model.ChannelsResponse;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ChannelsResponseJsonMarshaller {
    private static ChannelsResponseJsonMarshaller instance;

    ChannelsResponseJsonMarshaller() {
    }

    public void marshall(ChannelsResponse channelsResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (channelsResponse.getChannels() != null) {
            Map<String, ChannelResponse> channels = channelsResponse.getChannels();
            awsJsonWriter.name("Channels");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, ChannelResponse> entry : channels.entrySet()) {
                ChannelResponse value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    ChannelResponseJsonMarshaller.getInstance().marshall(value, awsJsonWriter);
                }
            }
            awsJsonWriter.endObject();
        }
        awsJsonWriter.endObject();
    }

    public static ChannelsResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ChannelsResponseJsonMarshaller();
        }
        return instance;
    }
}
