package com.felicanetworks.mfc.tcap.impl;

import android.os.RemoteException;
import com.felicanetworks.mfc.tcap.FeliCaClient;
import com.felicanetworks.mfc.tcap.FeliCaClientEventListener;
import com.felicanetworks.mfc.tcap.FeliCaClientException;
import com.felicanetworks.mfc.tcap.InternalDevice;
import com.felicanetworks.mfc.tcap.impl.v21.TcapSession21;
import com.felicanetworks.mfc.tcap.impl.v25.TcapSession25;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.mfw.i.fbl.Property;
import com.felicanetworks.tcap.IFelicaDevice;

/* JADX INFO: compiled from: StoppableTcapClient.java */
/* JADX INFO: loaded from: classes.dex */
class InnerTcapClient implements Runnable, FeliCaClient {
    private static final String EXC_TCAP_STOPED = "TCAP stoped.";
    private FeliCaClientEventListener mListener;
    private byte[] mPostdata;
    private TcapSession mSession;
    private Thread mThread;
    private boolean mTlamStopped;
    private String mUrl;
    private boolean mUseParam;
    private String mUserAgent;
    protected Object mListenerLockObject = new Object();
    protected boolean mCanceled = false;
    private InternalDeviceList mDeviceList = new InternalDeviceList();
    protected TcapCommunicationAgent mAgent = new TcapCommunicationAgent();

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public void setUrl(String str) throws NullPointerException, FeliCaClientException {
        if (isStarted()) {
            LogMgr.log(1, "%s", "700");
            throw new FeliCaClientException(Property.URL_VERUP_SITE);
        }
        if (str == null) {
            LogMgr.log(1, "%s", "701");
            throw new NullPointerException();
        }
        this.mUrl = str;
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public String getUrl() {
        return this.mUrl;
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public void setEventListener(FeliCaClientEventListener feliCaClientEventListener) throws FeliCaClientException {
        if (isStarted()) {
            LogMgr.log(1, "%s", "700");
            throw new FeliCaClientException(Property.URL_VERUP_SITE);
        }
        synchronized (this.mListenerLockObject) {
            this.mListener = feliCaClientEventListener;
        }
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public FeliCaClientEventListener getEventListener() {
        return this.mListener;
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public void addDevice(InternalDevice internalDevice, int i) throws IllegalArgumentException, NullPointerException, FeliCaClientException {
        if (isStarted()) {
            LogMgr.log(1, "%s", "700");
            throw new FeliCaClientException(Property.URL_VERUP_SITE);
        }
        if (internalDevice == null) {
            LogMgr.log(1, "%s", "701");
            throw new NullPointerException();
        }
        if (this.mDeviceList.add(internalDevice, i, null) != null) {
            return;
        }
        LogMgr.log(1, "%s", "703");
        throw new IllegalArgumentException();
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public InternalDevice getDevice(String str, String str2) {
        DeviceWrapper deviceWrapper = this.mDeviceList.get(str, str2);
        if (deviceWrapper == null) {
            return null;
        }
        return deviceWrapper.getDevice();
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public InternalDevice removeDevice(String str, String str2) throws FeliCaClientException {
        if (isStarted()) {
            LogMgr.log(1, "%s", "700");
            throw new FeliCaClientException(Property.URL_VERUP_SITE);
        }
        DeviceWrapper deviceWrapperRemove = this.mDeviceList.remove(str, str2);
        if (deviceWrapperRemove == null) {
            return null;
        }
        return deviceWrapperRemove.getDevice();
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public void removeAllDevices() throws FeliCaClientException {
        if (isStarted()) {
            LogMgr.log(1, "%s", "700");
            throw new FeliCaClientException(Property.URL_VERUP_SITE);
        }
        this.mDeviceList.clear();
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public synchronized void start(boolean z, byte[] bArr) throws FeliCaClientException {
        if (this.mThread != null) {
            LogMgr.log(1, "%s", "700");
            throw new FeliCaClientException(Property.URL_VERUP_SITE);
        }
        if (this.mUrl == null) {
            LogMgr.log(1, "%s", "701");
            throw new FeliCaClientException(Property.URL_VERUP_SITE);
        }
        if (this.mListener == null) {
            LogMgr.log(1, "%s", "702");
            throw new FeliCaClientException(Property.URL_VERUP_SITE);
        }
        if (this.mDeviceList == null) {
            LogMgr.log(1, "%s", "703");
            throw new FeliCaClientException(Property.URL_VERUP_SITE);
        }
        this.mUseParam = z;
        this.mPostdata = bArr;
        this.mThread = new Thread(this);
        this.mTlamStopped = false;
        this.mThread.start();
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public void stop(boolean z) {
        if (this.mThread != null) {
            this.mTlamStopped = true;
            if (z) {
                this.mAgent.abort();
            }
            TcapSession tcapSession = this.mSession;
            if (tcapSession != null) {
                try {
                    tcapSession.stop();
                } catch (RemoteException e) {
                    LogMgr.log(1, "%s %s", "700", e.getClass().getSimpleName());
                }
            }
        }
    }

    public boolean stopCutOff(boolean z) {
        if (this.mThread == null) {
            return false;
        }
        cutOffInstance(z);
        this.mTlamStopped = true;
        if (z) {
            this.mAgent.abort();
        }
        TcapSession tcapSession = this.mSession;
        if (tcapSession != null) {
            try {
                tcapSession.stop();
            } catch (Exception e) {
                LogMgr.log(1, "%s %s", "700", e.getClass().getSimpleName());
            }
        }
        if (!z) {
            return false;
        }
        this.mThread = null;
        return true;
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public boolean isStarted() {
        return this.mThread != null;
    }

    @Override // java.lang.Runnable
    public void run() {
        String message;
        int iStart;
        int i;
        String message2;
        FeliCaClientEventListener feliCaClientEventListener;
        String value;
        TcapSession tcapSession25;
        int i2 = 3;
        boolean z = false;
        try {
            this.mAgent.init();
            this.mAgent.setUserAgent(this.mUserAgent);
            this.mSession = null;
        } catch (HttpException e) {
            LogMgr.log(1, "%s HTTPException", "706");
            i = e.isCanceled() ? 1 : 2;
            message2 = e.getMessage();
            iStart = 0;
            z = true;
            int i3 = i;
            message = message2;
            i2 = i3;
        } catch (IllegalStateErrorException e2) {
            LogMgr.log(1, "%s IllegalStateErrorException", "702");
            message = e2.getMessage();
            iStart = 0;
            z = true;
        } catch (PacketFormatErrorException e3) {
            LogMgr.log(1, "%s PacketFormatErrorException", "701");
            message = e3.getMessage();
            iStart = 0;
            z = true;
        } catch (TlamFormatErrorException e4) {
            LogMgr.log(1, "%s TLAMFormatErrorException", "705");
            message = e4.getMessage();
            iStart = 0;
            z = true;
        } catch (UnexpectedErrorException e5) {
            LogMgr.log(1, "%s UnexpectedErrorException", "703");
            i = e5.getType() == 1 ? 1 : 0;
            message2 = e5.getMessage();
            iStart = 0;
            z = true;
            int i32 = i;
            message = message2;
            i2 = i32;
        } catch (TcapException e6) {
            LogMgr.log(1, "%s TCAPException", "704");
            message = e6.getMessage();
            iStart = 0;
            z = true;
        } catch (Exception e7) {
            LogMgr.log(1, "%s Exception", "707");
            message = e7.getMessage();
            i2 = 0;
            iStart = 0;
            z = true;
        }
        if (this.mTlamStopped) {
            LogMgr.log(1, "%s", "700");
            throw new UnexpectedErrorException((byte) 1, "Interrupted.");
        }
        if (this.mUseParam) {
            TlamResponse tlamMetadata = getTlamMetadata();
            value = tlamMetadata.getValue("SERV");
            if (value == null) {
                value = tlamMetadata.getValue("COMM");
                tcapSession25 = value != null ? new TcapSession21(this.mAgent) : null;
            } else {
                tcapSession25 = new TcapSession25(this.mAgent);
            }
        } else {
            value = this.mUrl;
            tcapSession25 = new TcapSession25(this.mAgent);
        }
        if (tcapSession25 == null) {
            throw new TlamFormatErrorException();
        }
        tcapSession25.setUrl(value);
        tcapSession25.setDeviceList(this.mDeviceList);
        this.mSession = tcapSession25;
        if (this.mTlamStopped) {
            this.mSession.stop();
        }
        iStart = this.mSession.start();
        message = null;
        i2 = 0;
        try {
            this.mThread = null;
            this.mSession = null;
            synchronized (this.mListenerLockObject) {
                feliCaClientEventListener = this.mListener;
            }
            if (z) {
                if (feliCaClientEventListener != null) {
                    feliCaClientEventListener.errorOccurred(i2, message);
                }
            } else if (feliCaClientEventListener != null) {
                feliCaClientEventListener.finished(iStart);
            }
        } catch (Exception unused) {
            LogMgr.log(1, "%s", "708");
        }
    }

    private TlamResponse getTlamMetadata() throws HttpException, TlamFormatErrorException {
        TlamRequest tlamRequest = new TlamRequest();
        TlamResponse tlamResponse = new TlamResponse();
        tlamRequest.setPostData(this.mPostdata);
        this.mAgent.doTlamTransaction(this.mUrl, tlamRequest, tlamResponse);
        tlamResponse.parse();
        return tlamResponse;
    }

    private void cutOffInstance(boolean z) {
        FeliCaClientEventListener feliCaClientEventListener;
        if (z) {
            if (this.mSession != null) {
                this.mDeviceList.clear();
            }
            synchronized (this.mListenerLockObject) {
                feliCaClientEventListener = this.mListener;
                this.mListener = null;
            }
            if (feliCaClientEventListener != null) {
                try {
                    feliCaClientEventListener.errorOccurred(100, EXC_TCAP_STOPED);
                } catch (Exception unused) {
                }
            }
            this.mCanceled = true;
        }
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public void setUserAgent(String str) throws NullPointerException {
        if (str == null) {
            throw new NullPointerException();
        }
        this.mUserAgent = str;
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public String getUserAgent() {
        return this.mUserAgent;
    }

    @Override // com.felicanetworks.mfc.tcap.FeliCaClient
    public void addDevice(InternalDevice internalDevice, int i, IFelicaDevice iFelicaDevice) throws IllegalArgumentException, NullPointerException, FeliCaClientException {
        this.mDeviceList.add(internalDevice, i, iFelicaDevice);
    }
}
