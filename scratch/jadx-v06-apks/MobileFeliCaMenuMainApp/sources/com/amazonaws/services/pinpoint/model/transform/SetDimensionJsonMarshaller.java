package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SetDimension;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class SetDimensionJsonMarshaller {
    private static SetDimensionJsonMarshaller instance;

    SetDimensionJsonMarshaller() {
    }

    public void marshall(SetDimension setDimension, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (setDimension.getDimensionType() != null) {
            String dimensionType = setDimension.getDimensionType();
            awsJsonWriter.name("DimensionType");
            awsJsonWriter.value(dimensionType);
        }
        if (setDimension.getValues() != null) {
            List<String> values = setDimension.getValues();
            awsJsonWriter.name("Values");
            awsJsonWriter.beginArray();
            for (String str : values) {
                if (str != null) {
                    awsJsonWriter.value(str);
                }
            }
            awsJsonWriter.endArray();
        }
        awsJsonWriter.endObject();
    }

    public static SetDimensionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SetDimensionJsonMarshaller();
        }
        return instance;
    }
}
