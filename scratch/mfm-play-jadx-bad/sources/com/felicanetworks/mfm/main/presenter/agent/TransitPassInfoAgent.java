package com.felicanetworks.mfm.main.presenter.agent;

import com.felicanetworks.mfm.main.model.info.OptionalInfo;
import com.felicanetworks.mfm.main.model.info.TransitPassInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class TransitPassInfoAgent extends InfoAgent {
    public static final String OPTIONAL_INFO_KEY = "key";
    public static final String OPTIONAL_INFO_VALUE = "value";
    private TransitPassInfo _client;

    public TransitPassInfoAgent(TransitPassInfo _client) {
        this._client = _client;
    }

    public String getTransitPassName() {
        return this._client.getTransitPassName();
    }

    public String getCategory() {
        return this._client.getCategory();
    }

    public String getCommuterPassName() {
        return this._client.getCommuterPassName();
    }

    public String getAdditionalInformation() {
        return this._client.getAdditionalInformation();
    }

    public String getVia() {
        return this._client.getVia();
    }

    public String getIssuerName() {
        return this._client.getIssuerName();
    }

    public String getSection1From() {
        if (this._client.getSection1() == null) {
            return null;
        }
        return this._client.getSection1().from;
    }

    public String getSection1To() {
        if (this._client.getSection1() == null) {
            return null;
        }
        return this._client.getSection1().to;
    }

    public String getSection2From() {
        if (this._client.getSection2() == null) {
            return null;
        }
        return this._client.getSection2().from;
    }

    public String getSection2To() {
        if (this._client.getSection2() == null) {
            return null;
        }
        return this._client.getSection2().to;
    }

    public String getTermOfValidityKey() {
        if (this._client.getTermOfValidity() == null) {
            return null;
        }
        return this._client.getTermOfValidity().key;
    }

    public String getTermOfValidityStart() {
        if (this._client.getTermOfValidity() == null) {
            return null;
        }
        return this._client.getTermOfValidity().start;
    }

    public String getTermOfValidityEnd() {
        if (this._client.getTermOfValidity() == null) {
            return null;
        }
        return this._client.getTermOfValidity().end;
    }

    public boolean getTermOfValidityExtension() {
        if (this._client.getTermOfValidity() == null) {
            return false;
        }
        return this._client.getTermOfValidity().extension;
    }

    public List<Map<String, String>> getOptionalInfoList() {
        List<OptionalInfo> optionalInfoList;
        ArrayList arrayList = new ArrayList();
        TransitPassInfo transitPassInfo = this._client;
        if (transitPassInfo != null && (optionalInfoList = transitPassInfo.getOptionalInfoList()) != null && !optionalInfoList.isEmpty()) {
            for (OptionalInfo optionalInfo : optionalInfoList) {
                HashMap map = new HashMap();
                map.put(OPTIONAL_INFO_KEY, optionalInfo.getOptionalInfoKey());
                map.put("value", optionalInfo.getOptionalInfoValue());
                arrayList.add(map);
            }
        }
        return arrayList;
    }
}
