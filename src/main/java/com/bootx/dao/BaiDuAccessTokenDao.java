package com.bootx.dao;

import com.bootx.entity.BaiDuAccessToken;

/**
 * @author black
 */
public interface BaiDuAccessTokenDao extends BaseDao<BaiDuAccessToken,Long>{
    String getToken();
}
