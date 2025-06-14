package be.ehb.enterpriseapplications.werkstuk.util;


import be.ehb.enterpriseapplications.werkstuk.dto.RevenueReportDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ExcelExporter {

    public static byte[] exportRevenue(RevenueReportDto report) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Revenue Report");

        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        Cell cell = row.createCell(0);
        cell.setCellValue("Revenue Report");

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Start Date");
        row.createCell(1).setCellValue(report.getStartDate().toString());

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("End Date");
        row.createCell(1).setCellValue(report.getEndDate().toString());

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Total Revenue");
        row.createCell(1).setCellValue(report.getTotalRevenue());

        row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Number of Auctions");
        row.createCell(1).setCellValue(report.getNumberOfAuctions());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}