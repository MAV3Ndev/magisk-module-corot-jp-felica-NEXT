package com.felicanetworks.mfm.main.policy.mfitest;

import android.util.Log;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams;
import com.felicanetworks.mfm.main.policy.FlavorConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class MfiTestSettings {
    private static final String FILE_PATH = "/data/local/tmp/mfi_test_settings.json";
    private static final String LOG_MSG = "Apply external file value. ";
    private static final String LOG_TAG = "[MENU_I]";
    private static final String ROOT_KEY = "menuConfig";

    public enum Param {
        SERVICE_ID_DCARD("serviceIdD", DataType.TYPE_STRING, 8, -1, -1, Format.ALPHA_NUMBER, new String[]{FlavorConstants.DEVKEY1, FlavorConstants.DEVKEY2}, new FeliCaParams.Consumer() { // from class: com.felicanetworks.mfm.main.policy.mfitest.MfiTestSettings$Param$$ExternalSyntheticLambda0
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams.Consumer
            public final void accept(Object obj) {
                FeliCaParams.setServiceIdDcard(obj);
            }
        }),
        SERVICE_ID_SUICA("serviceIdS", DataType.TYPE_STRING, 8, -1, -1, Format.ALPHA_NUMBER, new String[]{FlavorConstants.CERT, FlavorConstants.CERT_FN_TEST}, new FeliCaParams.Consumer() { // from class: com.felicanetworks.mfm.main.policy.mfitest.MfiTestSettings$Param$$ExternalSyntheticLambda1
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams.Consumer
            public final void accept(Object obj) {
                FeliCaParams.setServiceIdSuica(obj);
            }
        }),
        SERVICE_ID_QP("serviceIdQ", DataType.TYPE_STRING, 8, -1, -1, Format.ALPHA_NUMBER, new String[]{FlavorConstants.DEVKEY1, FlavorConstants.DEVKEY2}, new FeliCaParams.Consumer() { // from class: com.felicanetworks.mfm.main.policy.mfitest.MfiTestSettings$Param$$ExternalSyntheticLambda2
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams.Consumer
            public final void accept(Object obj) {
                FeliCaParams.setServiceIdQp(obj);
            }
        }),
        SERVICE_CODE_DCARD_ISSURE_MANAGE("serviceCodeDIssureManage", DataType.TYPE_STRING, 8, -1, -1, Format.HEX_NUMBER, new String[]{FlavorConstants.DEVKEY1, FlavorConstants.DEVKEY2}, new FeliCaParams.Consumer() { // from class: com.felicanetworks.mfm.main.policy.mfitest.MfiTestSettings$Param$$ExternalSyntheticLambda3
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams.Consumer
            public final void accept(Object obj) {
                FeliCaParams.setServiceCodeDcardIssureManage(obj);
            }
        }),
        SERVICE_CODE_DCARD_ISSURE_SETTING("serviceCodeDIssureSetting", DataType.TYPE_STRING, 8, -1, -1, Format.HEX_NUMBER, new String[]{FlavorConstants.DEVKEY1, FlavorConstants.DEVKEY2}, new FeliCaParams.Consumer() { // from class: com.felicanetworks.mfm.main.policy.mfitest.MfiTestSettings$Param$$ExternalSyntheticLambda4
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams.Consumer
            public final void accept(Object obj) {
                FeliCaParams.setServiceCodeDcardIssureSetting(obj);
            }
        }),
        SERVICE_CODE_QUICPAY_ID("serviceCodeQ", DataType.TYPE_STRING, 8, -1, -1, Format.HEX_NUMBER, new String[]{FlavorConstants.DEVKEY1, FlavorConstants.DEVKEY2}, new FeliCaParams.Consumer() { // from class: com.felicanetworks.mfm.main.policy.mfitest.MfiTestSettings$Param$$ExternalSyntheticLambda5
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams.Consumer
            public final void accept(Object obj) {
                FeliCaParams.setServiceCodeQp(obj);
            }
        });

        public final FeliCaParams.Consumer<Object> _exe;
        public final Format _format;
        public final String _key;
        public final int _length;
        public final int _max;
        public final int _min;
        public final String[] _target;
        public final DataType _type;

        public enum DataType {
            TYPE_STRING,
            TYPE_INTEGER
        }

        public enum Format {
            HEX_NUMBER,
            ALPHA_NUMBER
        }

        Param(String key, DataType type, int length, int min, int max, Format format, String[] target, FeliCaParams.Consumer exe) {
            this._key = key;
            this._type = type;
            this._length = length;
            this._min = min;
            this._max = max;
            this._format = format;
            this._target = target;
            this._exe = exe;
        }

        public static Param resolve(String key) {
            for (Param param : values()) {
                if (param._key.equals(key)) {
                    return param;
                }
            }
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:66:0x0043, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0077 A[Catch: JSONException -> 0x00b8, TryCatch #5 {JSONException -> 0x00b8, blocks: (B:24:0x006c, B:27:0x0077, B:29:0x0081, B:31:0x0097, B:30:0x0092), top: B:53:0x006c }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0076 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean load() {
        Object obj;
        int i;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(FILE_PATH)), StandardCharsets.UTF_8));
            try {
                try {
                    StringBuilder sb = new StringBuilder();
                    for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                        sb.append(line);
                    }
                    JSONObject jSONObject = new JSONObject(sb.toString());
                    try {
                        bufferedReader.close();
                    } catch (IOException unused) {
                    }
                    try {
                        JSONObject jSONObject2 = jSONObject.getJSONObject(ROOT_KEY);
                        Iterator<String> itKeys = jSONObject2.keys();
                        while (itKeys.hasNext()) {
                            String next = itKeys.next();
                            Param paramResolve = Param.resolve(next);
                            if (paramResolve != null) {
                                if (paramResolve._target.length != 0) {
                                    String[] strArr = paramResolve._target;
                                    int length = strArr.length;
                                    while (true) {
                                        if (i >= length) {
                                            break;
                                        }
                                        i = "production".equals(strArr[i]) ? 0 : i + 1;
                                        obj = jSONObject2.get(next);
                                        if (checkFormat(paramResolve, obj)) {
                                        }
                                    }
                                } else {
                                    try {
                                        obj = jSONObject2.get(next);
                                        if (checkFormat(paramResolve, obj)) {
                                            return false;
                                        }
                                        if (Param.Format.HEX_NUMBER.equals(paramResolve._format)) {
                                            paramResolve._exe.accept(Integer.valueOf(CommonUtil.hexStringToInt((String) obj)));
                                        } else {
                                            paramResolve._exe.accept(obj);
                                        }
                                        Log.i(LOG_TAG, LOG_MSG + paramResolve._key + " : " + obj);
                                    } catch (JSONException unused2) {
                                        return false;
                                    }
                                }
                            }
                        }
                        FeliCaParams.updateReadInfo();
                    } catch (Exception unused3) {
                    }
                    return true;
                } catch (IOException unused4) {
                    return false;
                }
            } catch (Exception unused5) {
                bufferedReader.close();
                return false;
            } catch (Throwable th) {
                try {
                    bufferedReader.close();
                } catch (IOException unused6) {
                }
                throw th;
            }
        } catch (Exception unused7) {
            return true;
        }
    }

    private static boolean checkFormat(Param e, Object val) {
        if (e._type == Param.DataType.TYPE_INTEGER) {
            return checkIntegerFormat(e, val);
        }
        if (e._type == Param.DataType.TYPE_STRING) {
            return checkStringFormat(e, val);
        }
        return false;
    }

    private static boolean checkIntegerFormat(Param e, Object val) {
        try {
            if (!(val instanceof Integer)) {
                return false;
            }
            DataCheckerUtil.checkMidwayLength(((Integer) val).intValue(), e._min, e._max);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private static boolean checkStringFormat(Param e, Object val) {
        if (!(val instanceof String)) {
            return false;
        }
        String str = (String) val;
        DataCheckerUtil.checkLessEqualLength(str.length(), e._length);
        if (e._format == Param.Format.ALPHA_NUMBER) {
            DataCheckerUtil.checkAlphaNumberFormat(str);
            return true;
        }
        if (e._format == Param.Format.HEX_NUMBER) {
            DataCheckerUtil.checkHexNumberFormat(str);
            return true;
        }
        return false;
    }
}
