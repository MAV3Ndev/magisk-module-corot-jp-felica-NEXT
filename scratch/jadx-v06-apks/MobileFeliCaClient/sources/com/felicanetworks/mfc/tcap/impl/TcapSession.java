package com.felicanetworks.mfc.tcap.impl;

import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public abstract class TcapSession {
    protected TcapCommunicationAgent mAgent;
    protected TcapContext mContext;

    protected abstract void reportIllegalStateError(String str) throws HttpException;

    protected abstract void reportPacketFormatError(String str) throws HttpException;

    protected abstract void reportUnexpectedError(String str) throws HttpException;

    public TcapSession(TcapCommunicationAgent tcapCommunicationAgent, int i, TcapState tcapState, TcapRequest tcapRequest, TcapResponse tcapResponse) {
        this.mAgent = tcapCommunicationAgent;
        this.mContext = new TcapContext(this, tcapRequest, tcapResponse);
        this.mContext.setVersion(i);
        this.mContext.setState(tcapState);
    }

    public TcapCommunicationAgent getAgent() {
        return this.mAgent;
    }

    public synchronized void setUrl(String str) {
        this.mContext.setUrl(str);
    }

    public synchronized void setCookie(String str) {
        this.mContext.setCookie(str);
    }

    public synchronized void setDeviceList(InternalDeviceList internalDeviceList) {
        this.mContext.setDeviceList(internalDeviceList);
    }

    public synchronized int start() throws HttpException, TcapException {
        do {
            try {
                try {
                    try {
                        try {
                            this.mContext.getState().doExecution(this.mContext);
                        } finally {
                            this.mContext.setStopFlag(false);
                        }
                    } catch (RuntimeException e) {
                        throw new UnexpectedErrorException((byte) 0, e.toString());
                    }
                } catch (UnexpectedErrorException e2) {
                    reportUnexpectedError(e2.getErrorDescription());
                    throw e2;
                }
            } catch (IllegalStateErrorException e3) {
                reportIllegalStateError(e3.getErrorDescription());
                throw e3;
            } catch (PacketFormatErrorException e4) {
                reportPacketFormatError(e4.getErrorDescription());
                throw e4;
            }
        } while (this.mContext.getState() != null);
        return this.mContext.getReturnCode();
    }

    public void stop() throws RemoteException {
        this.mContext.setStopFlag(true);
        InternalDeviceList deviceList = this.mContext.getDeviceList();
        for (int i = 0; i < deviceList.size(); i++) {
            if (deviceList.get(i) instanceof FeliCaChipWrapper) {
                ((FeliCaChipWrapper) deviceList.get(i)).cancel();
            }
        }
    }

    public boolean isStopped() {
        return this.mContext.getStopFlag();
    }
}
