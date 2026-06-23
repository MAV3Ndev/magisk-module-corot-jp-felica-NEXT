package com.felicanetworks.mfc.mfi.aim;

import android.os.Build;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.http.HttpCommunicationAgent;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.IRequest;
import com.felicanetworks.mfc.mfi.http.IResponse;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.util.SettingInfo;

/* JADX INFO: loaded from: classes3.dex */
public class AimClient {
    private String mSepId;

    public AimClient(String sepId) {
        this.mSepId = sepId;
    }

    public String checkVersionUp(int ct, int mct, int il, int mil) throws ProtocolException, HttpException {
        return runAimGet(createCheckVersionUpUri(ct, mct, il, mil));
    }

    private String runAimGet(String uri) throws ProtocolException, HttpException {
        return run(uri, createUserAgent(), new AimGetRequest(), new AimResponse());
    }

    private String createCheckVersionUpUri(int ct, int mct, int il, int mil) {
        return String.format("%s?ai=%s&av=%s&ct=%d&mct=%d&il=%d&mil=%d", SettingInfo.getAimServerCheckUpdateUrl(), SettingInfo.getAimServerApplicationIdForCheckUpdate(), FelicaAdapter.getInstance().getString(R.string.mfi_client_version), Integer.valueOf(ct), Integer.valueOf(mct), Integer.valueOf(il), Integer.valueOf(mil));
    }

    private String createUserAgent() {
        return String.format("%s/%s (%s %s; %s; %s; %s)", MfiClientConst.CLIENT_APP_NAME, FelicaAdapter.getInstance().getString(R.string.mfi_client_version), MfiClientConst.DEVICE_PLATFFORM_NAME, Build.VERSION.RELEASE, Property.getSeType(), this.mSepId, Build.MODEL);
    }

    private String run(String uri, String userAgent, IRequest request, IResponse response) throws ProtocolException, HttpException {
        HttpCommunicationAgent httpCommunicationAgent = new HttpCommunicationAgent();
        httpCommunicationAgent.setUserAgent(userAgent);
        httpCommunicationAgent.doTransaction(uri, request, response);
        return response.getData();
    }
}
