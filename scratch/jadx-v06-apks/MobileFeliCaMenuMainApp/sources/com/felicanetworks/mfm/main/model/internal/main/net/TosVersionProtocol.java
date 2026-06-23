package com.felicanetworks.mfm.main.model.internal.main.net;

import androidx.core.view.InputDeviceCompat;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.model.internal.main.net.VersionUpProtocol;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class TosVersionProtocol extends VersionUpProtocol {

    public static class Parameter extends VersionUpProtocol.Parameter {
        public Parameter(int i, int i2, int i3, int i4, int i5) {
            super(i, i2, i3, i4);
            this.appId = (String) Sg.getValue(Sg.Key.NET_TOS_APP_ID);
            this.appVersion = String.format(Locale.US, "0.0.%d", Integer.valueOf(i5));
        }
    }

    public static class Result {
        public static final String VERSION_UP_KIND_ANY = "2";
        public static final String VERSION_UP_KIND_FORCE = "1";
        public static final String VERSION_UP_KIND_UNNECESSARY = "0";
        public final int interval;
        public final int maxCount;
        public final int status;
        public final int version;

        public Result(int i, int i2, int i3, int i4) {
            this.maxCount = i;
            this.interval = i2;
            this.status = i3;
            this.version = i4;
        }

        public String toString() {
            return "Result{maxCount=" + this.maxCount + ", interval=" + this.interval + ", status=" + this.status + ", version=" + this.version + '}';
        }
    }

    public NetworkExpert.Request createTos(Parameter parameter) throws NetworkExpertException {
        return super.create(parameter);
    }

    public Result parseTos(NetworkExpert.Response response) throws NetworkExpertException {
        try {
            if (response.code != 200) {
                throw new NetworkExpertException(getClass(), InputDeviceCompat.SOURCE_DPAD, "a response cord is unjust", NetworkExpertException.Type.ID_COMMUNICATION_ERROR);
            }
            if (Integer.parseInt(response.header.get("content-length")) != response.data.length) {
                throw new NetworkExpertException(getClass(), 514, "data number of bytes injustice", NetworkExpertException.Type.ID_LENGTH_ERROR);
            }
            String[] strArrSplit = new String(response.data, StringUtil.UTF_8).split("\r\n", -1);
            if (strArrSplit.length != 6) {
                throw new NetworkExpertException(getClass(), 515, "number of the data injustice", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
            }
            DataCheckerUtil.checkLessEqualLength(strArrSplit[0].length(), 8);
            DataCheckerUtil.checkDecNumberFormat(strArrSplit[0]);
            DataCheckerUtil.checkLessEqualLength(strArrSplit[1].length(), 8);
            DataCheckerUtil.checkDecNumberFormat(strArrSplit[1]);
            DataCheckerUtil.checkFixValue(strArrSplit[3], new String[]{"0", "2", "1"});
            DataCheckerUtil.checkLessEqualLength(strArrSplit[4].length(), 2);
            DataCheckerUtil.checkDecNumberFormat(strArrSplit[4]);
            if (strArrSplit[2].length() != 0) {
                throw new NetworkExpertException(getClass(), 516, "Separation character injustice", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
            }
            if (strArrSplit[5].length() != 0) {
                throw new NetworkExpertException(getClass(), 517, "End data injustice", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
            }
            return new Result(Integer.parseInt(strArrSplit[0]), Integer.parseInt(strArrSplit[1]), Integer.parseInt(strArrSplit[3]), Integer.parseInt(strArrSplit[4]));
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

    TosVersionProtocol(NetworkExpert networkExpert) {
        super(networkExpert);
    }
}
