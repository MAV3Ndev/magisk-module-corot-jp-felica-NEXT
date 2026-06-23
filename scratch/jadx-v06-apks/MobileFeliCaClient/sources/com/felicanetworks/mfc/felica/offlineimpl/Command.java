package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
abstract class Command {
    abstract void doSet(ByteBuffer byteBuffer) throws OfflineException;

    abstract Response get(ByteBuffer byteBuffer) throws OfflineException;

    Command() {
    }

    final int set(ByteBuffer byteBuffer) throws OfflineException {
        int length = byteBuffer.getLength();
        doSet(byteBuffer);
        return byteBuffer.getLength() - length;
    }
}
