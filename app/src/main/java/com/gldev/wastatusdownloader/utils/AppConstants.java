package com.gldev.wastatusdownloader.utils;

import android.os.Environment;

import java.io.File;

public class AppConstants {
    public static  final File STATUS_DIRECTORY = new File(Environment.getExternalStorageDirectory()+File.separator+"WhatsApp/media/.Statuses");
    public static  final String MY_DIRECTOY_IMAGE = Environment.getExternalStorageDirectory()+File.separator+"GLWhatsappDownloads"+File.separator+"images";
    public static  final String MY_DIRECTOY_VIDEO = Environment.getExternalStorageDirectory()+File.separator+"GLWhatsappDownloads"+File.separator+"videos";
    public static  final String TRANSFER_KEY_VIEWPAGER="VIEWPAGERKEY";
    public static  final int THUMBNAIL_SIZE=360;
    public static boolean checkVideo(File file){
        if(file.getName().endsWith(".MP4")||file.getName().endsWith("mp4"))
            return true;
        return false;
        //if(file.getName().endsWith("."));
    }
    public static boolean checkImage(File file){
        if (file.getName().endsWith(".nomedia") || checkVideo(file)){
            return false;
        }
        return true;
    }

    private String app_id = "ca-app-pub-1554837528700151~8512806536";
}

