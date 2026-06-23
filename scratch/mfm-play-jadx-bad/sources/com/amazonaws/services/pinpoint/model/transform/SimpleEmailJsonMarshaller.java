package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SimpleEmail;
import com.amazonaws.services.pinpoint.model.SimpleEmailPart;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class SimpleEmailJsonMarshaller {
    private static SimpleEmailJsonMarshaller instance;

    SimpleEmailJsonMarshaller() {
    }

    public void marshall(SimpleEmail simpleEmail, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (simpleEmail.getHtmlPart() != null) {
            SimpleEmailPart htmlPart = simpleEmail.getHtmlPart();
            awsJsonWriter.name("HtmlPart");
            SimpleEmailPartJsonMarshaller.getInstance().marshall(htmlPart, awsJsonWriter);
        }
        if (simpleEmail.getSubject() != null) {
            SimpleEmailPart subject = simpleEmail.getSubject();
            awsJsonWriter.name("Subject");
            SimpleEmailPartJsonMarshaller.getInstance().marshall(subject, awsJsonWriter);
        }
        if (simpleEmail.getTextPart() != null) {
            SimpleEmailPart textPart = simpleEmail.getTextPart();
            awsJsonWriter.name("TextPart");
            SimpleEmailPartJsonMarshaller.getInstance().marshall(textPart, awsJsonWriter);
        }
        awsJsonWriter.endObject();
    }

    public static SimpleEmailJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SimpleEmailJsonMarshaller();
        }
        return instance;
    }
}
