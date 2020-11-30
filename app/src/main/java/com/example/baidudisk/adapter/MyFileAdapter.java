package com.example.baidudisk.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baidudisk.R;
import com.example.baidudisk.enity.FileEntity;
import com.example.baidudisk.enity.FileListEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyFileAdapter extends RecyclerView.Adapter<MyFileAdapter.ViewHolder> {

    public interface setOnClickListener {
        void onClick(int position);
    }

    private List<FileListEntity.ListBean> mFileList;
    public setOnClickListener myClickListener;

    public void setListener(setOnClickListener s) {
        myClickListener = s;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mFileTypePic;
        TextView mFileName;
        TextView mFileCreationDate;
        LinearLayout mRootLl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFileTypePic = itemView.findViewById(R.id.re_pic);
            mFileName = itemView.findViewById(R.id.re_name);
            mFileCreationDate = itemView.findViewById(R.id.re_creation_time);
            mRootLl = itemView.findViewById(R.id.ll_mRootLl);
        }
    }

    public MyFileAdapter(List<FileListEntity.ListBean> filelist) {
        mFileList = filelist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        FileListEntity.ListBean fileEntity = mFileList.get(position);
        holder.mFileTypePic.setImageResource(R.mipmap.ic_gc_main_empty_folder);
        holder.mFileName.setText(fileEntity.getServer_filename());
        holder.mFileCreationDate.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(fileEntity.getServer_ctime())));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickListener.onClick(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mFileList.size();
    }
}