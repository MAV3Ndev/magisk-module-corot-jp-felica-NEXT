package com.felicanetworks.mfc.tcap.impl.v21;

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
class NeutralState21 extends TcapState {
    private static NeutralState21 sInstance;

    static NeutralState21 getInstance() {
        if (sInstance == null) {
            sInstance = new NeutralState21();
        }
        return sInstance;
    }

    private NeutralState21() {
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
        for (int i = 0; i < currentRequest.getPacketNum(); i++) {
            Packet packet = currentRequest.getPacket(i);
            byte subProtocolType = packet.getSubProtocolType();
            if (subProtocolType != 2) {
                if (subProtocolType != 3) {
                    if (subProtocolType != 4) {
                        if (subProtocolType != 5) {
                            if (subProtocolType != 6) {
                                throw new IllegalStateErrorException(IllegalStateErrorException.TYPE_ILLEGAL_STATE);
                            }
                            if (!z) {
                                zHandleApplicationDataTransfer = handleOperateEntity(tcapContext, packet);
                                z = !zHandleApplicationDataTransfer;
                            }
                        } else if (!z) {
                            zHandleApplicationDataTransfer = handleUpdateEntity(tcapContext, packet);
                            z = !zHandleApplicationDataTransfer;
                        }
                    } else if (!z) {
                        zHandleApplicationDataTransfer = handleApplicationDataTransfer(tcapContext, packet);
                        z = !zHandleApplicationDataTransfer;
                    }
                } else if (i == 0) {
                    handleError(tcapContext, packet);
                } else {
                    throw new UnexpectedErrorException((byte) 0, null);
                }
            } else if (i == 0) {
                handleFarewell(tcapContext, packet);
            } else {
                throw new UnexpectedErrorException((byte) 0, null);
            }
        }
        if (tcapContext.getStopFlag()) {
            throw new UnexpectedErrorException((byte) 1, "Interrupted.");
        }
        tcapContext.getSession().getAgent().doTcapTransaction(tcapContext.getUrl(), currentResponse, currentRequest);
    }

    private boolean handleApplicationDataTransfer(TcapContext tcapContext, Packet packet) throws HttpException, TcapException {
        ApplicationDataTransferPacket21 applicationDataTransferPacket21 = new ApplicationDataTransferPacket21();
        packet.parseMessages(tcapContext.getDeviceList());
        int i = 0;
        while (true) {
            boolean z = true;
            if (i >= packet.getMessageNum()) {
                break;
            }
            Message message = packet.getMessage(i);
            if (tcapContext.getStopFlag()) {
                throw new UnexpectedErrorException((byte) 1, "Interrupted.");
            }
            if (message.getId() == 257) {
                try {
                    try {
                        byte[] bArrExecute = ((FeliCaChipWrapper) tcapContext.getDeviceList().getById(message.getDeviceId())).execute(((FeliCaCommandMessage) message).getCommand());
                        if (bArrExecute == null) {
                            throw new FeliCaChipException(0);
                        }
                        applicationDataTransferPacket21.addMessage(new FeliCaResponseMessage(message.getDeviceId(), bArrExecute));
                    } catch (RemoteException unused) {
                        throw new FeliCaChipException(0);
                    }
                } catch (FeliCaChipException unused2) {
                    applicationDataTransferPacket21.addMessage(new Message((byte) 1, message.getDeviceId(), (byte) 3));
                    z = false;
                    applicationDataTransferPacket21.addMessage(new Message((byte) 0, 0, (byte) 0));
                    tcapContext.getCurrentResponse().addPacket(applicationDataTransferPacket21);
                    return z;
                }
            }
            i++;
        }
    }

    private boolean handleUpdateEntity(TcapContext tcapContext, Packet packet) throws HttpException, TcapException {
        UpdateEntityPacket21 updateEntityPacket21 = new UpdateEntityPacket21();
        packet.parseMessages(tcapContext.getDeviceList());
        for (int i = 0; i < packet.getMessageNum(); i++) {
            Message message = packet.getMessage(i);
            if (tcapContext.getStopFlag()) {
                throw new UnexpectedErrorException((byte) 1, "Interrupted.");
            }
            int id = message.getId();
            if (id == 129) {
                tcapContext.getSession().getAgent().setTimeout(((SetNetworkTimeoutMessage) message).getTimeout());
            } else if (id == 257) {
                updateEntityPacket21.addMessage(new SelectedFelicaDeviceMessage(message.getDeviceId(), (byte) 1));
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
        updateEntityPacket21.addMessage(new Message((byte) 0, 0, (byte) 0));
        tcapContext.getCurrentResponse().addPacket(updateEntityPacket21);
        return true;
    }

    private boolean handleOperateEntity(TcapContext tcapContext, Packet packet) throws HttpException, TcapException {
        OperateEntityPacket21 operateEntityPacket21 = new OperateEntityPacket21();
        packet.parseMessages(tcapContext.getDeviceList());
        int i = 0;
        while (true) {
            boolean z = true;
            if (i >= packet.getMessageNum()) {
                break;
            }
            Message message = packet.getMessage(i);
            if (tcapContext.getStopFlag()) {
                throw new UnexpectedErrorException((byte) 1, "Interrupted.");
            }
            int id = message.getId();
            if (id == 37) {
                OperateDeviceMessage operateDeviceMessage = (OperateDeviceMessage) message;
                try {
                    operateEntityPacket21.addMessage(new DeviceResponseMessage(message.getDeviceId(), tcapContext.getDeviceList().getById(message.getDeviceId()).operate(operateDeviceMessage.getParamName(), operateDeviceMessage.getParam())));
                } catch (Exception e) {
                    throw new UnexpectedErrorException((byte) 2, e.getMessage());
                }
            } else if (id == 129) {
                continue;
            } else if (id == 257) {
                operateEntityPacket21.addMessage(new OpenRwStatusMessage(message.getDeviceId(), (byte) 1));
            } else if (id != 261) {
                continue;
            } else {
                try {
                    ((FeliCaChipWrapper) tcapContext.getDeviceList().getById(message.getDeviceId())).close();
                    operateEntityPacket21.addMessage(new CloseRwStatusMessage(message.getDeviceId(), (byte) 1));
                } catch (Exception unused) {
                    operateEntityPacket21.addMessage(new CloseRwStatusMessage(message.getDeviceId(), (byte) 0));
                    z = false;
                    operateEntityPacket21.addMessage(new Message((byte) 0, 0, (byte) 0));
                    tcapContext.getCurrentResponse().addPacket(operateEntityPacket21);
                    return z;
                }
            }
            i++;
        }
    }

    private void handleFarewell(TcapContext tcapContext, Packet packet) throws HttpException, TcapException {
        FarewellPacket21 farewellPacket21 = new FarewellPacket21();
        packet.parseMessages(tcapContext.getDeviceList());
        for (int i = 0; i < packet.getMessageNum(); i++) {
            Message message = packet.getMessage(i);
            if (tcapContext.getStopFlag()) {
                throw new UnexpectedErrorException((byte) 1, "Interrupted.");
            }
            int id = message.getId();
            if (id == 0) {
                throw new IllegalStateErrorException(IllegalStateErrorException.TYPE_ILLEGAL_STATE);
            }
            if (id == 37) {
                tcapContext.setReturnCode(((ReturnCodeMessage) message).getReturnCode());
            }
        }
        farewellPacket21.addMessage(new Message((byte) 0, 0, (byte) 33));
        farewellPacket21.addMessage(new Message((byte) 0, 0, (byte) 34));
        farewellPacket21.addMessage(new Message((byte) 0, 0, (byte) 0));
        tcapContext.getCurrentResponse().addPacket(farewellPacket21);
        tcapContext.setState(FarewellState21.getInstance());
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
