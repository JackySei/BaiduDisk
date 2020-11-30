package com.example.baidudisk.enity;

import com.example.baidudisk.R;

public class SearchEnity {
    int timeicon;
    int deleteicon;
    String filename;
    SearchEnity(String filename){
        timeicon= R.mipmap.time;
        deleteicon=R.mipmap.deleteall;
        this.filename=filename;
    }
    public int getTimeicon() {
        return timeicon;
    }

    public void setTimeicon(int timeicon) {
        this.timeicon = timeicon;
    }

    public int getDeleteicon() {
        return deleteicon;
    }

    public void setDeleteicon(int deleteicon) {
        this.deleteicon = deleteicon;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
