package com.felicanetworks.mfm.main.policy.analysis;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.amazonaws.mobileconnectors.pinpoint.internal.event.ClientContext;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.mfi.LogSender;
import com.felicanetworks.mfc.mfi.openidconnect.OpenIdConnectConst;
import com.felicanetworks.mfm.main.LaunchManagementActivity;
import com.felicanetworks.mfm.main.ReceiveNfcTagActivity;
import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.info.AssetInfo;
import com.felicanetworks.mfm.main.model.info.FpAreaInfo;
import com.felicanetworks.mfm.main.model.info.FpServiceInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.NoticeInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccessException;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.policy.analysis.MfsStamp;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.service.IdPicker;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.agent.BannerInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.ExtIcCardInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.FpServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceGroupInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.RecommendInfoAgent;
import com.felicanetworks.mfm.main.presenter.internal.MfiPreference;
import com.felicanetworks.mfm.main.presenter.internal.MfiTapPreference;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;
import com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess;
import com.felicanetworks.mfm.main.presenter.internal.notification.NotificationController;
import com.felicanetworks.mfm.main.presenter.structure.CentralDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.RecommendDetailDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.RequireMissingAppInstallDrawStructure;
import com.felicanetworks.mfm.main.view.activity.BaseActivity;
import com.google.android.gms.fido.fido2.api.common.DevicePublicKeyStringDef;
import com.google.android.material.timepicker.TimeModel;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: loaded from: classes3.dex */
public class MfmStamp {

    interface RequestDataInterface {

        public enum Type4Int {
            LAUNCH_COUNT,
            MAX_DATA_SIZE
        }

        public enum Type4String {
            USER_ID,
            LAUNCH_ID,
            LAUNCH_ID_SWITCH_ACCOUNT,
            MAIN_SCREEN_ID,
            MFM_IDM,
            MFM_IC_CODE,
            PUSH_STATUS,
            LAUNCH_KIND
        }

        int getIntData(Type4Int type);

        String getStringData(Type4String type);

        void setLaunchUnits(String launchUnits);
    }

    static class Data {
        static final String APP_ID_MFC = "0201";
        static final String APP_ID_MFS = "0202";
        static final String EVENT_DATA_KEY_PREFIX = "ex";
        static final String EVENT_GLOBAL_KEY_PREFIX = "g";
        static final String FELICA_LOCK = "lock";
        static final String FELICA_UNLOCK = "unlock";
        static final String LAUNCH_KIND_CARD_LINKAGE = "card";
        static final String LAUNCH_KIND_DIRECT = "direct";
        static final String LAUNCH_KIND_GOOGLE_PLAY = "googleplay";
        static final String LAUNCH_KIND_NORMAL = "normal";
        static final String LAUNCH_KIND_NOTIFICATION = "notification";
        static final String LAUNCH_KIND_REBOOT = "reboot";
        static final String LAUNCH_KIND_TAP_INTERACTION = "tapinteraction";
        static final String RESULT_D = "D";
        static final String RESULT_N = "N";
        static final String RESULT_NO = "no";
        static final String RESULT_Y = "Y";
        static final String RESULT_YES = "yes";
        static final String SERVICE_EXIST = "Y";
        static final String SERVICE_NOT_EXIST = "N";
        static final String SERVICE_UNIDENTIFIED = "U";
        static final String SWITCH_NON_DISP = "non-disp";
        static final String SWITCH_OFF = "off";
        static final String SWITCH_ON = "on";
        static final String TAP_AREA = "area";
        static final String TAP_BUTTON = "button";
        private String _key;
        private String _value;

        enum Type {
            API_LEVEL { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.1
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return LogSender.EXTRA_KEY_API_LEVEL;
                }
            },
            FIRST_TIME_LAUNCH { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.2
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "first_time";
                }
            },
            LAUNCH_KIND { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.3
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "launch_kind";
                }
            },
            APP_ID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.4
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return ClientContext.APP_ID_KEY;
                }
            },
            ISSUE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.5
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "issue";
                }
            },
            IDM { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.6
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "idm";
                }
            },
            IC_CODE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.7
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "ic_code";
                }
            },
            RESULT { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.8
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "result";
                }
            },
            SWITCH { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.9
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "switch";
                }
            },
            AREA_SID_LIST { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.10
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "area_sid_list_";
                }
            },
            APP_SID_LIST { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.11
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "app_sid_list_";
                }
            },
            AREA_SID_LIST_SIZE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.12
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "area_sid_list_size";
                }
            },
            APP_SID_LIST_SIZE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.13
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "app_sid_list_size";
                }
            },
            EMONEY_SID_LIST { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.14
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "emoney_list";
                }
            },
            MFI_SID_LIST { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.15
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "mfi_sid_list_";
                }
            },
            MFI_SID_LIST_SIZE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.16
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "mfi_sid_list_size";
                }
            },
            FELICA_LOCK_STATE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.17
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "lock_state";
                }
            },
            SERVICE_ID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.18
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "sid";
                }
            },
            SERVICE_VERSION { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.19
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "sv";
                }
            },
            RECOMMEND_STATUS { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.20
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return NotificationCompat.CATEGORY_STATUS;
                }
            },
            RECOMMEND_CATEGORY { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.21
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "category";
                }
            },
            NOTIFICATION_ID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.22
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "nid";
                }
            },
            PUSH_STATUS { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.23
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "push_status";
                }
            },
            EXT_CARD_SETTING { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.24
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "ext_card_setting";
                }
            },
            FP_NUM { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.25
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "fp_num";
                }
            },
            FPN_LIST { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.26
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "fpn_list";
                }
            },
            FPN { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.27
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "fpn";
                }
            },
            LOGIN_STATUS { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.28
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "login_status";
                }
            },
            TAP_INTERACTION_STATUS { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.29
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "tap_interaction_status";
                }
            },
            ERROR_ID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.30
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "eid";
                }
            },
            MFC_ERROR_ID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.31
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "mfc_err_id";
                }
            },
            MFC_ERROR_TYPE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.32
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "mfc_err_type";
                }
            },
            GUIDANCE_PATTERN { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.33
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "guidance";
                }
            },
            TAP_LOCATION { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.34
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "tap_location";
                }
            },
            RESOURCES_ID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.35
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "resource";
                }
            },
            ERROR_CODE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.36
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "err_code";
                }
            },
            AREA_REGISTRATION_STATUS { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.37
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "area_registration";
                }
            },
            APP_REGISTRATION_STATUS { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.38
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "app_registration";
                }
            },
            BANNER_ID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.39
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "bid";
                }
            },
            INCOMPLETION { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.40
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "incompletion";
                }
            },
            MFI_SERVICE_FLAG { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.41
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "mfi_service_flag";
                }
            },
            LAUNCH_APP_PKG { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.42
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "launch_app_pkg";
                }
            },
            EXCHANGE_RESULT { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.43
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "exchange_result";
                }
            },
            SCR_LINKAGE_LOCK_STATE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.44
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "scr_linkage_lock_state";
                }
            },
            LAUNCH_SCREEN { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.45
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "launch_screen";
                }
            },
            LOCATION_STATUS { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.46
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "location_status";
                }
            },
            DEVICE_TYPE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.47
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "device_type";
                }
            },
            TAP_POSITION { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.48
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "tap_position";
                }
            },
            CONTACT_KIND { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.49
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "contact_kind";
                }
            },
            IS_SORT { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.50
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "is_sort";
                }
            },
            SERVICE_ORDER_LIST { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.51
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "service_order_list_";
                }
            },
            SERVICE_ORDER_LIST_SIZE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.52
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "service_order_list_size";
                }
            },
            HIDDEN_SID_LIST { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.53
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "hidden_sid_list_";
                }
            },
            HIDDEN_SID_LIST_SIZE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.54
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "hidden_sid_list_size";
                }
            },
            SHOW_RECOMMEND_LOGIN { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.55
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "show_recommend_login";
                }
            },
            SHOW_NFC_OFF_WARNING { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.56
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "show_nfc_off_warning";
                }
            },
            DELETED_TRANSIT_CARD { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.57
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "deleted_transit_card";
                }
            },
            CARD_TYPE_LIST { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.58
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "card_type_list_";
                }
            },
            CARD_STATUS_LIST { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.59
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "card_status_list";
                }
            },
            CARD_ADDINFO_BROKEN_SID_LIST { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.60
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "additionalinfo_broken_sid_list";
                }
            },
            CARD_NUM_LIST { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.61
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "card_num_list";
                }
            },
            TERMS_VERSION { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.62
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "terms_version";
                }
            },
            LOGIN_RESULT { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.63
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "login_result";
                }
            },
            NFC_TYPE_LIST { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.64
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "nfc_type_list";
                }
            },
            AC_NOTIFICATION_STATUS { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.65
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "ac_notification_status";
                }
            },
            OS_NOTIFICATIONS_PERMISSION_STATUS { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.66
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "os_notifications_permission_status";
                }
            },
            TAP_INTERACTION_GET_INFORMATION_STATUS { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.67
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type
                String key() {
                    return "tap_interaction_get_information_status";
                }
            };

            /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR (r1v0 java.lang.String), (r2v0 int) A[MD:(java.lang.String, int):void (m)] (LINE:146) call: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Type.<init>(java.lang.String, int):void type: THIS */
            /* synthetic */ Type(AnonymousClass1 anonymousClass1) {
                this();
            }

            String key() {
                throw new UnsupportedOperationException("not define key!!!");
            }
        }

        enum Global {
            USER_ID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Global.1
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Global
                String key() {
                    return "uid";
                }
            },
            LAUNCH_ID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Global.2
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Global
                String key() {
                    return "mfm_lid";
                }
            },
            SCREEN_ID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Global.3
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Global
                String key() {
                    return "wn";
                }
            };

            /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR (r1v0 java.lang.String), (r2v0 int) A[MD:(java.lang.String, int):void (m)] (LINE:317) call: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Data.Global.<init>(java.lang.String, int):void type: THIS */
            /* synthetic */ Global(AnonymousClass1 anonymousClass1) {
                this();
            }

            String key() {
                throw new UnsupportedOperationException("not define global key!!!");
            }
        }

        Data(String key, String value, boolean addEventPrefix) {
            if (addEventPrefix) {
                this._key = "ex." + key;
            } else {
                this._key = key;
            }
            this._value = value;
        }

        Data(String key, String value) {
            this(key, value, true);
        }

        Data(Type type, String value) {
            this(type.key(), value);
        }

        Data(Type type, int value) {
            this(type, String.valueOf(value));
        }

        Data(Global g, String value) {
            this._key = "g." + g.key();
            this._value = value;
        }

        public String getKey() {
            return this._key;
        }

        public String getValue() {
            return this._value;
        }
    }

    public enum Event {
        AUTO_DUMP_LAUNCH { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.1
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "launch";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                String str;
                String str2;
                Intent intent = (Intent) args[0];
                Context context = (Context) args[1];
                String str3 = (String) Sg.getValue(Sg.Key.SETTING_MFM_ID);
                String str4 = (String) Sg.getValue(Sg.Key.FELICA_MFC_ISSUER_CODE);
                String strEvent = null;
                if (intent.getAction() != null && intent.getAction().equals("android.intent.action.MAIN") && intent.hasCategory("android.intent.category.INFO")) {
                    str = "googleplay";
                } else if (intent.hasExtra(NotificationController.NOTIFICATION_ID_KEY) || intent.hasExtra(NoticeInfo.INTENT_EXTRAS_KEY_MESSAGE_BODY)) {
                    str = "notification";
                } else if (intent.getAction() != null && intent.getAction().equals("android.nfc.action.TECH_DISCOVERED")) {
                    str = "card";
                } else if (intent.hasExtra(BaseActivity.EXTRA_KEY_REBOOT)) {
                    str = "reboot";
                } else if (intent.hasExtra("com.felicanetworks.mfm.main.tapinteraction")) {
                    str = "tapinteraction";
                } else if (LaunchManagementActivity.isDirectLaunch(intent)) {
                    int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$LaunchManagementActivity$DirectType[LaunchManagementActivity.findDirectType(intent).ordinal()];
                    if (i == 1) {
                        strEvent = SCREEN_W1_17_01.event();
                    } else if (i == 2) {
                        strEvent = SCREEN_W1_02_01.event();
                    }
                    str = DevicePublicKeyStringDef.DIRECT;
                } else {
                    str = "normal";
                }
                this._request.setLaunchUnits(str);
                this._datalist.add(new Data(Data.Type.LAUNCH_KIND, str));
                if (this._request.getIntData(RequestDataInterface.Type4Int.LAUNCH_COUNT) == 0) {
                    this._datalist.add(new Data(Data.Type.FIRST_TIME_LAUNCH, "yes"));
                } else {
                    this._datalist.add(new Data(Data.Type.FIRST_TIME_LAUNCH, "no"));
                }
                this._datalist.add(new Data(Data.Type.API_LEVEL, Build.VERSION.SDK_INT));
                this._datalist.add(new Data(Data.Type.APP_ID, str3));
                this._datalist.add(new Data(Data.Type.ISSUE, str4));
                this._datalist.add(new Data(Data.Type.IDM, this._request.getStringData(RequestDataInterface.Type4String.MFM_IDM)));
                this._datalist.add(new Data(Data.Type.IC_CODE, this._request.getStringData(RequestDataInterface.Type4String.MFM_IC_CODE)));
                this._datalist.add(new Data(Data.Type.PUSH_STATUS, this._request.getStringData(RequestDataInterface.Type4String.PUSH_STATUS)));
                if (PresenterData.getInstance().hasNFC()) {
                    int componentEnabledSetting = context.getPackageManager().getComponentEnabledSetting(new ComponentName(context, (Class<?>) ReceiveNfcTagActivity.class));
                    if (componentEnabledSetting == 1 || componentEnabledSetting == 0) {
                        this._datalist.add(new Data(Data.Type.EXT_CARD_SETTING, DebugKt.DEBUG_PROPERTY_VALUE_ON));
                    } else {
                        this._datalist.add(new Data(Data.Type.EXT_CARD_SETTING, DebugKt.DEBUG_PROPERTY_VALUE_OFF));
                    }
                } else {
                    this._datalist.add(new Data(Data.Type.EXT_CARD_SETTING, "non-disp"));
                }
                if (MfiPreference.getInstance(context).directLoadMfiAccountName() == null) {
                    this._datalist.add(new Data(Data.Type.LOGIN_STATUS, "N"));
                } else {
                    this._datalist.add(new Data(Data.Type.LOGIN_STATUS, "Y"));
                }
                if (MfiTapPreference.getInstance().loadTapInteractionFlag1(context)) {
                    str2 = MfiTapPreference.Flag2Value.TI_NOTIFICATION_DISPLAY_MODE_CARD == MfiTapPreference.getInstance().loadTapInteractionFlag2(context) ? "D" : "Y";
                } else {
                    str2 = "N";
                }
                this._datalist.add(new Data(Data.Type.TAP_INTERACTION_STATUS, str2));
                this._datalist.add(new Data(Data.Type.DEVICE_TYPE, Sg.getValue(Sg.Key.SETTING_DEVICE_TYPE) == null ? "0" : String.valueOf(Sg.getValue(Sg.Key.SETTING_DEVICE_TYPE))));
                this._datalist.add(new Data(Data.Type.LAUNCH_SCREEN, strEvent));
                LocationManager locationManager = (LocationManager) context.getSystemService(FirebaseAnalytics.Param.LOCATION);
                this._datalist.add(new Data(Data.Type.LOCATION_STATUS, (locationManager == null || !locationManager.isProviderEnabled("gps")) ? "N" : "Y"));
                this._datalist.add(new Data(Data.Type.AC_NOTIFICATION_STATUS, ServicePreference.getInstance().loadAccountChangeHistoryNotificationSetting(context) ? "Y" : "N"));
                if (Build.VERSION.SDK_INT >= 33) {
                    this._datalist.add(new Data(Data.Type.OS_NOTIFICATIONS_PERMISSION_STATUS, context.checkSelfPermission("android.permission.POST_NOTIFICATIONS") == 0 ? "Y" : "N"));
                }
                this._datalist.add(new Data(Data.Type.TAP_INTERACTION_GET_INFORMATION_STATUS, MfiTapPreference.getInstance().loadStopChipAccessFlag(context) ? "N" : "Y"));
            }
        },
        AUTO_DUMP_RECEIVE_MESSAGE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.2
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            boolean immediate() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            boolean receiver() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.RECEIVER_DUMP;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "receive_message";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                NoticeInfo noticeInfo = (NoticeInfo) args[0];
                boolean zBooleanValue = ((Boolean) args[1]).booleanValue();
                this._datalist.add(new Data(Data.Type.NOTIFICATION_ID, noticeInfo.getId()));
                this._datalist.add(new Data(Data.Type.PUSH_STATUS, this._request.getStringData(RequestDataInterface.Type4String.PUSH_STATUS)));
                this._datalist.add(new Data(Data.Type.RESULT, zBooleanValue ? "yes" : "no"));
            }
        },
        AUTO_DUMP_RECEIVE_TAG { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.3
            final String DIV = ",";
            final String FILTER = "android.nfc.tech.";

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "receive_tag";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                List listAsList = Arrays.asList(((Tag) args[0]).getTechList());
                int intData = this._request.getIntData(RequestDataInterface.Type4Int.MAX_DATA_SIZE);
                StringBuilder sb = new StringBuilder(intData);
                Iterator it = listAsList.iterator();
                while (it.hasNext()) {
                    String strReplace = ((String) it.next()).replace("android.nfc.tech.", "");
                    if (intData < sb.length() + strReplace.length()) {
                        break;
                    }
                    sb.append(strReplace);
                    if (intData >= sb.length() + 1) {
                        sb.append(",");
                    }
                }
                this._datalist.add(new Data(Data.Type.NFC_TYPE_LIST, sb.toString().replaceAll(",$", "")));
            }
        },
        AUTO_DUMP_RESULT_SIM { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.4
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "result_create_services";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                CentralFunc.CompiledResult compiledResult = (CentralFunc.CompiledResult) args[0];
                List<String> list = compiledResult.areaHitIds;
                List<String> list2 = compiledResult.sasHitIds;
                List<String> list3 = compiledResult.appHitIds;
                ArrayList arrayList = new ArrayList();
                Iterator it = ((List) args[1]).iterator();
                while (it.hasNext()) {
                    arrayList.add(((AssetInfo) it.next()).getServiceId());
                }
                CentralFunc.CompiledState.FelicaState felicaState = ((CentralFunc.CompiledState) args[2]).getFelicaState();
                ModelContext modelContext = (ModelContext) args[3];
                ArrayList arrayList2 = new ArrayList();
                arrayList2.addAll(list);
                arrayList2.addAll(list2);
                List<String> listPickupUniqueId = pickupUniqueId(arrayList2);
                List<String> listPickupUniqueId2 = pickupUniqueId(new ArrayList(list3));
                List<String> hiddenServiceIds = MyServiceDatabaseAccess.getInstance().getHiddenServiceIds();
                List<String> sortOrderGroupIds = MyServiceDatabaseAccess.getInstance().getSortOrderGroupIds();
                boolean zIsEmpty = true ^ sortOrderGroupIds.isEmpty();
                applySidListToDataList(Data.Type.AREA_SID_LIST, listPickupUniqueId);
                applySidListToDataList(Data.Type.APP_SID_LIST, listPickupUniqueId2);
                applySidListToDataList(Data.Type.HIDDEN_SID_LIST, hiddenServiceIds);
                this._datalist.add(new Data(Data.Type.AREA_SID_LIST_SIZE, listPickupUniqueId.size()));
                this._datalist.add(new Data(Data.Type.APP_SID_LIST_SIZE, listPickupUniqueId2.size()));
                this._datalist.add(new Data(Data.Type.HIDDEN_SID_LIST_SIZE, hiddenServiceIds.size()));
                applySidListToDataList(Data.Type.EMONEY_SID_LIST, arrayList);
                this._datalist.add(new Data(Data.Type.FELICA_LOCK_STATE, CentralFunc.CompiledState.FelicaState.LOCK_ERROR == felicaState ? "lock" : "unlock"));
                this._datalist.add(new Data(Data.Type.SCR_LINKAGE_LOCK_STATE, Settings.isScreenLock(modelContext.getAndroidContext()) ? "Y" : "N"));
                this._datalist.add(new Data(Data.Type.IDM, this._request.getStringData(RequestDataInterface.Type4String.MFM_IDM)));
                this._datalist.add(new Data(Data.Type.IC_CODE, this._request.getStringData(RequestDataInterface.Type4String.MFM_IC_CODE)));
                this._datalist.add(new Data(Data.Type.IS_SORT, String.valueOf(zIsEmpty)));
                applySidListToDataList(Data.Type.SERVICE_ORDER_LIST, sortOrderGroupIds);
                this._datalist.add(new Data(Data.Type.SERVICE_ORDER_LIST_SIZE, sortOrderGroupIds.size()));
            }

            private List<String> pickupUniqueId(List<String> srcList) {
                ArrayList arrayList = new ArrayList();
                for (String str : srcList) {
                    if (!arrayList.contains(str)) {
                        arrayList.add(str);
                    }
                }
                return arrayList;
            }

            private void applySidListToDataList(Data.Type dataType, List<String> sidList) {
                if (sidList.size() != 0) {
                    int intData = this._request.getIntData(RequestDataInterface.Type4Int.MAX_DATA_SIZE);
                    StringBuilder sb = new StringBuilder(intData);
                    int i = 0;
                    int i2 = 0;
                    while (i < sidList.size()) {
                        if (intData > sb.length() + sidList.get(i).length() + 1) {
                            sb.append(sidList.get(i));
                            sb.append(",");
                        } else {
                            sb.deleteCharAt(sb.length() - 1);
                            this._datalist.add(new Data(dataType.key() + i2, sb.toString()));
                            i += -1;
                            i2++;
                            sb.delete(0, sb.length());
                        }
                        i++;
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    this._datalist.add(new Data(dataType.key() + i2, sb.toString()));
                }
            }
        },
        AUTO_DUMP_RESULT_MFI { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.5
            final String DIV = ",";

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "result_create_mfi_services";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                String serviceName;
                String str;
                CentralDrawStructure centralDrawStructure = (CentralDrawStructure) args[0];
                List<String> uniqueServiceIds = centralDrawStructure.getMfiCardFunc().getUniqueServiceIds();
                ArrayList arrayList = new ArrayList();
                StringBuilder sb = new StringBuilder();
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                Iterator<MyServiceGroupInfoAgent> it = centralDrawStructure.getCentralFunc().getMyServiceGroupInfoList().iterator();
                while (it.hasNext()) {
                    arrayList3.addAll(it.next().getServices());
                }
                Iterator<String> it2 = uniqueServiceIds.iterator();
                while (it2.hasNext()) {
                    MyServiceInfoAgent myServiceInfoAgent = (MyServiceInfoAgent) IdPicker.pickupWithSid(arrayList3, it2.next());
                    if (myServiceInfoAgent == null) {
                        serviceName = "";
                        str = "";
                    } else {
                        serviceName = myServiceInfoAgent.getServiceName();
                        str = myServiceInfoAgent.isActiveService() ? "1" : "2";
                        if (myServiceInfoAgent.hasAdditionalInfoIllegalCard()) {
                            arrayList2.add(myServiceInfoAgent.getId());
                        }
                    }
                    arrayList.add(serviceName);
                    if (!TextUtils.isEmpty(sb)) {
                        sb.append(",");
                    }
                    sb.append(str);
                }
                applyStringListToDataList(Data.Type.MFI_SID_LIST, uniqueServiceIds);
                this._datalist.add(new Data(Data.Type.MFI_SID_LIST_SIZE, uniqueServiceIds.size()));
                applyStringListToDataList(Data.Type.CARD_TYPE_LIST, arrayList);
                if (!TextUtils.isEmpty(sb)) {
                    this._datalist.add(new Data(Data.Type.CARD_STATUS_LIST, sb.toString()));
                }
                applyStringListToDataString(Data.Type.CARD_ADDINFO_BROKEN_SID_LIST, arrayList2);
            }

            private void applyStringListToDataList(Data.Type dataType, List<String> list) {
                if (list.size() != 0) {
                    int intData = this._request.getIntData(RequestDataInterface.Type4Int.MAX_DATA_SIZE);
                    StringBuilder sb = new StringBuilder(intData);
                    int i = 0;
                    int i2 = 0;
                    while (i < list.size()) {
                        if (intData > sb.length() + list.get(i).length() + 1) {
                            sb.append(list.get(i));
                            sb.append(",");
                        } else {
                            sb.deleteCharAt(sb.length() - 1);
                            this._datalist.add(new Data(dataType.key() + i2, sb.toString()));
                            i += -1;
                            i2++;
                            sb.delete(0, sb.length());
                        }
                        i++;
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    this._datalist.add(new Data(dataType.key() + i2, sb.toString()));
                }
            }

            private void applyStringListToDataString(Data.Type dataType, List<String> list) {
                if (list.size() != 0) {
                    int intData = this._request.getIntData(RequestDataInterface.Type4Int.MAX_DATA_SIZE);
                    StringBuilder sb = new StringBuilder(intData);
                    for (int i = 0; i < list.size() && intData >= sb.length() + list.get(i).length(); i++) {
                        sb.append(list.get(i));
                        if (intData >= sb.length() + 1) {
                            sb.append(",");
                        }
                    }
                    String strReplaceAll = sb.toString().replaceAll(",$", "");
                    if (strReplaceAll.isEmpty()) {
                        return;
                    }
                    this._datalist.add(new Data(dataType.key(), strReplaceAll));
                }
            }
        },
        AUTO_DUMP_OPEN_MESSAGE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.6
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "open_message";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.NOTIFICATION_ID, ((NoticeInfo) args[0]).getId()));
                this._datalist.add(new Data(Data.Type.LAUNCH_KIND, this._request.getStringData(RequestDataInterface.Type4String.LAUNCH_KIND)));
            }
        },
        AUTO_DUMP_NEW_ARRIVALS { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.7
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "new_arrivals";
            }
        },
        AUTO_DUMP_NFC_IO_ERROR { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.8
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "nfc_io_error";
            }
        },
        AUTO_DUMP_NFC_ILLEGALSTATE_ERROR { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.9
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "nfc_illegalstate_error";
            }
        },
        AUTO_DUMP_RESULT_CREATE_FP_SERVICES { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.10
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "result_create_fp_services";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                FpAreaInfo fpAreaInfo = (FpAreaInfo) args[0];
                StringBuffer stringBuffer = new StringBuffer();
                Iterator<FpServiceInfo> it = fpAreaInfo.getFpServiceList().iterator();
                while (it.hasNext()) {
                    stringBuffer.append(String.format(Locale.US, "%08d", Integer.valueOf(it.next().getServiceNumber()))).append(",");
                }
                if (stringBuffer.length() != 0) {
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                }
                this._datalist.add(new Data(Data.Type.FPN_LIST, new String(stringBuffer)));
                this._datalist.add(new Data(Data.Type.FP_NUM, String.format(Locale.US, "%010d", Long.valueOf(fpAreaInfo.getFpNum()))));
            }
        },
        AUTO_DUMP_ACCOUNT_SWITCHING { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.11
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "account_switching";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.EXCHANGE_RESULT, ((Integer) args[0]).intValue()));
            }
        },
        AUTO_DUMP_RESULT_FIRST_LOGIN { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.12
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "result_first_login";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.LOGIN_RESULT, ((Integer) args[0]).intValue()));
            }
        },
        AUTO_DUMP_EXECUTE_CLEAR_MEMORY { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.13
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "execute_clear_memory";
            }
        },
        ACTION_REQUEST_APP_UPDATE_YES { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.14
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "request_app_update";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT, "yes");
            }
        },
        ACTION_REQUEST_APP_UPDATE_NO { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.15
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "request_app_update";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT, "no");
            }
        },
        ACTION_REQUEST_APP_INSTALL { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.16
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "request_app_install";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                String str;
                RequireMissingAppInstallDrawStructure requireMissingAppInstallDrawStructure = (RequireMissingAppInstallDrawStructure) args[0];
                boolean zBooleanValue = ((Boolean) args[1]).booleanValue();
                int missingApp = requireMissingAppInstallDrawStructure.getMissingApp();
                if (missingApp == 1) {
                    str = "0201";
                } else {
                    str = missingApp != 2 ? "" : "0202";
                }
                this._datalist.add(new Data(Data.Type.RESULT, zBooleanValue ? "yes" : "no"));
                this._datalist.add(new Data(Data.Type.APP_ID, str));
            }
        },
        ACTION_BUTTON_NEXT { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.17
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "button_next";
            }
        },
        ACTION_BUTTON_NEXT_AT_W1_01_11 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.18
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "button_next";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                String str;
                boolean zBooleanValue = ((Boolean) args[0]).booleanValue();
                List<Data> list = this._datalist;
                Data.Type type = Data.Type.EXT_CARD_SETTING;
                if (zBooleanValue) {
                    str = DebugKt.DEBUG_PROPERTY_VALUE_ON;
                } else {
                    str = DebugKt.DEBUG_PROPERTY_VALUE_OFF;
                }
                list.add(new Data(type, str));
            }
        },
        ACTION_BUTTON_NEXT_AT_W1_01_12 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.19
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "button_next";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                String str;
                boolean zBooleanValue = ((Boolean) args[0]).booleanValue();
                List<Data> list = this._datalist;
                Data.Type type = Data.Type.PUSH_STATUS;
                if (zBooleanValue) {
                    str = "Y";
                } else {
                    str = "N";
                }
                list.add(new Data(type, str));
            }
        },
        ACTION_DISCLAIMER_EMONEY { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.20
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "disclaimer_emoney";
            }
        },
        ACTION_RECREATE_SERVICES { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.21
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "recreate_services";
            }
        },
        ACTION_LOCK_SETTING { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.22
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "lock_setting";
            }
        },
        ACTION_NEW_ARRIVALS { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.23
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "new_arrivals";
            }
        },
        ACTION_START_MYSERVICE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.24
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "start_myservice";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                String str;
                MyServiceInfoAgent myServiceInfoAgent = (MyServiceInfoAgent) args[0];
                this._datalist.add(new Data(Data.Type.SERVICE_ID, myServiceInfoAgent.getId()));
                this._datalist.add(new Data(Data.Type.SERVICE_VERSION, myServiceInfoAgent.getVersion()));
                this._datalist.add(new Data(Data.Type.IDM, this._request.getStringData(RequestDataInterface.Type4String.MFM_IDM)));
                List<Data> list = this._datalist;
                Data.Type type = Data.Type.INCOMPLETION;
                if (myServiceInfoAgent.getLeadType() == MyServiceInfoAgent.LeadType.REGISTER_SERVICE) {
                    str = "Y";
                } else {
                    str = "N";
                }
                list.add(new Data(type, str));
                this._datalist.add(new Data(Data.Type.MFI_SERVICE_FLAG, myServiceInfoAgent.getMfiServiceFlag().ordinal()));
            }
        },
        ACTION_SHOW_RECOMMEND { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.25
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "show_recommend";
            }
        },
        ACTION_SHOW_DRAWER { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.26
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "show_drawer";
            }
        },
        ACTION_BANNER_MYSERVICE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.27
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "banner_myservice";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                String str;
                BannerInfoAgent bannerInfoAgent = (BannerInfoAgent) args[0];
                try {
                    str = (String) Sg.getValue(Sg.Key.FELICA_MFC_ISSUER_CODE);
                } catch (Exception unused) {
                    str = "";
                }
                this._datalist.add(new Data(Data.Type.BANNER_ID, bannerInfoAgent.getId()));
                this._datalist.add(new Data(Data.Type.ISSUE, str));
                this._datalist.add(new Data(Data.Type.IDM, this._request.getStringData(RequestDataInterface.Type4String.MFM_IDM)));
            }
        },
        ACTION_SWIPE_REFRESH_SERVICES { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.28
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "swipe_refresh_services";
            }
        },
        ACTION_SHOW_CARD_DETAIL_SERVICE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.29
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "show_card_detail_service";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SERVICE_ID, (String) args[0]));
            }
        },
        ACTION_SHOW_CARD_DETAIL_GROUP { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.30
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "show_card_detail_group";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SERVICE_ID, (String) args[0]));
            }
        },
        ACTION_SHOW_CARD_DETAIL_BACKGROUND { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.31
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "show_card_detail_background";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SERVICE_ID, (String) args[0]));
            }
        },
        ACTION_RECOMMEND_LOGIN { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.32
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "recommend_login";
            }
        },
        ACTION_HIDE_RECOMMEND_LOGIN { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.33
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "hide_recommend_login";
            }
        },
        ACTION_RECOMMEND_LOGIN_ON_CARD { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.34
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "recommend_login_on_card";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SERVICE_ID, ((MyServiceInfoAgent) args[0]).getId()));
            }
        },
        ACTION_NFC_OFF_WARNING { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.35
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "nfc_off_warning";
            }
        },
        ACTION_SHOW_DELETED_CARD_LIST { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.36
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "show_deleted_card_list";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SERVICE_ID, (String) args[0]));
            }
        },
        ACTION_SHOW_DETAIL { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.37
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "show_detail";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                RecommendInfoAgent recommendInfoAgent = (RecommendInfoAgent) args[0];
                this._datalist.add(new Data(Data.Type.SERVICE_ID, recommendInfoAgent.getId()));
                this._datalist.add(new Data(Data.Type.SERVICE_VERSION, recommendInfoAgent.getVersion()));
                this._datalist.add(new Data(Data.Type.RECOMMEND_CATEGORY, recommendInfoAgent.getCategoryId()));
                this._datalist.add(new Data(Data.Type.RECOMMEND_STATUS, recommendInfoAgent.getStatus()));
            }
        },
        ACTION_START_RECOMMEND_SERVICE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.38
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "start_recommendservice";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                String str;
                String str2;
                RecommendDetailDrawStructure recommendDetailDrawStructure = (RecommendDetailDrawStructure) args[0];
                RecommendInfoAgent recommendInfo = recommendDetailDrawStructure.getRecommendInfo();
                CentralFuncAgent centralFunc = recommendDetailDrawStructure.getCentralFunc();
                if (!centralFunc.isCompiled().booleanValue()) {
                    str = "U";
                    str2 = "U";
                } else {
                    Iterator<MyServiceGroupInfoAgent> it = centralFunc.getMyServiceGroupInfoList().iterator();
                    str = "N";
                    str2 = str;
                    while (it.hasNext()) {
                        for (MyServiceInfoAgent myServiceInfoAgent : it.next().getServices()) {
                            if (TextUtils.equals(recommendInfo.getId(), myServiceInfoAgent.getId())) {
                                MyServiceInfo client = myServiceInfoAgent.getClient();
                                String str3 = MyServiceInfo.RegisterState.REGISTERED == client.getRegisterState() ? "Y" : "N";
                                String str4 = MyServiceInfo.RegisterState.REGISTERED != client.getAppInstalledState() ? "N" : "Y";
                                str = str3;
                                str2 = str4;
                            }
                        }
                    }
                }
                this._datalist.add(new Data(Data.Type.SERVICE_ID, recommendInfo.getId()));
                this._datalist.add(new Data(Data.Type.SERVICE_VERSION, recommendInfo.getVersion()));
                this._datalist.add(new Data(Data.Type.RECOMMEND_CATEGORY, recommendInfo.getCategoryId()));
                this._datalist.add(new Data(Data.Type.RECOMMEND_STATUS, recommendInfo.getStatus()));
                this._datalist.add(new Data(Data.Type.IDM, this._request.getStringData(RequestDataInterface.Type4String.MFM_IDM)));
                this._datalist.add(new Data(Data.Type.AREA_REGISTRATION_STATUS, str));
                this._datalist.add(new Data(Data.Type.APP_REGISTRATION_STATUS, str2));
            }
        },
        ACTION_BANNER_NOTICE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.39
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "banner_notice";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                String str;
                BannerInfoAgent bannerInfoAgent = (BannerInfoAgent) args[0];
                try {
                    str = (String) Sg.getValue(Sg.Key.FELICA_MFC_ISSUER_CODE);
                } catch (Exception unused) {
                    str = "";
                }
                this._datalist.add(new Data(Data.Type.BANNER_ID, bannerInfoAgent.getId()));
                this._datalist.add(new Data(Data.Type.ISSUE, str));
                this._datalist.add(new Data(Data.Type.IDM, this._request.getStringData(RequestDataInterface.Type4String.MFM_IDM)));
            }
        },
        ACTION_MESSAGE_LINK_AREA { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.40
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "message_link";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.NOTIFICATION_ID, ((NoticeInfoAgent) args[0]).getId()));
                this._datalist.add(new Data(Data.Type.IDM, this._request.getStringData(RequestDataInterface.Type4String.MFM_IDM)));
                this._datalist.add(new Data(Data.Type.TAP_LOCATION, "area"));
            }
        },
        ACTION_MESSAGE_LINK_BUTTON { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.41
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "message_link";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.NOTIFICATION_ID, ((NoticeInfoAgent) args[0]).getId()));
                this._datalist.add(new Data(Data.Type.IDM, this._request.getStringData(RequestDataInterface.Type4String.MFM_IDM)));
                this._datalist.add(new Data(Data.Type.TAP_LOCATION, "button"));
            }
        },
        ACTION_NOTES { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.42
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "notes";
            }
        },
        ACTION_NFC_SETTINGS_YES { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.43
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "nfc_settings";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.RESULT, "yes"));
            }
        },
        ACTION_NFC_SETTINGS_NO { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.44
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "nfc_settings";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.RESULT, "no"));
            }
        },
        ACTION_AUTO_READ_SETTINGS_LINK { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.45
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "auto_read_settings_link";
            }
        },
        ACTION_MENU_SETTINGS { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.46
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "menu_settings";
            }
        },
        ACTION_SHOW_FP { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.47
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "show_fp";
            }
        },
        ACTION_JUMP_OSAIFU_VIA_IMAGE_AREA { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.48
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "jump_osaifu_via_image";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SERVICE_ID, ((ExtIcCardInfoAgent) args[0]).getId()));
                this._datalist.add(new Data(Data.Type.TAP_LOCATION, "area"));
            }
        },
        ACTION_JUMP_OSAIFU_VIA_IMAGE_BUTTON { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.49
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "jump_osaifu_via_image";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SERVICE_ID, ((ExtIcCardInfoAgent) args[0]).getId()));
                this._datalist.add(new Data(Data.Type.TAP_LOCATION, "button"));
            }
        },
        ACTION_DISCLAIMER_FELICA { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.50
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "disclaimer_felica";
            }
        },
        ACTION_DISCLAIMER_MFM { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.51
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "disclaimer_mfm";
            }
        },
        ACTION_DELETE_ACCOUNT { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.52
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "delete_account";
            }
        },
        ACTION_COPY_SEID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.53
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "copy_seid";
            }
        },
        ACTION_NAVI_MYSERVICE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.54
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "navi_myservice";
            }
        },
        ACTION_NAVI_RECOMMEND { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.55
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "navi_recommend";
            }
        },
        ACTION_NAVI_NOTICE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.56
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "navi_notice";
            }
        },
        ACTION_NAVI_READER { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.57
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "navi_reader";
            }
        },
        ACTION_NAVI_HOWTO_CHANGE_MODEL { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.58
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "howto_change_model";
            }
        },
        ACTION_RECOMMEND_LOGIN_ON_DRAWER { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.59
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "recommend_login_on_drawer";
            }
        },
        ACTION_CHANGE_ACCOUNT_ON_DRAWER { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.60
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "change_account_on_drawer";
            }
        },
        ACTION_PERMIT_NOTIFICATION_SETTING { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.61
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "permit_notification_setting";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SWITCH, ((Boolean) args[0]).booleanValue() ? DebugKt.DEBUG_PROPERTY_VALUE_ON : DebugKt.DEBUG_PROPERTY_VALUE_OFF));
            }
        },
        ACTION_NFC_SETTING { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.62
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "nfc_setting";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SWITCH, ((Boolean) args[0]).booleanValue() ? DebugKt.DEBUG_PROPERTY_VALUE_ON : DebugKt.DEBUG_PROPERTY_VALUE_OFF));
            }
        },
        ACTION_AC_NOTIFICATION_SETTING { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.63
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "ac_notification_setting";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SWITCH, ((Boolean) args[0]).booleanValue() ? DebugKt.DEBUG_PROPERTY_VALUE_ON : DebugKt.DEBUG_PROPERTY_VALUE_OFF));
            }
        },
        ACTION_MFI_TAP_INTERACTION_SETTING { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.64
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "tap_interaction_setting";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SWITCH, ((Boolean) args[0]).booleanValue() ? DebugKt.DEBUG_PROPERTY_VALUE_ON : DebugKt.DEBUG_PROPERTY_VALUE_OFF));
            }
        },
        ACTION_RESTRICT_CARD_READ { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.65
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "restrict_card_read";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SWITCH, ((Boolean) args[0]).booleanValue() ? DebugKt.DEBUG_PROPERTY_VALUE_ON : DebugKt.DEBUG_PROPERTY_VALUE_OFF));
            }
        },
        ACTION_TAP_INTERACTION_ANALYSIS_SETTING { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.66
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "tap_interaction_analysis_setting";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SWITCH, ((Boolean) args[0]).booleanValue() ? DebugKt.DEBUG_PROPERTY_VALUE_ON : DebugKt.DEBUG_PROPERTY_VALUE_OFF));
            }
        },
        ACTION_OS_MANAGEMENT_SCREEN_GUIDANCE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.67
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "os_management_screen_guidance";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.RESULT, ((Boolean) args[0]).booleanValue() ? "yes" : "no"));
            }
        },
        ACTION_SERVICE_FP { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.68
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "service_fp";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.FPN, String.format(Locale.US, "%08d", Integer.valueOf(((FpServiceInfoAgent) args[0]).getServiceNumber()))));
            }
        },
        ACTION_WEB_SITE_FP { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.69
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "show_website_fp";
            }
        },
        ACTION_SHOW_RECOMMEND_FP_BUTTON { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.70
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "show_recommend_fp";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.TAP_LOCATION, "button"));
            }
        },
        ACTION_SHOW_RECOMMEND_FP_AREA { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.71
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "show_recommend_fp";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.TAP_LOCATION, "area"));
            }
        },
        ACTION_CANCEL_SWITCH_ACCOUNT { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.72
            String callingPackageName;
            String myPackageName;

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "cancel_switch_account";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this.callingPackageName = (String) args[0];
                this.myPackageName = ((Activity) args[1]).getPackageName();
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            boolean isCallOtherApp() {
                return !this.myPackageName.equals(this.callingPackageName);
            }
        },
        ACTION_ENABLE_CARD { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.73
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "enable_card";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SERVICE_ID, (String) args[0]));
            }
        },
        ACTION_RECOVERY_CARD { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.74
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "recovery_card";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SERVICE_ID, (String) args[0]));
            }
        },
        ACTION_DELETE_CARD { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.75
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "delete_card";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SERVICE_ID, (String) args[0]));
            }
        },
        ACTION_LINKAGE_APP { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.76
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "linkage_app";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.TAP_POSITION, String.valueOf(((Integer) args[0]).intValue())));
            }
        },
        ACTION_LINKAGE_SITE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.77
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "linkage_site";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.TAP_POSITION, String.valueOf(((Integer) args[0]).intValue())));
            }
        },
        ACTION_LINKAGE_CONTACT { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.78
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "linkage_contact";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                String str;
                Intent intent = (Intent) args[0];
                if (intent.getAction() == null) {
                    return;
                }
                String action = intent.getAction();
                action.hashCode();
                switch (action) {
                    case "android.intent.action.DIAL":
                        str = "0";
                        break;
                    case "android.intent.action.VIEW":
                        str = "1";
                        break;
                    case "android.intent.action.SENDTO":
                        str = "2";
                        break;
                    default:
                        return;
                }
                this._datalist.add(new Data(Data.Type.CONTACT_KIND, str));
            }
        },
        ACTION_REFRESH_CARD_DETAIL { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.79
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "refresh_card_detail";
            }
        },
        ACTION_ACCOUNT_NAME { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.80
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return OpenIdConnectConst.MSG_DATA_ACCOUNT_NAME;
            }
        },
        ACTION_EXECUTE_UPLOAD_MF_CARD { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.81
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "execute_upload_mf_card";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                boolean zBooleanValue = ((Boolean) args[0]).booleanValue();
                String str = (String) args[1];
                this._datalist.add(new Data(Data.Type.RESULT, zBooleanValue ? "yes" : "no"));
                this._datalist.add(new Data(Data.Type.SERVICE_ID, str));
            }
        },
        ACTION_EXECUTE_UPLOAD_MAIN_CARD { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.82
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "execute_upload_main_card";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                boolean zBooleanValue = ((Boolean) args[0]).booleanValue();
                String str = (String) args[1];
                this._datalist.add(new Data(Data.Type.RESULT, zBooleanValue ? "yes" : "no"));
                this._datalist.add(new Data(Data.Type.SERVICE_ID, str));
            }
        },
        ACTION_SELECT_SERVICE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.83
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "select_service";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SERVICE_ID, (String) args[0]));
            }
        },
        ACTION_ISSUE_CARD { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.84
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "issue_card";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.SERVICE_ID, (String) args[0]));
            }
        },
        ACTION_DIALOG_RETRY { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.85
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "retry";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.RESOURCES_ID, (String) args[0]));
            }
        },
        ACTION_DIALOG_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.86
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "cancel";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.RESOURCES_ID, (String) args[0]));
            }
        },
        SCREEN_W1_01_02 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.87
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-01-02";
            }
        },
        SCREEN_W1_01_03 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.88
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-01-03";
            }
        },
        SCREEN_W1_01_04 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.89
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-01-04";
            }
        },
        SCREEN_W1_01_05 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.90
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-01-05";
            }
        },
        SCREEN_W1_01_06 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.91
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-01-06";
            }
        },
        SCREEN_W1_01_08 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.92
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-01-08";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                String str;
                int missingApp = ((RequireMissingAppInstallDrawStructure) args[0]).getMissingApp();
                if (missingApp == 1) {
                    str = "0201";
                } else {
                    str = missingApp != 2 ? "" : "0202";
                }
                this._datalist.add(new Data(Data.Type.APP_ID, str));
            }
        },
        SCREEN_W1_01_09 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.93
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-01-09";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.TERMS_VERSION, ((Integer) args[0]).intValue()));
            }
        },
        SCREEN_W1_01_10 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.94
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-01-10";
            }
        },
        SCREEN_W1_01_11 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.95
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-01-11";
            }
        },
        SCREEN_W1_01_12 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.96
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-01-12";
            }
        },
        SCREEN_W1_01_13 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.97
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-01-13";
            }
        },
        SCREEN_W1_01_14 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.98
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-01-14";
            }
        },
        SCREEN_W1_01_16 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.99
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_SUB;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-01-16";
            }
        },
        SCREEN_W1_02_01 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.100
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-02-01";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                boolean z = true;
                if (args.length >= 1) {
                    Object obj = args[0];
                    if (obj instanceof CentralDrawStructure) {
                        CentralDrawStructure centralDrawStructure = (CentralDrawStructure) obj;
                        Iterator<MyServiceGroupInfoAgent> it = centralDrawStructure.getCentralFunc().getMyServiceGroupInfoList().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                z = false;
                                break;
                            } else if (it.next().hasDeleteCard()) {
                                break;
                            }
                        }
                        this._datalist.add(new Data(Data.Type.SHOW_RECOMMEND_LOGIN, centralDrawStructure.isShowLinkGoogleAccount() ? "Y" : "N"));
                        this._datalist.add(new Data(Data.Type.SHOW_NFC_OFF_WARNING, !centralDrawStructure.canUseWirelessFeliCa() ? "Y" : "N"));
                        this._datalist.add(new Data(Data.Type.DELETED_TRANSIT_CARD, z ? "Y" : "N"));
                    }
                }
            }
        },
        SCREEN_W1_02_02 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.101
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-02-02";
            }
        },
        SCREEN_W1_03_01 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.102
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-03-01";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                String str;
                String str2;
                RecommendDetailDrawStructure recommendDetailDrawStructure = (RecommendDetailDrawStructure) args[0];
                RecommendInfoAgent recommendInfo = recommendDetailDrawStructure.getRecommendInfo();
                CentralFuncAgent centralFunc = recommendDetailDrawStructure.getCentralFunc();
                if (!centralFunc.isCompiled().booleanValue()) {
                    str = "U";
                    str2 = "U";
                } else {
                    Iterator<MyServiceGroupInfoAgent> it = centralFunc.getMyServiceGroupInfoList().iterator();
                    str = "N";
                    str2 = str;
                    while (it.hasNext()) {
                        for (MyServiceInfoAgent myServiceInfoAgent : it.next().getServices()) {
                            if (TextUtils.equals(recommendInfo.getId(), myServiceInfoAgent.getId())) {
                                MyServiceInfo client = myServiceInfoAgent.getClient();
                                String str3 = MyServiceInfo.RegisterState.REGISTERED == client.getRegisterState() ? "Y" : "N";
                                String str4 = MyServiceInfo.RegisterState.REGISTERED != client.getAppInstalledState() ? "N" : "Y";
                                str = str3;
                                str2 = str4;
                            }
                        }
                    }
                }
                this._datalist.add(new Data(Data.Type.SERVICE_ID, recommendInfo.getId()));
                this._datalist.add(new Data(Data.Type.SERVICE_VERSION, recommendInfo.getVersion()));
                this._datalist.add(new Data(Data.Type.RECOMMEND_CATEGORY, recommendInfo.getCategoryId()));
                this._datalist.add(new Data(Data.Type.RECOMMEND_STATUS, recommendInfo.getStatus()));
                this._datalist.add(new Data(Data.Type.AREA_REGISTRATION_STATUS, str));
                this._datalist.add(new Data(Data.Type.APP_REGISTRATION_STATUS, str2));
            }
        },
        SCREEN_W1_04_01 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.103
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-04-01";
            }
        },
        SCREEN_W1_05_01 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.104
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-05-01";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.NOTIFICATION_ID, ((NoticeInfoAgent) args[0]).getId()));
            }
        },
        SCREEN_W1_06_01 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.105
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-06-01";
            }
        },
        SCREEN_W1_06_02 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.106
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-06-02";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                ExtIcCardInfoAgent extIcCardInfoAgent = (ExtIcCardInfoAgent) args[0];
                this._datalist.add(new Data(Data.Type.SERVICE_ID, extIcCardInfoAgent.getId()));
                this._datalist.add(new Data(Data.Type.SERVICE_VERSION, extIcCardInfoAgent.getVersion()));
                this._datalist.add(new Data(Data.Type.GUIDANCE_PATTERN, extIcCardInfoAgent.getGuidanceId()));
            }
        },
        SCREEN_W1_07_01 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.107
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-07-01";
            }
        },
        SCREEN_W1_08_01 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.108
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-08-01";
            }
        },
        SCREEN_W1_08_02 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.109
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_SUB;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-08-02";
            }
        },
        SCREEN_W1_10_10 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.110
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-10-10";
            }
        },
        SCREEN_W1_11_10 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.111
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-11-10";
            }
        },
        SCREEN_W1_12_01 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.112
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-12-01";
            }
        },
        SCREEN_W1_12_02 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.113
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-12-02";
            }
        },
        SCREEN_W1_14_01 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.114
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-14-01";
            }
        },
        SCREEN_W1_14_02 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.115
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-14-02";
            }
        },
        SCREEN_W1_14_03 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.116
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-14-03";
            }
        },
        SCREEN_W1_14_04 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.117
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_SUB;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-14-04";
            }
        },
        SCREEN_W1_15_01 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.118
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-15-01";
            }
        },
        SCREEN_W1_16_01 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.119
            String callingPackageName;
            String myPackageName;

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-16-01";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this.callingPackageName = (String) args[0];
                this.myPackageName = ((Activity) args[1]).getPackageName();
                this._datalist.add(new Data(Data.Type.LAUNCH_APP_PKG, this.callingPackageName));
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            boolean isCallOtherApp() {
                return !this.myPackageName.equals(this.callingPackageName);
            }
        },
        SCREEN_W1_16_02 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.120
            String callingPackageName;
            String myPackageName;

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-16-02";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this.callingPackageName = (String) args[0];
                this.myPackageName = ((Activity) args[1]).getPackageName();
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            boolean isCallOtherApp() {
                return !this.myPackageName.equals(this.callingPackageName);
            }
        },
        SCREEN_W1_16_02_MULTIPLE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.121
            String callingPackageName;
            String myPackageName;

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_SUB;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-16-02";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                this.callingPackageName = (String) args[0];
                this.myPackageName = ((Activity) args[1]).getPackageName();
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            boolean isCallOtherApp() {
                return !this.myPackageName.equals(this.callingPackageName);
            }
        },
        SCREEN_W1_16_03 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.122
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-16-03";
            }
        },
        SCREEN_W1_17_01 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.123
            final String DIV = ",";

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-17-01";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                Object obj = args[0];
                if (obj instanceof MyServiceGroupInfoAgent) {
                    ArrayList arrayList = new ArrayList();
                    StringBuilder sb = new StringBuilder();
                    for (MyServiceInfoAgent myServiceInfoAgent : ((MyServiceGroupInfoAgent) obj).getServices()) {
                        arrayList.add(myServiceInfoAgent.getId());
                        if (!TextUtils.isEmpty(sb)) {
                            sb.append(",");
                        }
                        sb.append(myServiceInfoAgent.getMyCardInfoAgentList().size());
                    }
                    applySidListToDataList(Data.Type.MFI_SID_LIST, arrayList);
                    this._datalist.add(new Data(Data.Type.MFI_SID_LIST_SIZE, arrayList.size()));
                    if (TextUtils.isEmpty(sb)) {
                        return;
                    }
                    this._datalist.add(new Data(Data.Type.CARD_NUM_LIST, sb.toString()));
                }
            }

            private void applySidListToDataList(Data.Type dataType, List<String> sidList) {
                if (sidList.size() != 0) {
                    int intData = this._request.getIntData(RequestDataInterface.Type4Int.MAX_DATA_SIZE);
                    StringBuilder sb = new StringBuilder(intData);
                    int i = 0;
                    int i2 = 0;
                    while (i < sidList.size()) {
                        if (intData > sb.length() + sidList.get(i).length() + 1) {
                            sb.append(sidList.get(i));
                            sb.append(",");
                        } else {
                            sb.deleteCharAt(sb.length() - 1);
                            this._datalist.add(new Data(dataType.key() + i2, sb.toString()));
                            i += -1;
                            i2++;
                            sb.delete(0, sb.length());
                        }
                        i++;
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    this._datalist.add(new Data(dataType.key() + i2, sb.toString()));
                }
            }
        },
        SCREEN_W1_17_02 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.124
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_SUB;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-17-02";
            }
        },
        SCREEN_W1_17_06 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.125
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_SUB;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-17-06";
            }
        },
        SCREEN_W1_17_07 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.126
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_SUB;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-17-07";
            }
        },
        SCREEN_W1_17_08 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.127
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_SUB;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-17-08";
            }
        },
        SCREEN_W1_18_01 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.128
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_SUB;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-18-01";
            }
        },
        SCREEN_W1_19_01 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.129
            final String DIV = ",";

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-19-01";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                Object obj = args[0];
                if (obj instanceof MyServiceGroupInfoAgent) {
                    ArrayList arrayList = new ArrayList();
                    StringBuilder sb = new StringBuilder();
                    for (MyServiceInfoAgent myServiceInfoAgent : ((MyServiceGroupInfoAgent) obj).getServices()) {
                        arrayList.add(myServiceInfoAgent.getId());
                        if (!TextUtils.isEmpty(sb)) {
                            sb.append(",");
                        }
                        sb.append(myServiceInfoAgent.getMyCardInfoAgentList().size());
                    }
                    applySidListToDataList(Data.Type.MFI_SID_LIST, arrayList);
                    this._datalist.add(new Data(Data.Type.MFI_SID_LIST_SIZE, arrayList.size()));
                    if (TextUtils.isEmpty(sb)) {
                        return;
                    }
                    this._datalist.add(new Data(Data.Type.CARD_NUM_LIST, sb.toString()));
                }
            }

            private void applySidListToDataList(Data.Type dataType, List<String> sidList) {
                if (sidList.size() != 0) {
                    int intData = this._request.getIntData(RequestDataInterface.Type4Int.MAX_DATA_SIZE);
                    StringBuilder sb = new StringBuilder(intData);
                    int i = 0;
                    int i2 = 0;
                    while (i < sidList.size()) {
                        if (intData > sb.length() + sidList.get(i).length() + 1) {
                            sb.append(sidList.get(i));
                            sb.append(",");
                        } else {
                            sb.deleteCharAt(sb.length() - 1);
                            this._datalist.add(new Data(dataType.key() + i2, sb.toString()));
                            i += -1;
                            i2++;
                            sb.delete(0, sb.length());
                        }
                        i++;
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    this._datalist.add(new Data(dataType.key() + i2, sb.toString()));
                }
            }
        },
        SCREEN_W1_19_02 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.130
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_SUB;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-19-02";
            }
        },
        SCREEN_W1_19_05 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.131
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_SUB;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-19-05";
            }
        },
        SCREEN_W1_20_01 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.132
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-20-01";
            }
        },
        SCREEN_W1_21_01 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.133
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "W1-21-01";
            }
        },
        SCREEN_FATAL { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.134
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_ERROR;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "fatal";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                String str = (String) args[0];
                MfmException mfmException = (MfmException) args[1];
                this._datalist.add(new Data(Data.Type.RESOURCES_ID, str));
                this._datalist.add(new Data(Data.Type.ERROR_CODE, mfmException.getErrorCode()));
                if ("dlg_error_unknown_mfc_error".equals(str) && (mfmException.getFirstCaughtException() instanceof FelicaAccessException)) {
                    FelicaAccessException felicaAccessException = (FelicaAccessException) mfmException.getFirstCaughtException();
                    if (felicaAccessException.getFirstCaughtException() instanceof FelicaException) {
                        FelicaException felicaException = (FelicaException) felicaAccessException.getFirstCaughtException();
                        this._datalist.add(new Data(Data.Type.MFC_ERROR_ID, String.format(Locale.US, TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(felicaException.getID()))));
                        this._datalist.add(new Data(Data.Type.MFC_ERROR_TYPE, String.format(Locale.US, "%03d", Integer.valueOf(felicaException.getType()))));
                    }
                }
            }
        },
        SCREEN_WARNING { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.135
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.SCREEN_ERROR;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return "warning";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                Object obj;
                String str = (String) args[0];
                this._datalist.add(new Data(Data.Type.RESOURCES_ID, str));
                if ("dlg_warning_unknown_felica_error".equals(str) && (obj = args[1]) != null && (obj instanceof String)) {
                    String str2 = (String) obj;
                    this._datalist.add(new Data(Data.Type.MFC_ERROR_ID, str2.substring(3, 5)));
                    this._datalist.add(new Data(Data.Type.MFC_ERROR_TYPE, str2.substring(6, 9)));
                }
            }
        },
        MFS_MOVEMENT { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.136
            private MfsStamp.Event _event;

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            boolean receiver() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
                this._event.target();
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.MFS_MOVEMENT;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return this._event.getEventName();
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                MfsStamp.Event event = (MfsStamp.Event) args[0];
                this._event = event;
                for (MfsStamp.Data data : event._datalist) {
                    if (data.getKey().startsWith("g.")) {
                        this._datalist.add(new Data(data.getKey(), data.getValue(), false));
                    } else {
                        this._datalist.add(new Data(data.getKey(), data.getValue()));
                    }
                }
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type getMfmType() {
                int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event$Type[this._event.type().ordinal()];
                if (i == 1) {
                    return Type.USER_ACTION;
                }
                if (i == 2) {
                    return Type.SCREEN_MAIN;
                }
                if (i == 3) {
                    return Type.AUTO_DUMP;
                }
                if (i != 4) {
                    return null;
                }
                return Type.SCREEN_FATAL;
            }
        },
        APP_MOVEMENT { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.137
            private String _eventName;
            private String _launchId;
            private String _logType;
            private String _prefix;

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            boolean receiver() {
                return true;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type type() {
                return Type.APP_MOVEMENT;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String event() {
                return this._logType + "." + this._eventName;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            String getEventName() {
                return this._prefix + "." + event();
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            void setData(Object... args) {
                initValue();
                Bundle extras = ((Intent) args[0]).getExtras();
                for (String str : ((Bundle) Objects.requireNonNull(extras)).keySet()) {
                    if ("eventName".equals(str)) {
                        this._eventName = extras.getString(str);
                    } else if ("logType".equals(str)) {
                        this._logType = extras.getString(str);
                    } else if ("prefix".equals(str)) {
                        this._prefix = extras.getString(str);
                    } else if ("launchId".equals(str)) {
                        this._launchId = extras.getString(str);
                        this._datalist.add(new Data("g.mfic_lid", this._launchId, false));
                    } else {
                        this._datalist.add(new Data(str, extras.getString(str)));
                    }
                }
                checkValue();
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event
            Type getMfmType() {
                String str = this._logType;
                str.hashCode();
                switch (str) {
                    case "action":
                        return Type.USER_ACTION;
                    case "error.warning":
                        return Type.SCREEN_WARNING;
                    case "screen":
                        return Type.SCREEN_MAIN;
                    case "dump":
                        return Type.AUTO_DUMP;
                    case "error.fatal":
                        return Type.SCREEN_FATAL;
                    default:
                        throw new UnsupportedOperationException("not define type!!!");
                }
            }

            private void initValue() {
                this._prefix = null;
                this._logType = null;
                this._eventName = null;
                this._launchId = null;
            }

            private void checkValue() {
                if (this._eventName == null || this._logType == null || this._prefix == null || this._launchId == null) {
                    throw new UnsupportedOperationException("There is no required key!!");
                }
            }
        };

        List<Data> _datalist;
        RequestDataInterface _request;

        Data fixedData() {
            return null;
        }

        Type getMfmType() {
            return null;
        }

        boolean immediate() {
            return false;
        }

        boolean isCallOtherApp() {
            return true;
        }

        boolean receiver() {
            return false;
        }

        void setData(Object... args) {
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR (r1v0 java.lang.String), (r2v0 int) A[MD:(java.lang.String, int):void (m)] (LINE:373) call: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.<init>(java.lang.String, int):void type: THIS */
        /* synthetic */ Event(AnonymousClass1 anonymousClass1) {
            this();
        }

        void target() {
            throw new UnsupportedOperationException("don't recode app log.");
        }

        enum Type {
            USER_ACTION { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type.1
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type
                String prefix() {
                    return "action";
                }
            },
            AUTO_DUMP { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type.2
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type
                String prefix() {
                    return LogSender.EXTRA_VALUE_LOG_TYPE_DUMP;
                }
            },
            RECEIVER_DUMP { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type.3
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type
                String prefix() {
                    return LogSender.EXTRA_VALUE_LOG_TYPE_DUMP;
                }
            },
            SCREEN_MAIN { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type.4
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type
                String prefix() {
                    return LogSender.EXTRA_VALUE_LOG_TYPE_SCREEN;
                }
            },
            SCREEN_SUB { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type.5
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type
                String prefix() {
                    return LogSender.EXTRA_VALUE_LOG_TYPE_SCREEN;
                }
            },
            SCREEN_ERROR { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type.6
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type
                String prefix() {
                    return Constants.IPC_BUNDLE_KEY_SEND_ERROR;
                }
            },
            SCREEN_FATAL { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type.7
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type
                String prefix() {
                    return "error.fatal";
                }
            },
            SCREEN_WARNING { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type.8
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type
                String prefix() {
                    return "error.warning";
                }
            },
            MFS_MOVEMENT { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type.9
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type
                String prefix() {
                    return "mfs";
                }
            },
            APP_MOVEMENT { // from class: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type.10
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type
                String prefix() {
                    return "app";
                }
            };

            /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR (r1v0 java.lang.String), (r2v0 int) A[MD:(java.lang.String, int):void (m)] (LINE:3642) call: com.felicanetworks.mfm.main.policy.analysis.MfmStamp.Event.Type.<init>(java.lang.String, int):void type: THIS */
            /* synthetic */ Type(AnonymousClass1 anonymousClass1) {
                this();
            }

            String prefix() {
                throw new UnsupportedOperationException("not define prefix!!!");
            }
        }

        Type type() {
            throw new UnsupportedOperationException("not define type!!!");
        }

        String event() {
            throw new UnsupportedOperationException("not define event!!!");
        }

        boolean isTypeScreenMain() {
            return Type.SCREEN_MAIN == type();
        }

        void setup(RequestDataInterface rdi) {
            this._request = rdi;
            this._datalist = new ArrayList();
            Data dataFixedData = fixedData();
            if (dataFixedData != null) {
                this._datalist.add(dataFixedData);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x002f  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x005b  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x005f  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        List<Data> getDataList() {
            int iOrdinal;
            ArrayList arrayList = new ArrayList();
            if (this._request != null) {
                int iOrdinal2 = type().ordinal();
                if (iOrdinal2 != 0) {
                    if (iOrdinal2 != 2) {
                        if (iOrdinal2 != 5) {
                            if (iOrdinal2 == 8 || iOrdinal2 == 9) {
                            }
                            iOrdinal = ordinal();
                            if (iOrdinal == 71) {
                            }
                        }
                        arrayList.add(new Data(Data.Global.LAUNCH_ID, this._request.getStringData(RequestDataInterface.Type4String.LAUNCH_ID)));
                        iOrdinal = ordinal();
                        if (iOrdinal == 71) {
                        }
                    }
                    arrayList.add(new Data(Data.Global.LAUNCH_ID, ""));
                    iOrdinal = ordinal();
                    if (iOrdinal == 71) {
                    }
                } else {
                    arrayList.add(new Data(Data.Global.SCREEN_ID, this._request.getStringData(RequestDataInterface.Type4String.MAIN_SCREEN_ID)));
                    arrayList.add(new Data(Data.Global.LAUNCH_ID, this._request.getStringData(RequestDataInterface.Type4String.LAUNCH_ID)));
                    iOrdinal = ordinal();
                    if (iOrdinal == 71) {
                        switch (iOrdinal) {
                            case 118:
                            case 119:
                            case 120:
                            case 121:
                                if (isCallOtherApp()) {
                                    arrayList.add(new Data(Data.Global.LAUNCH_ID, this._request.getStringData(RequestDataInterface.Type4String.LAUNCH_ID_SWITCH_ACCOUNT)));
                                }
                                break;
                        }
                    }
                }
            }
            List<Data> list = this._datalist;
            if (list != null) {
                arrayList.addAll(list);
            }
            return arrayList;
        }

        String getEventName() {
            return type().prefix() + "." + event();
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.policy.analysis.MfmStamp$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$LaunchManagementActivity$DirectType;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event$Type;

        static {
            int[] iArr = new int[MfsStamp.Event.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event$Type = iArr;
            try {
                iArr[MfsStamp.Event.Type.USER_ACTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event$Type[MfsStamp.Event.Type.SCREEN_MAIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event$Type[MfsStamp.Event.Type.AUTO_DUMP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfsStamp$Event$Type[MfsStamp.Event.Type.SCREEN_FATAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[LaunchManagementActivity.DirectType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$LaunchManagementActivity$DirectType = iArr2;
            try {
                iArr2[LaunchManagementActivity.DirectType.CARD_DETAIL.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$LaunchManagementActivity$DirectType[LaunchManagementActivity.DirectType.UNKNOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }
}
