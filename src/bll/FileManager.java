package bll;

import dal.db.EventDAO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileManager {

    EventDAO databaseEvent;

    public FileManager() throws Exception {
        databaseEvent = new EventDAO();
    }


    public Workbook exportExcelFile (int idOfEvent) {
        String[][] multiArrayParticipants = databaseEvent.getParticipantsForEventById(idOfEvent);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Participants");
        String[] columnHeadings = {"First name", "Surname", "Phone Number", "Ticket type"};

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());

        Row headerRow = sheet.createRow(0);


        for (int i=0; i < columnHeadings.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnHeadings[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 2);
        }

        int rowCount = 0;

        for (String[] strings : multiArrayParticipants) {
            Row row = sheet.createRow(++rowCount);


            int columnCount = 0;

            for (String field : strings) {
                Cell cell = row.createCell(columnCount++);
                    cell.setCellValue((String) field);

            }

        }
        return workbook;

    }


}
