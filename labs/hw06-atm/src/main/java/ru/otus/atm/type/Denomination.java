package ru.otus.atm.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Denomination {
    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000),

    ;

    private final int value;
}
