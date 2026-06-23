package com.felicanetworks.mfm.main.model.info;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class MyCardAdditionalInfo {
    private static final String SP_ERROR_MSG = "Sp Parse Error";
    private AdditionalInfo additionalInfo;
    private String cardId;
    private ModelContext modelContext;

    public MyCardAdditionalInfo(String str, ModelContext modelContext, JSONObject jSONObject) throws MfcException {
        this.cardId = str;
        this.modelContext = modelContext;
        try {
            this.additionalInfo = parse(jSONObject);
        } catch (JSONException e) {
            LogUtil.warning(e);
        }
    }

    public MyCardAdditionalInfo(String str, ModelContext modelContext, AdditionalInfo additionalInfo) {
        this.cardId = str;
        this.modelContext = modelContext;
        this.additionalInfo = additionalInfo;
    }

    public String toString() {
        return "MyCardAdditionalInfo{cardId='" + this.cardId + "', additionalInfo=" + this.additionalInfo + '}';
    }

    /* JADX WARN: Removed duplicated region for block: B:151:0x025b  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0273  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mfm.main.model.info.AdditionalInfo parse(org.json.JSONObject r24) throws com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException, org.json.JSONException {
        /*
            Method dump skipped, instruction units count: 633
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.info.MyCardAdditionalInfo.parse(org.json.JSONObject):com.felicanetworks.mfm.main.model.info.AdditionalInfo");
    }

    public TransitInfo trafficParse(JSONObject jSONObject) throws JSONException {
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
        if (!jSONObject.has("transitInformation")) {
            return null;
        }
        JSONObject jSONObject2 = jSONObject.getJSONObject("transitInformation");
        try {
            JSONObject jSONObject3 = jSONObject2.getJSONObject("cardNumber");
            String string7 = jSONObject3.getString("displayName");
            String str23 = "optionalInformation";
            int length = string7.length();
            String str24 = LogSender.EXTRA_VALUE_EVENT_NAME_START;
            int i2 = 128;
            DataCheckerUtil.checkLessEqualLength(length, 128);
            String string8 = jSONObject3.getString(TransitPassInfoAgent.OPTIONAL_INFO_VALUE);
            DataCheckerUtil.checkLessEqualLength(string8.length(), 128);
            DataCheckerUtil.checkAlphaSignEmptyFormat(string8);
            TransitInfo.CardNumber cardNumber = new TransitInfo.CardNumber(string7, string8);
            ArrayList arrayList = new ArrayList();
            if (jSONObject2.has("transitPassInformation")) {
                JSONArray jSONArray2 = jSONObject2.getJSONArray("transitPassInformation");
                int i3 = 0;
                while (i3 < jSONArray2.length() && i3 < 5) {
                    JSONObject jSONObject4 = jSONArray2.getJSONObject(i3);
                    String string9 = "";
                    if (jSONObject4.has("transitPassName")) {
                        String string10 = jSONObject4.getString("transitPassName");
                        jSONArray = jSONArray2;
                        DataCheckerUtil.checkLessEqualLength(string10.length(), i2);
                        str = string10;
                    } else {
                        jSONArray = jSONArray2;
                        str = "";
                    }
                    if (jSONObject4.has(str22)) {
                        String string11 = jSONObject4.getString(str22);
                        str2 = str22;
                        DataCheckerUtil.checkLessEqualLength(string11.length(), i2);
                        str3 = string11;
                    } else {
                        str2 = str22;
                        str3 = "";
                    }
                    if (jSONObject4.has(str21)) {
                        String string12 = jSONObject4.getString(str21);
                        DataCheckerUtil.checkLessEqualLength(string12.length(), i2);
                        str4 = string12;
                    } else {
                        str4 = "";
                    }
                    if (jSONObject4.has(str20)) {
                        JSONObject jSONObject5 = jSONObject4.getJSONObject(str20);
                        if (jSONObject5.has("from")) {
                            String string13 = jSONObject5.getString("from");
                            str5 = str20;
                            str6 = str21;
                            DataCheckerUtil.checkLessEqualLength(string13.length(), 128);
                            str15 = string13;
                        } else {
                            str5 = str20;
                            str6 = str21;
                            str15 = null;
                        }
                        if (jSONObject5.has("to")) {
                            String string14 = jSONObject5.getString("to");
                            DataCheckerUtil.checkLessEqualLength(string14.length(), 128);
                            str16 = string14;
                        } else {
                            str16 = null;
                        }
                        section1 = new Section1(str15, str16);
                    } else {
                        str5 = str20;
                        str6 = str21;
                        section1 = null;
                    }
                    if (jSONObject4.has("section2")) {
                        JSONObject jSONObject6 = jSONObject4.getJSONObject("section2");
                        if (jSONObject6.has("from")) {
                            string5 = jSONObject6.getString("from");
                            DataCheckerUtil.checkLessEqualLength(string5.length(), 128);
                        } else {
                            string5 = null;
                        }
                        if (jSONObject6.has("to")) {
                            string6 = jSONObject6.getString("to");
                            DataCheckerUtil.checkLessEqualLength(string6.length(), 128);
                        } else {
                            string6 = null;
                        }
                        section2 = new Section2(string5, string6);
                    } else {
                        section2 = null;
                    }
                    if (jSONObject4.has("additionalInformation")) {
                        String string15 = jSONObject4.getString("additionalInformation");
                        DataCheckerUtil.checkLessEqualLength(string15.length(), 128);
                        str7 = string15;
                    } else {
                        str7 = "";
                    }
                    if (jSONObject4.has(str19)) {
                        String string16 = jSONObject4.getString(str19);
                        DataCheckerUtil.checkLessEqualLength(string16.length(), 128);
                        str8 = string16;
                    } else {
                        str8 = "";
                    }
                    if (jSONObject4.has(str18)) {
                        string9 = jSONObject4.getString(str18);
                        DataCheckerUtil.checkLessEqualLength(string9.length(), 128);
                    }
                    String str25 = string9;
                    boolean zHas = jSONObject4.has(str17);
                    String str26 = TransitPassInfoAgent.OPTIONAL_INFO_KEY;
                    if (zHas) {
                        JSONObject jSONObject7 = jSONObject4.getJSONObject(str17);
                        if (jSONObject7.has(TransitPassInfoAgent.OPTIONAL_INFO_KEY)) {
                            string3 = jSONObject7.getString(TransitPassInfoAgent.OPTIONAL_INFO_KEY);
                            DataCheckerUtil.checkLessEqualLength(string3.length(), 128);
                        } else {
                            string3 = null;
                        }
                        str11 = str24;
                        if (jSONObject7.has(str11)) {
                            string4 = jSONObject7.getString(str11);
                            str12 = str17;
                            str9 = str18;
                            DataCheckerUtil.checkLessEqualLength(string4.length(), 128);
                        } else {
                            str12 = str17;
                            str9 = str18;
                            string4 = null;
                        }
                        if (jSONObject7.has("end")) {
                            String string17 = jSONObject7.getString("end");
                            DataCheckerUtil.checkLessEqualLength(string17.length(), 128);
                            str14 = string17;
                        } else {
                            str14 = null;
                        }
                        if (jSONObject7.has("extension")) {
                            int i4 = jSONObject7.getInt("extension");
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
                    if (jSONObject4.has(str27)) {
                        JSONArray jSONArray3 = jSONObject4.getJSONArray(str27);
                        int i5 = 0;
                        while (i5 < jSONArray3.length() && i5 < 10) {
                            JSONObject jSONObject8 = jSONArray3.getJSONObject(i5);
                            if (jSONObject8.has(str26)) {
                                string = jSONObject8.getString(str26);
                                str13 = str26;
                                DataCheckerUtil.checkLessEqualLength(string.length(), 128);
                            } else {
                                str13 = str26;
                                string = null;
                            }
                            if (jSONObject8.has(TransitPassInfoAgent.OPTIONAL_INFO_VALUE)) {
                                string2 = jSONObject8.getString(TransitPassInfoAgent.OPTIONAL_INFO_VALUE);
                                DataCheckerUtil.checkLessEqualLength(string2.length(), 128);
                            } else {
                                string2 = null;
                            }
                            arrayList2.add(new OptionalInfo(string, string2));
                            i5++;
                            str26 = str13;
                        }
                    }
                    arrayList.add(new TransitPassInfo(str, str3, str4, section1, section2, str7, str8, str25, termOfValidity, arrayList2));
                    i3++;
                    str23 = str27;
                    str17 = str12;
                    str22 = str2;
                    str18 = str9;
                    jSONArray2 = jSONArray;
                    str20 = str5;
                    str19 = str10;
                    i2 = 128;
                    str24 = str11;
                    str21 = str6;
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

    private String decodeBase64_URL_SAFE(String str) throws DataCheckerException {
        byte[] bArrDecode = Base64.decode(str.getBytes(StandardCharsets.UTF_8), 8);
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

    private boolean isVailedLinkageInfo(LinkageInfo linkageInfo) {
        return !TextUtils.isEmpty(linkageInfo.linkageName);
    }

    public Linkage getLinkageInfo(int i) {
        LinkageInfo linkageInfo;
        AdditionalInfo additionalInfo = this.additionalInfo;
        if (additionalInfo == null || additionalInfo.linkageInfoList == null || i < 0 || i >= this.additionalInfo.linkageInfoList.size() || (linkageInfo = this.additionalInfo.linkageInfoList.get(i)) == null) {
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
