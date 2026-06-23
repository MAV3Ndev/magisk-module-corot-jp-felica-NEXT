package com.felicanetworks.mfc.tcap.impl.v21;

import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.tcap.impl.ErrorMessage;
import com.felicanetworks.mfc.tcap.impl.ErrorState;
import com.felicanetworks.mfc.tcap.impl.HttpException;
import com.felicanetworks.mfc.tcap.impl.IllegalStateErrorException;
import com.felicanetworks.mfc.tcap.impl.Message;
import com.felicanetworks.mfc.tcap.impl.Packet;
import com.felicanetworks.mfc.tcap.impl.TcapContext;
import com.felicanetworks.mfc.tcap.impl.TcapException;
import com.felicanetworks.mfc.tcap.impl.TcapRequest;
import com.felicanetworks.mfc.tcap.impl.TcapResponse;
import com.felicanetworks.mfc.tcap.impl.TcapState;
import com.felicanetworks.mfc.tcap.impl.UnexpectedErrorException;

/* JADX INFO: loaded from: classes.dex */
class HandshakeState21 extends TcapState {
    private static HandshakeState21 sInstance;

    static HandshakeState21 getInstance() {
        if (sInstance == null) {
            sInstance = new HandshakeState21();
        }
        return sInstance;
    }

    private HandshakeState21() {
    }

    @Override // com.felicanetworks.mfc.tcap.impl.TcapState
    public void doExecution(TcapContext tcapContext) throws HttpException, TcapException {
        TcapRequest currentRequest = tcapContext.getCurrentRequest();
        currentRequest.parse();
        tcapContext.setCookie(currentRequest.getCookie());
        TcapResponse currentResponse = tcapContext.getCurrentResponse();
        currentResponse.reset();
        currentResponse.setCookie(tcapContext.getCookie());
        if (currentRequest.getPacketNum() == 1) {
            Packet packet = currentRequest.getPacket(0);
            byte subProtocolType = packet.getSubProtocolType();
            if (subProtocolType == 1) {
                handleHandshake(tcapContext, packet);
            } else if (subProtocolType == 3) {
                handleError(tcapContext, packet);
            } else {
                throw new IllegalStateErrorException(IllegalStateErrorException.TYPE_ILLEGAL_STATE);
            }
            if (tcapContext.getStopFlag()) {
                throw new UnexpectedErrorException((byte) 1, "Interrupted.");
            }
            tcapContext.getSession().getAgent().doTcapTransaction(tcapContext.getUrl(), currentResponse, currentRequest);
            return;
        }
        throw new UnexpectedErrorException((byte) 0, null);
    }

    private void handleHandshake(TcapContext tcapContext, Packet packet) throws HttpException, TcapException {
        packet.parseMessages(tcapContext.getDeviceList());
        if (tcapContext.getStopFlag()) {
            throw new UnexpectedErrorException((byte) 1, "Interrupted.");
        }
        HandshakePacket21 handshakePacket21 = new HandshakePacket21();
        handshakePacket21.addMessage(new Message((byte) 0, 0, (byte) 0));
        tcapContext.getCurrentResponse().addPacket(handshakePacket21);
        tcapContext.setState(NeutralState21.getInstance());
    }

    private void handleError(TcapContext tcapContext, Packet packet) throws HttpException, TcapException {
        packet.parseMessages(tcapContext.getDeviceList());
        String message = null;
        for (int i = 0; i < packet.getMessageNum(); i++) {
            Message message2 = packet.getMessage(i);
            switch (message2.getId()) {
                case 33:
                case 34:
                case FelicaException.TYPE_GET_PRIVACY_NODE_INFORMATION_FAILED /* 35 */:
                    message = ((ErrorMessage) message2).getMessage();
                    break;
            }
        }
        tcapContext.setState(ErrorState.getInstance());
        throw new TcapException((byte) 0, message);
    }
}
