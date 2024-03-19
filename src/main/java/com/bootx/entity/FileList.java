package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class FileList extends OrderedEntity<Long>{

    /**
     * 树路径分隔符
     */
    public static final String TREE_PATH_SEPARATOR = ",";

    @Column(nullable = false,updatable = false,unique = true)
    private Long fsId;

    private Integer category;

    @Column(nullable = false,updatable = false,unique = true)
    private String path;

    private String fileName;

    private Long serverMTime;

    private Long serverCTime;

    private Long localMTime;

    private Long localCTime;

    /**
     * 0：不需要更新
     * 1：需要更新
     * 2：需要删除
     */
    @NotNull
    private Integer status;

    /**
     * 上级分类
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private FileList parent;

    /**
     * 下级分类
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OrderBy("order asc")
    @JsonIgnore
    private Set<FileList> children = new HashSet<>();

    /**
     * 树路径
     */
    @Column(nullable = false)
    private String treePath;

    /**
     * 层级
     */
    @Column(nullable = false)
    private Integer grade;

    private String playUrl;

    @Column(length = 2000)
    private String cover;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getFsId() {
        return fsId;
    }

    public void setFsId(Long fsId) {
        this.fsId = fsId;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getServerMTime() {
        return serverMTime;
    }

    public void setServerMTime(Long serverMTime) {
        this.serverMTime = serverMTime;
    }

    public Long getServerCTime() {
        return serverCTime;
    }

    public void setServerCTime(Long serverCTime) {
        this.serverCTime = serverCTime;
    }

    public Long getLocalMTime() {
        return localMTime;
    }

    public void setLocalMTime(Long localMTime) {
        this.localMTime = localMTime;
    }

    public Long getLocalCTime() {
        return localCTime;
    }

    public void setLocalCTime(Long localCTime) {
        this.localCTime = localCTime;
    }

    public FileList getParent() {
        return parent;
    }

    public void setParent(FileList parent) {
        this.parent = parent;
    }

    public Set<FileList> getChildren() {
        return children;
    }

    public void setChildren(Set<FileList> children) {
        this.children = children;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取所有上级分类ID
     *
     * @return 所有上级分类ID
     */
    @Transient
    public Long[] getParentIds() {
        String[] parentIds = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
        Long[] result = new Long[parentIds.length];
        for (int i = 0; i < parentIds.length; i++) {
            result[i] = Long.valueOf(parentIds[i]);
        }
        return result;
    }

    /**
     * 获取所有上级分类
     *
     * @return 所有上级分类
     */
    @Transient
    public List<FileList> getParents() {
        List<FileList> parents = new ArrayList<>();
        recursiveParents(parents, this);
        return parents;
    }

    /**
     * 递归上级分类
     *
     * @param parents
     *            上级分类
     * @param fileList
     *            商品分类
     */
    private void recursiveParents(List<FileList> parents, FileList fileList) {
        if (fileList == null) {
            return;
        }
        FileList parent = fileList.getParent();
        if (parent != null) {
            parents.add(0, parent);
            recursiveParents(parents, parent);
        }
    }
}
