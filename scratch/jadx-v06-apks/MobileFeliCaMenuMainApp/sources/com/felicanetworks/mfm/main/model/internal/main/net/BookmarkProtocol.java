package com.felicanetworks.mfm.main.model.internal.main.net;

import android.os.Build;
import androidx.core.view.InputDeviceCompat;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.fix.Information;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class BookmarkProtocol extends Protocol {

    public static class Parameter {
        public static final String INTERFACE_VERSION = "03";
        public String idm;
        public String bookmarkDistKind = (String) Sg.getValue(Sg.Key.SETTING_PREFIX_FELICA_SERVICE);
        public String plKind = (String) Sg.getValue(Sg.Key.SETTING_PLATFORM_TYPE);
        public String appId = (String) Sg.getValue(Sg.Key.SETTING_MFM_ID);
        public String isCode = (String) Sg.getValue(Sg.Key.FELICA_MFC_ISSUER_CODE);
        public String pfVersion = Build.VERSION.RELEASE;
        public String appVersion = Information.simpleVersionName();
        public String languageCode = Locale.getDefault().getLanguage();
        public String countryCode = Locale.getDefault().getCountry();

        public Parameter(String str) {
            this.idm = str;
        }

        public String toString() {
            return "Parameter{plKind='" + this.plKind + "', pfVersion='" + this.pfVersion + "', isCode='" + this.isCode + "', languageCode='" + this.languageCode + "', countryCode='" + this.countryCode + "', appId='" + this.appId + "', appVersion='" + this.appVersion + "', idm='" + this.idm + "', bookmarkDistKind='" + this.bookmarkDistKind + "'}";
        }
    }

    public static class Result {
        public static final String BOOKMARK_KIND_BROWSER = "1";
        public static final String BOOKMARK_KIND_MARKET = "2";
        public static final String COOP_KIND_APP = "2";
        public static final String COOP_KIND_SRV = "3";
        public List<BookmarkCategoryItem> result;

        public static class BookmarkCategoryItem {
            public List<BookmarkItem> bookmarkItemList;
            public String categoryId;
            public String categoryTitle;

            public BookmarkCategoryItem(String str, String str2, List<BookmarkItem> list) {
                this.categoryId = str;
                this.categoryTitle = str2;
                this.bookmarkItemList = list;
            }

            public String toString() {
                return "BookmarkCategoryItemList{categoryId:" + this.categoryId + ",categoryTitle:" + this.categoryTitle + ",bookmarkItemList:" + this.bookmarkItemList + "}";
            }
        }

        private Result(List<BookmarkCategoryItem> list) {
            this.result = list;
        }

        public String toString() {
            return "Result{result=" + this.result + '}';
        }

        public static class BookmarkItem implements ServiceIdPolicy.ServiceIdHolder {
            public String bookmarkApKind;
            public String bookmarkApUrl;
            public String bookmarkCategoryId;
            public String bookmarkCategoryName;
            public String bookmarkCooperKind;
            public String bookmarkStatusKind;
            public String bookmarkWebUrl;
            public String recommendedDetail;
            public String recommendedOverview;
            public String serviceId;
            public String serviceName;
            public String serviceProviderName;
            public String serviceVersion;
            public List<String> useProcedureList;

            public BookmarkItem(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, List<String> list, String str12, String str13) {
                this.serviceId = str;
                this.serviceVersion = str2;
                this.serviceName = str3;
                this.serviceProviderName = str4;
                this.bookmarkStatusKind = str5;
                this.bookmarkCooperKind = str6;
                this.bookmarkApKind = str7;
                this.bookmarkApUrl = str8;
                this.bookmarkWebUrl = str9;
                this.bookmarkCategoryId = str12;
                this.bookmarkCategoryName = str13;
                this.recommendedOverview = str10;
                this.recommendedDetail = str11;
                this.useProcedureList = list;
            }

            public String toString() {
                return "BookmarkItem{serviceId='" + this.serviceId + "', serviceVersion='" + this.serviceVersion + "', bookmarkCategoryId='" + this.bookmarkCategoryId + "', bookmarkCategoryName='" + this.bookmarkCategoryName + "', serviceName='" + this.serviceName + "', serviceProviderName='" + this.serviceProviderName + "', bookmarkStatusKind='" + this.bookmarkStatusKind + "', bookmarkCooperKind='" + this.bookmarkCooperKind + "', bookmarkApKind='" + this.bookmarkApKind + "', bookmarkApUrl='" + this.bookmarkApUrl + "', bookmarkWebUrl='" + this.bookmarkWebUrl + "', recommendedOverview='" + this.recommendedOverview + "', recommendedDetail='" + this.recommendedDetail + "', useProcedureList=" + this.useProcedureList + '}';
            }

            @Override // com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.ServiceIdHolder
            public String sid() {
                return this.serviceId;
            }
        }
    }

    public NetworkExpert.Request create(Parameter parameter) throws NetworkExpertException {
        try {
            String str = (String) Sg.getValue(Sg.Key.NET_SIM_BK_URL);
            int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_SIM_BK_SESSION_TIMEOUT)).intValue();
            int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_SIM_BK_READ_TIMEOUT)).intValue();
            parameter.pfVersion = URLEncoder.encode(parameter.pfVersion, StringUtil.UTF_8);
            DataCheckerUtil.checkLessEqualLength(parameter.appVersion.length(), 8);
            DataCheckerUtil.checkAlphaSignFormat(parameter.appVersion);
            try {
                DataCheckerUtil.checkAlphaNumberFormat(parameter.languageCode);
                DataCheckerUtil.checkAlphaNumberFormat(parameter.countryCode);
                DataCheckerUtil.checkLessEqualLength(parameter.languageCode.length(), 3);
                DataCheckerUtil.checkLessEqualLength(parameter.countryCode.length(), 3);
            } catch (DataCheckerException unused) {
                parameter.languageCode = "ja";
                parameter.countryCode = "JP";
            }
            byte[] bytes = ("pt=" + parameter.plKind + "&pv=" + parameter.pfVersion + "&i=" + parameter.isCode + "&l=" + parameter.languageCode + "&c=" + parameter.countryCode + "&ai=" + parameter.appId + "&av=" + parameter.appVersion + "&idm=" + parameter.idm + "&bdt=" + parameter.bookmarkDistKind + "&ifv=03").getBytes(StringUtil.UTF_8);
            HashMap map = new HashMap();
            map.put("user-agent", getUserAgent());
            map.put("content-length", Integer.toString(bytes.length));
            return new NetworkExpert.Request(str, "GET", map, iIntValue, iIntValue2, bytes);
        } catch (Exception e) {
            throw new NetworkExpertException(getClass(), 257, e, "bookmark information request message generation failed.", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v2, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r6v21 */
    /* JADX WARN: Type inference failed for: r6v22 */
    /* JADX WARN: Type inference failed for: r6v23 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v6, types: [int] */
    /* JADX WARN: Type inference failed for: r6v9 */
    public Result parse(NetworkExpert.Response response) throws NetworkExpertException {
        ?? r6;
        DataCheckerException dataCheckerException;
        ?? r62;
        ?? r63;
        ArrayList arrayList;
        JSONArray jSONArray;
        String str;
        Result.BookmarkItem bookmarkItem;
        ArrayList arrayList2;
        ArrayList arrayList3 = new ArrayList();
        try {
            try {
                r6 = response.code;
                try {
                } catch (DataCheckerException e) {
                    e = e;
                }
            } catch (DataCheckerException e2) {
                e = e2;
                r6 = arrayList3;
            }
            if (r6 != 200) {
                throw new NetworkExpertException(getClass(), InputDeviceCompat.SOURCE_DPAD, "a response cord is unjust :", NetworkExpertException.Type.ID_COMMUNICATION_ERROR);
            }
            JSONArray jSONArray2 = new JSONArray(new String(response.data, StringUtil.UTF_8));
            int i = 0;
            while (i < jSONArray2.length()) {
                JSONObject jSONObject = jSONArray2.getJSONObject(i);
                String string = jSONObject.getString("bci");
                DataCheckerUtil.checkEqualLength(string.length(), 2);
                DataCheckerUtil.checkDecNumberFormat(string);
                String string2 = jSONObject.getString("bcn");
                DataCheckerUtil.checkLessEqualLength(string2.length(), 64);
                ArrayList arrayList4 = new ArrayList();
                JSONArray jSONArray3 = new JSONArray(jSONObject.getString("bi"));
                int i2 = 0;
                while (i2 < jSONArray3.length()) {
                    try {
                        JSONObject jSONObject2 = jSONArray3.getJSONObject(i2);
                        String string3 = jSONObject2.getString("si");
                        DataCheckerUtil.checkEqualLength(string3.length(), 8);
                        DataCheckerUtil.checkAlphaNumberFormat(string3);
                        String string4 = jSONObject2.getString("sv");
                        JSONArray jSONArray4 = jSONArray2;
                        DataCheckerUtil.checkEqualLength(string4.length(), 8);
                        DataCheckerUtil.checkDecNumberFormat(string4);
                        String string5 = jSONObject2.getString("sn");
                        String str2 = string2;
                        DataCheckerUtil.checkLessEqualLength(string5.length(), 64);
                        String string6 = jSONObject2.getString("pn");
                        JSONArray jSONArray5 = jSONArray3;
                        DataCheckerUtil.checkLessEqualLength(string6.length(), 64);
                        String string7 = jSONObject2.getString("bst");
                        DataCheckerUtil.checkEqualLength(string7.length(), 8);
                        DataCheckerUtil.checkDecNumberFormat(string7);
                        String strReplaceAll = jSONObject2.getString("sm").replaceAll("\\\\n", "\n");
                        DataCheckerUtil.checkLessEqualLength(strReplaceAll.length(), 512);
                        String strReplaceAll2 = jSONObject2.getString("dt").replaceAll("\\\\n", "\n");
                        DataCheckerUtil.checkLessEqualLength(strReplaceAll2.length(), 512);
                        JSONArray jSONArray6 = new JSONArray(jSONObject2.getString("ps"));
                        ArrayList arrayList5 = new ArrayList();
                        ArrayList arrayList6 = arrayList4;
                        int i3 = i;
                        int length = 5;
                        if (jSONArray6.length() <= 5) {
                            length = jSONArray6.length();
                        }
                        ArrayList arrayList7 = arrayList3;
                        int length2 = 0;
                        int i4 = 0;
                        while (i4 < length) {
                            int i5 = length;
                            try {
                                String strReplaceAll3 = jSONArray6.getString(i4).replaceAll("\\\\n", "\n");
                                length2 += strReplaceAll3.length();
                                arrayList5.add(strReplaceAll3);
                                i4++;
                                length = i5;
                            } catch (DataCheckerException e3) {
                                dataCheckerException = e3;
                                r63 = arrayList7;
                            }
                        }
                        DataCheckerUtil.checkLessEqualLength(length2, 1024);
                        String string8 = jSONObject2.getString("blt");
                        DataCheckerUtil.checkFixValue(string8, new String[]{"2", "3"});
                        if (string8.equals("2")) {
                            String string9 = jSONObject2.getString("bat");
                            DataCheckerUtil.checkFixValue(string9, new String[]{"1", "2"});
                            String string10 = jSONObject2.getString("bau");
                            DataCheckerUtil.checkLessEqualLength(string10.getBytes(StringUtil.UTF_8).length, 255);
                            DataCheckerUtil.checkUrlCharFormat(string10);
                            jSONArray = jSONArray5;
                            bookmarkItem = new Result.BookmarkItem(string3, string4, string5, string6, string7, string8, string9, string10, "", strReplaceAll, strReplaceAll2, arrayList5, string, str2);
                            str = str2;
                            arrayList2 = arrayList6;
                        } else {
                            jSONArray = jSONArray5;
                            str = str2;
                            String string11 = jSONObject2.getString("bsu");
                            DataCheckerUtil.checkLessEqualLength(string11.getBytes(StringUtil.UTF_8).length, 255);
                            DataCheckerUtil.checkUrlCharFormat(string11);
                            arrayList2 = arrayList6;
                            bookmarkItem = new Result.BookmarkItem(string3, string4, string5, string6, string7, string8, "", "", string11, strReplaceAll, strReplaceAll2, arrayList5, string, str);
                        }
                        Result.BookmarkItem bookmarkItem2 = bookmarkItem;
                        if (ServiceIdPolicy.isAllowed(bookmarkItem2)) {
                            arrayList2.add(bookmarkItem2);
                        }
                        i2++;
                        arrayList4 = arrayList2;
                        string2 = str;
                        jSONArray2 = jSONArray4;
                        i = i3;
                        arrayList3 = arrayList7;
                        jSONArray3 = jSONArray;
                    } catch (DataCheckerException e4) {
                        dataCheckerException = e4;
                        r63 = arrayList3;
                    }
                }
                JSONArray jSONArray7 = jSONArray2;
                ArrayList arrayList8 = arrayList3;
                int i6 = i;
                ArrayList arrayList9 = arrayList4;
                String str3 = string2;
                try {
                    if (arrayList9.isEmpty()) {
                        arrayList = arrayList8;
                    } else {
                        arrayList = arrayList8;
                        arrayList.add(new Result.BookmarkCategoryItem(string, str3, arrayList9));
                    }
                    i = i6 + 1;
                    arrayList3 = arrayList;
                    jSONArray2 = jSONArray7;
                } catch (DataCheckerException e5) {
                    e = e5;
                    r6 = arrayList8;
                }
            }
            r62 = arrayList3;
            return new Result(r62);
            dataCheckerException = e;
            r63 = r6;
            LogUtil.warning(dataCheckerException);
            if (dataCheckerException.getErrorId() == 0) {
                throw new NetworkExpertException(getClass(), 514, dataCheckerException, "bookmark information response message analysis data length fraud", NetworkExpertException.Type.ID_LENGTH_ERROR);
            }
            r62 = r63;
            if (1 == dataCheckerException.getErrorId()) {
                throw new NetworkExpertException(getClass(), 515, dataCheckerException, "bookmark information response message analysis data Attribute fraud", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
            }
            return new Result(r62);
        } catch (NetworkExpertException e6) {
            LogUtil.warning(e6);
            throw e6;
        } catch (Exception e7) {
            LogUtil.warning(e7);
            throw new NetworkExpertException(getClass(), 516, e7, "bookmark information response message analysis other error", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    protected BookmarkProtocol(NetworkExpert networkExpert) {
        super(networkExpert);
    }
}
