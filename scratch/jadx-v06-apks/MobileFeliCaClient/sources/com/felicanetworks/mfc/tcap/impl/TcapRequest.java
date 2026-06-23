package com.felicanetworks.mfc.tcap.impl;

import com.felicanetworks.mfc.felica.access_control.AccessControlManager;
import com.felicanetworks.mfc.felica.util.ByteBuffer;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

/* JADX INFO: loaded from: classes.dex */
public abstract class TcapRequest {
    private static final int MAX_BUFFER_SIZE = 65540;
    private static final int READ_BUFFER_SIZE = 4096;
    private ByteBuffer mBuffer = new ByteBuffer(MAX_BUFFER_SIZE);
    private String mCookie;
    private Packets mPackets;

    protected abstract Packets parseData() throws PacketFormatErrorException;

    public void reset() {
        this.mPackets = null;
        this.mBuffer.setLength(0);
        this.mCookie = null;
    }

    void receive(HttpURLConnection httpURLConnection) throws Throwable {
        byte[] bArr = new byte[4096];
        reset();
        BufferedInputStream bufferedInputStream = null;
        try {
            try {
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode != 200) {
                    throw new HttpException("Invalid response code: " + responseCode);
                }
                String contentType = httpURLConnection.getContentType();
                if (contentType == null || contentType.indexOf("application/x-tcap") < 0) {
                    throw new HttpException("Invalid content-type: " + contentType);
                }
                this.mCookie = httpURLConnection.getHeaderField("Set-Cookie");
                if (httpURLConnection.getHeaderField("Transfer-Encoding") == null && httpURLConnection.getContentLength() <= 0) {
                    throw new HttpException(AccessControlManager.ERROR_MSG_HTTP_COMMUNICATION_ERROR);
                }
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(httpURLConnection.getInputStream());
                while (true) {
                    try {
                        int i = bufferedInputStream2.read(bArr);
                        if (i > 0) {
                            this.mBuffer.append(bArr, 0, i);
                        } else {
                            try {
                                bufferedInputStream2.close();
                                return;
                            } catch (IOException unused) {
                                return;
                            }
                        }
                    } catch (HttpException e) {
                        throw e;
                    } catch (Exception unused2) {
                        throw new HttpException(AccessControlManager.ERROR_MSG_HTTP_COMMUNICATION_ERROR);
                    } catch (Throwable th) {
                        th = th;
                        bufferedInputStream = bufferedInputStream2;
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                            } catch (IOException unused3) {
                            }
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (HttpException e2) {
            throw e2;
        } catch (Exception unused4) {
        }
    }

    public byte[] getBytes() {
        return this.mBuffer.getBytes();
    }

    public String getCookie() {
        return this.mCookie;
    }

    public int getSize() {
        return this.mBuffer.getLength();
    }

    public void parse() throws PacketFormatErrorException {
        if (this.mPackets != null) {
            return;
        }
        if (!validateData()) {
            LogMgr.log(1, "%s", "700");
            throw new PacketFormatErrorException(PacketFormatErrorException.TYPE_PACKET_FORMAT_ERROR, null);
        }
        this.mPackets = parseData();
    }

    public int getPacketNum() {
        return this.mPackets.getPacketNum();
    }

    public Packet getPacket(int i) {
        return this.mPackets.getPacket(i);
    }

    public void removePacket(int i) {
        this.mPackets.removePacket(i);
    }

    protected boolean validateData() {
        byte[] bytes = this.mBuffer.getBytes();
        int length = this.mBuffer.getLength();
        int i = 0;
        while (i < length) {
            if (length - i < 5) {
                return false;
            }
            i += (((bytes[i + 3] & 255) << 8) | (bytes[i + 4] & 255)) + 5;
        }
        return i == length;
    }
}
