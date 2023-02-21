package ru.otus.atm.service;

import lombok.AllArgsConstructor;
import ru.otus.atm.mapper.CellMapper;
import ru.otus.atm.type.Cell;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class ATM implements ATMService {

    private List<Cell> cells;

    private CellMapper cellMapper;

    private int withdrawnAmount;

    private int requestedAmount;

    public ATM() {
        this.cells = new ArrayList<>();
        this.cellMapper = new CellMapper();
        this.withdrawnAmount = 0;
        this.requestedAmount = 0;
    }

    @Override
    public void acceptBanknotes(List<Cell> cells) {
        if (!this.cells.isEmpty()) {
            this.cells = new ArrayList<>(Stream.of(this.cells, cells)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toMap(Cell::getDenomination, c -> c,
                            (c1, c2) -> {
                                c1.setCount(c1.getCount() + c2.getCount());
                                return c1;
                            }, LinkedHashMap::new)).values());
        } else {
            this.cells = cells;
        }
    }

    @Override
    public int getRequestedAmount(int requestedAmount) {
        if (isThereEnoughMoney(requestedAmount)) {
            if (isRequestedAmountValid(requestedAmount)) {
                withdrawnAmount = 0;
                this.requestedAmount = requestedAmount;
                List<Cell> cellsClone = cells.stream().map(c -> cellMapper.createCell(c.getDenomination(), c.getCount()))
                        .collect(Collectors.toList());
                Collections.sort(cells, Collections.reverseOrder());
                cells.forEach(this::getBanknoteFromCellForAmount);
                if (withdrawnAmount == requestedAmount) {
                    return withdrawnAmount;
                }
                cells = cellsClone;
            } else {
                System.out.println("Некорректная сумма списания");
            }
        } else {
            System.out.println("Недостаточно средств в банкомате");
        }
        System.out.println("Невозможно списать запрашиваемую сумму");
        return 0;
    }

    @Override
    public int getBalance() {
        return cells.stream().mapToInt(Cell::calculateAmountForCell).sum();
    }

    @Override
    public void printCells() {
        for (Cell cell: this.cells) {
            System.out.println("Ячейка с номиналом: " + cell.getDenomination() + ", c количеством купюр: " + cell.getCount());
        }
    }

    private void getBanknoteFromCellForAmount(Cell cell) {
        if (requestedAmount != 0 && cell.getDenomination().getValue() <= requestedAmount && cell.getCount() != 0) {
            int mod = requestedAmount % cell.getDenomination().getValue();
            if (mod == 0) {
                takeFromCell(requestedAmount, cell);
            } else {
                int tmp = requestedAmount - mod;
                takeFromCell(tmp, cell);
                requestedAmount -= tmp;
            }
        }
    }

    private void takeFromCell(int requestedAmount, Cell cell) {
        int banknoteCount = cell.getCount();
        int div = requestedAmount / cell.getDenomination().getValue();
        if (div >= banknoteCount) {
            withdrawnAmount += cell.getDenomination().getValue() * div;
            cell.setCount(banknoteCount - div);
        } else {
            withdrawnAmount += cell.getDenomination().getValue() * banknoteCount;
            cell.setCount(0);
        }
    }

    private boolean isRequestedAmountValid(int requestedAmount) {
        return requestedAmount > 0 && requestedAmount % 100 == 0;
    }

    private boolean isThereEnoughMoney(int requestedAmount) {
        return !cells.isEmpty() && requestedAmount <= getBalance();
    }

}
