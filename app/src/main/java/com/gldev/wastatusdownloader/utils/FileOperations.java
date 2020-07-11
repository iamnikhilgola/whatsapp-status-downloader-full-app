package com.gldev.wastatusdownloader.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileOperations {
    public static final String ERROR_MESSAGE="File copying unsuccessful, Try again!!";

    public static final String SUCCESS_MESSAGE="File copying successful!!";
    public void copyFile(File sourceFile, File destinationFile) throws IOException {
        if(!destinationFile.getParentFile().exists()){
            destinationFile.getParentFile().mkdirs();
        }
        if(!destinationFile.exists()){
            destinationFile.createNewFile();
        }
        FileChannel outChannel = null;
        FileChannel inChannel = null;

        outChannel = new FileInputStream(sourceFile).getChannel();
        inChannel = new FileOutputStream(destinationFile).getChannel();
        inChannel.transferFrom(outChannel,0,outChannel.size());

        outChannel.close();
        inChannel.close();

    }
}
