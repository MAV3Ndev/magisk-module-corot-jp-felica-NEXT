package com.felicanetworks.mnlib.felica.internal;

/* JADX INFO: loaded from: classes3.dex */
abstract class Command {
    abstract void doSet(ByteBuffer byteBuffer) throws FelicaInternalException;

    abstract Response get(ByteBuffer byteBuffer) throws FelicaInternalException;

    Command() {
    }

    final int set(ByteBuffer byteBuffer) throws FelicaInternalException {
        int length = byteBuffer.getLength();
        doSet(byteBuffer);
        return byteBuffer.getLength() - length;
    }
}
