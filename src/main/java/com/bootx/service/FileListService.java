package com.bootx.service;

import com.bootx.entity.FileList;
import com.bootx.pojo.FileListPojo;

import java.util.List;

/**
 * @author black
 */
public interface FileListService extends BaseService<FileList,Long>{

    /**
     * 根据目录来获取目录下的文件，并保存到数据库
     * @param path
     */
    void create(String path);

    FileList findByFsId(Long fsId);

    FileList findByPath(String path);

    FileList create(FileListPojo.ListDTO listDTO,FileList parent);

    void createBatch(List<FileListPojo.ListDTO> list,FileList parent);
}
