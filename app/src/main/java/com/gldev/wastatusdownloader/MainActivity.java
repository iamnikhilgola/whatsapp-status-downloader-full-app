package com.gldev.wastatusdownloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.gldev.wastatusdownloader.adaptors.TabPagerAdaptor;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ma_toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.tabsLayout)
    TabLayout tabLayout;
    @BindView(R.id.main_bannerAd)
    AdView mainbannerAd;

    private InterstitialAd mInterstitialAd;

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private static final int STORAGE_PERMISSION_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            load();

            }
            private void load(){
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
                {
                    init();
                }
                else{
                    if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)|| shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                        Toast.makeText(this, R.string.app_permission_error,Toast.LENGTH_LONG).show();
                    }
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
                }

            }
    private  void loadInterstetialAd(){
        //Main ad
        //mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-1554837528700151/9044015922");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==STORAGE_PERMISSION_CODE){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                load();
            }
            else{

                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInterstetialAd();
    }

    private void init(){
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        TabPagerAdaptor tabAdaptor = new TabPagerAdaptor(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(tabAdaptor);
        tabLayout.setupWithViewPager(viewPager);
        loadAd(mainbannerAd);

    }
    private void loadAd(AdView adView){
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        loadInterstetialAd();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private void addLaunchDownloadActivity(){
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                launchDownloadedActivity();
            }
        });
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }
        else{
            launchDownloadedActivity();
        }
    }
    private void launchDownloadedActivity(){
        Log.d("MYTAG","LAUNch function");
            Intent intent = new Intent(this,DownloadedActivity.class);
            startActivity(intent);
    }
    private void launchHelpSystem(){

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add: addLaunchDownloadActivity();return true;
            case R.id.action_help: launchHelpSystem();return true;
            default: return super.onOptionsItemSelected(item);
        }

    }
}