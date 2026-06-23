package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.http.HttpHeader;
import com.amazonaws.services.pinpoint.model.PublicEndpoint;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class PublicEndpointJsonUnmarshaller implements Unmarshaller<PublicEndpoint, JsonUnmarshallerContext> {
    private static PublicEndpointJsonUnmarshaller instance;

    PublicEndpointJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public PublicEndpoint unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        PublicEndpoint publicEndpoint = new PublicEndpoint();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Address")) {
                publicEndpoint.setAddress(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Attributes")) {
                publicEndpoint.setAttributes(new MapUnmarshaller(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance())).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("ChannelType")) {
                publicEndpoint.setChannelType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Demographic")) {
                publicEndpoint.setDemographic(EndpointDemographicJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("EffectiveDate")) {
                publicEndpoint.setEffectiveDate(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("EndpointStatus")) {
                publicEndpoint.setEndpointStatus(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals(HttpHeader.LOCATION)) {
                publicEndpoint.setLocation(EndpointLocationJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Metrics")) {
                publicEndpoint.setMetrics(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.DoubleJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("OptOut")) {
                publicEndpoint.setOptOut(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("RequestId")) {
                publicEndpoint.setRequestId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("User")) {
                publicEndpoint.setUser(EndpointUserJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return publicEndpoint;
    }

    public static PublicEndpointJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new PublicEndpointJsonUnmarshaller();
        }
        return instance;
    }
}
