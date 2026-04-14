package com.matecoder.xss.example.web.controller;

import com.matecoder.xss.core.IgnoreXssClean;
import com.matecoder.xss.example.model.Article;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xss")
public class XssController {

    /**
     * 测试Json的xss
     * @param article 文章对象
     * @return 文章
     */
    @PostMapping("/testJsonXss")
    public Article testJsonXss(@RequestBody Article article) {
        System.out.println(article);
        return article;
    }

    /**
     * 测试Form的xss
     * @param article 文章对象
     * @return 文章
     */
    @PostMapping("/testFormXss")
    public Article testFormXss(Article article) {
        System.out.println(article);
        return article;
    }

    /**
     * 测试Json的xss
     * @param article 文章对象
     * @return 文章
     */
    @IgnoreXssClean
    @PostMapping("/testIgnoreXssClean")
    public Article testIgnoreXssClean(@RequestBody Article article) {
        System.out.println(article);
        return article;
    }
}
