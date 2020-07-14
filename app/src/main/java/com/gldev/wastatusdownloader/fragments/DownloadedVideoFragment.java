package com.gldev.wastatusdownloader.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gldev.wastatusdownloader.AppPlayerActivity;
import com.gldev.wastatusdownloader.R;
import com.gldev.wastatusdownloader.adaptors.DownloadedVideoAdaptor;
import com.gldev.wastatusdownloader.adaptors.VideoAdaptor;
import com.gldev.wastatusdownloader.models.VideoModel;
import com.gldev.wastatusdownloader.utils.AppConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownloadedVideoFragment extends Fragment {
    @BindView(R.id.downvideoRecyclerView)
    RecyclerView videoRecyclerView;
    @BindView(R.id.downvideoProgressBar)
    ProgressBar vidProgressBar;

    ArrayList<VideoModel> myVideoData;
    Handler handler;
    DownloadedVideoAdaptor adaptor;
    boolean active = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        myVideoData = new ArrayList<>();
        videoRecyclerView.setHasFixedSize(true);
        videoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        handler = new Handler();
        loadData();

    }

    private void loadData() {
        active = true;
        final File fileDir = new File(AppConstants.MY_DIRECTOY_VIDEO);
        //System.out.println(fileDir.getAbsolutePath()+"          ++++++++DEBUGGING");
        if(fileDir.exists()){
                    File[] statusFiles = fileDir.listFiles();
                    if(statusFiles!=null && statusFiles.length>0){
                        Arrays.sort(statusFiles);
                        for(File f: statusFiles){
                            if (AppConstants.checkVideo(f)) {
                                VideoModel imageModel = new VideoModel(f,f.getName(),f.getAbsolutePath());
                                imageModel.setThumbnail(getThumbnail(imageModel));
                                myVideoData.add(imageModel);

                            }
                        }
                                adaptor = new DownloadedVideoAdaptor(myVideoData,DownloadedVideoFragment.this,getContext());
                                videoRecyclerView.setAdapter(adaptor);
                                adaptor.notifyDataSetChanged();
                                vidProgressBar.setVisibility(View.GONE);
                            }
                    }



            }


    private Bitmap getThumbnail(VideoModel videoModel){
        return ThumbnailUtils.createVideoThumbnail(videoModel.getFile().getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.downlaoded_video_fragment,container,false);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        if(!active) {
            myVideoData = new ArrayList<>();
            loadData();
        }
    }

    public void launchVideoPlayer(VideoModel model){
        active = false;
        Intent intent = new Intent(getContext(), AppPlayerActivity.class);
        intent.putExtra("VIDEO",model.getPath());
        intent.putExtra("VIDEOB",true);
        startActivity(intent);


    }


}
