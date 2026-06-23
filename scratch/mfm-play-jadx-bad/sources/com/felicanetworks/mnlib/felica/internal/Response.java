package com.felicanetworks.mnlib.felica.internal;

/* JADX INFO: loaded from: classes3.dex */
abstract class Response {
    abstract Response get(Command command, ByteBuffer byteBuffer) throws FelicaInternalException;

    Response() {
    }
}
