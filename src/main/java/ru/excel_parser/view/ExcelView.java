package ru.excel_parser.view;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import ru.excel_parser.data.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created by Dmitrii on 05.03.2016.
 */
public class ExcelView extends AbstractXlsView {

    public static final String NAME = "ExcelView";

    public static final String ROW = "page";
    public static final String NAME_SHEET = "Page";


    @Override
    protected void buildExcelDocument(Map<String, Object> map, org.apache.poi.ss.usermodel.Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Iterable<Page> rows = (Iterable<Page>) map.get(ExcelView.ROW);
        if (rows == null) {
            return;
        }

        Sheet excelSheet = workbook.createSheet(NAME_SHEET);

        buildExcelHeader(excelSheet);
        buildExcelRows(excelSheet, rows);
    }

    private void buildExcelHeader(Sheet excelSheet) {
        Row excelHeader = excelSheet.createRow(0);

        int columnPos = 0;
        for (PageColumn column : PageColumn.values()) {
            excelHeader.createCell(columnPos++).setCellValue(column.toString());
        }
    }

    private void buildExcelRows(Sheet excelSheet, Iterable<Page> rows) {
        int rowPos = 0;

        for (Page row : rows) {
            Row excelRow = excelSheet.createRow(++rowPos);

            buildCells(row, excelRow);
        }
    }

    private void buildCells(Page page, Row excelRow) {
        int columnPos = 0;
        for (PageColumn column : PageColumn.values()) {
            Cell cell = excelRow.createCell(columnPos++);

            Object value = column.apply(page);
            if (value == null) {
                continue;
            }

            switch (column.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    cell.setCellValue((Long) value);
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cell.setCellValue((Boolean) value);
                    break;
                case PageColumn.CELL_TYPE_DATE:
                    cell.setCellValue((Date) value);
                    break;
                default:
                    if (value == null) {
                        continue;
                    }
                    cell.setCellValue(value.toString());
            }
        }
    }
}
