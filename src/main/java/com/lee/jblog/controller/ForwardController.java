package com.lee.jblog.controller;

import com.lee.jblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

/**
 * 之所以不用 RestController，
 * 是因为要跳转页面
 */
@Controller
public class ForwardController {

    private final ArticleService articleService;

    @Autowired
    public ForwardController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response,
                        @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            request.getSession().removeAttribute("lastURL");
            return "index";
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("lastURL", (String) request.getSession().getAttribute("lastURL"));
        return "index";
    }

    @GetMapping("/myStory")
    public String myStory(HttpServletRequest request){
        request.getSession().removeAttribute("lastURL");
        return "myStory";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request){
        return "login";
    }

    @GetMapping("/preLogin")
    @ResponseBody
    public void preLogin(HttpServletRequest request){
        request.getSession().setAttribute("lastURL", request.getHeader("preLogin"));
    }

    @GetMapping("/signUP")
    public String signUP(HttpServletRequest request){
        return "signUP";
    }

    @GetMapping("/aboutMe")
    public String aboutMe(HttpServletRequest request){
        request.getSession().removeAttribute("lastURL");
        return "aboutMe";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request){
        request.getSession().removeAttribute("lastURL");
        return "update";
    }

    @GetMapping("/user")
    public String user(HttpServletRequest request){
        request.getSession().removeAttribute("lastURL");
        return "user";
    }

    @GetMapping("/editor")
    public String editor(HttpServletRequest request){
        request.getSession().removeAttribute("lastURL");
        String ID = request.getParameter("ID");
        if (!ID.equals("")){
            request.getSession().setAttribute("ID", ID);
        }
        return "editor";
    }

    @GetMapping("/article/{articleID}")
    public String showArticle(@PathVariable("articleID") long articleID,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              Model model){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        request.getSession().removeAttribute("lastURL");
        Map<String, String> articles = articleService.getArticleTitleByArticleID(articleID);
        if (articles.get("articleTitle") != null){
            model.addAttribute("articleTitle", articles.get("articleTitle"));
            String articleTabloid = articles.get("articleTabloid");
            if (articleTabloid.length() <= 110){
                model.addAttribute("articleTabloid", articleTabloid);
            } else {
                model.addAttribute("articleTabloid", articleTabloid.substring(0, 111));
            }
        }
        response.setHeader("articleID", String.valueOf(articleID));
        return "show";
    }

    @GetMapping("/archives")
    public String archives(HttpServletRequest request,
                           HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        request.getSession().removeAttribute("lastURL");
        String archive = request.getParameter("archive");
        response.setHeader("archive", archive);
        return "archives";
    }

    @GetMapping("/categories")
    public String categories(HttpServletRequest request,
                           HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        request.getSession().removeAttribute("lastURL");
        String category = request.getParameter("categories");
        response.setHeader("category", category);
        return "categories";
    }

    @GetMapping("/tags")
    public String tags(HttpServletRequest request,
                             HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        request.getSession().removeAttribute("lastURL");
        String tag = request.getParameter("tag");
        response.setHeader("tag", tag);
        return "tags";
    }

    @GetMapping("/admin")
    public String admin(HttpServletRequest request){
        request.getSession().removeAttribute("lastURL");
        return "admin";
    }

    @GetMapping("/today")
    public String today(){
        return "today";
    }
}
