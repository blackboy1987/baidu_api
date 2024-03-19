package com.bootx.dao;

import com.bootx.entity.FileList;

import java.util.List;

public interface FileListDao extends BaseDao<FileList,Long>{

    /**
     * 查找下级商品分类
     *
     * @param fileList
     *            商品分类
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @return 下级商品分类
     */
    List<FileList> findChildren(FileList fileList, boolean recursive, Integer count);

}
