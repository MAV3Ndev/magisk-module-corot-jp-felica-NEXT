package com.felicanetworks.mfc.tcap.impl.v25;

import com.felicanetworks.mfc.tcap.impl.Packet;
import com.felicanetworks.mfc.tcap.impl.PacketFormatErrorException;
import com.felicanetworks.mfc.tcap.impl.Packets;
import com.felicanetworks.mfc.tcap.impl.TcapRequest;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
class TcapRequest25 extends TcapRequest {
    private HashMap<Integer, FeliCaParamPool> mParamPool = new HashMap<>();

    TcapRequest25() {
    }

    @Override // com.felicanetworks.mfc.tcap.impl.TcapRequest
    public void reset() {
        super.reset();
        HashMap<Integer, FeliCaParamPool> map = this.mParamPool;
        if (map == null) {
            return;
        }
        map.clear();
    }

    public FeliCaParamPool getFeliCaParamPool(int i) {
        Integer numValueOf = Integer.valueOf(i);
        if (!this.mParamPool.containsKey(numValueOf)) {
            this.mParamPool.put(numValueOf, new FeliCaParamPool());
        }
        return this.mParamPool.get(numValueOf);
    }

    @Override // com.felicanetworks.mfc.tcap.impl.TcapRequest
    protected Packets parseData() throws PacketFormatErrorException {
        Packet handshakePacket25;
        byte[] bytes = getBytes();
        int size = getSize();
        Packets packets = new Packets();
        int size2 = 0;
        while (size2 < size) {
            Packet packet = new Packet(bytes, size2, size);
            if (packet.getVersion() == 517) {
                switch (packet.getSubProtocolType()) {
                    case 1:
                        handshakePacket25 = new HandshakePacket25(packet);
                        break;
                    case 2:
                        handshakePacket25 = new FarewellPacket25(packet);
                        break;
                    case 3:
                        handshakePacket25 = new ErrorPacket25(packet);
                        break;
                    case 4:
                        handshakePacket25 = new ApplicationDataTransferPacket25(packet);
                        break;
                    case 5:
                        handshakePacket25 = new UpdateEntityPacket25(packet);
                        break;
                    case 6:
                        handshakePacket25 = new OperateEntityPacket25(packet);
                        break;
                    default:
                        throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_UNSUPPORTED_SUBPROTOCOL, new byte[]{packet.getSubProtocolType()});
                }
                packets.addPacket(handshakePacket25);
                size2 += handshakePacket25.getSize();
            } else {
                throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_UNSUPPORTED_VERSION, new byte[]{packet.getMajorVersion(), packet.getMinorVersion()});
            }
        }
        if (validatePacketOrder(packets)) {
            return packets;
        }
        throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_PACKET_FORMAT_ERROR, null);
    }

    private boolean validatePacketOrder(Packets packets) {
        if (packets.getPacketNum() == 0) {
            return false;
        }
        if (packets.getPacket(0).getSubProtocolType() == 3) {
            if (packets.getPacketNum() > 1) {
                return false;
            }
        } else {
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < packets.getPacketNum(); i3++) {
                byte subProtocolType = packets.getPacket(i3).getSubProtocolType();
                if (subProtocolType == 1) {
                    i++;
                } else if (subProtocolType == 2) {
                    i2++;
                } else if (subProtocolType != 4 && subProtocolType != 5 && subProtocolType != 6) {
                    return false;
                }
            }
            if (i > 1 || i2 > 1) {
                return false;
            }
        }
        return true;
    }
}
