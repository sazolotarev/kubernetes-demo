package com.example.kubernetes_demo.ui.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value = {"/", "/login", "/logout", "/register", "/todo/lists/**"}, produces = MediaType.TEXT_HTML_VALUE)
    public String index() {
        return "forward:/index.html";
    }
}
