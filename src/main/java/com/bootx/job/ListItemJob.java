package com.bootx.job;

import com.bootx.entity.FileList;
import com.bootx.pojo.FileListPojo;
import com.bootx.pojo.FileMetasPojo;
import com.bootx.service.BaiDuAccessTokenService;
import com.bootx.service.FileListService;
import com.bootx.service.RedisService;
import com.bootx.util.BaiDuUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author black
 */
@Component
public class ListItemJob {

    @Resource
    private BaiDuAccessTokenService baiDuAccessTokenService;

    @Resource
    private FileListService fileListService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private RedisService redisService;

    private ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();


    //@Scheduled(fixedRate = 1000*60*60*20)
    public void run() {
        // 从网盘里面拉取文件
        String token = baiDuAccessTokenService.getToken();
        FileListPojo list = BaiDuUtils.fileList(token, "/", 0, null);
        if (!list.getList().isEmpty()) {
            for (FileListPojo.ListDTO listDTO : list.getList()) {
                check(token, listDTO, null);
            }
        }
    }

    //@Scheduled(fixedRate = 1000*60*60*20)
    public void run0() {
        // 从网盘里面拉取文件
        String token = baiDuAccessTokenService.getToken();
        FileListPojo list = BaiDuUtils.fileList(token, "/", 0, null);
        fileListService.createBatch(list.getList(), null);
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24 * 3)
    public void run1() {
        jdbcTemplate.update("truncate filelist;");
        update(null);
       /* for (int i = 0; i < 20; i++) {
            update(i);
        }*/
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void move() throws InterruptedException {
        String token = baiDuAccessTokenService.getToken();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select path,fileName from filelist where grade=11 and path like '/shortVideo/上千部短剧/月/日/重生嫡妃/368、重生嫡妃不好惹/368、重生嫡妃不好惹/我治好了新婚老公的绝症/重生/网剧大全/狂野小农民/01-短剧143集/%';");
        for (Map<String, Object> map : maps) {
            String path = map.get("path") + "";
            String dest = "/shortVideo/上千部短剧/月/日/狂野小农民";
            BaiDuUtils.move(token, path, dest, map.get("fileName") + "");
            Thread.sleep(200);
        }
    }

    @Scheduled(fixedRate = 2)
    public void fileMate() {
        String token = baiDuAccessTokenService.getToken();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select fsId,id from filelist where category=1 and size is null ORDER BY RAND() LIMIT 100");
        if (!maps.isEmpty()) {
            long start = System.currentTimeMillis();
            List<String> fsIds = maps.stream().map(item -> item.get("fsId") + "").toList();
            FileMetasPojo filemetas = BaiDuUtils.filemetas(token, StringUtils.join(fsIds, ","));
            System.out.println(System.currentTimeMillis() - start);
            executorService.submit(() -> {
                fileListService.batchUpdate(filemetas.getList());
            });
        }
    }

    //@Scheduled(fixedRate = 10)
    public void rename() {
        String code = redisService.get("code");
        String token = baiDuAccessTokenService.getToken();
        if (StringUtils.isNotBlank(code)) {
            String[] codes = code.split("_");
            if (codes.length >= 1) {
                List<Map<String, Object>> maps = jdbcTemplate.queryForList("select fileName,path,fsId,id from filelist where fileName like '%" + codes[0] + "%' limit 10");
                maps.forEach(item -> {
                    String path = item.get("path") + "";
                    String newName = item.get("fileName") + "";
                    if (codes.length == 1) {
                        newName = newName.replaceAll(codes[0], "");
                    } else {
                        newName = newName.replaceAll(codes[0], codes[1]);
                    }
                    BaiDuUtils.rename(token, path, newName);
                    System.out.println("开始重命名数据库");
                    FileMetasPojo filemetas = BaiDuUtils.filemetas(token, item.get("fsId") + "");
                    if (filemetas.getList().size() == 1) {
                        FileList fileList = fileListService.findByFsId(Long.valueOf(item.get("fsId") + ""));
                        fileList.setPath(filemetas.getList().get(0).getPath());
                        fileList.setFileName(filemetas.getList().get(0).getFilename());
                        fileListService.update(fileList);
                    }
                });
            }
        } else {
            System.out.println("code is empty");
        }
    }


    private void update(Integer grade) {
        // 从网盘里面拉取文件
        String token = baiDuAccessTokenService.getToken();
        if (grade == null) {
            for (int i = 1; i <= 3; i++) {
                FileListPojo list = BaiDuUtils.fileList(token, "/shortVideo/2022/demo", 0, i);
                fileListService.createBatch(list.getList(), null);
            }
        } else {
            List<Map<String, Object>> maps = jdbcTemplate.queryForList("select path,id from filelist where grade=? and category=6", grade);
            maps.forEach(map -> {
                String path = (String) map.get("path");
                FileListPojo list = BaiDuUtils.fileList(token, path, 0, null);
                fileListService.createBatch(list.getList(), fileListService.find(Long.valueOf(map.get("id") + "")));
            });
        }
    }

    private void check(String token, FileListPojo.ListDTO listDTO, FileList parent) {
        FileList byFsId = fileListService.findByFsId(listDTO.getFsId());
        // 服务器修改时间
        Long serverMtime = listDTO.getServerMtime();
        Boolean flag = false;
        if (byFsId == null) {
            // 需要保存
            FileList fileList = fileListService.create(listDTO, parent);
            parent = fileList;
            //fileListService.create(fileList.getPath());
            flag = true;
        } else if (!Objects.equals(byFsId.getServerMTime(), serverMtime)) {
            flag = true;
        }
        if (flag && listDTO.getCategory() == 6) {
            FileListPojo list = BaiDuUtils.fileList(token, listDTO.getPath(), null, null);
            if (!list.getList().isEmpty()) {
                for (FileListPojo.ListDTO child : list.getList()) {
                    check(token, child, parent);
                }
            }
        }
    }
}
