package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SimpleEmailPart implements Serializable {
    private String charset;
    private String data;

    public String getCharset() {
        return this.charset;
    }

    public void setCharset(String str) {
        this.charset = str;
    }

    public SimpleEmailPart withCharset(String str) {
        this.charset = str;
        return this;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String str) {
        this.data = str;
    }

    public SimpleEmailPart withData(String str) {
        this.data = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getCharset() != null) {
            sb.append("Charset: " + getCharset() + ",");
        }
        if (getData() != null) {
            sb.append("Data: " + getData());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getCharset() == null ? 0 : getCharset().hashCode()) + 31) * 31) + (getData() != null ? getData().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SimpleEmailPart)) {
            return false;
        }
        SimpleEmailPart simpleEmailPart = (SimpleEmailPart) obj;
        if ((simpleEmailPart.getCharset() == null) ^ (getCharset() == null)) {
            return false;
        }
        if (simpleEmailPart.getCharset() != null && !simpleEmailPart.getCharset().equals(getCharset())) {
            return false;
        }
        if ((simpleEmailPart.getData() == null) ^ (getData() == null)) {
            return false;
        }
        return simpleEmailPart.getData() == null || simpleEmailPart.getData().equals(getData());
    }
}
