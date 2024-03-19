package com.bootx.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FileMetasPojo extends BaseResponse{

    private List<ListBean> list = new ArrayList<>();

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class NamesBean {
    }

    public static class ListBean {

        private Integer category;
        private Integer duration;
        private String filename;
        @JsonProperty("fs_id")
        private Long fsId;
        @JsonProperty("isdir")
        private int isDir;
        @JsonProperty("local_ctime")
        private Long localCtime;

        @JsonProperty("local_mtime")
        private Long localMtime;
        private String md5;

        @JsonProperty("oper_id")
        private Long operId;
        private String path;

        @JsonProperty("server_ctime")
        private Long serverCtime;

        @JsonProperty("server_mtime")
        private Long serverMtime;
        private Long size;


        public Integer getCategory() {
            return category;
        }

        public void setCategory(Integer category) {
            this.category = category;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public Long getFsId() {
            return fsId;
        }

        public void setFsId(Long fsId) {
            this.fsId = fsId;
        }

        public int getIsDir() {
            return isDir;
        }

        public void setIsDir(int isDir) {
            this.isDir = isDir;
        }

        public Long getLocalCtime() {
            return localCtime;
        }

        public void setLocalCtime(Long localCtime) {
            this.localCtime = localCtime;
        }

        public Long getLocalMtime() {
            return localMtime;
        }

        public void setLocalMtime(Long localMtime) {
            this.localMtime = localMtime;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public Long getOperId() {
            return operId;
        }

        public void setOperId(Long operId) {
            this.operId = operId;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Long getServerCtime() {
            return serverCtime;
        }

        public void setServerCtime(Long serverCtime) {
            this.serverCtime = serverCtime;
        }

        public Long getServerMtime() {
            return serverMtime;
        }

        public void setServerMtime(Long serverMtime) {
            this.serverMtime = serverMtime;
        }

        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }
    }
}
