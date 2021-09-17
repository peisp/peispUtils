package com.peisp.exportest;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * EasyExcel util
 * @author peishaopeng
 */
public class ExportUtil {

    /**
     * 根据数据生成 Excel ，写入到项目路径下
     * 默认表头为 index(0) 的 map 中的key
     * @param excelSheets 数据
     * @return String 生成的文件路径
     */
    public static String createExcelByData(ExcelSheet... excelSheets) throws Exception {
        return createExcelByDataCore(FileUtil.getPath(),null,null,excelSheets);
    }

    /**
     * 根据数据生成 Excel ，写入到指定路径下
     * 默认表头为 index(0) 的 map 中的key
     * @param excelSheets 数据
     * @param path 指定的路径
     * @return String 生成的文件路径
     * @throws Exception
     */
    public static String createExcelByData(String path, ExcelSheet... excelSheets) throws Exception {
        if (path == null){
            return createExcelByData(excelSheets);
        }
        return createExcelByDataCore(path,null,null,excelSheets);
    }


    /**
     * 根据数据生成 Excel ，写入到 response
     * 默认表头为 index(0) 的 map 中的key
     * @param excelSheets 数据
     * @param response 响应
     * @throws Exception
     */
    public static void createExcelByData(HttpServletResponse response, ExcelSheet... excelSheets) throws Exception {
        createExcelByDataCore(FileUtil.getPath(),response,null,excelSheets);
    }

    /**
     * 根据数据及属性生成 Excel ，写入到项目路径下
     * 默认表头为 index(0) 的 map 中的key
     * @param excelSheets 数据
     * @param excelProperties excel属性
     * @return 生成的文件地址
     * @throws Exception
     */
    public static String createExcelByData(ExcelProperties excelProperties, ExcelSheet... excelSheets) throws Exception {
        return createExcelByDataCore(FileUtil.getPath(),null,excelProperties,excelSheets);
    }

    /**
     * 根据数据及属性生成 Excel ，写入到 response
     * 默认表头为 index(0) 的 map 中的key* @param maps
     * @param excelSheets 数据
     * @param response 响应
     * @param excelProperties excel 属性
     * @throws Exception
     */
    public static void createExcelByData(HttpServletResponse response, ExcelProperties excelProperties, ExcelSheet... excelSheets) throws Exception {
        createExcelByDataCore(null,response,excelProperties,excelSheets);
    }


    /**
     * 根据数据及属性生成 Excel ，写入到指定路径下
     * 默认表头为 index(0) 的 map 中的key* @param maps
     * @param excelSheets 数据
     * @param path 指定的文件路径
     * @param excelProperties excel 属性
     * @throws Exception
     */
    public static void responseWriteExcelByData(String path, ExcelProperties excelProperties, ExcelSheet... excelSheets) throws Exception {
        createExcelByDataCore(path,null,excelProperties,excelSheets);
    }

    /**
     * 导出核心方法
     * 根据数据生成 Excel ，写入到指定的路径。 默认表头为 index(0) 的 map 中的key
     * @param path 文件路径，为空时，生成在项目路径下(HttpServletResponse 不空时生效)
     * @param response HttpServletResponse 不为空时，将文件写到 HttpServletResponse
     * @param excelProperties 属性
     * @param excelSheets 数据
     * @return 生成的文件路径
     */
    private static String createExcelByDataCore(String path, HttpServletResponse response, ExcelProperties excelProperties, ExcelSheet... excelSheets) {
        excelProperties = excelProperties == null ? new ExcelProperties() : excelProperties;


        // 写数据
        String fileName = path +
                (excelProperties.getExcelName() == null ? UUID.randomUUID().toString().replaceAll("-", "") : excelProperties.getExcelName())
                + ".xlsx";

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 设置表头背景色
        headWriteCellStyle.setFillForegroundColor(excelProperties.getHeadBGColor() == null ? IndexedColors.GREY_25_PERCENT.getIndex() : excelProperties.getHeadBGColor().getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints(excelProperties.getHeadFontHeightInPoints() == null ? (short)14 : excelProperties.getHeadFontHeightInPoints());
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        if (excelProperties.getContentBGColor() != null){
            contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
            // 设置内容背景色
            contentWriteCellStyle.setFillForegroundColor(excelProperties.getContentBGColor().index);
        }
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints(excelProperties.getContentFontHeightInPoints() == null ? (short)11 : excelProperties.getContentFontHeightInPoints());
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        ExcelWriter excelWriter = null;
        try {
            if (response == null){
                excelWriter = EasyExcel.write(fileName).build();
            }else {
                excelWriter = EasyExcel.write(response.getOutputStream()).build();
            }

            for (int i = 0; i < excelSheets.length; i++) {
                ExcelSheet excelSheet = excelSheets[i];

                if (excelSheet.getSheetData() == null){
                    throw new NullPointerException("Waiting for the data to be empty");
                }

                // 获取表头
                List<List<String>> head = getHead(excelSheet.getSheetData().get(0));
                // 转换数据
                List<List<Object>> data2Wlist = data2Wlist(excelSheet.getSheetData());

                // sheet 名称
                String sheetName = excelSheet.getSheetName() == null ? "sheet" + (i + 1) : excelSheet.getSheetName();

                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class 实际上可以一直变
                WriteSheet writeSheet = EasyExcel.writerSheet(i, sheetName)
                        .head(head)
                        .registerWriteHandler(horizontalCellStyleStrategy)
                        .build();
                excelWriter.write(data2Wlist, writeSheet);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();

            }
        }
        return response == null ? fileName : null;
    }


    /**
     * 获取 map -> key 作为表头
     * @param map
     * @return
     */
    private static List<List<String>> getHead(Map<String, Object> map){
        List<List<String>> list = new ArrayList<>();
        Set<String> keySet = map.keySet();
        keySet.forEach(s -> {
            List<String> head = new ArrayList<>();
            head.add(s);
            list.add(head);
        });
        return list;

    }

    /**
     * List<Map<String, Object>> -> List<List<Object>>
     * @param data
     * @return
     */
    private static List<List<Object>> data2Wlist(List<Map<String, Object>> data){
        List<List<Object>> list = new ArrayList<>();
        data.forEach(map -> {
            List<Object> row = new ArrayList<>();
            map.forEach((s, o) -> {
                row.add(o);
            });
            list.add(row);
        });
        return list;
    }



}
