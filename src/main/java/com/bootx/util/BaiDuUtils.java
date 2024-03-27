package com.bootx.util;

import com.bootx.common.Pageable;
import com.bootx.pojo.*;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaiDuUtils {

    public static final String token = "";

    private static final String appKey = "I5CqOku7s1jjNpSQ2uMS4Kzzx7Sz6yzv";

    private static final String appId = "25599902";

    private static final String secretKey = "VwSQA3kIB5jqXHeCcLNB7KxOGFU6lNg4";

    private static final String signKey = "r*kksd-pqT*DTe6nf4ZoQ6V$AJ$AE%Jr";

    private static final String shareSecret = "c6c925c4";

    private static final Integer shareThirdld = 2080;

    public static String getCode(){
        String url="http://openapi.baidu.com/oauth/2.0/authorize?response_type=code&client_id="+appKey+"&redirect_uri=oob&scope=basic,netdisk&device_id="+appId;
        System.out.println(url);
        return "ec04f93409aa376e6578d5a161ebd47e";
    }


    public static BaiDuAccessToken getToken(){
        String code = getCode();
        String url="https://openapi.baidu.com/oauth/2.0/token?grant_type=authorization_code&code="+code+"&client_id="+appKey+"&client_secret="+secretKey+"&redirect_uri=oob";
        String s = WebUtils.get(url,null);
        return JsonUtils.toObject(s,new TypeReference<BaiDuAccessToken>(){});
    }

    public static BaiDuAccessToken refreshToken(String token) {
        String url="https://openapi.baidu.com/oauth/2.0/token?grant_type=refresh_token&refresh_token="+token+"&client_id="+appKey+"&client_secret="+secretKey;
        String s = WebUtils.get(url,null);
        return JsonUtils.toObject(s,new TypeReference<BaiDuAccessToken>(){});
    }


    /**
     * 获取用户信息
     * @param token
     * @return
     */
    public static UinfoPojo uinfo(String token) {
        String url="https://pan.baidu.com/rest/2.0/xpan/nas?method=uinfo&access_token="+token;
        String s = WebUtils.get(url,null);
        return JsonUtils.toObject(s, new TypeReference<UinfoPojo>() {
        });
    }

    /**
     * 获取网盘容量信息
     * @param token
     * @return
     */
    public static QuotaPojo quota(String token) {
        String url="https://pan.baidu.com/api/quota?checkfree=1&checkexpire=1&access_token="+token;
        String s = WebUtils.get(url,null);
        return JsonUtils.toObject(s, new TypeReference<QuotaPojo>() {
        });
    }

    /**
     * 获取网盘容量信息
     * @param token
     * @return
     */
    public static FileListPojo fileList(String token,String dir,Integer folder) {
        System.out.println("文件目录："+dir);
        if(StringUtils.isBlank(dir)){
            dir = "/shortVideo";
        }
        if(folder==null){
            folder = 0;
        }

        String url = "https://pan.baidu.com/rest/2.0/xpan/file?method=list&dir="+parsePath(dir)+"&order=time&start=0&limit=1000&web=web&folder=0&access_token="+token+"&desc=1";
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            FileListPojo object = JsonUtils.toObject(response.toString(), new TypeReference<FileListPojo>() {
            });
            if(object.getList().isEmpty()){
                System.out.println(response.toString());
            }
            return object;
        }catch (Exception e){
            e.printStackTrace();
            FileListPojo fileListPojo = new FileListPojo();
            fileListPojo.setErrmsg(e.getMessage());
            return fileListPojo;
        }
    }

    /**
     * 获取文档列表
     * @param token
     * @param dir
     * @return
     */
    public static DocListPojo docList(String token,String dir) {
        if(StringUtils.isBlank(dir)){
            dir = "/短剧";
        }

        String url = "https://pan.baidu.com/rest/2.0/xpan/file?method=doclist&parent_path="+parsePath(dir)+"&order=time&page=1&num=1000&web=1&folder=0&access_token="+token+"&desc=0";
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return JsonUtils.toObject(response.toString(), new TypeReference<DocListPojo>() {
            });
        }catch (Exception e){
            DocListPojo fileListPojo = new DocListPojo();
            fileListPojo.setErrmsg(e.getMessage());
            return fileListPojo;
        }
    }

    /**
     * 获取图片列表
     * @param token
     * @param dir
     * @return
     */
    public static ImageListPojo imageList(String token, String dir) {
        if(StringUtils.isBlank(dir)){
            dir = "/短剧";
        }

        String url = "https://pan.baidu.com/rest/2.0/xpan/file?method=imagelist&parent_path="+parsePath(dir)+"&order=time&page=1&num=1000&web=1&folder=0&access_token="+token+"&desc=0";
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return JsonUtils.toObject(response.toString(), new TypeReference<ImageListPojo>() {
            });
        }catch (Exception e){
            ImageListPojo fileListPojo = new ImageListPojo();
            fileListPojo.setErrmsg(e.getMessage());
            return fileListPojo;
        }
    }
    /**
     * 获取视频列表
     * @param token
     * @param dir
     * @return
     */
    public static FileListPojo videoList(String token,String dir) {
        if(StringUtils.isBlank(dir)){
            dir = "/短剧";
        }

        String url = "https://pan.baidu.com/rest/2.0/xpan/file?method=videolist&parent_path="+parsePath(dir)+"&order=time&page=1&num=1000&web=1&folder=0&access_token="+token+"&desc=0";
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return JsonUtils.toObject(response.toString(), new TypeReference<FileListPojo>() {
            });
        }catch (Exception e){
            FileListPojo fileListPojo = new FileListPojo();
            fileListPojo.setErrmsg(e.getMessage());
            return fileListPojo;
        }
    }

    /**
     * 获取bt列表
     * @param token
     * @param dir
     * @return
     */
    public static BtListPojo btList(String token,String dir) {
        if(StringUtils.isBlank(dir)){
            dir = "/短剧";
        }

        String url = "https://pan.baidu.com/rest/2.0/xpan/BtListPojo?method=doclist&parent_path="+parsePath(dir)+"&order=time&page=1&num=1000&web=1&folder=0&access_token="+token+"&desc=0";
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return JsonUtils.toObject(response.toString(), new TypeReference<BtListPojo>() {
            });
        }catch (Exception e){
            BtListPojo fileListPojo = new BtListPojo();
            fileListPojo.setErrmsg(e.getMessage());
            return fileListPojo;
        }
    }


    /**
     * 获取分类文件总个数
     * @param token
     * @param dir
     *      目录名称，为空时，parent_path = "/" && recursion = 1
     * @param category
     *      文件类型，1 视频、2 音频、3 图片、4 文档、5 应用、6 其他、7 种子
     * @param recursion
     *      是否递归，0 不递归、1 递归，默认0
     * @return
     */
    public static BtListPojo categoryInfo(String token,String dir,Integer category,Integer recursion) {
        if(StringUtils.isBlank(dir)){
            dir = "/短剧";
        }

        String url = "http://pan.baidu.com/api/categoryinfo?parent_path="+parsePath(dir)+"&category="+category+"&recursion="+recursion+"&access_token="+token+"&desc=0";
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return JsonUtils.toObject(response.toString(), new TypeReference<BtListPojo>() {
            });
        }catch (Exception e){
            BtListPojo fileListPojo = new BtListPojo();
            fileListPojo.setErrmsg(e.getMessage());
            return fileListPojo;
        }
    }

    /**
     * 获取分类文件总个数
     * @param token
     * @param dir
     *      目录名称，为空时，parent_path = "/" && recursion = 1
     * @param category
     *      文件类型，1 视频、2 音频、3 图片、4 文档、5 应用、6 其他、7 种子
     * @param recursion
     *      是否递归，0 不递归、1 递归，默认0
     * @return
     */
    public static CategoryListPojo categoryList(String token,String dir,Integer category,Integer recursion,Integer start,Integer count) {
        if(StringUtils.isBlank(dir)){
            dir = "/";
        }
        String url = "https://pan.baidu.com/rest/2.0/xpan/multimedia?method=categorylist&parent_path="+parsePath(dir)+"&category="+category+"&recursion="+recursion+"&access_token="+token+"&desc=0&start="+start+"&limit="+count;
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return JsonUtils.toObject(response.toString(), new TypeReference<CategoryListPojo>() {
            });
        }catch (Exception e){
            e.printStackTrace();
            CategoryListPojo fileListPojo = new CategoryListPojo();
            fileListPojo.setErrmsg(e.getMessage());
            return fileListPojo;
        }
    }


    public static FileMetasPojo filemetas(String token,String fsids) {

        String url = "http://pan.baidu.com/rest/2.0/xpan/multimedia?method=filemetas&fsids=["+fsids+"]&dlink=1&thumb=1&access_token="+token+"&extra=1&needmedia=1&detail=1";
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return JsonUtils.toObject(response.toString(), new TypeReference<FileMetasPojo>() {
            });
        }catch (Exception e){
            FileMetasPojo fileListPojo = new FileMetasPojo();
            fileListPojo.setErrmsg(e.getMessage());
            return fileListPojo;
        }
    }

    public static String streaming(String token,String path) {
        String url = "https://pan.baidu.com/rest/2.0/xpan/file?method=streaming&access_token="+token+"&path="+parsePath(path)+"&type=M3U8_FLV_264_480";
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine+"\t\n");
            }
            in.close();
            return response.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static String parsePath(String path){
        return path.replaceAll(" ","%20").replaceAll("&","%26");
    }


    public static String shareSet(String token,String fsIdList) {
        Map<String, Object> params = new HashMap(16);
        params.put("fid_list", "["+fsIdList+"]");
        params.put("period", 7);
        params.put("third_type", shareThirdld);
        params.put("csign", DigestUtils.md5Hex(shareThirdld + "["+fsIdList+"]" + 4 + shareSecret));
        params.put("schannel", 4);
        String url="https://pan.baidu.com/rest/2.0/xpan/share/set?access_token=" + token;
        String s = WebUtils.post(url, params);
        System.out.println(s);
        return s;
    }

    /**
     *  本接口用于获取用户网盘中指定目录下的文件列表。返回的文件列表支持排序、分页等操作。
     * @param dir
     *      需要list的目录，以/开头的绝对路径, 默认为/ 路径包含中文时需要UrlEncode编码
     * @param order
     *      排序字段：默认为name；
     *          time表示先按文件类型排序，后按修改时间排序；
     *          name表示先按文件类型排序，后按文件名称排序；(注意，此处排序是按字符串排序的，如果用户有剧集排序需求，需要自行开发)
     *          size表示先按文件类型排序，后按文件大小排序。
     * @param desc
     *      默认为升序，设置为1实现降序 （注：排序的对象是当前目录下所有文件，不是当前分页下的文件）
     * @param pageable
     *      分页属性
     * @param web
     *      值为1时，返回dir_empty属性和缩略图数据
     * @param folder
     *      是否只返回文件夹，0 返回所有，1 只返回文件夹，且属性只返回path字段
     * @param showempty
     *      是否返回dir_empty属性，0 不返回，1 返回
     *
     */
    public static FileListPojo list(String token,String dir, String order, Integer desc, Pageable pageable, Integer web, Integer folder, Integer showempty) {
        Map<String, Object> params = new HashMap(16);
        params.put("method", "list");
        params.put("access_token", token);
        params.put("dir", dir);
        params.put("order", order);
        params.put("desc", desc);
        params.put("start", (pageable.getPageNumber() - 1) * pageable.getPageSize());
        params.put("limit", pageable.getPageSize());
        params.put("web", web);
        params.put("folder", folder);
        params.put("showempty", showempty);
        String s = WebUtils.get("http://pan.baidu.com/rest/2.0/xpan/file", params);
        return JsonUtils.toObject(s, new TypeReference<FileListPojo>() {
        });
    }

    public static FileListPojo list(String token,String dir) {
        Pageable pageable = new Pageable();
        pageable.setPageSize(1000);
        return list(token,dir,"name",0,pageable,1,0,1);
    }


    /**
     * 删除文件
     * @param token
     *      token
     * @param fileList
     *      要删除的文件路径
     * @return
     */
    public static String delete(String token, List<String> fileList) {
        Map<String, Object> params = new HashMap(16);
        params.put("async", 1);
        params.put("filelist",JsonUtils.toJson(fileList));
        String url="http://pan.baidu.com/rest/2.0/xpan/file?method=filemanager&access_token="+token+"&opera=delete";
        String s = WebUtils.post(url, params);
        System.out.println(s);
        return s;
    }


    public static FileListPojo search(String token,String key, String dir, Integer category, Pageable pageable, Integer recursion, Integer web) {
        Map<String, Object> params = new HashMap(16);
        params.put("method", "search");
        params.put("access_token", token);
        params.put("key", key);
        params.put("dir", dir);
        params.put("category", category);
        params.put("page", pageable.getPageNumber());
        params.put("num", pageable.getPageSize());
        params.put("web", web);
        params.put("recursion", recursion);
        params.put("web", web);
        String s = WebUtils.get("http://pan.baidu.com/rest/2.0/xpan/file", params);
        System.out.println(s);
        return JsonUtils.toObject(s, new TypeReference<FileListPojo>() {
        });
    }

    public static void main(String[] args) {
        String token = "121.3b6dd2b52b40b5478767a79f9c5facb6.YQbCWdedA74iNzcQIdvSCOn-p5z1rkROrPzSEYS.DITsEg";
        move(token,"/shortVideo/新片源3/月/日/96：隐面佳人.mp4","/shortVideo/新片源3/月/日/96：隐面佳人","96：隐面佳人.mp4");
    }

    public static void rename(String token,String path,String newName){
        String url1="https://pan.baidu.com/rest/2.0/xpan/file?method=filemanager&access_token="+token+"&opera=rename";
        Map<String,Object> params = new HashMap<>();
        params.put("async",1);
        List<Map<String,Object>> fileList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("path",path);
        map.put("newname",newName);
        fileList.add(map);
        params.put("filelist",JsonUtils.toJson(fileList));
        params.put("ondup","fail");
        String s = WebUtils.post(url1, params);
        System.out.println(s);
    }

    public static void move(String token,String path,String dest,String newName){
        String url1="https://pan.baidu.com/rest/2.0/xpan/file?method=filemanager&access_token="+token+"&opera=move";
        Map<String,Object> params = new HashMap<>();
        params.put("async",1);
        List<Map<String,Object>> fileList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("path",path);
        map.put("dest",dest);
        map.put("ondup","fail");
        map.put("newname",newName);
        fileList.add(map);
        params.put("filelist",JsonUtils.toJson(fileList));
        params.put("ondup","fail");
        String s = WebUtils.post(url1, params);
        System.out.println(s);
    }
}
