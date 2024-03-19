package com.bootx.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class FileListPojo extends BaseResponse {

    @JsonProperty("guid_info")
    private String guidInfo;
    private List<ListDTO> list = new ArrayList<>();
    private Integer guid;

    public String getGuidInfo() {
        return guidInfo;
    }

    public void setGuidInfo(String guidInfo) {
        this.guidInfo = guidInfo;
    }

    public List<ListDTO> getList() {
        return list;
    }

    public void setList(List<ListDTO> list) {
        this.list = list;
    }

    public Integer getGuid() {
        return guid;
    }

    public void setGuid(Integer guid) {
        this.guid = guid;
    }

    public static class ListDTO {
        /**
         *
         */
        @JsonProperty("tkbind_id")
        private Integer tkbindId;
        /**
         *文件在服务器修改时间
         */
        @JsonProperty("server_mtime")
        private Long serverMtime;
        /**
         *文件类型，1 视频、2 音频、3 图片、4 文档、5 应用、6 其他、7 种子
         */
        private Integer category;
        /**
         *
         */
        @JsonProperty("real_category")
        private String realCategory;
        /**
         *文件在云端的唯一标识ID
         */
        @JsonProperty("fs_id")
        private Long fsId;
        /**
         *该目录是否存在子目录，只有请求参数web=1且该条目为目录时，该字段才存在， 0为存在， 1为不存在
         */
        @JsonProperty("dir_empty")
        private Integer dirEmpty;
        /**
         *
         */
        @JsonProperty("oper_id")
        private Long operId;
        /**
         *文件在服务器创建时间
         */
        @JsonProperty("server_ctime")
        private Long serverCtime;
        /**
         *
         */
        @JsonProperty("extent_tinyint7")
        private Integer extentTinyint7;
        /**
         *
         */
        private Integer wpfile;
        /**
         *
         */
        @JsonProperty("owner_type")
        private Integer ownerType;
        /**
         *文件在客户端修改时间
         */
        @JsonProperty("local_mtime")
        private Long localMtime;
        /**
         *	文件大小，单位B
         */
        private Long size;
        /**
         *是否为目录，0 文件、1 目录
         */
        private Integer isdir;
        /**
         *
         */
        private Integer share;
        /**
         *
         */
        private Integer pl;
        /**
         *
         */
        @JsonProperty("from_type")
        private Integer fromType;
        /**
         *文件在客户端创建时间
         */
        @JsonProperty("local_ctime")
        private Long localCtime;
        /**
         *文件的绝对路径
         */
        private String path;
        /**
         *
         */
        private Integer empty;
        /**
         *
         */
        @JsonProperty("server_atime")
        private Integer serverAtime;
        /**
         *文件名称
         */
        @JsonProperty("server_filename")
        private String serverFilename;
        /**
         *
         */
        @JsonProperty("owner_id")
        private Long ownerId;
        /**
         *
         */
        private Integer unlist;

        /**
         * 云端哈希（非文件真实MD5），只有是文件类型时，该字段才存在
         */
        private String md5;

        /**
         * 	只有请求参数web=1且该条目分类为图片时，该字段才存在，包含三个尺寸的缩略图URL
         */
        private Thumb thumbs;

        private String docpreview;

        private String lodocpreview;

        public Integer getTkbindId() {
            return tkbindId;
        }

        public void setTkbindId(Integer tkbindId) {
            this.tkbindId = tkbindId;
        }

        public Long getServerMtime() {
            return serverMtime;
        }

        public void setServerMtime(Long serverMtime) {
            this.serverMtime = serverMtime;
        }

        public Integer getCategory() {
            return category;
        }

        public void setCategory(Integer category) {
            this.category = category;
        }

        public String getRealCategory() {
            return realCategory;
        }

        public void setRealCategory(String realCategory) {
            this.realCategory = realCategory;
        }

        public Long getFsId() {
            return fsId;
        }

        public void setFsId(Long fsId) {
            this.fsId = fsId;
        }

        public Integer getDirEmpty() {
            return dirEmpty;
        }

        public void setDirEmpty(Integer dirEmpty) {
            this.dirEmpty = dirEmpty;
        }

        public Long getOperId() {
            return operId;
        }

        public void setOperId(Long operId) {
            this.operId = operId;
        }

        public Long getServerCtime() {
            return serverCtime;
        }

        public void setServerCtime(Long serverCtime) {
            this.serverCtime = serverCtime;
        }

        public Integer getExtentTinyint7() {
            return extentTinyint7;
        }

        public void setExtentTinyint7(Integer extentTinyint7) {
            this.extentTinyint7 = extentTinyint7;
        }

        public Integer getWpfile() {
            return wpfile;
        }

        public void setWpfile(Integer wpfile) {
            this.wpfile = wpfile;
        }

        public Integer getOwnerType() {
            return ownerType;
        }

        public void setOwnerType(Integer ownerType) {
            this.ownerType = ownerType;
        }

        public Long getLocalMtime() {
            return localMtime;
        }

        public void setLocalMtime(Long localMtime) {
            this.localMtime = localMtime;
        }

        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }

        public Integer getIsdir() {
            return isdir;
        }

        public void setIsdir(Integer isdir) {
            this.isdir = isdir;
        }

        public Integer getShare() {
            return share;
        }

        public void setShare(Integer share) {
            this.share = share;
        }

        public Integer getPl() {
            return pl;
        }

        public void setPl(Integer pl) {
            this.pl = pl;
        }

        public Integer getFromType() {
            return fromType;
        }

        public void setFromType(Integer fromType) {
            this.fromType = fromType;
        }

        public Long getLocalCtime() {
            return localCtime;
        }

        public void setLocalCtime(Long localCtime) {
            this.localCtime = localCtime;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Integer getEmpty() {
            return empty;
        }

        public void setEmpty(Integer empty) {
            this.empty = empty;
        }

        public Integer getServerAtime() {
            return serverAtime;
        }

        public void setServerAtime(Integer serverAtime) {
            this.serverAtime = serverAtime;
        }

        public String getServerFilename() {
            return serverFilename;
        }

        public void setServerFilename(String serverFilename) {
            this.serverFilename = serverFilename;
        }

        public Long getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(Long ownerId) {
            this.ownerId = ownerId;
        }

        public Integer getUnlist() {
            return unlist;
        }

        public void setUnlist(Integer unlist) {
            this.unlist = unlist;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public Thumb getThumbs() {
            return thumbs;
        }

        public void setThumbs(Thumb thumbs) {
            this.thumbs = thumbs;
        }

        public String getDocpreview() {
            return docpreview;
        }

        public void setDocpreview(String docpreview) {
            this.docpreview = docpreview;
        }

        public String getLodocpreview() {
            return lodocpreview;
        }

        public void setLodocpreview(String lodocpreview) {
            this.lodocpreview = lodocpreview;
        }
    }

    public static class Thumb {
        private String icon;
        private String url1;
        private String url2;
        private String url3;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUrl1() {
            return url1;
        }

        public void setUrl1(String url1) {
            this.url1 = url1;
        }

        public String getUrl2() {
            return url2;
        }

        public void setUrl2(String url2) {
            this.url2 = url2;
        }

        public String getUrl3() {
            return url3;
        }

        public void setUrl3(String url3) {
            this.url3 = url3;
        }
    }
}
