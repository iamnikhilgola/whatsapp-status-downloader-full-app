package com.gldev.wastatusdownloader.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gldev.wastatusdownloader.AppPlayerActivity;
import com.gldev.wastatusdownloader.R;
import com.gldev.wastatusdownloader.adaptors.ImageAdaptor;
import com.gldev.wastatusdownloader.adaptors.VideoAdaptor;
import com.gldev.wastatusdownloader.models.ImageModel;
import com.gldev.wastatusdownloader.models.VideoModel;
import com.gldev.wastatusdownloader.utils.AppConstants;
import com.gldev.wastatusdownloader.utils.FileOperations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends Fragment {

    @BindView(R.id.videoRecyclerView)
    RecyclerView videoRecyclerView;
    @BindView(R.id.videoProgressBar)
    ProgressBar vidProgressBar;

    ArrayList<VideoModel> myVideoData;
    Handler handler;
    VideoAdaptor adaptor;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        myVideoData = new ArrayList<>();
        videoRecyclerView.setHasFixedSize(true);
        videoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        handler = new Handler();
        loadData();
    }

    private Bitmap getThumbnail(VideoModel videoModel){
        return ThumbnailUtils.createVideoThumbnail(videoModel.getFile().getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos,container,false);
        return view;

    }


    private void loadData(){


            final File fileDir = AppConstants.STATUS_DIRECTORY;
            //System.out.println(fileDir.getAbsolutePath()+"          ++++++++DEBUGGING");
            if(fileDir.exists()){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    adaptor = new VideoAdaptor(myVideoData,VideoFragment.this,getContext());
                                    videoRecyclerView.setAdapter(adaptor);
                                    adaptor.notifyDataSetChanged();
                                }
                            });
                        }

                    }

                }).start();
            }
        }
        private void broadcastGallery(File file){
            Intent intent =new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            getActivity().sendBroadcast(intent);
        }
        public void launchVideoPlayer(VideoModel model){
        Intent intent = new Intent(getContext(), AppPlayerActivity.class);
        intent.putExtra("VIDEO",model.getPath());
        startActivity(intent);


        }
        public void downloadVideo(VideoModel model){
         File file  = new File(AppConstants.MY_DIRECTOY_VIDEO);
            if(!file.exists()){
                file.mkdirs();
            }
            File destFile = new File(file+File.separator+model.getTitle());
            if(destFile.exists()){
                destFile.delete();
            }

            FileOperations fileOperations = new FileOperations();
            try {
                fileOperations.copyFile(model.getFile(),destFile);
            } catch (IOException e) {
                Toast.makeText(getContext(),FileOperations.ERROR_MESSAGE,Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            Toast.makeText(getContext(),FileOperations.SUCCESS_MESSAGE,Toast.LENGTH_LONG).show();
            broadcastGallery(destFile);

        }
}
