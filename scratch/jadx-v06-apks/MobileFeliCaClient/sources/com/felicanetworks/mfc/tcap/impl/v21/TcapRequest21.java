package com.felicanetworks.mfc.tcap.impl.v21;

import com.felicanetworks.mfc.tcap.impl.Packet;
import com.felicanetworks.mfc.tcap.impl.PacketFormatErrorException;
import com.felicanetworks.mfc.tcap.impl.Packets;
import com.felicanetworks.mfc.tcap.impl.TcapRequest;

/* JADX INFO: loaded from: classes.dex */
class TcapRequest21 extends TcapRequest {
    TcapRequest21() {
    }

    @Override // com.felicanetworks.mfc.tcap.impl.TcapRequest
    protected Packets parseData() throws PacketFormatErrorException {
        Packet handshakePacket21;
        byte[] bytes = getBytes();
        int size = getSize();
        Packets packets = new Packets();
        int size2 = 0;
        boolean z = false;
        while (size2 < size) {
            Packet packet = new Packet(bytes, size2, size);
            if (packet.getVersion() != 513) {
                throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_UNSUPPORTED_VERSION, new byte[]{packet.getMajorVersion(), packet.getMinorVersion()});
            }
            switch (packet.getSubProtocolType()) {
                case 1:
                    if (packets.getPacketNum() > 0) {
                        throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_PACKET_FORMAT_ERROR, null);
                    }
                    handshakePacket21 = new HandshakePacket21(packet);
                    break;
                    break;
                case 2:
                    if (packets.getPacketNum() > 0) {
                        throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_PACKET_FORMAT_ERROR, null);
                    }
                    handshakePacket21 = new FarewellPacket21(packet);
                    break;
                    break;
                case 3:
                    if (packets.getPacketNum() > 0) {
                        throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_PACKET_FORMAT_ERROR, null);
                    }
                    handshakePacket21 = new ErrorPacket21(packet);
                    break;
                    break;
                case 4:
                    if (z) {
                        throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_PACKET_FORMAT_ERROR, null);
                    }
                    handshakePacket21 = new ApplicationDataTransferPacket21(packet);
                    continue;
                    packets.addPacket(handshakePacket21);
                    size2 += handshakePacket21.getSize();
                    break;
                    break;
                case 5:
                    if (z) {
                        throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_PACKET_FORMAT_ERROR, null);
                    }
                    handshakePacket21 = new UpdateEntityPacket21(packet);
                    continue;
                    packets.addPacket(handshakePacket21);
                    size2 += handshakePacket21.getSize();
                    break;
                    break;
                case 6:
                    if (z) {
                        throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_PACKET_FORMAT_ERROR, null);
                    }
                    handshakePacket21 = new OperateEntityPacket21(packet);
                    continue;
                    packets.addPacket(handshakePacket21);
                    size2 += handshakePacket21.getSize();
                    break;
                    break;
                default:
                    throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_UNSUPPORTED_SUBPROTOCOL, new byte[]{packet.getSubProtocolType()});
            }
            z = true;
            packets.addPacket(handshakePacket21);
            size2 += handshakePacket21.getSize();
        }
        return packets;
    }
}
