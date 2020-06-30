package com.weithink.fengkong.bean;


public class FileInfo {
    private long filecreateTime;
    private long filesize;
    private boolean fileisExists;
    private String relativePath;
    private String absolutePath;
    private boolean isWrite;
    private boolean isRead;
    private String parentPath;
    private long lastupdateTime;
    private boolean isFile;
    private boolean isDirectory;

    public long getFilecreateTime() {
        return this.filecreateTime;
    }

    public void setFilecreateTime(long filecreateTime) {
        this.filecreateTime = filecreateTime;
    }

    public long getFilesize() {
        return this.filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public boolean isFileisExists() {
        return this.fileisExists;
    }

    public void setFileisExists(boolean fileisExists) {
        this.fileisExists = fileisExists;
    }

    public String getRelativePath() {
        return this.relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getAbsolutePath() {
        return this.absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public boolean isWrite() {
        return this.isWrite;
    }

    public void setWrite(boolean write) {
        this.isWrite = write;
    }

    public boolean isRead() {
        return this.isRead;
    }

    public void setRead(boolean read) {
        this.isRead = read;
    }

    public String getParentPath() {
        return this.parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public long getLastupdateTime() {
        return this.lastupdateTime;
    }

    public void setLastupdateTime(long lastupdateTime) {
        this.lastupdateTime = lastupdateTime;
    }

    public boolean isFile() {
        return this.isFile;
    }

    public void setFile(boolean file) {
        this.isFile = file;
    }

    public boolean isDirectory() {
        return this.isDirectory;
    }

    public void setDirectory(boolean directory) {
        this.isDirectory = directory;
    }


    public String toString() {
        return "FileInfo{filecreateTime=" + this.filecreateTime + ", filesize=" + this.filesize + ", fileisExists=" + this.fileisExists + ", relativePath='" + this.relativePath + '\'' + ", absolutePath='" + this.absolutePath + '\'' + ", isWrite=" + this.isWrite + ", isRead=" + this.isRead + ", parentPath='" + this.parentPath + '\'' + ", lastupdateTime=" + this.lastupdateTime + ", isFile=" + this.isFile + ", isDirectory=" + this.isDirectory + '}';
    }
}


