package com.felicanetworks.mfw.i.fbl;

import com.felicanetworks.mfw.i.cmn.ArrayList;
import com.felicanetworks.mfw.i.cmn.NwConUtil;
import com.felicanetworks.mfw.i.cmn.NwConUtilListener;
import com.felicanetworks.mfw.i.cmn.RespData;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class BizNwConOptr implements NwConUtilListener {
    private static final String CONTENT_TYPE_COMMAND_DATA = "application/vnd.fn-command-data";
    private static final String CONTENT_TYPE_PERMIT_EXPIRE = "application/vnd.fn-revocation";
    private static final String CONTENT_TYPE_VERSION_UP = "application/vnd.fn-version";
    public static final String IF_AUTHENT = "I009";
    public static final String IF_COMMAND_DATA = "I007";
    public static final String IF_CONFIRM_PERMIT_EXPIRE = "I003";
    public static final String IF_CONFIRM_VERSIONUP = "I001";
    public static final String IF_PROC_RSLT_QUES = "I011";
    public static final String IF_RESOURCE_PROBLEM_LOG = "I005";
    private static final String URL_PRBLM_ANALYZE_LOG_SEND = "https://ots.fnrt.jp/~testuser2/site/command/debuglog.php";
    private static final String URL_VERUP_CONFIR_REQ = "https://ots.fnrt.jp/~testuser2/site/command/verup.php";
    private BizNwConOptrListener mListener;
    private RespData mRespData;
    private ArrayList mNwConOptrSettingList = new ArrayList();
    private ArrayList mTempNwConOptrSettingList = new ArrayList();

    public void startCommunication() {
        if (this.mNwConOptrSettingList.size() > 0) {
            this.mTempNwConOptrSettingList.addAllList(this.mNwConOptrSettingList);
            this.mNwConOptrSettingList.clear();
        }
        executeCommunication();
    }

    public void setRequestInfo(String str, String str2) {
        if (str.equals(IF_CONFIRM_VERSIONUP)) {
            setRequestInfo(str, str2, "https://ots.fnrt.jp/~testuser2/site/command/verup.php");
        } else if (str.equals(IF_RESOURCE_PROBLEM_LOG)) {
            setRequestInfo(str, str2, "https://ots.fnrt.jp/~testuser2/site/command/debuglog.php");
        }
    }

    public void setRequestInfo(String str, String str2, String str3) {
        NwConOptrSetting nwConOptrSetting = new NwConOptrSetting();
        nwConOptrSetting.setId(str);
        nwConOptrSetting.setData(str2);
        nwConOptrSetting.setUrl(str3);
        this.mNwConOptrSettingList.add(nwConOptrSetting);
    }

    public RespData getRespData() {
        return this.mRespData;
    }

    public void setListener(BizNwConOptrListener bizNwConOptrListener) {
        this.mListener = bizNwConOptrListener;
    }

    @Override // com.felicanetworks.mfw.i.cmn.NwConUtilListener
    public void webUtilExpired(RespData respData) {
        String id = ((NwConOptrSetting) this.mTempNwConOptrSettingList.get(0)).getId();
        this.mRespData = respData;
        if (this.mRespData.getStatusCode() == 200) {
            int iChkContentType = chkContentType(id, this.mRespData.getContentType());
            this.mTempNwConOptrSettingList.remove(0);
            if (iChkContentType == 0) {
                this.mListener.communicate(id, 0);
                if (this.mTempNwConOptrSettingList.size() < 1) {
                    this.mListener = null;
                    return;
                } else {
                    executeCommunication();
                    return;
                }
            }
            this.mListener.communicate(id, iChkContentType);
            this.mListener = null;
            return;
        }
        this.mTempNwConOptrSettingList.remove(0);
        if (id.equals(IF_CONFIRM_VERSIONUP)) {
            this.mListener.communicate(id, ErrMsgOptr.CA_ERR_HTTP_VERUP);
        } else if (id.equals(IF_CONFIRM_PERMIT_EXPIRE)) {
            this.mListener.communicate(id, ErrMsgOptr.CA_ERR_HTTP_CRL);
        } else if (id.equals(IF_RESOURCE_PROBLEM_LOG)) {
            this.mListener.communicate(id, ErrMsgOptr.CA_ERR_HTTP_PRBLMLOG);
        } else if (id.equals(IF_COMMAND_DATA)) {
            this.mListener.communicate(id, ErrMsgOptr.CA_ERR_HTTP_CMD);
        } else if (id.equals(IF_AUTHENT)) {
            this.mListener.communicate(id, ErrMsgOptr.CA_ERR_HTTP_AUTH);
        } else if (id.equals(IF_PROC_RSLT_QUES)) {
            this.mListener.communicate(id, ErrMsgOptr.CA_ERR_HTTP_INQUIRY);
        }
        this.mListener = null;
    }

    private int chkContentType(String str, String str2) {
        if (str.equals(IF_CONFIRM_VERSIONUP)) {
            if (str2.equals(CONTENT_TYPE_VERSION_UP)) {
                return 0;
            }
            return ErrMsgOptr.CA_ERR_CONTENTTYPE_VERUP;
        }
        if (str.equals(IF_CONFIRM_PERMIT_EXPIRE)) {
            if (str2.equals(CONTENT_TYPE_PERMIT_EXPIRE)) {
                return 0;
            }
            return ErrMsgOptr.CA_ERR_CONTENTTYPE_CRL;
        }
        if (!str.equals(IF_COMMAND_DATA) || str2.equals(CONTENT_TYPE_COMMAND_DATA)) {
            return 0;
        }
        return ErrMsgOptr.CA_ERR_CONTENTTYPE_CMD;
    }

    private void executeCommunication() {
        NwConOptrSetting nwConOptrSetting = (NwConOptrSetting) this.mTempNwConOptrSettingList.get(0);
        String id = nwConOptrSetting.getId();
        try {
            if (id.equals(IF_CONFIRM_VERSIONUP) || id.equals(IF_CONFIRM_PERMIT_EXPIRE)) {
                NwConUtil.get(nwConOptrSetting.getUrl(), nwConOptrSetting.getData(), this);
            } else if (id.equals(IF_RESOURCE_PROBLEM_LOG) || id.equals(IF_COMMAND_DATA) || id.equals(IF_AUTHENT) || id.equals(IF_PROC_RSLT_QUES)) {
                NwConUtil.post(nwConOptrSetting.getUrl(), nwConOptrSetting.getData(), this);
            }
        } catch (IOException e) {
            this.mTempNwConOptrSettingList.remove(0);
            if (NwConUtil.FAILED_ENCODE.equals(e.getMessage())) {
                if (id.equals(IF_CONFIRM_VERSIONUP)) {
                    this.mListener.communicate(id, ErrMsgOptr.CA_ERR_ENCODE_VERUP);
                } else if (id.equals(IF_CONFIRM_PERMIT_EXPIRE)) {
                    this.mListener.communicate(id, ErrMsgOptr.CA_ERR_ENCODE_CRL);
                } else if (id.equals(IF_RESOURCE_PROBLEM_LOG)) {
                    this.mListener.communicate(id, ErrMsgOptr.CA_ERR_ENCODE_PRBLMLOG);
                } else if (id.equals(IF_COMMAND_DATA)) {
                    this.mListener.communicate(id, ErrMsgOptr.CA_ERR_ENCODE_CMD);
                } else if (id.equals(IF_AUTHENT)) {
                    this.mListener.communicate(id, ErrMsgOptr.CA_ERR_ENCODE_AUTH);
                } else if (id.equals(IF_PROC_RSLT_QUES)) {
                    this.mListener.communicate(id, ErrMsgOptr.CA_ERR_ENCODE_INQUIRY);
                }
            } else if (id.equals(IF_CONFIRM_VERSIONUP)) {
                this.mListener.communicate(id, ErrMsgOptr.CA_ERR_HTTP_VERUP);
            } else if (id.equals(IF_CONFIRM_PERMIT_EXPIRE)) {
                this.mListener.communicate(id, ErrMsgOptr.CA_ERR_HTTP_CRL);
            } else if (id.equals(IF_RESOURCE_PROBLEM_LOG)) {
                this.mListener.communicate(id, ErrMsgOptr.CA_ERR_HTTP_PRBLMLOG);
            } else if (id.equals(IF_COMMAND_DATA)) {
                this.mListener.communicate(id, ErrMsgOptr.CA_ERR_HTTP_CMD);
            } else if (id.equals(IF_AUTHENT)) {
                this.mListener.communicate(id, ErrMsgOptr.CA_ERR_HTTP_AUTH);
            } else if (id.equals(IF_PROC_RSLT_QUES)) {
                this.mListener.communicate(id, ErrMsgOptr.CA_ERR_HTTP_INQUIRY);
            }
            this.mListener = null;
        }
    }
}
