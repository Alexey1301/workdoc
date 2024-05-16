package com.workdoc.controller;

import com.workdoc.controller.main.Main;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexCont extends Main {
    @GetMapping("/index")
    public String index1() {
        return "redirect:/documents";
    }

    @GetMapping("/")
    public String index2() {
        return "redirect:/documents";
    }
}