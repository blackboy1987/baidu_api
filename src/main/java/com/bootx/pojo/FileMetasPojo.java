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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ListBean {

        private Integer category;
        private String dlink;
        private Integer duration;
        private String filename;
        @JsonProperty("fs_id")
        private Long fsId;
        private Integer isdir;
        @JsonProperty("local_ctime")
        private Integer localCtime;
        @JsonProperty("local_mtime")
        private Integer localMtime;
        private String md5;
        @JsonProperty("media_info")
        private MediaInfoBean mediaInfo = new MediaInfoBean();
        @JsonProperty("oper_id")
        private Long operId;
        private String path;
        @JsonProperty("server_ctime")
        private Integer serverCtime;
        @JsonProperty("server_mtime")
        private Integer serverMtime;
        private Long size;
        private ThumbsBean thumbs = new ThumbsBean();

        public Integer getCategory() {
            return category;
        }

        public void setCategory(Integer category) {
            this.category = category;
        }

        public String getDlink() {
            return dlink;
        }

        public void setDlink(String dlink) {
            this.dlink = dlink;
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

        public Integer getIsdir() {
            return isdir;
        }

        public void setIsdir(Integer isdir) {
            this.isdir = isdir;
        }

        public Integer getLocalCtime() {
            return localCtime;
        }

        public void setLocalCtime(Integer localCtime) {
            this.localCtime = localCtime;
        }

        public Integer getLocalMtime() {
            return localMtime;
        }

        public void setLocalMtime(Integer localMtime) {
            this.localMtime = localMtime;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public MediaInfoBean getMediaInfo() {
            return mediaInfo;
        }

        public void setMediaInfo(MediaInfoBean mediaInfo) {
            this.mediaInfo = mediaInfo;
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

        public Integer getServerCtime() {
            return serverCtime;
        }

        public void setServerCtime(Integer serverCtime) {
            this.serverCtime = serverCtime;
        }

        public Integer getServerMtime() {
            return serverMtime;
        }

        public void setServerMtime(Integer serverMtime) {
            this.serverMtime = serverMtime;
        }

        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }

        public ThumbsBean getThumbs() {
            return thumbs;
        }

        public void setThumbs(ThumbsBean thumbs) {
            this.thumbs = thumbs;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class MediaInfoBean {

            private Integer channels;
            private Long duration;
            @JsonProperty("duration_ms")
            private Integer durationMs;
            @JsonProperty("extra_info")
            private String extraInfo;
            @JsonProperty("file_size")
            private String fileSize;
            @JsonProperty("frame_rate")
            private Integer frameRate;
            private Integer height;
            @JsonProperty("meta_info")
            private String metaInfo;
            private String resolution;
            private Integer rotate;
            @JsonProperty("sample_rate")
            private Integer sampleRate;
            @JsonProperty("use_segment")
            private Integer useSegment;
            private Integer width;

            public Integer getChannels() {
                return channels;
            }

            public void setChannels(Integer channels) {
                this.channels = channels;
            }

            public Long getDuration() {
                return duration;
            }

            public void setDuration(Long duration) {
                this.duration = duration;
            }

            public Integer getDurationMs() {
                return durationMs;
            }

            public void setDurationMs(Integer durationMs) {
                this.durationMs = durationMs;
            }

            public String getExtraInfo() {
                return extraInfo;
            }

            public void setExtraInfo(String extraInfo) {
                this.extraInfo = extraInfo;
            }

            public String getFileSize() {
                return fileSize;
            }

            public void setFileSize(String fileSize) {
                this.fileSize = fileSize;
            }

            public Integer getFrameRate() {
                return frameRate;
            }

            public void setFrameRate(Integer frameRate) {
                this.frameRate = frameRate;
            }

            public Integer getHeight() {
                return height;
            }

            public void setHeight(Integer height) {
                this.height = height;
            }

            public String getMetaInfo() {
                return metaInfo;
            }

            public void setMetaInfo(String metaInfo) {
                this.metaInfo = metaInfo;
            }

            public String getResolution() {
                return resolution;
            }

            public void setResolution(String resolution) {
                this.resolution = resolution;
            }

            public Integer getRotate() {
                return rotate;
            }

            public void setRotate(Integer rotate) {
                this.rotate = rotate;
            }

            public Integer getSampleRate() {
                return sampleRate;
            }

            public void setSampleRate(Integer sampleRate) {
                this.sampleRate = sampleRate;
            }

            public Integer getUseSegment() {
                return useSegment;
            }

            public void setUseSegment(Integer useSegment) {
                this.useSegment = useSegment;
            }

            public Integer getWidth() {
                return width;
            }

            public void setWidth(Integer width) {
                this.width = width;
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ThumbsBean {
            private String icon;
            private String url1;
            private String url2;
            private String url3;
            private String url4;

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

            public String getUrl4() {
                return url4;
            }

            public void setUrl4(String url4) {
                this.url4 = url4;
            }
        }
    }
}
