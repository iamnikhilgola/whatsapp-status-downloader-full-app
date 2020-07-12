package com.gldev.wastatusdownloader.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.system.Os;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.gldev.wastatusdownloader.ImageViewerActivity;
import com.gldev.wastatusdownloader.MainActivity;
import com.gldev.wastatusdownloader.R;
import com.gldev.wastatusdownloader.adaptors.ImageAdaptor;
import com.gldev.wastatusdownloader.models.ImageModel;
import com.gldev.wastatusdownloader.models.TransferModel;
import com.gldev.wastatusdownloader.utils.AppConstants;
import com.gldev.wastatusdownloader.utils.FileOperations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageFragment extends Fragment {
    @BindView(R.id.imageRecyclerView)
    RecyclerView imageRecyclerView;
    @BindView(R.id.imageProgressBar)
    ProgressBar imageProgressBar;

    ArrayList<ImageModel> imageModels;

    Handler handler;
    ImageAdaptor adaptor;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        imageModels = new ArrayList<>();
        //imageRecyclerView.setHasFixedSize(false);
        //staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        //imageRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        imageRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        handler = new Handler();
        loadImages();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images,container,false);
        return view;

    }

    private Bitmap getThumbnail(ImageModel imageModel){
        /*
        return ThumbnailUtils.createVideoThumbnail(imageModel.getFile().getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
        */
        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imageModel.getFile().getAbsolutePath()),AppConstants.THUMBNAIL_SIZE,AppConstants.THUMBNAIL_SIZE);
        //return BitmapFactory.decodeFile(imageModel.getFile().getAbsolutePath());
    }
    private void loadImages(){

         final File fileDir = AppConstants.STATUS_DIRECTORY;
         System.out.println(fileDir.getAbsolutePath()+"          ++++++++DEBUGGING");
            if(fileDir.exists()){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File[] statusFiles = fileDir.listFiles();
                        if(statusFiles!=null && statusFiles.length>0){
                            Arrays.sort(statusFiles);
                            for(File f: statusFiles){
                                if (AppConstants.checkImage(f)) {
                                    ImageModel imageModel = new ImageModel(f,f.getName(),f.getAbsolutePath());
                                    imageModel.setThumbnail(getThumbnail(imageModel));
                                    imageModels.add(imageModel);
                                    System.out.println(f.getName());
                                }
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    adaptor = new ImageAdaptor(imageModels,getContext(),ImageFragment.this);
                                    imageRecyclerView.setAdapter(adaptor);
                                    adaptor.notifyDataSetChanged();
                                }
                            });
                        }

                    }

                }).start();
            }
    }
    public void launchImageViewer(ImageModel model){
        Intent intent = new Intent(getActivity(), ImageViewerActivity.class);
        TransferModel transferModel = new TransferModel(model.getFile().getName(),model.getPath());
        intent.putExtra(AppConstants.TRANSFER_KEY_VIEWPAGER,transferModel);
        startActivity(intent);
    }
    private void broadcastGallery(File file){
        Intent intent =new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        getActivity().sendBroadcast(intent);
    }
    public void downloadImage(ImageModel model){
        File file  = new File(AppConstants.MY_DIRECTOY_IMAGE);
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
