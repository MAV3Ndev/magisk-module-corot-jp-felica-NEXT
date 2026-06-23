package com.felicanetworks.mfc.tcap.impl.v25;

import com.felicanetworks.mfc.tcap.impl.Packet;

/* JADX INFO: loaded from: classes.dex */
class FarewellPacket25 extends Packet {
    static final int MID_RETURN_CODE = 37;
    static final int MID_SERVER_GOOD_BYE = 35;
    static final int MID_SERVER_GOOD_BYE_DONE = 36;
    static final int MID_WARNING = 1;
    private static final int ORDER_COMPLETE = 3;
    private static final int ORDER_ERROR = 4;
    private static final int ORDER_FIRST = 0;
    private static final int ORDER_RETURN_CODE_WAIT = 1;
    private static final int ORDER_SERVER_GOOD_BYE_DONE_WAIT = 2;

    FarewellPacket25(int i) {
        super(i, (byte) 2);
    }

    FarewellPacket25(Packet packet) {
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
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.tcap.impl.v25.FarewellPacket25.parseMessages(com.felicanetworks.mfc.tcap.impl.InternalDeviceList):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x004b  */
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
            if (r1 >= r3) goto L51
            java.util.Vector<com.felicanetworks.mfc.tcap.impl.Message> r3 = r8.mMessages
            java.lang.Object r3 = r3.elementAt(r1)
            com.felicanetworks.mfc.tcap.impl.Message r3 = (com.felicanetworks.mfc.tcap.impl.Message) r3
            r6 = 2
            r7 = 4
            if (r2 == 0) goto L41
            if (r2 == r5) goto L34
            if (r2 == r6) goto L27
            if (r2 == r4) goto L20
            goto L51
        L20:
            byte r3 = r3.getType()
            if (r3 == r5) goto L4e
            goto L4b
        L27:
            byte r3 = r3.getType()
            if (r3 == r5) goto L4e
            r2 = 36
            if (r3 == r2) goto L32
            goto L4b
        L32:
            r2 = r4
            goto L4e
        L34:
            byte r3 = r3.getType()
            if (r3 == r5) goto L4e
            r2 = 37
            if (r3 == r2) goto L3f
            goto L4b
        L3f:
            r2 = r6
            goto L4e
        L41:
            byte r3 = r3.getType()
            if (r3 == r5) goto L4e
            r2 = 35
            if (r3 == r2) goto L4d
        L4b:
            r2 = r7
            goto L4e
        L4d:
            r2 = r5
        L4e:
            int r1 = r1 + 1
            goto L3
        L51:
            if (r2 != r4) goto L54
            r0 = r5
        L54:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.tcap.impl.v25.FarewellPacket25.validateMessageOrder():boolean");
    }
}
