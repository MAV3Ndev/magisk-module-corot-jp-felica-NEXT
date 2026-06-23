package com.felicanetworks.mfc.tcap.impl.v21;

import com.felicanetworks.mfc.FelicaConst;
import com.felicanetworks.mfc.tcap.impl.FeliCaChipWrapper;
import com.felicanetworks.mfc.tcap.impl.InternalDeviceList;
import com.felicanetworks.mfc.tcap.impl.Message;
import com.felicanetworks.mfc.tcap.impl.OperateDeviceMessage;
import com.felicanetworks.mfc.tcap.impl.Packet;
import com.felicanetworks.mfc.tcap.impl.PacketFormatErrorException;
import com.felicanetworks.mfc.tcap.impl.WarningMessage;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
class OperateEntityPacket21 extends Packet {
    static final int MID_CLOSE_RW_REQUEST = 261;
    static final int MID_DEVICE_RESPONSE = 38;
    static final int MID_FINISHED = 0;
    static final int MID_OPEN_RW_REQUEST = 257;
    static final int MID_OPERATE_DEVICE = 37;
    static final int MID_PLAY_SOUND = 129;
    static final int MID_WARNING = 1;
    private static final int ORDER_COMPLETE = 2;
    private static final int ORDER_ERROR = 3;
    private static final int ORDER_FINISHED_WAIT = 1;
    private static final int ORDER_FIRST = 0;

    OperateEntityPacket21() {
        super(TcapSession21.TCAP_VERSION_21, (byte) 6);
    }

    OperateEntityPacket21(Packet packet) {
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
            if (id != 0) {
                if (id == 1) {
                    if (message.getDeviceId() == 0) {
                        warningMessage = new WarningMessage(message);
                        message = warningMessage;
                    }
                    z = true;
                } else if (id != 37) {
                    if (id == MID_PLAY_SOUND) {
                        if (message.getDeviceId() == 0) {
                            warningMessage = new PlaySoundMessage(message);
                            message = warningMessage;
                        }
                    } else if ((id != MID_OPEN_RW_REQUEST && id != MID_CLOSE_RW_REQUEST) || !(internalDeviceList.getById(message.getDeviceId()) instanceof FeliCaChipWrapper)) {
                    }
                    z = true;
                } else {
                    if (message.getDeviceId() >= 2) {
                        warningMessage = new OperateDeviceMessage(message);
                        message = warningMessage;
                    }
                    z = true;
                }
            } else if (message.getDeviceId() != 0) {
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
        char c = 0;
        for (int i = 0; i < this.mMessages.size(); i++) {
            Message messageElementAt = this.mMessages.elementAt(i);
            if (c == 0) {
                int id = messageElementAt.getId();
                if (id == 0) {
                    c = 3;
                } else if (id != 1) {
                    c = 1;
                }
            } else if (c != 1) {
                if (c != 2) {
                    break;
                }
                c = 3;
            } else if (messageElementAt.getId() == 0) {
                c = 2;
            }
        }
        return c == 2;
    }
}
