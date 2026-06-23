package com.felicanetworks.mfw.i.fbl;

import com.felicanetworks.mfw.i.cmn.SysException;

/* JADX INFO: loaded from: classes.dex */
public class ErrMsgOptr {
    public static final String CA_BREAK = "0100";
    public static final String CA_CANCEL = "0200";
    public static final int CA_ERR_APP_START_ILLEGAL_URL = 2100;
    public static final int CA_ERR_APP_START_INVALID_CHARA_PARAM = 2001;
    public static final int CA_ERR_APP_START_INVALID_CHARA_URL = 2002;
    public static final int CA_ERR_APP_START_INVALID_LENGTH_PARAM = 2000;
    public static final int CA_ERR_CMD_COMMON_EXPECTED_SELECT = 2406;
    public static final int CA_ERR_CMD_COMMON_ILLEGAL_COMMAND_TO_GENERAL = 2404;
    public static final int CA_ERR_CMD_COMMON_ILLEGAL_COMMAND_TO_MODE = 2403;
    public static final int CA_ERR_CMD_COMMON_ILLEGAL_COMMAND_TO_PLATFORM = 2405;
    public static final int CA_ERR_CMD_COMMON_TOOMUCH_LINE = 2401;
    public static final int CA_ERR_CMD_COMMON_TOOMUCH_START_COMMAND = 2407;
    public static final int CA_ERR_CMD_COMMON_UNKNOWN_COMMAND = 2402;
    public static final int CA_ERR_CMD_EXE_GETKEYVER = 4120;
    public static final int CA_ERR_CMD_EXE_GETKEYVER_BADSTATE = 4128;
    public static final int CA_ERR_CMD_EXE_GETKEYVER_TIMEOUT = 4129;
    public static final int CA_ERR_CMD_EXE_IC_LOCK = 4000;
    public static final int CA_ERR_CMD_EXE_IC_LOCK_JUDGE = 4009;
    public static final int CA_ERR_CMD_EXE_ONFSTART = 4330;
    public static final int CA_ERR_CMD_EXE_ONFSTART_AUTH = 4321;
    public static final int CA_ERR_CMD_EXE_ONFSTART_BADSTATE = 4338;
    public static final int CA_ERR_CMD_EXE_ONFSTART_INQUIRY = 4341;
    public static final int CA_ERR_CMD_EXE_ONFSTART_OPERATEDEVICE = 4339;
    public static final int CA_ERR_CMD_EXE_ONFSTART_UNEXPECTED = 4322;
    public static final int CA_ERR_CMD_EXE_READ = 4130;
    public static final int CA_ERR_CMD_EXE_READ_BADSTATE = 4138;
    public static final int CA_ERR_CMD_EXE_READ_NEEDPINCOLLATION = 4132;
    public static final int CA_ERR_CMD_EXE_READ_TIMEOUT = 4139;
    public static final int CA_ERR_CMD_EXE_RWGETKEYVER = 4220;
    public static final int CA_ERR_CMD_EXE_RWGETKEYVER_BADSTATE = 4228;
    public static final int CA_ERR_CMD_EXE_RWGETKEYVER_TIMEOUT = 4229;
    public static final int CA_ERR_CMD_EXE_RWREAD = 4230;
    public static final int CA_ERR_CMD_EXE_RWREAD_BADSTATE = 4238;
    public static final int CA_ERR_CMD_EXE_RWREAD_TIMEOUT = 4239;
    public static final int CA_ERR_CMD_EXE_RWSELECT = 4210;
    public static final int CA_ERR_CMD_EXE_RWSELECT_BADSTATE = 4218;
    public static final int CA_ERR_CMD_EXE_RWSELECT_ILLEGAL_IDM = 4711;
    public static final int CA_ERR_CMD_EXE_RWSELECT_ILLEGAL_NODE_CODE = 4712;
    public static final int CA_ERR_CMD_EXE_RWSELECT_TIMEOUT = 4219;
    public static final int CA_ERR_CMD_EXE_RWWRITE = 4240;
    public static final int CA_ERR_CMD_EXE_RWWRITE_BADSTATE = 4248;
    public static final int CA_ERR_CMD_EXE_RWWRITE_TIMEOUT = 4249;
    public static final int CA_ERR_CMD_EXE_SELECT = 4110;
    public static final int CA_ERR_CMD_EXE_SELECT_BADSTATE = 4118;
    public static final int CA_ERR_CMD_EXE_SELECT_NOT_FELICAOS20 = 4611;
    public static final int CA_ERR_CMD_EXE_SELECT_TIMEOUT = 4119;
    public static final int CA_ERR_CMD_EXE_START = 4310;
    public static final int CA_ERR_CMD_EXE_START_BADSTATE = 4318;
    public static final int CA_ERR_CMD_EXE_START_OPERATEDEVICE = 4319;
    public static final int CA_ERR_CMD_EXE_WRITE = 4140;
    public static final int CA_ERR_CMD_EXE_WRITE_BADSTATE = 4148;
    public static final int CA_ERR_CMD_EXE_WRITE_CASHBACK_FAILED = 4145;
    public static final int CA_ERR_CMD_EXE_WRITE_LACK_PURSE_VALUE = 4146;
    public static final int CA_ERR_CMD_EXE_WRITE_NEEDPINCOLLATION = 4142;
    public static final int CA_ERR_CMD_EXE_WRITE_TIMEOUT = 4149;
    public static final int CA_ERR_CMD_GETKEYVER_EMPTY_NODE_CODE = 2422;
    public static final int CA_ERR_CMD_GETKEYVER_INCORRECT_ARGS = 2421;
    public static final int CA_ERR_CMD_GETKEYVER_INVALID_CHARA_NODE_CODE = 2424;
    public static final int CA_ERR_CMD_GETKEYVER_INVALID_LENGTH_NODE_CODE = 2423;
    public static final int CA_ERR_CMD_GETKEYVER_NOTEXIST_NODE_CODE = 2426;
    public static final int CA_ERR_CMD_GETKEYVER_NOTPERMITTED_NODE_CODE = 2425;
    public static final int CA_ERR_CMD_HEAD_DUPLICATE_ELEMENT = 2212;
    public static final int CA_ERR_CMD_HEAD_DUPLICATE_PERMIT = 2224;
    public static final int CA_ERR_CMD_HEAD_EMPTY_NEXT_URL = 2243;
    public static final int CA_ERR_CMD_HEAD_EMPTY_PERMIT = 2223;
    public static final int CA_ERR_CMD_HEAD_EXPECTED_FAREWELL = 2202;
    public static final int CA_ERR_CMD_HEAD_INCORRECT_ARGS_MODE = 2232;
    public static final int CA_ERR_CMD_HEAD_INCORRECT_ARGS_PERMIT = 2222;
    public static final int CA_ERR_CMD_HEAD_INCORRECT_NEXT_URL = 2242;
    public static final int CA_ERR_CMD_HEAD_INVALID_ARGS_MODE = 2233;
    public static final int CA_ERR_CMD_HEAD_INVALID_CHARA_NEXT_URL = 2246;
    public static final int CA_ERR_CMD_HEAD_INVALID_CHARA_PERMIT = 2225;
    public static final int CA_ERR_CMD_HEAD_INVALID_ELEMENT = 2211;
    public static final int CA_ERR_CMD_HEAD_INVALID_FORMAT = 2201;
    public static final int CA_ERR_CMD_HEAD_NOTFOUND_MODE = 2231;
    public static final int CA_ERR_CMD_HEAD_NOTFOUND_NEXT_URL = 2241;
    public static final int CA_ERR_CMD_HEAD_NOTFOUND_PERMIT = 2221;
    public static final int CA_ERR_CMD_HEAD_NOTPERMITTED_NEXT_URL = 2247;
    public static final int CA_ERR_CMD_HEAD_TOOLONG_NEXT_URL = 2244;
    public static final int CA_ERR_CMD_HEAD_TOOMUCH_CMD = 3100;
    public static final int CA_ERR_CMD_ONFSTART_EMPTY_AUTH_URL = 2492;
    public static final int CA_ERR_CMD_ONFSTART_EMPTY_TICKET = 2497;
    public static final int CA_ERR_CMD_ONFSTART_INCORRECT_ARGS = 2491;
    public static final int CA_ERR_CMD_ONFSTART_INVALID_CHARA_AUTH_URL = 2494;
    public static final int CA_ERR_CMD_ONFSTART_INVALID_CHARA_INQUIRY_URL = 2496;
    public static final int CA_ERR_CMD_ONFSTART_INVALID_CHARA_TICKET = 2498;
    public static final int CA_ERR_CMD_ONFSTART_INVALID_LENGTH_AUTH_URL = 2493;
    public static final int CA_ERR_CMD_ONFSTART_INVALID_LENGTH_INQUIRY_URL = 2495;
    public static final int CA_ERR_CMD_READ_EMPTY_NODE_CODE = 2432;
    public static final int CA_ERR_CMD_READ_EMPTY_READ_BLOCK = 2442;
    public static final int CA_ERR_CMD_READ_EMPTY_START_BLOCK = 2439;
    public static final int CA_ERR_CMD_READ_ILLEGAL_READ_BLOCK = 2445;
    public static final int CA_ERR_CMD_READ_INCORRECT_ARGS = 2431;
    public static final int CA_ERR_CMD_READ_INVALID_CHARA_NODE_CODE = 2434;
    public static final int CA_ERR_CMD_READ_INVALID_CHARA_READ_BLOCK = 2444;
    public static final int CA_ERR_CMD_READ_INVALID_CHARA_START_BLOCK = 2441;
    public static final int CA_ERR_CMD_READ_INVALID_LENGTH_NODE_CODE = 2433;
    public static final int CA_ERR_CMD_READ_INVALID_LENGTH_READ_BLOCK = 2443;
    public static final int CA_ERR_CMD_READ_INVALID_LENGTH_START_BLOCK = 2440;
    public static final int CA_ERR_CMD_READ_NEEDAUTH_NODE_CODE = 2438;
    public static final int CA_ERR_CMD_READ_NOTEXIST_NODE_CODE = 2436;
    public static final int CA_ERR_CMD_READ_NOTPERMITTED_NODE_CODE = 2435;
    public static final int CA_ERR_CMD_READ_PINALIAS_NODE_CODE = 2437;
    public static final int CA_ERR_CMD_RWGETKEYVER_EMPTY_NODE_CODE = 2522;
    public static final int CA_ERR_CMD_RWGETKEYVER_INCORRECT_ARGS = 2521;
    public static final int CA_ERR_CMD_RWGETKEYVER_INVALID_CHARA_NODE_CODE = 2524;
    public static final int CA_ERR_CMD_RWGETKEYVER_INVALID_LENGTH_NODE_CODE = 2523;
    public static final int CA_ERR_CMD_RWGETKEYVER_NOTEXIST_NODE_CODE = 2526;
    public static final int CA_ERR_CMD_RWGETKEYVER_NOTPERMITTED_NODE_CODE = 2525;
    public static final int CA_ERR_CMD_RWREAD_EMPTY_NODE_CODE = 2532;
    public static final int CA_ERR_CMD_RWREAD_EMPTY_READ_BLOCK = 2543;
    public static final int CA_ERR_CMD_RWREAD_EMPTY_START_BLOCK = 2540;
    public static final int CA_ERR_CMD_RWREAD_ILLEGAL_READ_BLOCK = 2546;
    public static final int CA_ERR_CMD_RWREAD_INCORRECT_ARGS = 2531;
    public static final int CA_ERR_CMD_RWREAD_INVALID_CHARA_NODE_CODE = 2534;
    public static final int CA_ERR_CMD_RWREAD_INVALID_CHARA_READ_BLOCK = 2545;
    public static final int CA_ERR_CMD_RWREAD_INVALID_CHARA_START_BLOCK = 2542;
    public static final int CA_ERR_CMD_RWREAD_INVALID_LENGTH_NODE_CODE = 2533;
    public static final int CA_ERR_CMD_RWREAD_INVALID_LENGTH_READ_BLOCK = 2544;
    public static final int CA_ERR_CMD_RWREAD_INVALID_LENGTH_START_BLOCK = 2541;
    public static final int CA_ERR_CMD_RWREAD_NEEDAUTH_NODE_CODE = 2538;
    public static final int CA_ERR_CMD_RWREAD_NOTEXIST_NODE_CODE = 2536;
    public static final int CA_ERR_CMD_RWREAD_NOTPERMITTED_NODE_CODE = 2535;
    public static final int CA_ERR_CMD_RWREAD_PINALIAS_NODE_CODE = 2537;
    public static final int CA_ERR_CMD_RWSELECT_EMPTY_SYSTEM_CODE = 2502;
    public static final int CA_ERR_CMD_RWSELECT_ILLEGAL_NODE_SYSTEM_CODE = 2505;
    public static final int CA_ERR_CMD_RWSELECT_ILLEGAL_TIMEOUT = 2512;
    public static final int CA_ERR_CMD_RWSELECT_INCORRECT_ARGS = 2501;
    public static final int CA_ERR_CMD_RWSELECT_INVALID_CHARA_IDM = 2509;
    public static final int CA_ERR_CMD_RWSELECT_INVALID_CHARA_SYSTEM_CODE = 2504;
    public static final int CA_ERR_CMD_RWSELECT_INVALID_CHARA_TIMEOUT = 2511;
    public static final int CA_ERR_CMD_RWSELECT_INVALID_LENGTH_IDM = 2508;
    public static final int CA_ERR_CMD_RWSELECT_INVALID_LENGTH_MESSAGE = 2513;
    public static final int CA_ERR_CMD_RWSELECT_INVALID_LENGTH_SYSTEM_CODE = 2503;
    public static final int CA_ERR_CMD_RWSELECT_INVALID_LENGTH_TIMEOUT = 2510;
    public static final int CA_ERR_CMD_RWSELECT_NOTPERMITTED_SYSTEM_CODE = 2507;
    public static final int CA_ERR_CMD_RWSELECT_WILDCARD_SYSTEM_CODE = 2506;
    public static final int CA_ERR_CMD_RWWRITE_EMPTY_BLOCK_DATA = 2573;
    public static final int CA_ERR_CMD_RWWRITE_EMPTY_NODE_CODE = 2552;
    public static final int CA_ERR_CMD_RWWRITE_EMPTY_START_BLOCK = 2561;
    public static final int CA_ERR_CMD_RWWRITE_EMPTY_WRITE_BLOCK = 2565;
    public static final int CA_ERR_CMD_RWWRITE_EMPTY_WRITE_MODE = 2569;
    public static final int CA_ERR_CMD_RWWRITE_EXPECTED_PCBDW_TO_WRITE_MODE = 2572;
    public static final int CA_ERR_CMD_RWWRITE_ILLEGAL_WRITE_BLOCK = 2568;
    public static final int CA_ERR_CMD_RWWRITE_INCORRECT_ARGS = 2551;
    public static final int CA_ERR_CMD_RWWRITE_INVALID_CHARA_BLOCK_DATA = 2575;
    public static final int CA_ERR_CMD_RWWRITE_INVALID_CHARA_NODE_CODE = 2554;
    public static final int CA_ERR_CMD_RWWRITE_INVALID_CHARA_START_BLOCK = 2563;
    public static final int CA_ERR_CMD_RWWRITE_INVALID_CHARA_WRITE_BLOCK = 2567;
    public static final int CA_ERR_CMD_RWWRITE_INVALID_LENGTH_BLOCK_DATA = 2574;
    public static final int CA_ERR_CMD_RWWRITE_INVALID_LENGTH_NODE_CODE = 2553;
    public static final int CA_ERR_CMD_RWWRITE_INVALID_LENGTH_START_BLOCK = 2562;
    public static final int CA_ERR_CMD_RWWRITE_INVALID_LENGTH_WRITE_BLOCK = 2566;
    public static final int CA_ERR_CMD_RWWRITE_INVALID_START_BLOCK_TO_CYCLIC_NODE_CODE = 2564;
    public static final int CA_ERR_CMD_RWWRITE_INVALID_WRITE_MODE = 2570;
    public static final int CA_ERR_CMD_RWWRITE_NEEDAUTH_NODE_CODE = 2558;
    public static final int CA_ERR_CMD_RWWRITE_NOTEXIST_NODE_CODE = 2556;
    public static final int CA_ERR_CMD_RWWRITE_NOTPERMITTED_NODE_CODE = 2555;
    public static final int CA_ERR_CMD_RWWRITE_PINALIAS_NODE_CODE = 2557;
    public static final int CA_ERR_CMD_RWWRITE_READONLY_NODE_CODE = 2559;
    public static final int CA_ERR_CMD_SELECT_EMPTY_SYSTEM_CODE = 2412;
    public static final int CA_ERR_CMD_SELECT_ILLEGAL_NODE_SYSTEM_CODE = 2415;
    public static final int CA_ERR_CMD_SELECT_INCORRECT_ARGS = 2411;
    public static final int CA_ERR_CMD_SELECT_INVALID_CHARA_SYSTEM_CODE = 2414;
    public static final int CA_ERR_CMD_SELECT_INVALID_LENGTH_MESSAGE = 2418;
    public static final int CA_ERR_CMD_SELECT_INVALID_LENGTH_SYSTEM_CODE = 2413;
    public static final int CA_ERR_CMD_SELECT_NOTPERMITTED_SYSTEM_CODE = 2417;
    public static final int CA_ERR_CMD_SELECT_WILDCARD_SYSTEM_CODE = 2416;
    public static final int CA_ERR_CMD_START_EMPTY_URL = 2482;
    public static final int CA_ERR_CMD_START_INCORRECT_ARGS = 2481;
    public static final int CA_ERR_CMD_START_INVALID_CHARA_URL = 2484;
    public static final int CA_ERR_CMD_START_INVALID_LENGTH_URL = 2483;
    public static final int CA_ERR_CMD_START_INVALID_OCS_URL = 2485;
    public static final int CA_ERR_CMD_WRITE_EMPTY_BLOCK_DATA = 2472;
    public static final int CA_ERR_CMD_WRITE_EMPTY_NODE_CODE = 2452;
    public static final int CA_ERR_CMD_WRITE_EMPTY_START_BLOCK = 2460;
    public static final int CA_ERR_CMD_WRITE_EMPTY_WRITE_BLOCK = 2464;
    public static final int CA_ERR_CMD_WRITE_EMPTY_WRITE_MODE = 2468;
    public static final int CA_ERR_CMD_WRITE_EXPECTED_PCBDW_TO_WRITE_MODE = 2471;
    public static final int CA_ERR_CMD_WRITE_ILLEGAL_WRITE_BLOCK = 2467;
    public static final int CA_ERR_CMD_WRITE_INCORRECT_ARGS = 2451;
    public static final int CA_ERR_CMD_WRITE_INVALID_CHARA_BLOCK_DATA = 2474;
    public static final int CA_ERR_CMD_WRITE_INVALID_CHARA_NODE_CODE = 2454;
    public static final int CA_ERR_CMD_WRITE_INVALID_CHARA_START_BLOCK = 2462;
    public static final int CA_ERR_CMD_WRITE_INVALID_CHARA_WRITE_BLOCK = 2466;
    public static final int CA_ERR_CMD_WRITE_INVALID_LENGTH_BLOCK_DATA = 2473;
    public static final int CA_ERR_CMD_WRITE_INVALID_LENGTH_NODE_CODE = 2453;
    public static final int CA_ERR_CMD_WRITE_INVALID_LENGTH_START_BLOCK = 2461;
    public static final int CA_ERR_CMD_WRITE_INVALID_LENGTH_WRITE_BLOCK = 2465;
    public static final int CA_ERR_CMD_WRITE_INVALID_START_BLOCK_TO_CYCLIC_NODE_CODE = 2463;
    public static final int CA_ERR_CMD_WRITE_INVALID_WRITE_MODE = 2469;
    public static final int CA_ERR_CMD_WRITE_NEEDAUTH_NODE_CODE = 2458;
    public static final int CA_ERR_CMD_WRITE_NOTEXIST_NODE_CODE = 2456;
    public static final int CA_ERR_CMD_WRITE_NOTPERMITTED_NODE_CODE = 2455;
    public static final int CA_ERR_CMD_WRITE_PINALIAS_NODE_CODE = 2457;
    public static final int CA_ERR_CMD_WRITE_READONLY_NODE_CODE = 2459;
    public static final int CA_ERR_CONTENTTYPE_CMD = 8023;
    public static final int CA_ERR_CONTENTTYPE_CRL = 8021;
    public static final int CA_ERR_CONTENTTYPE_VERUP = 8020;
    public static final int CA_ERR_CRL_ILLEGAL_OFFLINE_JUDGE_NUM = 8103;
    public static final int CA_ERR_CRL_ILLEGAL_OFFLINE_JUDGE_TERM = 8106;
    public static final int CA_ERR_CRL_ILLEGAL_SERIAL_NO_NUM = 8109;
    public static final int CA_ERR_CRL_INCORRECT_SERIAL_NO_LINE = 8114;
    public static final int CA_ERR_CRL_INVALID_CHARA_OFFLINE_JUDGE_NUM = 8102;
    public static final int CA_ERR_CRL_INVALID_CHARA_OFFLINE_JUDGE_TERM = 8105;
    public static final int CA_ERR_CRL_INVALID_CHARA_REVOKED_SERIAL_NO_LINE = 8113;
    public static final int CA_ERR_CRL_INVALID_CHARA_SERIAL_NO_NUM = 8108;
    public static final int CA_ERR_CRL_INVALID_LENGTH_OFFLINE_JUDGE_NUM = 8101;
    public static final int CA_ERR_CRL_INVALID_LENGTH_OFFLINE_JUDGE_TERM = 8104;
    public static final int CA_ERR_CRL_INVALID_LENGTH_REVOKED_SERIAL_NO_LINE = 8112;
    public static final int CA_ERR_CRL_INVALID_LENGTH_SERIAL_NO_NUM = 8107;
    public static final int CA_ERR_CRL_INVALID_LINES = 8110;
    public static final int CA_ERR_CRL_NOTFOUND_SEPARATE_LINE = 8111;
    public static final int CA_ERR_CRL_REVOKED_PERMIT = 3010;
    public static final int CA_ERR_CRL_TOOLESS_LINES = 8100;
    public static final int CA_ERR_DOCOMO_START_TYPE = 6820;
    public static final int CA_ERR_DOCOMO_START_TYPE_IC_APP_DELETE = 6800;
    public static final int CA_ERR_ENCODE_AUTH = 8014;
    public static final int CA_ERR_ENCODE_CMD = 8013;
    public static final int CA_ERR_ENCODE_CRL = 8011;
    public static final int CA_ERR_ENCODE_INQUIRY = 8015;
    public static final int CA_ERR_ENCODE_PRBLMLOG = 8012;
    public static final int CA_ERR_ENCODE_VERUP = 8010;
    public static final int CA_ERR_HTTP_AUTH = 8004;
    public static final int CA_ERR_HTTP_CMD = 8003;
    public static final int CA_ERR_HTTP_CRL = 8001;
    public static final int CA_ERR_HTTP_INQUIRY = 8005;
    public static final int CA_ERR_HTTP_PRBLMLOG = 8002;
    public static final int CA_ERR_HTTP_VERUP = 8000;
    public static final int CA_ERR_OTHER_VERUP_RES_ILLEGAL_OFFLINE_JUDGE_NUM = 8204;
    public static final int CA_ERR_OTHER_VERUP_RES_ILLEGAL_OFFLINE_JUDGE_TERM = 8207;
    public static final int CA_ERR_OTHER_VERUP_RES_INVALID_CHARA_OFFLINE_JUDGE_NUM = 8203;
    public static final int CA_ERR_OTHER_VERUP_RES_INVALID_CHARA_OFFLINE_JUDGE_TERM = 8206;
    public static final int CA_ERR_OTHER_VERUP_RES_INVALID_LENGTH_OFFLINE_JUDGE_NUM = 8202;
    public static final int CA_ERR_OTHER_VERUP_RES_INVALID_LENGTH_OFFLINE_JUDGE_TERM = 8205;
    public static final int CA_ERR_OTHER_VERUP_RES_INVALID_LINE = 8201;
    public static final int CA_ERR_OTHER_VERUP_RES_INVALID_VERUP_TYPE = 8209;
    public static final int CA_ERR_OTHER_VERUP_RES_NOTFOUND_SEPARATE_LINE = 8208;
    public static final int CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_BFELICA = 1151;
    public static final int CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_CHIPISSUER = 1121;
    public static final int CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_EXTDATA = 1112;
    public static final int CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_EXTENSION = 1110;
    public static final int CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_FELICA_ACCESS = 1131;
    public static final int CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_NODES = 1144;
    public static final int CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_NODE_RANGE = 1146;
    public static final int CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_OFFLINE_ACCESS = 1140;
    public static final int CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_OFFLINE_TARGET = 1142;
    public static final int CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_OPFAPP = 1161;
    public static final int CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_PERMIT_PARAM = 1100;
    public static final int CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_VALIDITY = 1104;
    public static final int CA_ERR_PERMIT0001_INVALID_APP_SIGNER = 1183;
    public static final int CA_ERR_PERMIT0001_INVALID_CHIPISSUER = 1170;
    public static final int CA_ERR_PERMIT0001_INVALID_CHIPISSUER_NAME = 1122;
    public static final int CA_ERR_PERMIT0001_INVALID_COMBINATION_EXTDATA = 1195;
    public static final int CA_ERR_PERMIT0001_INVALID_DISTRIBUTION = 1176;
    public static final int CA_ERR_PERMIT0001_INVALID_EXTPARAM = 1116;
    public static final int CA_ERR_PERMIT0001_INVALID_GENERAL_COMMAND_CATEGORY = 1177;
    public static final int CA_ERR_PERMIT0001_INVALID_LENGTH_ISSUERID = 1022;
    public static final int CA_ERR_PERMIT0001_INVALID_LENGTH_KEYID = 1024;
    public static final int CA_ERR_PERMIT0001_INVALID_OFFLINE_ACCESS_LOWER_NODE = 1180;
    public static final int CA_ERR_PERMIT0001_INVALID_OFFLINE_ACCESS_SYSTEM_CODE = 1179;
    public static final int CA_ERR_PERMIT0001_INVALID_OFFLINE_ACCESS_UPPER_NODE = 1181;
    public static final int CA_ERR_PERMIT0001_INVALID_PERMIT_TYPE = 1171;
    public static final int CA_ERR_PERMIT0001_INVALID_PRIVILEGE_COMMAND_CATEGORY = 1178;
    public static final int CA_ERR_PERMIT0001_INVALID_SERIAL_NO = 1172;
    public static final int CA_ERR_PERMIT0001_INVALID_SERVICEID = 1175;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_APP_SIGNER = 1162;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_BFELICA = 1150;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_CHIPISSUER = 1120;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_COMMAND_CATEGORY = 1132;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_DISTRIBUTION = 1108;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_EXTDATA = 1111;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_EXTENSION = 1109;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_EXTID = 1113;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_EXTPARAM = 1114;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_FELICA_ACCESS = 1130;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_ISSUERID = 1021;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_KEYID = 1023;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_NODE = 1147;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_NODE_RANGE = 1145;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_OFFLINE_ACCESS = 1133;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_OFFLINE_TARGET = 1141;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_OPFAPP = 1160;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_PERMIT_TYPE = 1101;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_SERIAL_NO = 1102;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_SERVICEID = 1107;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_SYSTEM_NODES = 1143;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_URL_LIMITATION = 1152;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_VALIDITY = 1103;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_VALIDITY_FROM = 1105;
    public static final int CA_ERR_PERMIT0001_INVALID_TYPE_VALIDITY_TO = 1106;
    public static final int CA_ERR_PERMIT0001_INVALID_URL = 1182;
    public static final int CA_ERR_PERMIT0001_INVALID_VALIDITY_FROM = 1173;
    public static final int CA_ERR_PERMIT0001_INVALID_VALIDITY_TO = 1174;
    public static final int CA_ERR_PERMIT0001_NOTFOUND_EXTENTION = 1194;
    public static final int CA_ERR_PERMIT0001_NOTFOUND_SIGNER_INFO = 1901;
    public static final int CA_ERR_PERMIT0001_NOTFOUND_URL = 1196;
    public static final int CA_ERR_PERMIT0001_TOOLESS_CHILDREN_IN_PERMIT_PARAM = 1020;
    public static final int CA_ERR_PERMIT0001_UNKNOWN_EXTID = 1115;
    public static final int CA_ERR_PERMIT_BASE64 = 1000;
    public static final int CA_ERR_PERMIT_EMPTY_DISTRIBUTION = 1192;
    public static final int CA_ERR_PERMIT_IMPROPER_USER = 1900;
    public static final int CA_ERR_PERMIT_INCORRECT_CHILDREN_IN_PERMIT_PARAM = 1010;
    public static final int CA_ERR_PERMIT_INCORRECT_CHILDREN_IN_ROOT = 1003;
    public static final int CA_ERR_PERMIT_INVALID_FORMAT_DISTRIBUTION = 1193;
    public static final int CA_ERR_PERMIT_INVALID_TYPE_CHILDREN_IN_ROOT = 1004;
    public static final int CA_ERR_PERMIT_INVALID_TYPE_ROOT = 1002;
    public static final int CA_ERR_PERMIT_INVALID_TYPE_VER = 1011;
    public static final int CA_ERR_PERMIT_NOTFOUND_PUBLICKEY = 1030;
    public static final int CA_ERR_PERMIT_NOT_VERIFIED = 1031;
    public static final int CA_ERR_PERMIT_OUT_OF_VALIDITY = 3000;
    public static final int CA_ERR_PERMIT_PURSE = 1001;
    public static final int CA_ERR_PERMIT_UNKNOWN_CHIPISSUER_CODE = 3001;
    public static final int CA_ERR_PERMIT_UNKNOWN_PERMIT_TYPE = 3002;
    public static final int CA_ERR_PERMIT_UNKNOWN_VER = 1012;
    public static final int CA_ERR_PERMIT_VERIFY_FAILED = 1032;
    public static final String CA_NORMAL = "0000";
    public static final int CA_STOP = 900;
    private static final String CHANGE_POINT = "%";
    private static final String ERR_MSG_APP_END = "アプリを終了します。\n（%）";
    private static final String ERR_MSG_COMMUNICATION = "通信できません。\n通信設定・電波状態などを確認してください。\n（%）";
    private static final String ERR_MSG_COMMUNICATION_APP_END = "通信に失敗しました。\nアプリを終了します。\n（%）";
    private static final String ERR_MSG_IC_APP_DELETE = "本アプリを削除する場合はソフト一覧から本アプリを起動し、メニューからアプリ削除を選択してください。\n（%）";
    private static final String ERR_MSG_IC_LOCK = "ICカードロックが\n設定されています。\nロック解除してください。\n（%）";

    public static String getMessage(int i) {
        return createMessage(i, null);
    }

    public static String createMessage(int i, String str) {
        String strSearchMsg = searchMsg(i);
        if (Property.URL_VERUP_SITE.equals(strSearchMsg)) {
            throw new SysException((Class<?>) ErrMsgOptr.class, "createMessage", "Could not searchMsg. [errorCode = " + i + "]");
        }
        return createBuryMsg(strSearchMsg, CHANGE_POINT, str);
    }

    private static String searchMsg(int i) {
        String str = ERR_MSG_APP_END;
        switch (i) {
            case 1000:
            case CA_ERR_PERMIT_PURSE /* 1001 */:
            case CA_ERR_PERMIT_INVALID_TYPE_ROOT /* 1002 */:
            case CA_ERR_PERMIT_INCORRECT_CHILDREN_IN_ROOT /* 1003 */:
            case CA_ERR_PERMIT_INVALID_TYPE_CHILDREN_IN_ROOT /* 1004 */:
                break;
            default:
                switch (i) {
                    case CA_ERR_PERMIT_INCORRECT_CHILDREN_IN_PERMIT_PARAM /* 1010 */:
                    case CA_ERR_PERMIT_INVALID_TYPE_VER /* 1011 */:
                    case CA_ERR_PERMIT_UNKNOWN_VER /* 1012 */:
                        break;
                    default:
                        switch (i) {
                            case CA_ERR_PERMIT0001_TOOLESS_CHILDREN_IN_PERMIT_PARAM /* 1020 */:
                            case CA_ERR_PERMIT0001_INVALID_TYPE_ISSUERID /* 1021 */:
                            case CA_ERR_PERMIT0001_INVALID_LENGTH_ISSUERID /* 1022 */:
                            case CA_ERR_PERMIT0001_INVALID_TYPE_KEYID /* 1023 */:
                            case CA_ERR_PERMIT0001_INVALID_LENGTH_KEYID /* 1024 */:
                                break;
                            default:
                                switch (i) {
                                    case CA_ERR_PERMIT_NOTFOUND_PUBLICKEY /* 1030 */:
                                    case CA_ERR_PERMIT_NOT_VERIFIED /* 1031 */:
                                    case CA_ERR_PERMIT_VERIFY_FAILED /* 1032 */:
                                        break;
                                    default:
                                        switch (i) {
                                            case CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_PERMIT_PARAM /* 1100 */:
                                            case CA_ERR_PERMIT0001_INVALID_TYPE_PERMIT_TYPE /* 1101 */:
                                            case CA_ERR_PERMIT0001_INVALID_TYPE_SERIAL_NO /* 1102 */:
                                            case CA_ERR_PERMIT0001_INVALID_TYPE_VALIDITY /* 1103 */:
                                            case CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_VALIDITY /* 1104 */:
                                            case CA_ERR_PERMIT0001_INVALID_TYPE_VALIDITY_FROM /* 1105 */:
                                            case CA_ERR_PERMIT0001_INVALID_TYPE_VALIDITY_TO /* 1106 */:
                                            case CA_ERR_PERMIT0001_INVALID_TYPE_SERVICEID /* 1107 */:
                                            case CA_ERR_PERMIT0001_INVALID_TYPE_DISTRIBUTION /* 1108 */:
                                            case CA_ERR_PERMIT0001_INVALID_TYPE_EXTENSION /* 1109 */:
                                            case CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_EXTENSION /* 1110 */:
                                            case CA_ERR_PERMIT0001_INVALID_TYPE_EXTDATA /* 1111 */:
                                            case CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_EXTDATA /* 1112 */:
                                            case CA_ERR_PERMIT0001_INVALID_TYPE_EXTID /* 1113 */:
                                            case CA_ERR_PERMIT0001_INVALID_TYPE_EXTPARAM /* 1114 */:
                                            case CA_ERR_PERMIT0001_UNKNOWN_EXTID /* 1115 */:
                                            case CA_ERR_PERMIT0001_INVALID_EXTPARAM /* 1116 */:
                                            case CA_ERR_PERMIT0001_INVALID_CHIPISSUER /* 1170 */:
                                            case CA_ERR_PERMIT0001_INVALID_PERMIT_TYPE /* 1171 */:
                                            case CA_ERR_PERMIT0001_INVALID_SERIAL_NO /* 1172 */:
                                            case CA_ERR_PERMIT0001_INVALID_VALIDITY_FROM /* 1173 */:
                                            case CA_ERR_PERMIT0001_INVALID_VALIDITY_TO /* 1174 */:
                                            case CA_ERR_PERMIT0001_INVALID_SERVICEID /* 1175 */:
                                            case CA_ERR_PERMIT0001_INVALID_DISTRIBUTION /* 1176 */:
                                            case CA_ERR_PERMIT0001_INVALID_GENERAL_COMMAND_CATEGORY /* 1177 */:
                                            case CA_ERR_PERMIT0001_INVALID_PRIVILEGE_COMMAND_CATEGORY /* 1178 */:
                                            case CA_ERR_PERMIT0001_INVALID_OFFLINE_ACCESS_SYSTEM_CODE /* 1179 */:
                                            case CA_ERR_PERMIT0001_INVALID_OFFLINE_ACCESS_LOWER_NODE /* 1180 */:
                                            case CA_ERR_PERMIT0001_INVALID_OFFLINE_ACCESS_UPPER_NODE /* 1181 */:
                                            case CA_ERR_PERMIT0001_INVALID_URL /* 1182 */:
                                            case CA_ERR_PERMIT0001_INVALID_APP_SIGNER /* 1183 */:
                                            case CA_ERR_PERMIT_EMPTY_DISTRIBUTION /* 1192 */:
                                            case CA_ERR_PERMIT_INVALID_FORMAT_DISTRIBUTION /* 1193 */:
                                            case CA_ERR_PERMIT0001_NOTFOUND_EXTENTION /* 1194 */:
                                            case CA_ERR_PERMIT0001_INVALID_COMBINATION_EXTDATA /* 1195 */:
                                            case CA_ERR_PERMIT0001_NOTFOUND_URL /* 1196 */:
                                            case CA_ERR_APP_START_INVALID_LENGTH_PARAM /* 2000 */:
                                            case CA_ERR_APP_START_INVALID_CHARA_PARAM /* 2001 */:
                                            case CA_ERR_APP_START_INVALID_CHARA_URL /* 2002 */:
                                            case CA_ERR_APP_START_ILLEGAL_URL /* 2100 */:
                                            case CA_ERR_CMD_HEAD_INVALID_FORMAT /* 2201 */:
                                            case CA_ERR_CMD_HEAD_EXPECTED_FAREWELL /* 2202 */:
                                            case CA_ERR_CMD_HEAD_INVALID_ELEMENT /* 2211 */:
                                            case CA_ERR_CMD_HEAD_DUPLICATE_ELEMENT /* 2212 */:
                                            case CA_ERR_CMD_HEAD_NOTFOUND_PERMIT /* 2221 */:
                                            case CA_ERR_CMD_HEAD_INCORRECT_ARGS_PERMIT /* 2222 */:
                                            case CA_ERR_CMD_HEAD_EMPTY_PERMIT /* 2223 */:
                                            case CA_ERR_CMD_HEAD_INVALID_CHARA_PERMIT /* 2225 */:
                                            case CA_ERR_CMD_HEAD_NOTFOUND_NEXT_URL /* 2241 */:
                                            case CA_ERR_CMD_HEAD_INCORRECT_NEXT_URL /* 2242 */:
                                            case CA_ERR_CMD_HEAD_EMPTY_NEXT_URL /* 2243 */:
                                            case CA_ERR_CMD_HEAD_TOOLONG_NEXT_URL /* 2244 */:
                                            case CA_ERR_CMD_HEAD_INVALID_CHARA_NEXT_URL /* 2246 */:
                                            case CA_ERR_CMD_HEAD_NOTPERMITTED_NEXT_URL /* 2247 */:
                                            case CA_ERR_PERMIT_OUT_OF_VALIDITY /* 3000 */:
                                            case CA_ERR_PERMIT_UNKNOWN_CHIPISSUER_CODE /* 3001 */:
                                            case CA_ERR_PERMIT_UNKNOWN_PERMIT_TYPE /* 3002 */:
                                            case CA_ERR_CRL_REVOKED_PERMIT /* 3010 */:
                                            case CA_ERR_CMD_HEAD_TOOMUCH_CMD /* 3100 */:
                                            case CA_ERR_CMD_EXE_IC_LOCK_JUDGE /* 4009 */:
                                            case CA_ERR_DOCOMO_START_TYPE /* 6820 */:
                                                break;
                                            case CA_ERR_CMD_EXE_IC_LOCK /* 4000 */:
                                                str = ERR_MSG_IC_LOCK;
                                                break;
                                            case CA_ERR_DOCOMO_START_TYPE_IC_APP_DELETE /* 6800 */:
                                                str = ERR_MSG_IC_APP_DELETE;
                                                break;
                                            case CA_ERR_HTTP_VERUP /* 8000 */:
                                            case CA_ERR_HTTP_CRL /* 8001 */:
                                            case CA_ERR_HTTP_PRBLMLOG /* 8002 */:
                                            case CA_ERR_HTTP_CMD /* 8003 */:
                                                str = ERR_MSG_COMMUNICATION;
                                                break;
                                            case CA_ERR_ENCODE_VERUP /* 8010 */:
                                            case CA_ERR_ENCODE_CRL /* 8011 */:
                                            case CA_ERR_ENCODE_PRBLMLOG /* 8012 */:
                                            case CA_ERR_ENCODE_CMD /* 8013 */:
                                            case CA_ERR_CONTENTTYPE_VERUP /* 8020 */:
                                            case CA_ERR_CONTENTTYPE_CRL /* 8021 */:
                                            case CA_ERR_CONTENTTYPE_CMD /* 8023 */:
                                            case CA_ERR_CRL_TOOLESS_LINES /* 8100 */:
                                            case CA_ERR_CRL_INVALID_LENGTH_OFFLINE_JUDGE_NUM /* 8101 */:
                                            case CA_ERR_CRL_INVALID_CHARA_OFFLINE_JUDGE_NUM /* 8102 */:
                                            case CA_ERR_CRL_ILLEGAL_OFFLINE_JUDGE_NUM /* 8103 */:
                                            case CA_ERR_CRL_INVALID_LENGTH_OFFLINE_JUDGE_TERM /* 8104 */:
                                            case CA_ERR_CRL_INVALID_CHARA_OFFLINE_JUDGE_TERM /* 8105 */:
                                            case CA_ERR_CRL_ILLEGAL_OFFLINE_JUDGE_TERM /* 8106 */:
                                            case CA_ERR_CRL_INVALID_LENGTH_SERIAL_NO_NUM /* 8107 */:
                                            case CA_ERR_CRL_INVALID_CHARA_SERIAL_NO_NUM /* 8108 */:
                                            case CA_ERR_CRL_ILLEGAL_SERIAL_NO_NUM /* 8109 */:
                                            case CA_ERR_CRL_INVALID_LINES /* 8110 */:
                                            case CA_ERR_CRL_NOTFOUND_SEPARATE_LINE /* 8111 */:
                                            case CA_ERR_CRL_INVALID_LENGTH_REVOKED_SERIAL_NO_LINE /* 8112 */:
                                            case CA_ERR_CRL_INVALID_CHARA_REVOKED_SERIAL_NO_LINE /* 8113 */:
                                            case CA_ERR_CRL_INCORRECT_SERIAL_NO_LINE /* 8114 */:
                                            case CA_ERR_OTHER_VERUP_RES_INVALID_LINE /* 8201 */:
                                            case CA_ERR_OTHER_VERUP_RES_INVALID_LENGTH_OFFLINE_JUDGE_NUM /* 8202 */:
                                            case CA_ERR_OTHER_VERUP_RES_INVALID_CHARA_OFFLINE_JUDGE_NUM /* 8203 */:
                                            case CA_ERR_OTHER_VERUP_RES_ILLEGAL_OFFLINE_JUDGE_NUM /* 8204 */:
                                            case CA_ERR_OTHER_VERUP_RES_INVALID_LENGTH_OFFLINE_JUDGE_TERM /* 8205 */:
                                            case CA_ERR_OTHER_VERUP_RES_INVALID_CHARA_OFFLINE_JUDGE_TERM /* 8206 */:
                                            case CA_ERR_OTHER_VERUP_RES_ILLEGAL_OFFLINE_JUDGE_TERM /* 8207 */:
                                            case CA_ERR_OTHER_VERUP_RES_NOTFOUND_SEPARATE_LINE /* 8208 */:
                                            case CA_ERR_OTHER_VERUP_RES_INVALID_VERUP_TYPE /* 8209 */:
                                                str = ERR_MSG_COMMUNICATION_APP_END;
                                                break;
                                            default:
                                                switch (i) {
                                                    case CA_ERR_PERMIT0001_INVALID_TYPE_CHIPISSUER /* 1120 */:
                                                    case CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_CHIPISSUER /* 1121 */:
                                                    case CA_ERR_PERMIT0001_INVALID_CHIPISSUER_NAME /* 1122 */:
                                                        break;
                                                    default:
                                                        switch (i) {
                                                            case CA_ERR_PERMIT0001_INVALID_TYPE_FELICA_ACCESS /* 1130 */:
                                                            case CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_FELICA_ACCESS /* 1131 */:
                                                            case CA_ERR_PERMIT0001_INVALID_TYPE_COMMAND_CATEGORY /* 1132 */:
                                                            case CA_ERR_PERMIT0001_INVALID_TYPE_OFFLINE_ACCESS /* 1133 */:
                                                                break;
                                                            default:
                                                                switch (i) {
                                                                    case CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_OFFLINE_ACCESS /* 1140 */:
                                                                    case CA_ERR_PERMIT0001_INVALID_TYPE_OFFLINE_TARGET /* 1141 */:
                                                                    case CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_OFFLINE_TARGET /* 1142 */:
                                                                    case CA_ERR_PERMIT0001_INVALID_TYPE_SYSTEM_NODES /* 1143 */:
                                                                    case CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_NODES /* 1144 */:
                                                                    case CA_ERR_PERMIT0001_INVALID_TYPE_NODE_RANGE /* 1145 */:
                                                                    case CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_NODE_RANGE /* 1146 */:
                                                                    case CA_ERR_PERMIT0001_INVALID_TYPE_NODE /* 1147 */:
                                                                        break;
                                                                    default:
                                                                        switch (i) {
                                                                            case CA_ERR_PERMIT0001_INVALID_TYPE_BFELICA /* 1150 */:
                                                                            case CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_BFELICA /* 1151 */:
                                                                            case CA_ERR_PERMIT0001_INVALID_TYPE_URL_LIMITATION /* 1152 */:
                                                                                break;
                                                                            default:
                                                                                switch (i) {
                                                                                    case CA_ERR_PERMIT0001_INVALID_TYPE_OPFAPP /* 1160 */:
                                                                                    case CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_OPFAPP /* 1161 */:
                                                                                    case CA_ERR_PERMIT0001_INVALID_TYPE_APP_SIGNER /* 1162 */:
                                                                                        break;
                                                                                    default:
                                                                                        str = Property.URL_VERUP_SITE;
                                                                                        break;
                                                                                }
                                                                                break;
                                                                        }
                                                                        break;
                                                                }
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                        }
                        break;
                }
                break;
        }
        return str.equals(Property.URL_VERUP_SITE) ? Property.URL_VERUP_SITE : str;
    }

    private static String createBuryMsg(String str, String str2, String str3) {
        StringBuffer stringBuffer = new StringBuffer();
        int iIndexOf = str.indexOf(str2);
        if (iIndexOf == -1) {
            return str;
        }
        if (iIndexOf == 0) {
            if (str3 != null) {
                stringBuffer.append(str3);
            }
            stringBuffer.append(str.substring(str2.length(), str.length()));
        } else if (str2.length() + iIndexOf == str.length()) {
            stringBuffer.append(str.substring(0, iIndexOf));
            if (str3 != null) {
                stringBuffer.append(str3);
            }
        } else {
            stringBuffer.append(str.substring(0, iIndexOf));
            if (str3 != null) {
                stringBuffer.append(str3);
            }
            stringBuffer.append(str.substring(iIndexOf + str2.length(), str.length()));
        }
        return stringBuffer.toString();
    }
}
