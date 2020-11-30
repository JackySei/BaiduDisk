package com.example.baidudisk.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baidudisk.R;
import com.example.baidudisk.adapter.FileAdapter;
import com.example.baidudisk.adapter.MyFileAdapter;
import com.example.baidudisk.enity.FileEntity;
import com.example.baidudisk.enity.FileListEntity;
import com.example.baidudisk.intent.NetworkInterfaces;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FileFragment extends Fragment implements View.OnClickListener {
    int mLevel = 0;
    LinearLayout linearLayout;
    private Handler mHandler;
    private List<FileEntity> fileEntityList = new ArrayList<>();
    private List<FileListEntity.ListBean> mFiles = new ArrayList<FileListEntity.ListBean>();
    FileAdapter mAdapter;
    String mCurrPath;
    MyFileAdapter myFileAdapter;
    private TextView mTip;
    Stack<String> mPathStack = new Stack<>();
    RecyclerView mFilesRV;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file, container, false);
        //initFile();
        mHandler = new Handler();
        mFilesRV = view.findViewById(R.id.rv_files);
        mAdapter = new FileAdapter(fileEntityList);
        myFileAdapter = new MyFileAdapter(mFiles);
        mTip = view.findViewById(R.id.mTipTv);
        linearLayout = view.findViewById(R.id.ll_1);
        linearLayout.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mFilesRV.setLayoutManager(layoutManager);
        mFilesRV.setAdapter(mAdapter);
        showExternalFiles();
        requestPermissions();
        mAdapter.setListener(new FileAdapter.setOnClickListener() {
            @Override
            public void onClick(int position) {
                if (getActivity() != null && fileEntityList.get(position).type != R.mipmap.file) {
                    mLevel++;
                    mCurrPath = mCurrPath + "/" + fileEntityList.get(position).name;
                    showSubFiles();
                }
            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        /*view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()== KeyEvent.ACTION_DOWN&&keyCode==KeyEvent.KEYCODE_BACK&&mLevel>0){
                    File file =new File(mCurrPath).getParentFile();
                    if(file!=null){
                        mCurrPath=file.getAbsolutePath();
                        mLevel--;
                        showSubFiles();
                        return true;
                    }
                }
                return false;
            }
        });*/


        myFileAdapter.setListener(new MyFileAdapter.setOnClickListener() {
            @Override
            public void onClick(int position) {
                if (mFiles.get(position).getIsdir() == 1) {
                    forward(mFiles.get(position).getPath());
                }
            }
        });
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (!mPathStack.empty()) {
                        back();
                        return true;
                    }
                }
                return false;
            }
        });
        getFileList("/");
        return view;
    }

    /**
     * 获取权限
     */
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && getActivity() != null) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //获取权限后的操作
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }

    }
    /*private void initFile() {
         for (int i=0;i<20;i++)
         {
             fileEntityList.add(new FileEntity(R.mipmap.ic_gc_main_empty_folder,"新建文件夹"+i,"2020-10-01 00:00"));
         }


    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_1:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
        }
    }

    private void showExternalFiles() {
        mCurrPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        showSubFiles();
    }

    private void showSubFiles() {
        File file = new File(mCurrPath);
        if (file.exists() && file.isDirectory()) {
            File[] sub = file.listFiles();
            if (sub != null) {
                fileEntityList.clear();
                for (File f : sub) {
                    fileEntityList.add(new FileEntity(f.isDirectory() ? R.mipmap.ic_gc_main_empty_folder : R.mipmap.file, f.getName(), simpleDateFormat.format(new Date(f.lastModified()))));
                }
            } else {
                fileEntityList.clear();
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    private void showLoading() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mFilesRV.setVisibility(View.GONE);
                mTip.setText("正在加载.......");
                mTip.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showResult() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mFilesRV.setVisibility(View.VISIBLE);
                mTip.setVisibility(View.INVISIBLE);
                myFileAdapter.notifyDataSetChanged();
            }
        });
    }

    private void showError() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mFilesRV.setVisibility(View.GONE);
                mTip.setVisibility(View.VISIBLE);
                mTip.setText("获取文件失败");
            }
        });
    }

    private void getFileList(String path) {
        showLoading();
        new NetworkInterfaces().getFileListALL(path, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                showError();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                FileListEntity entity = new Gson().fromJson(response.body().string(), FileListEntity.class);
                if (entity.getErrno() == 0) {
                    showFileList(entity.getList());
                } else {
                    showError();
                    System.out.println(entity.getErrmsg());
                }
            }
        });
    }

    private void showFileList(List<FileListEntity.ListBean> beans) {
        mFiles.clear();
        mFiles.addAll(beans);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mFilesRV.setAdapter(myFileAdapter);
                myFileAdapter.notifyDataSetChanged();
                if (mFiles.isEmpty()) {
                    mTip.setText("没有文件");
                    Toast.makeText(getContext(), "没有文件！", Toast.LENGTH_SHORT).show();
                } else {
                    showResult();
                }

            }
        });
    }

    private void forward(String path) {
        mCurrPath = path;
        mPathStack.push(path);
        getFileList(mCurrPath);
    }

    private void back() {
        mPathStack.pop();
        if (!mPathStack.empty()) {
            mCurrPath = mPathStack.peek();
            mPathStack.pop();
        } else {
            mCurrPath = "/";
        }
        getFileList(mCurrPath);
    }

}