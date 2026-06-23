package com.felicanetworks.mfm.main.model.internal.main.net;

import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.fix.Information;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class NetworkExpert {
    private static final String CHARCODE = "UTF-8";
    private static final String CONNECT_TYPE_GET = "GET";
    private static final String CONTENT_LENGTH_KEY = "content-length";
    private ModelContext _modelContext;
    private UserAgent _ua;
    private int communication_data_reception_maximum_size;
    private boolean isDisconnect;
    private HttpURLConnection urlconn;

    public interface NetworkAccessListener {
        void receiveRatio(int i);

        void session();
    }

    public static class Request {
        private static final int CUT_SIZE = 1024;
        public int connectTimeout;
        public byte[] data;
        public Map<String, String> header;
        public String method;
        public int readTimeout;
        public String url;

        public Request(String str, String str2, Map<String, String> map, int i, int i2, byte[] bArr) {
            this.url = str;
            this.method = str2;
            this.header = map;
            this.connectTimeout = i;
            this.readTimeout = i2;
            this.data = bArr != null ? (byte[]) bArr.clone() : null;
        }

        public String toString() {
            String str;
            byte[] bArr = this.data;
            if (bArr == null) {
                str = null;
            } else if (bArr.length > 1024) {
                byte[] bArr2 = new byte[1024];
                System.arraycopy(bArr, 0, bArr2, 0, 1024);
                str = new String(bArr2, StandardCharsets.UTF_8);
            } else {
                str = new String(this.data, StandardCharsets.UTF_8);
            }
            return "Request[" + this.url + ", " + this.method + ", " + this.header + ", " + str + "]";
        }
    }

    public static class Response {
        public int code;
        public byte[] data;
        public Map<String, String> header;

        public Response(int i, Map<String, String> map, byte[] bArr) {
            this.code = i;
            this.header = map;
            this.data = (byte[]) bArr.clone();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Response[");
            sb.append(this.code);
            sb.append(", ");
            sb.append(this.header);
            sb.append(", data.length=");
            byte[] bArr = this.data;
            sb.append(bArr != null ? Integer.valueOf(bArr.length) : "null");
            sb.append("]");
            return sb.toString();
        }
    }

    public static class UserAgent {
        public String userAgent = Information.userAgent();

        public String getString() {
            return this.userAgent;
        }

        public String toString() {
            return "UserAgent{userAgent='" + this.userAgent + "'}";
        }
    }

    public Response connect(Request request) throws NetworkExpertException {
        return connect(request, null, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v1 */
    /* JADX WARN: Type inference failed for: r12v20 */
    /* JADX WARN: Type inference failed for: r12v21 */
    /* JADX WARN: Type inference failed for: r12v5, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r12v6 */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v5 */
    public synchronized Response connect(Request request, NetworkAccessListener networkAccessListener, int i) throws NetworkExpertException {
        ?? r12;
        InputStream inputStream;
        Exception exc;
        SocketTimeoutException socketTimeoutException;
        InterruptedException interruptedException;
        OutputStream outputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        Object obj;
        int responseCode;
        HashMap map;
        int i2;
        Response response;
        this.isDisconnect = false;
        ?? r1 = 0;
        inputStream = null;
        InputStream inputStream2 = null;
        try {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) getUrl(request).openConnection();
                this.urlconn = httpURLConnection;
                httpURLConnection.setRequestMethod(request.method);
                this.urlconn.setConnectTimeout(request.connectTimeout);
                this.urlconn.setReadTimeout(request.readTimeout);
                for (String str : request.header.keySet()) {
                    this.urlconn.setRequestProperty(str, request.header.get(str));
                }
                if (request.method.equals(CONNECT_TYPE_GET)) {
                    outputStream = null;
                } else {
                    this.urlconn.setDoOutput(true);
                    this.urlconn.setFixedLengthStreamingMode(Integer.parseInt(request.header.get(CONTENT_LENGTH_KEY)));
                    outputStream = this.urlconn.getOutputStream();
                    try {
                        outputStream.write(request.data);
                    } catch (NetworkExpertException e) {
                        e = e;
                        byteArrayOutputStream = null;
                        try {
                            LogUtil.warning(e);
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            inputStream = inputStream2;
                            r1 = outputStream;
                            r12 = byteArrayOutputStream;
                            try {
                                r1.close();
                            } catch (Exception unused) {
                            }
                            try {
                                inputStream.close();
                            } catch (Exception unused2) {
                            }
                            try {
                                r12.close();
                            } catch (Exception unused3) {
                            }
                            disconnect();
                            throw th;
                        }
                    } catch (InterruptedException e2) {
                        interruptedException = e2;
                        LogUtil.warning(interruptedException);
                        throw new NetworkExpertException(getClass(), 257, interruptedException, "communication interruption", NetworkExpertException.Type.ID_CANCELED);
                    } catch (SocketTimeoutException e3) {
                        socketTimeoutException = e3;
                        LogUtil.warning(socketTimeoutException);
                        throw new NetworkExpertException(getClass(), 258, socketTimeoutException, "communication timeout error", NetworkExpertException.Type.ID_TIMEOUT);
                    } catch (Exception e4) {
                        exc = e4;
                        LogUtil.warning(exc);
                        throw new NetworkExpertException(getClass(), 259, exc, "communication other error", NetworkExpertException.Type.ID_OTHER_ERROR);
                    } catch (Throwable th2) {
                        th = th2;
                        obj = null;
                        inputStream = null;
                        r1 = outputStream;
                        r12 = obj;
                        r1.close();
                        inputStream.close();
                        r12.close();
                        disconnect();
                        throw th;
                    }
                }
                this.urlconn.connect();
                if (networkAccessListener != null) {
                    networkAccessListener.session();
                }
                responseCode = this.urlconn.getResponseCode();
                map = new HashMap();
                for (String str2 : this.urlconn.getHeaderFields().keySet()) {
                    if (str2 != null) {
                        String lowerCase = str2.toLowerCase(Locale.US);
                        map.put(lowerCase, this.urlconn.getHeaderField(lowerCase));
                    }
                }
                i2 = (map.get(CONTENT_LENGTH_KEY) == null || Integer.parseInt((String) map.get(CONTENT_LENGTH_KEY)) <= 0) ? 0 : Integer.parseInt((String) map.get(CONTENT_LENGTH_KEY));
                if (i > 0) {
                    i2 /= i;
                }
                inputStream = this.urlconn.getInputStream();
            } catch (Throwable th3) {
                r1 = request;
                th = th3;
                inputStream = null;
                r12 = networkAccessListener;
            }
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                int i3 = 0;
                while (!this.isDisconnect) {
                    try {
                        int i4 = inputStream.read();
                        if (i4 != -1) {
                            byteArrayOutputStream.write(i4);
                            int size = byteArrayOutputStream.size();
                            communicationDataMaxSizeCheck(size);
                            if (networkAccessListener != null) {
                                int i5 = i3 + 1;
                                if (size >= i2 * i5) {
                                    networkAccessListener.receiveRatio(i5);
                                    i3 = i5;
                                }
                            }
                        } else {
                            response = new Response(responseCode, map, byteArrayOutputStream.toByteArray());
                            try {
                                outputStream.close();
                            } catch (Exception unused4) {
                            }
                            try {
                                inputStream.close();
                            } catch (Exception unused5) {
                            }
                            try {
                                byteArrayOutputStream.close();
                            } catch (Exception unused6) {
                            }
                            disconnect();
                        }
                    } catch (NetworkExpertException e5) {
                        e = e5;
                        inputStream2 = inputStream;
                        LogUtil.warning(e);
                        throw e;
                    } catch (InterruptedException e6) {
                        interruptedException = e6;
                        LogUtil.warning(interruptedException);
                        throw new NetworkExpertException(getClass(), 257, interruptedException, "communication interruption", NetworkExpertException.Type.ID_CANCELED);
                    } catch (SocketTimeoutException e7) {
                        socketTimeoutException = e7;
                        LogUtil.warning(socketTimeoutException);
                        throw new NetworkExpertException(getClass(), 258, socketTimeoutException, "communication timeout error", NetworkExpertException.Type.ID_TIMEOUT);
                    } catch (Exception e8) {
                        exc = e8;
                        LogUtil.warning(exc);
                        throw new NetworkExpertException(getClass(), 259, exc, "communication other error", NetworkExpertException.Type.ID_OTHER_ERROR);
                    } catch (Throwable th4) {
                        th = th4;
                        r1 = outputStream;
                        r12 = byteArrayOutputStream;
                        r1.close();
                        inputStream.close();
                        r12.close();
                        disconnect();
                        throw th;
                    }
                }
                this.isDisconnect = false;
                throw new InterruptedException();
            } catch (NetworkExpertException e9) {
                e = e9;
                byteArrayOutputStream = null;
            } catch (InterruptedException e10) {
                interruptedException = e10;
                LogUtil.warning(interruptedException);
                throw new NetworkExpertException(getClass(), 257, interruptedException, "communication interruption", NetworkExpertException.Type.ID_CANCELED);
            } catch (SocketTimeoutException e11) {
                socketTimeoutException = e11;
                LogUtil.warning(socketTimeoutException);
                throw new NetworkExpertException(getClass(), 258, socketTimeoutException, "communication timeout error", NetworkExpertException.Type.ID_TIMEOUT);
            } catch (Exception e12) {
                exc = e12;
                LogUtil.warning(exc);
                throw new NetworkExpertException(getClass(), 259, exc, "communication other error", NetworkExpertException.Type.ID_OTHER_ERROR);
            } catch (Throwable th5) {
                th = th5;
                obj = null;
                r1 = outputStream;
                r12 = obj;
                r1.close();
                inputStream.close();
                r12.close();
                disconnect();
                throw th;
            }
        } catch (NetworkExpertException e13) {
            e = e13;
            outputStream = null;
            byteArrayOutputStream = null;
        } catch (InterruptedException e14) {
            interruptedException = e14;
        } catch (SocketTimeoutException e15) {
            socketTimeoutException = e15;
        } catch (Exception e16) {
            exc = e16;
        } catch (Throwable th6) {
            th = th6;
            r12 = 0;
            inputStream = null;
        }
        return response;
    }

    public void disconnect() {
        this.isDisconnect = true;
        HttpURLConnection httpURLConnection = this.urlconn;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    public VersionUpProtocol getVersionUpProtocol() {
        return new VersionUpProtocol(this);
    }

    public TosVersionProtocol getTosVersionProtocol() {
        return new TosVersionProtocol(this);
    }

    public BannerFileProtocol getBannerFileProtocol() {
        return new BannerFileProtocol(this);
    }

    public BannerImageProtocol getBannerImageProtocol() {
        return new BannerImageProtocol(this);
    }

    public IdsUpdateCheckProtocol getIdsUpdateCheckProtocol() {
        return new IdsUpdateCheckProtocol(this);
    }

    public ServiceIdsProtocol getServiceIdsProtocol() {
        return new ServiceIdsProtocol(this);
    }

    public ServiceProtocol getServiceProtocol() {
        return new ServiceProtocol(this);
    }

    public BookmarkProtocol getBookmarkProtocol() {
        return new BookmarkProtocol(this);
    }

    public SiteAccessProtocol getSiteAccessProtocol() {
        return new SiteAccessProtocol(this);
    }

    public FpServiceProtocol getFpServiceProtocol() {
        return new FpServiceProtocol(this);
    }

    public RegisterDeviceProtocol getRegisterDeviceProtocol() {
        return new RegisterDeviceProtocol(this);
    }

    public RenewDeviceProtocol getRenewDeviceProtocol() {
        return new RenewDeviceProtocol(this);
    }

    public RegisterTagProtocol getRegisterTagProtocol() {
        return new RegisterTagProtocol(this);
    }

    public RegisterEventProtocol getRegisterEventProtocol() {
        return new RegisterEventProtocol(this);
    }

    public ImageProtocol getImageProtocol() {
        return new ImageProtocol(this);
    }

    public OsaifulifePlusImageProtocol getOsaifulifePlusImageProtocol() {
        return new OsaifulifePlusImageProtocol(this);
    }

    UserAgent getUserAgent() {
        return this._ua;
    }

    ModelContext getModelContext() {
        return this._modelContext;
    }

    public NetworkExpert(ModelContext modelContext) {
        this.isDisconnect = false;
        this.urlconn = null;
        this._modelContext = modelContext;
        this._ua = new UserAgent();
        this.communication_data_reception_maximum_size = ((Integer) Sg.getValue(Sg.Key.NET_COMMUNICATION_DATA_RECEPTION_MAXIMUM_SIZE)).intValue() * 1024 * 1024;
    }

    protected NetworkExpert() {
        this.isDisconnect = false;
        this.urlconn = null;
    }

    private void communicationDataMaxSizeCheck(int i) throws NetworkExpertException {
        if (i > this.communication_data_reception_maximum_size) {
            throw new NetworkExpertException(getClass(), 257, "received data maximum size over", NetworkExpertException.Type.ID_COMMUNICATION_DATA_MAXIMUM_SIZE_ORVER_ERROR);
        }
    }

    private URL getUrl(Request request) throws Exception {
        URI uri;
        if (request.method.equals(CONNECT_TYPE_GET)) {
            request.header.remove(CONTENT_LENGTH_KEY);
            if (request.data != null) {
                uri = new URI(request.url + "?" + new String(request.data, "UTF-8"));
            } else {
                uri = new URI(request.url);
            }
        } else {
            uri = new URI(request.url);
        }
        return uri.normalize().toURL();
    }
}
