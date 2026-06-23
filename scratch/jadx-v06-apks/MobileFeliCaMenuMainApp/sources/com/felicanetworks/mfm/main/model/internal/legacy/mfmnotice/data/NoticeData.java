package com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data;

import com.felicanetworks.mfm.main.model.info.NoticeInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter;

/* JADX INFO: loaded from: classes.dex */
public class NoticeData {
    public String buttonLable;
    public String detailAccess;
    public String expirationDate;
    public String iconName;
    public String iconUrl;
    public String imageName;
    public String message;
    public String messageType;
    public String noticeId;
    public String patternId;
    public String scheme;
    public String sendDate;
    public String status;
    public String title;

    public NoticeData(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14) {
        this.noticeId = str;
        this.patternId = str2;
        this.messageType = str3;
        this.sendDate = str4;
        this.expirationDate = str5;
        this.title = str6;
        this.message = str7;
        this.iconUrl = str8;
        this.iconName = str9;
        this.imageName = str10;
        this.scheme = str11;
        this.buttonLable = str13;
        this.detailAccess = str14;
        this.status = str12;
    }

    public NoticeData(NoticeInfo noticeInfo) {
        DateFormatter dateFormatter = new DateFormatter(DateFormatter.DATE_MINUTE, "Asia/Tokyo");
        this.noticeId = noticeInfo.getId();
        this.patternId = noticeInfo.getPatternId();
        this.messageType = noticeInfo.getPushType();
        this.sendDate = dateFormatter.format(noticeInfo.getSendDate());
        this.expirationDate = dateFormatter.format(noticeInfo.getExpirationDate());
        this.title = noticeInfo.getTitle();
        this.message = noticeInfo.getMessage();
        this.iconUrl = noticeInfo.getIconUrl();
        this.iconName = noticeInfo.getIconName();
        this.imageName = noticeInfo.getImageName();
        this.scheme = noticeInfo.getScheme();
        this.buttonLable = noticeInfo.getBtnMsg();
        this.detailAccess = noticeInfo.getDetailAccess();
        this.status = noticeInfo.getStatus();
    }
}
