package com.gldev.wastatusdownloader.models;

import java.io.File;
import java.io.Serializable;

public class TransferModel implements Serializable {
    private String filename;
    private String filepath;
    private boolean isDownloadedFile;

    public boolean isDownloadedFile() {
        return isDownloadedFile;
    }

    public void setDownloadedFile(boolean downloadedFile) {
        isDownloadedFile = downloadedFile;
    }

    public TransferModel(String filename, String filepath) {
        this.filename = filename;
        this.filepath = filepath;
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
