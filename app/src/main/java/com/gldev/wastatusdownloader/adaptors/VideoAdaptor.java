package com.gldev.wastatusdownloader.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gldev.wastatusdownloader.R;
import com.gldev.wastatusdownloader.fragments.VideoFragment;
import com.gldev.wastatusdownloader.models.VideoModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdaptor extends RecyclerView.Adapter<VideoAdaptor.VideoHolder> {

    ArrayList<VideoModel> videoModelArrayList;
    VideoFragment videoFragment;
    Context context;

    public VideoAdaptor(ArrayList<VideoModel> videoModelArrayList, VideoFragment videoFragment, Context context) {
        this.videoModelArrayList = videoModelArrayList;
        this.videoFragment = videoFragment;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_status_item,parent,false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
            VideoModel model = videoModelArrayList.get(position);
            holder.imgThumbnail.setImageBitmap(model.getThumbnail());
    }

    @Override
    public int getItemCount() {
        return videoModelArrayList.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vi_thumbnail)
        ImageView imgThumbnail;
        @BindView(R.id.save_video)
        ImageButton saveVideo;
        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            saveVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VideoModel model = videoModelArrayList.get(getAbsoluteAdapterPosition());
                    videoFragment.downloadVideo(model);
                }
            });
            imgThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    videoFragment.launchVideoPlayer(videoModelArrayList.get(getAbsoluteAdapterPosition()));
                }
            });
        }
    }
}
