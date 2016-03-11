package ru.excel_parser.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.excel_parser.data.Page;
import ru.excel_parser.view.PageColumn;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Dmitrii on 11.03.2016.
 */
public final class ExcelParser {
    private ExcelParser() {
    }

    private enum ContentType implements FactoryConverterIO<InputStream, Workbook> {
        OPEN_XML("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", (InputStream is) -> new XSSFWorkbook(is)),

        EXCEL("application/vnd.ms-excel", (InputStream is) -> new HSSFWorkbook(is));

        private final String contentType;

        private final FactoryConverterIO<InputStream, Workbook> supplier;

        ContentType(String contentType, FactoryConverterIO<InputStream, Workbook> supplier) {
            this.contentType = contentType;
            this.supplier = supplier;
        }

        public Workbook get(InputStream is) throws IOException {
            return supplier.get(is);
        }

        public String getContentType() {
            return contentType;
        }

        @Override
        public String toString() {
            return contentType;
        }

        public static Optional<ContentType> valueOfStr(String contentType) {
            contentType = contentType.trim();
            for(ContentType iter : ContentType.values()) {
                if(iter.getContentType().equalsIgnoreCase(contentType)) {
                    return Optional.of(iter);
                }
            }

            return Optional.empty();
        }
    }

    public static Iterable<Page> parse(byte[] data, String contentTypeStr) throws IOException {
        Collection<Page> pages = new LinkedList<>();
        Optional<ContentType> contentType = ContentType.valueOfStr(contentTypeStr);
        if(!contentType.isPresent()) {
            throw new UnknownFormatConversionException(contentTypeStr);
        }

        try (Workbook workbook = contentType.get().get(new ByteArrayInputStream(data))) {
            Sheet sheet = workbook.getSheetAt(0);
            Map<Integer, PageColumn> mapColumn = new HashMap<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    parseHeader(mapColumn, row);
                    continue;
                } else if (mapColumn.isEmpty()) {
                    return Collections.emptyList();
                }
                Optional<Page> page = parseRow(mapColumn, row);

                if(page.isPresent()) {
                    pages.add(page.get());
                }
            }
        }

        return pages;
    }

    private static void parseHeader(Map<Integer, PageColumn> mapColumn, Row row) {
        for (Cell cell : row) {
            Optional<PageColumn> pageColumn = PageColumn.valueOfStr(cell.getStringCellValue());
            if (!pageColumn.isPresent()) {
                continue;
            }

            mapColumn.put(cell.getColumnIndex(), pageColumn.get());
        }
    }

    private static Optional<Page> parseRow(Map<Integer, PageColumn> mapColumn, Row row) {
        Page page = new Page();
        for (Cell cell : row) {
            if (!mapColumn.containsKey(cell.getColumnIndex())) {
                continue;
            }

            PageColumn column = mapColumn.get(cell.getColumnIndex());
            try {
                column.set(page, getCellValue(cell, column.getCellType() == PageColumn.CELL_TYPE_DATE));
            }catch(Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        page.setId(null);
        return Optional.of(page);
    }

    private static Object getCellValue(Cell cell, boolean isDate) throws ParseException {
        switch(cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                return isDate ? cell.getDateCellValue() : cell.getNumericCellValue();

            case Cell.CELL_TYPE_BOOLEAN:
                if(isDate) {
                    throw new ParseException("Date cell type should be numeric", 0);
                }
                return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_STRING:
                return !isDate ? cell.getStringCellValue() : ConcurrentDateFormat.getInstance().convertStringToDate(cell.getStringCellValue());

            default:
                return cell.getStringCellValue();
        }
    }

}
