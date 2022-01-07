package com.cyp.robot.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.cyp.robot.api.common.Constants;
import com.cyp.robot.api.dto.NasDto;
import com.cyp.robot.api.dto.ResultDto;
import com.cyp.robot.utils.DateUtil;
import com.cyp.robot.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
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


    private static final String user = "lyj";
    private static final String file_type_dir = "dir";
    private static final String file_type_file = "file";
    private static final String file_type_other = "other";

    /**
     * 文件限制大小不超过 10M
     */
    @RequestMapping("/upload")
    private List<String> upload(@RequestParam("file") MultipartFile[] fileName, @RequestParam(required = false) String filePath, HttpServletRequest request) {
        log.info("批量上传文件数量 =" + fileName.length);
        List<String> list = new ArrayList<>();

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multipartHttpServletRequest.getFiles("file");

        for (MultipartFile multipartFile : fileName) {
            log.info("上传文件最大10M，文件= {} size= {} 是否超过= {}", multipartFile.getName(), multipartFile.getOriginalFilename(), (multipartFile.getSize() > 10485760));
//            if (multipartFile.getSize() > 10485760)
//                continue;
            String upload = FileUtils.uploadMultipartFile(multipartFile, filePath);
            list.add(upload);
        }
        return list;
    }


    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        FileUtils.downloadFile(request, response);
    }


    @RequestMapping("/copyFile")
    public void upload(@RequestBody JSONObject jsonObject) {
        String src = jsonObject.getString("file");
        File srcFile = new File(src);
        String dist = Constants.UPLOAD_DIR + File.separator + srcFile.getName();
        FileUtils.copyFile(src, dist);
    }


    @RequestMapping("/getChild")
    private Object getChild(@RequestBody String params, HttpServletRequest request) {
        JSONObject map = new JSONObject();
        String[] paramArray = params.split("&");
        for (String param : paramArray) {
            String[] arr = param.split("=");
            if (arr.length == 2) {
                try {
                    map.put(arr[0], URLDecoder.decode(arr[1], "utf-8"));
                } catch (Exception e) {
                    log.info("URLDecoder.decode 异常 arr= {}", Arrays.toString(arr), e);
                }
            }
        }
        String filePath = StringUtils.isEmpty(map.getString("filePath")) ? "/" : map.getString("filePath");
        int page = map.getInteger("page") == null ? 1 : map.getInteger("page");
        int rows = map.getInteger("rows") == null ? 10 : map.getInteger("rows");

        String pathName;
        if (filePath.startsWith(Constants.TEMP_DIR)) {
            pathName = filePath;
        } else {
            pathName = Constants.TEMP_DIR + File.separator + filePath;
        }

        File file = new File(pathName);

        ArrayList<NasDto> nasDtoList = new ArrayList<>();
        String parentPath = file.getParent();

        nasDtoList.add(getDefaultFile(parentPath));

        String message;
        if (!file.exists()) {
            message = "文件目录不存在";
        } else if (!file.isDirectory()) {
            message = "不是文件目录";
        } else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    NasDto nasDto = new NasDto();
                    nasDto.setName(f.getName());
                    nasDto.setDir(f.getPath());
                    nasDto.setParentName(parentPath);
                    nasDto.setCreateDate(DateUtil.timestamp2Str(f.lastModified()));
                    nasDto.setUpdateDate(DateUtil.timestamp2Str(f.lastModified()));
                    nasDto.setUpdateBy(user);
                    nasDto.setType(f.isDirectory() ? file_type_dir : file_type_file);
                    nasDto.setSize(getSize(f));
                    nasDtoList.add(nasDto);
                }
            }
            int start = (page - 1) * rows;
            int end = Math.min(page * rows, nasDtoList.size());
            log.info("数据总共 {} 条，返回的开始下标= {} 结束下标= {}", nasDtoList.size(), start, end);

            List<NasDto> responseData = nasDtoList.subList(start, end);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", responseData);
            jsonObject.put("page", page);
            jsonObject.put("totalPage", nasDtoList.size() % rows == 0 ? nasDtoList.size() / rows : nasDtoList.size() / rows + 1);
            jsonObject.put("totalCount", nasDtoList.size());
            ResultDto<Object> resultDto = new ResultDto<>();
            return jsonObject;
        }
        System.out.println("hashCode= " + ResultDto.fail().hashCode());
        return ResultDto.fail(message);
    }

    private NasDto getDefaultFile(String parentPath) {
        NasDto nasDto = new NasDto();
        nasDto.setName("...");
        nasDto.setDir("");
        nasDto.setParentName(parentPath);
        nasDto.setCreateDate(null);
        nasDto.setUpdateDate(null);
        nasDto.setUpdateBy(user);
        nasDto.setType(file_type_other);
        nasDto.setSize(null);
        return nasDto;
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
            if (files != null) {
                for (File file : files) {
                    size += getDirectoryAndFileSize(file);
                }
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
            case file_type_dir:
                file = new File(pathName);
                break;
            case file_type_file:
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


}
