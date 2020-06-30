package com.weithink.fengkong.bean;

/**
 * 图片信息增加字段
 */
public class MediaInfoMore  {
    private String mediaId;
    private String mediaPath;
    private String mediaSize;
    private String mediaaddTime;
    private String mediaupdateTime;

    private String mediaFileName;
    private String mediaLongitude;
    private String mediaLatitude;
    private String mediaTilte;
    private String mediaType;

    private String mediaDuration;
    private String mediaWidth;
    private String mediaHeight;
    private String mediaDeviceName;//设备名称
    private String mediaDeviceCode;//设备型号

    public void convert(MediaInfo mediaInfo) {
        setMediaId(mediaInfo.getMediaId());
        setMediaPath(mediaInfo.getMediaPath());
        setMediaSize(mediaInfo.getMediaSize());
        setMediaaddTime(mediaInfo.getMediaaddTime());
        setMediaupdateTime(mediaInfo.getMediaupdateTime());
        setMediaFileName(mediaInfo.getMediaFileName());
        setMediaLongitude(mediaInfo.getMediaLongitude());
        setMediaLatitude(mediaInfo.getMediaLatitude());
        setMediaTilte(mediaInfo.getMediaTilte());
        setMediaType(mediaInfo.getMediaType());
        setMediaDuration(mediaInfo.getMediaDuration());
        setMediaWidth(mediaInfo.getMediaWidth());
        setMediaHeight(mediaInfo.getMediaHeight());
    }

    public String getMediaDeviceName() {
        return mediaDeviceName;
    }

    public void setMediaDeviceName(String mediaDeviceName) {
        this.mediaDeviceName = mediaDeviceName;
    }

    public String getMediaDeviceCode() {
        return mediaDeviceCode;
    }

    public void setMediaDeviceCode(String mediaDeviceCode) {
        this.mediaDeviceCode = mediaDeviceCode;
    }



    public String getMediaId() {
        return this.mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaPath() {
        return this.mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public String getMediaSize() {
        return this.mediaSize;
    }

    public void setMediaSize(String mediaSize) {
        this.mediaSize = mediaSize;
    }

    public String getMediaFileName() {
        return this.mediaFileName;
    }

    public void setMediaFileName(String mediaFileName) {
        this.mediaFileName = mediaFileName;
    }

    public String getMediaLongitude() {
        return this.mediaLongitude;
    }

    public void setMediaLongitude(String mediaLongitude) {
        this.mediaLongitude = mediaLongitude;
    }

    public String getMediaLatitude() {
        return this.mediaLatitude;
    }

    public void setMediaLatitude(String mediaLatitude) {
        this.mediaLatitude = mediaLatitude;
    }

    public String getMediaTilte() {
        return this.mediaTilte;
    }

    public void setMediaTilte(String mediaTilte) {
        this.mediaTilte = mediaTilte;
    }

    public String getMediaType() {
        return this.mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }


    public String getMediaWidth() {
        return this.mediaWidth;
    }

    public void setMediaWidth(String mediaWidth) {
        this.mediaWidth = mediaWidth;
    }

    public String getMediaHeight() {
        return this.mediaHeight;
    }

    public void setMediaHeight(String mediaHeight) {
        this.mediaHeight = mediaHeight;
    }

    public String getMediaaddTime() {
        return this.mediaaddTime;
    }

    public void setMediaaddTime(String mediaaddTime) {
        this.mediaaddTime = mediaaddTime;
    }

    public String getMediaupdateTime() {
        return this.mediaupdateTime;
    }

    public void setMediaupdateTime(String mediaupdateTime) {
        this.mediaupdateTime = mediaupdateTime;
    }

    public String getMediaDuration() {
        return this.mediaDuration;
    }

    public void setMediaDuration(String mediaDuration) {
        this.mediaDuration = mediaDuration;
    }


    public String toString() {
        return "MediaInfo{mediaId='" + this.mediaId + '\'' + ", mediaPath='" + this.mediaPath + '\'' + ", mediaSize='" + this.mediaSize + '\'' + ", mediaaddTime='" + this.mediaaddTime + '\'' + ", mediaupdateTime='" + this.mediaupdateTime + '\'' + ", mediaFileName='" + this.mediaFileName + '\'' + ", mediaLongitude='" + this.mediaLongitude + '\'' + ", mediaLatitude='" + this.mediaLatitude + '\'' + ", mediaTilte='" + this.mediaTilte + '\'' + ", mediaType='" + this.mediaType + '\'' + ", mediaDuration='" + this.mediaDuration + '\'' + ", mediaWidth='" + this.mediaWidth + '\'' + ", mediaHeight='" + this.mediaHeight + '\'' + '}';
    }
}


