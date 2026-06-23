package com.felicanetworks.common.cmnlib.log;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.PointerIconCompat;
import com.felicanetworks.common.cmnctrl.data.ErrorLogData;
import com.felicanetworks.common.cmnctrl.database.DatabaseAccess;
import com.felicanetworks.common.cmnctrl.database.DatabaseAccessException;
import com.felicanetworks.common.cmnlib.AppContext;
import com.felicanetworks.common.cmnlib.ExceptionCodeInterface;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.sg.SgMgr;
import com.felicanetworks.common.cmnlib.sg.SgMgrException;
import com.felicanetworks.common.cmnlib.util.CommonUtil;
import com.felicanetworks.common.cmnlib.util.DateFormatter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class LogMgr implements FunctionCodeInterface {
    private static final String APP_STC = "STC";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DateFormatter.DATE_TIME_MSEC);
    private static final int EXCEPTION_ATTR = 1024;
    private static final int EXP_MESSAGE_DIGITS_LINENUMBER = 4;
    private static final int EXP_MESSAGE_DISPLAY_COUNT = 3;
    private static final String EXP_MESSAGE_LINENUMBER_SEPARATOR = ":";
    private static final String EXP_MESSAGE_METHOD_SEPARATOR = "#";
    private static final String FIELD_SEPARATOR = ",";
    private static final int IDENTIFIRECODE_DIGITS_APPVERCODE = 4;
    private static final int IDENTIFIRECODE_DIGITS_CLASSID = 3;
    private static final int IDENTIFIRECODE_DIGITS_EXPID = 3;
    private static final int IDENTIFIRECODE_DIGITS_FUNCID = 2;
    private static final int IDENTIFIRECODE_DIGITS_LINENUMBER = 4;
    private static final int IDENTIFIRECODE_NONE_ID = 0;
    private static final String IDENTIFIRECODE_SEPARATOR = "-";
    private static final int IDENTIFIRECODE_UNKNOWN_ERR_ID = 4095;
    private static final String LOGDATA_CHARSET = "US-ASCII";
    private static final String LOGDATA_OMISSION_CODE = "...";
    private static final int LOGGER_DIGITS_LINENUMBER = 4;
    private static final String LOGGER_LINENUMBER_SEPARATOR = ":";
    private static final String LOGGER_METHOD_SEPARATOR = "#";
    private static final int MAX_LOGDATA_SIZE = 1024;
    static final String MSG_SEND_ERR_NOTHING_LOG_DATA = "Error Report is nothing.";
    static final String MSG_SEND_ERR_REPORT_ILLEGALARGUMENT = "listener parameter is null.";
    static final String MSG_TIME_ZONE_CANT_GET = "Timezone hasn't been set.";
    private static final String RECORD_SEPARATOR = ",";
    private static final int RUNTIME_ATTR = 2048;
    private static String appName;
    private static int appVerCode;
    private static final Map<String, Integer> exceptionIdentifierCodeMap;
    private static final Map<String, Integer> runtimeExceptionIdentifierCodeMap;
    private AppContext context;
    DateFormatter dateFormatter;
    ErrReportSender serverClient;
    private DatabaseAccess db = null;
    private boolean validErrReport = true;

    public enum CatDev {
    }

    public enum CatExp {
        ERR,
        WAR
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 2;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 6;
    }

    protected AppContext getContext() {
        return this.context;
    }

    protected synchronized DatabaseAccess getDataAccess() throws DatabaseAccessException {
        if (this.db == null) {
            this.db = new DatabaseAccess(this.context);
        }
        return this.db;
    }

    public LogMgr(AppContext appContext) {
        this.context = appContext;
        this.serverClient = new ErrReportSender(appContext);
        try {
            this.dateFormatter = new DateFormatter(DateFormatter.DATE_TIME_MSEC, (String) appContext.sgMgr.getSgValue(SgMgr.KEY_SRV_TIMEZONE));
        } catch (SgMgrException unused) {
            throw new IllegalStateException(MSG_TIME_ZONE_CANT_GET);
        }
    }

    static {
        HashMap map = new HashMap();
        runtimeExceptionIdentifierCodeMap = map;
        map.put("ActivityNotFoundException", 2049);
        runtimeExceptionIdentifierCodeMap.put("AndroidRuntimeException", 2050);
        runtimeExceptionIdentifierCodeMap.put("AnnotationTypeMismatchException", 2051);
        runtimeExceptionIdentifierCodeMap.put("ArithmeticException", 2052);
        runtimeExceptionIdentifierCodeMap.put("ArrayStoreException", 2053);
        runtimeExceptionIdentifierCodeMap.put("BufferOverflowException", 2054);
        runtimeExceptionIdentifierCodeMap.put("BufferUnderflowException", 2055);
        runtimeExceptionIdentifierCodeMap.put("ClassCastException", 2056);
        runtimeExceptionIdentifierCodeMap.put("ConcurrentModificationException", 2057);
        runtimeExceptionIdentifierCodeMap.put("DOMException", 2058);
        runtimeExceptionIdentifierCodeMap.put("EmptyStackException", 2059);
        runtimeExceptionIdentifierCodeMap.put("EnumConstantNotPresentException", 2060);
        runtimeExceptionIdentifierCodeMap.put("GLException", 2061);
        runtimeExceptionIdentifierCodeMap.put("IllegalArgumentException", 2062);
        runtimeExceptionIdentifierCodeMap.put("IllegalMonitorStateException", 2063);
        runtimeExceptionIdentifierCodeMap.put("IllegalStateException", 2064);
        runtimeExceptionIdentifierCodeMap.put("IncompleteAnnotationException", 2065);
        runtimeExceptionIdentifierCodeMap.put("IndexOutOfBoundsException", 2066);
        runtimeExceptionIdentifierCodeMap.put("InflateException", 2067);
        runtimeExceptionIdentifierCodeMap.put("MalformedParameterizedTypeException", 2068);
        runtimeExceptionIdentifierCodeMap.put("MissingResourceException", 2069);
        runtimeExceptionIdentifierCodeMap.put("NegativeArraySizeException", 2070);
        runtimeExceptionIdentifierCodeMap.put("NoSuchElementException", 2071);
        runtimeExceptionIdentifierCodeMap.put("NullPointerException", 2072);
        runtimeExceptionIdentifierCodeMap.put("ParcelFormatException", 2073);
        runtimeExceptionIdentifierCodeMap.put("ParseException", 2074);
        runtimeExceptionIdentifierCodeMap.put("ProviderException", 2075);
        runtimeExceptionIdentifierCodeMap.put("RejectedExecutionException", 2076);
        runtimeExceptionIdentifierCodeMap.put("ActionException", 2077);
        runtimeExceptionIdentifierCodeMap.put("NotFoundException", 2078);
        runtimeExceptionIdentifierCodeMap.put("SQLException", 2079);
        runtimeExceptionIdentifierCodeMap.put("SecurityException", 2080);
        runtimeExceptionIdentifierCodeMap.put("StaleDataException", 2081);
        runtimeExceptionIdentifierCodeMap.put("BadSurfaceTypeException", 2082);
        runtimeExceptionIdentifierCodeMap.put("TimeFormatException", 2083);
        runtimeExceptionIdentifierCodeMap.put("TypeNotPresentException", 2084);
        runtimeExceptionIdentifierCodeMap.put("UndeclaredThrowableException", 2085);
        runtimeExceptionIdentifierCodeMap.put("UnsupportedDigestAlgorithmException", 2086);
        runtimeExceptionIdentifierCodeMap.put("UnsupportedOperationException", 2087);
        runtimeExceptionIdentifierCodeMap.put("BadTokenException", 2088);
        runtimeExceptionIdentifierCodeMap.put("AccessControlException", 2089);
        runtimeExceptionIdentifierCodeMap.put("AlreadyConnectedException", 2090);
        runtimeExceptionIdentifierCodeMap.put("ArrayIndexOutOfBoundsException", 2091);
        runtimeExceptionIdentifierCodeMap.put("BadParcelableException", 2092);
        runtimeExceptionIdentifierCodeMap.put("CancellationException", 2093);
        runtimeExceptionIdentifierCodeMap.put("CancelledKeyException", 2094);
        runtimeExceptionIdentifierCodeMap.put("ClosedSelectorException", 2095);
        runtimeExceptionIdentifierCodeMap.put("ConnectionPendingException", 2096);
        runtimeExceptionIdentifierCodeMap.put("CursorIndexOutOfBoundsException", 2097);
        runtimeExceptionIdentifierCodeMap.put("DuplicateFormatFlagsException", 2098);
        runtimeExceptionIdentifierCodeMap.put("FormatFlagsConversionMismatchException", 2099);
        runtimeExceptionIdentifierCodeMap.put("FormatterClosedException", 2100);
        runtimeExceptionIdentifierCodeMap.put("IllegalBlockingModeException", 2101);
        runtimeExceptionIdentifierCodeMap.put("IllegalCharsetNameException", 2102);
        runtimeExceptionIdentifierCodeMap.put("IllegalFormatCodePointException", 2103);
        runtimeExceptionIdentifierCodeMap.put("IllegalFormatConversionException", 2104);
        runtimeExceptionIdentifierCodeMap.put("IllegalFormatException", 2105);
        runtimeExceptionIdentifierCodeMap.put("IllegalFormatFlagsException", 2106);
        runtimeExceptionIdentifierCodeMap.put("IllegalFormatPrecisionException", 2107);
        runtimeExceptionIdentifierCodeMap.put("IllegalFormatWidthException", 2108);
        runtimeExceptionIdentifierCodeMap.put("IllegalSelectorException", 2109);
        runtimeExceptionIdentifierCodeMap.put("IllegalThreadStateException", 2110);
        runtimeExceptionIdentifierCodeMap.put("InputMismatchException", 2112);
        runtimeExceptionIdentifierCodeMap.put("InvalidMarkException", 2113);
        runtimeExceptionIdentifierCodeMap.put("InvalidParameterException", 2114);
        runtimeExceptionIdentifierCodeMap.put("MissingFormatArgumentException", 2115);
        runtimeExceptionIdentifierCodeMap.put("MissingFormatWidthException", 2116);
        runtimeExceptionIdentifierCodeMap.put("NoConnectionPendingException", 2117);
        runtimeExceptionIdentifierCodeMap.put("NonReadableChannelException", 2118);
        runtimeExceptionIdentifierCodeMap.put("NonWritableChannelException", 2119);
        runtimeExceptionIdentifierCodeMap.put("NotYetBoundException", 2120);
        runtimeExceptionIdentifierCodeMap.put("NotYetConnectedException", 2121);
        runtimeExceptionIdentifierCodeMap.put("NumberFormatException", 2122);
        runtimeExceptionIdentifierCodeMap.put("OverlappingFileLockException", 2123);
        runtimeExceptionIdentifierCodeMap.put("PatternSyntaxException", 2124);
        runtimeExceptionIdentifierCodeMap.put("ReadOnlyBufferException", 2125);
        runtimeExceptionIdentifierCodeMap.put("ReceiverCallNotAllowedException", 2126);
        runtimeExceptionIdentifierCodeMap.put("SQLiteAbortException", 2127);
        runtimeExceptionIdentifierCodeMap.put("SQLiteConstraintException", 2128);
        runtimeExceptionIdentifierCodeMap.put("SQLiteDatabaseCorruptException", 2129);
        runtimeExceptionIdentifierCodeMap.put("SQLiteDiskIOException", 2130);
        runtimeExceptionIdentifierCodeMap.put("SQLiteDoneException", 2131);
        runtimeExceptionIdentifierCodeMap.put("SQLiteException", 2132);
        runtimeExceptionIdentifierCodeMap.put("SQLiteFullException", 2133);
        runtimeExceptionIdentifierCodeMap.put("SQLiteMisuseException", 2134);
        runtimeExceptionIdentifierCodeMap.put("StringIndexOutOfBoundsException", 2135);
        runtimeExceptionIdentifierCodeMap.put("UnknownFormatConversionException", 2136);
        runtimeExceptionIdentifierCodeMap.put("UnknownFormatFlagsException", 2137);
        runtimeExceptionIdentifierCodeMap.put("UnresolvedAddressException", 2138);
        runtimeExceptionIdentifierCodeMap.put("UnsupportedAddressTypeException", 2139);
        runtimeExceptionIdentifierCodeMap.put("UnsupportedCharsetException", 2140);
        HashMap map2 = new HashMap();
        exceptionIdentifierCodeMap = map2;
        map2.put("Exception", Integer.valueOf(InputDeviceCompat.SOURCE_GAMEPAD));
        exceptionIdentifierCodeMap.put("AccountsException", 1026);
        exceptionIdentifierCodeMap.put("AuthenticatorException", 1027);
        exceptionIdentifierCodeMap.put("NetworkErrorException", 1028);
        exceptionIdentifierCodeMap.put("OperationCanceledException", 1029);
        exceptionIdentifierCodeMap.put("AclNotFoundException", 1030);
        exceptionIdentifierCodeMap.put("AndroidException", 1031);
        exceptionIdentifierCodeMap.put("BackingStoreException", 1032);
        exceptionIdentifierCodeMap.put("BrokenBarrierException", 1033);
        exceptionIdentifierCodeMap.put("CertificateException", 1034);
        exceptionIdentifierCodeMap.put("CertificateEncodingException", 1035);
        exceptionIdentifierCodeMap.put("CertificateExpiredException", 1036);
        exceptionIdentifierCodeMap.put("CertificateNotYetValidException", 1037);
        exceptionIdentifierCodeMap.put("CertificateParsingException", 1038);
        exceptionIdentifierCodeMap.put("ClassNotFoundException", 1039);
        exceptionIdentifierCodeMap.put("CloneNotSupportedException", 1040);
        exceptionIdentifierCodeMap.put("DataFormatException", 1041);
        exceptionIdentifierCodeMap.put("DateParseException", 1042);
        exceptionIdentifierCodeMap.put("DestroyFailedException", 1043);
        exceptionIdentifierCodeMap.put("ExecutionException", 1044);
        exceptionIdentifierCodeMap.put("GeneralSecurityException", 1045);
        exceptionIdentifierCodeMap.put("BadPaddingException", 1046);
        exceptionIdentifierCodeMap.put("CRLException", 1047);
        exceptionIdentifierCodeMap.put("CertPathBuilderException", 1048);
        exceptionIdentifierCodeMap.put("CertPathValidatorException", 1049);
        exceptionIdentifierCodeMap.put("CertStoreException", 1050);
        exceptionIdentifierCodeMap.put("DigestException", 1052);
        exceptionIdentifierCodeMap.put("ExemptionMechanismException", 1053);
        exceptionIdentifierCodeMap.put("IllegalBlockSizeException", 1054);
        exceptionIdentifierCodeMap.put("InvalidAlgorithmParameterException", 1056);
        exceptionIdentifierCodeMap.put("InvalidKeySpecException", 1057);
        exceptionIdentifierCodeMap.put("InvalidParameterSpecException", 1058);
        exceptionIdentifierCodeMap.put("KeyException", 1059);
        exceptionIdentifierCodeMap.put("KeyStoreException", 1060);
        exceptionIdentifierCodeMap.put("LoginException", 1061);
        exceptionIdentifierCodeMap.put("NoSuchAlgorithmException", 1062);
        exceptionIdentifierCodeMap.put("NoSuchPaddingException", 1063);
        exceptionIdentifierCodeMap.put("NoSuchProviderException", 1064);
        exceptionIdentifierCodeMap.put("ShortBufferException", 1065);
        exceptionIdentifierCodeMap.put("SignatureException", 1066);
        exceptionIdentifierCodeMap.put("UnrecoverableEntryException", 1067);
        exceptionIdentifierCodeMap.put("UnrecoverableKeyException", 1068);
        exceptionIdentifierCodeMap.put("InvalidKeyException", 1069);
        exceptionIdentifierCodeMap.put("KeyManagementException", 1070);
        exceptionIdentifierCodeMap.put("HttpException", 1071);
        exceptionIdentifierCodeMap.put("MethodNotSupportedException", 1072);
        exceptionIdentifierCodeMap.put("ProtocolException", 1073);
        exceptionIdentifierCodeMap.put("TunnelRefusedException", 1074);
        exceptionIdentifierCodeMap.put("AuthenticationException", 1075);
        exceptionIdentifierCodeMap.put("CircularRedirectException", 1076);
        exceptionIdentifierCodeMap.put("InvalidCredentialsException", 1077);
        exceptionIdentifierCodeMap.put("MalformedChallengeException", 1078);
        exceptionIdentifierCodeMap.put("MalformedCookieException", 1079);
        exceptionIdentifierCodeMap.put("NTLMEngineException", 1080);
        exceptionIdentifierCodeMap.put("NonRepeatableRequestException", 1081);
        exceptionIdentifierCodeMap.put("RedirectException", 1082);
        exceptionIdentifierCodeMap.put("UnsupportedHttpVersionException", 1083);
        exceptionIdentifierCodeMap.put("IOException", 1084);
        exceptionIdentifierCodeMap.put("CharConversionException", 1085);
        exceptionIdentifierCodeMap.put("CharacterCodingException", 1086);
        exceptionIdentifierCodeMap.put("ClientProtocolException", 1087);
        exceptionIdentifierCodeMap.put("ClosedChannelException", 1088);
        exceptionIdentifierCodeMap.put("ConnectionClosedException", 1089);
        exceptionIdentifierCodeMap.put("EOFException", 1090);
        exceptionIdentifierCodeMap.put("FileLockInterruptionException", 1091);
        exceptionIdentifierCodeMap.put("FileNotFoundException", 1092);
        exceptionIdentifierCodeMap.put("HttpRetryException", 1093);
        exceptionIdentifierCodeMap.put("InterruptedIOException", 1094);
        exceptionIdentifierCodeMap.put("InvalidPropertiesFormatException", 1095);
        exceptionIdentifierCodeMap.put("MalformedChunkCodingException", 1096);
        exceptionIdentifierCodeMap.put("MalformedURLException", 1097);
        exceptionIdentifierCodeMap.put("NoHttpResponseException", 1098);
        exceptionIdentifierCodeMap.put("ObjectStreamException", 1099);
        exceptionIdentifierCodeMap.put("SSLException", 1101);
        exceptionIdentifierCodeMap.put("SocketException", 1102);
        exceptionIdentifierCodeMap.put("SyncFailedException", 1103);
        exceptionIdentifierCodeMap.put("UTFDataFormatException", 1104);
        exceptionIdentifierCodeMap.put("UnknownHostException", 1105);
        exceptionIdentifierCodeMap.put("UnknownServiceException", 1106);
        exceptionIdentifierCodeMap.put("UnsupportedEncodingException", 1107);
        exceptionIdentifierCodeMap.put("ZipException", 1108);
        exceptionIdentifierCodeMap.put("AsynchronousCloseException", 1109);
        exceptionIdentifierCodeMap.put("BindException", 1110);
        exceptionIdentifierCodeMap.put("ClosedByInterruptException", 1111);
        exceptionIdentifierCodeMap.put("ConnectException", 1112);
        exceptionIdentifierCodeMap.put("ConnectTimeoutException", 1113);
        exceptionIdentifierCodeMap.put("ConnectionPoolTimeoutException", 1114);
        exceptionIdentifierCodeMap.put("HttpHostConnectException", 1115);
        exceptionIdentifierCodeMap.put("HttpResponseException", 1116);
        exceptionIdentifierCodeMap.put("InvalidClassException", 1117);
        exceptionIdentifierCodeMap.put("InvalidObjectException", 1118);
        exceptionIdentifierCodeMap.put("JarException", 1119);
        exceptionIdentifierCodeMap.put("MalformedInputException", 1120);
        exceptionIdentifierCodeMap.put("NoRouteToHostException", 1121);
        exceptionIdentifierCodeMap.put("NotActiveException", 1122);
        exceptionIdentifierCodeMap.put("NotSerializableException", 1123);
        exceptionIdentifierCodeMap.put("OptionalDataException", 1124);
        exceptionIdentifierCodeMap.put("PortUnreachableException", 1125);
        exceptionIdentifierCodeMap.put("SSLHandshakeException", 1126);
        exceptionIdentifierCodeMap.put("SSLKeyException", 1127);
        exceptionIdentifierCodeMap.put("SSLPeerUnverifiedException", 1128);
        exceptionIdentifierCodeMap.put("SSLProtocolException", 1129);
        exceptionIdentifierCodeMap.put("SocketTimeoutException", 1130);
        exceptionIdentifierCodeMap.put("StreamCorruptedException", 1131);
        exceptionIdentifierCodeMap.put("UnmappableCharacterException", 1132);
        exceptionIdentifierCodeMap.put("WriteAbortedException", 1133);
        exceptionIdentifierCodeMap.put("IllegalAccessException", 1134);
        exceptionIdentifierCodeMap.put("InstantiationException", 1135);
        exceptionIdentifierCodeMap.put("InterruptedException", 1136);
        exceptionIdentifierCodeMap.put("InvalidPreferencesFormatException", 1137);
        exceptionIdentifierCodeMap.put("InvocationTargetException", 1138);
        exceptionIdentifierCodeMap.put("JSONException", 1139);
        exceptionIdentifierCodeMap.put("LastOwnerException", 1140);
        exceptionIdentifierCodeMap.put("NoSuchFieldException", 1141);
        exceptionIdentifierCodeMap.put("NoSuchMethodException", 1142);
        exceptionIdentifierCodeMap.put("NotOwnerException", 1143);
        exceptionIdentifierCodeMap.put("OperationApplicationException", 1144);
        exceptionIdentifierCodeMap.put("ParseException", 1145);
        exceptionIdentifierCodeMap.put("ParserConfigurationException", 1146);
        exceptionIdentifierCodeMap.put("PrivilegedActionException", 1147);
        exceptionIdentifierCodeMap.put("SAXException", 1148);
        exceptionIdentifierCodeMap.put("SAXNotRecognizedException", 1149);
        exceptionIdentifierCodeMap.put("SAXNotSupportedException", 1150);
        exceptionIdentifierCodeMap.put("SAXParseException", 1152);
        exceptionIdentifierCodeMap.put("SQLException", 1153);
        exceptionIdentifierCodeMap.put("BatchUpdateException", 1154);
        exceptionIdentifierCodeMap.put("SQLWarning", 1155);
        exceptionIdentifierCodeMap.put("DataTruncation", 1156);
        exceptionIdentifierCodeMap.put("OutOfResourcesException", 1157);
        exceptionIdentifierCodeMap.put("TimeoutException", 1158);
        exceptionIdentifierCodeMap.put("TooManyListenersException", 1159);
        exceptionIdentifierCodeMap.put("URISyntaxException", 1160);
        exceptionIdentifierCodeMap.put("UnsupportedCallbackException", 1161);
        exceptionIdentifierCodeMap.put("XmlPullParserException", 1162);
        exceptionIdentifierCodeMap.put("FelicaException", 1163);
    }

    protected String getTagElement(CatExp catExp) {
        if (appName == null) {
            appName = this.context.sgMgr.getAppName();
        }
        return appName;
    }

    protected static int getExpIdentifierIDFromIdentifierCodeArray(Exception exc, Map<String, Integer> map) {
        Integer num = map.get(exc.getClass().getSimpleName());
        return num != null ? num.intValue() : IDENTIFIRECODE_UNKNOWN_ERR_ID;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected static int getExpIdentifierID(Exception exc) {
        if (exc instanceof ExceptionCodeInterface) {
            return ((ExceptionCodeInterface) exc).getExceptionCode();
        }
        if (exc instanceof RuntimeException) {
            return getExpIdentifierIDFromIdentifierCodeArray(exc, runtimeExceptionIdentifierCodeMap);
        }
        return getExpIdentifierIDFromIdentifierCodeArray(exc, exceptionIdentifierCodeMap);
    }

    protected static String getErrIdentifierCodeElement(FunctionCodeInterface functionCodeInterface, StackTraceElement stackTraceElement, Exception exc) {
        return CommonUtil.intToDecString(appVerCode, 4) + IDENTIFIRECODE_SEPARATOR + CommonUtil.intToHexString(functionCodeInterface.getFunctionCode(), 2) + IDENTIFIRECODE_SEPARATOR + CommonUtil.intToHexString(functionCodeInterface.getClassCode(), 3) + IDENTIFIRECODE_SEPARATOR + CommonUtil.intToDecString(stackTraceElement.getLineNumber(), 4) + IDENTIFIRECODE_SEPARATOR + CommonUtil.intToHexString(exc != null ? getExpIdentifierID(exc) : 0, 3);
    }

    protected String getDateElement() {
        return this.dateFormatter.getSystemTime();
    }

    protected static String getLoggerInfoElement(StackTraceElement stackTraceElement) {
        return CommonUtil.getClassShortName(stackTraceElement.getClassName()) + "#" + stackTraceElement.getMethodName() + ":" + CommonUtil.intToDecString(stackTraceElement.getLineNumber(), 4);
    }

    protected String getExpNameElement(Exception exc) {
        return exc == null ? "" : exc.getClass().getSimpleName();
    }

    protected String getExpMsgElement(Exception exc) {
        return exc == null ? "" : exc.getMessage();
    }

    protected static class StackTraceFormatter {
        StackTraceElement[] st;

        public StackTraceFormatter(StackTraceElement[] stackTraceElementArr) {
            this.st = stackTraceElementArr;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer("");
            for (int i = 0; i < 3 && i < this.st.length; i++) {
                stringBuffer.append("[" + i + "]" + CommonUtil.getClassShortName(this.st[i].getClassName()) + "#" + this.st[i].getMethodName() + ":" + CommonUtil.intToDecString(this.st[i].getLineNumber(), 4));
            }
            return stringBuffer.toString();
        }
    }

    protected static class LogFormatter {
        protected String tag = "";
        protected String date = "";
        protected String identifyCode = "";
        protected String loggerInfo = "";
        protected String expClassName = "";
        protected String expMsg = "";
        protected String dbgMsg = "";
        protected String dbgData = "";

        public String toString() {
            return this.tag + "," + this.date + "," + this.identifyCode + "," + this.loggerInfo + "," + this.expClassName + "," + this.expMsg + "," + this.dbgMsg + "," + this.dbgData;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String out(CatExp catExp, FunctionCodeInterface functionCodeInterface, Exception exc) {
        try {
            LogFormatter logFormatter = new LogFormatter();
            StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
            logFormatter.tag = getTagElement(catExp);
            appVerCode = getAppVersionCode(this.context.androidContext);
            if (catExp == CatExp.ERR) {
                logFormatter.identifyCode = getErrIdentifierCodeElement(functionCodeInterface, stackTraceElement, exc);
            } else {
                logFormatter.identifyCode = getErrIdentifierCodeElement(functionCodeInterface, stackTraceElement, null);
            }
            logFormatter.date = getDateElement();
            logFormatter.loggerInfo = getLoggerInfoElement(stackTraceElement);
            logFormatter.expClassName = getExpNameElement(exc);
            logFormatter.expMsg = getExpMsgElement(exc);
            logFormatter.dbgData = new StackTraceFormatter(exc.getStackTrace()).toString();
            String string = logFormatter.toString();
            if (catExp == CatExp.ERR && (exc instanceof RuntimeException) && this.validErrReport) {
                try {
                    getDataAccess().addErrorLog(string);
                } catch (Exception unused) {
                }
            }
            if (catExp != CatExp.ERR) {
                return null;
            }
            String str = logFormatter.identifyCode;
            return (!(exc instanceof ExceptionCodeInterface) || ((ExceptionCodeInterface) exc).getErrIdentifierCode() == null) ? str : ((ExceptionCodeInterface) exc).getErrIdentifierCode();
        } catch (Exception unused2) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static String getErrIdentifierCode(Context context, FunctionCodeInterface functionCodeInterface, Exception exc) {
        try {
            StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
            appVerCode = getAppVersionCode(context);
            String errIdentifierCodeElement = getErrIdentifierCodeElement(functionCodeInterface, stackTraceElement, exc);
            if (!(exc instanceof ExceptionCodeInterface) || ((ExceptionCodeInterface) exc).getErrIdentifierCode() == null) {
                return errIdentifierCodeElement;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(CommonUtil.intToDecString(appVerCode, 4));
            stringBuffer.append(IDENTIFIRECODE_SEPARATOR);
            stringBuffer.append(((ExceptionCodeInterface) exc).getErrIdentifierCode());
            return stringBuffer.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    public boolean isExistErrReport() {
        try {
            return !getDataAccess().getErrorLogData().isEmpty();
        } catch (Exception e) {
            this.out(CatExp.WAR, this, e);
            return false;
        }
    }

    public void deleteErrReport() {
        try {
            getDataAccess().cleanErrorInfo();
        } catch (Exception e) {
            out(CatExp.WAR, this, e);
        }
    }

    public void sendErrReport(LogMgrEventListener logMgrEventListener) throws LogMgrException {
        try {
            if (logMgrEventListener == null) {
                throw new IllegalArgumentException(MSG_SEND_ERR_REPORT_ILLEGALARGUMENT);
            }
            List<ErrorLogData> errorLogData = getDataAccess().getErrorLogData();
            if (errorLogData.isEmpty()) {
                throw new LogMgrException(MSG_SEND_ERR_NOTHING_LOG_DATA);
            }
            String str = null;
            Iterator<ErrorLogData> it = errorLogData.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ErrorLogData next = it.next();
                if (next.idm != null && !next.idm.equals("")) {
                    str = next.idm;
                    break;
                }
            }
            StringBuffer stringBuffer = new StringBuffer(1024);
            Iterator<ErrorLogData> it2 = errorLogData.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                ErrorLogData next2 = it2.next();
                if (stringBuffer.length() != 0) {
                    stringBuffer.append(",");
                }
                stringBuffer.append(URLEncoder.encode(next2.log, LOGDATA_CHARSET));
                if (stringBuffer.length() == 1024) {
                    break;
                }
                if (stringBuffer.length() > 1024) {
                    stringBuffer.delete(PointerIconCompat.TYPE_GRABBING, stringBuffer.length());
                    stringBuffer.append(LOGDATA_OMISSION_CODE);
                    break;
                }
            }
            this.serverClient.start(logMgrEventListener, str, stringBuffer.toString());
        } catch (LogMgrException e) {
            throw e;
        } catch (Exception e2) {
            throw new LogMgrException(e2, out(CatExp.WAR, this, e2));
        }
    }

    public void stopSendingErrReport() {
        this.serverClient.stop();
    }

    private static int getAppVersionCode(Context context) {
        int longVersionCode;
        int i = appVerCode;
        if (i != 0) {
            return i;
        }
        if (context == null) {
            return 0;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= 28) {
                longVersionCode = (int) packageInfo.getLongVersionCode();
            } else {
                longVersionCode = packageInfo.versionCode;
            }
            return longVersionCode;
        } catch (PackageManager.NameNotFoundException unused) {
            return 0;
        }
    }

    public void validateErrReport(boolean z) {
        this.validErrReport = z;
    }
}
