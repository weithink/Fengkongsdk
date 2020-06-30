package com.weithink.fengkong.bean;

public class CallsInfo {
    private Integer callId;
    private String simNumber;
    private String callName;
    private String callNumber;
    private int callType;
    private int callDuration;
    private String callDate;
    private String callTime;

    public Integer getCallId() {
        return this.callId;
    }
    public void setCallId(Integer callId) {
        this.callId = callId;
    }
    public String getSimNumber() {
        return this.simNumber;
    }
    public void setSimNumber(String simNumber) {
        this.simNumber = simNumber;
    }
    public String getCallName() {
        return this.callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getCallNumber() {
        return this.callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public int getCallType() {
        return this.callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    public int getCallDuration() {
        return this.callDuration;
    }

    public void setCallDuration(int callDuration) {
        this.callDuration = callDuration;
    }

    public String getCallDate() {
        return this.callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getCallTime() {
        return this.callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }


    public String toString() {
        return "CallsInfo{callName='" + this.callName + '\'' + ", callNumber='" + this.callNumber + '\'' + ", callType=" + this.callType + ", callDuration=" + this.callDuration + ", callDate='" + this.callDate + '\'' + ", callTime='" + this.callTime + '\'' + '}';
    }
}


