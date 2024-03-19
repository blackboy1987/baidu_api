package com.bootx.job;

import com.bootx.entity.BaiDuAccessToken;
import com.bootx.service.BaiDuAccessTokenService;
import com.bootx.util.BaiDuUtils;
import com.bootx.util.DateUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @author black
 */
//@Component
public class BaiDuJob {

    @Resource
    private BaiDuAccessTokenService baiDuAccessTokenService;

    @Scheduled(fixedRate = 1000*60*60*24*20)
    public void refreshToken(){
        List<BaiDuAccessToken> all = baiDuAccessTokenService.findAll();
        BaiDuAccessToken baiDuAccessToken1 = all.get(all.size() - 1);
        com.bootx.pojo.BaiDuAccessToken token = BaiDuUtils.refreshToken(baiDuAccessToken1.getRefreshToken());
        if(StringUtils.isNotBlank(token.getAccessToken())){
            BaiDuAccessToken baiDuAccessToken = new BaiDuAccessToken();
            BeanUtils.copyProperties(token,baiDuAccessToken);
            baiDuAccessToken.setExpiresDate(DateUtils.getNextSecond(baiDuAccessToken.getExpiresIn()));
            baiDuAccessTokenService.save(baiDuAccessToken);
        }
    }

}
