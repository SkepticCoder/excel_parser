package ru.excel_parser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.excel_parser.data.Page;
import ru.excel_parser.service.ExcelPageService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Dmitrii on 05.03.2016.
 */

@RestController
@RequestMapping("/page")
public class PagesCRUDController {

    private ExcelPageService service;

    @Autowired
    public PagesCRUDController(ExcelPageService service) {
        this.service = service;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public Iterable<Page> readRecords() {
        return service.getAll();
    }
}
