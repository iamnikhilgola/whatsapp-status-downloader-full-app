package com.gldev.wastatusdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.gldev.wastatusdownloader.models.VideoModel;
import com.gldev.wastatusdownloader.utils.AppConstants;
import com.gldev.wastatusdownloader.utils.FileOperations;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppPlayerActivity extends AppCompatActivity implements View.OnClickListener{

    private File videoModel;
    private SimpleExoPlayer player;

    @BindView(R.id.videoPlayer)
    PlayerView playerView;
    @BindView(R.id.download_floatbutton)
    FloatingActionButton downloadButton;
    @BindView(R.id.share_floatbutton)
    FloatingActionButton shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_player);
        videoModel = new File(getIntent().getStringExtra("VIDEO"));
        initComponents();
       // initializePlayer();
    }
    private void initComponents(){
        ButterKnife.bind(this);
        shareButton.setOnClickListener(this);
        downloadButton.setOnClickListener(this);
    }
    private void initializePlayer(){
        playVideo(Uri.fromFile(videoModel));
    }
    private void playVideo(Uri uri){
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);

    }
    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, getString(R.string.app_name));
        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }

    private void releasePlayer(){
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer();
        }
    }

    @Override
    protected void onPause() {
        if(Util.SDK_INT<24){
            releasePlayer();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if(Util.SDK_INT>=24){
            releasePlayer();
        }
        super.onStop();
    }
    public void downloadVideo(File model){
        File file  = new File(AppConstants.MY_DIRECTOY_VIDEO);
        if(!file.exists()){
            file.mkdirs();
        }
        File destFile = new File(file+File.separator+model.getName());
        if(destFile.exists()){
            destFile.delete();
        }

        FileOperations fileOperations = new FileOperations();
        try {
            fileOperations.copyFile(model,destFile);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),FileOperations.ERROR_MESSAGE,Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),FileOperations.SUCCESS_MESSAGE,Toast.LENGTH_LONG).show();
        broadcastGallery(destFile);

    }
    private void broadcastGallery(File file){
        Intent intent =new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }

    @Override
    public void onClick(View view) {
       if(view==downloadButton){
           downloadVideo(videoModel);
       }
       if(view==shareButton){
           Intent shareIntent = new Intent();
           shareIntent.setAction(Intent.ACTION_SEND);
           //shareIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
           Uri apkURI = FileProvider.getUriForFile(
                   this,
                   getApplicationContext()
                           .getPackageName() + ".provider", videoModel);
           grantUriPermission(getPackageName(),apkURI, Intent.FLAG_GRANT_READ_URI_PERMISSION);
           //shareIntent.setDataAndType(apkURI, "video/mp4");
           shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


           shareIntent.putExtra(Intent.EXTRA_STREAM, apkURI);
           shareIntent.setType("video/mp4");
           startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));

       }
    }
}