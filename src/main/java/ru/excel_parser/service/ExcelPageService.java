package ru.excel_parser.service;

import ru.excel_parser.data.Page;

/**
 * Created by Dmitrii on 09.03.2016.
 */

public interface ExcelPageService {

    void add(Iterable<Page> records);

    Iterable<Page> getAll();
}
