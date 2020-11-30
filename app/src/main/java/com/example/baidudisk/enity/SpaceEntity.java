package com.example.baidudisk.enity;

public class SpaceEntity {

    /**
     * errno : 0
     * total : 2205465706496
     * free : 2205465706496
     * request_id : 4890482559098510375
     * expire : false
     * used : 686653888910
     */

    private int errno;
    private long total;
    private long free;
    private long request_id;
    private boolean expire;
    private long used;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getFree() {
        return free;
    }

    public void setFree(long free) {
        this.free = free;
    }

    public long getRequest_id() {
        return request_id;
    }

    public void setRequest_id(long request_id) {
        this.request_id = request_id;
    }

    public boolean isExpire() {
        return expire;
    }

    public void setExpire(boolean expire) {
        this.expire = expire;
    }

    public long getUsed() {
        return used;
    }

    public void setUsed(long used) {
        this.used = used;
    }
}
