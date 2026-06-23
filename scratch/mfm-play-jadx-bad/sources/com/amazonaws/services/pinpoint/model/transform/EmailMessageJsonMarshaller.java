package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EmailMessage;
import com.amazonaws.services.pinpoint.model.RawEmail;
import com.amazonaws.services.pinpoint.model.SimpleEmail;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class EmailMessageJsonMarshaller {
    private static EmailMessageJsonMarshaller instance;

    EmailMessageJsonMarshaller() {
    }

    public void marshall(EmailMessage emailMessage, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (emailMessage.getBody() != null) {
            String body = emailMessage.getBody();
            awsJsonWriter.name("Body");
            awsJsonWriter.value(body);
        }
        if (emailMessage.getFeedbackForwardingAddress() != null) {
            String feedbackForwardingAddress = emailMessage.getFeedbackForwardingAddress();
            awsJsonWriter.name("FeedbackForwardingAddress");
            awsJsonWriter.value(feedbackForwardingAddress);
        }
        if (emailMessage.getFromAddress() != null) {
            String fromAddress = emailMessage.getFromAddress();
            awsJsonWriter.name("FromAddress");
            awsJsonWriter.value(fromAddress);
        }
        if (emailMessage.getRawEmail() != null) {
            RawEmail rawEmail = emailMessage.getRawEmail();
            awsJsonWriter.name("RawEmail");
            RawEmailJsonMarshaller.getInstance().marshall(rawEmail, awsJsonWriter);
        }
        if (emailMessage.getReplyToAddresses() != null) {
            List<String> replyToAddresses = emailMessage.getReplyToAddresses();
            awsJsonWriter.name("ReplyToAddresses");
            awsJsonWriter.beginArray();
            for (String str : replyToAddresses) {
                if (str != null) {
                    awsJsonWriter.value(str);
                }
            }
            awsJsonWriter.endArray();
        }
        if (emailMessage.getSimpleEmail() != null) {
            SimpleEmail simpleEmail = emailMessage.getSimpleEmail();
            awsJsonWriter.name("SimpleEmail");
            SimpleEmailJsonMarshaller.getInstance().marshall(simpleEmail, awsJsonWriter);
        }
        if (emailMessage.getSubstitutions() != null) {
            Map<String, List<String>> substitutions = emailMessage.getSubstitutions();
            awsJsonWriter.name("Substitutions");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, List<String>> entry : substitutions.entrySet()) {
                List<String> value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    awsJsonWriter.beginArray();
                    for (String str2 : value) {
                        if (str2 != null) {
                            awsJsonWriter.value(str2);
                        }
                    }
                    awsJsonWriter.endArray();
                }
            }
            awsJsonWriter.endObject();
        }
        awsJsonWriter.endObject();
    }

    public static EmailMessageJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EmailMessageJsonMarshaller();
        }
        return instance;
    }
}
