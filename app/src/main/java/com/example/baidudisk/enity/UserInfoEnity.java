package com.example.baidudisk.enity;

public class UserInfoEnity {


    /**来自百度官方的示例
     * avatar_url : https://dss0.bdstatic.com/7Ls0a8Sm1A5BphGlnYG/sys/portrait/item/netdisk.1.3d20c095.phlucxvny00WCx9W4kLifw.jpg
     * baidu_name : 啊呀呀
     * errmsg : succ
     * errno : 0
     * netdisk_name : abcdefff
     * request_id : 674030589892501935
     * uk : 208281036
     * vip_type : 0
     */

    private String avatar_url;
    private String baidu_name;
    private String errmsg;
    private int errno;
    private String netdisk_name;
    private String request_id;
    private double uk;
    private int vip_type;

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getBaidu_name() {
        return baidu_name;
    }

    public void setBaidu_name(String baidu_name) {
        this.baidu_name = baidu_name;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getNetdisk_name() {
        return netdisk_name;
    }

    public void setNetdisk_name(String netdisk_name) {
        this.netdisk_name = netdisk_name;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public double getUk() {
        return uk;
    }

    public void setUk(int uk) {
        this.uk = uk;
    }

    public int getVip_type() {
        return vip_type;
    }

    public void setVip_type(int vip_type) {
        this.vip_type = vip_type;
    }
}
