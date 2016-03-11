package ru.excel_parser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.excel_parser.data.Page;
import ru.excel_parser.data.PageRepository;
import ru.excel_parser.service.ExcelPageService;

/**
 * Created by Dmitrii on 09.03.2016.
 */
@Service
public class ExcelPageServiceImpl implements ExcelPageService {

    @Autowired
    private PageRepository repository;

    @Override
    public void add(Iterable<Page> records) {
        repository.save(records);
    }

    @Override
    public Iterable<Page> getAll() {
        return repository.findAll();
    }
}
