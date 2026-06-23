package com.felicanetworks.mfc.mfi.fws;

import android.os.Build;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.fws.json.GetMfiControlInfoRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetMfiControlInfoResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
class GetMfiControlInfoTask extends AccessFwsTask<GetMfiControlInfoResponseJson> {
    private static final List<String> VALID_RESULT_CODE_LIST_GET_CONTENT;
    private final MfiChipHolder mChipHolder;
    private String mContentId;
    private final DataManager mDataManager;
    private final a mJwsCreator;

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_CONTENT = arrayList;
        arrayList.add(null);
    }

    GetMfiControlInfoTask(int i, FwsClient fwsClient, String str, a aVar, MfiChipHolder mfiChipHolder, DataManager dataManager) {
        super(i, fwsClient);
        this.mContentId = str;
        this.mJwsCreator = aVar;
        this.mChipHolder = mfiChipHolder;
        this.mDataManager = dataManager;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
    protected RequestJson createRequestJson() throws JSONException {
        GetMfiControlInfoRequestJson getMfiControlInfoRequestJson = new GetMfiControlInfoRequestJson();
        getMfiControlInfoRequestJson.setContentId(this.mContentId);
        getMfiControlInfoRequestJson.setMficAppVer(FelicaAdapter.getInstance().getString(R.string.mfi_client_version));
        getMfiControlInfoRequestJson.setDevicePlatformVersion(Build.VERSION.RELEASE);
        getMfiControlInfoRequestJson.setSeType(this.mDataManager.getSeInfo().getSeType());
        getMfiControlInfoRequestJson.setSepId(this.mDataManager.getSeInfo().getSepId());
        getMfiControlInfoRequestJson.setDeviceName(Build.MODEL);
        return getMfiControlInfoRequestJson;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
    protected String callFws(String str) throws ProtocolException, HttpException {
        String strA;
        byte[] content = this.mFwsClient.getContent(str);
        try {
            if (Property.isChipGP()) {
                strA = this.mJwsCreator.a(content, this.mChipHolder.getGpController());
            } else {
                strA = this.mJwsCreator.a(content, this.mChipHolder.getFelica());
            }
            if (strA != null) {
                return strA;
            }
            LogMgr.log(2, "700 decryptedResponse is null.");
            return null;
        } catch (Exception e) {
            LogMgr.printStackTrace(2, e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
    public GetMfiControlInfoResponseJson convertResponse(String str) throws JSONException {
        if (str != null) {
            return new GetMfiControlInfoResponseJson(str);
        }
        return null;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
    protected List<String> getValidResultCodeList() {
        return VALID_RESULT_CODE_LIST_GET_CONTENT;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
    protected String getApiHash() {
        return MfiClientCallbackConst.Api.FWS_GET_CONTENT.msg;
    }
}
