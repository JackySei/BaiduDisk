package com.example.baidudisk.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.baidudisk.R;
import com.example.baidudisk.adapter.GridviewAdapter;
import com.example.baidudisk.enity.Icon;
import com.example.baidudisk.enity.SpaceEntity;
import com.example.baidudisk.enity.UserInfoEnity;
import com.example.baidudisk.intent.NetworkInterfaces;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProFileFragment extends Fragment {
    GridView gridView;
    TextView user_id;
    private GridviewAdapter adapter;
    private ArrayList<Icon> Data;
    private Handler mHandler;
    private TextView mSpace;
    private RecyclerView mFilesRV;
    private ProgressBar mProgress;
    ImageView mVip,touxiang;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_me_acitivity,container,false);
        gridView=view.findViewById(R.id.grid_view);
        user_id=view.findViewById(R.id.user_id);
        touxiang=view.findViewById(R.id.touxiang);
        mFilesRV=view.findViewById(R.id.rv_files);
        mProgress=view.findViewById(R.id.pro);
        mSpace=view.findViewById(R.id.mSpace);
        String data=getActivity().getIntent().getStringExtra("str1");
        mVip=view.findViewById(R.id.mVip);
        user_id.setText(data);
        Data=new ArrayList<>();
        mHandler=new Handler();
        Data.add(new Icon(R.mipmap.file_add_btn_file,"上传文档"));
        Data.add(new Icon(R.mipmap.file_add_btn_folder,"新建文件夹"));
        Data.add(new Icon(R.mipmap.file_add_btn_music,"上传音乐"));
        Data.add(new Icon(R.mipmap.file_add_btn_note,"新建笔记"));
        Data.add(new Icon(R.mipmap.file_add_btn_other,"上传其他文件"));
        Data.add(new Icon(R.mipmap.file_add_btn_photo,"上传照片"));
        Data.add(new Icon(R.mipmap.file_add_btn_scan,"扫一扫"));
        Data.add(new Icon(R.mipmap.file_add_btn_video,"上传视频"));
        adapter=new GridviewAdapter(getContext(),R.layout.item_grid_icon,Data);
        gridView.setAdapter(adapter);
        syncUserInfo();
        syncSpace();
        return view;
    }

    private void syncUserInfo(){
        new NetworkInterfaces().syncUserInfo(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(getContext(),"获取用户信息失败！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                UserInfoEnity entity=new Gson().fromJson(response.body().string(),UserInfoEnity.class);
                if(entity.getErrno()==0){
                    showUserInfo(entity);
                }
                else {
                    Toast.makeText(getContext(),"获取失败",Toast.LENGTH_SHORT).show();
                }
            }

        });
        }
    private void showUserInfo(final UserInfoEnity entity) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(ProFileFragment.this)
                        .load(entity.getAvatar_url())
                        .centerCrop()
                        .placeholder(R.mipmap.swan_app_user_portrait_pressed)
                        .apply(RequestOptions.circleCropTransform())
                        .into(touxiang);
                user_id.setText(entity.getBaidu_name());
                switch (entity.getVip_type()){
                    case 0: mVip.setImageResource(R.mipmap.home_identity_common);
                         break;
                    case 1: mVip.setImageResource(R.mipmap.home_identity_member);
                        break;
                    case 2: mVip.setImageResource(R.mipmap.home_identity_super);
                        break;
                }
            }
        });
    }
    public void syncSpace(){
        new NetworkInterfaces().syncUserSpace(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(getContext(),"获取容量失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                SpaceEntity entity=new Gson().fromJson(response.body().string(),SpaceEntity.class);
                if(entity.getErrno()==0){
                    showSpace(entity);
                }
                else {
                    Toast.makeText(getContext(),"获取失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showSpace(final SpaceEntity entity) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                long total=entity.getTotal()/(1024*1024*1024);
                long used =entity.getUsed()/(1024*1024*1024);
                int process=(int)(used*100/total);
                mSpace.setText(used+"GB/"+total+"GB");
                mProgress.setProgress(process);
            }
        });
    }
}

