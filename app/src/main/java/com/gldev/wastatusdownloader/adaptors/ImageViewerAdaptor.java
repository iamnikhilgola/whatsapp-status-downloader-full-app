package com.gldev.wastatusdownloader.adaptors;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.gldev.wastatusdownloader.R;
import com.gldev.wastatusdownloader.models.TransferModel;

import java.util.ArrayList;

public class ImageViewerAdaptor extends PagerAdapter {
    private ArrayList<TransferModel> imageList;
    private Context context;

    public ImageViewerAdaptor(ArrayList<TransferModel> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
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
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage(position);
            }
        });
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadImage(position);
            }
        });
        Glide.with(context)
                .load(BitmapFactory.decodeFile(imageList.get(position).getFilename()))
                .into(imageView);
        container.addView(view);
        return view;
    }
    private void shareImage(int position){

    }
    private void downloadImage(int position){

    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object==view;
    }
}
