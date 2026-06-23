package com.felicanetworks.mfc.tcap.impl.v25;

import com.felicanetworks.mfc.tcap.impl.ErrorMessage;
import com.felicanetworks.mfc.tcap.impl.HttpException;
import com.felicanetworks.mfc.tcap.impl.TcapCommunicationAgent;
import com.felicanetworks.mfc.tcap.impl.TcapResponse;
import com.felicanetworks.mfc.tcap.impl.TcapSession;

/* JADX INFO: loaded from: classes.dex */
public class TcapSession25 extends TcapSession {
    public static final int TCAP_VERSION_25 = 517;
    private FeatureList mFeatureList;

    public TcapSession25(TcapCommunicationAgent tcapCommunicationAgent) {
        super(tcapCommunicationAgent, TCAP_VERSION_25, InitialState25.getInstance(), new TcapRequest25(), new TcapResponse());
        this.mFeatureList = new FeatureList();
        this.mFeatureList.add(new Feature(this.mContext.getVersion()));
    }

    public FeatureList getFeatureList() {
        return this.mFeatureList;
    }

    @Override // com.felicanetworks.mfc.tcap.impl.TcapSession
    protected void reportPacketFormatError(String str) throws HttpException {
        TcapResponse currentResponse = this.mContext.getCurrentResponse();
        currentResponse.reset();
        currentResponse.setCookie(this.mContext.getCookie());
        ErrorPacket25 errorPacket25 = new ErrorPacket25(this.mContext.getVersion());
        errorPacket25.addMessage(new ErrorMessage((byte) 33, str));
        currentResponse.addPacket(errorPacket25);
        this.mAgent.doTcapTransaction(this.mContext.getUrl(), currentResponse, this.mContext.getCurrentRequest());
    }

    @Override // com.felicanetworks.mfc.tcap.impl.TcapSession
    protected void reportIllegalStateError(String str) throws HttpException {
        TcapResponse currentResponse = this.mContext.getCurrentResponse();
        currentResponse.reset();
        currentResponse.setCookie(this.mContext.getCookie());
        ErrorPacket25 errorPacket25 = new ErrorPacket25(this.mContext.getVersion());
        errorPacket25.addMessage(new ErrorMessage((byte) 34, str));
        currentResponse.addPacket(errorPacket25);
        this.mAgent.doTcapTransaction(this.mContext.getUrl(), currentResponse, this.mContext.getCurrentRequest());
    }

    @Override // com.felicanetworks.mfc.tcap.impl.TcapSession
    protected void reportUnexpectedError(String str) throws HttpException {
        TcapResponse currentResponse = this.mContext.getCurrentResponse();
        currentResponse.reset();
        currentResponse.setCookie(this.mContext.getCookie());
        ErrorPacket25 errorPacket25 = new ErrorPacket25(this.mContext.getVersion());
        errorPacket25.addMessage(new ErrorMessage((byte) 35, str));
        currentResponse.addPacket(errorPacket25);
        this.mAgent.doTcapTransaction(this.mContext.getUrl(), currentResponse, this.mContext.getCurrentRequest());
    }
}
