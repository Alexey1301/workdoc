package com.workdoc.controller;

import com.workdoc.controller.main.Main;
import com.workdoc.model.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/accounts")
public class AccountCont extends Main {
    @GetMapping
    public String accounts(Model model) {
        getCurrentUserAndRole(model);
        return "accounts";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name, @RequestParam float sum, @RequestParam String currency) {
        accountRepo.save(new Account(name, sum, currency));
        return "redirect:/accounts";
    }

    @GetMapping("/{id}/status")
    public String status(@PathVariable Long id) {
        Account account = accountRepo.getReferenceById(id);
        account.setStatus(!account.isStatus());
        accountRepo.save(account);
        return "redirect:/accounts";
    }

    @PostMapping("/{id}/edit")
    public String edit(@RequestParam String name, @RequestParam float sum, @RequestParam String currency, @PathVariable Long id) {
        Account account = accountRepo.getReferenceById(id);
        account.set(name, sum, currency);
        accountRepo.save(account);
        return "redirect:/accounts";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        accountRepo.deleteById(id);
        return "redirect:/accounts";
    }
}
