package com.example.baidudisk.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baidudisk.R;


import java.util.List;
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    public interface Click{
        void Delete(int position);
        void AddText(int position);
    }
    Click click;
    public void setClick(Click click) {
        this.click = click;
    }

    private List<String> historyList;
    public SearchAdapter(List<String> historyList){
        this.historyList=historyList;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton imageDelete;
        ImageView imageTime;
        TextView textName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageDelete=itemView.findViewById(R.id.delete);
            imageTime =itemView.findViewById(R.id.re_time);
            textName=itemView.findViewById(R.id.search_name);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);
       return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String str =historyList.get(position);
        holder.textName.setText(str);
        holder.imageTime.setImageResource(R.mipmap.time);
        holder.imageDelete.setImageResource(R.mipmap.deleteall);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.AddText(position);
            }
        });
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             click.Delete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}
