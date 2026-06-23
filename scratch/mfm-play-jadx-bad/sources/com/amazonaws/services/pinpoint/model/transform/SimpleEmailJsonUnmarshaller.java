package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SimpleEmail;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class SimpleEmailJsonUnmarshaller implements Unmarshaller<SimpleEmail, JsonUnmarshallerContext> {
    private static SimpleEmailJsonUnmarshaller instance;

    SimpleEmailJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public SimpleEmail unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SimpleEmail simpleEmail = new SimpleEmail();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("HtmlPart")) {
                simpleEmail.setHtmlPart(SimpleEmailPartJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Subject")) {
                simpleEmail.setSubject(SimpleEmailPartJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TextPart")) {
                simpleEmail.setTextPart(SimpleEmailPartJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return simpleEmail;
    }

    public static SimpleEmailJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SimpleEmailJsonUnmarshaller();
        }
        return instance;
    }
}
