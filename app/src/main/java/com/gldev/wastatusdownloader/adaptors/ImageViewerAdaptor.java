package com.gldev.wastatusdownloader.adaptors;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.gldev.wastatusdownloader.ImageViewerActivity;
import com.gldev.wastatusdownloader.R;
import com.gldev.wastatusdownloader.fragments.DownloadedImageFragment;
import com.gldev.wastatusdownloader.models.TransferModel;
import com.gldev.wastatusdownloader.utils.AppConstants;
import com.gldev.wastatusdownloader.utils.FileOperations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageViewerAdaptor extends PagerAdapter {
    private ArrayList<TransferModel> imageList;
    private Context context;
    private boolean toggleFlag=false;
    private  ImageViewerActivity activity;

    public ImageViewerAdaptor(ArrayList<TransferModel> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    public ImageViewerAdaptor(ArrayList<TransferModel> imageList, Context context, ImageViewerActivity activity) {
        this.imageList = imageList;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.imageviewer_item,null);
        ImageView imageView = view.findViewById(R.id.viewPager_item_image);
        ImageButton shareButton = view.findViewById(R.id.imageViewer_sharebutton);
        ImageButton downloadButton = view.findViewById(R.id.imageviewer_downloadbutton);
        ImageButton deleteButton=view.findViewById(R.id.imageviewer_deletebutton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage(position);
            }
        });

        final RelativeLayout relativeLayout = view.findViewById(R.id.imageViewer_RL);
        relativeLayout.setVisibility(View.GONE);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadImage(position);
            }
        });
        Glide.with(context)
                .load(BitmapFactory.decodeFile(imageList.get(position).getFilepath()))
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleAnim(relativeLayout);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.deleteImage(position);

            }
        });
        if(imageList.get(position).isDownloadedFile()){
            deleteButton.setVisibility(View.VISIBLE);
            downloadButton.setVisibility(View.GONE);

        }
        else{
            deleteButton.setVisibility(View.GONE);
            downloadButton.setVisibility(View.VISIBLE);
        }
        container.addView(view);

        return view;
    }


    private void shareImage(int position){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        //shareIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri apkURI = FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".provider", new File(imageList.get(position).getFilepath()));
        context.grantUriPermission(context.getPackageName(),apkURI, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //shareIntent.setDataAndType(apkURI, "video/mp4");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


        shareIntent.putExtra(Intent.EXTRA_STREAM, apkURI);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, context.getResources().getText(R.string.send_to)));

    }
    private void downloadImage(int position){
            downloadImageFile(new File(imageList.get(position).getFilepath()));

    }

    public void downloadImageFile(File model){
        File file  = new File(AppConstants.MY_DIRECTOY_IMAGE);
        if(!file.exists()){
            file.mkdirs();
        }
        File destFile = new File(file+File.separator+model.getName());
        if(destFile.exists()){
            destFile.delete();
        }

        FileOperations fileOperations = new FileOperations();
        try {
            fileOperations.copyFile(model,destFile);
        } catch (IOException e) {
            Toast.makeText(context,FileOperations.ERROR_MESSAGE,Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        Toast.makeText(context,FileOperations.SUCCESS_MESSAGE,Toast.LENGTH_LONG).show();

    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        toggleFlag=false;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object==view;
    }
    private void toggleAnim(final RelativeLayout relativeLayout){
        if(toggleFlag){
            relativeLayout.animate()
                    .alpha(0f)
                    .setDuration(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            relativeLayout.setVisibility(View.GONE);
                        }
                    });
        }
        else{
            relativeLayout.setAlpha(0f);
            relativeLayout.setVisibility(View.VISIBLE);
            relativeLayout.animate().alpha(1.0f).setDuration(1000).setListener(null);
        }
        toggleFlag=!toggleFlag;
    }

}
