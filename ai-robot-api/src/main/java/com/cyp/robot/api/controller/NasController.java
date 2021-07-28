package com.cyp.robot.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyp.robot.api.common.Constants;
import com.cyp.robot.utils.DateUtil;
import com.cyp.robot.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author :        luyijun
 * @Date :          2020/10/29 22:23
 * @Description :   NAS盘
 */
@Slf4j
@RestController
@RequestMapping("/nas")
public class NasController {


    /**
     * 文件限制大小不超过 10M
     *
     * @param fileName
     * @param filePath
     */
    @RequestMapping("/upload")
    private List<String> upload(@RequestParam("fileName") MultipartFile[] fileName, @RequestParam(required = false) String filePath) {
        log.info("批量上传文件数量=" + fileName.length);
        List<String> list = new ArrayList<>();
        for (MultipartFile multipartFile : fileName) {
            String upload = FileUtils.uploadMultipartFile(multipartFile, filePath);
            list.add(upload);
        }
        return list;
    }


    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        FileUtils.downloadFile(request, response);
    }


    public String date2String() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String format = now.format(formatter);
        return format;
    }


    @RequestMapping("/getChild")
    private Object getChild(@RequestBody JSONObject params, HttpServletRequest request) {
        String page1 = request.getParameter("currPage");
        String rows1 = request.getParameter("rows");
        String order = request.getParameter("rowNum");
        String filePath = params.getString("filePath");
        Integer pageNo = params.getInteger("pageNo") == null ? 1 : params.getInteger("pageNo");
        Integer pageSize = params.getInteger("pageSize") == null ? 10 : params.getInteger("pageSize");
        String pathName = Constants.TEMP_DIR + File.separator + filePath;
        File file = new File(pathName);
        List<String> child = new ArrayList<>();

        JSONArray list = new JSONArray();
        String message;
        if (!file.exists()) {
            message = "文件目录不存在";
        } else if (!file.isDirectory()) {
            message = "不是文件目录";
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                child.add(f.getName());
                JSONObject dto = new JSONObject();
                dto.put("name", f.getName());
                dto.put("updateDate", DateUtil.timestamp2Str(f.lastModified()));
                dto.put("type", f.isDirectory() ? "文件夹" : "文件");
                dto.put("size", getSize(f));
                dto.put("updateBy", "lyj");
                list.add(dto);
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("page", pageNo);
        jsonObject.put("totalPage", list.size() / pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1);
        jsonObject.put("totalCount", list.size());
        return jsonObject;
    }

    private static String getSize(File f) {

        long size = getDirectoryAndFileSize(f);

        int level = 1;
        BigDecimal divisor = new BigDecimal(1024);
        BigDecimal calculateSize = new BigDecimal(size);
        while (calculateSize.compareTo(divisor) >= 0) {
            calculateSize = calculateSize.divide(divisor).setScale(2, RoundingMode.HALF_UP);//保留2位小数
            level++;
        }

        String company = "B";
        switch (level) {
            case 1:
                company = "B";
                break;
            case 2:
                company = "kB";
                break;
            case 3:
                company = "MB";
                break;
            case 4:
                company = "GB";
                break;
            case 5:
                company = "PB";
                break;
            default:
                break;
        }
        return calculateSize + " " + company;
    }

    private static long getDirectoryAndFileSize(File f) {
        long size = 0;
        if (f.isFile()) {
            size += f.length();
        } else {
            File[] files = f.listFiles();
            for (File file : files) {
                size += getDirectoryAndFileSize(file);
            }
        }
        return size;
    }


    @RequestMapping("/mk")
    private Object mk(@RequestBody JSONObject params) {
        String filePath = params.getString("filePath");
        // directory  file
        String fileType = params.getString("fileType");
        String pathName = Constants.TEMP_DIR + File.separator + filePath;
        File file;
        switch (fileType) {
            case "directory":
                file = new File(pathName);
                break;
            case "file":
            default:
                file = new File(pathName).getParentFile();
                break;
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        List<String> child = new ArrayList<>();
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                child.add(f.getName());
            }
        }
        return child;
    }


    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/getIdiom")
    private void getIdiom() {
        //============================采集百度成语============================
        String filePathName = Constants.TEMP_DIR + "/" + "成语.txt";
        int i = 0;
        String url = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=28204" +
                "&query=成语大全" +
                "&pn=" + i +
                "&rn=30";

        while (true) {
            log.info("=================采集百度成语第 {} 轮=================", i);
            RestTemplate restTemplate = new RestTemplate();
            JSONObject forObject = restTemplate.getForObject(url, JSONObject.class);
            System.out.println("forObject = " + forObject);
            assert forObject != null;
            JSONArray data = forObject.getJSONArray("data");


            if (data == null) {
                break;
            }

            JSONObject jsonObject = data.getJSONObject(0);
            JSONArray result = jsonObject.getJSONArray("result");

            ArrayList<String> arrayList = new ArrayList<>();
            result.forEach(e -> {
                JSONObject jsonObject1 = JSONObject.parseObject(e.toString());
                String ename = jsonObject1.getString("ename");
                String jumplink = jsonObject1.getString("jumplink");
                String newJumplink = null;
                try {
                    newJumplink = URLDecoder.decode(jumplink, "utf-8");
                } catch (UnsupportedEncodingException unsupportedEncodingException) {
                    unsupportedEncodingException.printStackTrace();
                }

//                log.info(ename + "," + newJumplink);
                arrayList.add(ename + "," + newJumplink);
            });
            save(filePathName, arrayList);
            i++;
        }

        //============================采集百度成语============================
    }


    //在文件最后追加数据
    private static void save(String filePathName, List<String> sourceList) {
        BufferedWriter bufferedWriter = null;
        try {
            //文件最后追加数据
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePathName), true), StandardCharsets.UTF_8));
            for (String source : sourceList) {
                //每次另起一行追加数据
                bufferedWriter.newLine();
                bufferedWriter.write(source);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bufferedWriter);
        }
    }


    public static void main(String[] args) {
        String pathName = Constants.TEMP_DIR + File.separator + "a";
        File file = new File(pathName);
        boolean file1 = file.isFile();
        boolean directory = file.isDirectory();
        File[] files = file.listFiles();
        file.mkdirs();

    }

}
