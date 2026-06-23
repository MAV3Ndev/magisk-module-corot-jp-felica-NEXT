package com.felicanetworks.mfc.tcap.impl.v25;

import com.felicanetworks.mfc.tcap.impl.Packet;

/* JADX INFO: loaded from: classes.dex */
class ErrorPacket25 extends Packet {
    static final int MID_ILLEGAL_STATE_ERROR = 34;
    static final int MID_PACKET_FORMAT_ERROR = 33;
    static final int MID_UNEXPECTED_ERROR = 35;
    static final int MID_WARNING = 1;
    private static final int ORDER_COMPLETE = 1;
    private static final int ORDER_ERROR = 2;
    private static final int ORDER_FIRST = 0;

    ErrorPacket25(int i) {
        super(i, (byte) 3);
    }

    ErrorPacket25(Packet packet) {
        super(packet);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0070  */
    @Override // com.felicanetworks.mfc.tcap.impl.Packet
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void parseMessages(com.felicanetworks.mfc.tcap.impl.InternalDeviceList r11) throws com.felicanetworks.mfc.tcap.impl.PacketFormatErrorException {
        /*
            Method dump skipped, instruction units count: 254
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.tcap.impl.v25.ErrorPacket25.parseMessages(com.felicanetworks.mfc.tcap.impl.InternalDeviceList):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002a  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean validateMessageOrder() {
        /*
            r6 = this;
            r0 = 0
            r1 = r0
            r2 = r1
        L3:
            java.util.Vector<com.felicanetworks.mfc.tcap.impl.Message> r3 = r6.mMessages
            int r3 = r3.size()
            r4 = 1
            if (r1 >= r3) goto L30
            java.util.Vector<com.felicanetworks.mfc.tcap.impl.Message> r3 = r6.mMessages
            java.lang.Object r3 = r3.elementAt(r1)
            com.felicanetworks.mfc.tcap.impl.Message r3 = (com.felicanetworks.mfc.tcap.impl.Message) r3
            r5 = 2
            if (r2 == 0) goto L21
            if (r2 == r4) goto L1a
            goto L30
        L1a:
            byte r3 = r3.getType()
            if (r3 == r4) goto L2d
            goto L2a
        L21:
            byte r3 = r3.getType()
            if (r3 == r4) goto L2d
            switch(r3) {
                case 33: goto L2c;
                case 34: goto L2c;
                case 35: goto L2c;
                default: goto L2a;
            }
        L2a:
            r2 = r5
            goto L2d
        L2c:
            r2 = r4
        L2d:
            int r1 = r1 + 1
            goto L3
        L30:
            if (r2 != r4) goto L33
            r0 = r4
        L33:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.tcap.impl.v25.ErrorPacket25.validateMessageOrder():boolean");
    }
}
