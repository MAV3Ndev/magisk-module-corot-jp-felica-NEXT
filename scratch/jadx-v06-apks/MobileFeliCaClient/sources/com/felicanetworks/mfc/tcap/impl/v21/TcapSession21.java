package com.felicanetworks.mfc.tcap.impl.v21;

import com.felicanetworks.mfc.tcap.impl.ErrorMessage;
import com.felicanetworks.mfc.tcap.impl.HttpException;
import com.felicanetworks.mfc.tcap.impl.Message;
import com.felicanetworks.mfc.tcap.impl.TcapCommunicationAgent;
import com.felicanetworks.mfc.tcap.impl.TcapResponse;
import com.felicanetworks.mfc.tcap.impl.TcapSession;

/* JADX INFO: loaded from: classes.dex */
public class TcapSession21 extends TcapSession {
    public static final int TCAP_VERSION_21 = 513;

    public TcapSession21(TcapCommunicationAgent tcapCommunicationAgent) {
        super(tcapCommunicationAgent, TCAP_VERSION_21, InitialState21.getInstance(), new TcapRequest21(), new TcapResponse());
    }

    @Override // com.felicanetworks.mfc.tcap.impl.TcapSession
    protected void reportPacketFormatError(String str) throws HttpException {
        TcapResponse currentResponse = this.mContext.getCurrentResponse();
        currentResponse.reset();
        currentResponse.setCookie(this.mContext.getCookie());
        ErrorPacket21 errorPacket21 = new ErrorPacket21();
        errorPacket21.addMessage(new ErrorMessage((byte) 33, str));
        errorPacket21.addMessage(new Message((byte) 0, 0, (byte) 0));
        currentResponse.addPacket(errorPacket21);
        this.mAgent.doTcapTransaction(this.mContext.getUrl(), currentResponse, this.mContext.getCurrentRequest());
    }

    @Override // com.felicanetworks.mfc.tcap.impl.TcapSession
    protected void reportIllegalStateError(String str) throws HttpException {
        TcapResponse currentResponse = this.mContext.getCurrentResponse();
        currentResponse.reset();
        currentResponse.setCookie(this.mContext.getCookie());
        ErrorPacket21 errorPacket21 = new ErrorPacket21();
        errorPacket21.addMessage(new ErrorMessage((byte) 34, str));
        errorPacket21.addMessage(new Message((byte) 0, 0, (byte) 0));
        currentResponse.addPacket(errorPacket21);
        this.mAgent.doTcapTransaction(this.mContext.getUrl(), currentResponse, this.mContext.getCurrentRequest());
    }

    @Override // com.felicanetworks.mfc.tcap.impl.TcapSession
    protected void reportUnexpectedError(String str) throws HttpException {
        TcapResponse currentResponse = this.mContext.getCurrentResponse();
        currentResponse.reset();
        currentResponse.setCookie(this.mContext.getCookie());
        ErrorPacket21 errorPacket21 = new ErrorPacket21();
        errorPacket21.addMessage(new ErrorMessage((byte) 35, str));
        errorPacket21.addMessage(new Message((byte) 0, 0, (byte) 0));
        currentResponse.addPacket(errorPacket21);
        this.mAgent.doTcapTransaction(this.mContext.getUrl(), currentResponse, this.mContext.getCurrentRequest());
    }
}
