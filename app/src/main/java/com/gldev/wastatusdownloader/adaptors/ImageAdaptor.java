package com.gldev.wastatusdownloader.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gldev.wastatusdownloader.R;
import com.gldev.wastatusdownloader.fragments.ImageFragment;
import com.gldev.wastatusdownloader.models.ImageModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageAdaptor extends RecyclerView.Adapter<ImageAdaptor.ImageViewHolder> {

    ArrayList<ImageModel> imageModelArrayList;
    Context context;
    ImageFragment fragment;

    public ImageAdaptor(ArrayList<ImageModel> imageModelArrayList, Context context, ImageFragment fragment) {
        this.imageModelArrayList = imageModelArrayList;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.image_status_item,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
       // ImageModel imageModel = imageModelArrayList.get(position);
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(context)
                .load(imageModelArrayList.get(position).getThumbnail())
                .apply(requestOptions)
                .into(holder.thumbnailImageView);

       // holder.thumbnailImageView.setImageBitmap(imageModel.getThumbnail());

    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_thumbnail)
        ImageView thumbnailImageView;
        public ImageViewHolder(@NonNull View itemView) {
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
