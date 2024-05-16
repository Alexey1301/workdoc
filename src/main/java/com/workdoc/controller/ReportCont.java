package com.workdoc.controller;

import com.workdoc.controller.main.Main;
import com.workdoc.model.Report;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/reports")
public class ReportCont extends Main {
    @GetMapping
    public String reports(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("reports", reportRepo.findAll());
        return "reports";
    }

    @GetMapping("/add")
    public String add(Model model) {
        getCurrentUserAndRole(model);
        return "report_add";
    }

    @PostMapping("/add")
    public String add(Model model, @RequestParam String name, @RequestParam MultipartFile file) {
        Report report = new Report(name, getUser());

        try {
            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                report.setFile(saveFile(file, "reports"));
            }
        } catch (IOException e) {
            model.addAttribute("message", "Некорректные данные");
            getCurrentUserAndRole(model);
            return "report_add";
        }

        reportRepo.save(report);

        return "redirect:/reports/add";
    }
}