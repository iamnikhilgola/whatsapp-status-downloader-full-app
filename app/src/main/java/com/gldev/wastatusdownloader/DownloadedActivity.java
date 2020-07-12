package com.gldev.wastatusdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.gldev.wastatusdownloader.adaptors.DownloadTabPageAdaptor;
import com.gldev.wastatusdownloader.adaptors.TabPagerAdaptor;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloaded);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        DownloadTabPageAdaptor tabAdaptor = new DownloadTabPageAdaptor(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(tabAdaptor);
        tabLayout.setupWithViewPager(viewPager);

    }
}