package com.gldev.wastatusdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.gldev.wastatusdownloader.adaptors.ImageViewerAdaptor;
import com.gldev.wastatusdownloader.models.TransferModel;
import com.gldev.wastatusdownloader.utils.AppConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageViewerActivity extends AppCompatActivity {

    @BindView(R.id.imageViewer_pager)
    ViewPager viewPager;
    ImageViewerAdaptor adaptor;

    TransferModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        model = (TransferModel) getIntent().getSerializableExtra(AppConstants.TRANSFER_KEY_VIEWPAGER);
        initComponents();

    }
    private void initComponents(){
        ButterKnife.bind(this);
        ArrayList<TransferModel> arrayList = getItems();
        int currentPosition = getItemPosition(arrayList,model);
        adaptor = new ImageViewerAdaptor(arrayList,this);
        viewPager.setAdapter(adaptor);
        viewPager.setCurrentItem(currentPosition);
        adaptor.notifyDataSetChanged();
    }
    private int getItemPosition(ArrayList<TransferModel> arrayList,TransferModel model){
        int i =0;
        for(;i<arrayList.size();i++){
            if(model.getFilename().equals(arrayList.get(i).getFilename()))
                return i;
        }
        return -1;
    }
    private ArrayList<TransferModel> getItems(){
        ArrayList<TransferModel> imageModels = new ArrayList<>();
        final File fileDir = AppConstants.STATUS_DIRECTORY;
        if(fileDir.exists()){
                    File[] statusFiles = fileDir.listFiles();


                    if(statusFiles!=null && statusFiles.length>0){
                        Arrays.sort(statusFiles);
                        for(File f: statusFiles){
                            if (AppConstants.checkImage(f)) {
                                TransferModel imageModel = new TransferModel(f.getName(),f.getAbsolutePath());
                                imageModels.add(imageModel);
                            }
                        }
                    }

        }

        return imageModels;
    }
}