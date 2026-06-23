package com.felicanetworks.mfw.i.fbl;

import com.felicanetworks.mfw.i.cmn.ArrayList;
import com.felicanetworks.mfw.i.cmn.StringUtil;
import java.io.File;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class Property {
    public static final String APPLICATION_ID = "0201";
    public static final int COMMAND_RW_TRY_TIME = 25;
    public static final String COUNTRY_CODE = "JP";
    public static final HashMap<String, String> KEY_SET;
    public static final String LANGUAGE_CODE = "ja";
    public static final int MAX_COMMANDDATA_COUNT = 5;
    public static final String MSG_CARD_EXTERNAL_CANCEL = "処理中";
    public static final String MSG_CARD_EXTERNAL_PROCESSING = "処理中";
    public static final String MSG_DEFAULT_CARD_EXTERNAL_CATCH = "ケータイをかざしてください";
    public static final String MSG_DEFAULT_CARD_INTERNAL_PROCESSING = "処理中";
    public static final String MSG_PROCESSING = "処理中";
    public static final int OFFLINE_VERUP_EVAL_COUNT_LIMIT = 200;
    public static final int OFFLINE_VERUP_EVAL_TREM = 43200;
    public static final String PERMIT_TYPE = "02";
    public static final String PERMIT_VERSION = "0001";
    public static final byte[] PERM_AREA_FORMAT_VERSIONUP;
    public static final String PLATFORM_TYPE = "02";
    public static final int RVCTION_CACHE_NUMBER_LIMIT = 10;
    public static final int RVCTION_LIMIT = 99999999;
    public static final int RVCTION_SERIAL_NUMBER_LIMIT = 200;
    public static final int RVCTION_TERM = 43200;
    public static final String URL_CTRL_APP_DEL_SITE = "https://ots.fnrt.jp/~testuser2/site/delete_site.html";
    public static final String URL_CTRL_APP_EXPLAIN_SITE = "https://ots.fnrt.jp/~testuser2/site/detail_site.html";
    public static final String URL_PRBLM_ANALYZE_LOG_SEND = "https://ots.fnrt.jp/~testuser2/site/command/debuglog.php";
    public static final String URL_VERUP_CONFIR_REQ = "https://ots.fnrt.jp/~testuser2/site/command/verup.php";
    public static final String URL_VERUP_SITE = "";
    public static String sApplicationVersion;
    public static String sChipIssuerId;
    public static File sFileDir;
    public static String sUserAgent;
    private static final ArrayList PATH_LIST = new ArrayList();
    private static final ArrayList SERVICE_LIST = new ArrayList();

    static {
        add("foofoo.co.jp#00AA,00BB,00CC");
        add("path2#B,C");
        add("path3#A,C");
        add("idldev.fnrt.jp/start.jsp#12345678,456789aZ");
        sApplicationVersion = null;
        sChipIssuerId = null;
        PERM_AREA_FORMAT_VERSIONUP = new byte[]{2};
        sFileDir = null;
        KEY_SET = new HashMap<String, String>() { // from class: com.felicanetworks.mfw.i.fbl.Property.1
            private static final long serialVersionUID = -5784506449329077642L;

            {
                put("0000000000", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA09gP9b5uBIiW88fdLJvS03CZE3+d1ARs0adYZowX/1hFm1wv0QGS0ftu3aot3MFHCTtOvyRxqeP3asQRBnyJlyfi8WvMnknIS6xikLksVG793XtLC//RMKkD6AAkWwLgfU7E03rFvQQABxhCZYOCkpPYkpTogPAEW3+r+4zBQuA2tOU+nPqCKnhalz1OkGpKk1SFX5hP4QgMYhsoAskbFsqgrFS1JFhsqDPh9LXV+iK4yPr273e4Q5A1El+RO8tZ6URP5lAJD5lur7mTSJr3cyUGg+7YO1gt0PRz11gycnS86kt9mKMByVkuz9PBlCSAGM5xx9/qD7beu0KhtbRgZQIDAQAB");
            }
        };
        sUserAgent = null;
    }

    private static void add(String str) {
        try {
            String[] strArrCreateSeparated = StringUtil.createSeparated(str, "#");
            String str2 = strArrCreateSeparated[0];
            String[] strArrCreateSeparated2 = StringUtil.createSeparated(strArrCreateSeparated[1], ",");
            try {
                PATH_LIST.add(str2);
                SERVICE_LIST.add(strArrCreateSeparated2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception unused) {
        }
    }

    public static boolean containUrl(String str) {
        String strCreateTargetUrlStr = createTargetUrlStr(str);
        for (int i = 0; i < PATH_LIST.size(); i++) {
            if (((String) PATH_LIST.get(i)).equals(strCreateTargetUrlStr)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containUrlAndService(String str, String str2) {
        String strCreateTargetUrlStr = createTargetUrlStr(str);
        for (int i = 0; i < PATH_LIST.size(); i++) {
            if (((String) PATH_LIST.get(i)).equals(strCreateTargetUrlStr)) {
                for (String str3 : (String[]) SERVICE_LIST.get(i)) {
                    if (str2.equalsIgnoreCase(str3)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static String createTargetUrlStr(String str) {
        if (str.indexOf("http://") != -1) {
            String strSubstring = str.substring(7);
            return strSubstring.indexOf("?") != -1 ? strSubstring.substring(0, strSubstring.indexOf("?")) : strSubstring;
        }
        if (str.indexOf("https://") == -1) {
            return null;
        }
        String strSubstring2 = str.substring(8);
        return strSubstring2.indexOf("?") != -1 ? strSubstring2.substring(0, strSubstring2.indexOf("?")) : strSubstring2;
    }
}
