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
import com.felicanetworks.semc.sws.SwsRequest;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
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

        public Parameter(String idm) {
            this.idm = idm;
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

            public BookmarkCategoryItem(String categoryId, String categoryTitle, List<BookmarkItem> bookmarkItemList) {
                this.categoryId = categoryId;
                this.categoryTitle = categoryTitle;
                this.bookmarkItemList = bookmarkItemList;
            }

            public String toString() {
                return "BookmarkCategoryItemList{categoryId:" + this.categoryId + ",categoryTitle:" + this.categoryTitle + ",bookmarkItemList:" + this.bookmarkItemList + "}";
            }
        }

        private Result(List<BookmarkCategoryItem> result) {
            this.result = result;
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

            public BookmarkItem(String serviceId, String serviceVersion, String serviceName, String serviceProviderName, String bookmarkStatusKind, String bookmarkCooperKind, String bookmarkApKind, String bookmarkApUrl, String bookmarkWebUrl, String recommendedOverview, String recommendedDetail, List<String> useProcedureList, String bookmarkCategoryId, String bookmarkCategoryName) {
                this.serviceId = serviceId;
                this.serviceVersion = serviceVersion;
                this.serviceName = serviceName;
                this.serviceProviderName = serviceProviderName;
                this.bookmarkStatusKind = bookmarkStatusKind;
                this.bookmarkCooperKind = bookmarkCooperKind;
                this.bookmarkApKind = bookmarkApKind;
                this.bookmarkApUrl = bookmarkApUrl;
                this.bookmarkWebUrl = bookmarkWebUrl;
                this.bookmarkCategoryId = bookmarkCategoryId;
                this.bookmarkCategoryName = bookmarkCategoryName;
                this.recommendedOverview = recommendedOverview;
                this.recommendedDetail = recommendedDetail;
                this.useProcedureList = useProcedureList;
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

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
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
            return new NetworkExpert.Request(str, SwsRequest.HTTP_METHOD_GET, map, iIntValue, iIntValue2, bytes);
        } catch (Exception e) {
            throw new NetworkExpertException(getClass(), 257, e, "bookmark information request message generation failed.", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    public Result parse(NetworkExpert.Response response) throws NetworkExpertException {
        JSONArray jSONArray;
        int i;
        Result.BookmarkItem bookmarkItem;
        String str;
        String str2;
        ArrayList arrayList = new ArrayList();
        try {
        } catch (DataCheckerException e) {
            LogUtil.warning(e);
            if (e.getErrorId() == 0) {
                throw new NetworkExpertException(getClass(), 514, e, "bookmark information response message analysis data length fraud", NetworkExpertException.Type.ID_LENGTH_ERROR);
            }
            if (1 == e.getErrorId()) {
                throw new NetworkExpertException(getClass(), 515, e, "bookmark information response message analysis data Attribute fraud", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
            }
        } catch (NetworkExpertException e2) {
            LogUtil.warning(e2);
            throw e2;
        } catch (Exception e3) {
            LogUtil.warning(e3);
            throw new NetworkExpertException(getClass(), 516, e3, "bookmark information response message analysis other error", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
        if (response.code != 200) {
            throw new NetworkExpertException(getClass(), InputDeviceCompat.SOURCE_DPAD, "a response cord is unjust :", NetworkExpertException.Type.ID_COMMUNICATION_ERROR);
        }
        JSONArray jSONArray2 = new JSONArray(new String(response.data, StringUtil.UTF_8));
        int i2 = 0;
        while (i2 < jSONArray2.length()) {
            JSONObject jSONObject = jSONArray2.getJSONObject(i2);
            String string = jSONObject.getString("bci");
            DataCheckerUtil.checkEqualLength(string.length(), 2);
            DataCheckerUtil.checkDecNumberFormat(string);
            String string2 = jSONObject.getString("bcn");
            int i3 = 64;
            DataCheckerUtil.checkLessEqualLength(string2.length(), 64);
            ArrayList arrayList2 = new ArrayList();
            JSONArray jSONArray3 = new JSONArray(jSONObject.getString("bi"));
            int i4 = 0;
            while (i4 < jSONArray3.length()) {
                JSONObject jSONObject2 = jSONArray3.getJSONObject(i4);
                String string3 = jSONObject2.getString("si");
                DataCheckerUtil.checkEqualLength(string3.length(), 8);
                DataCheckerUtil.checkAlphaNumberFormat(string3);
                String string4 = jSONObject2.getString("sv");
                DataCheckerUtil.checkEqualLength(string4.length(), 8);
                DataCheckerUtil.checkDecNumberFormat(string4);
                String string5 = jSONObject2.getString("sn");
                DataCheckerUtil.checkLessEqualLength(string5.length(), i3);
                String string6 = jSONObject2.getString("pn");
                JSONArray jSONArray4 = jSONArray2;
                DataCheckerUtil.checkLessEqualLength(string6.length(), i3);
                String string7 = jSONObject2.getString("bst");
                DataCheckerUtil.checkEqualLength(string7.length(), 8);
                DataCheckerUtil.checkDecNumberFormat(string7);
                String strReplaceAll = jSONObject2.getString("sm").replaceAll("\\\\n", "\n");
                DataCheckerUtil.checkLessEqualLength(strReplaceAll.length(), 512);
                String strReplaceAll2 = jSONObject2.getString("dt").replaceAll("\\\\n", "\n");
                DataCheckerUtil.checkLessEqualLength(strReplaceAll2.length(), 512);
                JSONArray jSONArray5 = new JSONArray(jSONObject2.getString("ps"));
                ArrayList arrayList3 = new ArrayList();
                int i5 = i2;
                int length = 5;
                if (jSONArray5.length() <= 5) {
                    length = jSONArray5.length();
                }
                int i6 = i4;
                int i7 = 0;
                int length2 = 0;
                while (i7 < length) {
                    int i8 = length;
                    String strReplaceAll3 = jSONArray5.getString(i7).replaceAll("\\\\n", "\n");
                    length2 += strReplaceAll3.length();
                    arrayList3.add(strReplaceAll3);
                    i7++;
                    length = i8;
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
                    jSONArray = jSONArray3;
                    i = 64;
                    bookmarkItem = new Result.BookmarkItem(string3, string4, string5, string6, string7, string8, string9, string10, "", strReplaceAll, strReplaceAll2, arrayList3, string, string2);
                    str = string;
                    str2 = string2;
                } else {
                    jSONArray = jSONArray3;
                    i = 64;
                    String string11 = jSONObject2.getString("bsu");
                    DataCheckerUtil.checkLessEqualLength(string11.getBytes(StringUtil.UTF_8).length, 255);
                    DataCheckerUtil.checkUrlCharFormat(string11);
                    bookmarkItem = new Result.BookmarkItem(string3, string4, string5, string6, string7, string8, "", "", string11, strReplaceAll, strReplaceAll2, arrayList3, string, string2);
                    str = string;
                    str2 = string2;
                }
                if (ServiceIdPolicy.isAllowed(bookmarkItem)) {
                    arrayList2.add(bookmarkItem);
                }
                jSONArray3 = jSONArray;
                string = str;
                string2 = str2;
                i4 = i6 + 1;
                jSONArray2 = jSONArray4;
                i2 = i5;
                i3 = i;
            }
            JSONArray jSONArray6 = jSONArray2;
            int i9 = i2;
            String str3 = string;
            String str4 = string2;
            if (!arrayList2.isEmpty()) {
                arrayList.add(new Result.BookmarkCategoryItem(str3, str4, arrayList2));
            }
            i2 = i9 + 1;
            jSONArray2 = jSONArray6;
        }
        return new Result(arrayList);
    }

    protected BookmarkProtocol(NetworkExpert net) {
        super(net);
    }
}
