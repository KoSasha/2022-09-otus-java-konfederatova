package ru.otus.atm.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Cell implements Comparable<Cell> {

    private Denomination denomination;

    private int count;

    public int calculateAmountForCell() {
        return this.denomination.getValue() * this.count;
    }

    @Override
    public int compareTo(Cell cell) {
        return this.denomination.getValue() - cell.getDenomination().getValue();
    }
}
