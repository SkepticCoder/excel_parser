package ru.excel_parser.view;

import org.apache.poi.ss.usermodel.Cell;
import ru.excel_parser.data.Page;
import ru.excel_parser.utils.ConcurrentDateFormat;
import ru.excel_parser.utils.SafeBiConsumer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Dmitrii on 10.03.2016.
 */
public enum PageColumn implements Function<Page, Object> {

    CODE("code", Page::getId, (Page obj, Object value) -> {
        if ((value instanceof Long)) {
            obj.setId((Long) value);
        } else if (value instanceof Double) {
            obj.setId(((Double) value).longValue());
        } else {
            Long.valueOf(value.toString());
        }
    }
    ),

    NAME("name", Page::getName, (Page obj, Object value) -> obj.setName(value.toString())),

    PRICE("price", Page::getPrice, (Page obj, Object value) -> {
        if (value instanceof BigDecimal) {
            obj.setPrice((BigDecimal) value);
            return;
        }

        obj.setPrice(new BigDecimal(value.toString().replaceAll(",", "")));
    }),

    DATE("date", Page::getDateTime, (Page obj, Object value) -> {
        if (value instanceof Date) {
            obj.setDateTime((Date) value);
            return;
        }

        obj.setDateTime(ConcurrentDateFormat.getInstance().convertStringToDate(value.toString()));
    }, PageColumn.CELL_TYPE_DATE
    );

    public static final int CELL_TYPE_DATE = 100;
    private static final Optional<PageColumn> EMPTY = Optional.empty();
    private final Function<Page, Object> supplier;

    private final SafeBiConsumer<Page, Object> consumer;

    private final String strValue;
    private final int cellType;

    PageColumn(String strValue, Function<Page, Object> supplier, SafeBiConsumer<Page, Object> consumer) {
        this(strValue, supplier, consumer, Cell.CELL_TYPE_STRING);
    }

    PageColumn(String strValue, Function<Page, Object> supplier, SafeBiConsumer<Page, Object> consumer, int cellType) {
        this.supplier = supplier;
        this.strValue = strValue;
        this.cellType = cellType;
        this.consumer = consumer;
    }

    public static Optional<PageColumn> valueOfStr(String strValue) {
        for (PageColumn pageColumn : PageColumn.values()) {
            if (Objects.equals(pageColumn.toString(), strValue)) {
                return Optional.of(pageColumn);
            }
        }

        return EMPTY;
    }

    public int getCellType() {
        return cellType;
    }

    @Override
    public Object apply(Page page) {
        return supplier.apply(page);
    }

    public void set(Page page, Object value) throws Exception {
        consumer.accept(page, value);
    }

    @Override
    public String toString() {
        return strValue;
    }
}
