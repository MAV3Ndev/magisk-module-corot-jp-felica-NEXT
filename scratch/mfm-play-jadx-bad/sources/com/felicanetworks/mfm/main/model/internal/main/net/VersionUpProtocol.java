package com.felicanetworks.mfm.main.model.internal.main.net;

import androidx.core.view.InputDeviceCompat;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.fix.Information;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.semc.sws.SwsRequest;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public class VersionUpProtocol extends Protocol {

    public static class Parameter {
        public int actualOfflineCheckInterval;
        public String appId = (String) Sg.getValue(Sg.Key.SETTING_MFM_ID);
        public String appVersion = Information.simpleVersionName();
        public int offlineCheckActualCnt;
        public int offlineCheckInterval;
        public int offlineCheckMaxCnt;

        public Parameter(int offlineCheckActualCnt, int offlineCheckMaxCnt, int offlineCheckInterval, int actualOfflineCheckInterval) {
            this.offlineCheckActualCnt = offlineCheckActualCnt;
            this.offlineCheckMaxCnt = offlineCheckMaxCnt;
            this.offlineCheckInterval = offlineCheckInterval;
            this.actualOfflineCheckInterval = actualOfflineCheckInterval;
        }

        public String toString() {
            return "Parameter{offlineCheckActualCnt=" + this.offlineCheckActualCnt + ", offlineCheckMaxCnt=" + this.offlineCheckMaxCnt + ", offlineCheckInterval=" + this.offlineCheckInterval + ", actualOfflineCheckInterval=" + this.actualOfflineCheckInterval + ", appId='" + this.appId + "', appVersion='" + this.appVersion + "'}";
        }
    }

    public static class Result {
        public static final String COMMUNICATION_MODE_LOG_STOP = "2";
        public static final String COMMUNICATION_MODE_NORMAL = "0";
        public static final String COMMUNICATION_MODE_PUSH_LOG_STOP = "3";
        public static final String COMMUNICATION_MODE_PUSH_STOP = "1";
        private static final int MASK_PREVENT_LOGS = 2;
        private static final int MASK_PREVENT_PUSH = 1;
        public static final String VERSION_UP_KIND_ANY = "2";
        public static final String VERSION_UP_KIND_FORCE = "1";
        public static final String VERSION_UP_KIND_UNNECESSARY = "0";
        public int networkMode;
        public int offlineCheckInterval;
        public int offlineCheckMaxCnt;
        public int versionupInfo;

        private Result(int offlineCheckMaxCnt, int offlineCheckInterval, int versionupInfo, int networkMode) {
            this.offlineCheckMaxCnt = offlineCheckMaxCnt;
            this.offlineCheckInterval = offlineCheckInterval;
            this.versionupInfo = versionupInfo;
            this.networkMode = networkMode;
        }

        public boolean isPreventPush() {
            return (this.networkMode & 1) != 0;
        }

        public boolean isPreventLogs() {
            return (this.networkMode & 2) != 0;
        }

        public String toString() {
            return "Result{offlineCheckMaxCnt=" + this.offlineCheckMaxCnt + ", offlineCheckInterval=" + this.offlineCheckInterval + ", versionupInfo=" + this.versionupInfo + ", networkMode=" + this.networkMode + '}';
        }
    }

    public NetworkExpert.Request create(Parameter parameter) throws NetworkExpertException {
        try {
            String str = (String) Sg.getValue(Sg.Key.NET_AIM_VERSION_URL);
            int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_AIM_VERSION_SESSION_TIMEOUT)).intValue();
            int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_AIM_VERSION_READ_TIMEOUT)).intValue();
            DataCheckerUtil.checkLessEqualLength(parameter.appVersion.length(), 8);
            DataCheckerUtil.checkAlphaSignFormat(parameter.appVersion);
            byte[] bytes = ("ai=" + parameter.appId + "&av=" + parameter.appVersion + "&ct=" + parameter.offlineCheckActualCnt + "&mct=" + parameter.offlineCheckMaxCnt + "&il=" + parameter.actualOfflineCheckInterval + "&mil=" + parameter.offlineCheckInterval).getBytes(StringUtil.UTF_8);
            HashMap map = new HashMap();
            map.put("user-agent", getUserAgent());
            map.put("content-length", Integer.toString(bytes.length));
            NetworkExpert.Request request = new NetworkExpert.Request(str, SwsRequest.HTTP_METHOD_GET, map, iIntValue, iIntValue2, bytes);
            return BasicAuthentication.isNeedBasicAuthentication() ? BasicAuthentication.addBasicAuthorization(request) : request;
        } catch (Exception e) {
            throw new NetworkExpertException(getClass(), 257, e, "Version up confirmation communication request message generation failed.", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    public Result parse(NetworkExpert.Response response) throws NetworkExpertException {
        try {
            if (response.code != 200) {
                throw new NetworkExpertException(getClass(), InputDeviceCompat.SOURCE_DPAD, "a response cord is unjust", NetworkExpertException.Type.ID_COMMUNICATION_ERROR);
            }
            if (Integer.parseInt(response.header.get("content-length")) == response.data.length) {
                String[] strArrSplit = new String(response.data, StringUtil.UTF_8).split("\r\n", -1);
                if (strArrSplit.length != 6) {
                    throw new NetworkExpertException(getClass(), 515, "number of the data injustice", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
                }
                DataCheckerUtil.checkLessEqualLength(strArrSplit[0].length(), 8);
                DataCheckerUtil.checkDecNumberFormat(strArrSplit[0]);
                DataCheckerUtil.checkLessEqualLength(strArrSplit[1].length(), 8);
                DataCheckerUtil.checkDecNumberFormat(strArrSplit[1]);
                DataCheckerUtil.checkFixValue(strArrSplit[3], new String[]{"0", "2", "1"});
                DataCheckerUtil.checkFixValue(strArrSplit[4], new String[]{"0", "1", "2", "3"});
                if (strArrSplit[2].length() != 0) {
                    throw new NetworkExpertException(getClass(), 516, "Separation character injustice", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
                }
                if (strArrSplit[5].length() != 0) {
                    throw new NetworkExpertException(getClass(), 517, "End data injustice", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
                }
                return new Result(Integer.parseInt(strArrSplit[0]), Integer.parseInt(strArrSplit[1]), Integer.parseInt(strArrSplit[3]), Integer.parseInt(strArrSplit[4]));
            }
            throw new NetworkExpertException(getClass(), 514, "data number of bytes injustice", NetworkExpertException.Type.ID_LENGTH_ERROR);
        } catch (DataCheckerException e) {
            LogUtil.warning(e);
            if (e.getErrorId() == 0) {
                throw new NetworkExpertException(getClass(), 518, e, "version up confirmation communication response message analysis data length fraud", NetworkExpertException.Type.ID_LENGTH_ERROR);
            }
            if (1 != e.getErrorId()) {
                return null;
            }
            throw new NetworkExpertException(getClass(), 519, e, "version up confirmation communication response message analysis data Attribute fraud", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
        } catch (NetworkExpertException e2) {
            LogUtil.warning(e2);
            throw e2;
        } catch (Exception e3) {
            LogUtil.warning(e3);
            throw new NetworkExpertException(getClass(), 520, e3, "version up confirmation communication response message analysis other error", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    VersionUpProtocol(NetworkExpert net) {
        super(net);
    }
}
