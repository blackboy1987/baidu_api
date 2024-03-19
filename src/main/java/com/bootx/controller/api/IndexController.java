
package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.entity.FileList;
import com.bootx.service.BaiDuAccessTokenService;
import com.bootx.service.FileListService;
import com.bootx.util.BaiDuUtils;
import com.bootx.util.FileUploadUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author black
 */
@RestController("ApiIndexController")
@RequestMapping("/api")
public class IndexController extends BaseController {

    @Resource
    private FileListService fileListService;

    @Resource
    private BaiDuAccessTokenService baiDuAccessTokenService;

    @PostMapping("/category")
    public Result category(){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select fileName name,fsId id from filelist where grade=0");
        return Result.success(maps);
    }

    @PostMapping("/list")
    public Result list(Long id){
        FileList fileList = fileListService.findByFsId(id);
        if(fileList==null || fileList.getChildren().isEmpty() || fileList.getCategory()!=6){
            return Result.success(Collections.emptyList());
        }
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select fileName name,fsId id,filelist.category,treePath from filelist where grade=3 and (category=6 or category=1) and treePath like ? order by serverMTime asc ;","%,"+fileList.getId()+",%");
        return Result.success(maps);
    }
    @PostMapping("/items")
    public Result items(Long id){
        FileList fileList = fileListService.findByFsId(id);
        if(fileList==null || fileList.getChildren().isEmpty() || fileList.getCategory()!=6){
            return Result.success(Collections.emptyList());
        }
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select fsId id from filelist where category=1 and treePath like ? order by orders asc ;",fileList.getTreePath()+fileList.getId()+",%");
        return Result.success(maps);
    }

    @PostMapping("/play")
    public Result play(Long id) throws InterruptedException {
        String s = redisService.get("play:" + id);
        if(StringUtils.isNotBlank(s)){
            return Result.success(s);
        }
        FileList fileList = fileListService.findByFsId(id);
        if(fileList==null || fileList.getCategory()!=1){
            return Result.success("");
        }

        String token = baiDuAccessTokenService.getToken();
        String streaming = BaiDuUtils.streaming(token, fileList.getPath());
        while (!StringUtils.contains(streaming,"#EXT-X-ENDLIST")){
            streaming = BaiDuUtils.streaming(token, fileList.getPath());
            Thread.sleep(1000);
        }
        String path = fileList.getFsId()+".m3u8";
        FileUploadUtils.upload(streaming,path);
        String url = "https://bootx-video.oss-cn-hangzhou.aliyuncs.com/"+path;
        fileList.setPlayUrl(url);
        fileListService.update(fileList);
        redisService.set("play:"+fileList.getFsId(),url,8, TimeUnit.HOURS);
        return Result.success(url);
    }
}