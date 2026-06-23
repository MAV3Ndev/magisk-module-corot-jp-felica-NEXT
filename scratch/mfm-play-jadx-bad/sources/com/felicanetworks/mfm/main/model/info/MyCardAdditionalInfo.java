package com.felicanetworks.mfm.main.model.info;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.felicanetworks.mfc.mfi.LogSender;
import com.felicanetworks.mfm.main.model.info.LinkageInfo;
import com.felicanetworks.mfm.main.model.info.TransitInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.model.internal.main.pkg.PackageExpert;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.agent.TransitPassInfoAgent;
import com.felicanetworks.semc.SemClientConst;
import com.google.android.gms.common.internal.ImagesContract;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class MyCardAdditionalInfo {
    private static final String SP_ERROR_MSG = "Sp Parse Error";
    private AdditionalInfo additionalInfo;
    private final String additionalInfoHash;
    private final String cardId;
    private final ModelContext modelContext;

    public MyCardAdditionalInfo(String cardId, ModelContext modelContext, JSONObject jsonObject, String additionalInfoHash) throws MfcException {
        this.cardId = cardId;
        this.modelContext = modelContext;
        this.additionalInfoHash = additionalInfoHash;
        try {
            this.additionalInfo = parse(jsonObject);
        } catch (JSONException e) {
            LogUtil.warning(e);
        }
    }

    public MyCardAdditionalInfo(String cardId, ModelContext modelContext, AdditionalInfo additionalInfo, String additionalInfoHash) {
        this.cardId = cardId;
        this.modelContext = modelContext;
        this.additionalInfo = additionalInfo;
        this.additionalInfoHash = additionalInfoHash;
    }

    public String toString() {
        return "MyCardAdditionalInfo{cardId='" + this.cardId + "', additionalInfo=" + this.additionalInfo + ", additionalInfoHash=" + this.additionalInfoHash + '}';
    }

    /* JADX WARN: Removed duplicated region for block: B:158:0x0279  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0291  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AdditionalInfo parse(JSONObject json) throws DataCheckerException, JSONException {
        CommonInfo commonInfo;
        String string;
        TransitInfo transitInfoTrafficParse;
        String str;
        String string2;
        ContactInfo contactInfo;
        String string3;
        String string4;
        String string5;
        String str2;
        String string6;
        LinkageApplicationInfo linkageApplicationInfo;
        LinkageInfo.LinkageKind linkageKind;
        String string7;
        String string8;
        String string9;
        String strDecodeBase64_URL_SAFE;
        String strValueOf;
        String string10;
        ArrayList arrayList = new ArrayList();
        try {
            string = json.getString("languageCode");
            try {
                try {
                    DataCheckerUtil.checkEqualLength(string.length(), 3);
                    DataCheckerUtil.checkAlphaNumberFormat(string);
                    JSONArray jSONArray = new JSONArray(json.getString("linkageInfoList"));
                    int i = 0;
                    int i2 = 0;
                    int i3 = 0;
                    while (i < jSONArray.length()) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        String strValueOf2 = String.valueOf(jSONObject.getInt("linkageKind"));
                        DataCheckerUtil.checkDecNumberFormat(strValueOf2);
                        DataCheckerUtil.checkEqualLength(strValueOf2.length(), 1);
                        String string11 = jSONObject.getString("linkageName");
                        DataCheckerUtil.checkLessEqualLength(string11.length(), 64);
                        if (TextUtils.equals(strValueOf2, "2") && i2 < 5) {
                            try {
                                JSONObject jSONObject2 = jSONObject.getJSONObject("linkageApplicationInfo");
                                try {
                                    try {
                                        string7 = jSONObject2.getString("appIconUrl");
                                    } catch (JSONException e) {
                                        e = e;
                                        string7 = null;
                                    }
                                    try {
                                        DataCheckerUtil.checkLessEqualLength(string7.length(), 2048);
                                        DataCheckerUtil.checkAlphaSignFormat(string7);
                                    } catch (JSONException e2) {
                                        e = e2;
                                        LogUtil.warning(e);
                                    }
                                    String str3 = string7;
                                    try {
                                        string8 = jSONObject2.getString("appIdentifiableInfo");
                                        try {
                                            DataCheckerUtil.checkLessEqualLength(string8.length(), 1024);
                                            DataCheckerUtil.checkAlphaSignFormat(string8);
                                        } catch (JSONException e3) {
                                            e = e3;
                                            LogUtil.warning(e);
                                        }
                                    } catch (JSONException e4) {
                                        e = e4;
                                        string8 = null;
                                    }
                                    String str4 = string8;
                                    try {
                                        try {
                                            string9 = jSONObject2.getString("appCallerInfo");
                                            try {
                                                DataCheckerUtil.checkLessEqualLength(string9.length(), 1024);
                                                DataCheckerUtil.checkAlphaSignFormat(string9);
                                            } catch (JSONException e5) {
                                                e = e5;
                                            }
                                            try {
                                                strDecodeBase64_URL_SAFE = decodeBase64_URL_SAFE(string9);
                                            } catch (JSONException e6) {
                                                e = e6;
                                                LogUtil.warning(e);
                                                strDecodeBase64_URL_SAFE = string9;
                                            }
                                        } catch (DataCheckerException e7) {
                                            e = e7;
                                            commonInfo = null;
                                            if (e.getMessage().equals(SP_ERROR_MSG)) {
                                            }
                                        }
                                    } catch (JSONException e8) {
                                        e = e8;
                                        string9 = null;
                                    }
                                    try {
                                        strValueOf = String.valueOf(jSONObject2.getInt("appGetKind"));
                                        try {
                                            DataCheckerUtil.checkDecNumberFormat(strValueOf);
                                            str2 = string;
                                            try {
                                                try {
                                                    DataCheckerUtil.checkEqualLength(strValueOf.length(), 1);
                                                } catch (DataCheckerException e9) {
                                                    e = e9;
                                                    string = str2;
                                                    commonInfo = null;
                                                    if (e.getMessage().equals(SP_ERROR_MSG)) {
                                                        throw new JSONException("Parse Error");
                                                    }
                                                    LogUtil.warning(e);
                                                    transitInfoTrafficParse = null;
                                                    if (arrayList.size() != 0) {
                                                    }
                                                    throw new JSONException("Parse Error");
                                                }
                                            } catch (JSONException e10) {
                                                e = e10;
                                                LogUtil.warning(e);
                                            }
                                        } catch (JSONException e11) {
                                            e = e11;
                                            str2 = string;
                                        }
                                    } catch (JSONException e12) {
                                        e = e12;
                                        str2 = string;
                                        strValueOf = null;
                                    }
                                    String str5 = strValueOf;
                                    try {
                                        string10 = jSONObject2.getString("appGetUrl");
                                        try {
                                            DataCheckerUtil.checkLessEqualLength(string10.length(), 2048);
                                            DataCheckerUtil.checkAlphaSignFormat(string10);
                                        } catch (JSONException e13) {
                                            e = e13;
                                            LogUtil.warning(e);
                                        }
                                    } catch (JSONException e14) {
                                        e = e14;
                                        string10 = null;
                                    }
                                    i2++;
                                    linkageApplicationInfo = new LinkageApplicationInfo(str3, str4, strDecodeBase64_URL_SAFE, str5, string10);
                                    string6 = null;
                                } catch (DataCheckerException e15) {
                                    e = e15;
                                    commonInfo = null;
                                    if (e.getMessage().equals(SP_ERROR_MSG)) {
                                    }
                                }
                            } catch (DataCheckerException e16) {
                                e = e16;
                                commonInfo = null;
                                if (e.getMessage().equals(SP_ERROR_MSG)) {
                                }
                            } catch (JSONException e17) {
                                e = e17;
                                commonInfo = null;
                                if (e.getMessage().equals(SP_ERROR_MSG)) {
                                }
                            }
                        } else {
                            str2 = string;
                            if ((TextUtils.equals(strValueOf2, "1") || TextUtils.equals(strValueOf2, "3")) && i3 < 5) {
                                string6 = jSONObject.getString("linkageWebsiteURL");
                                DataCheckerUtil.checkLessEqualLength(string6.length(), 2048);
                                DataCheckerUtil.checkAlphaSignFormat(string6);
                                i3++;
                                linkageApplicationInfo = null;
                            } else {
                                i++;
                                string = str2;
                            }
                        }
                        if ("2".equals(strValueOf2)) {
                            try {
                                linkageKind = LinkageInfo.LinkageKind.APP;
                            } catch (JSONException e18) {
                                e = e18;
                                string = str2;
                                commonInfo = null;
                                if (e.getMessage().equals(SP_ERROR_MSG)) {
                                }
                            }
                        } else {
                            linkageKind = LinkageInfo.LinkageKind.WEB;
                        }
                        arrayList.add(new LinkageInfo(linkageKind, string11, linkageApplicationInfo, string6));
                        i++;
                        string = str2;
                    }
                    str = string;
                    JSONObject jSONObject3 = json.getJSONObject("commonInformation");
                    JSONObject jSONObject4 = jSONObject3.getJSONObject("generalInformation");
                    String string12 = jSONObject4.getString("cardTypeName");
                    DataCheckerUtil.checkLessEqualLength(string12.length(), 64);
                    String string13 = jSONObject4.getString("cardArtUrl");
                    DataCheckerUtil.checkLessEqualLength(string13.length(), 2048);
                    DataCheckerUtil.checkAlphaSignFormat(string13);
                    try {
                        string2 = jSONObject4.getString("contactName");
                        try {
                            DataCheckerUtil.checkLessEqualLength(string2.length(), 64);
                        } catch (JSONException e19) {
                            e = e19;
                            LogUtil.warning(e);
                        }
                    } catch (JSONException e20) {
                        e = e20;
                        string2 = null;
                    }
                    String string14 = "";
                    try {
                        string14 = jSONObject4.getString("specialCardArtUrl");
                        if (!string14.isEmpty()) {
                            DataCheckerUtil.checkLessEqualLength(string14.length(), 2048);
                            DataCheckerUtil.checkAlphaSignFormat(string14);
                        }
                    } catch (JSONException e21) {
                        LogUtil.warning(e21);
                    }
                    if (jSONObject3.has("contact")) {
                        JSONObject jSONObject5 = jSONObject3.getJSONObject("contact");
                        String string15 = jSONObject5.getString("name");
                        DataCheckerUtil.checkLessEqualLength(string15.length(), 64);
                        try {
                            string3 = jSONObject5.getString("phoneNumber");
                            try {
                                DataCheckerUtil.checkLessEqualLength(string3.length(), 64);
                                DataCheckerUtil.checkAlphaSignFormat(string3);
                            } catch (JSONException e22) {
                                e = e22;
                                LogUtil.warning(e);
                            }
                        } catch (JSONException e23) {
                            e = e23;
                            string3 = null;
                        }
                        try {
                            string4 = jSONObject5.getString(ImagesContract.URL);
                            try {
                                DataCheckerUtil.checkLessEqualLength(string4.length(), 2048);
                                DataCheckerUtil.checkAlphaSignFormat(string4);
                            } catch (JSONException e24) {
                                e = e24;
                                LogUtil.warning(e);
                            }
                        } catch (JSONException e25) {
                            e = e25;
                            string4 = null;
                        }
                        try {
                            string5 = jSONObject5.getString("email");
                        } catch (JSONException e26) {
                            e = e26;
                            string5 = null;
                        }
                        try {
                            DataCheckerUtil.checkLessEqualLength(string5.length(), 256);
                            DataCheckerUtil.checkAlphaSignFormat(string5);
                        } catch (JSONException e27) {
                            e = e27;
                            LogUtil.warning(e);
                        }
                        if (string3 == null && string4 == null && string5 == null) {
                            throw new JSONException("Parse Error. No contact info.");
                        }
                        contactInfo = new ContactInfo(string15, string3, string4, string5);
                    } else {
                        contactInfo = null;
                    }
                    commonInfo = new CommonInfo(new GeneralInfo(string12, string13, string2, string14), contactInfo);
                } catch (DataCheckerException e28) {
                    e = e28;
                }
            } catch (JSONException e29) {
                e = e29;
            }
        } catch (DataCheckerException | JSONException e30) {
            e = e30;
            commonInfo = null;
            string = null;
        }
        try {
            transitInfoTrafficParse = trafficParse(json);
            string = str;
        } catch (DataCheckerException | JSONException e31) {
            e = e31;
            string = str;
            if (e.getMessage().equals(SP_ERROR_MSG)) {
            }
        }
        if (arrayList.size() != 0 || commonInfo == null) {
            throw new JSONException("Parse Error");
        }
        return new AdditionalInfo(string, arrayList, commonInfo, transitInfoTrafficParse);
    }

    public TransitInfo trafficParse(JSONObject json) throws JSONException {
        JSONArray jSONArray;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        Section1 section1;
        Section2 section2;
        String str7;
        String str8;
        String str9;
        String str10;
        String str11;
        String str12;
        TermOfValidity termOfValidity;
        String str13;
        String string;
        String string2;
        String string3;
        String string4;
        String str14;
        int i;
        String string5;
        String string6;
        String str15;
        String str16;
        String str17 = "termOfValidity";
        String str18 = "issuerName";
        String str19 = "via";
        String str20 = "section1";
        String str21 = "commuterPassName";
        String str22 = "category";
        if (!json.has("transitInformation")) {
            return null;
        }
        JSONObject jSONObject = json.getJSONObject("transitInformation");
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("cardNumber");
            String string7 = jSONObject2.getString("displayName");
            String str23 = "optionalInformation";
            int length = string7.length();
            String str24 = LogSender.EXTRA_VALUE_EVENT_NAME_START;
            int i2 = 128;
            DataCheckerUtil.checkLessEqualLength(length, 128);
            String string8 = jSONObject2.getString("value");
            DataCheckerUtil.checkLessEqualLength(string8.length(), 128);
            DataCheckerUtil.checkAlphaSignEmptyFormat(string8);
            TransitInfo.CardNumber cardNumber = new TransitInfo.CardNumber(string7, string8);
            ArrayList arrayList = new ArrayList();
            if (jSONObject.has("transitPassInformation")) {
                JSONArray jSONArray2 = jSONObject.getJSONArray("transitPassInformation");
                int i3 = 0;
                while (i3 < jSONArray2.length() && i3 < 5) {
                    JSONObject jSONObject3 = jSONArray2.getJSONObject(i3);
                    String string9 = "";
                    if (jSONObject3.has("transitPassName")) {
                        String string10 = jSONObject3.getString("transitPassName");
                        jSONArray = jSONArray2;
                        DataCheckerUtil.checkLessEqualLength(string10.length(), i2);
                        str = string10;
                    } else {
                        jSONArray = jSONArray2;
                        str = "";
                    }
                    if (jSONObject3.has(str22)) {
                        String string11 = jSONObject3.getString(str22);
                        DataCheckerUtil.checkLessEqualLength(string11.length(), i2);
                        str2 = string11;
                    } else {
                        str2 = "";
                    }
                    if (jSONObject3.has(str21)) {
                        String string12 = jSONObject3.getString(str21);
                        DataCheckerUtil.checkLessEqualLength(string12.length(), i2);
                        str3 = string12;
                    } else {
                        str3 = "";
                    }
                    if (jSONObject3.has(str20)) {
                        str5 = str21;
                        JSONObject jSONObject4 = jSONObject3.getJSONObject(str20);
                        if (jSONObject4.has("from")) {
                            String string13 = jSONObject4.getString("from");
                            str4 = str20;
                            str6 = str22;
                            DataCheckerUtil.checkLessEqualLength(string13.length(), 128);
                            str15 = string13;
                        } else {
                            str4 = str20;
                            str6 = str22;
                            str15 = null;
                        }
                        if (jSONObject4.has(TypedValues.TransitionType.S_TO)) {
                            String string14 = jSONObject4.getString(TypedValues.TransitionType.S_TO);
                            DataCheckerUtil.checkLessEqualLength(string14.length(), 128);
                            str16 = string14;
                        } else {
                            str16 = null;
                        }
                        section1 = new Section1(str15, str16);
                    } else {
                        str4 = str20;
                        str5 = str21;
                        str6 = str22;
                        section1 = null;
                    }
                    if (jSONObject3.has("section2")) {
                        JSONObject jSONObject5 = jSONObject3.getJSONObject("section2");
                        if (jSONObject5.has("from")) {
                            string5 = jSONObject5.getString("from");
                            DataCheckerUtil.checkLessEqualLength(string5.length(), 128);
                        } else {
                            string5 = null;
                        }
                        if (jSONObject5.has(TypedValues.TransitionType.S_TO)) {
                            string6 = jSONObject5.getString(TypedValues.TransitionType.S_TO);
                            DataCheckerUtil.checkLessEqualLength(string6.length(), 128);
                        } else {
                            string6 = null;
                        }
                        section2 = new Section2(string5, string6);
                    } else {
                        section2 = null;
                    }
                    if (jSONObject3.has(SemClientConst.SEM_CLIENT_VERSION_ADD_INFO_RESOURCE_NAME)) {
                        String string15 = jSONObject3.getString(SemClientConst.SEM_CLIENT_VERSION_ADD_INFO_RESOURCE_NAME);
                        DataCheckerUtil.checkLessEqualLength(string15.length(), 128);
                        str7 = string15;
                    } else {
                        str7 = "";
                    }
                    if (jSONObject3.has(str19)) {
                        String string16 = jSONObject3.getString(str19);
                        DataCheckerUtil.checkLessEqualLength(string16.length(), 128);
                        str8 = string16;
                    } else {
                        str8 = "";
                    }
                    if (jSONObject3.has(str18)) {
                        string9 = jSONObject3.getString(str18);
                        DataCheckerUtil.checkLessEqualLength(string9.length(), 128);
                    }
                    String str25 = string9;
                    boolean zHas = jSONObject3.has(str17);
                    String str26 = TransitPassInfoAgent.OPTIONAL_INFO_KEY;
                    if (zHas) {
                        JSONObject jSONObject6 = jSONObject3.getJSONObject(str17);
                        if (jSONObject6.has(TransitPassInfoAgent.OPTIONAL_INFO_KEY)) {
                            string3 = jSONObject6.getString(TransitPassInfoAgent.OPTIONAL_INFO_KEY);
                            DataCheckerUtil.checkLessEqualLength(string3.length(), 128);
                        } else {
                            string3 = null;
                        }
                        str11 = str24;
                        if (jSONObject6.has(str11)) {
                            string4 = jSONObject6.getString(str11);
                            str12 = str17;
                            str9 = str18;
                            DataCheckerUtil.checkLessEqualLength(string4.length(), 128);
                        } else {
                            str12 = str17;
                            str9 = str18;
                            string4 = null;
                        }
                        if (jSONObject6.has("end")) {
                            String string17 = jSONObject6.getString("end");
                            DataCheckerUtil.checkLessEqualLength(string17.length(), 128);
                            str14 = string17;
                        } else {
                            str14 = null;
                        }
                        if (jSONObject6.has("extension")) {
                            int i4 = jSONObject6.getInt("extension");
                            str10 = str19;
                            DataCheckerUtil.checkFixValue(String.valueOf(i4), new String[]{"0", "1"});
                            i = i4;
                        } else {
                            str10 = str19;
                            i = 0;
                        }
                        boolean z = true;
                        if (i != 1) {
                            z = false;
                        }
                        termOfValidity = new TermOfValidity(string3, string4, str14, z);
                    } else {
                        str9 = str18;
                        str10 = str19;
                        str11 = str24;
                        str12 = str17;
                        termOfValidity = null;
                    }
                    ArrayList arrayList2 = new ArrayList();
                    String str27 = str23;
                    if (jSONObject3.has(str27)) {
                        JSONArray jSONArray3 = jSONObject3.getJSONArray(str27);
                        int i5 = 0;
                        while (i5 < jSONArray3.length() && i5 < 10) {
                            JSONObject jSONObject7 = jSONArray3.getJSONObject(i5);
                            if (jSONObject7.has(str26)) {
                                string = jSONObject7.getString(str26);
                                str13 = str26;
                                DataCheckerUtil.checkLessEqualLength(string.length(), 128);
                            } else {
                                str13 = str26;
                                string = null;
                            }
                            if (jSONObject7.has("value")) {
                                string2 = jSONObject7.getString("value");
                                DataCheckerUtil.checkLessEqualLength(string2.length(), 128);
                            } else {
                                string2 = null;
                            }
                            arrayList2.add(new OptionalInfo(string, string2));
                            i5++;
                            str26 = str13;
                        }
                    }
                    arrayList.add(new TransitPassInfo(str, str2, str3, section1, section2, str7, str8, str25, termOfValidity, arrayList2));
                    i3++;
                    str23 = str27;
                    i2 = 128;
                    str17 = str12;
                    str18 = str9;
                    jSONArray2 = jSONArray;
                    str20 = str4;
                    str22 = str6;
                    str19 = str10;
                    str24 = str11;
                    str21 = str5;
                }
            }
            return new TransitInfo(cardNumber, arrayList);
        } catch (DataCheckerException unused) {
            throw new JSONException(SP_ERROR_MSG);
        }
    }

    public String getCid() {
        return this.cardId;
    }

    public AdditionalInfo getAdditionalInfo() {
        return this.additionalInfo;
    }

    public String getAdditionalInfoHash() {
        return this.additionalInfoHash;
    }

    private String decodeBase64_URL_SAFE(String data) throws DataCheckerException {
        byte[] bArrDecode = Base64.decode(data.getBytes(StandardCharsets.UTF_8), 8);
        StringBuilder sb = new StringBuilder();
        for (byte b : bArrDecode) {
            sb.append(String.format("%02X", Byte.valueOf(b)));
        }
        String string = sb.toString();
        DataCheckerUtil.checkHexNumberFormat(string);
        return string;
    }

    public List<Linkage> getLinkageInfoOtherList() {
        Intent defaultIntent;
        AdditionalInfo additionalInfo = this.additionalInfo;
        if (additionalInfo == null) {
            return new ArrayList();
        }
        List<LinkageInfo> list = additionalInfo.linkageInfoList;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (LinkageInfo.LinkageKind.WEB == list.get(i2).linkageKind && isVailedLinkageInfo(list.get(i2))) {
                arrayList.add(getLinkageInfo(i2));
                if (i > 5) {
                    break;
                }
                i++;
            }
        }
        if (this.additionalInfo.commonInformation != null && this.additionalInfo.commonInformation.contact != null && !TextUtils.isEmpty(this.additionalInfo.commonInformation.contact.name) && (defaultIntent = this.additionalInfo.commonInformation.contact.getDefaultIntent()) != null) {
            arrayList.add(new LinkageContact(defaultIntent, this.additionalInfo.commonInformation.contact.name));
        }
        return arrayList;
    }

    public List<Linkage> getLinkageInfoAppList() {
        AdditionalInfo additionalInfo = this.additionalInfo;
        if (additionalInfo == null) {
            return new ArrayList();
        }
        List<LinkageInfo> list = additionalInfo.linkageInfoList;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (LinkageInfo.LinkageKind.APP == list.get(i).linkageKind && isVailedLinkageInfo(list.get(i))) {
                arrayList.add(getLinkageInfo(i));
            }
        }
        return arrayList;
    }

    private boolean isVailedLinkageInfo(LinkageInfo info) {
        return !TextUtils.isEmpty(info.linkageName);
    }

    public Linkage getLinkageInfo(int position) {
        LinkageInfo linkageInfo;
        AdditionalInfo additionalInfo = this.additionalInfo;
        if (additionalInfo == null || additionalInfo.linkageInfoList == null || position < 0 || position >= this.additionalInfo.linkageInfoList.size() || (linkageInfo = this.additionalInfo.linkageInfoList.get(position)) == null) {
            return null;
        }
        if (linkageInfo.linkageKind == LinkageInfo.LinkageKind.APP) {
            if (linkageInfo.linkageApplicationInfo.appIcon != null) {
                return new LinkageApp(linkageInfo.linkageApplicationInfo.appIdentifiableInfo, linkageInfo.linkageApplicationInfo.appCallerInfo, linkageInfo.linkageApplicationInfo.appGetKind, linkageInfo.linkageApplicationInfo.appGetUrl, new PackageExpert(this.modelContext), linkageInfo.linkageName, "", null, linkageInfo.linkageApplicationInfo.appIcon);
            }
            return new LinkageApp(linkageInfo.linkageApplicationInfo.appIdentifiableInfo, linkageInfo.linkageApplicationInfo.appCallerInfo, linkageInfo.linkageApplicationInfo.appGetKind, linkageInfo.linkageApplicationInfo.appGetUrl, new PackageExpert(this.modelContext), linkageInfo.linkageName, linkageInfo.linkageApplicationInfo.appIconUrl, null, null);
        }
        if (TextUtils.isEmpty(linkageInfo.linkageName)) {
            return new LinkageWeb(linkageInfo.linkageWebsiteURL);
        }
        return new LinkageWeb(linkageInfo.linkageWebsiteURL, linkageInfo.linkageName);
    }
}
