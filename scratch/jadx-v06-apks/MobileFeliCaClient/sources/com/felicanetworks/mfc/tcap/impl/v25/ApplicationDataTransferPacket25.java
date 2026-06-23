package com.felicanetworks.mfc.tcap.impl.v25;

import com.felicanetworks.mfc.FelicaConst;
import com.felicanetworks.mfc.tcap.impl.FeliCaChipWrapper;
import com.felicanetworks.mfc.tcap.impl.FeliCaCommandMessage;
import com.felicanetworks.mfc.tcap.impl.InternalDeviceList;
import com.felicanetworks.mfc.tcap.impl.Message;
import com.felicanetworks.mfc.tcap.impl.Packet;
import com.felicanetworks.mfc.tcap.impl.PacketFormatErrorException;
import com.felicanetworks.mfc.tcap.impl.WarningMessage;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
class ApplicationDataTransferPacket25 extends Packet {
    static final int MID_FELICA_COMMAND = 257;
    static final int MID_FELICA_COMMAND_THRURW = 262;
    static final int MID_FELICA_ERROR = 259;
    static final int MID_FELICA_ERROR_THRURW = 264;
    static final int MID_FELICA_EXCOMMAND = 261;
    static final int MID_FELICA_EXCOMMAND_THRURW = 266;
    static final int MID_FELICA_PRECOMMAND = 260;
    static final int MID_FELICA_PRECOMMAND_THRURW = 265;
    static final int MID_FELICA_RESPONSE = 258;
    static final int MID_FELICA_RESPONSE_THRURW = 263;
    static final int MID_WARNING = 1;

    ApplicationDataTransferPacket25(int i) {
        super(i, (byte) 4);
    }

    ApplicationDataTransferPacket25(Packet packet) {
        super(packet);
    }

    @Override // com.felicanetworks.mfc.tcap.impl.Packet
    public void parseMessages(InternalDeviceList internalDeviceList) throws PacketFormatErrorException {
        Message warningMessage;
        int size = this.mOffset + 5;
        this.mMessages = new Vector<>();
        boolean z = false;
        while (size < this.mOffset + this.mSize) {
            Message message = new Message(this.mData, size, this.mSize);
            if (message.getDeviceId() != 0 && internalDeviceList.getById(message.getDeviceId()) == null) {
                byte[] bArr = {(byte) ((message.getDeviceId() & FelicaConst.WILD_CARD_SYSTEM_CODE3) >> 8), (byte) (message.getDeviceId() & 255)};
                this.mMessages = null;
                throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_INVALID_DEVICE_ID, bArr);
            }
            int id = message.getId();
            if (id != 1) {
                if (id != MID_FELICA_COMMAND) {
                    if (id != MID_FELICA_PRECOMMAND_THRURW) {
                        if (id != MID_FELICA_EXCOMMAND_THRURW) {
                            switch (id) {
                                case MID_FELICA_PRECOMMAND /* 260 */:
                                    if (internalDeviceList.getById(message.getDeviceId()) instanceof FeliCaChipWrapper) {
                                        warningMessage = new FeliCaPreCommandMessage(message);
                                        message = warningMessage;
                                    } else {
                                        z = true;
                                    }
                                    break;
                                case MID_FELICA_EXCOMMAND /* 261 */:
                                    if (internalDeviceList.getById(message.getDeviceId()) instanceof FeliCaChipWrapper) {
                                        warningMessage = new FeliCaExCommandMessage(message);
                                        message = warningMessage;
                                    } else {
                                        z = true;
                                    }
                                    break;
                                case MID_FELICA_COMMAND_THRURW /* 262 */:
                                    if (internalDeviceList.getById(message.getDeviceId()) instanceof FeliCaChipWrapper) {
                                        warningMessage = new FeliCaCommandThruRwMessage(message);
                                        message = warningMessage;
                                    } else {
                                        z = true;
                                    }
                                    break;
                                default:
                                    z = true;
                                    break;
                            }
                        } else if (internalDeviceList.getById(message.getDeviceId()) instanceof FeliCaChipWrapper) {
                            warningMessage = new FeliCaExCommandThruRwMessage(message);
                            message = warningMessage;
                        } else {
                            z = true;
                        }
                    } else if (internalDeviceList.getById(message.getDeviceId()) instanceof FeliCaChipWrapper) {
                        warningMessage = new FeliCaPreCommandThruRwMessage(message);
                        message = warningMessage;
                    } else {
                        z = true;
                    }
                } else if (internalDeviceList.getById(message.getDeviceId()) instanceof FeliCaChipWrapper) {
                    warningMessage = new FeliCaCommandMessage(message);
                    message = warningMessage;
                } else {
                    z = true;
                }
            } else if (message.getDeviceId() == 0) {
                warningMessage = new WarningMessage(message);
                message = warningMessage;
            } else {
                z = true;
            }
            if (z) {
                byte[] bArr2 = {message.getExtension(), this.mSubProtocolType, message.getType(), (byte) ((message.getDeviceId() & FelicaConst.WILD_CARD_SYSTEM_CODE3) >> 8), (byte) (message.getDeviceId() & 255)};
                this.mMessages = null;
                throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_UNSUPPORTED_MESSAGE, bArr2);
            }
            if (!message.validateFormat()) {
                this.mMessages = null;
                throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_MESSAGE_FORMAT_ERROR, null);
            }
            this.mMessages.addElement(message);
            size += message.getSize();
        }
        if (!validateMessageOrder()) {
            this.mMessages = null;
            throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_MESSAGE_ORDER_ERROR, null);
        }
        for (int i = 0; i < this.mMessages.size(); i++) {
            if (!this.mMessages.elementAt(i).validateData()) {
                this.mMessages = null;
                throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_DATA_FORMAT_ERROR, null);
            }
        }
    }

    private boolean validateMessageOrder() {
        int i = 0;
        for (int i2 = 0; i2 < this.mMessages.size(); i2++) {
            if (this.mMessages.elementAt(i2).getId() != 1) {
                i++;
            }
        }
        return i > 0;
    }
}
