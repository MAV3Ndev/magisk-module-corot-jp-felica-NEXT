package com.felicanetworks.mfc.tcap.impl.v21;

import com.felicanetworks.mfc.tcap.impl.DevicesMessage;
import com.felicanetworks.mfc.tcap.impl.HttpException;
import com.felicanetworks.mfc.tcap.impl.Message;
import com.felicanetworks.mfc.tcap.impl.TcapContext;
import com.felicanetworks.mfc.tcap.impl.TcapException;
import com.felicanetworks.mfc.tcap.impl.TcapRequest;
import com.felicanetworks.mfc.tcap.impl.TcapResponse;
import com.felicanetworks.mfc.tcap.impl.TcapState;
import com.felicanetworks.mfc.tcap.impl.UnexpectedErrorException;

/* JADX INFO: loaded from: classes.dex */
class InitialState21 extends TcapState {
    private static InitialState21 sInstance;

    static InitialState21 getInstance() {
        if (sInstance == null) {
            sInstance = new InitialState21();
        }
        return sInstance;
    }

    private InitialState21() {
    }

    @Override // com.felicanetworks.mfc.tcap.impl.TcapState
    public void doExecution(TcapContext tcapContext) throws HttpException, TcapException {
        if (tcapContext.getStopFlag()) {
            throw new UnexpectedErrorException((byte) 1, "Interrupted.");
        }
        TcapRequest currentRequest = tcapContext.getCurrentRequest();
        currentRequest.reset();
        TcapResponse currentResponse = tcapContext.getCurrentResponse();
        currentResponse.reset();
        currentResponse.setCookie(tcapContext.getCookie());
        HandshakePacket21 handshakePacket21 = new HandshakePacket21();
        handshakePacket21.addMessage(new Message((byte) 0, 0, (byte) 33));
        handshakePacket21.addMessage(new DevicesMessage(tcapContext.getDeviceList()));
        handshakePacket21.addMessage(new Message((byte) 0, 0, (byte) 34));
        currentResponse.addPacket(handshakePacket21);
        tcapContext.getSession().getAgent().doTcapTransaction(tcapContext.getUrl(), currentResponse, currentRequest);
        tcapContext.setState(HandshakeState21.getInstance());
    }
}
