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


    //@Scheduled(fixedRate = 1000*60*60*20)
    public void run() {
        // 从网盘里面拉取文件
        String token = baiDuAccessTokenService.getToken();
        FileListPojo list = BaiDuUtils.fileList(token, "/",0);
        if (!list.getList().isEmpty()) {
            for (FileListPojo.ListDTO listDTO : list.getList()) {
                check(token,listDTO,null);
            }
        }
    }

    //@Scheduled(fixedRate = 1000*60*60*20)
    public void run0() {
        // 从网盘里面拉取文件
        String token = baiDuAccessTokenService.getToken();
        FileListPojo list = BaiDuUtils.fileList(token, "/",0);
        fileListService.createBatch(list.getList(),null);
    }

    @Scheduled(fixedRate = 1000*60*60*24*3)
    public void run1() {
        jdbcTemplate.update("truncate filelist;");
        update(null);
        for (int i = 0; i < 20; i++) {
            update(i);
        }
    }

    @Scheduled(fixedRate = 10)
    public void fileMate() {
        String token = baiDuAccessTokenService.getToken();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select fsId,id from filelist where category=1 and size is null ORDER BY RAND() LIMIT 10");
        maps.forEach(item->{
            FileMetasPojo filemetas = BaiDuUtils.filemetas(token, item.get("fsId") + "");
            if(filemetas.getList().size()==1){
                FileMetasPojo.ListBean listBean = filemetas.getList().get(0);
                Integer duration = listBean.getDuration();
                Long size = listBean.getSize();
                FileList fileList = fileListService.findByFsId(Long.valueOf(item.get("fsId")+""));
                fileList.setDuration(duration);
                fileList.setSize(size);
                fileListService.update(fileList);
            }
        });
    }
    @Scheduled(fixedRate = 10)
    public void rename() {
        String code = redisService.get("code");
        String token = baiDuAccessTokenService.getToken();
        if(StringUtils.isNotBlank(code)){
            String[] codes = code.split("_");
            if(codes.length>=1){
                List<Map<String, Object>> maps = jdbcTemplate.queryForList("select fileName,path,fsId,id from filelist where fileName like '%"+codes[0]+"%' limit 10");
                maps.forEach(item->{
                    String path = item.get("path")+"";
                    String newName = item.get("fileName")+"";
                    if(codes.length==1){
                        newName = newName.replaceAll(codes[0], "");
                    }else{
                        newName = newName.replaceAll(codes[0], codes[1]);
                    }
                    BaiDuUtils.rename(token,path,newName);
                    System.out.println("开始重命名数据库");
                    FileMetasPojo filemetas = BaiDuUtils.filemetas(token, item.get("fsId") + "");
                    if(filemetas.getList().size()==1){
                        FileList fileList = fileListService.findByFsId(Long.valueOf(item.get("fsId")+""));
                        fileList.setPath(filemetas.getList().get(0).getPath());
                        fileList.setFileName(filemetas.getList().get(0).getFilename());
                        fileListService.update(fileList);
                    }
                });
            }
        }else{
            System.out.println("code is empty");
        }
    }


    private void update(Integer grade){
        // 从网盘里面拉取文件
        String token = baiDuAccessTokenService.getToken();
        if(grade==null){
            FileListPojo list = BaiDuUtils.fileList(token, "/shortVideo",0);
            fileListService.createBatch(list.getList(),null);
        }else{
            List<Map<String, Object>> maps = jdbcTemplate.queryForList("select path,id from filelist where grade=? and category=6",grade);
            maps.forEach(map -> {
                String path = (String) map.get("path");
                FileListPojo list = BaiDuUtils.fileList(token, path,0);
                fileListService.createBatch(list.getList(),fileListService.find(Long.valueOf(map.get("id")+"")));
            });
        }
    }

    private void check(String token,FileListPojo.ListDTO listDTO,FileList parent) {
        FileList byFsId = fileListService.findByFsId(listDTO.getFsId());
        // 服务器修改时间
        Long serverMtime = listDTO.getServerMtime();
        Boolean flag = false;
        if(byFsId == null){
            // 需要保存
            FileList fileList = fileListService.create(listDTO,parent);
            parent = fileList;
            //fileListService.create(fileList.getPath());
            flag = true;
        }else if(!Objects.equals(byFsId.getServerMTime(), serverMtime)){
            flag = true;
        }
        if(flag && listDTO.getCategory()==6){
            FileListPojo list = BaiDuUtils.fileList(token, listDTO.getPath(),null);
            if (!list.getList().isEmpty()) {
                for (FileListPojo.ListDTO child : list.getList()) {
                    check(token,child,parent);
                }
            }
        }
    }
}
