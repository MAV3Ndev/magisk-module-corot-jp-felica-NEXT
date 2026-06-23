package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
abstract class Response {
    abstract Response get(Command command, ByteBuffer byteBuffer) throws OfflineException;

    Response() {
    }
}
