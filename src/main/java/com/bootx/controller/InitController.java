package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.service.BaiDuAccessTokenService;
import com.bootx.util.BaiDuUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select fileName,path from filelist where fileName like '%劇場%' and fileName !='剧场';");
        for (Map<String, Object> item : maps) {
            BaiDuUtils.move(baiDuAccessTokenService.getToken(),item.get("path")+"","/shortVideo/神反转热门短剧/剧场",item.get("fileName")+"");
            Thread.sleep(500);
        }



        return Result.success();
    }

    @GetMapping("/remove")
    public Result remove() throws InterruptedException {
        String token = "121.3b6dd2b52b40b5478767a79f9c5facb6.YQbCWdedA74iNzcQIdvSCOn-p5z1rkROrPzSEYS.DITsEg";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select fileName from (select fileName,count(id) count from filelist where category=6 and grade=3 group by fileName) AS X where X.count>1;");
        for (Map<String, Object> map : maps) {
            List<Map<String, Object>> maps1 = jdbcTemplate.queryForList("select id, path from filelist where fileName=?", map.get("fileName"));
            if(maps1.size()>1){
                List<Map<String, Object>> collect = maps1.stream().filter(item -> StringUtils.indexOf(item.get("path") + "", "2022") > 0).collect(Collectors.toList());
                if(!collect.isEmpty()){
                    List<String> fileList = new ArrayList<>();
                    fileList.add(collect.getFirst().get("path")+"");
                    BaiDuUtils.delete(token,fileList);
                    jdbcTemplate.update("update fileList set status=100 where id=?",collect.getFirst().get("id"));
                }else{
                    List<String> fileList = new ArrayList<>();
                    fileList.add(maps1.getFirst().get("path")+"");
                    BaiDuUtils.delete(token,fileList);
                    jdbcTemplate.update("update fileList set status=100 where id=?",maps1.getFirst().get("id"));
                }
            }
        }
        return Result.success();
    }

    @GetMapping("/rename")
    public Result rename(String keywords) {
        String token = "121.3b6dd2b52b40b5478767a79f9c5facb6.YQbCWdedA74iNzcQIdvSCOn-p5z1rkROrPzSEYS.DITsEg";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select fileName,path from filelist where grade=0");
        for (Map<String, Object> map : maps) {
            String fileName = map.get("fileName")+"";
            String replace = fileName;
            for (int i = 1; i < 3000; i++) {
                if(fileName.indexOf(i+"-")==0){
                    replace = fileName.replace(i+"-","");
                }
            }
            if(!StringUtils.equals(fileName,replace)){
                System.out.println(fileName+":"+replace);
                BaiDuUtils.rename(token,map.get("path")+"",replace);
            }

        }
        return Result.success();
    }

}
