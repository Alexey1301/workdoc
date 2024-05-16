package com.workdoc.controller;

import com.workdoc.controller.main.Main;
import com.workdoc.model.AppUser;
import com.workdoc.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserCont extends Main {
    @GetMapping
    public String users(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("users", userRepo.findAll());
        return "users";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @RequestParam Role role) {
        AppUser user = userRepo.getReferenceById(id);
        user.setRole(role);
        userRepo.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        userRepo.deleteById(id);
        return "redirect:/users";
    }
}