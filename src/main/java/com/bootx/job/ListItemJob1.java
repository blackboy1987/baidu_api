package com.bootx.job;

import com.bootx.pojo.FileListPojo;
import com.bootx.service.BaiDuAccessTokenService;
import com.bootx.service.FileListService;
import com.bootx.util.BaiDuUtils;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
//@Component
public class ListItemJob1 {

    @Resource
    private BaiDuAccessTokenService baiDuAccessTokenService;

    @Resource
    private FileListService fileListService;

    @Resource
    private JdbcTemplate jdbcTemplate;


    //@Scheduled(fixedRate = 1000*60*60*20)
    public void run0() {
        // 从网盘里面拉取文件
        String token = baiDuAccessTokenService.getToken();
        FileListPojo list = BaiDuUtils.fileList(token, "/shortVideo",0);
        if (!list.getList().isEmpty()) {
            fileListService.createBatch(list.getList(),null);
        }
    }

    @Scheduled(fixedRate = 1000*60*60*20)
    public void run1() {
        // 从网盘里面拉取文件
        String token = baiDuAccessTokenService.getToken();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select path,id from filelist where category=6 and grade=11");
        for (Map<String, Object> map : maps) {
            String path = map.get("path")+"";
            Long id = Long.valueOf(map.get("id") + "");
            FileListPojo list = BaiDuUtils.fileList(token, path,0);
            if (!list.getList().isEmpty()) {
                fileListService.createBatch(list.getList(),fileListService.find(id));
            }
        }
    }
}
