package com.felicanetworks.mfc.tcap.impl.v21;

import com.felicanetworks.mfc.FelicaConst;
import com.felicanetworks.mfc.tcap.impl.InternalDeviceList;
import com.felicanetworks.mfc.tcap.impl.Message;
import com.felicanetworks.mfc.tcap.impl.Packet;
import com.felicanetworks.mfc.tcap.impl.PacketFormatErrorException;
import com.felicanetworks.mfc.tcap.impl.ReturnCodeMessage;
import com.felicanetworks.mfc.tcap.impl.WarningMessage;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
class FarewellPacket21 extends Packet {
    static final int MID_CLIENT_GOOD_BYE = 33;
    static final int MID_CLIENT_GOOD_BYE_DONE = 34;
    static final int MID_FINISHED = 0;
    static final int MID_RETURN_CODE = 37;
    static final int MID_SERVER_GOOD_BYE = 35;
    static final int MID_SERVER_GOOD_BYE_DONE = 36;
    static final int MID_WARNING = 1;
    private static final int ORDER_COMPLETE = 4;
    private static final int ORDER_ERROR = 5;
    private static final int ORDER_FIRST = 0;
    private static final int ORDER_RETURN_CODE_WAIT = 1;
    private static final int ORDER_SERVER_GOOD_BYE_DONE_WAIT = 2;

    FarewellPacket21() {
        super(TcapSession21.TCAP_VERSION_21, (byte) 2);
    }

    FarewellPacket21(Packet packet) {
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
            if (message.getExtension() == 0 && message.getDeviceId() == 0) {
                byte type = message.getType();
                if (type != 0) {
                    if (type == 1) {
                        warningMessage = new WarningMessage(message);
                    } else {
                        switch (type) {
                            case 35:
                            case 36:
                                break;
                            case 37:
                                warningMessage = new ReturnCodeMessage(message);
                                break;
                            default:
                                z = true;
                                break;
                        }
                    }
                    message = warningMessage;
                }
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x004b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean validateMessageOrder() {
        /*
            r8 = this;
            r0 = 0
            r1 = r0
            r2 = r1
        L3:
            java.util.Vector<com.felicanetworks.mfc.tcap.impl.Message> r3 = r8.mMessages
            int r3 = r3.size()
            r4 = 4
            r5 = 1
            if (r1 >= r3) goto L4f
            java.util.Vector<com.felicanetworks.mfc.tcap.impl.Message> r3 = r8.mMessages
            java.lang.Object r3 = r3.elementAt(r1)
            com.felicanetworks.mfc.tcap.impl.Message r3 = (com.felicanetworks.mfc.tcap.impl.Message) r3
            r6 = 2
            r7 = 5
            if (r2 == 0) goto L3c
            if (r2 == r5) goto L2d
            if (r2 == r6) goto L22
            if (r2 == r4) goto L20
            goto L4f
        L20:
            r2 = r7
            goto L4c
        L22:
            byte r3 = r3.getType()
            if (r3 == r5) goto L4c
            r2 = 36
            if (r3 == r2) goto L4b
            goto L20
        L2d:
            byte r3 = r3.getType()
            if (r3 == 0) goto L4b
            if (r3 == r5) goto L4c
            r2 = 37
            if (r3 == r2) goto L3a
            goto L20
        L3a:
            r2 = r6
            goto L4c
        L3c:
            byte r3 = r3.getType()
            if (r3 == 0) goto L4b
            if (r3 == r5) goto L4c
            r2 = 35
            if (r3 == r2) goto L49
            goto L20
        L49:
            r2 = r5
            goto L4c
        L4b:
            r2 = r4
        L4c:
            int r1 = r1 + 1
            goto L3
        L4f:
            if (r2 != r4) goto L52
            r0 = r5
        L52:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.tcap.impl.v21.FarewellPacket21.validateMessageOrder():boolean");
    }
}
