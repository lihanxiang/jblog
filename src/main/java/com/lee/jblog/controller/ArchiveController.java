package com.lee.jblog.controller;

import com.lee.jblog.service.ArchiveService;
import com.lee.jblog.service.ArticleService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ArchiveController {

    private final ArchiveService archiveService;
    private final ArticleService articleService;

    @Autowired
    public ArchiveController(ArchiveService archiveService, ArticleService articleService) {
        this.archiveService = archiveService;
        this.articleService = articleService;
    }

    @GetMapping("/getArchiveNameAndSize")
    public JSONObject getArchiveNameAndSize(){
        return archiveService.getArchiveNameAndSize();
    }

    @GetMapping("/getArchiveArticle")
    public JSONObject getArchiveArticle(@RequestParam("archive") String archive,
                                        HttpServletRequest request){
        int rows = Integer.parseInt(request.getParameter("rows"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        return articleService.getArticleByArchive(archive, rows, pageNum);
    }
}
