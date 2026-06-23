package com.felicanetworks.mfc.mfi.aim;

import android.os.Build;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.http.HttpCommunicationAgent;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.IRequest;
import com.felicanetworks.mfc.mfi.http.IResponse;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;

/* JADX INFO: loaded from: classes.dex */
public class AimClient {
    private String mSepId;

    public AimClient(String str) {
        this.mSepId = str;
    }

    public String checkVersionUp(int i, int i2, int i3, int i4) throws ProtocolException, HttpException {
        return runAimGet(createCheckVersionUpUri(i, i2, i3, i4));
    }

    private String runAimGet(String str) throws ProtocolException, HttpException {
        return run(str, createUserAgent(), new AimGetRequest(), new AimResponse());
    }

    private String createCheckVersionUpUri(int i, int i2, int i3, int i4) {
        return String.format("%s?ai=%s&av=%s&ct=%d&mct=%d&il=%d&mil=%d", FlavorConst.CHECK_UPDATE_URL, FlavorConst.APPLICATION_ID_FOR_CHECK_UPDATE, FelicaAdapter.getInstance().getString(R.string.mfi_client_version), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
    }

    private String createUserAgent() {
        return String.format("%s/%s (%s %s; %s; %s; %s)", MfiClientConst.CLIENT_APP_NAME, FelicaAdapter.getInstance().getString(R.string.mfi_client_version), MfiClientConst.DEVICE_PLATFFORM_NAME, Build.VERSION.RELEASE, Property.getSeType(), this.mSepId, Build.MODEL);
    }

    private String run(String str, String str2, IRequest iRequest, IResponse iResponse) throws ProtocolException, HttpException {
        HttpCommunicationAgent httpCommunicationAgent = new HttpCommunicationAgent();
        httpCommunicationAgent.setUserAgent(str2);
        httpCommunicationAgent.doTransaction(str, iRequest, iResponse);
        return iResponse.getData();
    }
}
