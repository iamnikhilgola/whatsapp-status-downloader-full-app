package com.gldev.wastatusdownloader.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gldev.wastatusdownloader.ImageViewerActivity;
import com.gldev.wastatusdownloader.R;
import com.gldev.wastatusdownloader.adaptors.DownloadedImageAdaptor;
import com.gldev.wastatusdownloader.adaptors.ImageAdaptor;
import com.gldev.wastatusdownloader.models.ImageModel;
import com.gldev.wastatusdownloader.models.TransferModel;
import com.gldev.wastatusdownloader.utils.AppConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownloadedImageFragment extends Fragment {
    @BindView(R.id.down_imageRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.down_imageProgressBar)
    ProgressBar progressBar;



    ArrayList<ImageModel> imageModels;
    DownloadedImageAdaptor adaptor;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        imageModels = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        loadImages();
    }
    private Bitmap getThumbnail(ImageModel imageModel){
        /*
        return ThumbnailUtils.createVideoThumbnail(imageModel.getFile().getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
        */
        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imageModel.getFile().getAbsolutePath()), AppConstants.THUMBNAIL_SIZE,AppConstants.THUMBNAIL_SIZE);
        //return BitmapFactory.decodeFile(imageModel.getFile().getAbsolutePath());
    }

    private void loadImages() {
        final File fileDir = new File(AppConstants.MY_DIRECTOY_IMAGE);
        System.out.println(fileDir.getAbsolutePath()+"          ++++++++DEBUGGING------------");
        if(fileDir.exists()){
                    File[] statusFiles = fileDir.listFiles();
                    if(statusFiles!=null && statusFiles.length>0){
                        Arrays.sort(statusFiles);
                        for(File f: statusFiles){
                            if (AppConstants.checkImage(f)) {
                                ImageModel imageModel = new ImageModel(f,f.getName(),f.getAbsolutePath());
                                imageModel.setThumbnail(getThumbnail(imageModel));
                                imageModels.add(imageModel);
                                // System.out.println(f.getName());
                            }
                        }
                                adaptor = new DownloadedImageAdaptor(imageModels,getContext(),DownloadedImageFragment.this);
                                recyclerView.setAdapter(adaptor);
                                adaptor.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);

                    }

                }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.downloaded_image_fragment,container,false);
        return view;
    }
    public void launchImageViewer(ImageModel model){
        Intent intent = new Intent(getActivity(), ImageViewerActivity.class);
        TransferModel transferModel = new TransferModel(model.getFile().getName(),model.getPath());
        transferModel.setDownloadedFile(true);
        intent.putExtra(AppConstants.TRANSFER_KEY_VIEWPAGER,transferModel);
        startActivity(intent);
        getActivity().finish();
    }


}
