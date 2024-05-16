package com.workdoc.controller;

import com.workdoc.controller.main.Main;
import com.workdoc.model.AppUser;
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
@RequestMapping("/profile")
public class ProfileCont extends Main {
    @GetMapping
    public String profile(Model model) {
        getCurrentUserAndRole(model);
        return "profile";
    }

    @PostMapping("/photo")
    public String photo(Model model, @RequestParam MultipartFile photo) {
        AppUser user = getUser();
        try {
            if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
                user.setPhoto(saveFile(photo, "users"));
            }
        } catch (IOException e) {
            model.addAttribute("message", "Некорректные данные!");
            getCurrentUserAndRole(model);
            return "profile";
        }
        userRepo.save(user);
        return "redirect:/profile";
    }

    @PostMapping("/edit")
    public String fio(@RequestParam String fio, @RequestParam String email) {
        AppUser user = getUser();
        user.setFio(fio);
        user.setEmail(email);
        userRepo.save(user);
        return "redirect:/profile";
    }
}