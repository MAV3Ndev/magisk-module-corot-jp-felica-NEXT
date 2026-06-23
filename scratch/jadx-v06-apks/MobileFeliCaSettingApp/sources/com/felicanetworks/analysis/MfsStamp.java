package com.felicanetworks.analysis;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.felicanetworks.common.cmnctrl.data.FelicaErrorInfo;
import com.felicanetworks.common.cmnctrl.data.IssueStateData;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MfsStamp {
    private static final String EXTRA_KEY_MFS_STAMP_EVENT = "com.felicanetworks.mfs.MFS_STAMP_EVENT";

    interface RequestDataInterface {

        public enum Type4Int {
            MFS_VERSION_CODE,
            RESULT_CODE_SUCCESS,
            RESULT_CODE_ALREADYINIT,
            RESULT_CODE_CANCEL,
            RESULT_CODE_FAILED_BADSTART,
            RESULT_CODE_FAILED_FELICAINUSE,
            RESULT_CODE_FAILED_FELICALOCK,
            RESULT_CODE_FAILED_INIT,
            RESULT_CODE_FAILED_COMMUNICATIONERROR,
            RESULT_CODE_FAILED_OVERCROWDING,
            RESULT_CODE_FAILED_SERVERMAINTENANCE,
            RESULT_CODE_FAILED_MFICVERSION,
            RESULT_CODE_FAILED_MFCNOTFOUND,
            RESULT_CODE_FAILED_MFCDISABLE,
            RESULT_CODE_FAILED_MFSVERSION,
            RESULT_CODE_FAILED_NOTAPPLICABLEDEVICE,
            RESULT_CODE_FAILED_FATALERROR
        }

        public enum Type4String {
            LAUNCH_ID,
            MAIN_SCREEN_ID,
            MFS_IDM,
            MFS_IC_CODE,
            MFS_APP_ID,
            LAUNCH_APP_ID,
            LAUNCH_PARAM_CMD,
            LAUNCH_APP_PKG,
            ISSUE_CODE
        }

        int getIntData(Type4Int type4Int);

        String getStringData(Type4String type4String);
    }

    public static class Data {
        static final String BOOL_N = "N";
        static final String BOOL_Y = "Y";
        static final String EVENT_GLOBAL_KEY_PREFIX = "g";
        static final String RESULT_NO = "no";
        static final String RESULT_YES = "yes";
        private String _key;
        private String _value;

        enum Type {
            RESULT { // from class: com.felicanetworks.analysis.MfsStamp.Data.Type.1
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Type
                String key() {
                    return "result";
                }
            },
            ERROR_ID { // from class: com.felicanetworks.analysis.MfsStamp.Data.Type.2
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Type
                String key() {
                    return "eid";
                }
            },
            RESULT_CODE { // from class: com.felicanetworks.analysis.MfsStamp.Data.Type.3
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Type
                String key() {
                    return "res_code";
                }
            },
            API_LEVEL { // from class: com.felicanetworks.analysis.MfsStamp.Data.Type.4
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Type
                String key() {
                    return "api_level";
                }
            },
            ISSUE { // from class: com.felicanetworks.analysis.MfsStamp.Data.Type.5
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Type
                String key() {
                    return "issue";
                }
            },
            APP_ID { // from class: com.felicanetworks.analysis.MfsStamp.Data.Type.6
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Type
                String key() {
                    return "app_id";
                }
            },
            IDM { // from class: com.felicanetworks.analysis.MfsStamp.Data.Type.7
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Type
                String key() {
                    return "idm";
                }
            },
            IC_CODE { // from class: com.felicanetworks.analysis.MfsStamp.Data.Type.8
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Type
                String key() {
                    return "ic_code";
                }
            },
            LAUNCH_APP_ID { // from class: com.felicanetworks.analysis.MfsStamp.Data.Type.9
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Type
                String key() {
                    return "launch_app_id";
                }
            },
            LAUNCH_CMD { // from class: com.felicanetworks.analysis.MfsStamp.Data.Type.10
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Type
                String key() {
                    return "launch_cmd";
                }
            },
            LAUNCH_APP_PKG { // from class: com.felicanetworks.analysis.MfsStamp.Data.Type.11
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Type
                String key() {
                    return "launch_app_pkg";
                }
            },
            MFS_APP_VER { // from class: com.felicanetworks.analysis.MfsStamp.Data.Type.12
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Type
                String key() {
                    return "mfs_app_ver";
                }
            },
            MFC_ERR_ID { // from class: com.felicanetworks.analysis.MfsStamp.Data.Type.13
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Type
                String key() {
                    return "mfc_err_id";
                }
            },
            MFC_ERR_TYPE { // from class: com.felicanetworks.analysis.MfsStamp.Data.Type.14
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Type
                String key() {
                    return "mfc_err_type";
                }
            },
            TOS_FLG { // from class: com.felicanetworks.analysis.MfsStamp.Data.Type.15
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Type
                String key() {
                    return "tos_flg";
                }
            };

            /* synthetic */ Type(AnonymousClass1 anonymousClass1) {
                this();
            }

            String key() {
                throw new UnsupportedOperationException();
            }
        }

        enum Global {
            LAUNCH_ID { // from class: com.felicanetworks.analysis.MfsStamp.Data.Global.1
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Global
                String key() {
                    return "mfs_lid";
                }
            },
            SCREEN_ID { // from class: com.felicanetworks.analysis.MfsStamp.Data.Global.2
                @Override // com.felicanetworks.analysis.MfsStamp.Data.Global
                String key() {
                    return "wn";
                }
            };

            /* synthetic */ Global(AnonymousClass1 anonymousClass1) {
                this();
            }

            String key() {
                throw new UnsupportedOperationException("not define global key!!!");
            }
        }

        public Data(String str, String str2) {
            this._key = str;
            this._value = str2;
        }

        public Data(Type type, String str) {
            this(type.key(), str);
        }

        public Data(Type type, int i) {
            this(type, String.valueOf(i));
        }

        public Data(Global global, String str) {
            this._key = "g." + global.key();
            this._value = str;
        }

        public String getKey() {
            return this._key;
        }

        public String getValue() {
            return this._value;
        }
    }

    public enum Event {
        ACTION_WID_1_2_YES { // from class: com.felicanetworks.analysis.MfsStamp.Event.1
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "result_confirm_initialize";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT, "yes");
            }
        },
        ACTION_WID_1_2_NO { // from class: com.felicanetworks.analysis.MfsStamp.Event.2
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "result_confirm_initialize";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT, "no");
            }
        },
        ACTION_WID_1_2_LINK { // from class: com.felicanetworks.analysis.MfsStamp.Event.3
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "disclaimer_felica";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_1_3_CANCEL_INITIALIZE { // from class: com.felicanetworks.analysis.MfsStamp.Event.4
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "cancel_initialize";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_1_4_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.5
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-4_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_1_5_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.6
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-5_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_1_6_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.7
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-6_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_1_7_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.8
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-7_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_1_8_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.9
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-8_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_1_9_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.10
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-9_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_1_10_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.11
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-10_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_1_11_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.12
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-11_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_1_12_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.13
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-12_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_1_13_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.14
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-13_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_1_17_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.15
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-17_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_1_18_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.16
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-18_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_1_25_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.17
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-25_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_1_26_REQUEST_APP_UPDATE_POSITIVE { // from class: com.felicanetworks.analysis.MfsStamp.Event.18
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "request_app_update";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            void setData(Object... objArr) {
                this._datalist.add(new Data(Data.Type.RESULT, "yes"));
            }
        },
        ACTION_WID_1_26_REQUEST_APP_UPDATE_NEGATIVE { // from class: com.felicanetworks.analysis.MfsStamp.Event.19
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "request_app_update";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            void setData(Object... objArr) {
                this._datalist.add(new Data(Data.Type.RESULT, "no"));
            }
        },
        ACTION_WID_1_27_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.20
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-27_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_1_28_REQUEST_APP_UPDATE_POSITIVE { // from class: com.felicanetworks.analysis.MfsStamp.Event.21
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "request_app_install";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            void setData(Object... objArr) {
                this._datalist.add(new Data(Data.Type.RESULT, "yes"));
            }
        },
        ACTION_WID_1_28_REQUEST_APP_UPDATE_NEGATIVE { // from class: com.felicanetworks.analysis.MfsStamp.Event.22
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "request_app_install";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            void setData(Object... objArr) {
                this._datalist.add(new Data(Data.Type.RESULT, "no"));
            }
        },
        ACTION_WID_1_29_REQUEST_APP_UPDATE_POSITIVE { // from class: com.felicanetworks.analysis.MfsStamp.Event.23
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "request_app_update";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            void setData(Object... objArr) {
                this._datalist.add(new Data(Data.Type.RESULT, "yes"));
            }
        },
        ACTION_WID_1_29_REQUEST_APP_UPDATE_NEGATIVE { // from class: com.felicanetworks.analysis.MfsStamp.Event.24
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "request_app_update";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            void setData(Object... objArr) {
                this._datalist.add(new Data(Data.Type.RESULT, "no"));
            }
        },
        ACTION_WID_2_1_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.25
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w2-1_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_2_2_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.26
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w2-2_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_2_3_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.27
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w2-3_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_2_4_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.28
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w2-4_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        ACTION_WID_2_5_CLOSE { // from class: com.felicanetworks.analysis.MfsStamp.Event.29
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w2-5_close";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }
        },
        AUTO_DUMP_MFS_LAUNCH { // from class: com.felicanetworks.analysis.MfsStamp.Event.30
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            String event() {
                return "mfs_launch";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            void setData(Object... objArr) {
                String str = (String) objArr[0];
                String str2 = (String) objArr[1];
                String str3 = (String) objArr[2];
                this._datalist.add(new Data(Data.Type.LAUNCH_APP_ID, str));
                this._datalist.add(new Data(Data.Type.LAUNCH_CMD, str2));
                this._datalist.add(new Data(Data.Type.LAUNCH_APP_PKG, str3));
            }
        },
        AUTO_DUMP_RESULT_CHECK_FELICA_INITIALIZATION { // from class: com.felicanetworks.analysis.MfsStamp.Event.31
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "result_initialize";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            void setData(Object... objArr) {
                IssueStateData issueStateData = (IssueStateData) objArr[0];
                this._datalist.add(new Data(Data.Type.IDM, issueStateData.idmData));
                this._datalist.add(new Data(Data.Type.IC_CODE, issueStateData.icCode));
            }
        },
        AUTO_DUMP_SKIP_WID_1_2 { // from class: com.felicanetworks.analysis.MfsStamp.Event.32
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "skip_w1-2";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            void setData(Object... objArr) {
                this._datalist.add(new Data(Data.Type.TOS_FLG, ((Boolean) objArr[0]).booleanValue() ? "Y" : "N"));
                this._datalist.add(new Data(Data.Type.API_LEVEL, Build.VERSION.SDK_INT));
                this._datalist.add(new Data(Data.Type.ISSUE, this._request.getStringData(RequestDataInterface.Type4String.ISSUE_CODE)));
                this._datalist.add(new Data(Data.Type.APP_ID, this._request.getStringData(RequestDataInterface.Type4String.MFS_APP_ID)));
                this._datalist.add(new Data(Data.Type.IDM, this._request.getStringData(RequestDataInterface.Type4String.MFS_IDM)));
                this._datalist.add(new Data(Data.Type.IC_CODE, this._request.getStringData(RequestDataInterface.Type4String.MFS_IC_CODE)));
                this._datalist.add(new Data(Data.Type.LAUNCH_APP_ID, this._request.getStringData(RequestDataInterface.Type4String.LAUNCH_APP_ID)));
                this._datalist.add(new Data(Data.Type.LAUNCH_CMD, this._request.getStringData(RequestDataInterface.Type4String.LAUNCH_PARAM_CMD)));
                this._datalist.add(new Data(Data.Type.LAUNCH_APP_PKG, this._request.getStringData(RequestDataInterface.Type4String.LAUNCH_APP_PKG)));
                this._datalist.add(new Data(Data.Type.MFS_APP_VER, this._request.getIntData(RequestDataInterface.Type4Int.MFS_VERSION_CODE)));
            }
        },
        SCREEN_WID_1_1 { // from class: com.felicanetworks.analysis.MfsStamp.Event.33
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-1";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }
        },
        SCREEN_WID_1_2 { // from class: com.felicanetworks.analysis.MfsStamp.Event.34
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-2";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            void setData(Object... objArr) {
                this._datalist.add(new Data(Data.Type.API_LEVEL, Build.VERSION.SDK_INT));
                this._datalist.add(new Data(Data.Type.ISSUE, this._request.getStringData(RequestDataInterface.Type4String.ISSUE_CODE)));
                this._datalist.add(new Data(Data.Type.APP_ID, this._request.getStringData(RequestDataInterface.Type4String.MFS_APP_ID)));
                this._datalist.add(new Data(Data.Type.IDM, this._request.getStringData(RequestDataInterface.Type4String.MFS_IDM)));
                this._datalist.add(new Data(Data.Type.IC_CODE, this._request.getStringData(RequestDataInterface.Type4String.MFS_IC_CODE)));
                this._datalist.add(new Data(Data.Type.LAUNCH_APP_ID, this._request.getStringData(RequestDataInterface.Type4String.LAUNCH_APP_ID)));
                this._datalist.add(new Data(Data.Type.LAUNCH_CMD, this._request.getStringData(RequestDataInterface.Type4String.LAUNCH_PARAM_CMD)));
                this._datalist.add(new Data(Data.Type.LAUNCH_APP_PKG, this._request.getStringData(RequestDataInterface.Type4String.LAUNCH_APP_PKG)));
                this._datalist.add(new Data(Data.Type.MFS_APP_VER, this._request.getIntData(RequestDataInterface.Type4Int.MFS_VERSION_CODE)));
            }
        },
        SCREEN_WID_1_3 { // from class: com.felicanetworks.analysis.MfsStamp.Event.35
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-3";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }
        },
        SCREEN_WID_1_4 { // from class: com.felicanetworks.analysis.MfsStamp.Event.36
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-4";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_SUCCESS));
            }
        },
        SCREEN_WID_1_5 { // from class: com.felicanetworks.analysis.MfsStamp.Event.37
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-5";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_ALREADYINIT));
            }
        },
        SCREEN_WID_1_6 { // from class: com.felicanetworks.analysis.MfsStamp.Event.38
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-6";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FELICAINUSE));
            }
        },
        SCREEN_WID_1_7 { // from class: com.felicanetworks.analysis.MfsStamp.Event.39
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-7";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FELICALOCK));
            }
        },
        SCREEN_WID_1_8 { // from class: com.felicanetworks.analysis.MfsStamp.Event.40
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-8";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_CANCEL));
            }
        },
        SCREEN_WID_1_9 { // from class: com.felicanetworks.analysis.MfsStamp.Event.41
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-9";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_CANCEL));
            }
        },
        SCREEN_WID_1_10 { // from class: com.felicanetworks.analysis.MfsStamp.Event.42
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-10";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_INIT));
            }
        },
        SCREEN_WID_1_11 { // from class: com.felicanetworks.analysis.MfsStamp.Event.43
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-11";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_COMMUNICATIONERROR));
            }
        },
        SCREEN_WID_1_12 { // from class: com.felicanetworks.analysis.MfsStamp.Event.44
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-12";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_OVERCROWDING));
            }
        },
        SCREEN_WID_1_13 { // from class: com.felicanetworks.analysis.MfsStamp.Event.45
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-13";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_SERVERMAINTENANCE));
            }
        },
        SCREEN_WID_1_15 { // from class: com.felicanetworks.analysis.MfsStamp.Event.46
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-15";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }
        },
        SCREEN_WID_1_17 { // from class: com.felicanetworks.analysis.MfsStamp.Event.47
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-17";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FELICAINUSE));
            }
        },
        SCREEN_WID_1_18 { // from class: com.felicanetworks.analysis.MfsStamp.Event.48
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-18";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FELICALOCK));
            }
        },
        SCREEN_WID_1_25 { // from class: com.felicanetworks.analysis.MfsStamp.Event.49
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-25";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_COMMUNICATIONERROR));
            }
        },
        SCREEN_WID_1_26 { // from class: com.felicanetworks.analysis.MfsStamp.Event.50
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-26";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_MFICVERSION));
            }
        },
        SCREEN_WID_1_27 { // from class: com.felicanetworks.analysis.MfsStamp.Event.51
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-27";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_MFCDISABLE));
            }
        },
        SCREEN_WID_1_28 { // from class: com.felicanetworks.analysis.MfsStamp.Event.52
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-28";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_MFCNOTFOUND));
            }
        },
        SCREEN_WID_1_29 { // from class: com.felicanetworks.analysis.MfsStamp.Event.53
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-29";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_MFSVERSION));
            }
        },
        SCREEN_WID_1_30 { // from class: com.felicanetworks.analysis.MfsStamp.Event.54
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w1-30";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_NOTAPPLICABLEDEVICE));
            }
        },
        SCREEN_WID_2_1 { // from class: com.felicanetworks.analysis.MfsStamp.Event.55
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w2-1";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FATALERROR));
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            void setData(Object... objArr) {
                this._datalist.add(new Data(Data.Type.ERROR_ID, (String) objArr[0]));
            }
        },
        SCREEN_WID_2_2 { // from class: com.felicanetworks.analysis.MfsStamp.Event.56
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w2-2";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FATALERROR));
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            void setData(Object... objArr) {
                this._datalist.add(new Data(Data.Type.ERROR_ID, (String) objArr[0]));
            }
        },
        SCREEN_WID_2_3 { // from class: com.felicanetworks.analysis.MfsStamp.Event.57
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w2-3";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FATALERROR));
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            void setData(Object... objArr) {
                this._datalist.add(new Data(Data.Type.ERROR_ID, (String) objArr[0]));
            }
        },
        SCREEN_WID_2_4 { // from class: com.felicanetworks.analysis.MfsStamp.Event.58
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w2-4";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FATALERROR));
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            void setData(Object... objArr) {
                this._datalist.add(new Data(Data.Type.ERROR_ID, (String) objArr[0]));
            }
        },
        SCREEN_WID_2_5 { // from class: com.felicanetworks.analysis.MfsStamp.Event.59
            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public String event() {
                return "w2-5";
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FATALERROR));
            }

            @Override // com.felicanetworks.analysis.MfsStamp.Event
            void setData(Object... objArr) {
                String str = (String) objArr[0];
                FelicaErrorInfo felicaErrorInfo = (FelicaErrorInfo) objArr[1];
                this._datalist.add(new Data(Data.Type.ERROR_ID, str));
                this._datalist.add(new Data(Data.Type.MFC_ERR_ID, felicaErrorInfo.errorID));
                this._datalist.add(new Data(Data.Type.MFC_ERR_TYPE, felicaErrorInfo.errorType));
            }
        };

        List<Data> _datalist;
        RequestDataInterface _request;

        Data fixedData() {
            return null;
        }

        void setData(Object... objArr) {
        }

        /* synthetic */ Event(AnonymousClass1 anonymousClass1) {
            this();
        }

        enum Type {
            USER_ACTION { // from class: com.felicanetworks.analysis.MfsStamp.Event.Type.1
                @Override // com.felicanetworks.analysis.MfsStamp.Event.Type
                String prefix() {
                    return "action";
                }
            },
            SCREEN_MAIN { // from class: com.felicanetworks.analysis.MfsStamp.Event.Type.2
                @Override // com.felicanetworks.analysis.MfsStamp.Event.Type
                String prefix() {
                    return "screen";
                }
            },
            AUTO_DUMP { // from class: com.felicanetworks.analysis.MfsStamp.Event.Type.3
                @Override // com.felicanetworks.analysis.MfsStamp.Event.Type
                String prefix() {
                    return "dump";
                }
            },
            SCREEN_FATAL { // from class: com.felicanetworks.analysis.MfsStamp.Event.Type.4
                @Override // com.felicanetworks.analysis.MfsStamp.Event.Type
                String prefix() {
                    return "error.fatal";
                }
            };

            /* synthetic */ Type(AnonymousClass1 anonymousClass1) {
                this();
            }

            String prefix() {
                throw new UnsupportedOperationException();
            }
        }

        void target() {
            throw new UnsupportedOperationException();
        }

        String event() {
            throw new UnsupportedOperationException();
        }

        Type type() {
            throw new UnsupportedOperationException();
        }

        void addData(Data data) {
            this._datalist.add(data);
        }

        void setup(RequestDataInterface requestDataInterface) {
            this._request = requestDataInterface;
            this._datalist = new ArrayList();
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0048  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0053  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        java.util.List<com.felicanetworks.analysis.MfsStamp.Data> getDataList() {
            /*
                r5 = this;
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                int[] r1 = com.felicanetworks.analysis.MfsStamp.AnonymousClass1.$SwitchMap$com$felicanetworks$analysis$MfsStamp$Event$Type
                com.felicanetworks.analysis.MfsStamp$Event$Type r2 = r5.type()
                int r2 = r2.ordinal()
                r1 = r1[r2]
                r2 = 1
                if (r1 == r2) goto L1e
                r2 = 2
                if (r1 == r2) goto L30
                r2 = 3
                if (r1 == r2) goto L30
                r2 = 4
                if (r1 == r2) goto L30
                goto L42
            L1e:
                com.felicanetworks.analysis.MfsStamp$Data r1 = new com.felicanetworks.analysis.MfsStamp$Data
                com.felicanetworks.analysis.MfsStamp$Data$Global r2 = com.felicanetworks.analysis.MfsStamp.Data.Global.SCREEN_ID
                com.felicanetworks.analysis.MfsStamp$RequestDataInterface r3 = r5._request
                com.felicanetworks.analysis.MfsStamp$RequestDataInterface$Type4String r4 = com.felicanetworks.analysis.MfsStamp.RequestDataInterface.Type4String.MAIN_SCREEN_ID
                java.lang.String r3 = r3.getStringData(r4)
                r1.<init>(r2, r3)
                r0.add(r1)
            L30:
                com.felicanetworks.analysis.MfsStamp$Data r1 = new com.felicanetworks.analysis.MfsStamp$Data
                com.felicanetworks.analysis.MfsStamp$Data$Global r2 = com.felicanetworks.analysis.MfsStamp.Data.Global.LAUNCH_ID
                com.felicanetworks.analysis.MfsStamp$RequestDataInterface r3 = r5._request
                com.felicanetworks.analysis.MfsStamp$RequestDataInterface$Type4String r4 = com.felicanetworks.analysis.MfsStamp.RequestDataInterface.Type4String.LAUNCH_ID
                java.lang.String r3 = r3.getStringData(r4)
                r1.<init>(r2, r3)
                r0.add(r1)
            L42:
                com.felicanetworks.analysis.MfsStamp$Data r1 = r5.fixedData()
                if (r1 == 0) goto L4f
                com.felicanetworks.analysis.MfsStamp$Data r1 = r5.fixedData()
                r0.add(r1)
            L4f:
                java.util.List<com.felicanetworks.analysis.MfsStamp$Data> r5 = r5._datalist
                if (r5 == 0) goto L56
                r0.addAll(r5)
            L56:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.analysis.MfsStamp.Event.getDataList():java.util.List");
        }

        String getEventName() {
            return type().prefix() + "." + event();
        }

        Intent intent() {
            Intent intent = new Intent();
            intent.putExtra(MfsStamp.EXTRA_KEY_MFS_STAMP_EVENT, name());
            for (Data data : getDataList()) {
                intent.putExtra(data.getKey(), data.getValue());
            }
            return intent;
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.analysis.MfsStamp$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$analysis$MfsStamp$Event$Type;

        static {
            int[] iArr = new int[Event.Type.values().length];
            $SwitchMap$com$felicanetworks$analysis$MfsStamp$Event$Type = iArr;
            try {
                iArr[Event.Type.USER_ACTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$Event$Type[Event.Type.AUTO_DUMP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$Event$Type[Event.Type.SCREEN_FATAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$Event$Type[Event.Type.SCREEN_MAIN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static Event parseIntent(Intent intent) {
        String stringExtra = intent.getStringExtra(EXTRA_KEY_MFS_STAMP_EVENT);
        intent.removeExtra(EXTRA_KEY_MFS_STAMP_EVENT);
        Event eventValueOf = Event.valueOf(stringExtra);
        eventValueOf.setup(null);
        Bundle extras = intent.getExtras();
        if (extras != null) {
            for (String str : extras.keySet()) {
                eventValueOf.addData(new Data(str, intent.getStringExtra(str)));
            }
        }
        return eventValueOf;
    }
}
