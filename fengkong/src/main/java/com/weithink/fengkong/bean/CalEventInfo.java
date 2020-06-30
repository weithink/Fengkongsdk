package com.weithink.fengkong.bean;

public class CalEventInfo {
    private String calId;
    private String startTime;
    private String endTime;
    private String title;
    private String content;
    private String address;

    public String getCalId() {
        return this.calId;
    }

    public void setCalId(String calId) {
        this.calId = calId;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String toString() {
        return "CalEventInfo{calId='" + this.calId + '\'' + ", startTime='" + this.startTime + '\'' + ", endTime='" + this.endTime + '\'' + ", title='" + this.title + '\'' + ", content='" + this.content + '\'' + ", address='" + this.address + '\'' + '}';
    }
}


