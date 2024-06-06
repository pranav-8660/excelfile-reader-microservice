package com.pranavv51.microservice.xlreadmicroservice.service;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

@Service
public class ExcelService {


    private static File multipartFileToFile(MultipartFile fileInst) throws Exception {

        File file = new File(fileInst.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(fileInst.getBytes());
        fos.close();
        return file;
    }


    public List<List<String>> readAnXlFile(MultipartFile fileInst) throws Exception {
        List<List<String>> allList = new LinkedList<>();
        List<String> labels = new LinkedList<>();

        try (FileInputStream fileStream = new FileInputStream(multipartFileToFile(fileInst));
             XSSFWorkbook workbook = new XSSFWorkbook(fileStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                List<String> scratch = new LinkedList<>();

                if (row != null) {
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        Cell cell = row.getCell(j);
                        if (i == 0) {
                            labels.add(cell != null ? dataFormatter.formatCellValue(cell) : "");
                        } else {
                            scratch.add(cell != null ? formatCell(cell, dataFormatter) : "");
                        }
                    }
                    if (i != 0) {
                        allList.add(scratch);
                    }
                }
            }
        }
        allList.add(0, labels); // Add labels as the first row
        return allList;
    }

    private String formatCell(Cell cell, DataFormatter dataFormatter) {
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return dataFormatter.formatCellValue(cell);
                } else {
                    return dataFormatter.formatCellValue(cell);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return dataFormatter.formatCellValue(cell);
            default:
                return "";
        }
    }

    public File getFileFrombyteArray(byte[] byteArray) throws IOException {
        File tempFile = new File("fileTemp.pdf");
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(byteArray);
        fos.close();
        return tempFile;
    }

}



