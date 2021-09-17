package com.peisp.exportest;

import java.util.List;
import java.util.Map;

/**
 * @author peishaopeng
 */
public class ExcelSheet {

    /** sheet 页名称 为空时，sheetN */
    private String sheetName;
    /** sheet 页名称 数据 */
    private List<Map<String, Object>> sheetData;

    private ExcelSheet() {
    }

    public ExcelSheet(List<Map<String, Object>> sheetData) {
        this.sheetData = sheetData;
    }

    public ExcelSheet(String sheetName, List<Map<String, Object>> sheetData) {
        this.sheetName = sheetName;
        this.sheetData = sheetData;
    }

    public String getSheetName() {
        return sheetName;
    }

    public List<Map<String, Object>> getSheetData() {
        return sheetData;
    }
}
