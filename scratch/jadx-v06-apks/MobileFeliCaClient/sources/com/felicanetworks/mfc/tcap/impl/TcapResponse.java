package com.felicanetworks.mfc.tcap.impl;

import com.felicanetworks.mfc.felica.access_control.AccessControlManager;
import com.felicanetworks.mfc.felica.util.ByteBuffer;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

/* JADX INFO: loaded from: classes.dex */
public class TcapResponse {
    private static final int MAX_SIZE = 65540;
    private ByteBuffer mBuffer = new ByteBuffer(MAX_SIZE);
    private String mCookie;
    private Packets mPackets;
    private int mSize;

    public void reset() {
        this.mPackets = null;
        this.mCookie = null;
        this.mSize = 0;
        this.mBuffer.setLength(0);
    }

    public void send(HttpURLConnection httpURLConnection) throws Throwable {
        Packets packets = this.mPackets;
        if (packets != null) {
            packets.getBytes(this.mBuffer);
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setFixedLengthStreamingMode(this.mBuffer.getLength());
                httpURLConnection.setRequestProperty("Accept", "*, */*");
                httpURLConnection.setRequestProperty("Content-Type", "application/x-tcap");
                if (this.mCookie != null) {
                    httpURLConnection.setRequestProperty("Cookie", this.mCookie);
                }
                if (this.mBuffer.getLength() > 0) {
                    BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    try {
                        bufferedOutputStream2.write(this.mBuffer.getBytes(), 0, this.mBuffer.getLength());
                        bufferedOutputStream = bufferedOutputStream2;
                    } catch (Exception unused) {
                        bufferedOutputStream = bufferedOutputStream2;
                        LogMgr.log(1, "%s Exception", "700");
                        throw new HttpException(AccessControlManager.ERROR_MSG_HTTP_COMMUNICATION_ERROR);
                    } catch (Throwable th) {
                        th = th;
                        bufferedOutputStream = bufferedOutputStream2;
                        if (bufferedOutputStream != null) {
                            try {
                                bufferedOutputStream.close();
                            } catch (IOException unused2) {
                            }
                        }
                        throw th;
                    }
                }
                if (bufferedOutputStream != null) {
                    try {
                        bufferedOutputStream.close();
                    } catch (IOException unused3) {
                    }
                }
            } catch (Exception unused4) {
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void setCookie(String str) {
        this.mCookie = str;
    }

    public void addPacket(Packet packet) {
        if (this.mPackets == null) {
            this.mPackets = new Packets();
        }
        this.mSize += packet.getSize();
        if (this.mSize > MAX_SIZE) {
            LogMgr.log(1, "%s", "700");
            throw new RuntimeException("Buffer overflow.");
        }
        this.mPackets.addPacket(packet);
    }
}
