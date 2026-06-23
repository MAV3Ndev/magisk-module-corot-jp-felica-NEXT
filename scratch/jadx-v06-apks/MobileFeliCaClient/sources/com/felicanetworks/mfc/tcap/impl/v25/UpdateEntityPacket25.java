package com.felicanetworks.mfc.tcap.impl.v25;

import com.felicanetworks.mfc.FelicaConst;
import com.felicanetworks.mfc.tcap.impl.FeliCaChipWrapper;
import com.felicanetworks.mfc.tcap.impl.InternalDeviceList;
import com.felicanetworks.mfc.tcap.impl.Message;
import com.felicanetworks.mfc.tcap.impl.Packet;
import com.felicanetworks.mfc.tcap.impl.PacketFormatErrorException;
import com.felicanetworks.mfc.tcap.impl.SetFelicaTimeoutMessage;
import com.felicanetworks.mfc.tcap.impl.SetNetworkTimeoutMessage;
import com.felicanetworks.mfc.tcap.impl.SetRetryCountMessage;
import com.felicanetworks.mfc.tcap.impl.WarningMessage;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
class UpdateEntityPacket25 extends Packet {
    static final int MID_REQUEST_ID = 48;
    static final int MID_SET_FELICA_TIMEOUT = 385;
    static final int MID_SET_NETWORK_TIMEOUT = 129;
    static final int MID_SET_RETRY_COUNT = 386;
    static final int MID_WARNING = 1;

    UpdateEntityPacket25(int i) {
        super(i, (byte) 5);
    }

    UpdateEntityPacket25(Packet packet) {
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
            if (id == 1) {
                if (message.getDeviceId() == 0) {
                    warningMessage = new WarningMessage(message);
                    message = warningMessage;
                }
                z = true;
            } else if (id == 48) {
                if (message.getDeviceId() == 0) {
                    warningMessage = new RequestIdMessage(message);
                    message = warningMessage;
                }
                z = true;
            } else if (id == MID_SET_NETWORK_TIMEOUT) {
                if (message.getDeviceId() == 0) {
                    warningMessage = new SetNetworkTimeoutMessage(message);
                    message = warningMessage;
                }
                z = true;
            } else if (id == MID_SET_FELICA_TIMEOUT) {
                if (internalDeviceList.getById(message.getDeviceId()) instanceof FeliCaChipWrapper) {
                    warningMessage = new SetFelicaTimeoutMessage(message);
                    message = warningMessage;
                }
                z = true;
            } else {
                if (id == MID_SET_RETRY_COUNT && (internalDeviceList.getById(message.getDeviceId()) instanceof FeliCaChipWrapper)) {
                    warningMessage = new SetRetryCountMessage(message);
                    message = warningMessage;
                }
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
