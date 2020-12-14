package com.uzykj.chinatruck.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ghostxbh
 */
@Controller
public class ViewController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/number.html")
    public String number(){
        return "number";
    }

    @GetMapping("/categories.html")
    public String categories(){
        return "categories";
    }

    @GetMapping("/about.html")
    public String about(){
        return "about";
    }
}
