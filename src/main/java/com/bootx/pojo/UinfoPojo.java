package com.bootx.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UinfoPojo extends BaseResponse {

    /**
     *头像地址
     */
    @JsonProperty("avatar_url")
    private String avatarUrl;

    /**
     *百度账号
     */
    @JsonProperty("baidu_name")
    private String baiduName;



    /**
     *网盘账号
     */
    @JsonProperty("netdisk_name")
    private String netdiskName;

    /**
     *用户ID
     */
    private Long uk;

    /**
     *会员类型，0普通用户、1普通会员、2超级会员
     */
    @JsonProperty("vip_type")
    private Integer vipType;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBaiduName() {
        return baiduName;
    }

    public void setBaiduName(String baiduName) {
        this.baiduName = baiduName;
    }

    public String getNetdiskName() {
        return netdiskName;
    }

    public void setNetdiskName(String netdiskName) {
        this.netdiskName = netdiskName;
    }

    public Long getUk() {
        return uk;
    }

    public void setUk(Long uk) {
        this.uk = uk;
    }

    public Integer getVipType() {
        return vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }
}
