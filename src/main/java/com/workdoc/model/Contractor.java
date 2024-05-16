package com.workdoc.model;

import com.workdoc.model.enums.ContractorCategory;
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
public class Contractor implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private int tin;
    private String name;
    private String address;
    private String tel;

    @Enumerated(EnumType.STRING)
    private ContractorCategory category;

    @OneToMany(mappedBy = "contractor", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<>();

    public Contractor(int tin, String name, String address, String tel, ContractorCategory category) {
        this.tin = tin;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.category = category;
    }

    public void set(int tin, String name, String address, String tel, ContractorCategory category) {
        this.tin = tin;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.category = category;
    }
}