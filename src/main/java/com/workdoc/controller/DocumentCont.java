package com.workdoc.controller;

import com.workdoc.controller.main.Main;
import com.workdoc.model.Document;
import com.workdoc.model.DocumentComment;
import com.workdoc.model.enums.DocumentCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/documents")
public class DocumentCont extends Main {
    @GetMapping
    public String documents(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("documents", documentRepo.findAll());
        return "documents";
    }

    @GetMapping("/{id}")
    public String document(Model model, @PathVariable Long id) {
        getCurrentUserAndRole(model);
        model.addAttribute("document", documentRepo.getReferenceById(id));
        return "document";
    }

    @PostMapping("/{id}/comment")
    public String comment(@PathVariable Long id, @RequestParam String text) {
        documentCommentRepo.save(new DocumentComment(text, getUser(), documentRepo.getReferenceById(id)));
        return "redirect:/documents/{id}";
    }

    @GetMapping("/add")
    public String add(Model model) {
        getCurrentUserAndRole(model);
        return "document_add";
    }

    @PostMapping("/add")
    public String add(
            Model model, @RequestParam String name, @RequestParam float income, @RequestParam float expense,
            @RequestParam DocumentCategory category, @RequestParam Long accountId, @RequestParam Long contractorId,
            @RequestParam MultipartFile file
    ) {
        Document document = new Document(name, income, expense, category, accountRepo.getReferenceById(accountId), contractorRepo.getReferenceById(contractorId));

        try {
            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                document.setFile(saveFile(file, "documents"));
            }
        } catch (IOException e) {
            model.addAttribute("message", "Некорректные данные");
            getCurrentUserAndRole(model);
            return "document_add";
        }

        document = documentRepo.save(document);

        return "redirect:/documents/" + document.getId();
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id) {
        getCurrentUserAndRole(model);
        model.addAttribute("document", documentRepo.getReferenceById(id));
        return "document_edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(
            Model model, @RequestParam String name, @RequestParam float income, @RequestParam float expense,
            @RequestParam DocumentCategory category, @RequestParam Long accountId, @RequestParam Long contractorId,
            @RequestParam MultipartFile file, @PathVariable Long id
    ) {
        Document document = documentRepo.getReferenceById(id);

        document.set(name, income, expense, category, accountRepo.getReferenceById(accountId), contractorRepo.getReferenceById(contractorId));

        try {
            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                document.setFile(saveFile(file, "documents"));
            }
        } catch (IOException e) {
            model.addAttribute("message", "Некорректные данные");
            getCurrentUserAndRole(model);
            model.addAttribute("document", documentRepo.getReferenceById(id));
            return "document_edit";
        }

        documentRepo.save(document);

        return "redirect:/documents/{id}";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        documentRepo.deleteById(id);
        return "redirect:/documents";
    }
}
