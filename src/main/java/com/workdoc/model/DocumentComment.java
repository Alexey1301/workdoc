package com.workdoc.model;

import com.workdoc.controller.main.Main;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DocumentComment implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String date = Main.getDate();

    @Column(length = 5000)
    private String text;

    @ManyToOne
    private AppUser owner;
    @ManyToOne
    private Document document;

    public DocumentComment(String text, AppUser owner, Document document) {
        this.text = text;
        this.owner = owner;
        this.document = document;
    }
}