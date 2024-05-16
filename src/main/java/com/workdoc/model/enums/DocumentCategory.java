package com.workdoc.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DocumentCategory {
    CONTRACT("Договор"),
    PAYMENT ("Счет на оплату"),
    INVOICE("Товарная накладная"),
    ACT_COMPLETED_WORKS("Акт выполненных работ"),
    ;

    private final String name;

}

