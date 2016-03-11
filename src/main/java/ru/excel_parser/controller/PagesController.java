package ru.excel_parser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.excel_parser.data.Page;
import ru.excel_parser.utils.ExcelParser;
import ru.excel_parser.view.ExcelView;
import ru.excel_parser.service.ExcelPageService;

import java.io.IOException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

/**
 * Created by Dmitrii on 09.03.2016.
 */
@Controller
@RequestMapping
public class PagesController {

    private ExcelPageService service;

    @Autowired
    public PagesController(ExcelPageService service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = TEXT_HTML_VALUE)
    public String main(ModelAndView model) {
        return "main";
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ModelAndView getExcel() {
        ModelAndView view =  new ModelAndView(ExcelView.NAME, ExcelView.ROW, service.getAll());
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/import", consumes = MULTIPART_FORM_DATA_VALUE, produces = TEXT_HTML_VALUE)
    public @ResponseBody String provideUploadInfo(@RequestParam("file") MultipartFile file) {
        try {
            service.add(ExcelParser.parse(file.getBytes(), file.getContentType()));
        } catch (Exception e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }

        return "success upload ";
    }
    public void uploadExcel() {

    }
}
