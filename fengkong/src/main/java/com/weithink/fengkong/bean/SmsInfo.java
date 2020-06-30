package com.weithink.fengkong.bean;

public class SmsInfo {
    private String smsId;
    private String person;
    private String phone;
    private String type;
    private String content;
    private long date;

    public String getSmsId() {
        return this.smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getPerson() {
        return this.person;
    }

    public void setPerson(String person) {
        this.person = person;
    }


    public String toString() {
        return "SmsInfo{smsId='" + this.smsId + '\'' + ", person='" + this.person + '\'' + ", phone='" + this.phone + '\'' + ", type='" + this.type + '\'' + ", content='" + this.content + '\'' + ", date=" + this.date + '}';
    }
}


