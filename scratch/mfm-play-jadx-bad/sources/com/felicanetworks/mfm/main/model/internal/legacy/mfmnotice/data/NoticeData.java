package com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data;

import com.felicanetworks.mfm.main.model.info.NoticeInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter;

/* JADX INFO: loaded from: classes3.dex */
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
    public String scheme;
    public String sendDate;
    public String status;
    public String title;

    public NoticeData(String nid, String msgtype, String sdate, String edate, String title, String msg, String url, String icon, String image, String lstr, String stat, String buttonLable, String detailAccess) {
        this.noticeId = nid;
        this.messageType = msgtype;
        this.sendDate = sdate;
        this.expirationDate = edate;
        this.title = title;
        this.message = msg;
        this.iconUrl = url;
        this.iconName = icon;
        this.imageName = image;
        this.scheme = lstr;
        this.buttonLable = buttonLable;
        this.detailAccess = detailAccess;
        this.status = stat;
    }

    public NoticeData(NoticeInfo info) {
        DateFormatter dateFormatter = new DateFormatter(DateFormatter.DATE_MINUTE, "Asia/Tokyo");
        this.noticeId = info.getId();
        this.messageType = info.getPushType();
        this.sendDate = dateFormatter.format(info.getSendDate());
        this.expirationDate = dateFormatter.format(info.getExpirationDate());
        this.title = info.getTitle();
        this.message = info.getMessage();
        this.iconUrl = info.getIconUrl();
        this.iconName = info.getIconName();
        this.imageName = info.getImageName();
        this.scheme = info.getScheme();
        this.buttonLable = info.getBtnMsg();
        this.detailAccess = info.getDetailAccess();
        this.status = info.getStatus();
    }
}
