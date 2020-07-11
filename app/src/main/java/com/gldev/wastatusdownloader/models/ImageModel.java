package com.gldev.wastatusdownloader.models;

import java.io.File;
import java.io.Serializable;

public class ImageModel extends Media implements Serializable {
    public ImageModel(File file, String title, String path) {
        super(file, title, path);
    }
}
