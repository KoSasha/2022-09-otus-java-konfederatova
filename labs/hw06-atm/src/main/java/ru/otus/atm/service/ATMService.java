package ru.otus.atm.service;

import ru.otus.atm.type.Cell;

import java.util.List;

public interface ATMService {

    void acceptBanknotes(List<Cell> banknotes);

    int getRequestedAmount(int requestedAmount);

    int getBalance();

    void printCells();

}
