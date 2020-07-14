package com.gldev.wastatusdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

    AlertDialog.Builder builder;
    ArrayList<TransferModel> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        model = (TransferModel) getIntent().getSerializableExtra(AppConstants.TRANSFER_KEY_VIEWPAGER);
        initComponents();

    }
    private void initComponents(){
        ButterKnife.bind(this);

        arrayList = getItems(model.isDownloadedFile());
        int currentPosition = getItemPosition(arrayList,model);
        if(!model.isDownloadedFile())
            adaptor = new ImageViewerAdaptor(arrayList,this);
        else
            adaptor = new ImageViewerAdaptor(arrayList,this,this);
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
    private ArrayList<TransferModel> getItems(boolean b){
        ArrayList<TransferModel> imageModels = new ArrayList<>();
        File fileDir = AppConstants.STATUS_DIRECTORY;

        if(b){
            fileDir = new File(AppConstants.MY_DIRECTOY_IMAGE);
        }
        if(fileDir.exists()){
                    File[] statusFiles = fileDir.listFiles();


                    if(statusFiles!=null && statusFiles.length>0){
                        Arrays.sort(statusFiles);
                        for(File f: statusFiles){
                            if (AppConstants.checkImage(f)) {
                                TransferModel imageModel = new TransferModel(f.getName(),f.getAbsolutePath());
                                imageModel.setDownloadedFile(b);
                                imageModels.add(imageModel);
                            }
                        }
                    }

        }

        return imageModels;
    }
    public void deleteFile(File file,int position){
        file.delete();
        arrayList.remove(position);

        adaptor = new ImageViewerAdaptor(arrayList,this,this);
        viewPager.setAdapter(adaptor);
        viewPager.setCurrentItem(0);
        adaptor.notifyDataSetChanged();
    }
    public void deleteImage(final int position){
        builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.deletefile_message) .setTitle(R.string.deletefile_title);
        builder.setCancelable(false)
                .setPositiveButton(R.string.yes_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteFile(new File(arrayList.get(position).getFilepath()),position);
                    }
                }).setNegativeButton(R.string.not_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}