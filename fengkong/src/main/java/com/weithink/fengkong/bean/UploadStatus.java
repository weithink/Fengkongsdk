package com.weithink.fengkong.bean;

public class UploadStatus {
    int code;
    String msg;
    Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class  Data {

        int app_infos;
        int cal_event_infos;
        int calls_infos;
        int device_infos;
        int file_infos;
        int location_infos;
        int media_info;
        int model_contact;
        int sms_infos;
        int user_infos;

        public int getApp_infos() {
            return app_infos;
        }

        public void setApp_infos(int app_infos) {
            this.app_infos = app_infos;
        }

        public int getCal_event_infos() {
            return cal_event_infos;
        }

        public void setCal_event_infos(int cal_event_infos) {
            this.cal_event_infos = cal_event_infos;
        }

        public int getCalls_infos() {
            return calls_infos;
        }

        public void setCalls_infos(int calls_infos) {
            this.calls_infos = calls_infos;
        }

        public int getDevice_infos() {
            return device_infos;
        }

        public void setDevice_infos(int device_infos) {
            this.device_infos = device_infos;
        }

        public int getFile_infos() {
            return file_infos;
        }

        public void setFile_infos(int file_infos) {
            this.file_infos = file_infos;
        }

        public int getLocation_infos() {
            return location_infos;
        }

        public void setLocation_infos(int location_infos) {
            this.location_infos = location_infos;
        }

        public int getMedia_info() {
            return media_info;
        }

        public void setMedia_info(int media_info) {
            this.media_info = media_info;
        }

        public int getModel_contact() {
            return model_contact;
        }

        public void setModel_contact(int model_contact) {
            this.model_contact = model_contact;
        }

        public int getSms_infos() {
            return sms_infos;
        }

        public void setSms_infos(int sms_infos) {
            this.sms_infos = sms_infos;
        }

        public int getUser_infos() {
            return user_infos;
        }

        public void setUser_infos(int user_infos) {
            this.user_infos = user_infos;
        }
    }
}
