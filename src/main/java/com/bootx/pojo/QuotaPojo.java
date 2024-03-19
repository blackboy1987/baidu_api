package com.bootx.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuotaPojo extends BaseResponse{


    /**
     *7天内是否有容量到期
     */
    private Boolean expire;

    /**
     *免费容量，单位B
     */
    private Long free;
    @JsonProperty("is_show_window")
    private Integer isShowWindow;
    private String newno;
    @JsonProperty("recmd_vip")
    private String recmdVip;
    private Integer recyclestatus;
    @JsonProperty("sbox_total")
    private Integer sboxTotal;
    @JsonProperty("sbox_used")
    private Integer sboxUsed;
    @JsonProperty("server_time")
    private Integer serverTime;

    /**
     *总空间大小，单位B
     */
    private Long total;

    /**
     *已使用大小，单位B
     */
    private Long used;

    public Boolean getExpire() {
        return expire;
    }

    public void setExpire(Boolean expire) {
        this.expire = expire;
    }

    public Long getFree() {
        return free;
    }

    public void setFree(Long free) {
        this.free = free;
    }

    public Integer getIsShowWindow() {
        return isShowWindow;
    }

    public void setIsShowWindow(Integer isShowWindow) {
        this.isShowWindow = isShowWindow;
    }

    public String getNewno() {
        return newno;
    }

    public void setNewno(String newno) {
        this.newno = newno;
    }

    public String getRecmdVip() {
        return recmdVip;
    }

    public void setRecmdVip(String recmdVip) {
        this.recmdVip = recmdVip;
    }

    public Integer getRecyclestatus() {
        return recyclestatus;
    }

    public void setRecyclestatus(Integer recyclestatus) {
        this.recyclestatus = recyclestatus;
    }

    public Integer getSboxTotal() {
        return sboxTotal;
    }

    public void setSboxTotal(Integer sboxTotal) {
        this.sboxTotal = sboxTotal;
    }

    public Integer getSboxUsed() {
        return sboxUsed;
    }

    public void setSboxUsed(Integer sboxUsed) {
        this.sboxUsed = sboxUsed;
    }

    public Integer getServerTime() {
        return serverTime;
    }

    public void setServerTime(Integer serverTime) {
        this.serverTime = serverTime;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getUsed() {
        return used;
    }

    public void setUsed(Long used) {
        this.used = used;
    }
}
