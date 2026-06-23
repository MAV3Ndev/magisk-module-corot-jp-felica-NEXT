package com.felicanetworks.semc;

/* JADX INFO: loaded from: classes3.dex */
public class SemClientExternalLogConst {
    public static final String MSG_FORMAT_ON_ERROR = "%s: onError(CODE=%d)";
    public static final String MSG_FORMAT_OTHER_EXCEPTION = "%s: %s";
    public static final String MSG_FORMAT_SEM_CLIENT_EXCEPTION = "%s: %s(CODE=%d)";
    public static final String TAG_WARN = "[SEMC_W]";

    public enum SemcApi {
        CONNECT("SemClient", SemClientApiInfo.METHOD_NAME_CONNECT),
        DISCONNECT("SemClient", SemClientApiInfo.METHOD_NAME_DISCONNECT),
        GET_SEID("SemClient", SemClientApiInfo.METHOD_NAME_GET_SEID),
        GET_SE_READER_NAME("SemClient", SemClientApiInfo.METHOD_NAME_GET_SE_READER_NAME),
        START_TSM_SEQUENCE("SemClient", SemClientApiInfo.METHOD_NAME_START_TSM_SEQUENCE),
        GET_CONTACTLESS_INTERFACE_STATUS("SemClient", SemClientApiInfo.METHOD_NAME_GET_CONTACTLESS_INTERFACE_STATUS),
        ACTIVATE_CONTACTLESS_INTERFACE("SemClient", SemClientApiInfo.METHOD_NAME_ACTIVATE_CONTACTLESS_INTERFACE),
        DEACTIVATE_CONTACTLESS_INTERFACE("SemClient", SemClientApiInfo.METHOD_NAME_DEACTIVATE_CONTACTLESS_INTERFACE),
        NOTIFY_CLIENT_EVENT("SemClient", SemClientApiInfo.METHOD_NAME_NOTIFY_CLIENT_EVENT),
        INSTALL_APPLET("SemClient", SemClientApiInfo.METHOD_NAME_INSTALL_APPLET),
        UPGRADE_APPLET("SemClient", SemClientApiInfo.METHOD_NAME_UPGRADE_APPLET),
        DELETE_APPLET("SemClient", SemClientApiInfo.METHOD_NAME_DELETE_APPLET),
        GET_APPLET_STATUS("SemClient", SemClientApiInfo.METHOD_NAME_GET_APPLET_STATUS);

        public final String className;
        public final String methodName;
        public final String msg;

        SemcApi(String str, String str2) {
            this.className = str;
            this.methodName = str2;
            this.msg = Integer.toHexString(str.hashCode()) + "#" + Integer.toHexString(str2.hashCode());
        }
    }
}
