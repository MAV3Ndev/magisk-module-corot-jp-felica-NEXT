package com.felicanetworks.mfm.main.presenter.structure;

import android.content.Context;
import com.felicanetworks.mfm.main.model.info.AccountChangeHistoryInfo;
import com.felicanetworks.mfm.main.presenter.internal.MfiContentProvider;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class AccountChangeHistoryDrawStructure extends CloseDrawStructure {
    public AccountChangeHistoryDrawStructure() {
        super(StructureType.ACCOUNT_CHANGE_HISTORY);
    }

    public List<AccountChangeHistoryInfo> getDataList(Context context) {
        String strLoadAccountChangeHistoryList = ServicePreference.getInstance().loadAccountChangeHistoryList(context);
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(strLoadAccountChangeHistoryList);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                arrayList.add(new AccountChangeHistoryInfo(jSONObject.getString(MfiContentProvider.Accounts.PATH), jSONObject.getString("changeDate"), jSONObject.getString("changeAppId")));
            }
        } catch (JSONException unused) {
        }
        return arrayList;
    }
}
