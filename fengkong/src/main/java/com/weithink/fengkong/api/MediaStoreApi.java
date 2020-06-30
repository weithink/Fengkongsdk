package com.weithink.fengkong.api;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;


import com.weithink.fengkong.Contents;
import com.weithink.fengkong.WeithinkFactory;
import com.weithink.fengkong.bean.MediaInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MediaStoreApi {
    private static String[] audioVlues = new String[]{"_id", "title", "_data", "date_added", "date_modified", "duration", "mime_type", "_display_name", "_size"};

    private static String[] imageVlues = new String[]{
            "_id", "title", "_data", "date_added", "date_modified", "mime_type", "width", "height", "_display_name", "_size",
            "latitude", "longitude"};

    private static String[] videoVlues = new String[]{
            "_id", "title", "_data", "date_added", "date_modified", "duration", "mime_type", "_display_name", "_size", "width",
            "height", "latitude", "longitude", "datetaken"};

    public static List<MediaInfo> getMediaAudioInfos(Context context) {
        return getMediaValues(context, Contents.MEDIASTORE_AUDIO_URI, audioVlues, "AUDIO");
    }

    public static List<MediaInfo> getMediaImageInfos(Context context) {
        return getMediaValues(context, Contents.MEDIASTORE_IMAGE_URI, imageVlues, "IMAGE");
    }

    public static List<MediaInfo> getMediaVideoInfos(Context context) {
        return getMediaValues(context, Contents.MEDIASTORE_VIDEO_URI, videoVlues, "VIDEO");
    }

    public static List<MediaInfo> getMediaValues(Context context, Uri uri, String[] strsKey, String item) {
        List<MediaInfo> mediaInfoList = new ArrayList<>();
        try {
            Cursor cursor = context.getContentResolver().query(uri, strsKey, null, null, "date_added desc");
            try {
                while (cursor != null && cursor.moveToNext()) {
                    String date = cursor.getString(cursor.getColumnIndex("date_added"));
                    long dateValue = Long.valueOf(date).longValue() * 1000L;
                    if (isOver6Months(dateValue)) {
                        break;
                    }
                    MediaInfo mediaInfo = new MediaInfo();
                    if (item.equals("VIDEO")) {
                        mediaInfo.setMediaId(cursor.getString(cursor.getColumnIndex("_id")));
                        mediaInfo.setMediaTilte(cursor.getString(cursor.getColumnIndex("title")));
                        mediaInfo.setMediaPath(cursor.getString(cursor.getColumnIndex("_data")));
                        mediaInfo.setMediaSize(cursor.getString(cursor.getColumnIndex("_size")));
                        mediaInfo.setMediaupdateTime(cursor.getString(cursor.getColumnIndex("date_modified")));
                        mediaInfo.setMediaLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
                        mediaInfo.setMediaLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
                        mediaInfo.setMediaType(cursor.getString(cursor.getColumnIndex("mime_type")));
                        mediaInfo.setMediaDuration(cursor.getString(cursor.getColumnIndex("duration")));
                        mediaInfo.setMediaWidth(cursor.getString(cursor.getColumnIndex("width")));
                        mediaInfo.setMediaHeight(cursor.getString(cursor.getColumnIndex("height")));
                        mediaInfo.setMediaFileName(cursor.getString(cursor.getColumnIndex("_display_name")));
                        mediaInfo.setMediaaddTime(cursor.getString(cursor.getColumnIndex("date_added")));
                    } else if (item.equals("IMAGE")) {
                        mediaInfo.setMediaId(cursor.getString(cursor.getColumnIndex("_id")));
                        mediaInfo.setMediaTilte(cursor.getString(cursor.getColumnIndex("title")));
                        mediaInfo.setMediaPath(cursor.getString(cursor.getColumnIndex("_data")));
                        mediaInfo.setMediaSize(cursor.getString(cursor.getColumnIndex("_size")));
                        mediaInfo.setMediaupdateTime(cursor.getString(cursor.getColumnIndex("date_modified")));
                        mediaInfo.setMediaLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
                        mediaInfo.setMediaLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
                        mediaInfo.setMediaType(cursor.getString(cursor.getColumnIndex("mime_type")));
                        mediaInfo.setMediaWidth(cursor.getString(cursor.getColumnIndex("width")));
                        mediaInfo.setMediaHeight(cursor.getString(cursor.getColumnIndex("height")));
                        mediaInfo.setMediaFileName(cursor.getString(cursor.getColumnIndex("_display_name")));
                        mediaInfo.setMediaaddTime(cursor.getString(cursor.getColumnIndex("date_added")));
                    } else if (item.equals("AUDIO")) {
                        mediaInfo.setMediaId(cursor.getString(cursor.getColumnIndex("_id")));
                        mediaInfo.setMediaTilte(cursor.getString(cursor.getColumnIndex("title")));
                        mediaInfo.setMediaPath(cursor.getString(cursor.getColumnIndex("_data")));
                        mediaInfo.setMediaSize(cursor.getString(cursor.getColumnIndex("_size")));
                        mediaInfo.setMediaupdateTime(cursor.getString(cursor.getColumnIndex("date_modified")));
                        mediaInfo.setMediaType(cursor.getString(cursor.getColumnIndex("mime_type")));
                        mediaInfo.setMediaDuration(cursor.getString(cursor.getColumnIndex("duration")));
                        mediaInfo.setMediaFileName(cursor.getString(cursor.getColumnIndex("_display_name")));
                        mediaInfo.setMediaaddTime(cursor.getString(cursor.getColumnIndex("date_added")));
                    }
                    mediaInfoList.add(mediaInfo);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable throwable) {
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                    }
                }
                throw throwable;
            }
        } catch (Exception e) {
            WeithinkFactory.getLogger().debug("exception = " + e.toString());
        }
        return mediaInfoList;
    }

    private static boolean isOver6Months(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.add(2, -6);
        String time = sdf.format(cal.getTime());
        try {
            if (sdf.parse(time).after(new Date(date))) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
