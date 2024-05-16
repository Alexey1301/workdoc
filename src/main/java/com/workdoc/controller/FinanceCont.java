package com.workdoc.controller;

import com.workdoc.controller.main.Main;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/finance")
public class FinanceCont extends Main {
    @GetMapping
    public String finance(Model model) {
        getCurrentUserAndRole(model);

        String[] documentFinanceNames = new String[]{"Доход", "Расход"};
        float[] documentFinanceValues = new float[2];

        documentFinanceValues[0] = documentRepo.findAll().stream().reduce(0f, (i, document) -> i + document.getIncome(), Float::sum);
        documentFinanceValues[1] = documentRepo.findAll().stream().reduce(0f, (i, document) -> i + document.getExpense(), Float::sum);

        model.addAttribute("documentFinanceNames", documentFinanceNames);
        model.addAttribute("documentFinanceValues", documentFinanceValues);

        return "finance";
    }

    @GetMapping("/filter")
    public String filter(Model model, @RequestParam String dateWith, @RequestParam String dateBy) {
        getCurrentUserAndRole(model);
        model.addAttribute("dateWith", dateWith);
        model.addAttribute("dateBy", dateBy);

        Date with = new Date();
        Date by = new Date();
        try {
            with = new SimpleDateFormat("yyyy-MM-dd").parse(dateWith);
            by = new SimpleDateFormat("yyyy-MM-dd").parse(dateBy);
        } catch (ParseException ignored) {

        }

        Calendar calWith = Calendar.getInstance();
        calWith.setTime(with);
        calWith.add(Calendar.DATE, -1);
        Calendar calBy = Calendar.getInstance();
        calBy.setTime(by);
        calBy.add(Calendar.DATE, 1);

        String[] documentFinanceNames = new String[]{"Доход", "Расход"};
        float[] documentFinanceValues = new float[2];

        documentFinanceValues[0] = documentRepo.findAll().stream().reduce(0f, (i, document) -> {
            Date date = new Date();
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(document.getDate());
            } catch (ParseException ignored) {
            }
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(date);
            if (calDate.after(calWith) && calDate.before(calBy)) {
                return i + document.getIncome();
            }
            return i;
        }, Float::sum);

        documentFinanceValues[1] = documentRepo.findAll().stream().reduce(0f, (i, document) -> {
            Date date = new Date();
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(document.getDate());
            } catch (ParseException ignored) {
            }
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(date);
            if (calDate.after(calWith) && calDate.before(calBy)) {
                return i + document.getExpense();
            }
            return i;
        }, Float::sum);

        model.addAttribute("documentFinanceNames", documentFinanceNames);
        model.addAttribute("documentFinanceValues", documentFinanceValues);

        return "finance";
    }


}
