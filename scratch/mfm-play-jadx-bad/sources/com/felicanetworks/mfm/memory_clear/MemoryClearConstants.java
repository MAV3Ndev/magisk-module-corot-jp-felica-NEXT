package com.felicanetworks.mfm.memory_clear;

/* JADX INFO: loaded from: classes3.dex */
public class MemoryClearConstants {

    public enum RESULT_CODE {
        SUCCESS(1),
        PROCESSED(2),
        CANCEL(0),
        FAILED_CALLER(100),
        FAILED_MENU_APP_RUNNING(101),
        FAILED_USED_MFC_BY_OTHER_APP(200),
        FAILED_RUNNING_BY_MFIC(201),
        FAILED_LOCKED_FELICA_CHIP(202),
        FAILED_FELICA_OPEN(203),
        FAILED_INVALID_RESPONSE(204),
        FAILED_FELICA_CHIP_TIME_OUT(205),
        FAILED_NO_PERMISSION_TO_ACTIVATE_MFC(206),
        FAILED_FELICA_HTTP_ERROR(207),
        FAILED_MFI_SERVER_MAINTENANCE(208),
        FAILED_INVALID_TOKEN_REQUEST(209),
        FAILED_RETRY_MEMORY_CLEAR(210),
        FAILED_UNKNOWN_MFC_ERROR(299),
        FAILED_PIA_PLAY_STORE(300),
        FAILED_PIA_PLAY_SERVICE(301),
        FAILED_PIA_VERIFICATION(302),
        FAILED_VERSION_UP(400),
        FAILED_MFC_ERROR(401),
        FAILED_APP_START_FAILED(502),
        FAILED_NETWORK_ERROR(800),
        FAILED_DELETE_CARD(801),
        FAILED_DISPLAY_SERVICE(802),
        FAILED_MEMORY_CLEAR(803),
        FAILED_FATAL_ERROR(999);

        private final int code;

        RESULT_CODE(int code) {
            this.code = code;
        }

        public int code() {
            return this.code;
        }

        static RESULT_CODE resolve(int code) {
            for (RESULT_CODE result_code : values()) {
                if (result_code.code() == code) {
                    return result_code;
                }
            }
            return FAILED_FATAL_ERROR;
        }
    }

    enum EVENT_ID {
        INITIALIZE(0),
        CHECK_VERSION_UP(10),
        TOS(20),
        CHECK_ISSUE_STATE(30),
        GET_REMAINED_CARD(31),
        SHOW_CARD_DELETE(32),
        CARD_DELETE(33),
        GET_SIM(40),
        GET_DISPLAY_SERVICE(51),
        SHOW_DISPLAY_SERVICE(52),
        EXECUTE_CONFIRMATION(53),
        EXECUTE(60),
        SUCCESS(70),
        FAILED(100);

        private final int id;

        EVENT_ID(int id) {
            this.id = id;
        }

        public int id() {
            return this.id;
        }

        static EVENT_ID resolve(int code) {
            for (EVENT_ID event_id : values()) {
                if (event_id.id() == code) {
                    return event_id;
                }
            }
            return FAILED;
        }
    }
}
