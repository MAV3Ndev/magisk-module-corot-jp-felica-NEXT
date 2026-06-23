package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class RawEmail implements Serializable {
    private ByteBuffer data;

    public ByteBuffer getData() {
        return this.data;
    }

    public void setData(ByteBuffer byteBuffer) {
        this.data = byteBuffer;
    }

    public RawEmail withData(ByteBuffer byteBuffer) {
        this.data = byteBuffer;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getData() != null) {
            sb.append("Data: " + getData());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getData() == null ? 0 : getData().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof RawEmail)) {
            return false;
        }
        RawEmail rawEmail = (RawEmail) obj;
        if ((rawEmail.getData() == null) ^ (getData() == null)) {
            return false;
        }
        return rawEmail.getData() == null || rawEmail.getData().equals(getData());
    }
}
