package com.gldev.wastatusdownloader.models;

import android.graphics.Bitmap;

import java.io.File;
import java.io.Serializable;

public  class Media implements Serializable {
    private File file;
    private Bitmap thumbnail;
    private String title;
    private String path;


    public Media(File file, String title, String path) {
        this.file = file;
        this.title = title;
        this.path = path;
    }

    public Media(File file, Bitmap thumbnail, String title, String path) {
        this.file = file;
        this.thumbnail = thumbnail;
        this.title = title;
        this.path = path;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
