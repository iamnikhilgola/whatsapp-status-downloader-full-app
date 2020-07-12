package com.gldev.wastatusdownloader.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gldev.wastatusdownloader.R;
import com.gldev.wastatusdownloader.fragments.DownloadedImageFragment;
import com.gldev.wastatusdownloader.fragments.ImageFragment;
import com.gldev.wastatusdownloader.models.ImageModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownloadedImageAdaptor extends RecyclerView.Adapter<DownloadedImageAdaptor.DviewHolder>{
    ArrayList<ImageModel> imageModelArrayList;
    Context context;
    DownloadedImageFragment fragment;

    public DownloadedImageAdaptor(ArrayList<ImageModel> imageModelArrayList, Context context, DownloadedImageFragment fragment) {
        this.imageModelArrayList = imageModelArrayList;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public DviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.downloaded_image_item,parent,false);
        return new DownloadedImageAdaptor.DviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DviewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(context)
                .load(imageModelArrayList.get(position).getThumbnail())
                .apply(requestOptions)
                .into(holder.thumbnailImageView);

    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    public class DviewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.downimg_thumbnail)
        ImageView thumbnailImageView;

        public DviewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            thumbnailImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment.launchImageViewer(imageModelArrayList.get(getAbsoluteAdapterPosition()));
                }
            });
        }
    }
}
