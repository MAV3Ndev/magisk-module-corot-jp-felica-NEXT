package com.felicanetworks.mfw.i.fbl;

import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.mfw.i.cmn.ArrayList;
import com.felicanetworks.mfw.i.cmn.Asn1Object;
import com.felicanetworks.mfw.i.cmn.Asn1Parser;
import com.felicanetworks.mfw.i.cmn.Base64Util;
import com.felicanetworks.mfw.i.cmn.DateUtil;
import com.felicanetworks.mfw.i.cmn.DebugLogger;
import com.felicanetworks.mfw.i.cmn.ResUtil;
import com.felicanetworks.mfw.i.cmn.RespData;
import com.felicanetworks.mfw.i.cmn.StringUtil;
import com.felicanetworks.mfw.i.cmn.SysException;
import com.felicanetworks.mfw.i.cmn.VerifierUtil;
import java.io.File;
import java.util.Arrays;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class PermitOptr {
    private static final String PERMIT_EXPIRE_SUFFIX = "2.bin";
    private static final int SIZE_PERMIT_EXPIRE = 2711;
    private static Verifier sVerifier;
    private Permit mPermit;
    private PermitOptrListener mPermitListener;
    private boolean mStopped = false;
    private static StringBuffer sParameter = new StringBuffer();
    private static File sFile = null;

    public PermitOptr() {
        if (sVerifier == null) {
            sVerifier = new Verifier();
        }
        if (sFile == null) {
            sFile = new File(Property.sFileDir, PERMIT_EXPIRE_SUFFIX);
        }
    }

    private boolean isStopped() {
        return this.mStopped;
    }

    public void stopVerification() {
        this.mStopped = true;
    }

    public Permit getPermit() {
        return this.mPermit;
    }

    public void setListener(PermitOptrListener permitOptrListener) {
        if (permitOptrListener == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [listener = " + permitOptrListener + "]");
            throw new SysException((Class<?>) PermitOptr.class, "setListener", stringBuffer.toString());
        }
        this.mPermitListener = permitOptrListener;
    }

    public void startVerification(String str, Vector<byte[]> vector) throws Throwable {
        ResDataPermitRvction revocationResponse;
        LogMgr.log(6, "%s", "000");
        if (this.mPermitListener == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal state. Set listener first.");
            throw new SysException((Class<?>) PermitOptr.class, "startVerification", stringBuffer.toString());
        }
        if (str == null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Illegal argument.");
            stringBuffer2.append(" [permitString = " + str + "]");
            throw new SysException((Class<?>) PermitOptr.class, "startVerification", stringBuffer2.toString());
        }
        LogMgr.log(6, "%s", "001");
        if (vector == null || vector.isEmpty()) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Illegal argument.");
            stringBuffer3.append(" [signatures = " + vector + "]");
            throw new SysException((Class<?>) PermitOptr.class, "startVerification", stringBuffer3.toString());
        }
        try {
            if (isStopped()) {
                throw new PermitException(ErrMsgOptr.CA_STOP);
            }
            Permit permitCreatePermit = createPermit(str);
            verifyCaller(vector, permitCreatePermit);
            LogMgr.log(6, "%s", "002");
            verifyPeriod(permitCreatePermit);
            verifyParameters(permitCreatePermit);
            this.mPermit = permitCreatePermit;
            if (StringUtil.isEmpty(permitCreatePermit.getRvctionDistributionPoint())) {
                this.mPermitListener.ntfyVerificationResult(0, null);
                return;
            }
            if (isStopped()) {
                throw new PermitException(ErrMsgOptr.CA_STOP);
            }
            ResourceConnector resourceConnector = new ResourceConnector();
            DebugLogger.set("Start get local Revocation.");
            LogMgr.log(6, "%s", "003");
            ResDataPermitRvction revocationResource = resourceConnector.readRevocationResource(permitCreatePermit.getRvctionDistributionPoint());
            if (isStopped()) {
                throw new PermitException(ErrMsgOptr.CA_STOP);
            }
            String updateDate = revocationResource.getUpdateDate();
            long millis = updateDate == null ? 0L : DateUtil.toMillis(updateDate);
            if (isInvalidRevocation(revocationResource, permitCreatePermit)) {
                String rvctionDistributionPoint = permitCreatePermit.getRvctionDistributionPoint();
                DebugLogger.set("Start get online Revocation.");
                ResDataPermitRvction revocationResponse2 = parseRevocationResponse(getRevocationResponse(revocationResource, rvctionDistributionPoint, millis));
                String currentJSTString = DateUtil.getCurrentJSTString();
                LogMgr.log(6, "%s", "004");
                revocationResource.setUpdateDate(currentJSTString);
                revocationResource.setUsedDate(currentJSTString);
                revocationResource.setRvctionPoint(rvctionDistributionPoint);
                revocationResource.setRvctionPointSize(rvctionDistributionPoint.length());
                revocationResource.setOfflineRvctionNumLimit(revocationResponse2.getOfflineRvctionNumLimit());
                revocationResource.setOfflineRvctionTerm(revocationResponse2.getOfflineRvctionTerm());
                revocationResource.setSerialNumCount(revocationResponse2.getSerialNumCount());
                revocationResource.setRvctionList(revocationResponse2.getRvctionList());
                revocationResource.setOfflineRvctionNum(1);
                if (isStopped()) {
                    throw new PermitException(ErrMsgOptr.CA_STOP);
                }
                resourceConnector.updateRevocationResource(revocationResource);
            } else if (isOverLimitRevocation(revocationResource, millis)) {
                LogMgr.log(6, "%s", "005");
                String rvctionDistributionPoint2 = permitCreatePermit.getRvctionDistributionPoint();
                DebugLogger.set("Start get online Revocation.");
                try {
                    revocationResponse = parseRevocationResponse(getRevocationResponse(revocationResource, rvctionDistributionPoint2, millis));
                } catch (PermitException unused) {
                    int offlineRvctionNum = revocationResource.getOfflineRvctionNum() + 1;
                    if (offlineRvctionNum > 99999999) {
                        offlineRvctionNum = 0;
                    }
                    revocationResource.setOfflineRvctionNum(offlineRvctionNum);
                    if (isStopped()) {
                        throw new PermitException(ErrMsgOptr.CA_STOP);
                    }
                    resourceConnector.updateOfflineRevocationResource(revocationResource);
                    revocationResponse = null;
                }
                if (revocationResponse != null) {
                    String currentJSTString2 = DateUtil.getCurrentJSTString();
                    revocationResource.setUpdateDate(currentJSTString2);
                    revocationResource.setUsedDate(currentJSTString2);
                    revocationResource.setRvctionPoint(rvctionDistributionPoint2);
                    revocationResource.setRvctionPointSize(rvctionDistributionPoint2.length());
                    revocationResource.setOfflineRvctionNumLimit(revocationResponse.getOfflineRvctionNumLimit());
                    revocationResource.setOfflineRvctionTerm(revocationResponse.getOfflineRvctionTerm());
                    revocationResource.setSerialNumCount(revocationResponse.getSerialNumCount());
                    revocationResource.setRvctionList(revocationResponse.getRvctionList());
                    revocationResource.setOfflineRvctionNum(1);
                    if (isStopped()) {
                        throw new PermitException(ErrMsgOptr.CA_STOP);
                    }
                    resourceConnector.updateRevocationResource(revocationResource);
                }
            } else {
                LogMgr.log(6, "%s", "006");
                int offlineRvctionNum2 = revocationResource.getOfflineRvctionNum() + 1;
                if (offlineRvctionNum2 > 99999999) {
                    offlineRvctionNum2 = 0;
                }
                revocationResource.setOfflineRvctionNum(offlineRvctionNum2);
                revocationResource.setUsedDate(DateUtil.getCurrentJSTString());
                if (isStopped()) {
                    throw new PermitException(ErrMsgOptr.CA_STOP);
                }
                resourceConnector.updateOfflineRevocationResource(revocationResource);
            }
            DebugLogger.set("End Revocation");
            LogMgr.log(6, "%s", "007");
            if (!isValidPermit(permitCreatePermit, revocationResource)) {
                throw new PermitException(ErrMsgOptr.CA_ERR_CRL_REVOKED_PERMIT);
            }
            this.mPermitListener.ntfyVerificationResult(0, null);
            LogMgr.log(6, "%s", "999");
        } catch (PermitException e) {
            this.mPermitListener.ntfyVerificationResult(e.getErrorCode(), null);
            LogMgr.log(1, "%s", "998");
        }
    }

    private Permit createPermit(String str) throws PermitException {
        Asn1Object permitSequence = getPermitSequence(str);
        validatePermit(permitSequence);
        verifyPermit(permitSequence);
        Permit permit = parsePermit(permitSequence);
        validateFormat(permit);
        return permit;
    }

    private Asn1Object getPermitSequence(String str) throws PermitException {
        try {
            try {
                Asn1Object asn1Object = Asn1Parser.parse(Base64Util.decode(str));
                if (asn1Object == null) {
                    throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_PURSE);
                }
                return asn1Object.getChildren()[0];
            } catch (Exception unused) {
                throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_PURSE);
            }
        } catch (Exception unused2) {
            throw new PermitException(1000);
        }
    }

    private void validatePermit(Asn1Object asn1Object) throws PermitException {
        if (asn1Object.getTagType() != 48) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_INVALID_TYPE_ROOT);
        }
        Asn1Object[] children = asn1Object.getChildren();
        if (children == null || children.length != 2) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_INCORRECT_CHILDREN_IN_ROOT);
        }
        Asn1Object asn1Object2 = children[0];
        Asn1Object asn1Object3 = children[1];
        if (asn1Object2.getTagType() != 48 || asn1Object3.getTagType() != 3) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_INVALID_TYPE_CHILDREN_IN_ROOT);
        }
        Asn1Object[] children2 = asn1Object2.getChildren();
        if (children2 == null || children2.length == 0) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_INCORRECT_CHILDREN_IN_PERMIT_PARAM);
        }
        if (children2.length < 5) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_TOOLESS_CHILDREN_IN_PERMIT_PARAM);
        }
        validateVersion(children2[0]);
        validateIssuerAndKey(children2[2], children2[4]);
    }

    private void validateVersion(Asn1Object asn1Object) throws PermitException {
        if (asn1Object.getTagType() != 22) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_INVALID_TYPE_VER);
        }
        if (!isValidPermitVersion(asn1Object.getData())) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_UNKNOWN_VER);
        }
    }

    private boolean isValidPermitVersion(byte[] bArr) {
        return new String(bArr).equals("0001");
    }

    private void validateIssuerAndKey(Asn1Object asn1Object, Asn1Object asn1Object2) throws PermitException {
        if (asn1Object.getTagType() != 22) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_ISSUERID);
        }
        if (asn1Object.getData().length != 6 || !StringUtil.isDecOrAlpha(new String(asn1Object.getData()))) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_LENGTH_ISSUERID);
        }
        if (asn1Object2.getTagType() != 22) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_KEYID);
        }
        if (asn1Object2.getData().length != 4 || !StringUtil.isHexString(new String(asn1Object2.getData()))) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_LENGTH_KEYID);
        }
    }

    private void verifyPermit(Asn1Object asn1Object) throws PermitException {
        Asn1Object asn1Object2 = asn1Object.getChildren()[0];
        byte[] publicKey = getPublicKey(new String(asn1Object2.getChildren()[2].getData()), new String(asn1Object2.getChildren()[4].getData()));
        byte[] bArrCreateSignatur = createSignatur(asn1Object.getChildren()[1]);
        try {
            if (!sVerifier.verify(createSignatureTarget(asn1Object2), bArrCreateSignatur, publicKey)) {
                throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_NOT_VERIFIED);
            }
        } catch (Exception unused) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_VERIFY_FAILED);
        }
    }

    private byte[] getPublicKey(String str, String str2) throws PermitException {
        try {
            byte[] publicKey = new Verifier().getPublicKey(str, str2);
            if (publicKey != null) {
                return publicKey;
            }
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_NOTFOUND_PUBLICKEY);
        } catch (Exception unused) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_VERIFY_FAILED);
        }
    }

    private byte[] createSignatureTarget(Asn1Object asn1Object) {
        byte[] tagAndLengthData = asn1Object.getTagAndLengthData();
        byte[] data = asn1Object.getData();
        byte[] bArr = new byte[tagAndLengthData.length + data.length];
        System.arraycopy(tagAndLengthData, 0, bArr, 0, tagAndLengthData.length);
        System.arraycopy(data, 0, bArr, tagAndLengthData.length, data.length);
        return bArr;
    }

    private byte[] createSignatur(Asn1Object asn1Object) {
        byte[] data = asn1Object.getData();
        if (data.length == 0 || data[0] != 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[data.length - 1];
        System.arraycopy(data, 1, bArr, 0, data.length - 1);
        return bArr;
    }

    private Permit parsePermit(Asn1Object asn1Object) throws PermitException {
        Asn1Object[] children = asn1Object.getChildren();
        Asn1Object asn1Object2 = children[0];
        Asn1Object asn1Object3 = children[1];
        Asn1Object[] children2 = asn1Object2.getChildren();
        int length = children2.length;
        if (length != 8 && length != 9) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_PERMIT_PARAM);
        }
        Asn1Object asn1Object4 = children2[0];
        Asn1Object asn1Object5 = children2[1];
        Asn1Object asn1Object6 = children2[2];
        Asn1Object asn1Object7 = children2[3];
        Asn1Object asn1Object8 = children2[4];
        Asn1Object asn1Object9 = children2[5];
        Asn1Object asn1Object10 = children2[6];
        Asn1Object asn1Object11 = children2[7];
        if (asn1Object5.getTagType() != 22) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_PERMIT_TYPE);
        }
        if (asn1Object7.getTagType() != 22) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_SERIAL_NO);
        }
        if (asn1Object9.getTagType() != 48) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_VALIDITY);
        }
        Asn1Object[] children3 = asn1Object9.getChildren();
        if (asn1Object9.getChildren() == null || asn1Object9.getChildren().length != 2) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_VALIDITY);
        }
        Asn1Object asn1Object12 = children3[0];
        if (asn1Object12.getTagType() != 24) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_VALIDITY_FROM);
        }
        Asn1Object asn1Object13 = children3[1];
        if (asn1Object13.getTagType() != 24) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_VALIDITY_TO);
        }
        if (asn1Object10.getTagType() != 22) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_SERVICEID);
        }
        if (asn1Object11.getTagType() != 22) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_DISTRIBUTION);
        }
        Permit permit = new Permit();
        permit.setVersion(new String(asn1Object4.getData()));
        permit.setPermitType(new String(asn1Object5.getData()));
        permit.setPermitIssuerId(new String(asn1Object6.getData()));
        permit.setSerialNum(new String(asn1Object7.getData()));
        permit.setKeyId(new String(asn1Object8.getData()));
        permit.setNotBefore(new String(asn1Object12.getData()));
        permit.setNotAfter(new String(asn1Object13.getData()));
        permit.setServiceId(new String(asn1Object10.getData()));
        permit.setRvctionDistributionPoint(new String(asn1Object11.getData()));
        permit.setSignature(asn1Object3.getData());
        if (length == 9) {
            Asn1Object asn1Object14 = children2[8];
            if (asn1Object14.getTagType() != 48) {
                throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_EXTENSION);
            }
            Asn1Object[] children4 = asn1Object14.getChildren();
            if (children4 == null || children4.length == 0) {
                throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_EXTENSION);
            }
            for (Asn1Object asn1Object15 : children4) {
                permit.addExtension(parseExtensionData(asn1Object15));
            }
        }
        return permit;
    }

    private ExtensionParameter parseExtensionData(Asn1Object asn1Object) throws PermitException {
        if (asn1Object.getTagType() != 48) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_EXTDATA);
        }
        Asn1Object[] children = asn1Object.getChildren();
        if (children == null || children.length != 2) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_EXTDATA);
        }
        Asn1Object asn1Object2 = children[0];
        if (asn1Object2.getTagType() != 22) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_EXTID);
        }
        Asn1Object asn1Object3 = children[1];
        if (asn1Object3.getTagType() != 4) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_EXTPARAM);
        }
        Asn1Object asn1Object4 = Asn1Parser.parse(asn1Object3.getData());
        if (asn1Object4 == null) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_EXTPARAM);
        }
        Asn1Object asn1Object5 = asn1Object4.getChildren()[0];
        String str = new String(asn1Object2.getData());
        if (str.equals("0001")) {
            return parseChipIssuer(asn1Object5);
        }
        if (str.equals(ExtensionParameter.FELICA_ACCESS)) {
            return parseFelicaAccess(asn1Object5);
        }
        if (str.equals(ExtensionParameter.BROWSER_FELICA)) {
            return parseBrowserFelica(asn1Object5);
        }
        if (str.equals(ExtensionParameter.OPEN_PLATFORM)) {
            return parseOpenPlatformApp(asn1Object5);
        }
        throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_UNKNOWN_EXTID);
    }

    private ExtensionParameter parseChipIssuer(Asn1Object asn1Object) throws PermitException {
        if (asn1Object.getTagType() != 48) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_CHIPISSUER);
        }
        Asn1Object[] children = asn1Object.getChildren();
        if (children == null || children.length != 1) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_CHIPISSUER);
        }
        Asn1Object asn1Object2 = children[0];
        if (asn1Object2.getTagType() != 22) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_CHIPISSUER_NAME);
        }
        ChipIssuerInfo chipIssuerInfo = new ChipIssuerInfo();
        chipIssuerInfo.setChipIssuerCode(new String(asn1Object2.getData()));
        return chipIssuerInfo;
    }

    private ExtensionParameter parseFelicaAccess(Asn1Object asn1Object) throws PermitException {
        if (asn1Object.getTagType() != 48) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_FELICA_ACCESS);
        }
        Asn1Object[] children = asn1Object.getChildren();
        if (children == null || (children.length != 2 && children.length != 3)) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_FELICA_ACCESS);
        }
        Asn1Object asn1Object2 = children[0];
        Asn1Object asn1Object3 = children[1];
        if (asn1Object2.getTagType() != 4 || asn1Object3.getTagType() != 4) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_COMMAND_CATEGORY);
        }
        FeliCaAccessData feliCaAccessData = new FeliCaAccessData();
        feliCaAccessData.setPrivilegedCommandCategory(asn1Object2.getData());
        feliCaAccessData.setOrdinaryCommandCategory(asn1Object3.getData());
        if (children.length == 3) {
            Asn1Object asn1Object4 = children[2];
            if (asn1Object4.getTagType() != 48) {
                throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_OFFLINE_ACCESS);
            }
            Asn1Object[] children2 = asn1Object4.getChildren();
            if (children2 == null || children2.length == 0) {
                throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_OFFLINE_ACCESS);
            }
            for (Asn1Object asn1Object5 : children2) {
                feliCaAccessData.addOfflineAccessTarget(parseOfflineAccessTarget(asn1Object5));
            }
        }
        return feliCaAccessData;
    }

    private OfflineAccessTarget parseOfflineAccessTarget(Asn1Object asn1Object) throws PermitException {
        if (asn1Object.getTagType() != 48) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_OFFLINE_TARGET);
        }
        Asn1Object[] children = asn1Object.getChildren();
        if (children == null || children.length != 2) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_OFFLINE_TARGET);
        }
        Asn1Object asn1Object2 = children[0];
        Asn1Object asn1Object3 = children[1];
        if (asn1Object2.getTagType() != 22 || asn1Object3.getTagType() != 48) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_SYSTEM_NODES);
        }
        Asn1Object[] children2 = asn1Object3.getChildren();
        if (children2 == null || children2.length == 0) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_NODES);
        }
        OfflineAccessTarget offlineAccessTarget = new OfflineAccessTarget();
        offlineAccessTarget.setSystemCode(new String(asn1Object2.getData()));
        for (Asn1Object asn1Object4 : children2) {
            offlineAccessTarget.addNodeCodeRange(parseNodeCodeRange(asn1Object4));
        }
        return offlineAccessTarget;
    }

    private NodeCodeRange parseNodeCodeRange(Asn1Object asn1Object) throws PermitException {
        if (asn1Object.getTagType() != 48) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_NODE_RANGE);
        }
        Asn1Object[] children = asn1Object.getChildren();
        if (children == null || children.length != 2) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_NODE_RANGE);
        }
        Asn1Object asn1Object2 = children[0];
        Asn1Object asn1Object3 = children[1];
        if (asn1Object2.getTagType() != 22 || asn1Object3.getTagType() != 22) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_NODE);
        }
        NodeCodeRange nodeCodeRange = new NodeCodeRange();
        nodeCodeRange.setLowerNode(new String(asn1Object2.getData()));
        nodeCodeRange.setUpperNode(new String(asn1Object3.getData()));
        return nodeCodeRange;
    }

    private ExtensionParameter parseBrowserFelica(Asn1Object asn1Object) throws PermitException {
        if (asn1Object.getTagType() != 48) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_BFELICA);
        }
        Asn1Object[] children = asn1Object.getChildren();
        if (children == null || children.length != 1) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_BFELICA);
        }
        Asn1Object asn1Object2 = children[0];
        if (asn1Object2.getTagType() != 22) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_URL_LIMITATION);
        }
        BrowserFelica browserFelica = new BrowserFelica();
        browserFelica.setAllowUrl(new String(asn1Object2.getData()));
        return browserFelica;
    }

    private ExtensionParameter parseOpenPlatformApp(Asn1Object asn1Object) throws PermitException {
        if (asn1Object.getTagType() != 48) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_OPFAPP);
        }
        Asn1Object[] children = asn1Object.getChildren();
        if (children == null || children.length != 1) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INCORRECT_CHILDREN_IN_OPFAPP);
        }
        Asn1Object asn1Object2 = children[0];
        if (asn1Object2.getTagType() != 4) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_TYPE_APP_SIGNER);
        }
        OpenPlatformApp openPlatformApp = new OpenPlatformApp();
        openPlatformApp.setHash(asn1Object2.getData());
        return openPlatformApp;
    }

    private void validateFormat(Permit permit) throws PermitException {
        if (permit.getPermitType().length() != 2 || !StringUtil.isDecString(permit.getPermitType())) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_PERMIT_TYPE);
        }
        if (permit.getSerialNum().length() != 12 || !StringUtil.isDecOrAlpha(permit.getSerialNum())) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_SERIAL_NO);
        }
        if (permit.getNotBefore().length() != 19 || !DateUtil.isValidDateFormat(permit.getNotBefore())) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_VALIDITY_FROM);
        }
        if (permit.getNotAfter().length() != 19 || !DateUtil.isValidDateFormat(permit.getNotAfter())) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_VALIDITY_TO);
        }
        if (permit.getServiceId().length() != 8 || !StringUtil.isDecOrAlpha(permit.getServiceId())) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_SERVICEID);
        }
        String rvctionDistributionPoint = permit.getRvctionDistributionPoint();
        if (!StringUtil.isEmpty(rvctionDistributionPoint) && rvctionDistributionPoint.length() > 255) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_DISTRIBUTION);
        }
        for (int i = 0; i < permit.extensionSize(); i++) {
            validateExtensionFormat(permit.getExtension(i));
        }
    }

    private void validateExtensionFormat(ExtensionParameter extensionParameter) throws PermitException {
        if (extensionParameter.getId().equals("0001")) {
            String chipIssuerCode = ((ChipIssuerInfo) extensionParameter).getChipIssuerCode();
            if (chipIssuerCode.length() != 6 || !StringUtil.isHexString(chipIssuerCode)) {
                throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_CHIPISSUER);
            }
            return;
        }
        if (extensionParameter.getId().equals(ExtensionParameter.FELICA_ACCESS)) {
            validateFelicaAccessFormat((FeliCaAccessData) extensionParameter);
            return;
        }
        if (extensionParameter.getId().equals(ExtensionParameter.BROWSER_FELICA)) {
            if (!StringUtil.isValidUrlCharacter(((BrowserFelica) extensionParameter).getAllowUrl())) {
                throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_URL);
            }
        } else if (extensionParameter.getId().equals(ExtensionParameter.OPEN_PLATFORM) && ((OpenPlatformApp) extensionParameter).getHash().length != 32) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_APP_SIGNER);
        }
    }

    private void validateFelicaAccessFormat(FeliCaAccessData feliCaAccessData) throws PermitException {
        if (feliCaAccessData.getOrdinaryCommandCategory().length != 2) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_GENERAL_COMMAND_CATEGORY);
        }
        if (feliCaAccessData.getPrivilegedCommandCategory().length != 2) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_PRIVILEGE_COMMAND_CATEGORY);
        }
        for (int i = 0; i < feliCaAccessData.offlineAccessTargetSize(); i++) {
            validateAccessTargetFormat(feliCaAccessData.getOfflineAccessTarget(i));
        }
    }

    private void validateAccessTargetFormat(OfflineAccessTarget offlineAccessTarget) throws PermitException {
        if (offlineAccessTarget.getSystemCode().length() != 4 || !StringUtil.isHexString(offlineAccessTarget.getSystemCode())) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_OFFLINE_ACCESS_SYSTEM_CODE);
        }
        for (int i = 0; i < offlineAccessTarget.nodeCodeRangeSize(); i++) {
            NodeCodeRange nodeCodeRange = offlineAccessTarget.getNodeCodeRange(i);
            if (nodeCodeRange.getLowerNode().length() != 8 || !StringUtil.isHexString(nodeCodeRange.getLowerNode())) {
                throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_OFFLINE_ACCESS_LOWER_NODE);
            }
            if (nodeCodeRange.getUpperNode().length() != 8 || !StringUtil.isHexString(nodeCodeRange.getUpperNode())) {
                throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_OFFLINE_ACCESS_UPPER_NODE);
            }
        }
    }

    private void verifyCaller(Vector<byte[]> vector, Permit permit) throws PermitException {
        LogMgr.log(4, "%s : signatures = %s, permit = %s", "000", vector, permit);
        if (vector == null || vector.isEmpty() || permit == null) {
            LogMgr.log(1, "800 signatures = %s, permit = %s", vector, permit);
            throw new IllegalArgumentException();
        }
        if (permit.extensionSize() == 0) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_NOTFOUND_EXTENTION);
        }
        OpenPlatformApp openPlatformApp = (OpenPlatformApp) permit.getExtension(ExtensionParameter.OPEN_PLATFORM);
        if (openPlatformApp == null) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_NOTFOUND_SIGNER_INFO);
        }
        byte[] hash = openPlatformApp.getHash();
        for (int i = 0; i < vector.size(); i++) {
            if (Arrays.equals(vector.get(i), hash)) {
                LogMgr.log(4, "%s, (found at signatures[%d])", "999", Integer.valueOf(i));
                return;
            }
        }
        LogMgr.log(1, "801 signatures ummatch");
        throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_IMPROPER_USER);
    }

    private void verifyPeriod(Permit permit) throws PermitException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long millisWithTZD = DateUtil.toMillisWithTZD(permit.getNotBefore());
        long millisWithTZD2 = DateUtil.toMillisWithTZD(permit.getNotAfter());
        if (jCurrentTimeMillis < millisWithTZD || millisWithTZD2 < jCurrentTimeMillis) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_OUT_OF_VALIDITY);
        }
    }

    private void verifyParameters(Permit permit) throws PermitException {
        if (permit.extensionSize() != 3 || permit.getExtension("0001") == null || permit.getExtension(ExtensionParameter.FELICA_ACCESS) == null || permit.getExtension(ExtensionParameter.OPEN_PLATFORM) == null) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT0001_INVALID_COMBINATION_EXTDATA);
        }
        if (!((ChipIssuerInfo) permit.getExtension("0001")).getChipIssuerCode().equals(Property.sChipIssuerId)) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_UNKNOWN_CHIPISSUER_CODE);
        }
        if (!permit.getPermitType().equals("02")) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_UNKNOWN_PERMIT_TYPE);
        }
        String rvctionDistributionPoint = permit.getRvctionDistributionPoint();
        if (!StringUtil.isEmpty(rvctionDistributionPoint) && !StringUtil.isValidURL(rvctionDistributionPoint)) {
            throw new PermitException(ErrMsgOptr.CA_ERR_PERMIT_INVALID_FORMAT_DISTRIBUTION);
        }
    }

    private boolean isInvalidRevocation(ResDataPermitRvction resDataPermitRvction, Permit permit) {
        String rvctionPoint;
        if (resDataPermitRvction.getUpdateDate() != null && (rvctionPoint = resDataPermitRvction.getRvctionPoint()) != null && rvctionPoint.equals(permit.getRvctionDistributionPoint())) {
            int offlineRvctionNum = resDataPermitRvction.getOfflineRvctionNum();
            int offlineRvctionNumLimit = resDataPermitRvction.getOfflineRvctionNumLimit();
            if (offlineRvctionNum != -1 && offlineRvctionNumLimit != -1 && offlineRvctionNum != 0 && offlineRvctionNum <= offlineRvctionNumLimit) {
                return false;
            }
        }
        return true;
    }

    private boolean isOverLimitRevocation(ResDataPermitRvction resDataPermitRvction, long j) {
        return System.currentTimeMillis() > j + (((long) resDataPermitRvction.getOfflineRvctionTerm()) * 60000);
    }

    private boolean isValidPermit(Permit permit, ResDataPermitRvction resDataPermitRvction) {
        String serialNum = permit.getSerialNum();
        String[] rvctionList = resDataPermitRvction.getRvctionList();
        if (rvctionList == null) {
            return true;
        }
        for (String str : rvctionList) {
            if (str.equals(serialNum)) {
                return false;
            }
        }
        return true;
    }

    private RespData getRevocationResponse(ResDataPermitRvction resDataPermitRvction, String str, long j) throws PermitException {
        sParameter.setLength(0);
        sParameter.append("ai=0201");
        sParameter.append("&av=" + Property.sApplicationVersion);
        if (resDataPermitRvction.getUpdateDate() == null || resDataPermitRvction.getUpdateDate().equals(Property.URL_VERUP_SITE)) {
            sParameter.append("&ct=0");
            sParameter.append("&mct=0");
            sParameter.append("&il=0");
            sParameter.append("&mil=0");
        } else {
            sParameter.append("&ct=" + String.valueOf(resDataPermitRvction.getOfflineRvctionNum()));
            sParameter.append("&mct=" + String.valueOf(resDataPermitRvction.getOfflineRvctionNumLimit()));
            long jCurrentTimeMillis = (System.currentTimeMillis() - j) / 60000;
            long j2 = 0;
            if (jCurrentTimeMillis >= 0 && jCurrentTimeMillis <= 99999999) {
                j2 = jCurrentTimeMillis;
            }
            sParameter.append("&il=" + String.valueOf(j2));
            sParameter.append("&mil=" + String.valueOf(resDataPermitRvction.getOfflineRvctionTerm()));
        }
        return new NetworkConnector().connect(BizNwConOptr.IF_CONFIRM_PERMIT_EXPIRE, str, sParameter.toString());
    }

    private ResDataPermitRvction parseRevocationResponse(RespData respData) throws Throwable {
        String[] serialNumberList;
        ArrayList arrayListCreateLineList = StringUtil.createLineList(respData.getMessageBody());
        int size = arrayListCreateLineList.size();
        if (size < 3) {
            throw new PermitException(ErrMsgOptr.CA_ERR_CRL_TOOLESS_LINES);
        }
        int revocationLimit = parseRevocationLimit((String) arrayListCreateLineList.get(0));
        int revocationTerm = parseRevocationTerm((String) arrayListCreateLineList.get(1));
        int serialNumberCount = parseSerialNumberCount((String) arrayListCreateLineList.get(2));
        if (size == 4) {
            if (StringUtil.isEmpty((String) arrayListCreateLineList.get(3))) {
                throw new PermitException(ErrMsgOptr.CA_ERR_CRL_INVALID_LINES);
            }
            throw new PermitException(ErrMsgOptr.CA_ERR_CRL_NOTFOUND_SEPARATE_LINE);
        }
        if (size > 4) {
            for (int i = 4; i < arrayListCreateLineList.size(); i++) {
                if (StringUtil.isEmpty((String) arrayListCreateLineList.get(i))) {
                    throw new PermitException(ErrMsgOptr.CA_ERR_CRL_INVALID_LINES);
                }
            }
            if (!StringUtil.isEmpty((String) arrayListCreateLineList.get(3))) {
                throw new PermitException(ErrMsgOptr.CA_ERR_CRL_NOTFOUND_SEPARATE_LINE);
            }
            serialNumberList = parseSerialNumberList(arrayListCreateLineList);
            if (serialNumberCount != serialNumberList.length) {
                throw new PermitException(ErrMsgOptr.CA_ERR_CRL_INCORRECT_SERIAL_NO_LINE);
            }
        } else {
            if (serialNumberCount != 0) {
                throw new PermitException(ErrMsgOptr.CA_ERR_CRL_INCORRECT_SERIAL_NO_LINE);
            }
            serialNumberList = new String[0];
        }
        ResDataPermitRvction resDataPermitRvction = new ResDataPermitRvction();
        resDataPermitRvction.setOfflineRvctionNumLimit(revocationLimit);
        resDataPermitRvction.setOfflineRvctionTerm(revocationTerm);
        resDataPermitRvction.setSerialNumCount(serialNumberCount);
        resDataPermitRvction.setRvctionList(serialNumberList);
        return resDataPermitRvction;
    }

    private int parseRevocationLimit(String str) throws PermitException {
        if (str.length() < 1 || 8 < str.length()) {
            throw new PermitException(ErrMsgOptr.CA_ERR_CRL_INVALID_LENGTH_OFFLINE_JUDGE_NUM);
        }
        if (!StringUtil.isDecString(str)) {
            throw new PermitException(ErrMsgOptr.CA_ERR_CRL_INVALID_CHARA_OFFLINE_JUDGE_NUM);
        }
        int i = Integer.parseInt(str);
        if (i <= 99999999) {
            return i;
        }
        throw new PermitException(ErrMsgOptr.CA_ERR_CRL_ILLEGAL_OFFLINE_JUDGE_NUM);
    }

    private int parseRevocationTerm(String str) throws PermitException {
        if (str.length() < 1 || 8 < str.length()) {
            throw new PermitException(ErrMsgOptr.CA_ERR_CRL_INVALID_LENGTH_OFFLINE_JUDGE_TERM);
        }
        if (!StringUtil.isDecString(str)) {
            throw new PermitException(ErrMsgOptr.CA_ERR_CRL_INVALID_CHARA_OFFLINE_JUDGE_TERM);
        }
        int i = Integer.parseInt(str);
        if (i <= 43200) {
            return i;
        }
        throw new PermitException(ErrMsgOptr.CA_ERR_CRL_ILLEGAL_OFFLINE_JUDGE_TERM);
    }

    private int parseSerialNumberCount(String str) throws PermitException {
        if (str.length() < 1 || 4 < str.length()) {
            throw new PermitException(ErrMsgOptr.CA_ERR_CRL_INVALID_LENGTH_SERIAL_NO_NUM);
        }
        if (!StringUtil.isDecString(str)) {
            throw new PermitException(ErrMsgOptr.CA_ERR_CRL_INVALID_CHARA_SERIAL_NO_NUM);
        }
        int i = Integer.parseInt(str);
        if (i <= 200) {
            return i;
        }
        throw new PermitException(ErrMsgOptr.CA_ERR_CRL_ILLEGAL_SERIAL_NO_NUM);
    }

    private String[] parseSerialNumberList(ArrayList arrayList) throws PermitException {
        String[] strArr = new String[arrayList.size() - 4];
        for (int i = 0; i < arrayList.size() - 4; i++) {
            String str = (String) arrayList.get(i + 4);
            if (str.length() != 12) {
                throw new PermitException(ErrMsgOptr.CA_ERR_CRL_INVALID_LENGTH_REVOKED_SERIAL_NO_LINE);
            }
            if (!StringUtil.isDecOrAlpha(str)) {
                throw new PermitException(ErrMsgOptr.CA_ERR_CRL_INVALID_CHARA_REVOKED_SERIAL_NO_LINE);
            }
            strArr[i] = str;
        }
        return strArr;
    }

    private class PermitException extends Throwable {
        private static final long serialVersionUID = -3636880792132387853L;
        private int mErrorCode;

        public PermitException(int i) {
            this.mErrorCode = i;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }
    }

    private class NetworkConnector {
        private BizNwConOptrListener mNetworkListener;
        private int mSituationCode;

        private NetworkConnector() {
            this.mNetworkListener = new BizNwConOptrListener() { // from class: com.felicanetworks.mfw.i.fbl.PermitOptr.NetworkConnector.1
                @Override // com.felicanetworks.mfw.i.fbl.BizNwConOptrListener
                public void communicate(String str, int i) {
                    NetworkConnector.this.mSituationCode = i;
                }
            };
        }

        public RespData connect(String str, String str2, String str3) throws PermitException {
            BizNwConOptr bizNwConOptr = new BizNwConOptr();
            bizNwConOptr.setListener(this.mNetworkListener);
            bizNwConOptr.setRequestInfo(str, str3, str2);
            bizNwConOptr.startCommunication();
            int situationCode = getSituationCode();
            if (situationCode == 0) {
                return bizNwConOptr.getRespData();
            }
            throw PermitOptr.this.new PermitException(situationCode);
        }

        private int getSituationCode() {
            return this.mSituationCode;
        }
    }

    private class ResourceConnector {
        private BizResOptrListener mResourceListener;
        private int mSituationCode;
        private String[] mUrlList;

        private ResourceConnector() {
            this.mResourceListener = new BizResOptrListener() { // from class: com.felicanetworks.mfw.i.fbl.PermitOptr.ResourceConnector.1
                @Override // com.felicanetworks.mfw.i.fbl.BizResOptrListener
                public void write(String str, int i) {
                    ResourceConnector.this.mSituationCode = i;
                }

                @Override // com.felicanetworks.mfw.i.fbl.BizResOptrListener
                public void read(String str, int i) {
                    ResourceConnector.this.mSituationCode = i;
                }
            };
            this.mUrlList = new String[10];
        }

        public ResDataPermitRvction readRevocationResource(String str) {
            ResData resDataPermitRvction;
            int i;
            int i2;
            int i3;
            BizResOptr bizResOptr = BizResOptr.getInstance();
            int i4 = 10;
            try {
                if (!checkFileSize(PermitOptr.sFile, 27110L)) {
                    LogMgr.log(2, "700 checkFileSize error. filesize = " + PermitOptr.sFile.length());
                    if (PermitOptr.sFile.exists()) {
                        deleteFile(PermitOptr.sFile);
                    }
                    i4 = 1;
                }
                synchronized (bizResOptr) {
                    i = -1;
                    long j = 999999999999L;
                    i2 = -1;
                    resDataPermitRvction = null;
                    int i5 = 0;
                    i3 = 0;
                    while (true) {
                        if (i5 >= i4) {
                            break;
                        }
                        bizResOptr.setListener(this.mResourceListener);
                        bizResOptr.setReadInfo(BizResOptr.RESOURCE_PERMIT_EXPIRE, i5 * PermitOptr.SIZE_PERMIT_EXPIRE);
                        bizResOptr.startReadWrite();
                        String rvctionUrl = bizResOptr.getRvctionUrl();
                        if (isUrlExist(i5, rvctionUrl)) {
                            LogMgr.log(2, "701 isUrlExist");
                            deleteFile(PermitOptr.sFile);
                            bizResOptr.setListener(this.mResourceListener);
                            bizResOptr.setReadInfo(BizResOptr.RESOURCE_PERMIT_EXPIRE, 0L);
                            bizResOptr.startReadWrite();
                            resDataPermitRvction = null;
                            i = 0;
                            break;
                        }
                        this.mUrlList[i5] = rvctionUrl;
                        if (this.mSituationCode == 0 && rvctionUrl != null) {
                            if (rvctionUrl.equals(str)) {
                                resDataPermitRvction = bizResOptr.getReadData(BizResOptr.RESOURCE_PERMIT_EXPIRE);
                                i3 = i5;
                            }
                            i5++;
                        }
                        if (resDataPermitRvction == null) {
                            if (i >= 0 || rvctionUrl != null) {
                                try {
                                    long j2 = Long.parseLong(bizResOptr.getRvctionUsedDate());
                                    if (j2 < j) {
                                        i2 = i5;
                                    } else {
                                        j2 = j;
                                    }
                                    j = j2;
                                } catch (Exception unused) {
                                    i2 = i5;
                                }
                            } else {
                                i = i5;
                            }
                        }
                        i5++;
                    }
                }
                if (resDataPermitRvction == null) {
                    resDataPermitRvction = new ResDataPermitRvction();
                    if (i < 0) {
                        i = i2;
                    }
                } else {
                    i = i3;
                }
                ((ResDataPermitRvction) resDataPermitRvction).setPosInFile(i * PermitOptr.SIZE_PERMIT_EXPIRE);
            } catch (SysException e) {
                LogMgr.log(2, "702 SysException");
                if (e.getCauseClass() == ResUtil.class && e.getCauseMethod() == "readArea" && e.getMessage() == ResUtil.FILE_CREATION_FAIL) {
                    resDataPermitRvction = new ResDataPermitRvction();
                } else if (e.getCauseClass() == ResourceConnector.class && e.getCauseMethod() == "deleteFile") {
                    resDataPermitRvction = new ResDataPermitRvction();
                } else {
                    throw e;
                }
            }
            return (ResDataPermitRvction) resDataPermitRvction;
        }

        public void updateRevocationResource(ResDataPermitRvction resDataPermitRvction) {
            writeResource(BizResOptr.RESOURCE_PERMIT_EXPIRE, resDataPermitRvction);
        }

        public void updateOfflineRevocationResource(ResDataPermitRvction resDataPermitRvction) {
            writeResource(BizResOptr.RESOURCE_PERMIT_OFFLINE_UPDATE, resDataPermitRvction);
        }

        private void writeResource(String str, ResData resData) {
            BizResOptr bizResOptr = BizResOptr.getInstance();
            synchronized (bizResOptr) {
                bizResOptr.setListener(this.mResourceListener);
                bizResOptr.setWriteInfo(str, resData);
                try {
                    bizResOptr.startReadWrite();
                } catch (SysException e) {
                    if (e.getCauseClass() != ResUtil.class || e.getCauseMethod() != "writeToArea" || e.getMessage() != ResUtil.FILE_CREATION_FAIL) {
                        throw e;
                    }
                }
            }
        }

        private boolean checkFileSize(File file, long j) {
            try {
                return file.length() == j;
            } catch (SecurityException unused) {
                LogMgr.log(1, "800 throw SecurityException");
                return false;
            }
        }

        private void deleteFile(File file) throws SysException {
            try {
                if (file.delete()) {
                    return;
                }
                LogMgr.log(1, "800 failed to delete file");
                throw new SysException((Class<?>) ResourceConnector.class, "deleteFile", file.getPath());
            } catch (SecurityException unused) {
                LogMgr.log(1, "801 throw SecurityException");
                throw new SysException((Class<?>) ResourceConnector.class, "deleteFile", file.getPath());
            }
        }

        private boolean isUrlExist(int i, String str) {
            for (int i2 = 0; i2 < i; i2++) {
                String[] strArr = this.mUrlList;
                if (strArr[i2] != null && str != null && strArr[i2].matches(str)) {
                    return true;
                }
            }
            return false;
        }
    }

    private class Verifier {
        private byte[] mKeyValue;
        private VeriferUtilListener mListener;
        private int mVerifyResult;

        private Verifier() {
            this.mListener = new VeriferUtilListener() { // from class: com.felicanetworks.mfw.i.fbl.PermitOptr.Verifier.1
                @Override // com.felicanetworks.mfw.i.fbl.VeriferUtilListener
                public void resKeyValue(byte[] bArr, int i) {
                    Verifier.this.mKeyValue = bArr;
                }

                @Override // com.felicanetworks.mfw.i.fbl.VeriferUtilListener
                public void ntfyVerificationEnd(int i) {
                    Verifier.this.mVerifyResult = i;
                }
            };
        }

        public byte[] getPublicKey(String str, String str2) {
            VerifierUtil.getKeyValue(str, str2, this.mListener);
            return this.mKeyValue;
        }

        public boolean verify(byte[] bArr, byte[] bArr2, byte[] bArr3) {
            VerifierUtil.verify(bArr, bArr.length, bArr2, bArr2.length, bArr3, bArr3.length, this.mListener);
            return this.mVerifyResult == 0;
        }
    }
}
