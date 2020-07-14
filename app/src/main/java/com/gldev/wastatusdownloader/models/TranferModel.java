package com.gldev.wastatusdownloader.models;

import java.io.File;
import java.io.Serializable;

public class TranferModel implements Serializable {
    private String filename;
    private String filepath;
    private boolean isDownloadedFile;

    public TranferModel(String filename, String filepath) {
        this.filename = filename;
        this.filepath = filepath;
        this.isDownloadedFile = false;
    }

    public boolean isDownloadedFile() {
        return isDownloadedFile;
    }

    public void setDownloadedFile(boolean downloadedFile) {
        isDownloadedFile = downloadedFile;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
    public File getFile(){
        return new File(filepath);
    }
}
