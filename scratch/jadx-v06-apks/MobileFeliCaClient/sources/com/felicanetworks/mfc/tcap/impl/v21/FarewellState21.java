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
class FarewellState21 extends TcapState {
    private static FarewellState21 sInstance;

    static FarewellState21 getInstance() {
        if (sInstance == null) {
            sInstance = new FarewellState21();
        }
        return sInstance;
    }

    private FarewellState21() {
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
            if (subProtocolType == 2) {
                handleFarewell(tcapContext, packet);
                return;
            } else {
                if (subProtocolType == 3) {
                    handleError(tcapContext, packet);
                    return;
                }
                throw new IllegalStateErrorException(IllegalStateErrorException.TYPE_ILLEGAL_STATE);
            }
        }
        throw new UnexpectedErrorException((byte) 0, null);
    }

    private void handleFarewell(TcapContext tcapContext, Packet packet) throws HttpException, TcapException {
        packet.parseMessages(tcapContext.getDeviceList());
        for (int i = 0; i < packet.getMessageNum(); i++) {
            int id = packet.getMessage(i).getId();
            if (id != 0) {
                switch (id) {
                    case FelicaException.TYPE_GET_PRIVACY_NODE_INFORMATION_FAILED /* 35 */:
                    case FelicaException.TYPE_SET_PRIVACY_FAILED /* 36 */:
                    case FelicaException.TYPE_NOT_CLOSED /* 37 */:
                        throw new IllegalStateErrorException(IllegalStateErrorException.TYPE_ILLEGAL_STATE);
                }
            }
        }
        tcapContext.setState(null);
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
