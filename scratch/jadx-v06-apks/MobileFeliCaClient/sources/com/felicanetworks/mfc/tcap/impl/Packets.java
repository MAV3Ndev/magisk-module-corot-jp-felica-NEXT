package com.felicanetworks.mfc.tcap.impl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class Packets {
    protected Vector<Packet> mPackets;

    public int getPacketNum() {
        Vector<Packet> vector = this.mPackets;
        if (vector == null) {
            return 0;
        }
        return vector.size();
    }

    public Packet getPacket(int i) {
        return this.mPackets.elementAt(i);
    }

    public void addPacket(Packet packet) {
        if (this.mPackets == null) {
            this.mPackets = new Vector<>();
        }
        this.mPackets.addElement(packet);
    }

    public void removePacket(int i) {
        this.mPackets.removeElementAt(i);
    }

    public void getBytes(ByteBuffer byteBuffer) {
        if (this.mPackets != null) {
            for (int i = 0; i < this.mPackets.size(); i++) {
                this.mPackets.elementAt(i).getBytes(byteBuffer);
            }
        }
    }
}
