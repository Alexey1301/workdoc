package com.workdoc.controller.main;

import com.workdoc.model.AppUser;
import com.workdoc.model.enums.ContractorCategory;
import com.workdoc.model.enums.DocumentCategory;
import com.workdoc.model.enums.Role;
import com.workdoc.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Main {
    @Autowired
    protected UserRepo userRepo;
    @Autowired
    protected ContractorRepo contractorRepo;
    @Autowired
    protected AccountRepo accountRepo;
    @Autowired
    protected DocumentRepo documentRepo;
    @Autowired
    protected DocumentCommentRepo documentCommentRepo;
    @Autowired
    protected ReportRepo reportRepo;

    @Value("${upload.img}")
    protected String uploadImg;

    protected void getCurrentUserAndRole(Model model) {
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
        model.addAttribute("photo", getPhoto());
        model.addAttribute("fio", getFio());

        model.addAttribute("roles", Role.values());
        model.addAttribute("contractorCategories", ContractorCategory.values());
        model.addAttribute("documentCategories", DocumentCategory.values());
        model.addAttribute("accounts", accountRepo.findAll());
        model.addAttribute("contractors", contractorRepo.findAll());
    }

    protected AppUser getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            return userRepo.findByUsername(userDetail.getUsername());
        }
        return null;
    }

    protected String getRole() {
        AppUser appUser = getUser();
        if (appUser == null) return "NOT";
        return appUser.getRole().name();
    }

    protected String getPhoto() {
        AppUser appUser = getUser();
        if (appUser == null) return "def.jpg";
        return appUser.getPhoto();
    }

    protected String getFio() {
        AppUser appUser = getUser();
        if (appUser == null) return "Добро пожаловать";
        return appUser.getFio();
    }

    public static String getDate() {
        return LocalDateTime.now().toString().substring(0, 10);
    }

    public static String getDateAndTime() {
        String date = LocalDateTime.now().toString();
        return date.substring(0, 10) + " " + date.substring(11, 16);
    }

    protected String saveFile(MultipartFile photo, String path) throws IOException {
        if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            File uploadDir = new File(uploadImg);
            if (!uploadDir.exists()) uploadDir.mkdir();
            String result = path + "/" + uuidFile + "_" + photo.getOriginalFilename();
            photo.transferTo(new File(uploadImg + "/" + result));
            return result;
        } else throw new IOException();
    }

    public static float round(float value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (float) tmp / factor;
    }
}
