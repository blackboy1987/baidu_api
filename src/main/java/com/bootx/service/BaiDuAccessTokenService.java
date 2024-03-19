package com.bootx.service;

import com.bootx.entity.BaiDuAccessToken;

/**
 * @author black
 */
public interface BaiDuAccessTokenService extends BaseService<BaiDuAccessToken,Long>{
    String getToken();
}
