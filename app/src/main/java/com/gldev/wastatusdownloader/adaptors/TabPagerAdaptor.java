package com.gldev.wastatusdownloader.adaptors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gldev.wastatusdownloader.fragments.ImageFragment;
import com.gldev.wastatusdownloader.fragments.VideoFragment;

public class TabPagerAdaptor extends FragmentPagerAdapter {
    private VideoFragment videoFragment;
    private ImageFragment imageFragment;

    public TabPagerAdaptor(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        videoFragment = new VideoFragment();
        imageFragment = new ImageFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return videoFragment;
            case 1: return imageFragment;
            default: return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Videos";
            case 1: return "Images";
            default: return "";
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
