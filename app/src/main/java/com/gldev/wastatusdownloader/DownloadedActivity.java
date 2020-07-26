package com.gldev.wastatusdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;

import com.gldev.wastatusdownloader.adaptors.DownloadTabPageAdaptor;
import com.gldev.wastatusdownloader.adaptors.TabPagerAdaptor;
import com.gldev.wastatusdownloader.utils.AppConstants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownloadedActivity extends AppCompatActivity {
    @BindView(R.id.downloadma_toolbar)
    Toolbar toolbar;
    @BindView(R.id.downloadappbarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.downloadtabsLayout)
    TabLayout tabLayout;

    @BindView(R.id.downloadviewPager)
    ViewPager viewPager;
    @BindView(R.id.download_bannerAd)
    AdView adView;
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloaded);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        DownloadTabPageAdaptor tabAdaptor = new DownloadTabPageAdaptor(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(tabAdaptor);
        tabLayout.setupWithViewPager(viewPager);
        //loadAd(adView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        endAds();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAds();
    }

    private void startAds(){
        adRunnable.run();
    }
    private void endAds(){
        handler.removeCallbacks(adRunnable);
    }
    private Runnable adRunnable =  new Runnable() {
        @Override
        public void run() {
            loadAd(adView);
            handler.postDelayed(this, AppConstants.ADREQUESTTIME);
        }
    };
    private void loadAd(AdView adView){
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }
}