package com.felicanetworks.mfm.main.policy.analysis;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.amazonaws.mobileconnectors.pinpoint.internal.event.ClientContext;
import com.felicanetworks.mfc.mfi.LogSender;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.data.FelicaErrorInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.data.IssueStateData;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
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

        int getIntData(Type4Int type);

        String getStringData(Type4String type);
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
            RESULT { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type.1
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type
                String key() {
                    return "result";
                }
            },
            ERROR_ID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type.2
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type
                String key() {
                    return "eid";
                }
            },
            RESULT_CODE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type.3
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type
                String key() {
                    return "res_code";
                }
            },
            API_LEVEL { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type.4
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type
                String key() {
                    return LogSender.EXTRA_KEY_API_LEVEL;
                }
            },
            ISSUE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type.5
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type
                String key() {
                    return "issue";
                }
            },
            APP_ID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type.6
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type
                String key() {
                    return ClientContext.APP_ID_KEY;
                }
            },
            IDM { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type.7
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type
                String key() {
                    return "idm";
                }
            },
            IC_CODE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type.8
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type
                String key() {
                    return "ic_code";
                }
            },
            LAUNCH_APP_ID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type.9
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type
                String key() {
                    return "launch_app_id";
                }
            },
            LAUNCH_CMD { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type.10
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type
                String key() {
                    return "launch_cmd";
                }
            },
            LAUNCH_APP_PKG { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type.11
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type
                String key() {
                    return "launch_app_pkg";
                }
            },
            MFS_APP_VER { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type.12
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type
                String key() {
                    return "mfs_app_ver";
                }
            },
            MFC_ERR_ID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type.13
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type
                String key() {
                    return "mfc_err_id";
                }
            },
            MFC_ERR_TYPE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type.14
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type
                String key() {
                    return "mfc_err_type";
                }
            },
            TOS_FLG { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type.15
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Type
                String key() {
                    return "tos_flg";
                }
            };

            String key() {
                throw new UnsupportedOperationException();
            }
        }

        enum Global {
            LAUNCH_ID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Global.1
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Global
                String key() {
                    return "mfs_lid";
                }
            },
            SCREEN_ID { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Global.2
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Data.Global
                String key() {
                    return "wn";
                }
            };

            String key() {
                throw new UnsupportedOperationException("not define global key!!!");
            }
        }

        public Data(String key, String value) {
            this._key = key;
            this._value = value;
        }

        public Data(Type type, String value) {
            this(type.key(), value);
        }

        public Data(Type type, int value) {
            this(type, String.valueOf(value));
        }

        public Data(Global g, String value) {
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
        ACTION_WID_1_2_YES { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.1
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "result_confirm_initialize";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT, "yes");
            }
        },
        ACTION_WID_1_2_NO { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.2
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "result_confirm_initialize";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT, "no");
            }
        },
        ACTION_WID_1_2_LINK { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.3
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "disclaimer_felica";
            }
        },
        ACTION_WID_1_3_CANCEL_INITIALIZE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.4
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "cancel_initialize";
            }
        },
        ACTION_WID_1_4_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.5
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-4_close";
            }
        },
        ACTION_WID_1_5_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.6
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-5_close";
            }
        },
        ACTION_WID_1_6_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.7
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-6_close";
            }
        },
        ACTION_WID_1_7_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.8
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-7_close";
            }
        },
        ACTION_WID_1_8_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.9
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-8_close";
            }
        },
        ACTION_WID_1_9_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.10
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-9_close";
            }
        },
        ACTION_WID_1_10_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.11
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-10_close";
            }
        },
        ACTION_WID_1_11_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.12
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-11_close";
            }
        },
        ACTION_WID_1_12_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.13
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-12_close";
            }
        },
        ACTION_WID_1_13_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.14
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-13_close";
            }
        },
        ACTION_WID_1_17_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.15
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-17_close";
            }
        },
        ACTION_WID_1_18_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.16
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-18_close";
            }
        },
        ACTION_WID_1_25_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.17
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-25_close";
            }
        },
        ACTION_WID_1_26_REQUEST_APP_UPDATE_POSITIVE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.18
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "request_app_update";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.RESULT, "yes"));
            }
        },
        ACTION_WID_1_26_REQUEST_APP_UPDATE_NEGATIVE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.19
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "request_app_update";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.RESULT, "no"));
            }
        },
        ACTION_WID_1_27_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.20
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-27_close";
            }
        },
        ACTION_WID_1_28_REQUEST_APP_UPDATE_POSITIVE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.21
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "request_app_install";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.RESULT, "yes"));
            }
        },
        ACTION_WID_1_28_REQUEST_APP_UPDATE_NEGATIVE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.22
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "request_app_install";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.RESULT, "no"));
            }
        },
        ACTION_WID_1_29_REQUEST_APP_UPDATE_POSITIVE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.23
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "request_app_update";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.RESULT, "yes"));
            }
        },
        ACTION_WID_1_29_REQUEST_APP_UPDATE_NEGATIVE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.24
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "request_app_update";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.RESULT, "no"));
            }
        },
        ACTION_WID_2_1_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.25
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w2-1_close";
            }
        },
        ACTION_WID_2_2_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.26
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w2-2_close";
            }
        },
        ACTION_WID_2_3_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.27
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w2-3_close";
            }
        },
        ACTION_WID_2_4_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.28
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w2-4_close";
            }
        },
        ACTION_WID_2_5_CLOSE { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.29
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.USER_ACTION;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w2-5_close";
            }
        },
        AUTO_DUMP_MFS_LAUNCH { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.30
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            String event() {
                return "mfs_launch";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            void setData(Object... args) {
                String str = (String) args[0];
                String str2 = (String) args[1];
                String str3 = (String) args[2];
                this._datalist.add(new Data(Data.Type.LAUNCH_APP_ID, str));
                this._datalist.add(new Data(Data.Type.LAUNCH_CMD, str2));
                this._datalist.add(new Data(Data.Type.LAUNCH_APP_PKG, str3));
            }
        },
        AUTO_DUMP_RESULT_CHECK_FELICA_INITIALIZATION { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.31
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "result_initialize";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            void setData(Object... args) {
                IssueStateData issueStateData = (IssueStateData) args[0];
                this._datalist.add(new Data(Data.Type.IDM, issueStateData.idmData));
                this._datalist.add(new Data(Data.Type.IC_CODE, issueStateData.icCode));
            }
        },
        AUTO_DUMP_SKIP_WID_1_2 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.32
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.AUTO_DUMP;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "skip_w1-2";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.TOS_FLG, ((Boolean) args[0]).booleanValue() ? "Y" : "N"));
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
        SCREEN_WID_1_1 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.33
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-1";
            }
        },
        SCREEN_WID_1_2 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.34
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-2";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            void setData(Object... args) {
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
        SCREEN_WID_1_3 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.35
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-3";
            }
        },
        SCREEN_WID_1_4 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.36
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-4";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_SUCCESS));
            }
        },
        SCREEN_WID_1_5 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.37
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-5";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_ALREADYINIT));
            }
        },
        SCREEN_WID_1_6 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.38
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-6";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FELICAINUSE));
            }
        },
        SCREEN_WID_1_7 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.39
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-7";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FELICALOCK));
            }
        },
        SCREEN_WID_1_8 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.40
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-8";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_CANCEL));
            }
        },
        SCREEN_WID_1_9 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.41
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-9";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_CANCEL));
            }
        },
        SCREEN_WID_1_10 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.42
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-10";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_INIT));
            }
        },
        SCREEN_WID_1_11 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.43
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-11";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_COMMUNICATIONERROR));
            }
        },
        SCREEN_WID_1_12 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.44
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-12";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_OVERCROWDING));
            }
        },
        SCREEN_WID_1_13 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.45
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-13";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_SERVERMAINTENANCE));
            }
        },
        SCREEN_WID_1_15 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.46
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-15";
            }
        },
        SCREEN_WID_1_17 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.47
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-17";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FELICAINUSE));
            }
        },
        SCREEN_WID_1_18 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.48
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-18";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FELICALOCK));
            }
        },
        SCREEN_WID_1_25 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.49
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-25";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_COMMUNICATIONERROR));
            }
        },
        SCREEN_WID_1_26 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.50
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-26";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_MFICVERSION));
            }
        },
        SCREEN_WID_1_27 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.51
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-27";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_MFCDISABLE));
            }
        },
        SCREEN_WID_1_28 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.52
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-28";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_MFCNOTFOUND));
            }
        },
        SCREEN_WID_1_29 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.53
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-29";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_MFSVERSION));
            }
        },
        SCREEN_WID_1_30 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.54
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_MAIN;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w1-30";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_NOTAPPLICABLEDEVICE));
            }
        },
        SCREEN_WID_2_1 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.55
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w2-1";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FATALERROR));
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.ERROR_ID, (String) args[0]));
            }
        },
        SCREEN_WID_2_2 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.56
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w2-2";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FATALERROR));
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.ERROR_ID, (String) args[0]));
            }
        },
        SCREEN_WID_2_3 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.57
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w2-3";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FATALERROR));
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.ERROR_ID, (String) args[0]));
            }
        },
        SCREEN_WID_2_4 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.58
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w2-4";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FATALERROR));
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            void setData(Object... args) {
                this._datalist.add(new Data(Data.Type.ERROR_ID, (String) args[0]));
            }
        },
        SCREEN_WID_2_5 { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.59
            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public void target() {
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public Type type() {
                return Type.SCREEN_FATAL;
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            public String event() {
                return "w2-5";
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            Data fixedData() {
                return new Data(Data.Type.RESULT_CODE, this._request.getIntData(RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FATALERROR));
            }

            @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event
            void setData(Object... args) {
                String str = (String) args[0];
                FelicaErrorInfo felicaErrorInfo = (FelicaErrorInfo) args[1];
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

        void setData(Object... args) {
        }

        enum Type {
            USER_ACTION { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.Type.1
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.Type
                String prefix() {
                    return "action";
                }
            },
            SCREEN_MAIN { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.Type.2
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.Type
                String prefix() {
                    return LogSender.EXTRA_VALUE_LOG_TYPE_SCREEN;
                }
            },
            AUTO_DUMP { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.Type.3
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.Type
                String prefix() {
                    return LogSender.EXTRA_VALUE_LOG_TYPE_DUMP;
                }
            },
            SCREEN_FATAL { // from class: com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.Type.4
                @Override // com.felicanetworks.mfm.main.policy.analysis.MfsStamp.Event.Type
                String prefix() {
                    return "error.fatal";
                }
            };

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

        void addData(Data ed) {
            this._datalist.add(ed);
        }

        void setup(RequestDataInterface rdi) {
            this._request = rdi;
            this._datalist = new ArrayList();
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x004e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        List<Data> getDataList() {
            List<Data> list;
            ArrayList arrayList = new ArrayList();
            int iOrdinal = type().ordinal();
            if (iOrdinal == 0) {
                arrayList.add(new Data(Data.Global.SCREEN_ID, this._request.getStringData(RequestDataInterface.Type4String.MAIN_SCREEN_ID)));
            } else {
                if (iOrdinal == 1 || iOrdinal == 2 || iOrdinal == 3) {
                }
                if (fixedData() != null) {
                    arrayList.add(fixedData());
                }
                list = this._datalist;
                if (list != null) {
                    arrayList.addAll(list);
                }
                return arrayList;
            }
            arrayList.add(new Data(Data.Global.LAUNCH_ID, this._request.getStringData(RequestDataInterface.Type4String.LAUNCH_ID)));
            if (fixedData() != null) {
            }
            list = this._datalist;
            if (list != null) {
            }
            return arrayList;
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
