package com.gldev.wastatusdownloader.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gldev.wastatusdownloader.R;
import com.gldev.wastatusdownloader.fragments.DownloadedVideoFragment;
import com.gldev.wastatusdownloader.fragments.VideoFragment;
import com.gldev.wastatusdownloader.models.VideoModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownloadedVideoAdaptor extends RecyclerView.Adapter<DownloadedVideoAdaptor.DviewHolder>{
    ArrayList<VideoModel> videoModelArrayList;
    DownloadedVideoFragment videoFragment;
    Context context;

    public DownloadedVideoAdaptor(ArrayList<VideoModel> videoModelArrayList, DownloadedVideoFragment videoFragment, Context context) {
        this.videoModelArrayList = videoModelArrayList;
        this.videoFragment = videoFragment;
        this.context = context;
    }

    @NonNull
    @Override
    public DviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.downloaded_video_item,parent,false);
        return new DownloadedVideoAdaptor.DviewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DviewHolder holder, int position) {
        VideoModel model = videoModelArrayList.get(position);
        holder.imgThumbnail.setImageBitmap(model.getThumbnail());

    }

    @Override
    public int getItemCount() {
        return videoModelArrayList.size();
    }
    public void deleteItem(VideoModel model){



    }
    public void launchVideo(VideoModel model){
        videoFragment.launchVideoPlayer(model);
    }

    public class DviewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.downvi_thumbnail)
        ImageView imgThumbnail;
        @BindView(R.id.downvi_delete)
        ImageView deleteButton;

        public DviewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            imgThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    launchVideo(videoModelArrayList.get(getAbsoluteAdapterPosition()));
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }
    }
}
