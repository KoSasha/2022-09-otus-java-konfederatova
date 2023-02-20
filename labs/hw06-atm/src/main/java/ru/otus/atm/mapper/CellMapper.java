package ru.otus.atm.mapper;

import ru.otus.atm.type.Cell;
import ru.otus.atm.type.Denomination;

public class CellMapper {

    public Cell createCell(Denomination denomination, int count) {
        return Cell.builder()
                .denomination(denomination)
                .count(count)
                .build();
    }
}
