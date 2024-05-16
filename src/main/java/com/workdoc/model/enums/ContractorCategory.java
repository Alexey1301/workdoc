package com.workdoc.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum ContractorCategory {
    LEGAL("Юр. лицо"),
    FIZ("Физ. лицо"),
    ;

    private final String name;

}

