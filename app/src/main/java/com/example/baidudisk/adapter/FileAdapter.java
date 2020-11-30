package com.example.baidudisk.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baidudisk.enity.FileEntity;
import com.example.baidudisk.R;

import java.util.List;
public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    public interface setOnClickListener {
        void onClick(int position);
    }

    private List<FileEntity> mFileList;
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

    public FileAdapter(List<FileEntity> filelist) {
        mFileList = filelist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        FileEntity fileEntity = mFileList.get(position);
        holder.mFileTypePic.setImageResource(fileEntity.getType());
        holder.mFileName.setText(fileEntity.getName());
        holder.mFileCreationDate.setText(fileEntity.getDate());
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

//    public FileAdapter(List<ProFileFragment.FileEntity> entities, FragmentActivity activity) {
//    }
//
//    private interface OnClickListener
//    {
//        void onClick(int position);
//        void setOnClickListener(OnClickListener a);
//    }
//    private List<FileFragment.FileEntity> mFileEntities;
//    private Context mContext;
//    private OnClickListener mListener;
//
//
//
//    public FileAdapter(List<FileFragment.FileEntity> entities, Context context)
//    {
//        this.mFileEntities=entities;
//        this.mContext=context;
//    }
//
//    static class FileViewHolder extends RecyclerView.ViewHolder {
//        private ImageView mFileTypePic;
//        private TextView mFileName;
//        private TextView mFileCreationDate;
//        private LinearLayout mRootLl;
//        public FileViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mFileTypePic=itemView.findViewById(R.id.re_pic);
//            mFileName=itemView.findViewById(R.id.re_name);
//            mFileCreationDate=itemView.findViewById(R.id.re_creation_time);
//            mRootLl=itemView.findViewById(R.id.ll_mRootLl);
//        }
//        public void setData(FileFragment.FileEntity entity)
//        {
//            mFileName.setText(entity.getName());
//            mFileCreationDate.setText(entity.getDate());
//        }
//
//    }
//    @NonNull
//    @Override
//    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new FileViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_file,parent,false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull FileViewHolder holder, final int position) {
//        holder.setData(mFileEntities.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return mFileEntities.size();
//    }



