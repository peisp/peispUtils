package com.peisp.exportest;

import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * excel 属性
 * 待扩展
 * @author peisp
 */
public class ExcelProperties {

    /** excel name */
    private String excelName;
    /** sheet name */
    private String sheetName;
    /** 表头背景颜色，默认灰色 */
    private IndexedColors headBGColor;
    /** 表头字体高度，默认 14 */
    private Short headFontHeightInPoints;
    /** 内容背景颜色，默认灰色 */
    private IndexedColors contentBGColor;
    /** 内容字体高度，默认 11 */
    private Short contentFontHeightInPoints;

    public String getExcelName() {
        return excelName;
    }

    public ExcelProperties setExcelName(String excelName) {
        this.excelName = excelName;
        return this;
    }

    public String getSheetName() {
        return sheetName;
    }

    public ExcelProperties setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public IndexedColors getHeadBGColor() {
        return headBGColor;
    }

    public ExcelProperties setHeadBGColor(IndexedColors headBGColor) {
        this.headBGColor = headBGColor;
        return this;
    }

    public Short getHeadFontHeightInPoints() {
        return headFontHeightInPoints;
    }

    public ExcelProperties setHeadFontHeightInPoints(Short headFontHeightInPoints) {
        this.headFontHeightInPoints = headFontHeightInPoints;
        return this;
    }

    public IndexedColors getContentBGColor() {
        return contentBGColor;
    }

    public ExcelProperties setContentBGColor(IndexedColors contentBGColor) {
        this.contentBGColor = contentBGColor;
        return this;
    }

    public Short getContentFontHeightInPoints() {
        return contentFontHeightInPoints;
    }

    public ExcelProperties setContentFontHeightInPoints(Short contentFontHeightInPoints) {
        this.contentFontHeightInPoints = contentFontHeightInPoints;
        return this;
    }
}
