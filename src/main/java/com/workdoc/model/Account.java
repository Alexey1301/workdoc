package com.workdoc.model;

import com.workdoc.controller.main.Main;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private float sum;
    private String currency;

    private String date = Main.getDate();
    private boolean status = true;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<>();

    public Account(String name, float sum, String currency) {
        this.name = name;
        this.sum = sum;
        this.currency = currency;
    }

    public void set(String name, float sum, String currency) {
        this.name = name;
        this.sum = sum;
        this.currency = currency;
    }
}