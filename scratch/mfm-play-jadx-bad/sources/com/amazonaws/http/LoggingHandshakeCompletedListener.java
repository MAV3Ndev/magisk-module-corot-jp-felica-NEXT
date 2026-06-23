package com.amazonaws.http;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;

/* JADX INFO: loaded from: classes.dex */
public class LoggingHandshakeCompletedListener implements HandshakeCompletedListener {
    private static final Log log = LogFactory.getLog((Class<?>) LoggingHandshakeCompletedListener.class);

    @Override // javax.net.ssl.HandshakeCompletedListener
    public void handshakeCompleted(HandshakeCompletedEvent handshakeCompletedEvent) {
        try {
            SSLSession session = handshakeCompletedEvent.getSession();
            String protocol = session.getProtocol();
            String cipherSuite = session.getCipherSuite();
            log.debug("Protocol: " + protocol + ", CipherSuite: " + cipherSuite);
        } catch (Exception e) {
            log.debug("Failed to log connection protocol/cipher suite", e);
        }
    }
}
