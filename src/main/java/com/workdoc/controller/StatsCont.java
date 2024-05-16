package com.workdoc.controller;

import com.workdoc.controller.main.Main;
import com.workdoc.model.Contractor;
import com.workdoc.model.Document;
import com.workdoc.model.enums.DocumentCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/stats")
public class StatsCont extends Main {
    @GetMapping
    public String stats(Model model) {
        getCurrentUserAndRole(model);

        DocumentCategory[] categories = DocumentCategory.values();

        String[] categoriesNames = new String[categories.length];
        int[] categoriesValues = new int[categories.length];

        for (int i = 0; i < categories.length; i++) {
            categoriesNames[i] = categories[i].getName();
            categoriesValues[i] = documentRepo.findAllByCategory(categories[i]).size();
        }

        model.addAttribute("categoriesNames", categoriesNames);
        model.addAttribute("categoriesValues", categoriesValues);

        model.addAttribute("accountStatusNames", new String[]{"Активен", "Заблокирован"});
        model.addAttribute("accountStatusValues", new int[]{accountRepo.findAllByStatusTrue().size(), accountRepo.findAllByStatusFalse().size()});

        List<Contractor> contractors = contractorRepo.findAll();

        String[] contractorNames = new String[contractors.size()];
        int[] contractorValues = new int[contractors.size()];

        for (int i = 0; i < contractors.size(); i++) {
            contractorNames[i] = contractors.get(i).getName();
            contractorValues[i] = contractors.get(i).getDocuments().size();
        }

        model.addAttribute("contractorNames", contractorNames);
        model.addAttribute("contractorValues", contractorValues);

        return "stats";
    }
}
