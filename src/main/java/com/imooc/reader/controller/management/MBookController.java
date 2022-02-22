package com.imooc.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.exception.BusinessException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/book")
public class MBookController {
    @Resource
    private BookService bookService;
    @GetMapping("/index.html")
    public ModelAndView showBook() {
        return new ModelAndView("/management/book");
    }

    /**
     * wangEditor file upload
     * @param file file to be uploaded
     * @param request Servlet http request
     * @return wangEditor need this return
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("img")MultipartFile file, HttpServletRequest request) throws IOException {
        String uploadPath = request.getServletContext().getResource("/").getPath() + "/upload/";
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        file.transferTo(new File(uploadPath + fileName + suffix));
        Map<String, Object> result = new HashMap<>();
        result.put("errno", 0);
        result.put("data", new String[]{"/upload/" + fileName +suffix});
        return result;
    }

    /**
     * create new book
     * @param book
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public Map<String, String> createBook(Book book) {
        Map<String, String> result = new HashMap<>();
        try {
            book.setEvaluationQuantity(0);
            book.setEvaluationScore(0F);
            Document doc = Jsoup.parse(book.getDescription()); //Parse the book details
            Element img = doc.select("img").first(); //Get the first picture of the book description
            String cover = img.attr(("src"));
            book.setCover(cover);
            bookService.createBook(book);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BusinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

    /**
     * Query a page
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object>list(Integer page, Integer limit) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        IPage<Book> pageObject = bookService.paging(null, null, page, limit);
        // layui need this responded
        Map<String, Object> result = new HashMap<>();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", pageObject.getRecords());    // current page data
        result.put("count", pageObject.getTotal()); // total number book
        return result;
    }

    /**
     * show original content before update
     * @param bookId
     * @return
     */
    @GetMapping("/id/{id}")
    @ResponseBody
    public Map<String, Object> selectById(@PathVariable("id") Long bookId) {
        Book book = bookService.selectById(bookId);
        Map<String, Object> result = new HashMap<>();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", book);
        return result;
    }

    /**
     * update book
     * @param book
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public Map updateBook(Book book) {
        Map<String, String> result = new HashMap<>();
        try {
            Book rawBook = bookService.selectById(book.getBookId());
            rawBook.setBookName(book.getBookName());
            rawBook.setSubTitle(book.getSubTitle());
            rawBook.setAuthor(book.getAuthor());
            rawBook.setCategoryId(book.getCategoryId());
            rawBook.setDescription(book.getDescription());
            Document doc = Jsoup.parse(book.getDescription());
            String cover = doc.select("img").first().attr("src");
            rawBook.setCover(cover);
            bookService.updateBook(rawBook);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BusinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return  result;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public Map<String, String> deleteBook(@PathVariable("id") Long bookId) {
        Map<String, String> result = new HashMap<>();
        try {
            bookService.deleteBook(bookId);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BusinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return  result;
    }
}
