package com.workdoc.controller;

import com.workdoc.controller.main.Main;
import com.workdoc.model.Contractor;
import com.workdoc.model.enums.ContractorCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contractors")
public class ContractorCont extends Main {
    @GetMapping
    public String contractors(Model model) {
        getCurrentUserAndRole(model);
        return "contractors";
    }

    @PostMapping("/add")
    public String add(@RequestParam int tin, @RequestParam String name, @RequestParam String address, @RequestParam String tel, @RequestParam ContractorCategory category) {
        contractorRepo.save(new Contractor(tin, name, address, tel, category));
        return "redirect:/contractors";
    }

    @PostMapping("/{id}/edit")
    public String edit(@RequestParam int tin, @RequestParam String name, @RequestParam String address, @RequestParam String tel, @RequestParam ContractorCategory category, @PathVariable Long id) {
        Contractor contractor = contractorRepo.getReferenceById(id);
        contractor.set(tin, name, address, tel, category);
        contractorRepo.save(contractor);
        return "redirect:/contractors";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        contractorRepo.deleteById(id);
        return "redirect:/contractors";
    }
}
