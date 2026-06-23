package com.felicanetworks.mfc.tcap.impl.v25;

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
class InitialState25 extends TcapState {
    private static InitialState25 sInstance;

    static InitialState25 getInstance() {
        if (sInstance == null) {
            sInstance = new InitialState25();
        }
        return sInstance;
    }

    private InitialState25() {
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
        HandshakePacket25 handshakePacket25 = new HandshakePacket25(TcapSession25.TCAP_VERSION_25);
        handshakePacket25.addMessage(new Message((byte) 0, 0, (byte) 33));
        handshakePacket25.addMessage(new DevicesMessage(tcapContext.getDeviceList()));
        FeatureList featureList = ((TcapSession25) tcapContext.getSession()).getFeatureList();
        for (int i = 0; i < featureList.size(); i++) {
            handshakePacket25.addMessage(new FeaturesMessage(featureList.get(i)));
        }
        handshakePacket25.addMessage(new Message((byte) 0, 0, (byte) 34));
        currentResponse.addPacket(handshakePacket25);
        tcapContext.getSession().getAgent().doTcapTransaction(tcapContext.getUrl(), currentResponse, currentRequest);
        tcapContext.setState(HandshakeState25.getInstance());
    }
}
