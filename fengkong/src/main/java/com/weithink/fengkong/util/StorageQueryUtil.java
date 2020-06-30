package com.weithink.fengkong.util;

import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;


import com.weithink.fengkong.bean.DeviceInfo;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class StorageQueryUtil {
    private static final String TAG = "storage";

    public static void queryWithStorageManager(Context context, DeviceInfo deviceInfo) {
        StorageManager storageManager = (StorageManager) context.getSystemService("storage");
        long total = 0L, used = 0L, systemSize = 0L;
        long sdTotal = 0L, sdUsed = 0L;
        Boolean sdCard = Boolean.valueOf(false);
        float unit = 1024.0F, unit2 = 1000.0F;
        int version = Build.VERSION.SDK_INT;
        if (version < 23) {
            try {
                Method getVolumeList = StorageManager.class.getDeclaredMethod("getVolumeList", new Class[0]);
                StorageVolume[] volumeList = (StorageVolume[]) getVolumeList.invoke(storageManager, new Object[0]);
                long totalSize = 0L, availableSize = 0L;
                if (volumeList != null) {
                    Method getPathFile = null;
                    for (StorageVolume volume : volumeList) {
                        if (getPathFile == null)
                            getPathFile = volume.getClass().getDeclaredMethod("getPathFile", new Class[0]);
                        File file = (File) getPathFile.invoke(volume, new Object[0]);
                        total += file.getTotalSpace();
                        availableSize += file.getUsableSpace();
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Method getVolumes = StorageManager.class.getDeclaredMethod("getVolumes", new Class[0]);
                List<Object> getVolumeInfo = (List<Object>) getVolumes.invoke(storageManager, new Object[0]);
                for (Object obj : getVolumeInfo) {
                    Field getType = obj.getClass().getField("type");
                    int type = getType.getInt(obj);
                    if (type == 1) {
                        long totalSize = 0L;
                        if (version >= 26) {
                            Method getFsUuid = obj.getClass().getDeclaredMethod("getFsUuid", new Class[0]);
                            String fsUuid = (String) getFsUuid.invoke(obj, new Object[0]);
                            totalSize = getTotalSize(context, fsUuid);
                        } else if (version >= 25) {
                            Method getPrimaryStorageSize = StorageManager.class.getMethod("getPrimaryStorageSize", new Class[0]);
                            totalSize = ((Long) getPrimaryStorageSize.invoke(storageManager, new Object[0])).longValue();
                        }
                        Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable", new Class[0]);
                        boolean readable = ((Boolean) isMountedReadable.invoke(obj, new Object[0])).booleanValue();
                        if (readable) {
                            Method file = obj.getClass().getDeclaredMethod("getPath", new Class[0]);
                            File f = (File) file.invoke(obj, new Object[0]);
                            if (totalSize == 0L)
                                totalSize = f.getTotalSpace();
                            systemSize = totalSize - f.getTotalSpace();
                            used += totalSize - f.getFreeSpace();
                            total += totalSize;
                        }
                        continue;
                    }
                    if (type == 0) {
                        Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable", new Class[0]);
                        boolean readable = ((Boolean) isMountedReadable.invoke(obj, new Object[0])).booleanValue();
                        if (readable) {
                            Method file = obj.getClass().getDeclaredMethod("getPath", new Class[0]);
                            File f = (File) file.invoke(obj, new Object[0]);
                            used += f.getTotalSpace() - f.getFreeSpace();
                            sdTotal += f.getTotalSpace();
                            sdCard = Boolean.valueOf(true);
                        }
                        continue;
                    }
                    if (type == 2) ;
                }
            } catch (SecurityException e) {
                Log.e("storage", "缺少权限：permission.PACKAGE_USAGE_STATS");
            } catch (Exception e) {
                e.printStackTrace();
                queryWithStatFs();
            }
        }
        String totalGB = getUnit((float) total, unit2);
        String sdTotalGB = getUnit((float) sdTotal, unit2);
        deviceInfo.setSdCard(sdCard);
        deviceInfo.setSdCardSize(sdTotalGB);
        deviceInfo.setSystemSize(totalGB);
    }

    public static void queryWithStatFs() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long blockCount = statFs.getBlockCount();
        long blockSize = statFs.getBlockSize();
        long availableCount = statFs.getAvailableBlocks();
        long freeBlocks = statFs.getFreeBlocks();
        Log.d("storage", "=========");
        Log.d("storage", "total = " + getUnit((float) (blockSize * blockCount), 1024.0F));
        Log.d("storage", "available = " + getUnit((float) (blockSize * availableCount), 1024.0F));
        Log.d("storage", "free = " + getUnit((float) (blockSize * freeBlocks), 1024.0F));
    }

    private static String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};

    public static String getUnit(float size, float base) {
        size /= base * base * base;
        return String.format(Locale.getDefault(), "%.2f", new Object[]{Float.valueOf(size)});
    }

    public static long getTotalSize(Context context, String fsUuid) {
        try {
            UUID id;
            if (fsUuid == null) {
                id = StorageManager.UUID_DEFAULT;
            } else {
                id = UUID.fromString(fsUuid);
            }
            StorageStatsManager stats = (StorageStatsManager) context.getSystemService(StorageStatsManager.class);
            return stats.getTotalBytes(id);
        } catch (NoSuchFieldError | NoClassDefFoundError | NullPointerException | java.io.IOException e) {
            e.printStackTrace();
            return -1L;
        }
    }
}
