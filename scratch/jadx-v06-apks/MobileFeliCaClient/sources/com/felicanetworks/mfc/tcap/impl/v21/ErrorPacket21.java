package com.felicanetworks.mfc.tcap.impl.v21;

import com.felicanetworks.mfc.tcap.impl.Packet;

/* JADX INFO: loaded from: classes.dex */
class ErrorPacket21 extends Packet {
    static final int MID_FINISHED = 0;
    static final int MID_ILLEGAL_STATE_ERROR = 34;
    static final int MID_PACKET_FORMAT_ERROR = 33;
    static final int MID_UNEXPECTED_ERROR = 35;
    static final int MID_WARNING = 1;
    private static final int ORDER_COMPLETE = 2;
    private static final int ORDER_ERROR = 3;
    private static final int ORDER_FINISHED_WAIT = 1;
    private static final int ORDER_FIRST = 0;

    ErrorPacket21() {
        super(TcapSession21.TCAP_VERSION_21, (byte) 3);
    }

    ErrorPacket21(Packet packet) {
        super(packet);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0072  */
    @Override // com.felicanetworks.mfc.tcap.impl.Packet
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void parseMessages(com.felicanetworks.mfc.tcap.impl.InternalDeviceList r11) throws com.felicanetworks.mfc.tcap.impl.PacketFormatErrorException {
        /*
            Method dump skipped, instruction units count: 256
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.tcap.impl.v21.ErrorPacket21.parseMessages(com.felicanetworks.mfc.tcap.impl.InternalDeviceList):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean validateMessageOrder() {
        /*
            r7 = this;
            r0 = 0
            r1 = r0
            r2 = r1
        L3:
            java.util.Vector<com.felicanetworks.mfc.tcap.impl.Message> r3 = r7.mMessages
            int r3 = r3.size()
            r4 = 2
            r5 = 1
            if (r1 >= r3) goto L38
            java.util.Vector<com.felicanetworks.mfc.tcap.impl.Message> r3 = r7.mMessages
            java.lang.Object r3 = r3.elementAt(r1)
            com.felicanetworks.mfc.tcap.impl.Message r3 = (com.felicanetworks.mfc.tcap.impl.Message) r3
            r6 = 3
            if (r2 == 0) goto L2a
            if (r2 == r5) goto L1f
            if (r2 == r4) goto L1d
            goto L38
        L1d:
            r2 = r6
            goto L35
        L1f:
            int r3 = r3.getId()
            if (r3 == 0) goto L28
            if (r3 == r5) goto L35
            goto L1d
        L28:
            r2 = r4
            goto L35
        L2a:
            int r3 = r3.getId()
            if (r3 == r5) goto L35
            switch(r3) {
                case 33: goto L34;
                case 34: goto L34;
                case 35: goto L34;
                default: goto L33;
            }
        L33:
            goto L1d
        L34:
            r2 = r5
        L35:
            int r1 = r1 + 1
            goto L3
        L38:
            if (r2 != r4) goto L3b
            r0 = r5
        L3b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.tcap.impl.v21.ErrorPacket21.validateMessageOrder():boolean");
    }
}
