package com.framework.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {
    private XSSFWorkbook wb;
    private XSSFSheet sheet;
    private final DataFormatter formatter = new DataFormatter();

    public ExcelReader(String filePath) throws IOException {
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file)) {
            wb = new XSSFWorkbook(fis);
        }
    }

    /**
     * Gets string data from a specific cell.
     * Uses DataFormatter to ensure values like numbers are returned as Strings properly.
     */
    public String getStringData(String sheetName, int rowNumber, int cellNumber) {
        sheet = wb.getSheet(sheetName);
        XSSFRow row = sheet.getRow(rowNumber);
        XSSFCell cell = row.getCell(cellNumber);
        return formatter.formatCellValue(cell);
    }

    /**
     * Gets numeric data and casts to integer.
     */
    public int getIntegerData(String sheetName, int rowNumber, int cellNumber) {
        sheet = wb.getSheet(sheetName);
        XSSFRow row = sheet.getRow(rowNumber);
        XSSFCell cell = row.getCell(cellNumber);
        return (int) cell.getNumericCellValue();
    }

    public int getLastRow(String sheetName) {
        this.sheet = wb.getSheet(sheetName);
        return this.sheet.getLastRowNum();
    }
}