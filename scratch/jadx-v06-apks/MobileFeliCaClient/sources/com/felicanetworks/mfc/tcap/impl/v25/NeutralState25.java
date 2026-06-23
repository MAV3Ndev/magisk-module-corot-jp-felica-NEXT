package com.felicanetworks.mfc.tcap.impl.v25;

import android.os.RemoteException;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.felica.FeliCaChipException;
import com.felicanetworks.mfc.tcap.impl.CloseRwStatusMessage;
import com.felicanetworks.mfc.tcap.impl.DeviceResponseMessage;
import com.felicanetworks.mfc.tcap.impl.ErrorMessage;
import com.felicanetworks.mfc.tcap.impl.ErrorState;
import com.felicanetworks.mfc.tcap.impl.FeliCaChipWrapper;
import com.felicanetworks.mfc.tcap.impl.FeliCaCommandMessage;
import com.felicanetworks.mfc.tcap.impl.FeliCaResponseMessage;
import com.felicanetworks.mfc.tcap.impl.HttpException;
import com.felicanetworks.mfc.tcap.impl.IllegalStateErrorException;
import com.felicanetworks.mfc.tcap.impl.Message;
import com.felicanetworks.mfc.tcap.impl.OpenRwStatusMessage;
import com.felicanetworks.mfc.tcap.impl.OperateDeviceMessage;
import com.felicanetworks.mfc.tcap.impl.Packet;
import com.felicanetworks.mfc.tcap.impl.ReturnCodeMessage;
import com.felicanetworks.mfc.tcap.impl.SetFelicaTimeoutMessage;
import com.felicanetworks.mfc.tcap.impl.SetNetworkTimeoutMessage;
import com.felicanetworks.mfc.tcap.impl.SetRetryCountMessage;
import com.felicanetworks.mfc.tcap.impl.TcapContext;
import com.felicanetworks.mfc.tcap.impl.TcapException;
import com.felicanetworks.mfc.tcap.impl.TcapRequest;
import com.felicanetworks.mfc.tcap.impl.TcapResponse;
import com.felicanetworks.mfc.tcap.impl.TcapState;
import com.felicanetworks.mfc.tcap.impl.UnexpectedErrorException;

/* JADX INFO: loaded from: classes.dex */
class NeutralState25 extends TcapState {
    private static NeutralState25 sInstance;

    static NeutralState25 getInstance() {
        if (sInstance == null) {
            sInstance = new NeutralState25();
        }
        return sInstance;
    }

    private NeutralState25() {
    }

    @Override // com.felicanetworks.mfc.tcap.impl.TcapState
    public void doExecution(TcapContext tcapContext) throws HttpException, TcapException {
        boolean zHandleApplicationDataTransfer;
        TcapRequest currentRequest = tcapContext.getCurrentRequest();
        currentRequest.parse();
        tcapContext.setCookie(currentRequest.getCookie());
        TcapResponse currentResponse = tcapContext.getCurrentResponse();
        currentResponse.reset();
        currentResponse.setCookie(tcapContext.getCookie());
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < currentRequest.getPacketNum(); i++) {
            Packet packet = currentRequest.getPacket(i);
            byte subProtocolType = packet.getSubProtocolType();
            if (subProtocolType != 2) {
                if (subProtocolType != 3) {
                    if (subProtocolType == 4) {
                        if (z) {
                            throw new IllegalStateErrorException(IllegalStateErrorException.TYPE_ILLEGAL_STATE);
                        }
                        if (!z2) {
                            zHandleApplicationDataTransfer = handleApplicationDataTransfer(tcapContext, packet);
                            z2 = !zHandleApplicationDataTransfer;
                        }
                    } else if (subProtocolType != 5) {
                        if (subProtocolType != 6) {
                            throw new IllegalStateErrorException(IllegalStateErrorException.TYPE_ILLEGAL_STATE);
                        }
                        if (z) {
                            throw new IllegalStateErrorException(IllegalStateErrorException.TYPE_ILLEGAL_STATE);
                        }
                        if (!z2) {
                            zHandleApplicationDataTransfer = handleOperateEntity(tcapContext, packet);
                            z2 = !zHandleApplicationDataTransfer;
                        }
                    } else {
                        if (z) {
                            throw new IllegalStateErrorException(IllegalStateErrorException.TYPE_ILLEGAL_STATE);
                        }
                        if (!z2) {
                            zHandleApplicationDataTransfer = handleUpdateEntity(tcapContext, packet);
                            z2 = !zHandleApplicationDataTransfer;
                        }
                    }
                } else if (i == 0) {
                    handleError(tcapContext, packet);
                } else {
                    throw new UnexpectedErrorException((byte) 0, null);
                }
            } else if (!z) {
                handleFarewell(tcapContext, packet);
                z = true;
            } else {
                throw new UnexpectedErrorException((byte) 0, null);
            }
        }
        if (z) {
            return;
        }
        if (tcapContext.getStopFlag()) {
            throw new UnexpectedErrorException((byte) 1, "Interrupted.");
        }
        tcapContext.getSession().getAgent().doTcapTransaction(tcapContext.getUrl(), currentResponse, currentRequest);
    }

    private void handleFarewell(TcapContext tcapContext, Packet packet) throws HttpException, TcapException {
        packet.parseMessages(tcapContext.getDeviceList());
        for (int i = 0; i < packet.getMessageNum(); i++) {
            Message message = packet.getMessage(i);
            if (message.getId() == 37) {
                tcapContext.setReturnCode(((ReturnCodeMessage) message).getReturnCode());
            }
        }
        tcapContext.setState(null);
    }

    private boolean handleError(TcapContext tcapContext, Packet packet) throws HttpException, TcapException {
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

    boolean handleApplicationDataTransfer(TcapContext tcapContext, Packet packet) throws HttpException, TcapException {
        ApplicationDataTransferPacket25 applicationDataTransferPacket25 = new ApplicationDataTransferPacket25(tcapContext.getVersion());
        packet.parseMessages(tcapContext.getDeviceList());
        boolean z = false;
        int i = 0;
        while (true) {
            if (i < packet.getMessageNum()) {
                Message message = packet.getMessage(i);
                FeliCaParamPool feliCaParamPool = ((TcapRequest25) tcapContext.getCurrentRequest()).getFeliCaParamPool(message.getDeviceId());
                if (tcapContext.getStopFlag()) {
                    throw new UnexpectedErrorException((byte) 1, "Interrupted.");
                }
                int id = message.getId();
                if (id == 257) {
                    try {
                        try {
                            byte[] bArrExecute = ((FeliCaChipWrapper) tcapContext.getDeviceList().getById(message.getDeviceId())).execute(((FeliCaCommandMessage) message).getCommand());
                            if (bArrExecute == null) {
                                throw new FeliCaChipException(0);
                            }
                            applicationDataTransferPacket25.addMessage(new FeliCaResponseMessage(message.getDeviceId(), bArrExecute));
                            i++;
                        } catch (RemoteException unused) {
                            throw new FeliCaChipException(0);
                        }
                    } catch (FeliCaChipException unused2) {
                        applicationDataTransferPacket25.addMessage(new Message((byte) 1, message.getDeviceId(), (byte) 3));
                        tcapContext.getCurrentResponse().addPacket(applicationDataTransferPacket25);
                        return z;
                    }
                } else if (id == 265) {
                    try {
                        try {
                            byte[] bArrExecuteThru = ((FeliCaChipWrapper) tcapContext.getDeviceList().getById(message.getDeviceId())).executeThru(((FeliCaPreCommandThruRwMessage) message).getCommand());
                            if (bArrExecuteThru == null) {
                                throw new FeliCaChipException(0);
                            }
                            if (bArrExecuteThru.length < 1) {
                                applicationDataTransferPacket25.addMessage(new Message((byte) 1, message.getDeviceId(), (byte) 8));
                            } else {
                                try {
                                    feliCaParamPool.saveParam(((FeliCaPreCommandThruRwMessage) message).getParamId(), bArrExecuteThru, ((FeliCaPreCommandThruRwMessage) message).getParamPos(), ((FeliCaPreCommandThruRwMessage) message).getParamLen());
                                    applicationDataTransferPacket25.addMessage(new FeliCaResponseThruRwMessage(message.getDeviceId(), bArrExecuteThru));
                                    i++;
                                } catch (Exception unused3) {
                                    throw new UnexpectedErrorException((byte) 4, null);
                                }
                            }
                        } catch (RemoteException unused4) {
                            throw new FeliCaChipException(0);
                        }
                    } catch (FeliCaChipException unused5) {
                        applicationDataTransferPacket25.addMessage(new Message((byte) 1, message.getDeviceId(), (byte) 8));
                    }
                } else if (id != 266) {
                    switch (id) {
                        case 260:
                            try {
                                try {
                                    byte[] bArrExecute2 = ((FeliCaChipWrapper) tcapContext.getDeviceList().getById(message.getDeviceId())).execute(((FeliCaPreCommandMessage) message).getCommand());
                                    if (bArrExecute2 == null) {
                                        throw new FeliCaChipException(0);
                                    }
                                    try {
                                        feliCaParamPool.saveParam(((FeliCaPreCommandMessage) message).getParamId(), bArrExecute2, ((FeliCaPreCommandMessage) message).getParamPos(), ((FeliCaPreCommandMessage) message).getParamLen());
                                        applicationDataTransferPacket25.addMessage(new FeliCaResponseMessage(message.getDeviceId(), bArrExecute2));
                                        continue;
                                        i++;
                                    } catch (Exception unused6) {
                                        throw new UnexpectedErrorException((byte) 4, null);
                                    }
                                } catch (RemoteException unused7) {
                                    throw new FeliCaChipException(0);
                                }
                            } catch (FeliCaChipException unused8) {
                                applicationDataTransferPacket25.addMessage(new Message((byte) 1, message.getDeviceId(), (byte) 3));
                                tcapContext.getCurrentResponse().addPacket(applicationDataTransferPacket25);
                                return z;
                            }
                            break;
                        case 261:
                            try {
                                try {
                                    try {
                                        byte[] bArrExecute3 = ((FeliCaChipWrapper) tcapContext.getDeviceList().getById(message.getDeviceId())).execute(((FeliCaExCommandMessage) message).getCommand(feliCaParamPool.callParam(((FeliCaExCommandMessage) message).getParamId())));
                                        if (bArrExecute3 == null) {
                                            throw new FeliCaChipException(0);
                                        }
                                        applicationDataTransferPacket25.addMessage(new FeliCaResponseMessage(message.getDeviceId(), bArrExecute3));
                                        continue;
                                        i++;
                                    } catch (RemoteException unused9) {
                                        throw new FeliCaChipException(0);
                                    }
                                } catch (Exception unused10) {
                                    throw new UnexpectedErrorException((byte) 4, null);
                                }
                            } catch (FeliCaChipException unused11) {
                                applicationDataTransferPacket25.addMessage(new Message((byte) 1, message.getDeviceId(), (byte) 3));
                                tcapContext.getCurrentResponse().addPacket(applicationDataTransferPacket25);
                                return z;
                            }
                            break;
                        case 262:
                            try {
                                try {
                                    byte[] bArrExecuteThru2 = ((FeliCaChipWrapper) tcapContext.getDeviceList().getById(message.getDeviceId())).executeThru(((FeliCaCommandThruRwMessage) message).getCommand());
                                    if (bArrExecuteThru2 == null) {
                                        throw new FeliCaChipException(0);
                                    }
                                    if (bArrExecuteThru2.length < 1) {
                                        applicationDataTransferPacket25.addMessage(new Message((byte) 1, message.getDeviceId(), (byte) 8));
                                    } else {
                                        applicationDataTransferPacket25.addMessage(new FeliCaResponseThruRwMessage(message.getDeviceId(), bArrExecuteThru2));
                                        i++;
                                    }
                                } catch (RemoteException unused12) {
                                    throw new FeliCaChipException(0);
                                }
                            } catch (FeliCaChipException unused13) {
                                applicationDataTransferPacket25.addMessage(new Message((byte) 1, message.getDeviceId(), (byte) 8));
                            }
                            break;
                        default:
                            continue;
                            i++;
                            break;
                    }
                } else {
                    try {
                        try {
                            try {
                                byte[] bArrExecuteThru3 = ((FeliCaChipWrapper) tcapContext.getDeviceList().getById(message.getDeviceId())).executeThru(((FeliCaExCommandThruRwMessage) message).getCommand(feliCaParamPool.callParam(((FeliCaExCommandThruRwMessage) message).getParamId())));
                                if (bArrExecuteThru3 == null) {
                                    throw new FeliCaChipException(0);
                                }
                                if (bArrExecuteThru3.length < 1) {
                                    applicationDataTransferPacket25.addMessage(new Message((byte) 1, message.getDeviceId(), (byte) 8));
                                } else {
                                    applicationDataTransferPacket25.addMessage(new FeliCaResponseThruRwMessage(message.getDeviceId(), bArrExecuteThru3));
                                    i++;
                                }
                            } catch (RemoteException unused14) {
                                throw new FeliCaChipException(0);
                            }
                        } catch (Exception unused15) {
                            throw new UnexpectedErrorException((byte) 4, null);
                        }
                    } catch (FeliCaChipException unused16) {
                        applicationDataTransferPacket25.addMessage(new Message((byte) 1, message.getDeviceId(), (byte) 8));
                    }
                }
            } else {
                z = true;
            }
        }
    }

    boolean handleUpdateEntity(TcapContext tcapContext, Packet packet) throws HttpException, TcapException {
        UpdateEntityPacket25 updateEntityPacket25 = new UpdateEntityPacket25(tcapContext.getVersion());
        packet.parseMessages(tcapContext.getDeviceList());
        for (int i = 0; i < packet.getMessageNum(); i++) {
            Message message = packet.getMessage(i);
            if (tcapContext.getStopFlag()) {
                throw new UnexpectedErrorException((byte) 1, "Interrupted.");
            }
            int id = message.getId();
            if (id == 48) {
                updateEntityPacket25.addMessage(new RequestIdMessage(((RequestIdMessage) message).getRequestId()));
            } else if (id == 129) {
                tcapContext.getSession().getAgent().setTimeout(((SetNetworkTimeoutMessage) message).getTimeout());
            } else if (id == 385) {
                try {
                    ((FeliCaChipWrapper) tcapContext.getDeviceList().getById(message.getDeviceId())).setTimeout(((SetFelicaTimeoutMessage) message).getTimeout());
                } catch (Exception unused) {
                    throw new UnexpectedErrorException((byte) 0, null);
                }
            } else if (id != 386) {
                continue;
            } else {
                try {
                    ((FeliCaChipWrapper) tcapContext.getDeviceList().getById(message.getDeviceId())).setRetryCount(((SetRetryCountMessage) message).getRetryCount());
                } catch (Exception unused2) {
                    throw new UnexpectedErrorException((byte) 0, null);
                }
            }
        }
        tcapContext.getCurrentResponse().addPacket(updateEntityPacket25);
        return true;
    }

    boolean handleOperateEntity(TcapContext tcapContext, Packet packet) throws HttpException, TcapException {
        OperateEntityPacket25 operateEntityPacket25 = new OperateEntityPacket25(tcapContext.getVersion());
        packet.parseMessages(tcapContext.getDeviceList());
        boolean z = false;
        for (int i = 0; i < packet.getMessageNum(); i++) {
            Message message = packet.getMessage(i);
            if (tcapContext.getStopFlag()) {
                throw new UnexpectedErrorException((byte) 1, "Interrupted.");
            }
            int id = message.getId();
            if (id == 37) {
                OperateDeviceMessage operateDeviceMessage = (OperateDeviceMessage) message;
                try {
                    operateEntityPacket25.addMessage(new DeviceResponseMessage(message.getDeviceId(), tcapContext.getDeviceList().getById(message.getDeviceId()).operate(operateDeviceMessage.getParamName(), operateDeviceMessage.getParam())));
                } catch (Exception e) {
                    throw new UnexpectedErrorException((byte) 2, e.getMessage());
                }
            } else if (id == 257) {
                operateEntityPacket25.addMessage(new OpenRwStatusMessage(message.getDeviceId(), (byte) 1));
            } else if (id != 261) {
                continue;
            } else {
                try {
                    ((FeliCaChipWrapper) tcapContext.getDeviceList().getById(message.getDeviceId())).close();
                    operateEntityPacket25.addMessage(new CloseRwStatusMessage(message.getDeviceId(), (byte) 1));
                } catch (Exception unused) {
                    operateEntityPacket25.addMessage(new CloseRwStatusMessage(message.getDeviceId(), (byte) 0));
                }
            }
        }
        z = true;
        tcapContext.getCurrentResponse().addPacket(operateEntityPacket25);
        return z;
    }
}
