package com.example.baidudisk.intent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetworkInterfaces {
    private static final int CONNECT_TIME_OUT=8;
    private OkHttpClient mClient;
    private final static String ACCESS_TOKEN="123.a7997fe2a1775e866539b7236fd2c051.YGTUTkyu57YOSvbupacce5RktcGUwQ1ycrb3hM-.i_qEVg";
    //private final static String ACCESS_TOKEN="123.57af2d8462e548f7d31f4ed5fddc5680.YQgeEvkf6RALDctiXZ6j0h_3CkiQW5XKc2DZnew.o4KJtA";
    private final static String HOST="https://pan.baidu.com";
    private final static String SYNC_INFO="/rest/2.0/xpan/nas?method=uinfo";
    private final static String SPACE="/api/quota";
    private final static String FILE_LIST_ALL="/rest/2.0/xpan/multimedia?method=listall";

    private OkHttpClient getOkHttpClient(){
        if (mClient==null){
            mClient=new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    .build();
        }
        return mClient;
    }

    public void sendGetHttpRequest(String url, Map<String,String> params, Callback callback){
        HttpUrl.Builder builder=HttpUrl.get(url).newBuilder();
        for (String key :params.keySet()){
            builder.addQueryParameter(key,params.get(key));
        }
        Request request=new Request.Builder()
                .url(builder.build())
                .get()
                .build();
        getOkHttpClient().newCall(request).enqueue(callback);
    }

//获取用户信息
    public void syncUserInfo(Callback callback){
        Map<String,String> param =new HashMap<>();
        param.put("access_token",ACCESS_TOKEN);
        sendGetHttpRequest(HOST+SYNC_INFO,param,callback);
    }
//获取网盘容量
    public void syncUserSpace(Callback callback){
        Map<String,String> param =new HashMap<>();
        param.put("access_token",ACCESS_TOKEN);
        sendGetHttpRequest(HOST+SPACE,param,callback);
    }

//获取文件列表
     public void getFileListALL(String path,Callback callback){
     Map<String,String> param =new HashMap<>();
     param.put("access_token",ACCESS_TOKEN);
     param.put("path",path);
     sendGetHttpRequest(HOST+FILE_LIST_ALL,param,callback);
}

}
