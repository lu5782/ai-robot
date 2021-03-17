package com.cyp.robot.utils;

import com.cyp.robot.api.common.Constants;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class ExcelUtils {


    public static String exportExcel(Object data) {
        int maxRowNum = Constants.EXCEL_SHEET_MAX_ROW_NUM;
        List<Object> dataList = null;
        if (data instanceof List) {
            dataList = (List<Object>) data;
        } else {
            dataList = new ArrayList<>();
            dataList.add(data);
        }
        if (dataList.size() == 0) {
            return "没有数据需要导出";
        }
        // 创建 一个excel文档对象
        Workbook workBook = null;

        String suffix = null;
        boolean isExcel2003 = false;
        if (isExcel2003) {
            workBook = new HSSFWorkbook();
            suffix = Constants.EXCEL_2003_SUFFIX;
        } else {
            workBook = new XSSFWorkbook();
            suffix = Constants.EXCEL_2007_SUFFIX;
        }


        int sheetNum = dataList.size() % maxRowNum == 0 ? dataList.size() / maxRowNum : dataList.size() / maxRowNum + 1;
        for (int i = 0; i < sheetNum; i++) {
            int begin = i * maxRowNum;
            int end = Math.min((i + 1) * maxRowNum, dataList.size());
            List<Object> newList = new ArrayList<>();
            for (int j = begin; j < end; j++) {
                newList.add(dataList.get(j));
            }
            // 创建一个工作薄对象
            createSheet(workBook.createSheet(), newList);
        }

        String fileName = Constants.UPLOAD_PATH + File.separator + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + Constants.POINT + suffix;
        File file = new File(fileName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            workBook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
        return "success";
    }

    private static void createSheet(Sheet sheet, List<Object> dataList) {
        AtomicInteger rowNum = new AtomicInteger();
        dataList.forEach(obj -> {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            Row row = sheet.createRow(rowNum.get());
            for (int i = 0; i < declaredFields.length; i++) {
                String name = declaredFields[i].getName();
                String getMethod = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                try {
                    Method declaredMethod = obj.getClass().getDeclaredMethod(getMethod);
                    Object invoke = declaredMethod.invoke(obj);

                    Class<?> type = declaredFields[i].getType();
                    Cell cell = row.createCell(i);
                    cell.setCellValue(invoke + "");
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            rowNum.getAndIncrement();
        });
    }


    public static void importExcel(String filePathName) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(filePathName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /*
     * 插入图片
     */
    public static void createPicture(HSSFWorkbook workbook, HSSFSheet sheet, int beginColl, int endColl, int beginRow, int endRow, String imgUrl) {
        byte[] pictureData = null;
        try {
            File file = new File(imgUrl);
            FileInputStream in = new FileInputStream(file);
            int size = (int) file.length();
            byte[] buffer = new byte[size];
            in.read(buffer, 0, size);
            pictureData = buffer;
//            pictureData = FileUtils.readFileToByteArray(new File(imgUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int pictureIdx = workbook.addPicture(pictureData, Workbook.PICTURE_TYPE_JPEG);
        CreationHelper helper = workbook.getCreationHelper();
        ClientAnchor anchor = helper.createClientAnchor();

        anchor.setCol1(beginColl); //图片开始列数
        anchor.setCol2(endColl); //图片结束列数
        anchor.setRow1(beginRow); //图片开始行数
        anchor.setRow2(endRow);//图片结束行数

        Drawing drawing = sheet.createDrawingPatriarch();
        drawing.createPicture(anchor, pictureIdx);
    }
}
