//
// IMfiFelica.aidl
//
// Copyright 2017-2020 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.IFelicaEventListener;
import com.felicanetworks.mfc.IFelicaPushAppNotificationListener;
import com.felicanetworks.mfc.DataList;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.BlockDataList;
import com.felicanetworks.mfc.PushSegment;
import com.felicanetworks.mfc.PushSegmentParcelableWrapper;
import com.felicanetworks.mfc.PrivacySettingData;
import com.felicanetworks.mfc.FelicaResultInfo;
import com.felicanetworks.mfc.FelicaResultInfoBoolean;
import com.felicanetworks.mfc.FelicaResultInfoInt;
import com.felicanetworks.mfc.FelicaResultInfoByteArray;
import com.felicanetworks.mfc.FelicaResultInfoIntArray;
import com.felicanetworks.mfc.FelicaResultInfoBlockCountInformationArray;
import com.felicanetworks.mfc.FelicaResultInfoDataArray;
import com.felicanetworks.mfc.FelicaResultInfoNodeInformation;
import com.felicanetworks.mfc.FelicaResultInfoKeyInformationArray;

import com.felicanetworks.mfc.IFSCEventListener;
import com.felicanetworks.mfc.DeviceList;

import com.felicanetworks.mfc.mfi.CardInfo;
import com.felicanetworks.mfc.mfi.ILoginEventCallback;
import com.felicanetworks.mfc.mfi.ILogoutEventCallback;
import com.felicanetworks.mfc.mfi.ICardDeleteEventCallback;
import com.felicanetworks.mfc.mfi.ICardDisableEventCallback;
import com.felicanetworks.mfc.mfi.ICardEnableEventCallback;
import com.felicanetworks.mfc.mfi.ICardIssueEventCallback;
import com.felicanetworks.mfc.mfi.ICardListEventCallback;
import com.felicanetworks.mfc.mfi.ICardAdditionalInfoListEventCallback;
import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.mfc.mfi.FelicaResultInfoSeInfo;
import com.felicanetworks.mfc.mfi.FelicaResultInfoString;
/* AGP2 2019/01/20 add START */
import com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback;
import com.felicanetworks.mfc.mfi.ILinkageDataListEventCallback;
import com.felicanetworks.mfc.mfi.IInitializedEventCallback;
import com.felicanetworks.mfc.mfi.ICardListEventCallback;
import com.felicanetworks.mfc.mfi.ICardIssueEventCallback;
import com.felicanetworks.mfc.mfi.ISilentStartEventCallback;
import com.felicanetworks.mfc.mfi.IPipeEventCallback;
/* AGP2 2019/01/20 add END */
/* AGP2 2019/03/20 add START */
import com.felicanetworks.mfc.mfi.ICardAccessEventCallback;
import com.felicanetworks.mfc.mfi.ICardIssueV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardEnableV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardDisableV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardDeleteV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardReissuableDeleteEventCallback;
import com.felicanetworks.mfc.mfi.FelicaResultInfoStringArray;
/* AGP2 2019/03/20 add END */
/* FY19-02 2019/12/10 add START */
import com.felicanetworks.mfc.mfi.IDataListEventCallback;
/* FY19-02 2019/12/10 add END */
/* FY20-01 2020/04/17 add START */
import com.felicanetworks.mfc.mfi.IServerOperationEventCallback;
/* FY20-01 2020/04/17 add END */
/* FY20-01_Step2 2020/09/30 add START */
import com.felicanetworks.mfc.mfi.ICachedCardEnableEventCallback;
import com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback;
import com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardDeleteEventCallback;
/* FY20-01_Step2 2020/09/30 add END */

/**
 * FeliCa APIを実行するためのAIDLインターフェースです。
 * <p>
 */
interface IMfiFelica {

    /**
     * FeliCaチップの利用開始処理を実施します。<p>
     * permitListにはnullを設定することが可能です。
     * <p>
     * @param packageName パッケージ名
     * @param listener 処理結果通知用リスナ
     * <p>
     * @return 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_SECURITY} アクセスが許可されない場合
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_ILLEGAL_ARGUMENT} 処理結果通知用リスナがnullの場合
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} 利用開始処理が失敗した場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_USED_BY_OTHER_APP}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ALREADY_ACTIVATED}</td>
     * </tr>
     * </tbody> </table>
     * ※種別が{@link FelicaException#ID_ILLEGAL_STATE_ERROR}でタイプが{@link FelicaException#TYPE_USED_BY_OTHER_APP}の場合は
     * {@link FelicaResultInfo#getOtherAppPID}でFelica利用中のプロセスIDを取得できます。
     */
    FelicaResultInfo activateFelica(in String packageName, IFelicaEventListener listener);

    /**
     * FeliCaチップをクローズします。チップの電源はオフになります。既にクローズされている場合は何も行いません。
     * <p>
     * <code>FSC</code>
     * がFeliCaサーバとのオンライン処理を行っている間にこのメソッドが呼ばれた場合、FeliCaサーバに何も通知することなく強制終了します。
     * <p>
     * {@link #setPushNotificationListener}で設定したPushアプリ通知リスナ及びアプリケーション識別コードもクリアします。
     * <p>
     * @return 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} クローズが失敗した場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfo close();

    /**
     * 指定したエリアまたはサービスのブロック使用状況（割り当てブロック数および空きブロック数）をFeliCaチップから取得します。
     * <p>
     * @param nodeCodeList エリアまたはサービスの一覧
     * @param timeout タイムアウト値
     * @param retry リトライ回数
     * <p>
     * @return 指定したエリアまたはサービスのブロック使用状況
     * <p>
     * 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_ILLEGAL_ARGUMENT} エリアまたはサービスの一覧の数が規定値の範囲外となる場合
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} 割り当てブロック数および空きブロック数が取得できない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_SELECTED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_PERMISSION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ILLEGAL_METHOD_CALL}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_GET_BLOCK_COUNT_INFORMATION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_GET_BLOCK_COUNT_INFORMATION_FAILED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_GET_BLOCK_COUNT_INFORMATION_FAILED}</td>
     * </tr>
     * </tbody> </table>
     * <p>
     * ※種別が{@link FelicaException#ID_GET_BLOCK_COUNT_INFORMATION_ERROR}の場合は
     * ステータスフラグをチェックすることによりエラー詳細を取得できます。
    　    */
    FelicaResultInfoBlockCountInformationArray getBlockCountInformation(in int[] nodeCodeList, int timeout, int retry);

    /**
     * FeliCaチップからコンテナIDを取得します。
     * <p>
     * @param timeout タイムアウト値
     * @param retry リトライ回数
     * @return コンテナID
     * <p>
     * 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} コンテナIDの取得ができない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_SELECTED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_PERMISSION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ILLEGAL_METHOD_CALL}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_GET_CONTAINER_ID_FAILED}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfoByteArray getContainerId(int timeout, int retry);

    /**
     * FeliCaチップのコンテナ発行情報を取得します。
     * <p>
     * @param timeout タイムアウト値
     * @param retry リトライ回数
     * @return FeliCaチップのコンテナ発行情報
     * <p>
     * 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} コンテナ発行情報が取得できない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_SELECTED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_GET_CONTAINER_ISSUE_INFORMATION_FAILED}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfoByteArray getContainerIssueInformation(int timeout, int retry);

    /**
     * 現在選択されているシステムのICコードを取得します。
     * <p>
     * @return 現在選択されているシステムのICコード
     * <p>
     * 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} ICコードが取得できない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_SELECTED}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfoByteArray getICCode();

    /**
     * 現在選択されているシステムのIDmを取得します。
     * <p>
     * @return 現在選択されているシステムのIDm
     * <p>
     * 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} IDmが取得できない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_SELECTED}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfoByteArray getIDm();

    /**
     * FeliCaチップのシステム選択で使用したインタフェース種別(有線・無線)を取得します。
     * <p>
     * @return インタフェース種別
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">インタフェース種別</th>
     * <th style="text-align: center;">定数</th>
     * </tr>
     * <tr>
     * <td>有線インタフェース</td>
     * <td>{@link Felica#INTERFACE_WIRED}</td>
     * </tr>
     * <tr>
     * <td>無線インタフェース</td>
     * <td>{@link Felica#INTERFACE_WIRELESS}</td>
     * </tr>
     * </tbody> </table>
     * <p>
     * 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} インタフェース種別が取得できない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_SELECTED}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfoInt getInterface();

    /**
     * 指定されたサービス、またはエリアの鍵バージョンを取得します。<code>0xffff</code>(ノードコードサイズが4バイトの場合は<code>0xffffffff</code>)
     * が指定された場合、現在選択されているシステムの鍵バージョンを返します。<p>
     * serviceCodeには許可証に含まれるノードコードの範囲を指定することができます。
     * <p>
     * @param serviceCode 鍵バージョンを取得するサービス、エリアコード
     * @param timeout タイムアウト値
     * @param retry リトライ回数
     * @return 指定されたサービス、エリアの鍵バージョン
     * <p>
     * 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_ILLEGAL_ARGUMENT} サービス、エリアコードの属性値が正しくない場合
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} サービス、またはエリアの鍵バージョンが取得できない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_SELECTED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_PERMISSION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ILLEGAL_NODECODE}</td>
     * </tr>
     * <tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_GET_KEY_VERSION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_SERVICE_NOT_FOUND}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_GET_KEY_VERSION_FAILED}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfoInt getKeyVersion(int serviceCode, int timeout, int retry);

    /**
     * 指定したエリア以下の階層のノードの情報（エリアコード/エンドサービスコードの一覧およびサービスコードの一覧）をFeliCaチップから取得します。
     * <p>
     * @param parentAreaCode 親エリアコード
     * @param timeout タイムアウト値
     * @param retry リトライ回数
     * <p>
     * @return 指定したエリア以下の階層のノードの情報
     * <p>
     * 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_ILLEGAL_ARGUMENT} サービス、エリアコードが正しくない場合
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} ノードの情報が取得できない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_SELECTED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_PERMISSION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ILLEGAL_METHOD_CALL}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_GET_NODE_INFORMATION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_SERVICE_NOT_FOUND }</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_GET_NODE_INFORMATION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_GET_NODE_INFORMATION_FAILED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_GET_NODE_INFORMATION_FAILED}</td>
     * </tr>
     * </tbody> </table>
     * <p>
     * ※種別が{@link FelicaException#ID_GET_NODE_INFORMATION_ERROR}の場合は
     * ステータスフラグをチェックすることによりエラー詳細を取得できます。
     */
    FelicaResultInfoNodeInformation getNodeInformation(int parentAreaCode, int timeout, int retry);

    /**
     * 指定したエリア以下の階層のプライバシ設定が有効なノードの情報（エリアコード/エンドサービスコードの一覧およびサービスコードの一覧）をFeliCaチップから取得します。<p>
     * parentAreaCodeには許可証に含まれるノードコードの範囲を指定することができます。
     * <p>
     * @param timeout タイムアウト値
     * @param retry リトライ回数
     * @param parentAreaCode 親エリアコード
     * <p>
     * @return 指定したエリア以下の階層のプライバシ設定が有効なノードの情報
     * <p>
     * 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_ILLEGAL_ARGUMENT} 許可証に設定されていないエリアコードを指定した場合またはエリアコードではない場合
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} プライバシ設定が有効なノードの情報が取得できない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_SELECTED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_GET_PRIVACY_NODE_INFORMATION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_SERVICE_NOT_FOUND }</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_GET_PRIVACY_NODE_INFORMATION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_GET_PRIVACY_NODE_INFORMATION_FAILED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_PERMISSION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ILLEGAL_METHOD_CALL}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_PERMISSION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ILLEGAL_NODECODE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_GET_PRIVACY_NODE_INFORMATION_FAILED}</td>
     * </tr>
     * </tbody> </table>
     * <p>
     * ※種別が{@link FelicaException#ID_GET_PRIVACY_NODE_INFORMATION_ERROR}の場合は
     * ステータスフラグをチェックすることによりエラー詳細を取得できます。
     */
    FelicaResultInfoNodeInformation getPrivacyNodeInformation(int parentAreaCode, int timeout, int retry);

    /**
     * RFS端子の状態を取得します。
     * <p>
     * @return RFS端子の状態（true: High / false: Low）
     * <p>
     * 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} RFS端子の状態取得ができない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfoBoolean getRFSState();

    /**
     * 現在選択されているシステムのシステムコードを取得します。
     * <p>
     * @return 現在選択されているシステムのシステムコード
     * <p>
     * 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} システムコードが取得できない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_SELECTED}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfoInt getSystemCode();

    /**
     * FeliCaチップからシステムコードの一覧を取得します。
     * <p>
     * @param timeout タイムアウト値
     * @param retry リトライ回数
     * @return システムコードの一覧
     * <p>
     * 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} システムコードの一覧が取得できない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_SELECTED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_PERMISSION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ILLEGAL_METHOD_CALL}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_GET_SYSTEM_CODE_LIST_FAILED}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfoIntArray getSystemCodeList(int timeout, int retry);

    /**
     * FeliCaチップの利用終了処理を実施します。
     * <p>
     * <p>
     * @return 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} 利用終了処理が失敗した場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_CLOSED}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfo inactivateFelica();

    /**
     * FeliCaチップをオープンします。チップの電源がオンになります。 既にオープンされている場合は何も行いません。
     * オープン直後のノードコードサイズは2バイト（{@link #NODECODESIZE_2}）です。
     * <p>
     * @return 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} オープンが失敗した場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_OPEN_FAILED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_OPEN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_IC_CHIP_FORMATTING}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_OPEN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_FELICA_NOT_AVAILABLE}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfo open();

    /**
     * Pushによるデータ送信処理を行います。
     * <p>
     * @param timeout タイムアウト値
     * @return 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_ILLEGAL_ARGUMENT} 送信データ列のサイズが192byteを超えた場合
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} 送信に失敗した場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_PUSH_FAILED}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfo push(in PushSegmentParcelableWrapper pushSegmentParcelableWrapper);

    /**
     * 指定されたブロックリストを使用してブロックデータを読み込みます。<p>
     * blockListには許可証に含まれるノードコードの範囲を指定することができます。
     * <p>
     * @param blockList 読み込みに使用するブロックリスト
     * @param timeout タイムアウト値(分)
     * @param retry リトライ回数
     * @return 指定されたブロックリストに対応するブロックデータのリスト
     * <p>
     * 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_ILLEGAL_ARGUMENT} ブロックリストが <code>null</code> 、または空の場合
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} ブロックデータが読み込めない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_SELECTED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_PERMISSION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ILLEGAL_NODECODE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_READ_ERROR}</td>
     * <td>{@link FelicaException#TYPE_SERVICE_NOT_FOUND}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_READ_ERROR}</td>
     * <td>{@link FelicaException#TYPE_BLOCK_NOT_FOUND}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_READ_ERROR}</td>
     * <td>{@link FelicaException#TYPE_PIN_NOT_CHECKED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_READ_ERROR}</td>
     * <td>{@link FelicaException#TYPE_READ_FAILED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_READ_FAILED}</td>
     * </tr>
     * </tbody> </table>
     * <p>
     * ※種別が{@link FelicaException#ID_READ_ERROR}の場合は
     * ステータスフラグをチェックすることによりエラー詳細を取得できます。
     * <p>
     * ※無線インタフェース選択時、種別が{@link FelicaException#ID_READ_ERROR}の場合は、
     * タイプが{@link FelicaException#TYPE_READ_FAILED}固定となります。
     */
    FelicaResultInfoDataArray read(in BlockList blockList,in int timeout,in int retry);

    /**
     * FeliCaチップをソフトウェアリセットします。
     * 搬送波が出ている時は停止します。
     * システムが未選択になります。
     * ノードコードサイズは2バイト（{@link #NODECODESIZE_2}）に戻ります。
     * <p>
     * @return 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} FeliCaチップのリセットが失敗した場合
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_PERMISSION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ILLEGAL_METHOD_CALL}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_RESET_FAILED}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfo reset();

    /**
     * 有線インタフェースを使用してFeliCaチップ上のシステムを選択します。
     * <p>
     * システムコードにはワイルドカード( <code>0xffff, 0x00ff, 0xff00</code> )を指定することができます。
     *
     * @param systemCode システムコード
     * @param timeout タイムアウト値
     * @param retry リトライ回数
     * @return 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_ILLEGAL_ARGUMENT} システムコードが範囲外の場合(
     *             <code>systemCode < 0x0000 || systemCode > 0xffff</code>)
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} FeliCaチップ上のシステムを選択できない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_PERMISSION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ILLEGAL_METHOD_CALL}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_SELECT_FAILED}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfo select(int systemCode);

    /**
     * 指定したインタフェースを使用してFeliCaチップ上のシステムを選択します。
     * <p>
     * システムコードにはワイルドカード( <code>0xffff, 0x00ff, 0xff00</code> )を指定することができます。
     * <p>
     * @param target インタフェース種別
     * @param timeout タイムアウト値
     * @param retry リトライ回数
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">インタフェース種別</th>
     * <th style="text-align: center;">定数</th>
     * </tr>
     * <tr>
     * <td>有線インタフェース</td>
     * <td>{@link #INTERFACE_WIRED}</td>
     * </tr>
     * <tr>
     * <td>無線インタフェース</td>
     * <td>{@link #INTERFACE_WIRELESS}</td>
     * </tr>
     * </tbody> </table>
     * <p>
     * @param systemCode システムコード
     * @return 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_ILLEGAL_ARGUMENT} システムコードが範囲外の場合(
     *             <code>systemCode < 0x0000 || systemCode > 0xffff</code>)、
     *             またはtargetが{@link #INTERFACE_WIRED}、
     *             {@link #INTERFACE_WIRELESS}のいずれでもない場合
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} FeliCaチップ上のシステムを選択できない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_PERMISSION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ILLEGAL_METHOD_CALL}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_SELECT_FAILED}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfo selectWithTarget(int target, int systemCode);

    /**
     * ノードのプライバシ設定を変更します。
     * <p>
     * @param privacySettingData ノードのプライバシ設定の一覧
     * @param timeout タイムアウト値
     * @param retry リトライ回数
     * <p>
     * @return 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_ILLEGAL_ARGUMENT} ノードの数が規定値の範囲外となる場合
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} プライバシが設定できない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_SELECTED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_SET_PRIVACY_ERROR}</td>
     * <td>{@link FelicaException#TYPE_SERVICE_NOT_FOUND }</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_SET_PRIVACY_ERROR}</td>
     * <td>{@link FelicaException#TYPE_SET_PRIVACY_FAILED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_PERMISSION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ILLEGAL_METHOD_CALL}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_SET_PRIVACY_FAILED}</td>
     * </tr>
     * </tbody> </table>
     * <p>
     * ※種別が{@link FelicaException#ID_SET_PRIVACY_ERROR}の場合は
     * ステータスフラグをチェックすることによりエラー詳細を取得できます。
     */
    FelicaResultInfo setPrivacy(in PrivacySettingData[] privacySettingData, int timeout, int retry);

    /**
     * 指定されたブロックデータリストを使用してブロックデータを書き込みます。<p>
     * blockDataListには許可証に含まれるノードコードの範囲を指定することができます。
     * <p>
     * @param blockDataList 書き込みに使用するブロックデータリスト
     * @param timeout タイムアウト値
     * @param retry リトライ回数
     * @return 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_ILLEGAL_ARGUMENT} ブロックデータリストが <code>null</code> 、または空の場合
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} ブロックデータが書き込めない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_SELECTED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_PERMISSION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ILLEGAL_NODECODE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_WRITE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_PURSE_FAILED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_WRITE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CASH_BACK_FAILED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_WRITE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_PIN}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_WRITE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CHECK_PIN_LIMIT}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_WRITE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CHECK_PIN_OVERRUN}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_WRITE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_SERVICE_NOT_FOUND}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_WRITE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_BLOCK_NOT_FOUND}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_WRITE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_PIN_NOT_CHECKED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_WRITE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ENABLE_PIN_FAILED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_WRITE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_WRITE_FAILED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_WRITE_FAILED}</td>
     * </tr>
     * </tbody> </table>
     * <p>
     * ※種別が{@link FelicaException#ID_WRITE_ERROR}の場合は
     * ステータスフラグをチェックすることによりエラー詳細を取得できます。
     * <p>
     * ※無線インタフェース選択時、種別が{@link FelicaException#ID_WRITE_ERROR}の場合は、
     * タイプが{@link FelicaException#TYPE_WRITE_FAILED}固定となります。
     */
    FelicaResultInfo write(in BlockDataList blockDataList, int timeout, int retry);

    /**
     * Push受信によるアプリケーション通知のリスナを設定します。
     * <p>
     * 受信したデータのアプリケーション識別コードと設定したアプリケーション識別コードが異なる場合、通知は行われません。<p>
     * Pushアプリ通知リスナにnullを指定した場合、リスナは解除されます。
     * @param listener Pushアプリ通知リスナ
     * @param appIdentificationCode アプリケーション識別コード
     * @return 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_ILLEGAL_ARGUMENT} Pushアプリ通知リスナがnullかつ、アプリケーション識別コードがnullでない場合
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} リスナ登録に失敗した場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfo setPushNotificationListener(IFelicaPushAppNotificationListener listener, String appIdentificationCode);

    /**
     * FeliCaチップにアクセスする際のノードコード(サービス、エリアコード)サイズを設定します。
     * <p>
     * @param nodeCodeSize 設定するノードコードサイズ
     * @param timeout タイムアウト値
     * @param retry リトライ回数
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">ノードコードサイズ</th>
     * <th style="text-align: center;">定数</th>
     * </tr>
     * <tr>
     * <td>2バイト</td>
     * <td>{@link #NODECODESIZE_2}</td>
     * </tr>
     * <tr>
     * <td>4バイト</td>
     * <td>{@link #NODECODESIZE_4}</td>
     * </tr>
     * </tbody> </table>
     * @return 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_ILLEGAL_ARGUMENT} ノードコードサイズが不正な場合(
     *             {@link #NODECODESIZE_2}、{@link #NODECODESIZE_4}のいずれでもない場合)
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} ノードコードサイズが設定できない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_SELECTED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_SET_NODECODESIZE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_SET_NODECODESIZE_FAILED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_SET_NODECODESIZE_FAILED}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfo setNodeCodeSize(int nodeCodeSize, int timeout, int retry);

    /**
     * オンライン処理の実行が可能かどうかのチェックを行います
     * <p>
     * @throws FelicaException オンライン処理実行不可の場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_PERMISSION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ILLEGAL_METHOD_CALL}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfo checkOnlineAccess();

    /**
     * selectで使用されるタイムアウト値を設定します。
     *
     * @param timeout 設定するタイムアウト値(ミリ秒)
     * @throws FelicaException <code>Felica</code> を使用しているアプリと、現在のコンテキストのアプリ間でUID/PIDが異なる.
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfo setSelectTimeout(int timeout);

    /**
     * selectで使用されるタイムアウト値を取得します。
     *
     * @return timeout 設定するタイムアウト値(ミリ秒)
     * @throws FelicaException <code>Felica</code> を使用しているアプリと、現在のコンテキストのアプリ間でUID/PIDが異なる.
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_REMOTE_ACCESS_FAILED}</td>
     * </tr>
     * </tbody> </table>
     */
   FelicaResultInfoInt getSelectTimeout();

    /**
     * オフライン機能を中断します。
     *
     * @throws FelicaException　中断可能状態でない場合
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ACTIVATING_FALP}</td>
     * </tr>
     * </tbody> </table>
     */
	FelicaResultInfo cancelOffline();

    /**
     * 指定したbyte配列をそのままコマンドパケットとしてFeliCaチップに対し実行します。
     * <p>
     * 実行したコマンドに対するレスポンスパケットが戻り値(byte[])となります。
     * <p>
     * @param commandPacket FeliCaチップに対して実行するコマンドパケット
     * @param timeout タイムアウト値
     * @param retry リトライ回数
     * <p>
     * @return レスポンス
     * <p>
     * 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_ILLEGAL_ARGUMENT} 指定されたコマンドがnull又はサイズが0又はサイズが255以上のとき
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} コマンドを実行できない場合（詳細は下表を参照）
     * <p>
     * <table style="vertical-align: middle;" border="1" cellpadding="2"
     * cellspacing="2"> <tbody>
     * <tr>
     * <th style="text-align: center;">種別</th>
     * <th style="text-align: center;">タイプ</th>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_ACTIVATED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_PERMISSION_ERROR}</td>
     * <td>{@link FelicaException#TYPE_ILLEGAL_METHOD_CALL}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_INVALID_RESPONSE}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_TIMEOUT_OCCURRED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     * <td>{@link FelicaException#TYPE_EXECUTE_FELICA_COMMAND_FAILED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_IO_ERROR}</td>
     * <td>{@link FelicaException#TYPE_OFFLINE_CANCELED}</td>
     * </tr>
     * <tr>
     * <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     * <td>{@link FelicaException#TYPE_NOW_EXECUTING_FALP}</td>
     * </tr>
     * </tbody> </table>
     */
    FelicaResultInfoByteArray executeFelicaCommand(in byte[] commandPacket, int timeout, int retry);

    FelicaResultInfoKeyInformationArray getKeyVersionV2(in int[] nodeCodeList, int timeout, int retry);


    // 以下、MfiClientオンラインAPI

    FelicaResultInfo login(in String accountIssuer, in String accountName, ILoginEventCallback callback);

    FelicaResultInfoString getCurrentAccountHash();

    FelicaResultInfo clearMfiAccount();

    FelicaResultInfoSeInfo getSeInfomation();

    FelicaResultInfo logout(boolean autoMfiServerLogout, ILogoutEventCallback callback);

    FelicaResultInfo getCardList(ICardListEventCallback callback);

    FelicaResultInfo getCardAdditionalInfoList(ICardAdditionalInfoListEventCallback callback);

    FelicaResultInfo issueCard(in String linkageData, ICardIssueEventCallback callback);

    FelicaResultInfo enable(in CardInfo cardInfo, ICardEnableEventCallback callback);

    FelicaResultInfo disable(in CardInfo cardInfo, ICardDisableEventCallback callback);

    FelicaResultInfo delete(in CardInfo cardInfo, in String linkageData, ICardDeleteEventCallback
    callback);

    FelicaResultInfo cancelCardOperation();

    /**
     * FeliCaサーバとのオンライン処理を開始します。
     * <p>
     *
     * @param url FeliCaサーバのURL
     * @param deviceList <code>DeviceList</code>
     * @param fscEventListener <code>FSC</code> イベントリスナ
     * @param felica IFelicaブジェクト
     * @return 例外発生時、以下の例外情報を返します。例外種別は{@link ResultInfo#getExceptionType}で取得します。
     * <p>
     * {@link ResultInfo#EXCEPTION_TYPE_ILLEGAL_ARGUMENT} 指定されたURLが <code>null</code> の場合
     *             <p>
     * {@link ResultInfo#EXCEPTION_TYPE_FELICA} FeliCaサーバとのオンライン処理の開始が失敗した場合（詳細は下表を参照）
     *             <p>
     *             <table style="vertical-align: middle;" border="1" cellpadding="2" * cellspacing="2">
     *             <tbody>
     *             <tr>
     *             <th style="text-align: center;">種別</th> <th
     *             style="text-align: center;">タイプ</th>
     *             </tr>
     *             <tr>
     *             <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     *             <td>{@link FelicaException#TYPE_CURRENTLY_ONLINE}</td>
     *             </tr>
     *             <tr>
     *             <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     *             <td>{@link FelicaException#TYPE_FELICA_NOT_SET}</td>
     *             </tr>
     *             <tr>
     *             <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     *             <td>{@link FelicaException#TYPE_DEVICELIST_NOT_SET}</td>
     *             </tr>
     *             <tr>
     *             <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     *             <td>{@link FelicaException#TYPE_LISTENER_NOT_SET}</td>
     *             </tr>
     *             <tr>
     *             <td>{@link FelicaException#ID_ILLEGAL_STATE_ERROR}</td>
     *             <td>{@link FelicaException#TYPE_NOT_OPENED}</td>
     *             </tr>
     *             <tr>
     *             <td>{@link FelicaException#ID_UNKNOWN_ERROR}</td>
     *             <td>{@link FelicaException#TYPE_COMMUNICATION_START_FAILED}</td>
     *             </tr>
     *             </tbody>
     *             </table>
     */
    FelicaResultInfo start(String url, in DeviceList deviceList, IFSCEventListener fscEventListener, IMfiFelica felica);

    /**
     * FeliCaサーバとのオンライン処理を中断します。
     * <p>
     * <code>FSC</code> がオンライン処理中ではない場合、何も行いません。
     * <p>
     */
    void stop();

    /**
     * デバイス操作要求の結果が正常終了だった場合に通知します。
     * <p>
     * @param result 操作処理結果
     */
    void notifyResult(in byte[] result);

    /**
     * デバイス操作要求中にExceptionが発生した場合に通知します。
     * <p>
     * @param msg エラーメッセージ
     */
    void notifyError(in String msg );

    /* AGP2 2019/01/20 add START */
    FelicaResultInfo silentStartForMfiAdmin(in String accountIssuer, in String accountName, boolean login, int code, ISilentStartForMfiAdminEventCallback callback);
    FelicaResultInfo getLinkageDataList(int actionType, in String[] cid, ILinkageDataListEventCallback callback);
    FelicaResultInfo initialize(in String linkageData, IInitializedEventCallback callback);
    FelicaResultInfo getCardListV2(IPipeEventCallback callback);
    FelicaResultInfo getCardAdditionalInfoListV2(IPipeEventCallback callback);
    FelicaResultInfo silentStart(in String accountIssuer,in String accountName,int code, ISilentStartEventCallback callback);
    FelicaResultInfoInt checkChipFormatting();
    /* AGP2 2019/01/20 add END */
    /* AGP2 2019/03/20 add START */
    FelicaResultInfo issueCardV2(in String linkageData, ICardIssueV2EventCallback callback);
    FelicaResultInfo enableV2(in String cardInfoJson, ICardEnableV2EventCallback callback);
    FelicaResultInfo disableV2(in String cardInfoJson, ICardDisableV2EventCallback callback);
    FelicaResultInfo deleteV2(in String cardInfoJson, in String linkageData, ICardDeleteV2EventCallback callback);
    FelicaResultInfo issueCardWithOtp(in String linkageData, in String otp, ICardIssueV2EventCallback callback);
    FelicaResultInfo access(in String cardInfoJson, in String linkageData, ICardAccessEventCallback callback);
    FelicaResultInfo getCardInfoListWithSpStatus(in String serviceId, IPipeEventCallback callback);
    FelicaResultInfo getCardAdditionalInfoListWithCidList(in String[] cidList, IPipeEventCallback callback);
    FelicaResultInfo saveDelete(in String cardInfoJson, in String linkageData, ICardReissuableDeleteEventCallback callback);
    FelicaResultInfo selectWithCid(int systemCode, String cid);
    FelicaResultInfoStringArray getLocalCidList();
    FelicaResultInfoInt identifyService();
    /* AGP2 2019/03/20 add START */
    /* FY19-02 2019/12/10 add START */
    FelicaResultInfo silentStartForMfiAdminV2(in String accountIssuer, in String accountName, boolean login, int code, int layoutType, ISilentStartForMfiAdminEventCallback callback);
    FelicaResultInfoInt getUnsupportMfiService1CardPosition();
    FelicaResultInfo cancelMfiOffline();
    FelicaResultInfoStringArray getLocalCidListV2();
    FelicaResultInfo getCardListV3(IDataListEventCallback callback);
    FelicaResultInfo getCardInfoListWithSpStatusV3(in String serviceId, IDataListEventCallback callback);
    FelicaResultInfo getCardAdditionalInfoListV3(in String[] cidList, IDataListEventCallback callback);
    /* FY19-02 2019/12/10 add END */
    /* FY20-01 2020/04/17 add START */
    FelicaResultInfo provisionServerOperation(IServerOperationEventCallback callback);
    /* FY20-01 2020/04/17 add END */
    /* FY20-01 2020/06/19 add START */
    FelicaResultInfoStringArray getLocalPartialCardInfoList(in String serviceId);
    /* FY20-01 2020/06/19 add END */
    /* FY20-01_Step2 2020/09/30 add START */
    FelicaResultInfo existUnsupportMfiService1Card(in String serviceId, IUnsupportMfiService1CardExistEventCallback callback);
    FelicaResultInfo deleteUnsupportMfiService1Card(in String linkageData, IUnsupportMfiService1CardDeleteEventCallback callback);
    FelicaResultInfoInt existEmptySlot();
    FelicaResultInfo getCachedCardList(IDataListEventCallback callback);
    FelicaResultInfo enableCachedCard(in String cachedCardInfoJson, ICachedCardEnableEventCallback callback);
    /* FY20-01_Step2 2020/09/30 add END */
}
