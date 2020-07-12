package com.gldev.wastatusdownloader.adaptors;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DownloadedVideoAdaptor extends RecyclerView.Adapter<DownloadedVideoAdaptor.DviewHolder>{
    @NonNull
    @Override
    public DviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DviewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DviewHolder extends RecyclerView.ViewHolder{

        public DviewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
