package com.felicanetworks.mfm.main.policy.sg;

import android.content.Context;
import android.content.res.Resources;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.semc.config.AccessConfig;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class Sg {
    private static final Map<String, Integer> ISSUER_MAP;
    private static final String LOG_TAG = "SG";
    private static final String[] MATCH_PROTOCOL;
    private static final String MATCH_URI = "[#-;?-Za-z!=_~]+";
    private static final String SEPARATOR = ",";
    private static final String SUPPORTED_LINE_FORMAT = "^[0-9A-F]{8},.*";
    private static final String UNSUPPORTED_LINE_FORMAT = ".*,.*,";
    private static Sg _this;
    private Context _context;
    private Map<Key, Object> _sgMap = new HashMap();
    private Map<Key, Object> _testSgMap = new HashMap();

    public enum Key {
        NET_SIM_UC_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.1
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030395);
            }
        },
        NET_SIM_UC_SESSION_TIMEOUT(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.2
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030304";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 8, false);
                    return Integer.valueOf(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        NET_SIM_UC_READ_TIMEOUT(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.3
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030312";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 8, false);
                    return Integer.valueOf(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        NET_SIM_GID_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.4
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030305);
            }
        },
        NET_SIM_GID_SESSION_TIMEOUT(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.5
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030306";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 8, false);
                    return Integer.valueOf(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        NET_SIM_GID_READ_TIMEOUT(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.6
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030313";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 8, false);
                    return Integer.valueOf(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        NET_SIM_SID_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.7
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030396);
            }
        },
        NET_SIM_SID_SESSION_TIMEOUT(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.8
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030308";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 8, false);
                    return Integer.valueOf(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        NET_SIM_SID_READ_TIMEOUT(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.9
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030314";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 8, false);
                    return Integer.valueOf(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        NET_FP_SERVICE_GETTING_INFO_CONNECTION_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.10
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303C4);
            }
        },
        NET_FP_SESSION_TIMEOUT(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.11
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_020303C5));
            }
        },
        NET_FP_RECEIVING_TIMEOUT(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.12
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_020303C6));
            }
        },
        NET_FP_UNKNOWN_SERVICE_LINK_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.13
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303C8);
            }
        },
        NET_FP_WEB_SITE_LINK_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.14
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303C9);
            }
        },
        NET_SIM_BK_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.15
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030380);
            }
        },
        NET_SIM_BK_SESSION_TIMEOUT(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.16
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_02030381));
            }
        },
        NET_SIM_BK_READ_TIMEOUT(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.17
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_02030382));
            }
        },
        NET_SIM_SA_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.18
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030383);
            }
        },
        NET_SIM_SA_SESSION_TIMEOUT(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.19
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_02030384));
            }
        },
        NET_SIM_SA_READ_TIMEOUT(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.20
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_02030385));
            }
        },
        NET_SIM_SA_SAI_SIGN_OF_UNIDENTIFIED(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.21
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303C3);
            }
        },
        NET_AIM_VERSION_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.22
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030390);
            }
        },
        NET_AIM_VERSION_SESSION_TIMEOUT(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.23
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030319";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 8, false);
                    return Integer.valueOf(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        NET_AIM_VERSION_READ_TIMEOUT(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.24
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "0203031A";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 8, false);
                    return Integer.valueOf(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        NET_AIM_VERSION_DEFAULT_PARAM_INTERVAL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.25
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030313);
            }
        },
        NET_AIM_VERSION_DEFAULT_PARAM_FACT_INTERVAL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.26
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030314);
            }
        },
        NET_AIM_VERSION_DEFAULT_PARAM_MAX_COUNT(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.27
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030315);
            }
        },
        NET_AIM_VERSION_DEFAULT_PARAM_FACT_COUNT(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.28
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030316);
            }
        },
        NET_AIM_TROUBLE_URL(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.29
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_mfm_0203030A);
            }
        },
        NET_AIM_TROUBLE_SESSION_TIMEOUT(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.30
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_mfm_0203030B));
            }
        },
        NET_AIM_TROUBLE_READ_TIMEOUT(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.31
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_mfm_02030316));
            }
        },
        NET_AIM_BANNER_SERVICE_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.32
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getStringArray(R.array.sg_02030398)[carrierId];
            }
        },
        NET_AIM_BANNER_NOTICE_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.33
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getStringArray(R.array.sg_02030399)[carrierId];
            }
        },
        NET_AIM_BANNER_FILE_NAME(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.34
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_0203039A);
            }
        },
        NET_AIM_BANNER_SESSION_TIMEOUT(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.35
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_0203039B));
            }
        },
        NET_AIM_BANNER_READ_TIMEOUT(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.36
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_0203039C));
            }
        },
        NET_AIM_BANNER_IMAGE_SESSION_TIMEOUT(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.37
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_0203039D));
            }
        },
        NET_AIM_BANNER_IMAGE_READ_TIMEOUT(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.38
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_0203039E));
            }
        },
        NET_EXTRA_GET_IMAGE_SESSION_TIMEOUT(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.39
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_02030339));
            }
        },
        NET_EXTRA_GET_IMAGE_READ_TIMEOUT(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.40
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_02030340));
            }
        },
        NET_COMMUNICATION_DATA_RECEPTION_MAXIMUM_SIZE(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.41
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_020303A6));
            }
        },
        DB_AREA_IDS_TABLE_SELECT_LIMIT(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.42
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030702";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 8, false);
                    return value;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        DB_APP_IDS_TABLE_SELECT_LIMIT(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.43
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030703";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 8, false);
                    return value;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        DB_MULTI_IDS_TABLE_SELECT_LIMIT(FileType.INTERNAL_SG_DB_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.44
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030780);
            }
        },
        DB_SERVICE_TABLE_SELECT_LIMIT(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.45
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030704";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 8, false);
                    return value;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        DB_RECOMMEND_TABLE_SELECT_LIMIT(FileType.INTERNAL_SG_DB_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.46
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030781);
            }
        },
        DB_FP_SERVICE_TABLE_SELECT_LIMIT(FileType.INTERNAL_SG_DB_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.47
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030782);
            }
        },
        FELICA_CHIP_TIMEOUT_VALUE(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.48
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030201";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 8, false);
                    return Integer.valueOf(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        FELICA_CHIP_RETRY_COUNT(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.49
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030202";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 2, false);
                    return Integer.valueOf(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        FELICA_MFC_ISSUER_CODE(FileType.EXTERNAL_COMMON_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.50
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return AccessConfig.SYSTEM_FILE_CHIP_ISSUER_ID_KEY;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkHexNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 6, true);
                    return value;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        CAREER_CODE(FileType.EXTERNAL_COMMON_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.51
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "00000002";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkHexNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 6, true);
                    return CommonUtil.hexStringToBin(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_TIMEZONE(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.52
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "0203030C";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(value);
                    DataCheckerUtil.checkByteLength(value, 64, false);
                    return value;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_API_CODE_ALPHA(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.53
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030309";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkHexNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 32, true);
                    return CommonUtil.hexStringToBin(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object translate(String string) {
                return CommonUtil.hexStringToBin(string);
            }
        },
        SETTING_API_CODE_VERSION(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.54
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030311";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkHexNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 4, true);
                    return value;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_MAX_STOCK_COUNT_TROUBLE_LOG(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.55
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_02030506));
            }
        },
        SETTING_ONLINE_FORMAT(FileType.EXTERNAL_COMMON_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.56
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "00000005";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 1, true);
                    DataCheckerUtil.checkFixValue(value, new String[]{String.valueOf(0), String.valueOf(1)});
                    return Integer.valueOf(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_DEVICE_TYPE(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.57
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return false;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030906";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 1, true);
                    DataCheckerUtil.checkFixValue(value, new String[]{String.valueOf(1), String.valueOf(2)});
                    return Integer.valueOf(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_FELICA_VERSION(FileType.EXTERNAL_COMMON_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.58
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return false;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "00000013";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 1, true);
                    DataCheckerUtil.checkFixValue(value, new String[]{String.valueOf(0), String.valueOf(1)});
                    return Integer.valueOf(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_MFM_ID(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.59
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030303);
            }
        },
        SETTING_MFM_NAME(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.60
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030508);
            }
        },
        SETTING_PLATFORM_TYPE(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.61
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030304);
            }
        },
        SETTING_URL_FAQ(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.62
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030397);
            }
        },
        SETTING_NOTICE_PREINSTALL_DATA(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.63
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030991);
            }
        },
        SETTING_WHITE_DOMAIN_LIST(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.64
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getStringArray(R.array.sg_020303A5);
            }
        },
        SETTING_DEFAULT_SERVICE_ICON(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.65
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303B2);
            }
        },
        SETTING_SKU_URL_VALUE(FileType.EXTERNAL_COMMON_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.66
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return false;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "00000014";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(value);
                    DataCheckerUtil.checkByteLength(value, 999, false);
                    return value;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_SKU_KEY_VALUE(FileType.EXTERNAL_COMMON_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.67
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return false;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "00000015";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(value);
                    DataCheckerUtil.checkByteLength(value, 64, false);
                    return value;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_SKU_VALUE(FileType.EXTERNAL_COMMON_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.68
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return false;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "00000018";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 1, true);
                    DataCheckerUtil.checkFixValue(value, new String[]{String.valueOf(0), String.valueOf(1)});
                    return Integer.valueOf(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_LAUNCH_LINK_APP_SERVICE_INTENT_FILTER_ACTION_NAME(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.69
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030607";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(value);
                    DataCheckerUtil.checkByteLength(value, 999, false);
                    return value;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_LAUNCH_LINK_APP_PKG_MFS(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.70
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030601";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(value);
                    DataCheckerUtil.checkByteLength(value, 64, false);
                    return value;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_LAUNCH_LINK_APP_CLS_MFS(FileType.EXTERNAL_MFM_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.71
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030602";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(value);
                    DataCheckerUtil.checkByteLength(value, 96, false);
                    return value;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_LAUNCH_LINK_APP_PKG_PLAY_STORE(FileType.EXTERNAL_COMMON_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.72
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "00000004";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(value);
                    DataCheckerUtil.checkByteLength(value, 64, false);
                    return value;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_LAUNCH_LINK_APP_PKG_FELICA_LOCK(FileType.EXTERNAL_COMMON_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.73
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030001";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(value);
                    DataCheckerUtil.checkByteLength(value, 64, false);
                    return value;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_LAUNCH_LINK_APP_CLS_FELICA_LOCK(FileType.EXTERNAL_COMMON_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.74
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030002";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(value);
                    DataCheckerUtil.checkByteLength(value, 96, false);
                    return value;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_LAUNCH_SCREEN_LOCK_SETTING(FileType.EXTERNAL_COMMON_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.75
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return false;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030003";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(value);
                    DataCheckerUtil.checkByteLength(value, 999, false);
                    return value;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_TAP_INTERACTION_INCOMPATIBLE_FLG(FileType.EXTERNAL_COMMON_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.76
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return false;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "00000017";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkDecNumberFormat(value);
                    DataCheckerUtil.checkByteLength(value, 1, true);
                    DataCheckerUtil.checkFixValue(value, new String[]{String.valueOf(1)});
                    return Integer.valueOf(value);
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        },
        SETTING_LAUNCH_LINK_APP_URI_NFC_SETTING(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.77
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020309B3);
            }
        },
        SETTING_LAUNCH_LINK_URL_DISCLAIMER_SECURE_ELEMENT_F(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.78
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getStringArray(R.array.sg_02030404)[carrierId];
            }
        },
        SETTING_LAUNCH_LINK_URL_HOW_TO_CHANGE_MODEL(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.79
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030495);
            }
        },
        SETTING_LAUNCH_LINK_URL_DISCLAIMER_EMONEY(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.80
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030483);
            }
        },
        SETTING_LAUNCH_LINK_URL_DISCLAIMER_MFM_FOR_BROWSER(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.81
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030484);
            }
        },
        SETTING_LAUNCH_LINK_URL_DISCLAIMER_MFM_FOR_WEBVIEW(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.82
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020304A0);
            }
        },
        SETTING_DISCLAIMER_MFM_CONTENT_TITLE_FOR_WEBVIEW(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.83
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020304A1);
            }
        },
        SETTING_CLEAR_ACCOUNT_URL(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.84
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020304A3);
            }
        },
        SETTING_LAUNCH_LINK_URL_NOTES_OF_READ_EX_IC_CARD(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.85
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030DA4);
            }
        },
        SETTING_ID_DEFAULT_BANNER_SERVICE(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.86
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303B8);
            }
        },
        SETTING_LAUNCH_LINK_URL_DEFAULT_BANNER_SERVICE(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.87
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_0203039F);
            }
        },
        SETTING_IMAGE_DEFAULT_BANNER_SERVICE(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.88
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303A7);
            }
        },
        SETTING_ID_DEFAULT_BANNER_NOTICE(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.89
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303B9);
            }
        },
        SETTING_LAUNCH_LINK_URL_DEFAULT_BANNER_NOTICE(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.90
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303A0);
            }
        },
        SETTING_IMAGE_DEFAULT_BANNER_NOTICE(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.91
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303A8);
            }
        },
        SETTING_PREFIX_FELICA_SERVICE(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.92
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030120);
            }
        },
        SETTING_DCARD_SERVICE_NAME(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.93
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030BA9);
            }
        },
        SETTING_DCARD_ICON(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.94
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030BB0);
            }
        },
        SETTING_QP_GROUP_NAME(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.95
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030BC0);
            }
        },
        SETTING_QP_GROUP_ICON(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.96
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030BC1);
            }
        },
        SETTING_QP_MIZUHO_SERVICE_NAME(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.97
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030BC2);
            }
        },
        SETTING_QP_MIZUHO_ICON(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.98
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030BC3);
            }
        },
        SETTING_QP_MIZUHO_PKG(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.99
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030BC4);
            }
        },
        SETTING_QP_MIZUHO_SIG_HASH(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.100
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030BC5);
            }
        },
        SETTING_QP_MIZUHO_DOWNLOAD_TYPE(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.101
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030BC6);
            }
        },
        SETTING_QP_MIZUHO_DOWNLOAD_URL(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.102
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030BC7);
            }
        },
        SETTING_QP_SERVICE_NAME(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.103
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030BC8);
            }
        },
        SETTING_QP_ICON(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.104
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030BC9);
            }
        },
        SETTING_QP_PKG(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.105
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030BCA);
            }
        },
        SETTING_QP_SIG_HASH(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.106
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030BCB);
            }
        },
        SETTING_QP_DOWNLOAD_TYPE(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.107
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030BCC);
            }
        },
        SETTING_QP_DOWNLOAD_URL(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.108
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030BCD);
            }
        },
        SETTING_DEFAULT_GUIDANCE_ID_LIST(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.109
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getStringArray(R.array.sg_020309B4);
            }
        },
        SETTING_DEFAULT_GUIDANCE_TEXT_LIST(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.110
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getStringArray(R.array.sg_020309B5);
            }
        },
        SETTING_DEFAULT_GUIDANCE_ICON_LIST(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.111
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getStringArray(R.array.sg_020309B6);
            }
        },
        SETTING_TRANSPORTATION_IC_CARD_NAME(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.112
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020309C1);
            }
        },
        SETTING_DEFAULT_NOTICE_ICON(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.113
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020309C2);
            }
        },
        SETTING_FP_SERVICE_COLOR_BLUE(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.114
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030496);
            }
        },
        SETTING_FP_SERVICE_COLOR_GRAY(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.115
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030497);
            }
        },
        SETTING_TAP_INTERACTION_PKG(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.116
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020309C5);
            }
        },
        SETTING_TAP_INTERACTION_CLS(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.117
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020309C6);
            }
        },
        SETTING_TAP_INTERACTION_ACT(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.118
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020309C7);
            }
        },
        SETTING_TAP_INTERACTION_EXT(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.119
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020309C8);
            }
        },
        SETTING_STOP_CHIP_ACCESS_ACT(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.120
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020309CB);
            }
        },
        SETTING_STOP_CHIP_ACCESS_EXT(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.121
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020309CC);
            }
        },
        SETTING_TARGET_SERVICE_REGISTER_PROPERTY(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.122
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getStringArray(R.array.sg_020309CD);
            }
        },
        SETTING_TARGET_SERVICE_SPECIAL_CARD_FACE(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.123
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getStringArray(R.array.sg_020309CE);
            }
        },
        SETTING_MFC_PACKAGE_NAME(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.124
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030610);
            }
        },
        SETTING_DISCLAIMER_MFS(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.125
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getStringArray(R.array.sg_02030498)[carrierId];
            }
        },
        SETTING_STRING_TERMS_OF_SERVICE_CHECKBOX(FileType.INTERNAL_SG_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.126
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getStringArray(R.array.sg_02030499)[carrierId];
            }
        },
        NET_TOS_APP_ID(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.127
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303CD);
            }
        },
        NET_OSAIFULIFE_PLUS_IMAGE_TRAFFIC_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.128
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303D3);
            }
        },
        NET_OSAIFULIFE_PLUS_IMAGE_EDY_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.129
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303D4);
            }
        },
        NET_OSAIFULIFE_PLUS_IMAGE_NANACO_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.130
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303D5);
            }
        },
        NET_OSAIFULIFE_PLUS_IMAGE_WAON_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.131
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303D6);
            }
        },
        NET_OSAIFULIFE_PLUS_IMAGE_SESSION_TIMEOUT(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.132
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_020303D7));
            }
        },
        NET_OSAIFULIFE_PLUS_IMAGE_RECEIVING_TIMEOUT(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.133
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return Integer.valueOf(res.getInteger(R.integer.sg_020303D8));
            }
        },
        NET_OSAIFULIFE_PLUS_DL_TRAFFIC_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.134
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303D9);
            }
        },
        NET_OSAIFULIFE_PLUS_DL_EDY_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.135
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303DA);
            }
        },
        NET_OSAIFULIFE_PLUS_DL_NANACO_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.136
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303DB);
            }
        },
        NET_OSAIFULIFE_PLUS_DL_WAON_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.137
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303DC);
            }
        },
        NET_ACCOUNT_CHANGE_GUIDANCE_PAGE_URL(FileType.INTERNAL_SG_NET_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.138
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_020303DD);
            }
        },
        MEMORY_CLEAR_TERMS_OF_SERVICE_URL(FileType.INTERNAL_MEMORY_CLEAR_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.139
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getStringArray(R.array.sg_02030E01)[carrierId];
            }
        },
        MEMORY_CLEAR_TERMS_OF_SERVICE_URL_GUIDANCE(FileType.INTERNAL_MEMORY_CLEAR_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.140
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getStringArray(R.array.sg_02030E02)[carrierId];
            }
        },
        MEMORY_CLEAR_TERMS_OF_SERVICE_URL_STRING(FileType.INTERNAL_MEMORY_CLEAR_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.141
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getStringArray(R.array.sg_02030E03)[carrierId];
            }
        },
        MEMORY_CLEAR_DISPLAY_SERVICE_DETAIL_URL(FileType.INTERNAL_MEMORY_CLEAR_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.142
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030E04);
            }
        },
        MEMORY_CLEAR_DISPLAY_SERVICE_NOTES_URL(FileType.INTERNAL_MEMORY_CLEAR_SETTING_XML) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.143
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object getRes(Resources res, int carrierId) {
                return res.getString(R.string.sg_02030E05);
            }
        },
        SETTING_MEMORY_CLEAR_PERMIT_APP_PKG_SETTINGS(FileType.EXTERNAL_COMMON_CFG) { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.Key.144
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            boolean required() {
                return false;
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            String keyId() {
                return "02030004";
            }

            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.Key
            Object inspect(String value) throws InspectException {
                try {
                    DataCheckerUtil.checkAlphaSignFormat(value);
                    DataCheckerUtil.checkByteLength(value, 64, false);
                    return value;
                } catch (DataCheckerException e) {
                    throw new InspectException(e);
                }
            }
        };

        private FileType fileType;

        enum FileType {
            EXTERNAL_MFM_CFG,
            EXTERNAL_COMMON_CFG,
            INTERNAL_SG_DB_XML,
            INTERNAL_SG_FELICA_XML,
            INTERNAL_SG_NET_XML,
            INTERNAL_SG_SETTING_XML,
            INTERNAL_MEMORY_CLEAR_SETTING_XML
        }

        Object translate(String string) {
            return string;
        }

        public FileType getFileType() {
            return this.fileType;
        }

        Key(FileType fileType) {
            this.fileType = fileType;
        }

        String keyId() {
            throw new UnsupportedOperationException("bug");
        }

        boolean required() {
            throw new UnsupportedOperationException("bug");
        }

        Object inspect(String value) throws InspectException {
            throw new UnsupportedOperationException("bug");
        }

        Object getRes(Resources res, int carrierId) {
            throw new UnsupportedOperationException("bug");
        }
    }

    private enum DependencyChecker {
        FELICA_CHIP_VERSION_CHECKER { // from class: com.felicanetworks.mfm.main.policy.sg.Sg.DependencyChecker.1
            @Override // com.felicanetworks.mfm.main.policy.sg.Sg.DependencyChecker
            boolean check() {
                return Settings.getFelicaChipVersion() != null;
            }
        };

        abstract boolean check();

        void inspect() throws InspectException {
            if (!check()) {
                throw new InspectException(InspectException.Type.INVALID_DEPENDENCY, "dependency error");
            }
        }
    }

    static class InspectException extends Exception {
        private Type type;

        enum Type {
            INVALID_LENGTH,
            INVALID_ATTRIBUTE,
            INVALID_RANGE,
            INVALID_DEPENDENCY
        }

        InspectException(DataCheckerException e) {
            super(e);
            int errorId = e.getErrorId();
            if (errorId == 0) {
                this.type = Type.INVALID_LENGTH;
            } else {
                if (errorId != 1) {
                    return;
                }
                this.type = Type.INVALID_ATTRIBUTE;
            }
        }

        InspectException(Type type, String target) {
            super(target);
            this.type = type;
        }

        public Type getType() {
            return this.type;
        }
    }

    private Sg(Context context) {
        this._context = context;
    }

    static {
        HashMap map = new HashMap();
        ISSUER_MAP = map;
        map.put("100001", 0);
        map.put("100002", 1);
        map.put("100003", 2);
        map.put("100008", 3);
        MATCH_PROTOCOL = new String[]{"http:", "https:"};
    }

    public static boolean load(Context context) {
        try {
            Sg sg = new Sg(context);
            _this = sg;
            boolean zInnerLoad = sg.innerLoad();
            if (zInnerLoad) {
                return zInnerLoad;
            }
            return false;
        } catch (Exception unused) {
            return false;
        } finally {
            _this = null;
        }
    }

    private boolean innerLoad() {
        String str;
        Resources resources = this._context.getResources();
        ArrayList arrayList = new ArrayList();
        HashMap map = new HashMap();
        String[] sgPath = getSgPath(resources);
        int length = sgPath.length;
        String str2 = null;
        String str3 = null;
        int i = 0;
        while (true) {
            if (i >= length) {
                str = str2;
                break;
            }
            str = sgPath[i];
            String str4 = str + getCmnFileName(resources);
            try {
                if (new File(str4).exists()) {
                    try {
                        str2 = str + getMfmFileName(resources);
                        str3 = str4;
                        break;
                    } catch (Exception unused) {
                        str3 = str4;
                    }
                } else {
                    continue;
                }
            } catch (Exception unused2) {
            }
            i++;
        }
        if (str3 != null) {
            map.putAll(readCfgFile(str3, arrayList));
        }
        int iIntValue = ISSUER_MAP.get(map.get(Key.FELICA_MFC_ISSUER_CODE.keyId())).intValue();
        if (str2 != null) {
            map.putAll(readCfgFile(str2, arrayList));
        }
        this._testSgMap.putAll(readTestSgFile(resources, str));
        for (Key key : Key.values()) {
            switch (key.getFileType()) {
                case EXTERNAL_MFM_CFG:
                case EXTERNAL_COMMON_CFG:
                    if (map.containsKey(key.keyId())) {
                        try {
                            this._sgMap.put(key, key.inspect((String) map.get(key.keyId())));
                        } catch (InspectException unused3) {
                            arrayList.add("Illegal:" + key);
                        }
                    } else if (key.required()) {
                        arrayList.add("Required:" + key);
                    }
                    break;
                case INTERNAL_SG_DB_XML:
                case INTERNAL_SG_FELICA_XML:
                case INTERNAL_SG_NET_XML:
                case INTERNAL_SG_SETTING_XML:
                case INTERNAL_MEMORY_CLEAR_SETTING_XML:
                    this._sgMap.put(key, key.getRes(resources, iIntValue));
                    break;
            }
        }
        for (DependencyChecker dependencyChecker : DependencyChecker.values()) {
            try {
                dependencyChecker.inspect();
            } catch (InspectException unused4) {
                arrayList.add("Banned Dependency : " + dependencyChecker);
            }
        }
        return arrayList.isEmpty();
    }

    public static Object getValue(Key key) {
        Sg sg = _this;
        if (sg != null) {
            return sg.innerGetValue(key);
        }
        throw new IllegalStateException("Call load() before this method.");
    }

    private Object innerGetValue(Key key) {
        Object obj = this._testSgMap.get(key);
        return obj == null ? this._sgMap.get(key) : obj;
    }

    private String getCmnFileName(Resources res) {
        return res.getString(R.string.sg_02030901);
    }

    private String getMfmFileName(Resources res) {
        return res.getString(R.string.sg_02030902);
    }

    private String[] getSgPath(Resources res) {
        return res.getStringArray(R.array.sg_02030903);
    }

    private Map<String, String> readCfgFile(String path, List<String> errMsg) throws Throwable {
        BufferedReader bufferedReader;
        HashMap map = new HashMap();
        BufferedReader bufferedReader2 = null;
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), StringUtil.UTF_8));
            } catch (IOException unused) {
                return map;
            }
        } catch (IOException unused2) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                if (line.matches(SUPPORTED_LINE_FORMAT) && !line.matches(UNSUPPORTED_LINE_FORMAT)) {
                    String[] strArrSplit = line.split(SEPARATOR);
                    if (strArrSplit.length > 1) {
                        if (map.containsKey(strArrSplit[0])) {
                            errMsg.add("Overlap:" + strArrSplit[0]);
                        } else {
                            map.put(strArrSplit[0], strArrSplit[1]);
                        }
                    }
                }
            }
            bufferedReader.close();
            return map;
        } catch (IOException unused3) {
            bufferedReader2 = bufferedReader;
            if (bufferedReader2 != null) {
                bufferedReader2.close();
            }
            return map;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader2 = bufferedReader;
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (IOException unused4) {
                }
            }
            throw th;
        }
    }

    private Map<Key, Object> readTestSgFile(Resources res, String path) throws Throwable {
        HashMap map = new HashMap();
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path + res.getString(R.string.sg_02030919))), StandardCharsets.UTF_8));
                try {
                    try {
                        StringBuilder sb = new StringBuilder();
                        for (String line = bufferedReader2.readLine(); line != null; line = bufferedReader2.readLine()) {
                            sb.append(line);
                        }
                        JSONObject jSONObject = new JSONObject(sb.toString());
                        for (Key key : Key.values()) {
                            try {
                                Object obj = jSONObject.get(key.name());
                                if (obj instanceof JSONArray) {
                                    JSONArray jSONArray = (JSONArray) obj;
                                    if (jSONArray.get(0) instanceof String) {
                                        ArrayList arrayList = new ArrayList();
                                        for (int i = 0; i < jSONArray.length(); i++) {
                                            arrayList.add(jSONArray.getString(i));
                                        }
                                        map.put(key, arrayList.toArray(new String[0]));
                                    } else if (jSONArray.get(0) instanceof Integer) {
                                        ArrayList arrayList2 = new ArrayList();
                                        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                                            arrayList2.add(Integer.valueOf(jSONArray.getInt(i2)));
                                        }
                                        map.put(key, arrayList2.toArray(new Integer[0]));
                                    }
                                } else if (obj instanceof String) {
                                    map.put(key, key.translate((String) obj));
                                } else if (obj instanceof Integer) {
                                    map.put(key, obj);
                                }
                            } catch (Exception unused) {
                            }
                        }
                        bufferedReader2.close();
                    } catch (IOException | JSONException unused2) {
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        return map;
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th;
                }
            } catch (IOException unused4) {
            }
        } catch (IOException | JSONException unused5) {
        } catch (Throwable th2) {
            th = th2;
        }
        return map;
    }

    private static void checkUrlCharFormat(String target) throws InspectException {
        if (target == null) {
            throw new InspectException(InspectException.Type.INVALID_LENGTH, "target is null");
        }
        for (String str : MATCH_PROTOCOL) {
            if (target.indexOf(str) == 0) {
                if (target.matches("[#-;?-Za-z!=_~]+")) {
                    return;
                }
                throw new InspectException(InspectException.Type.INVALID_ATTRIBUTE, target + " is unmatch URI");
            }
        }
        throw new InspectException(InspectException.Type.INVALID_ATTRIBUTE, target + " is invalid protocol");
    }
}
