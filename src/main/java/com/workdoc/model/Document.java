package com.workdoc.model;

import com.workdoc.controller.main.Main;
import com.workdoc.model.enums.DocumentCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Document implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private float income;
    private float expense;
    private String date = Main.getDate();
    private String file;

    @Enumerated(EnumType.STRING)
    private DocumentCategory category;

    @ManyToOne
    private Account account;
    @ManyToOne
    private Contractor contractor;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<DocumentComment> documentComments = new ArrayList<>();

    public Document(String name, float income, float expense, DocumentCategory category, Account account, Contractor contractor) {
        this.name = name;
        this.income = income;
        this.expense = expense;
        this.category = category;
        this.account = account;
        this.contractor = contractor;
    }

    public void set(String name, float income, float expense, DocumentCategory category, Account account, Contractor contractor) {
        this.name = name;
        this.income = income;
        this.expense = expense;
        this.category = category;
        this.account = account;
        this.contractor = contractor;
    }

    public List<DocumentComment> getDocumentComments() {
        documentComments.sort(Comparator.comparing(DocumentComment::getId));
        Collections.reverse(documentComments);
        return documentComments;
    }
}