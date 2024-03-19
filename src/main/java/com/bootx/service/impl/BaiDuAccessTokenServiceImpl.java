package com.bootx.service.impl;

import com.bootx.dao.BaiDuAccessTokenDao;
import com.bootx.entity.BaiDuAccessToken;
import com.bootx.service.BaiDuAccessTokenService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author black
 */
@Service
public class BaiDuAccessTokenServiceImpl extends BaseServiceImpl<BaiDuAccessToken,Long> implements BaiDuAccessTokenService {

    @Resource
    private BaiDuAccessTokenDao baiDuAccessTokenDao;

    @Override
    public String getToken() {
        return baiDuAccessTokenDao.getToken();
    }
}
