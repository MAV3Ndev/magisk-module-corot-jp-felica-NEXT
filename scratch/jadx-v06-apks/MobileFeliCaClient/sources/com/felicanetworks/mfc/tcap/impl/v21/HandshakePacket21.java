package com.felicanetworks.mfc.tcap.impl.v21;

import com.felicanetworks.mfc.tcap.impl.Packet;

/* JADX INFO: loaded from: classes.dex */
class HandshakePacket21 extends Packet {
    static final int MID_CLIENT_HELLO = 33;
    static final int MID_CLIENT_HELLO_DONE = 34;
    static final int MID_DEVICES = 37;
    static final int MID_FINISHED = 0;
    static final int MID_SERVER_HELLO = 35;
    static final int MID_SERVER_HELLO_DONE = 36;
    static final int MID_WARNING = 1;
    private static final int ORDER_COMPLETE = 3;
    private static final int ORDER_ERROR = 4;
    private static final int ORDER_FINISHED_WAIT = 2;
    private static final int ORDER_FIRST = 0;
    private static final int ORDER_SERVER_HELLO_DONE_WAIT = 1;

    HandshakePacket21() {
        super(TcapSession21.TCAP_VERSION_21, (byte) 1);
    }

    HandshakePacket21(Packet packet) {
        super(packet);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0071  */
    @Override // com.felicanetworks.mfc.tcap.impl.Packet
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void parseMessages(com.felicanetworks.mfc.tcap.impl.InternalDeviceList r12) throws com.felicanetworks.mfc.tcap.impl.PacketFormatErrorException {
        /*
            Method dump skipped, instruction units count: 244
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.tcap.impl.v21.HandshakePacket21.parseMessages(com.felicanetworks.mfc.tcap.impl.InternalDeviceList):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0020  */
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
            r4 = 3
            r5 = 1
            if (r1 >= r3) goto L49
            java.util.Vector<com.felicanetworks.mfc.tcap.impl.Message> r3 = r8.mMessages
            java.lang.Object r3 = r3.elementAt(r1)
            com.felicanetworks.mfc.tcap.impl.Message r3 = (com.felicanetworks.mfc.tcap.impl.Message) r3
            r6 = 2
            r7 = 4
            if (r2 == 0) goto L3a
            if (r2 == r5) goto L2d
            if (r2 == r6) goto L22
            if (r2 == r4) goto L20
            goto L49
        L20:
            r2 = r7
            goto L46
        L22:
            int r3 = r3.getId()
            if (r3 == 0) goto L2b
            if (r3 == r5) goto L46
            goto L20
        L2b:
            r2 = r4
            goto L46
        L2d:
            int r3 = r3.getId()
            if (r3 == r5) goto L46
            r2 = 36
            if (r3 == r2) goto L38
            goto L20
        L38:
            r2 = r6
            goto L46
        L3a:
            int r3 = r3.getId()
            if (r3 == r5) goto L46
            r2 = 35
            if (r3 == r2) goto L45
            goto L20
        L45:
            r2 = r5
        L46:
            int r1 = r1 + 1
            goto L3
        L49:
            if (r2 != r4) goto L4c
            r0 = r5
        L4c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.tcap.impl.v21.HandshakePacket21.validateMessageOrder():boolean");
    }
}
