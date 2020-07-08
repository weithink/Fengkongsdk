package com.weithink.fengkong.api;



import com.weithink.fengkong.WeithinkFactory;
import com.weithink.fengkong.bean.FileInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileApi {
    public static List<FileInfo> getFileInfoList(String path) {
        List<String> pathList = getFilesAllName(path);
        if (pathList == null || pathList.size() == 0) {
            return new ArrayList<>();
        }
        List<FileInfo> fileInfoList = new ArrayList<>();
        for (int i = 0; i < pathList.size(); i++) {
            FileInfo fileInfo = new FileInfo();
            try {
                File file = new File(pathList.get(i));
                if (file.exists()) {
                    fileInfo.setFileisExists(true);
                    fileInfo.setFile(file.isFile());
                    fileInfo.setDirectory(file.isDirectory());
                    fileInfo.setAbsolutePath(file.getAbsolutePath());
                    fileInfo.setFilecreateTime(file.lastModified());
                    fileInfo.setLastupdateTime(file.lastModified());
                    fileInfo.setParentPath(file.getParent());
                    fileInfo.setRead(file.canRead());
                    fileInfo.setWrite(file.canWrite());
                    fileInfo.setRelativePath(file.getPath());
                    fileInfo.setFilesize(file.length());
                } else {
                    fileInfo.setFileisExists(false);
                }
                fileInfoList.add(fileInfo);
            } catch (Exception e) {
                WeithinkFactory.getLogger().debug("exception = %s" , e.toString());
            }
        }
        return fileInfoList;
    }

    private static List<String> getFilesAllName(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files == null) {
            return null;
        }
        List<String> nameList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
          nameList.add(files[i].getAbsolutePath());
        }
        return nameList;
    }
}


