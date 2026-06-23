package com.amazonaws.util.json;

import java.io.Reader;
import java.io.Writer;

/* JADX INFO: loaded from: classes.dex */
public interface AwsJsonFactory {
    AwsJsonReader getJsonReader(Reader reader);

    AwsJsonWriter getJsonWriter(Writer writer);
}
