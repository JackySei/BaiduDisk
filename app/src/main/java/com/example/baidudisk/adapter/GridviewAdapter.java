package com.example.baidudisk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baidudisk.R;
import com.example.baidudisk.enity.Icon;

import java.util.List;

public class GridviewAdapter extends ArrayAdapter<Icon> {

    int resourceId;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Icon icon=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView imageView=view.findViewById(R.id.img_icon);
        TextView textView =view.findViewById(R.id.txt_icon);
        imageView.setImageResource(icon.getiId());
        textView.setText(icon.getiName());
        return view;
    }

    public GridviewAdapter(@NonNull Context context, int resource, @NonNull List<Icon> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }
}
