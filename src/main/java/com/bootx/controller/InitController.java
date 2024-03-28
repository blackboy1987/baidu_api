package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.service.BaiDuAccessTokenService;
import com.bootx.util.BaiDuUtils;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@RestController
@RequestMapping("/init")
public class InitController {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private BaiDuAccessTokenService baiDuAccessTokenService;

    @GetMapping("/1")
    public Result init() throws InterruptedException {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select path,fileName from filelist where fileName like '%劇場%' and fileName!='劇場'");
        for (Map<String, Object> item : maps) {
            BaiDuUtils.move(baiDuAccessTokenService.getToken(),item.get("path")+"","/shortVideo/神反转热门短剧/剧场",item.get("fileName")+"");
            Thread.sleep(300);
        }



        return Result.success();
    }

}
