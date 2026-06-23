package com.felicanetworks.mfm.main.model.internal.main.net;

import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class BannerImageProtocol extends Protocol {

    public static class Parameter {
        public String fileName;
        public Type type;

        public enum Type {
            MY_SERVICE,
            NOTIFICATION
        }

        public Parameter(Type type, String str) {
            this.type = type;
            this.fileName = str;
        }

        public String toString() {
            return "BannerImageProtocol$Parameter{pos:" + this.type + ",imgName:" + this.fileName + "}";
        }
    }

    public static class Result {
        public byte[] binary;

        /* synthetic */ Result(byte[] bArr, AnonymousClass1 anonymousClass1) {
            this(bArr);
        }

        private Result(byte[] bArr) {
            this.binary = (byte[]) bArr.clone();
        }

        public String toString() {
            return "BannerImageProtocol$Result{binary size:" + this.binary.length + "}";
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.net.BannerImageProtocol$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$net$BannerImageProtocol$Parameter$Type;

        static {
            int[] iArr = new int[Parameter.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$net$BannerImageProtocol$Parameter$Type = iArr;
            try {
                iArr[Parameter.Type.MY_SERVICE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$net$BannerImageProtocol$Parameter$Type[Parameter.Type.NOTIFICATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public NetworkExpert.Request create(Parameter parameter) {
        String str;
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$net$BannerImageProtocol$Parameter$Type[parameter.type.ordinal()];
        if (i == 1) {
            str = (String) Sg.getValue(Sg.Key.NET_AIM_BANNER_SERVICE_URL);
        } else {
            str = i != 2 ? null : (String) Sg.getValue(Sg.Key.NET_AIM_BANNER_NOTICE_URL);
        }
        String str2 = str + parameter.fileName;
        int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_AIM_BANNER_IMAGE_SESSION_TIMEOUT)).intValue();
        int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_AIM_BANNER_IMAGE_READ_TIMEOUT)).intValue();
        HashMap map = new HashMap();
        map.put("user-agent", getUserAgent());
        NetworkExpert.Request request = new NetworkExpert.Request(str2, "GET", map, iIntValue, iIntValue2, null);
        return BasicAuthentication.isNeedBasicAuthentication() ? BasicAuthentication.addBasicAuthorization(request) : request;
    }

    public Result parse(NetworkExpert.Response response) throws NetworkExpertException {
        try {
            if (response.code != 200) {
                throw new NetworkExpertException(getClass(), InputDeviceCompat.SOURCE_DPAD, "A response cord is unjust :", NetworkExpertException.Type.ID_COMMUNICATION_ERROR);
            }
            if (Integer.parseInt(response.header.get("content-length")) != response.data.length) {
                throw new NetworkExpertException(getClass(), 514, "Data number of bytes injustice", NetworkExpertException.Type.ID_LENGTH_ERROR);
            }
            return new Result(response.data, null);
        } catch (NetworkExpertException e) {
            LogUtil.warning(e);
            throw e;
        } catch (Exception e2) {
            LogUtil.warning(e2);
            throw new NetworkExpertException(getClass(), 515, e2, "banner information file response message analysis other error", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    BannerImageProtocol(NetworkExpert networkExpert) {
        super(networkExpert);
    }
}
