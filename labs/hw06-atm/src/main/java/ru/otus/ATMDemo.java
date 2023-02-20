package ru.otus;

import ru.otus.atm.mapper.CellMapper;
import ru.otus.atm.service.ATMService;
import ru.otus.atm.service.ATM;
import ru.otus.atm.type.Cell;
import ru.otus.atm.type.Denomination;

import java.util.ArrayList;
import java.util.List;

public class ATMDemo {
    public static void main(String[] args) {
        CellMapper cellMapper = new CellMapper();
        List<Cell> startCells = new ArrayList<>();
        startCells.add(cellMapper.createCell(Denomination.FIVE_THOUSAND, 1));
        ATMService atmService = new ATM(startCells, new CellMapper(), 0, 0);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cellMapper.createCell(Denomination.TWO_HUNDRED, 2));

        atmService.acceptBanknotes(cellList);

        int atmAmount = atmService.getBalance();
        System.out.println("Баланс: " + atmAmount);

        int requestedAmount = atmService.getRequestedAmount(500);
        System.out.println("Списано: " + requestedAmount);

        atmAmount = atmService.getBalance();
        System.out.println("Баланс после неудачного списания: " + atmAmount);

        requestedAmount = atmService.getRequestedAmount(400);
        System.out.println("Списано: " + requestedAmount);

        atmAmount = atmService.getBalance();
        System.out.println("Баланс после списания: " + atmAmount);

        atmService.printCells();
    }
}